package paqueteDeInterfacesGraficas;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import cajaDeHerramientas.Cliente;
import cajaDeHerramientas.Msg;
import cajaDeHerramientas.Usuario;
import paqueteDeDatos.PaqueteInicioSesion;

public class PanelPrincipal extends JPanel{
	private int ancho;
	private int alto;
	private static final long serialVersionUID = 1L;
	private Usuario usr;
	private String usuarioLocal;
	private ArrayList<Usuario> listaUsuarios;
	private JList listAmigosConectados;
	private DefaultListModel modeloListaDefault;
	private Cliente cliente;
	
	public PanelPrincipal(int ancho, int alto, Cliente usu, Usuario usr) {
		listaUsuarios = new ArrayList<>();
		this.setAncho(ancho);
		this.setAlto(alto);
		this.cliente = usu;
		this.setPreferredSize(new Dimension(ancho, alto));
		this.usr = usr;
		listAmigosConectados = new JList();
		listAmigosConectados.setBounds(10, 11, 133, 239);
		this.modeloListaDefault = new DefaultListModel<>();
		listAmigosConectados.setModel(modeloListaDefault);
		this.agregarComponente(listAmigosConectados);
	}
	
	private void listarUsuariosConectados() {

		cliente.enviarMsg(new Msg("listaUsuarios",new PaqueteInicioSesion(usr.getEmail(),usr.getUsu(),usr.getPass())));
		Msg mensajeDesdeSv = cliente.recibirMsg();
		if (mensajeDesdeSv.getAccion().equals("OK")) {
			listaUsuarios = (ArrayList<Usuario>) mensajeDesdeSv.getObj();
			Iterator<Usuario> it = listaUsuarios.iterator();
			while (it.hasNext()) {	
				modeloListaDefault.addElement(it.next());			
			}
		}	
	}

	public void actualizar() {
		listarUsuariosConectados();
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
	
	public void agregarComponente(Component comp) {
		this.add(comp);
	}
}
