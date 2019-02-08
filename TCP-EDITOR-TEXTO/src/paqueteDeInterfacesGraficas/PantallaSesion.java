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

import cajaDeHerramientas.Usuario;

public class PantallaSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pantallaSesion;
	private JTextField usuario;
	private JPasswordField password;
	private JTextField estado;
	private Usuario cli;
	private String actionListenerActivada = null;
	private String usuario1;

	public String obtenerAccionLanzada() {
		return actionListenerActivada;
	}

	public Usuario obtenerSocketCliente() {
		return cli;
	}

	public String obtenerEstado() {
		return estado.getText();
	}

	public PantallaSesion() {
		setTitle("Juego");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		pantallaSesion = new JPanel();
		pantallaSesion.setPreferredSize(new Dimension(800, 600));
		pantallaSesion.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pantallaSesion);
		pantallaSesion.setLayout(null);
		setLocationRelativeTo(null);
		usuario = new JTextField();
		usuario.setText("");
		usuario.setBounds(320, 277, 164, 20);
		pantallaSesion.add(usuario);
		usuario.setColumns(10);
		cli = new Usuario("192.168.1.51", 5000);
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent es) {

				if (usuario.getText().isEmpty() || password.getText().isEmpty() || usuario.getText().equals(" ")
						|| password.getText().equals(" ")) {
					estado.setText("Debe ingresar el usuario y contraseña para poder ingresar al juego.");
					estado.setBackground(Color.RED);
				} else {
					usuario1 = usuario.getText();
					//Armo consulta y envio al servidor
//					cli.enviarMsg(consultaAlServidor);
					//Recibo el msg
//					if ("OK") {
						System.out.println("Todo correcto");
						actionListenerActivada = es.getActionCommand();
						estado.setText("Ingresar");
						estado.setBackground(Color.GREEN);
//					} else {
						estado.setText("El nombre de usuario o contraseña no son correctas. Ingrese nuevamente!");
						estado.setBackground(Color.RED);
//					}
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

		JLabel lblUsuario = new JLabel("Usuario");
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

		if (cli.obtenerMsgErr() != null) {
			estado.setText(cli.obtenerMsgErr() + ". Salga del juego y reinicie el Servidor por favor!.");
			estado.setBackground(Color.RED);
		}

		///// Temporal
//		usuario.setText("nicoDrugstore");
//		password.setText("Configuracion1");

	}

	public String getUsuario() {
		return usuario1;
	}

}
