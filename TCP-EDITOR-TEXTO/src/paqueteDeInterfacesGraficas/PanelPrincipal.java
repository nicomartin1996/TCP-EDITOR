package paqueteDeInterfacesGraficas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	private JButton eliminarArchivo;
	private JButton btnCrearDoc;
	private JButton btnEdicion;
	private JButton btnRefrescar;
	private JButton btnCompartir;
	
		
	public PanelPrincipal(int ancho, int alto, Cliente usu, Usuario usr) {
		listaUsuarios = new ArrayList<>();
		this.setAncho(ancho);
		this.setAlto(alto);
		this.cliente = usu;
		this.setPreferredSize(new Dimension(ancho, alto));
		this.usr = usr;
		this.setLayout(null);
		listAmigosConectados = new JList();
		listAmigosConectados.setBounds(10, 154, 185, 335);
		this.modeloListaDefault = new DefaultListModel<>();
		listAmigosConectados.setModel(modeloListaDefault);
		this.agregarComponente(listAmigosConectados);
		listarUsuariosConectados(); //Inicializo lista de amigos conectados
		initComponent();
	}
	
	private void listarUsuariosConectados() {

		cliente.enviarMsg(new Msg("listaUsuarios",new PaqueteInicioSesion(usr.getEmail(),usr.getUsu(),usr.getPass())));
		Msg mensajeDesdeSv = cliente.recibirMsg();
		if (mensajeDesdeSv.getAccion().equals("OK")) {
			listaUsuarios = (ArrayList<Usuario>) mensajeDesdeSv.getObj();
			Iterator<Usuario> it = listaUsuarios.iterator();
			while (it.hasNext()) {
				Usuario auxiliar = it.next();
				String email = auxiliar.getEmail();
				boolean estaConnectado = auxiliar.EstaConectado();
				String estado = ":Desconectado";
				if (estaConnectado) {
					estado = ":Conectado";
				}
				modeloListaDefault.addElement(email+" "+estado);			
			}
			
			if (modeloListaDefault.isEmpty()) {
				modeloListaDefault.addElement("Aún no tienes amigos");
			}
		}	
	}
	
	private void limpiarListaAmigos() {
		modeloListaDefault.clear();
	}
	public void actualizar() {
//		listarUsuariosConectados();
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
	
	public void crearBoton (String name, int x,int y,int w,int h) {
		JButton btn = new JButton(name);
		btn.setEnabled(true);
		btn.setPreferredSize(new Dimension(w, h));
	}
	public void agregarComponente(Component comp) {
		this.add(comp);
	}
	
	
	private void initComponent() {		
		btnRefrescar = new JButton("Refrescar Lista");
		btnRefrescar.setBounds(38, 495, 133, 23);
		this.add(btnRefrescar);
		
		JLabel lblListaDeAmigos = new JLabel("Lista de amigos");
		lblListaDeAmigos.setBounds(10, 129, 133, 14);
		this.add(lblListaDeAmigos);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(201, 154, 465, 335);
		this.add(textArea);
		
		JLabel arbolDocumental = new JLabel("Documentos");
		arbolDocumental.setBounds(672, 129, 102, 14);
		this.add(arbolDocumental);
		
		JLabel lblEditor = new JLabel("Editor");
		lblEditor.setBounds(465, 129, 46, 14);
		this.add(lblEditor);
		
		btnEdicion = new JButton("Modo edici\u00F3n");
		btnEdicion.setBounds(201, 495, 151, 23);
		this.add(btnEdicion);
		
		btnCompartir = new JButton("CompartirArchivo");
		btnCompartir.setBounds(379, 495, 148, 23);
		this.add(btnCompartir);
		
		btnCrearDoc = new JButton("Crear documento");
		btnCrearDoc.setBounds(546, 495, 162, 23);
		this.add(btnCrearDoc);
		
		JList listaDocumentos = new JList();
		listaDocumentos.setBounds(672, 154,100, 256);
		
		ScrollPane panelListaDoc = new ScrollPane();
		panelListaDoc.setBounds(672, 154, 100, 256);
		panelListaDoc.add(listaDocumentos);
		this.add(panelListaDoc);
		
		eliminarArchivo = new JButton("Eliminar Archivo");
		eliminarArchivo.setBounds(201, 529, 151, 23);
		this.add(eliminarArchivo);
		
		
		btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarListaAmigos();
				listarUsuariosConectados();
			}
		});
		
		btnCompartir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Usuario usuario = (Usuario) listAmigosConectados.getSelectedValue();
				String usuarioSeleccionado = (String) listAmigosConectados.getSelectedValue();
				String[] informacionSeparada = usuarioSeleccionado.split(":");
				if (!informacionSeparada[1].equals("Desconectado")) {
					//Tomo usuarios que esten conectados.
					Usuario usu = new Usuario ("Usr",informacionSeparada[1]);
					
				}
				System.out.println(informacionSeparada[0]+" "+informacionSeparada[1]);
			}
		});

	}
	
	
}
