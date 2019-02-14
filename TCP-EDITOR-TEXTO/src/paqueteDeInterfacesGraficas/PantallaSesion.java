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
import paqueteDeDatos.PaqueteRegistracion;

public class PantallaSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pantallaSesion;
	private JTextField email;
	private JPasswordField password;
	private JTextField estado;
	private static Cliente cli = new Cliente("localhost", 5000);
	public static String actionListenerActivada;
	private Usuario usr;
	private JTextField tfnombreUsuario;
	private JLabel lblUsuario;
	private JLabel lblPass;
	private JButton btnRegistrar;
	private JButton btnIngresar;
	private JTextField nyaReg;
	private JTextField usuarioReg;
	private JTextField estadoReg;
	private JPasswordField passwordReg;
	private JTextField textFieldEmailReg;
	private JTextField txtRespSegReg;

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
		
		actionListenerActivada = null;
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

		btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent es) {
				
				
				if (cli.obtenerMsgErr() != null) {
					estado.setText(cli.obtenerMsgErr() + ". Salga del juego y reinicie el Servidor por favor!.");
					estado.setBackground(Color.RED);
				}else {
					
					if (email.getText().isEmpty() || password.getText().isEmpty() || email.getText().equals(" ")
							|| password.getText().equals(" ")) {
						estado.setText("Debe ingresar el email y contraseña para poder ingresar al editor.");
						estado.setBackground(Color.RED);
					} else {
							String emailUsuario  = email.getText();
							String nombreUsuario = obtenerNombreUsuario(emailUsuario);
							String pass = password.getText();
							PaqueteInicioSesion packInicioSesion = new PaqueteInicioSesion(emailUsuario,nombreUsuario, pass);
							cli.enviarMsg(new Msg("iniciarsesion", packInicioSesion));
							Msg msgARecibirDelSv = cli.recibirMsg();
							
							if (msgARecibirDelSv.getAccion().equals("OK")) {
								usr = new Usuario (nombreUsuario,emailUsuario);
								actionListenerActivada = "Ingresar";
								System.out.println("En pantalla: "+actionListenerActivada);
								estado.setText("Bienvenido "+nombreUsuario+"!!");
								estado.setBackground(Color.GREEN);
							}else {
								estado.setText("El nombre de usuario o contraseña no son correctas. Ingrese nuevamente!");
								estado.setBackground(Color.RED);
							}
					}					
					
				}

			}
		});
		btnIngresar.setBounds(281, 368, 89, 23);
		pantallaSesion.add(btnIngresar);

		btnRegistrar = new JButton("Registrarse");
		btnRegistrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRegistrar.setVisible(false);
				btnIngresar.setVisible(false);
				lblPass.setVisible(false);
				lblUsuario.setVisible(false);
				email.setVisible(false);
				password.setVisible(false);
				
				PantallaRegistracion();
			}
		});
		
		btnRegistrar.setBounds(421, 368, 112, 23);
		pantallaSesion.add(btnRegistrar);

		lblUsuario = new JLabel("Email");
		lblUsuario.setBounds(245, 280, 46, 14);
		pantallaSesion.add(lblUsuario);

		lblPass = new JLabel("Contrase\u00F1a");
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
	
		//TEMPORAL
		email.setText("nico");
		password.setText("123");
	}

	protected String obtenerNombreUsuario(String emailUsuario) {
		String name= null;
		cli.enviarMsg(new Msg ("nombreUsuario",emailUsuario));
		Msg msg = cli.recibirMsg();
		name = (String) msg.getObj();
		return name;
	}

	public Usuario getUsuario() {
		return usr;
	}
	
	public void PantallaRegistracion() {
		pantallaSesion.updateUI();
		nyaReg = new JTextField();
		nyaReg.setBounds(335, 196, 237, 20);
		pantallaSesion.add(nyaReg);
		nyaReg.setColumns(10);
		
		usuarioReg = new JTextField();
		usuarioReg.setBounds(335, 227, 237, 20);
		pantallaSesion.add(usuarioReg);
		usuarioReg.setColumns(10);

		JLabel lblNombreUsuarioReg = new JLabel("Nombre Usuario");
		lblNombreUsuarioReg.setBounds(198, 231, 117, 14);
		pantallaSesion.add(lblNombreUsuarioReg);
		
		JLabel lblContrasenaReg = new JLabel("Contrase\u00F1a");
		lblContrasenaReg.setBounds(198, 263, 117, 14);
		pantallaSesion.add(lblContrasenaReg);
		
		JLabel lblNombreYApellidoReg = new JLabel("Nombre y Apellido");
		lblNombreYApellidoReg.setBounds(198, 199, 117, 14);
		pantallaSesion.add(lblNombreYApellidoReg);
		
		estadoReg = new JTextField();
		estadoReg.setEditable(false);
		estadoReg.setBounds(0, 541, 784, 20);
		pantallaSesion.add(estadoReg);
		estadoReg.setColumns(10);
		
		passwordReg = new JPasswordField();
		passwordReg.setEchoChar('*');
		passwordReg.setBounds(335, 260, 237, 20);
		pantallaSesion.add(passwordReg);
		
		textFieldEmailReg = new JTextField();
		textFieldEmailReg.setColumns(10);
		textFieldEmailReg.setBounds(335, 165, 237, 20);
		pantallaSesion.add(textFieldEmailReg);
		
		JLabel lblEmailReg = new JLabel("Email");
		lblEmailReg.setBounds(198, 168, 117, 14);
		pantallaSesion.add(lblEmailReg);
		
		JLabel lblRespSegReg = new JLabel("Tu mascota favorita?");
		lblRespSegReg.setBounds(198, 291, 117, 14);
		pantallaSesion.add(lblRespSegReg);
		
		txtRespSegReg = new JTextField();
		txtRespSegReg.setColumns(10);
		txtRespSegReg.setBounds(335, 291, 237, 20);
		pantallaSesion.add(txtRespSegReg);
		JButton btnRegistrarseReg = new JButton("Registrarse");
		btnRegistrarseReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PaqueteRegistracion reg = new PaqueteRegistracion(nyaReg.getText(),usuarioReg.getText(),txtRespSegReg.getText(),passwordReg.getText(),textFieldEmailReg.getText());
			    Msg msgEnviarASv = new Msg ("registrar",reg);
			    cli.enviarMsg(msgEnviarASv); 
			    Msg msgRecibido = cli.recibirMsg();
			    
				if (msgRecibido.getAccion().equals("OK")) {
					estado.setText("Te haz registrado correctamente!");
					estado.setBackground(Color.GREEN);
				}else {
					estado.setText("El nombre de usuario Ya existe!");
					estado.setBackground(Color.RED);
			    }

			}
		});
		btnRegistrarseReg.setBounds(292, 346, 105, 23);
		pantallaSesion.add(btnRegistrarseReg);
		JButton btnVolverReg = new JButton("Volver");
		btnVolverReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNombreUsuarioReg.setVisible(false);
				lblNombreYApellidoReg.setVisible(false);
				lblRespSegReg.setVisible(false);
				txtRespSegReg.setVisible(false);
				lblEmailReg.setVisible(false);
				lblRespSegReg.setVisible(false);
				textFieldEmailReg.setVisible(false);
				passwordReg.setVisible(false);
				lblContrasenaReg.setVisible(false);
				estadoReg.setVisible(false);
				nyaReg.setVisible(false);
				usuarioReg.setVisible(false);
				btnVolverReg.setVisible(false);
				btnRegistrarseReg.setVisible(false);
				
				btnRegistrar.setVisible(true);
				btnIngresar.setVisible(true);
				lblPass.setVisible(true);
				lblUsuario.setVisible(true);
				email.setVisible(true);
				password.setVisible(true);
			}
		});
		btnVolverReg.setBounds(496, 346, 89, 23);
		pantallaSesion.add(btnVolverReg);
		

		
	}	
}
