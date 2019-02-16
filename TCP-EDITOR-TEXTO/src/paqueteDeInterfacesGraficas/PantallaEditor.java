package paqueteDeInterfacesGraficas;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cajaDeHerramientas.Usuario;
import pruebasClases.FramePruebas;

public class PantallaEditor extends JFrame {
	
	private int idEditorDisponible ;
	ArrayList <Usuario> usuarioConectado ;
	private static final long serialVersionUID = 1L;
	private int ancho;
	private int alto;
	private PanelPrincipal panel;

	public PantallaEditor(String nombre,PanelPrincipal JPanel, int ancho,int alto) {
		this.ancho = ancho;
		this.alto = alto;
		this.panel = JPanel;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle(nombre);
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(FramePruebas.class.getResource("/imagenes/icono.jpg")));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				confirmarSalir();
			}
		});
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	protected void confirmarSalir() {
		int salir = JOptionPane.showConfirmDialog(this, "¿Seguro desea Salir?","Advertencia",JOptionPane.YES_NO_OPTION);
		if (salir == JOptionPane.YES_OPTION) {
			if (panel.salir()) {
				System.exit(0);	
			}else {
				JOptionPane.showConfirmDialog(this, "Debe cerrar el documento antes de salir!.","Advertencia",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
