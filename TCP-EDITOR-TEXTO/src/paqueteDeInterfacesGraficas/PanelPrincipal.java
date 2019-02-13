package paqueteDeInterfacesGraficas;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cajaDeHerramientas.Cliente;
import cajaDeHerramientas.Msg;
import cajaDeHerramientas.Usuario;
import paqueteDeDatos.PaqueteCrearDocumento;
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
	private JButton btnEliminarArchivo;
	private JButton btnCrearDoc;
	private JButton btnEdicion;
	private JButton btnRefrescar;
	private JButton btnCompartir;
	private JButton btnCerrarDoc;
	private JButton btnModificarDatosPersonales;
	private TextArea textArea;
	private JList listaDocumentos;
	
		
	public PanelPrincipal(int ancho, int alto, Cliente usu, Usuario usr) {
		listaUsuarios = new ArrayList<>();
		this.setAncho(ancho);
		this.setAlto(alto);
		this.cliente = usu;
		this.setPreferredSize(new Dimension(ancho, alto));
		this.usr = usr;
		this.setLayout(null);
		initComponent();
		listarUsuariosConectados(); //Inicializo lista de amigos conectados
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
	
	private void initComponent() {		
		
		listAmigosConectados = new JList();
		listAmigosConectados.setBounds(10, 154, 160, 335);
		this.modeloListaDefault = new DefaultListModel<>();
		listAmigosConectados.setModel(modeloListaDefault);
		this.add(listAmigosConectados);
		
		JLabel lblListaDeAmigos = new JLabel("Lista de amigos");
		lblListaDeAmigos.setBounds(10, 129, 133, 14);
		this.add(lblListaDeAmigos);
		
		textArea = new TextArea();
		textArea.setBounds(201, 154, 465, 335);
		this.add(textArea);
		
		JLabel arbolDocumental = new JLabel("Documentos");
		arbolDocumental.setBounds(672, 129, 102, 14);
		this.add(arbolDocumental);
//		
//		JLabel lblEditor = new JLabel("Editor");
//		lblEditor.setBounds(465, 129, 46, 14);
//		this.add(lblEditor);
		
		btnEdicion = new JButton("Modo edici\u00F3n");
		btnEdicion.setBounds(300, 154, 270, 23);
		this.add(btnEdicion);
		
		btnCrearDoc = new JButton("Crear documento");
		btnCrearDoc.setBounds(300, 154+23+8, 270, 23);
		this.add(btnCrearDoc);
		
		btnEliminarArchivo = new JButton("Eliminar Documento");
		btnEliminarArchivo.setBounds(300,154+(23+8)*2, 270, 23);
		this.add(btnEliminarArchivo);
		
		btnModificarDatosPersonales = new JButton("Modificar Datos Personales");
		btnModificarDatosPersonales.setBounds(300, 154+(23+8)*3, 270, 23);
		this.add(btnModificarDatosPersonales);
		
		btnRefrescar = new JButton("Refrescar Lista");
		btnRefrescar.setBounds(38, 495, 133, 23);
		this.add(btnRefrescar);
		
		btnCompartir = new JButton("Compartir Doc.");
		btnCompartir.setBounds(38+133+50, 495, 148, 23);
		this.add(btnCompartir);
	
		btnCerrarDoc = new JButton("Cerrar Documento");
		btnCerrarDoc.setBounds(38+133+133+70, 495, 151, 23);
		this.add(btnCerrarDoc);
		
	    listaDocumentos = new JList();
		listaDocumentos.setBounds(672, 154,100, 256);
		
		ScrollPane panelListaDoc = new ScrollPane();
		panelListaDoc.setBounds(672, 154, 100, 256);
		panelListaDoc.add(listaDocumentos);
		this.add(panelListaDoc);
		
		btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarListaAmigos();
				listarUsuariosConectados();
			}
		});
		
		btnCrearDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreDoc = JOptionPane.showInputDialog("Ingrese el nombre del nuevo documento:");
				String contDeArchivo =" "; //Se crea el archivo vacio
				byte[] archivo = new byte[(int) contDeArchivo.length()];
				Calendar today = Calendar.getInstance();
				int mes = today.get(Calendar.MONTH);
				int anio = today.get(Calendar.YEAR);
				int dia = today.get(Calendar.DAY_OF_MONTH);
				String fecha = anio+"-"+mes+"-"+dia;
				cliente.enviarMsg(new Msg("crearDocumento",new PaqueteCrearDocumento(usr.getEmail(),fecha,nombreDoc,archivo)));
				Msg mensajeRecibido = cliente.recibirMsg();
				if (mensajeRecibido.getAccion().equals("OK")) {
					JOptionPane.showMessageDialog(null, "Documento Creado!", "Nombre Doc.", JOptionPane.INFORMATION_MESSAGE);
					listarDocumentos();
				}		
			}
		});
		
		
		btnCerrarDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicializarMenuPrincipal();
			}
		});
		
		btnEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicializarMenuEdicion();
				
			}
		});
		
		btnModificarDatosPersonales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicializarMenuDatosPersonales();
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
		
		listaDocumentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnEliminarArchivo.setEnabled(true);
				btnEdicion.setEnabled(true);
			}
		});
		
		listAmigosConectados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnCompartir.setEnabled(true);
			}
		});
		
		inicializarMenuPrincipal();
	}
	
	private void listarDocumentos() {
		cliente.enviarMsg(new Msg("listaDocumentos",new PaqueteInicioSesion(usr.getEmail(),usr.getUsu(),usr.getPass())));
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

	private void inicializarMenuPrincipal () {
		btnCompartir.setVisible(false);
		textArea.setVisible(false);
		btnCerrarDoc.setVisible(false);
		
		btnCrearDoc.setVisible(true);
		btnEdicion.setVisible(true);
		btnModificarDatosPersonales.setVisible(true);
		btnEliminarArchivo.setVisible(true);
		
		btnEdicion.setEnabled(false);
		btnEliminarArchivo.setEnabled(false);
		
	}
	
	private void inicializarMenuEdicion () {
		btnCompartir.setVisible(true);
		textArea.setVisible(true);
		btnCerrarDoc.setVisible(true);
		
		btnCrearDoc.setVisible(false);
		btnEdicion.setVisible(false);
		btnModificarDatosPersonales.setVisible(false);
		btnEliminarArchivo.setVisible(false);
		
		btnCompartir.setEnabled(false);	
	}
	
	
	private void inicializarMenuDatosPersonales() {
		updateUI();
		btnCrearDoc.setVisible(false);
		btnEdicion.setVisible(false);
		btnModificarDatosPersonales.setVisible(false);
		btnEliminarArchivo.setVisible(false);
		btnCompartir.setVisible(false);
		textArea.setVisible(false);
		btnCerrarDoc.setVisible(false);
		
		

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(200, 154, 133, 14);
		this.add(lblUsuario);
		
		JLabel lblPassword = new JLabel("Contraseña");
		lblPassword.setBounds(200, 154+23+8, 133, 14);
		this.add(lblPassword);
		
		JLabel lblPreguntaSeg = new JLabel("Tu mascota favorita?");
		lblPreguntaSeg.setBounds(200,154+(23+8)*2, 133, 14);
		this.add(lblPreguntaSeg);
		
		JTextField usuarioMod = new JTextField();
		usuarioMod.setBounds(380, 154, 150, 23);
		this.add(usuarioMod);
		
		JTextField passMod = new JTextField();
		passMod.setBounds(380, 154+23+8, 150, 23);
		this.add(passMod);
		
		JTextField preguntaSeg = new JTextField();;
		preguntaSeg.setBounds(380,154+(23+8)*2, 150, 23);
		this.add(preguntaSeg);
		
		JButton enviarModificaciones = new JButton("Enviar");
		enviarModificaciones.setBounds(300, 154+(23+8)*3, 200, 23);
		this.add(enviarModificaciones);	
		
		enviarModificaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Tengo que enviar los nuevos datos para actualizar la tabla usuarios
				
				enviarModificaciones.setVisible(false);
				preguntaSeg.setVisible(false);
				passMod.setVisible(false);
				usuarioMod.setVisible(false);
				lblPreguntaSeg.setVisible(false);
				lblPassword.setVisible(false);
				lblUsuario.setVisible(false);
				inicializarMenuPrincipal();
				
			}
		});
		
	}
	
	
}
