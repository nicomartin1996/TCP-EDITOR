package paqueteDeInterfacesGraficas;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import cajaDeHerramientas.Usuario;

public class PantallaEditor extends JFrame {
	
	private int idEditorDisponible ;
	ArrayList <Usuario> usuarioConectado ;
	private static final long serialVersionUID = 1L;
	private int ancho;
	private int alto;

	public PantallaEditor(String nombre,PanelPrincipal JPanel, int ancho,int alto) {
		this.ancho = ancho;
		this.alto = alto;
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(nombre);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.add(JPanel);
		this.pack();
		this.setVisible(true);
	}
}
