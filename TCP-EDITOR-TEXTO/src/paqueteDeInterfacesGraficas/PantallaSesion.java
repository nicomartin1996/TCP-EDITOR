package paqueteDeInterfacesGraficas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
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
import paqueteDeDatos.PaqueteRecuperarContrasena;
import paqueteDeDatos.PaqueteRegistracion;
import pruebasClases.FramePruebas;

public class PantallaSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pantallaSesion;
	private JTextField email;
	private JPasswordField password;
	private JTextField estado;
	private static Cliente cli = new Cliente("localhost", 5000);
	public static String actionListenerActivada;
	private Usuario usr;
	private JLabel lblUsuario;
	private JLabel lblPass;
	private JButton btnRegistrar;
	private JButton btnIngresar;
	private JTextField nyaReg;
	private JTextField usuarioReg;
	private JPasswordField passwordReg;
	private JTextField textFieldEmailReg;
	private JTextField txtRespSegReg;
	private JTextField txtRespSegRec;
	private JPasswordField passwordRec;
	private JButton btnVolverRec;
	private JButton btnEnviarRec;
	private JLabel lblContrasenaRec;
	private JLabel lblRespSegRec;
	private ImageIcon fondo;
	private JLabel lblRe;

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
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePruebas.class.getResource("/imagenes/icono.jpg")));
		actionListenerActivada = null;
		setTitle("Iniciar sesión");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		pantallaSesion = new PanelConFondo("/imagenes/fondo2.jpg");
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
							String emailUsuario = "";
							emailUsuario  = email.getText();
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
		btnIngresar.setBounds(259, 368, 112, 23);
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
				lblRe.setVisible(false);
				
				PantallaRegistracion();
			}
		});
		
		btnRegistrar.setBounds(398, 368, 112, 23);
		pantallaSesion.add(btnRegistrar);

		lblUsuario = new JLabel("Email");
		lblUsuario.setBounds(245, 280, 46, 14);
		lblUsuario.setForeground(Color.BLACK);
		pantallaSesion.add(lblUsuario);
		
		
		lblPass = new JLabel("Contrase\u00F1a");
		lblPass.setBounds(245, 311, 76, 14);
		lblPass.setForeground(Color.BLACK);
		pantallaSesion.add(lblPass);
		
		password = new JPasswordField();
		password.setBounds(320, 308, 164, 20);
		pantallaSesion.add(password);
		
		lblRe = new JLabel ("Recuperar contraseña");
		lblRe.setBounds(320,  330, 164, 20);
		lblRe.setForeground(Color.BLUE);
		pantallaSesion.add(lblRe);
		
		lblRe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pantallaRecuContrasena();
			}
		});
		
		
		estado = new JTextField();
		estado.setEditable(false);
		estado.setBounds(0, 551, 794, 20);
		pantallaSesion.add(estado);
		estado.setColumns(10);
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
	
	public void pantallaRecuContrasena() {
		pantallaSesion.updateUI();
		email.setVisible(false);
		password.setVisible(false);
		btnIngresar.setVisible(false);
		lblRe.setVisible(false);
		btnRegistrar.setVisible(false);
		lblUsuario.setVisible(false);
		lblPass.setVisible(false);
		
		lblContrasenaRec = new JLabel("Ingrese la nueva clave");
		lblContrasenaRec.setBounds(198-50, 263+30, 130, 14);
		pantallaSesion.add(lblContrasenaRec);
		passwordRec = new JPasswordField();
		passwordRec.setEchoChar('*');
		passwordRec.setBounds(335-30, 260+30, 237, 20);
		pantallaSesion.add(passwordRec);
		
		lblRespSegRec = new JLabel("Tu mascota favorita?");
		lblRespSegRec.setBounds(198-50, 291+30, 130, 14);
		pantallaSesion.add(lblRespSegRec);
		txtRespSegRec = new JTextField();
		txtRespSegRec.setColumns(10);
		txtRespSegRec.setBounds(335-30, 291+30, 237, 20);
		pantallaSesion.add(txtRespSegRec);
		
		btnEnviarRec = new JButton("Enviar");
		btnEnviarRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PaqueteRecuperarContrasena pck = new PaqueteRecuperarContrasena(email.getText(),passwordRec.getText(),txtRespSegRec.getText());
				cli.enviarMsg(new Msg("recuperarContraseña",pck));
			    Msg msgRecibido = cli.recibirMsg();  
				if (msgRecibido.getAccion().equals("OK")) {
					estado.setText("La contraseña ha sido modificada correctamente!");
					estado.setBackground(Color.GREEN);
				}else {
					estado.setText("El email o la respuesta no fueron correctas!");
					estado.setBackground(Color.RED);
			    }


			}
		});
		btnEnviarRec.setBounds(292-30, 346+30, 105, 23);
		pantallaSesion.add(btnEnviarRec);
		btnVolverRec = new JButton("Volver");
		btnVolverRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pantallaSesion.updateUI();
				btnVolverRec.setVisible(false);
				btnEnviarRec.setVisible(false);
				lblContrasenaRec.setVisible(false);
				passwordRec.setVisible(false);
				lblRespSegRec.setVisible(false);
				txtRespSegRec.setVisible(false);
				
				email.setVisible(true);
				password.setVisible(true);
				btnIngresar.setVisible(true);
				lblRe.setVisible(true);
				btnRegistrar.setVisible(true);
				lblUsuario.setVisible(true);
				lblPass.setVisible(true);
			}
		});
		btnVolverRec.setBounds(496-30, 346+30, 89, 23);
		pantallaSesion.add(btnVolverRec);
		
		
	}
	
	protected void volver() {
		btnVolverRec.setVisible(false);
		btnEnviarRec.setVisible(false);
		lblContrasenaRec.setVisible(false);
		lblRespSegRec.setVisible(false);
		txtRespSegRec.setVisible(false);

		btnRegistrar.setVisible(true);
		btnIngresar.setVisible(true);
		lblPass.setVisible(true);
		lblUsuario.setVisible(true);
		email.setVisible(true);
		password.setVisible(true);
	}

	public void PantallaRegistracion() {
		pantallaSesion.updateUI();
		nyaReg = new JTextField();
		nyaReg.setBounds(335-30, 196+30, 237, 20);
		pantallaSesion.add(nyaReg);
		nyaReg.setColumns(10);
		
		usuarioReg = new JTextField();
		usuarioReg.setBounds(335-30, 227+30, 237, 20);
		pantallaSesion.add(usuarioReg);
		usuarioReg.setColumns(10);

		JLabel lblNombreUsuarioReg = new JLabel("Nombre Usuario");
		lblNombreUsuarioReg.setBounds(198-30, 231+30, 117, 14);
		pantallaSesion.add(lblNombreUsuarioReg);
		
		JLabel lblContrasenaReg = new JLabel("Contrase\u00F1a");
		lblContrasenaReg.setBounds(198-30, 263+30, 117, 14);
		pantallaSesion.add(lblContrasenaReg);
		
		JLabel lblNombreYApellidoReg = new JLabel("Nombre y Apellido");
		lblNombreYApellidoReg.setBounds(198-30, 199+30, 117, 14);
		pantallaSesion.add(lblNombreYApellidoReg);
		
		passwordReg = new JPasswordField();
		passwordReg.setEchoChar('*');
		passwordReg.setBounds(335-30, 260+30, 237, 20);
		pantallaSesion.add(passwordReg);
		
		textFieldEmailReg = new JTextField();
		textFieldEmailReg.setColumns(10);
		textFieldEmailReg.setBounds(335-30, 165+30, 237, 20);
		pantallaSesion.add(textFieldEmailReg);
		
		JLabel lblEmailReg = new JLabel("Email");
		lblEmailReg.setBounds(198-30, 168+30, 117, 14);
		pantallaSesion.add(lblEmailReg);
		
		JLabel lblRespSegReg = new JLabel("Tu mascota favorita?");
		lblRespSegReg.setBounds(198-30, 291+30, 125, 14);
		pantallaSesion.add(lblRespSegReg);
		
		txtRespSegReg = new JTextField();
		txtRespSegReg.setColumns(10);
		txtRespSegReg.setBounds(335-30, 291+30, 237, 20);
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
					estado.setText("El email de usuario ya existe!");
					estado.setBackground(Color.RED);
			    }

			}
		});
		btnRegistrarseReg.setBounds(292-30, 346+30, 105, 23);
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
//				estadoReg.setVisible(false);
				nyaReg.setVisible(false);
				usuarioReg.setVisible(false);
				btnVolverReg.setVisible(false);
				btnRegistrarseReg.setVisible(false);
				
				
				btnRegistrar.setVisible(true);
				btnIngresar.setVisible(true);
				lblRe.setVisible(true);
				lblPass.setVisible(true);
				lblUsuario.setVisible(true);
				email.setVisible(true);
				password.setVisible(true);
			}
		});
		btnVolverReg.setBounds(496-30, 346+30, 89, 23);
		pantallaSesion.add(btnVolverReg);
		

		
	}	
}
