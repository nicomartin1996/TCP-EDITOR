package paqueteDeInterfacesGraficas;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import cajaDeHerramientas.Usuario;

public class PanelPrincipal extends JPanel{
	private int ancho;
	private int alto;
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private String usuarioLocal;
	private ArrayList<Usuario> listaUsuarios;

	public PanelPrincipal(int ancho, int alto, Usuario usu, String usuario) {
		listaUsuarios = new ArrayList<>();
		this.usuarioLocal = usuario;
		this.setAncho(ancho);
		this.setAlto(alto);
		this.usuario = usu;
		this.setPreferredSize(new Dimension(ancho, alto));
	}

	public void actualizar() {
		actualizarUsuariosConectados();
	}

	private void actualizarUsuariosConectados() {

	}

	public void dibujar() {
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}



}
