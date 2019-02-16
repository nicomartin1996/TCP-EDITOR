package paqueteDeInterfacesGraficas;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelConFondo extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2210923519223247665L;
	private ImageIcon fondo;
	private String ruta;
	
	/**
	 * @param rutaFondo
	 */
	public PanelConFondo(String rutaFondo) {
		super();
		ruta = rutaFondo;
		fondo = new ImageIcon(getClass().getResource(ruta));
	}
	
	protected void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(fondo.getImage(), 0, 0, d.width, d.height, null);
		this.setOpaque(false);
		super.paintComponent(g);	
	}

}
