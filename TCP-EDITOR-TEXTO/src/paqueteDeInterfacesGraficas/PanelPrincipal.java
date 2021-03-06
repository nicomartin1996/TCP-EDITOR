package paqueteDeInterfacesGraficas;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cajaDeHerramientas.Cliente;
import cajaDeHerramientas.Documento;
import cajaDeHerramientas.Msg;
import cajaDeHerramientas.Usuario;
import paqueteDeDatos.PaqueteAgregarAmigo;
import paqueteDeDatos.PaqueteCompartirArch;
import paqueteDeDatos.PaqueteCrearDocumento;
import paqueteDeDatos.PaqueteDatosPersonalesAct;
import paqueteDeDatos.PaqueteEliminarAmigo;
import paqueteDeDatos.PaqueteEliminarArch;
import paqueteDeDatos.PaqueteGuardarDocumento;
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
	private JButton btnAgregarAmigo;
	private boolean controlEliminacionArch;
	private JButton btnRefrescarDoc;
	private JButton btnEliminarAmigo;
	private ImageIcon fondo;
	private Choice listaTam;
	private Choice listaFuente;
	private Choice listaEstilo;
	private String[] fuentes;
		
	public PanelPrincipal(int ancho, int alto, Cliente cliente, Usuario usr,String rutaImagen) {
		fuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fondo = new ImageIcon(getClass().getResource(rutaImagen));
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
		controlEliminacionArch = false;
	}
	
	//Metodo para colocar un fondo en el JPanel
	protected void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(fondo.getImage(), 0, 0, d.width, d.height, null);
		this.setOpaque(false);
		super.paintComponent(g);	
	}
	
	//////// METODOS PARA CONTROL DE LISTAS /////////////////////
	private void listarUsuariosConectados() {
//		System.out.println(cliente.obtenerSocketCliente().isClosed()+"-"+cliente.obtenerSocketCliente().getPort());
		cliente.enviarMsg(new Msg("listaUsuarios",new PaqueteInicioSesion(usr.getEmail(),usr.getUsu(),usr.getPass())));
		Msg mensajeDesdeSv = cliente.recibirMsg();
		if (mensajeDesdeSv.getAccion().equals("OK")) {
			listaUsuarios = (ArrayList<Usuario>) mensajeDesdeSv.getObj();
			Iterator<Usuario> it = listaUsuarios.iterator();
			while (it.hasNext()) {
				Usuario auxiliar = it.next();
				String email = auxiliar.getEmail();
				boolean estaConnectado = auxiliar.EstaConectado();
				String estado = ": Desconectado";
				if (estaConnectado) {
					estado = ": Conectado";
				}
				modeloListaDefault.addElement(email+""+estado);			
			}
			
			if (modeloListaDefault.isEmpty()) {
				modeloListaDefault.addElement("A�n no tienes amigos");
			}
		}	
	}
	
	private void limpiarListaAmigos() {
		modeloListaDefault.clear();
	}
	
	private void limpiarListaDocumentos() {
		this.modeloListaDefaultDocu.clear();
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
				modeloListaDefaultDocu.addElement(codigo+"-"+nombre);			
			}
			
			if (modeloListaDefaultDocu.isEmpty()) {
				modeloListaDefaultDocu.addElement("A�n no tienes documentos!");
			}
		}
	}
	
	/////////////// FIN /////////////////////////////////////////
	
	//////////// METODO PARA FUNCIONALIDAD EDICION ///////////////
	public void actualizar() {
		controlarExistenciaArch();
		
		if (modoEdicion) {
			
			String info = docSeleccionado+"-"+usr.getEmail();
			cliente.enviarMsg(new Msg ("existeArch",docSeleccionado));
			Msg msg = cliente.recibirMsg();
			if (msg.getAccion().equals("OK")) {
				//Archivo existe
				//Si el archivo no fue eliminado por el propietario. puedo editar
				cliente.enviarMsg(new Msg ("edicionDoc",info));
				Msg mensajeRecibido = cliente.recibirMsg();
				actualizarDatos(); // Actualizar fecha, integrantes en tiempo real.
				if (mensajeRecibido.getAccion().equals("OK")) {
					docAEditar = (Documento) mensajeRecibido.getObj();
					String cadenaLegible = new String(docAEditar.getContenidoArchivo());
					textArea.setText(cadenaLegible);
					modoEdicion = false;
					habilitarEdicion();
					btnCerrarDoc.setEnabled(true);
					listaEstilo.setEnabled(true);
					listaFuente.setEnabled(true);
					listaTam.setEnabled(true);
					if (!consultarUsrCreadorArch().equals(usr.getEmail())) {
						//No soy el creador del archivo seleccionado. Controlo la eliminacion del mismo
						controlEliminacionArch = true;
						System.out.println("No es mi archivo");
					}	
				}else {
					listaEstilo.setEnabled(false);
					listaFuente.setEnabled(false);
					listaTam.setEnabled(false);
					btnCerrarDoc.setEnabled(false);
				}
			}else {
				modoEdicion = false;
				JOptionPane.showMessageDialog(null,"El documento seleccionado no existe!", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
				inicializarMenuPrincipal();
				limpiarListaDocumentos();
				listarDocumentos();
			}
		}	
	}

	////////////// FIN //////////////////////////////////////////
	

	private synchronized void controlarExistenciaArch() {
		if (controlEliminacionArch) {
			//Controlo si el archivo que estoy editando si es eliminado por el propietario (El documento es compartido)
			cliente.enviarMsg(new Msg ("existeArch",docSeleccionado));
			Msg msg = cliente.recibirMsg();
			if (!msg.getAccion().equals("OK")) {
				inicializarMenuPrincipal();
				controlEliminacionArch = false;
				JOptionPane.showMessageDialog(null, "El documento que est� editando, ha sido eliminado por el Propietario", "Advertencia", JOptionPane.CLOSED_OPTION);
				limpiarListaDocumentos();
				listarDocumentos();
			}
		}	
	}

	/**
	 * METODOS PARA OBTENCION DE DATOS INFORMATIVOS DE DOCUMENTOS.
	 * Datos:
	 * Integrantes
	 * Ult fecha mod.
	 * Usr Creador Doc
	 * Usuario que esta editando en tiempo real
	 * */
	private String consultarUsrCreadorArch() {
		cliente.enviarMsg(new Msg ("creadorArch",docSeleccionado));
		Msg msg = cliente.recibirMsg();
		if (msg.getAccion().equals("OK")) {
			return (String) msg.getObj();
		}
		return null;
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
		txtFIntegrantes.setBackground(Color.lightGray);
		strr="";
		it = null;
		it = fechasUltModUsuariosDoc.iterator();
		while (it.hasNext()) {
			String fecAux = it.next();
			if (fecAux != null) {
				strr += fecAux;
			}else {
				strr += "No registra Modificaciones";
			}
			strr += " | ";
		}
			
		txtFFechMod.setText(strr);
		txtFFechMod.setBackground(Color.lightGray);
		strr ="";
//		System.out.println(usuarioEdita+" "+usr.getEmail());
		if (usuarioEdita != null) {
			Color col = Color.GREEN;
			if (usuarioEdita.equals(usr.getEmail())) {
				strr = "Usted est� editando este Documento...";
			}else {
				col = Color.YELLOW;
				strr = usuarioEdita+" est� editando el documento. Espere a que termine sus modificaciones ...";
			}
			
			txtFEstadoDoc.setText(strr);
			txtFEstadoDoc.setBackground(col);
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

	/**
	 * FIN
	 * */
	
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
	
	/**
	 * Metodo que controla toda la funcionalidad de
	 *  Los botones de: 
	 *  menu principal
	 *  menu edicion
     *  datos personales
     *  Definicion de componentes e inicializaci�n
     *  */
	private void initComponent() {	
		
		listaTam = new Choice();
		listaTam.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Font f = textArea.getFont();
				textArea.setFont(new Font (f.getFontName(),f.getStyle(),Integer.parseInt(String.valueOf(listaTam.getSelectedItem()))));
			
			}
		});
		listaTam.setBounds(288, 120, 60, 20);
		this.add(listaTam);
		
		listaFuente = new Choice();
		listaFuente.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Font f = textArea.getFont();
				Font nuevoF = new Font (listaFuente.getSelectedItem(),f.getStyle(),f.getSize());
//				System.out.println(listaFuente.getSelectedItem() +"-"+f.getFontName()+ " - nuevo Font: "+nuevoF.getName());
				textArea.setFont(nuevoF);
//				textArea.setFont(new Font (listaFuente.getSelectedItem(),f.getStyle(),f.getSize()));
				
			}
		});
		listaFuente.setBounds(157, 120, 60, 20);
		this.add(listaFuente);
		
		listaEstilo = new Choice();
		listaEstilo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				int estilo  = Font.PLAIN;
				if (listaEstilo.getSelectedItem().equals("Normal")) {
					estilo = Font.PLAIN;
					System.out.println("normal: "+estilo);
				}
				if (listaEstilo.getSelectedItem().equals("Negrita")) {
					estilo = Font.BOLD;
					System.out.println("negrita: "+estilo);
				}
				if (listaEstilo.getSelectedItem().equals("Cursiva")) {
					
					estilo = Font.ITALIC;
					
					System.out.println("cursiva: "+estilo);
				}
				
				Font f = textArea.getFont();
				textArea.setFont(new Font (f.getFontName(),estilo,f.getSize()));
			
			}
		});
		listaEstilo.setBounds(381, 120, 60, 20);
		this.add(listaEstilo);
		
		cargarListasEdicion();
		
		listAmigosConectados = new JList();
		listAmigosConectados.setBounds(10, 154, 160, 335);
		this.modeloListaDefault = new DefaultListModel<>();
		listAmigosConectados.setModel(modeloListaDefault);
//		this.add(listAmigosConectados);
		
		ScrollPane panelListaAmigo = new ScrollPane();
		panelListaAmigo.setBounds(10, 154, 160, 335);
		panelListaAmigo.add(listAmigosConectados);
		this.add(panelListaAmigo);
	
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
		txtFEstadoDoc.setBounds(0, 541, 800, 20);
		this.add(txtFEstadoDoc);
		txtFEstadoDoc.setColumns(10);
		
		JLabel lblListaDeAmigos = new JLabel("Lista de amigos");
		lblListaDeAmigos.setBounds(10, 129, 133, 14);
		this.add(lblListaDeAmigos);
		
		textArea = new TextArea();
		textArea.setBounds(175, 154, 465, 335);
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
		
		btnAgregarAmigo = new JButton("Agregar un amigo");
		btnAgregarAmigo.setBounds(300, 154+(23+8)*4, 270, 23);
		this.add(btnAgregarAmigo);
		
		btnEliminarAmigo = new JButton("Eliminar amigo");
		btnEliminarAmigo.setBounds(300, 154+(23+8)*5, 270, 23);
		this.add(btnEliminarAmigo);
		
		btnRefrescar = new JButton("Refrescar Lista");
		btnRefrescar.setBounds(23, 495, 133, 23);
		this.add(btnRefrescar);
		
		btnRefrescarDoc = new JButton("Refrescar Lista Doc.");
		btnRefrescarDoc.setBounds(645, 495, 150, 23);
		this.add(btnRefrescarDoc);
		
		btnCompartir = new JButton("Compartir Doc.");
		btnCompartir.setBounds(38+133+50, 495, 148, 23);
		this.add(btnCompartir);
	
		btnCerrarDoc = new JButton("Cerrar Documento");
		btnCerrarDoc.setBounds(38+133+133+70, 495, 151, 23);
		this.add(btnCerrarDoc);
		
	    listaDocumentos = new JList();
		listaDocumentos.setBounds(672-25, 154,145, 335);
		this.modeloListaDefaultDocu = new DefaultListModel<>();
		
		ScrollPane panelListaDoc = new ScrollPane();
		panelListaDoc.setBounds(672-25, 154, 145, 335);
		listaDocumentos.setModel(modeloListaDefaultDocu);
		panelListaDoc.add(listaDocumentos);
		this.add(panelListaDoc);

		btnAgregarAmigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String emailAmigo = null;
				emailAmigo = JOptionPane.showInputDialog("Ingrese el email de amigo:");
				PaqueteAgregarAmigo pckAmigo = new PaqueteAgregarAmigo(emailAmigo, usr.getEmail());
				if (emailAmigo != null) {
					cliente.enviarMsg(new Msg ("agregarAmigo",pckAmigo));
					Msg msg = cliente.recibirMsg();
					if (msg.getAccion().equals("OK")) {
						JOptionPane.showMessageDialog(null, "Amigo agregado!", "Agregar Amigos", JOptionPane.INFORMATION_MESSAGE);
						limpiarListaAmigos();
						listarUsuariosConectados();
					}else {
						JOptionPane.showMessageDialog(null, "el Usuario ingresado no existe en el Sistema", "Agregar Amigos", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnRefrescar.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				limpiarListaAmigos();
				listarUsuariosConectados();
			}
		});
		
		btnRefrescarDoc.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				limpiarListaDocumentos();
				listarDocumentos();
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
					Date laFechadeHoy = new Date();
					String fecha = new SimpleDateFormat("dd-MM-yyyy").format(laFechadeHoy);
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
			public synchronized void actionPerformed(ActionEvent e) {
				
				Date laFechadeHoy = new Date();
				String fecha = new SimpleDateFormat("dd-MM-yyyy").format(laFechadeHoy);
//				System.out.println(fecha);
				byte[] archMod = textArea.getText().getBytes();
				controlEliminacionArch = false;
				cliente.enviarMsg(new Msg ("guardarDocumento",new PaqueteGuardarDocumento(fecha, usr.getEmail(), archMod, Integer.parseInt(docSeleccionado))));
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
				String[] informacionSeparada = info.split("-");
				docSeleccionado = informacionSeparada[0];
			}
		});
		
		btnEliminarAmigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String info = (String) listAmigosConectados.getSelectedValue();
				String[] informacionSeparada = info.split(":");
				String usrAEliminar = informacionSeparada[0];
				
				int resultado = JOptionPane.showConfirmDialog(null, "Estas seguro que desea eliminar a "+usrAEliminar+"");
				if (resultado == JOptionPane.YES_OPTION) {
					cliente.enviarMsg(new Msg("eliminarAmigo",new PaqueteEliminarAmigo(usrAEliminar, usr.getEmail())));
					Msg msg = cliente.recibirMsg();
					if (msg.getAccion().equals("OK")) {
						limpiarListaAmigos();
						listarUsuariosConectados();
						JOptionPane.showMessageDialog(null, "Has eliminado a "+usrAEliminar+"", "Eliminar amigos", JOptionPane.INFORMATION_MESSAGE);
						btnEliminarAmigo.setEnabled(false);
					}
				}else {
//					System.out.println("no ok");
				}

			}
		});
		
		btnModificarDatosPersonales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicializarMenuDatosPersonales();	
			}
		});
		
		btnCompartir.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				String valorSel = null;
				String valorDoc = null;
				valorSel = (String) listAmigosConectados.getSelectedValue();
				valorDoc = (String) listaDocumentos.getSelectedValue();
				
				if (valorSel != null && valorDoc != null) {
					String[] informacionSeparada = valorSel.split(":");
					String emailAmigo = informacionSeparada[0];
					
					String [] infoDoc = valorDoc.split("-");
					int codArch = Integer.parseInt(infoDoc[0]);
					cliente.enviarMsg(new Msg ("compartirArch",new PaqueteCompartirArch(emailAmigo, codArch)));
					Msg msg = cliente.recibirMsg();
					if (msg.getAccion().equals("OK")) {
						JOptionPane.showMessageDialog(null, "Has compartido el Documento "+infoDoc[1]+" con "+emailAmigo+"!", "Operaci�n Satisfactoria", JOptionPane.INFORMATION_MESSAGE);
						actualizarDatos();
					}else {
						JOptionPane.showMessageDialog(null, "Se super� la cantidad de usuarios participantes!", "Advertencia", JOptionPane.WARNING_MESSAGE);
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Recordar que para compartir documentos,tiene que estar seleccionado el amigo y el documento!.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		
		btnEliminarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valorDoc = null;
				valorDoc = (String) listaDocumentos.getSelectedValue();
				
				if ( valorDoc != null) {
					String [] infoDoc = valorDoc.split("-");
					int codArch = Integer.parseInt(infoDoc[0]);
					PaqueteEliminarArch pack = new PaqueteEliminarArch(usr.getEmail(), codArch);
//					System.out.println("Codigo: "+codArch);
					cliente.enviarMsg(new Msg ("eliminarArch",pack));
					Msg msg= cliente.recibirMsg();
					if (msg.getAccion().equals("OK")) {
						JOptionPane.showMessageDialog(null, "Documento eliminado!", "Completado", JOptionPane.INFORMATION_MESSAGE);
						limpiarListaDocumentos();
						listarDocumentos();
						btnEliminarArchivo.setEnabled(false);
					}else {
						JOptionPane.showMessageDialog(null, "No eres el creador del archivo seleccionado. Operaci�n no concretada", "Advertencia", JOptionPane.NO_OPTION);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar el archivo para eliminarlo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		
		listaDocumentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listaDocumentos.getSelectedValue() != null) {
					if (!((String) listaDocumentos.getSelectedValue()).equals("A�n no tienes documentos!")) {
						btnEliminarArchivo.setEnabled(true);
						btnEdicion.setEnabled(true);	
					}	
				}else {
					btnEliminarArchivo.setEnabled(false);
					btnEdicion.setEnabled(false);
				}
			}
		});
		
		listAmigosConectados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!modoEdicion && listAmigosConectados.getSelectedValue() != null ) {
					if (!((String) listAmigosConectados.getSelectedValue()).equals("A�n no tienes amigos") ) {
						btnCompartir.setEnabled(true);
						btnEliminarAmigo.setEnabled(true);
					}
				}else {
					btnCompartir.setEnabled(false);
					btnEliminarAmigo.setEnabled(false);
				}
			}
		});
		
		inicializarMenuPrincipal();
	}

	private void cargarListasEdicion() {
		
		for (int i = 10; i <= 30; i++) {
			listaTam.addItem(String.valueOf(i));
		}
		
		for (String nombreFuente : fuentes) {
			listaFuente.addItem(nombreFuente);
		}
		
		listaEstilo.addItem("Normal");
		listaEstilo.addItem("Negrita");
		listaEstilo.addItem("Cursiva");
	}

	/**
	 * FIN
	 * */
//////METODOS PARA MOSTRAR DIFERENTES TIPO DE MEN�///////	
	private void inicializarMenuPrincipal () {
		listaEstilo.setVisible(false);
		listaFuente.setVisible(false);
		listaTam.setVisible(false);
		btnCompartir.setVisible(false);
		textArea.setVisible(false);
		btnCerrarDoc.setVisible(false);
		lblIntegrantes.setVisible(false);
		lblFechaDeModificacion.setVisible(false);
		txtFFechMod.setVisible(false);
		txtFIntegrantes.setVisible(false);
		txtFEstadoDoc.setVisible(false);
		
		btnEliminarAmigo.setVisible(true);
		btnAgregarAmigo.setVisible(true);
		btnCrearDoc.setVisible(true);
		btnEdicion.setVisible(true);
		btnModificarDatosPersonales.setVisible(true);
		btnEliminarArchivo.setVisible(true);
		
		btnEdicion.setEnabled(false);
		btnEliminarArchivo.setEnabled(false);
		btnEliminarAmigo.setEnabled(false);
	}
	
	private void inicializarMenuEdicion () {
		updateUI();
		listaEstilo.setVisible(true);
		listaFuente.setVisible(true);
		listaTam.setVisible(true);
		btnCompartir.setVisible(true);
		textArea.setVisible(true);
		btnCerrarDoc.setVisible(true);
		lblIntegrantes.setVisible(true);
		lblFechaDeModificacion.setVisible(true);
		txtFFechMod.setVisible(true);
		txtFIntegrantes.setVisible(true);
		txtFEstadoDoc.setVisible(true);
		
		btnEliminarAmigo.setVisible(false);
		btnAgregarAmigo.setVisible(false);
		btnCrearDoc.setVisible(false);
		btnEdicion.setVisible(false);
		btnModificarDatosPersonales.setVisible(false);
		btnEliminarArchivo.setVisible(false);
		
		txtFEstadoDoc.setEditable(false);
		txtFFechMod.setEditable(false);
		txtFIntegrantes.setEditable(false);
		btnCompartir.setEnabled(false);
		textArea.setEnabled(false);
	}
	
	private void inicializarMenuDatosPersonales() {
		updateUI();
		listaEstilo.setVisible(false);
		listaFuente.setVisible(false);
		listaTam.setVisible(false);
		btnAgregarAmigo.setVisible(false);
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
		btnEliminarAmigo.setVisible(false);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(200, 154, 133, 14);
		this.add(lblUsuario);
		
		JLabel lblPassword = new JLabel("Contrase�a");
		lblPassword.setBounds(200, 154+23+8, 133, 14);
		this.add(lblPassword);
		
		JLabel lblPreguntaSeg = new JLabel("Tu mascota favorita?");
		lblPreguntaSeg.setBounds(200,154+(23+8)*2, 133, 14);
		this.add(lblPreguntaSeg);
		
		JTextField usuarioMod = new JTextField();
		usuarioMod.setBounds(380, 154, 150, 23);
		
		this.add(usuarioMod);
		
		JPasswordField passMod = new JPasswordField();
		passMod.setBounds(380, 154+23+8, 150, 23);
		passMod.setEchoChar('*');
		this.add(passMod);
		
		JTextField preguntaSeg = new JTextField();;
		preguntaSeg.setBounds(380,154+(23+8)*2, 150, 23);
		this.add(preguntaSeg);
		
		JButton enviarModificaciones = new JButton("Enviar");
		enviarModificaciones.setBounds(300, 154+(23+8)*3, 200, 23);
		this.add(enviarModificaciones);	
		
		JButton atras = new JButton("Atras");
		atras.setBounds(300, 154+(23+8)*4, 200, 23);
		this.add(atras);
		atras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarModificaciones.setVisible(false);
				preguntaSeg.setVisible(false);
				passMod.setVisible(false);
				usuarioMod.setVisible(false);
				lblPreguntaSeg.setVisible(false);
				lblPassword.setVisible(false);
				lblUsuario.setVisible(false);
				atras.setVisible(false);
				inicializarMenuPrincipal();
			}
		});	
		enviarModificaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Tengo que enviar los nuevos datos para actualizar la tabla usuarios
				cliente.enviarMsg(new Msg("actualizarDatosPersonales", new PaqueteDatosPersonalesAct(usuarioMod.getText(),passMod.getText(),preguntaSeg.getText(),usr.getEmail())));
				Msg msg = cliente.recibirMsg();
				if (msg.getAccion().equals("OK")) {
					JOptionPane.showMessageDialog(null, "Se han modificado los datos ingresados correctamente!","Modificaci�n de datos", JOptionPane.INFORMATION_MESSAGE);
					enviarModificaciones.setVisible(false);
					preguntaSeg.setVisible(false);
					passMod.setVisible(false);
					usuarioMod.setVisible(false);
					lblPreguntaSeg.setVisible(false);
					lblPassword.setVisible(false);
					lblUsuario.setVisible(false);
					atras.setVisible(false);
					inicializarMenuPrincipal();
				}else {
					JOptionPane.showMessageDialog(null, "La respuesta de seguridad es incorrecta!.","Modificaci�n de datos", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}

///////////////// FIN /////////////////////////////////////	
	
	public synchronized boolean salir() {
		modoEdicion = false;
		cliente.enviarMsg(new Msg("Salir",usr.getEmail()));
		Msg msgRecibido = cliente.recibirMsg();
		if (msgRecibido.getAccion().equals("OK")) {
			return true;
		}
		return false;
	}

}
