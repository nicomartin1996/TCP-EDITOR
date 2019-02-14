package paqueteDeInterfacesGraficas;

import java.awt.Color;
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
import cajaDeHerramientas.Documento;
import cajaDeHerramientas.Msg;
import cajaDeHerramientas.Usuario;
import paqueteDeDatos.PaqueteCrearDocumento;
import paqueteDeDatos.PaqueteInicioSesion;

public class PanelPrincipal extends JPanel{
	private int ancho;
	private int alto;
	private static final long serialVersionUID = 1L;
	private Usuario usr;
	private ArrayList<Usuario> listaUsuarios;
	private ArrayList<Documento> listaDocumento;
	private JList listAmigosConectados;
	private DefaultListModel modeloListaDefault;
	private DefaultListModel modeloListaDefaultDocu;
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
	private boolean modoEdicion;
	private Documento docAEditar;
	private String docSeleccionado;
	private ArrayList<String> usuariosIntegrantesDoc;
	private String usuarioEdita;
	private ArrayList<String> fechasUltModUsuariosDoc;
	private JTextField txtFFechMod;
	private JTextField txtFIntegrantes;
	private JLabel lblFechaDeModificacion;
	private JLabel lblIntegrantes;
	private JTextField txtFEstadoDoc;
	
		
	public PanelPrincipal(int ancho, int alto, Cliente cliente, Usuario usr) {
		modoEdicion = false;
		usuariosIntegrantesDoc =null;
		usuarioEdita=null;
		fechasUltModUsuariosDoc=null;
		listaUsuarios = new ArrayList<>();
		this.setAncho(ancho);
		this.setAlto(alto);
		this.cliente = cliente;
		this.setPreferredSize(new Dimension(ancho, alto));
		this.usr = usr;
		this.setLayout(null);
		initComponent();
		listarUsuariosConectados(); //Inicializo lista de amigos conectados
		listarDocumentos();
		this.docSeleccionado = null;
	}
	
	private void listarUsuariosConectados() {
		System.out.println(cliente.obtenerSocketCliente().isClosed()+"-"+cliente.obtenerSocketCliente().getPort());
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
	
	private void limpiarListaDocumentos() {
		this.modeloListaDefaultDocu.clear();
	}
	
	public void actualizar() {
		
		if (modoEdicion) {
			
			String info = docSeleccionado+"-"+usr.getEmail();
			cliente.enviarMsg(new Msg ("edicionDoc",info));
			Msg mensajeRecibido = cliente.recibirMsg();
			actualizarDatos(); // Actualizar fecha, integrantes en tiempo real.
			if (mensajeRecibido.getAccion().equals("OK")) {
				docAEditar = (Documento) mensajeRecibido.getObj();
				String cadenaLegible = new String(docAEditar.getContenidoArchivo());
				textArea.setText(cadenaLegible);
				modoEdicion = false;
				habilitarEdicion();
			}else {
				System.out.println("El archivo esta siendo usado por otro usuario");
				
			}
			
		}
		
	}

	private void actualizarDatos() {
		usuariosIntegrantesDoc = listaIntegrantes();
		fechasUltModUsuariosDoc = fechaUltModificacion();
		usuarioEdita = obtenerUsuarioEdita();
		
		Iterator<String> it = usuariosIntegrantesDoc.iterator();
		String strr = "";
		while (it.hasNext()) {
			strr += it.next();
			strr += " | ";
		}
		txtFIntegrantes.setText(strr);
		strr="";
		it = null;
		it = fechasUltModUsuariosDoc.iterator();
		while (it.hasNext()) {
			strr += it.next();
			strr += " | ";
		}
			
		txtFFechMod.setText(strr);	
		strr ="";
		System.out.println(usuarioEdita+" "+usr.getEmail());
		if (usuarioEdita != null) {
			if (usuarioEdita.equals(usr.getEmail())) {
				strr = "Usted está editando este Documento...";
				txtFEstadoDoc.setText(usuarioEdita+" está editando el documento. Espere a que termine sus modificaciones ...");
//				txtFEstadoDoc.setBackground(Color.GREEN);
				txtFEstadoDoc.setForeground(Color.BLACK);

			}else {
				strr = usuarioEdita+" está editando el documento. Espere a que termine sus modificaciones ...";
				txtFEstadoDoc.setText(strr);
//				txtFEstadoDoc.setBackground(Color.YELLOW);
				txtFEstadoDoc.setForeground(Color.BLACK);
			}
		}
		
	}

	private ArrayList<String> listaIntegrantes(){
		
		cliente.enviarMsg(new Msg("listaIntegranteDoc",docSeleccionado));
		Msg msg = cliente.recibirMsg();
		if (msg.getAccion().equals("OK")) {
			return (ArrayList<String>) msg.getObj();
		}
		return null;
	}
	
	private ArrayList<String> fechaUltModificacion(){
		cliente.enviarMsg(new Msg("fecUltModUsuarios",docSeleccionado));
		Msg msg = cliente.recibirMsg();
		if (msg.getAccion().equals("OK")) {
			return (ArrayList<String>) msg.getObj();
		}
		return null;
	}
	
	private String obtenerUsuarioEdita() {
		cliente.enviarMsg(new Msg("usuarioEditaDoc",docSeleccionado));
		Msg msg = cliente.recibirMsg();
		if (msg.getAccion().equals("OK")) {
			return (String) msg.getObj();
		}
		return null;
	}
	
	private void habilitarEdicion() {
		textArea.setEnabled(true);
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
	
		lblIntegrantes = new JLabel("Integrantes");
		lblIntegrantes.setBounds(46, 22, 70, 14);
		this.add(lblIntegrantes);
		
		txtFIntegrantes = new JTextField();
		txtFIntegrantes.setBounds(205, 19, 300, 20);
		this.add(txtFIntegrantes);
		
		lblFechaDeModificacion = new JLabel("Fecha de modificacion");
		lblFechaDeModificacion.setBounds(46, 63, 149, 14);
		this.add(lblFechaDeModificacion);
		
		txtFFechMod = new JTextField();
		txtFFechMod.setColumns(10);
		txtFFechMod.setBounds(205, 60, 300, 20);
		this.add(txtFFechMod);
		
		txtFEstadoDoc = new JTextField();
		txtFEstadoDoc.setBounds(0, 541, 784, 20);
		this.add(txtFEstadoDoc);
		txtFEstadoDoc.setColumns(10);
		
		JLabel lblListaDeAmigos = new JLabel("Lista de amigos");
		lblListaDeAmigos.setBounds(10, 129, 133, 14);
		this.add(lblListaDeAmigos);
		
		textArea = new TextArea();
		textArea.setBounds(201, 154, 465, 335);
		this.add(textArea);
		
		JLabel arbolDocumental = new JLabel("Documentos");
		arbolDocumental.setBounds(672, 129, 102, 14);
		this.add(arbolDocumental); 
		
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
		this.modeloListaDefaultDocu = new DefaultListModel<>();
		
		ScrollPane panelListaDoc = new ScrollPane();
		panelListaDoc.setBounds(672, 154, 100, 256);
		listaDocumentos.setModel(modeloListaDefaultDocu);
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
				String nombreDoc = null;
				nombreDoc = JOptionPane.showInputDialog("Ingrese el nombre del nuevo documento:");
				if (nombreDoc != null) {
		
					String contDeArchivo =" "; //Se crea el archivo vacio
					byte[] archivo = null;
					archivo = contDeArchivo.getBytes();
					Calendar today = Calendar.getInstance();
					int mes = today.get(Calendar.MONTH);
					int anio = today.get(Calendar.YEAR);
					int dia = today.get(Calendar.DAY_OF_MONTH);
					String fecha = anio+"-"+mes+"-"+dia;
					cliente.enviarMsg(new Msg("crearDocumento",new PaqueteCrearDocumento(usr.getEmail(),fecha,nombreDoc,archivo)));
					Msg mensajeRecibido = cliente.recibirMsg();
					if (mensajeRecibido.getAccion().equals("OK")) {
						JOptionPane.showMessageDialog(null, "Documento Creado!", "Nombre Doc.", JOptionPane.INFORMATION_MESSAGE);
						limpiarListaDocumentos();
						listarDocumentos();
					}
				
				}
			}
		});
		
		
		btnCerrarDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cliente.enviarMsg(new Msg ("actualizarEstadoDoc",docSeleccionado));
				Msg msgRecibido = cliente.recibirMsg();
				if (msgRecibido.getAccion().equals("OK")) {
					docSeleccionado = null;
					inicializarMenuPrincipal();
				}
			}
		});
		
		btnEdicion.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				inicializarMenuEdicion();
				modoEdicion = true;
				
				String info = (String) listaDocumentos.getSelectedValue();
				String[] informacionSeparada = info.split("|");
				docSeleccionado = informacionSeparada[0];
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
				if (!modoEdicion) {
					btnCompartir.setEnabled(true);
				}
				
			}
		});
		
		inicializarMenuPrincipal();
	}

	private void listarDocumentos() {
		
		cliente.enviarMsg(new Msg("listarDoc",new PaqueteInicioSesion(usr.getEmail(),usr.getUsu(),usr.getPass())));
		Msg mensajeDesdeSv = cliente.recibirMsg();
		if (mensajeDesdeSv.getAccion().equals("OK")) {
			
			listaDocumento = (ArrayList<Documento>) mensajeDesdeSv.getObj();
			Iterator<Documento> it = listaDocumento.iterator();
			while (it.hasNext()) {
				Documento auxiliar = it.next();
				String nombre = auxiliar.getNombreArchivo();
				int codigo = auxiliar.getCodigo();
				modeloListaDefaultDocu.addElement(codigo+"|"+nombre);			
			}
			
			if (modeloListaDefaultDocu.isEmpty()) {
				modeloListaDefaultDocu.addElement("Aún no tienes documentos!");
			}
		}
	}

	private void inicializarMenuPrincipal () {
		btnCompartir.setVisible(false);
		textArea.setVisible(false);
		btnCerrarDoc.setVisible(false);
		lblIntegrantes.setVisible(false);
		lblFechaDeModificacion.setVisible(false);
		txtFFechMod.setVisible(false);
		txtFIntegrantes.setVisible(false);
		txtFEstadoDoc.setVisible(false);
		
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
		lblIntegrantes.setVisible(true);
		lblFechaDeModificacion.setVisible(true);
		txtFFechMod.setVisible(true);
		txtFIntegrantes.setVisible(true);
		txtFEstadoDoc.setVisible(true);
		
		btnCrearDoc.setVisible(false);
		btnEdicion.setVisible(false);
		btnModificarDatosPersonales.setVisible(false);
		btnEliminarArchivo.setVisible(false);
		
		txtFEstadoDoc.setEnabled(false);
		txtFFechMod.setEnabled(false);
		txtFIntegrantes.setEnabled(false);
		btnCompartir.setEnabled(false);
		textArea.setEnabled(false);
	}
	
	private void inicializarMenuDatosPersonales() {
		updateUI();
		btnCrearDoc.setVisible(false);
		btnEdicion.setVisible(false);
		btnModificarDatosPersonales.setVisible(false);
		btnEliminarArchivo.setVisible(false);
		txtFEstadoDoc.setVisible(false);
		btnCompartir.setVisible(false);
		textArea.setVisible(false);
		btnCerrarDoc.setVisible(false);
		lblIntegrantes.setVisible(false);
		lblFechaDeModificacion.setVisible(false);
		txtFFechMod.setVisible(false);
		txtFIntegrantes.setVisible(false);

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

	public boolean salir() {
		modoEdicion = false;
		cliente.enviarMsg(new Msg("Salir",usr.getEmail()));
		Msg msgRecibido = cliente.recibirMsg();
		if (msgRecibido.getAccion().equals("OK")) {
			return true;
		}
		return false;
	}

}
