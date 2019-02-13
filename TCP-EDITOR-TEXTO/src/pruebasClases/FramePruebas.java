package pruebasClases;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FramePruebas extends JFrame {

	private JPanel contentPane;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
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
		lblListaDeAmigos.setBounds(10, 129, 133, 14);
		contentPane.add(lblListaDeAmigos);
		
		JLabel arbolDocumental = new JLabel("Documentos");
		arbolDocumental.setBounds(672, 129, 102, 14);
		contentPane.add(arbolDocumental);
		
		JLabel lblEditor = new JLabel("Editor");
		lblEditor.setBounds(465, 129, 46, 14);
		contentPane.add(lblEditor);
		
		JButton btnEdicion = new JButton("Modo edici\u00F3n");
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
		btnCrearDoc.setBounds(363, 233, 148, 23);
		contentPane.add(btnCrearDoc);
		
		JList listaDocumentos = new JList();
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
		contentPane.add(eliminarArchivo);
		
		JLabel label = new JLabel("Lista de amigos");
		label.setBounds(205, 154, 133, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Lista de amigos");
		label_1.setBounds(205, 192, 133, 14);
		contentPane.add(label_1);
		

	}
}
