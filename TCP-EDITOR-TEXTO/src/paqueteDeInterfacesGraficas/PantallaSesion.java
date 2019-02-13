package paqueteDeInterfacesGraficas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cajaDeHerramientas.Cliente;
import cajaDeHerramientas.Msg;
import cajaDeHerramientas.Usuario;
import paqueteDeDatos.PaqueteInicioSesion;

public class PantallaSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pantallaSesion;
	private JTextField email;
	private JPasswordField password;
	private JTextField estado;
	private Cliente cli;
	private String actionListenerActivada = null;
	private Usuario usr;
	private JTextField tfnombreUsuario;

	public String obtenerAccionLanzada() {
		return actionListenerActivada;
	}

	public Cliente obtenerSocketCliente() {
		return cli;
	}

	public String obtenerEstado() {
		return estado.getText();
	}

	public PantallaSesion() {
		setTitle("Iniciar sesión");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		pantallaSesion = new JPanel();
		pantallaSesion.setPreferredSize(new Dimension(800, 600));
		pantallaSesion.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pantallaSesion);
		pantallaSesion.setLayout(null);
		setLocationRelativeTo(null);
		email = new JTextField();
		email.setText("");
		email.setBounds(320, 277, 164, 20);
		pantallaSesion.add(email);
		email.setColumns(10);
		cli = new Cliente("localhost", 5000);
		
		//TEMPORAL 

		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent es) {

				if (email.getText().isEmpty() || password.getText().isEmpty() || email.getText().equals(" ")
						|| password.getText().equals(" ")) {
					estado.setText("Debe ingresar el email y contraseña para poder ingresar al editor.");
					estado.setBackground(Color.RED);
				} else {
						String nombreUsuario = tfnombreUsuario.getText();
						String emailUsuario  = email.getText();
						String pass = password.getText();
						PaqueteInicioSesion packInicioSesion = new PaqueteInicioSesion(emailUsuario,nombreUsuario, pass);
						Msg msgAEnviar = new Msg("iniciarsesion", packInicioSesion);
						cli.enviarMsg(msgAEnviar);
						Msg msgARecibirDelSv = cli.recibirMsg();
						
						if (msgARecibirDelSv.getAccion().equals("OK")) {
							usr = new Usuario (nombreUsuario,emailUsuario);
							System.out.println("Todo correcto");
							actionListenerActivada = es.getActionCommand();
							estado.setText("Bienvenido "+nombreUsuario+"!!");
							estado.setBackground(Color.GREEN);
						}else {
							estado.setText("El nombre de usuario o contraseña no son correctas. Ingrese nuevamente!");
							estado.setBackground(Color.RED);
						}
				}

			}
		});
		btnIngresar.setBounds(281, 368, 89, 23);
		pantallaSesion.add(btnIngresar);

		JButton btnRegistrar = new JButton("Registrarse");
		btnRegistrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionListenerActivada = e.getActionCommand();
				PantallaRegistracion pantallaReg = new PantallaRegistracion();
				pantallaReg.setVisible(true);
				dispose();
			}
		});
		btnRegistrar.setBounds(421, 368, 112, 23);
		pantallaSesion.add(btnRegistrar);

		JLabel lblUsuario = new JLabel("Email");
		lblUsuario.setBounds(245, 280, 46, 14);
		pantallaSesion.add(lblUsuario);

		JLabel lblPass = new JLabel("Contrase\u00F1a");
		lblPass.setBounds(245, 311, 76, 14);
		pantallaSesion.add(lblPass);

		password = new JPasswordField();
		password.setBounds(320, 308, 164, 20);
		pantallaSesion.add(password);

		estado = new JTextField();
		estado.setEditable(false);
		estado.setBounds(0, 551, 794, 20);
		pantallaSesion.add(estado);
		estado.setColumns(10);
		
		tfnombreUsuario = new JTextField();
		tfnombreUsuario.setText("");
		tfnombreUsuario.setColumns(10);
		tfnombreUsuario.setBounds(320, 246, 164, 20);
		pantallaSesion.add(tfnombreUsuario);
		
		JLabel nombreUsuario = new JLabel("Su nombre");
		nombreUsuario.setBounds(245, 249, 65, 14);
		pantallaSesion.add(nombreUsuario);

		if (cli.obtenerMsgErr() != null) {
			estado.setText(cli.obtenerMsgErr() + ". Salga del juego y reinicie el Servidor por favor!.");
			estado.setBackground(Color.RED);
		}
		
		//TEMPORAL
		email.setText("nico");
		password.setText("123");

	}

	public Usuario getUsuario() {
		return usr;
	}
	


}
