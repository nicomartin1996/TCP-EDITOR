package paqueteDeInterfacesGraficas;

import java.awt.Color;
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
import paqueteDeDatos.PaqueteRegistracion;

public class PantallaRegistracionBck extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nya;
	private JTextField usuario;
	private JTextField estado;
	private JPasswordField password;
	Cliente cli ;
	private JTextField textFieldEmail;
	private JTextField txtRespSeg;
	
	public PantallaRegistracionBck() {

		cli = new Cliente("localhost",5000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nya = new JTextField();
		nya.setBounds(335, 196, 237, 20);
		contentPane.add(nya);
		nya.setColumns(10);
		
		usuario = new JTextField();
		usuario.setBounds(335, 227, 237, 20);
		contentPane.add(usuario);
		usuario.setColumns(10);

		JLabel lblNombreUsuario = new JLabel("Nombre Usuario");
		lblNombreUsuario.setBounds(198, 231, 117, 14);
		contentPane.add(lblNombreUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(198, 263, 117, 14);
		contentPane.add(lblContrasea);
		
		JLabel lblNombreYApellido = new JLabel("Nombre y Apellido");
		lblNombreYApellido.setBounds(198, 199, 117, 14);
		contentPane.add(lblNombreYApellido);
		
		estado = new JTextField();
		estado.setEditable(false);
		estado.setBounds(0, 541, 784, 20);
		contentPane.add(estado);
		estado.setColumns(10);
		
		password = new JPasswordField();
		password.setEchoChar('*');
		password.setBounds(335, 260, 237, 20);
		contentPane.add(password);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(335, 165, 237, 20);
		contentPane.add(textFieldEmail);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(198, 168, 117, 14);
		contentPane.add(lblEmail);
		
		JLabel lblRespSeg = new JLabel("Tu mascota favorita?");
		lblRespSeg.setBounds(198, 291, 117, 14);
		contentPane.add(lblRespSeg);
		
		txtRespSeg = new JTextField();
		txtRespSeg.setColumns(10);
		txtRespSeg.setBounds(335, 291, 237, 20);
		contentPane.add(txtRespSeg);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cli.enviarMsg(new Msg("Salir",null));
				Msg msgRecibido = cli.recibirMsg();
				if (msgRecibido.getAccion().equals("OK")) {
					System.out.println("ASDASD");
					PantallaSesion pantallaInicioSesion = new PantallaSesion();
					pantallaInicioSesion.setVisible(true);
					dispose();
				}
				System.out.println("SEQWEQWEQWEQWEQWEQWEQ");
			}
		});
		btnVolver.setBounds(496, 346, 89, 23);
		contentPane.add(btnVolver);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PaqueteRegistracion reg = new PaqueteRegistracion(nya.getText(),usuario.getText(),txtRespSeg.getText(),password.getText(),textFieldEmail.getText());
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
		btnRegistrarse.setBounds(292, 346, 105, 23);
		contentPane.add(btnRegistrarse);
		
	}
}
