package pruebasClases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class FramePruebas extends JFrame {

	private JPanel contentPane;
	private JTextField txtFIntegrantes;
	private JTextField txtFFechMod;
	private JTextField txtFEstadoDoc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FramePruebas frame = new FramePruebas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FramePruebas() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePruebas.class.getResource("/imagenes/icono.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane);
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(800,600));
		
		JList list = new JList();
		list.setBounds(10, 154, 185, 335);
		
		JScrollPane panelListaScroll = new JScrollPane();
		panelListaScroll.setBounds(10, 154, 185, 335);
		panelListaScroll.add(list);
		contentPane.add(panelListaScroll);
		
		JButton btnRefrescar = new JButton("Refrescar Lista");
		btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRefrescar.setBounds(38, 495, 133, 23);
		contentPane.add(btnRefrescar);
		
		JLabel lblListaDeAmigos = new JLabel("Lista de amigos");
		lblListaDeAmigos.setFont(new Font("Palatino Linotype", Font.PLAIN, 15));
		lblListaDeAmigos.setBounds(205, 229, 116, 38);
//		ImageIcon iconoEmail = new ImageIcon(FramePruebas.class.getResource("/imagenes/emailSesion.jpg"));
//		Icon icono = new ImageIcon(iconoEmail.getImage().getScaledInstance(lblListaDeAmigos.getWidth(), lblListaDeAmigos.getHeight(), Image.SCALE_DEFAULT));
//		lblListaDeAmigos.setIcon(icono);

		contentPane.add(lblListaDeAmigos);
		this.repaint();
		JLabel arbolDocumental = new JLabel("Documentos");
		arbolDocumental.setBounds(672, 129, 102, 14);
		contentPane.add(arbolDocumental);
		
		JLabel lblEditor = new JLabel("Editor");
		lblEditor.setForeground(Color.BLUE);
		lblEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		lblEditor.setBounds(465, 129, 46, 14);
		lblEditor.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(lblEditor);
		
		JButton btnEdicion = new JButton("Modo edici\u00F3n");
		btnEdicion.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		btnEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEdicion.setBounds(363, 154, 151, 23);
		contentPane.add(btnEdicion);
		
		JButton btnCompartir = new JButton("CompartirArchivo");
		btnCompartir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCompartir.setBounds(363, 188, 148, 23);
		contentPane.add(btnCompartir);
		
		JButton btnCrearDoc = new JButton("Crear documento");
		btnCrearDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCrearDoc.setSelectedIcon(new ImageIcon(FramePruebas.class.getResource("/imagenes/candadoSesion.jpg")));
		btnCrearDoc.setBounds(363, 233, 148, 23);
		contentPane.add(btnCrearDoc);
		
		JList listaDocumentos = new JList();
		listaDocumentos.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listaDocumentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		listaDocumentos.setBounds(672, 154,100, 256);
		
		ScrollPane panelListaDoc = new ScrollPane();
		panelListaDoc.setBounds(672, 154, 100, 256);
		panelListaDoc.add(listaDocumentos);
		contentPane.add(panelListaDoc);
		
		JButton eliminarArchivo = new JButton("Eliminar Archivo");
		eliminarArchivo.setBounds(363, 279, 148, 23);
		eliminarArchivo.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(eliminarArchivo);
		
		JLabel label = new JLabel("Lista de amigos");
		label.setBounds(205, 154, 133, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Lista de amigos");
		label_1.setBounds(205, 192, 133, 14);
		contentPane.add(label_1);
		
		JLabel lblIntegrantes = new JLabel("Integrantes");
		lblIntegrantes.setBounds(46, 22, 70, 14);
		contentPane.add(lblIntegrantes);
		
		txtFIntegrantes = new JTextField();
		txtFIntegrantes.setBounds(205, 19, 86, 20);
		contentPane.add(txtFIntegrantes);
		txtFIntegrantes.setColumns(10);
		
		JLabel lblFechaDeModificacion = new JLabel("Fecha de modificacion");
		lblFechaDeModificacion.setBounds(46, 63, 149, 14);
		contentPane.add(lblFechaDeModificacion);
		
		txtFFechMod = new JTextField();
		txtFFechMod.setColumns(10);
		txtFFechMod.setBounds(205, 60, 86, 20);
		contentPane.add(txtFFechMod);
		
		txtFEstadoDoc = new JTextField();
		txtFEstadoDoc.setBounds(0, 541, 784, 20);
		contentPane.add(txtFEstadoDoc);
		txtFEstadoDoc.setColumns(10);
		

	}
}
