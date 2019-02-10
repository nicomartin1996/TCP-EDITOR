package pruebasClases;

import javax.swing.JTextArea;

import org.junit.jupiter.api.Test;

import cajaDeHerramientas.Cliente;
import cajaDeHerramientas.Usuario;
import junit.framework.Assert;
import paqueteDeInterfacesGraficas.PanelPrincipal;
import paqueteDeInterfacesGraficas.PantallaEditor;

class TestPantallaEditor {

	@Test
	void testDibujarComponente() {
		PanelPrincipal j= new PanelPrincipal(800, 600, new Cliente("192.168.1.51", 5000),new Usuario ("email","drug"));
		PantallaEditor pantalla = new PantallaEditor("Nico", j, 800, 600);
		JTextArea cajaDeTexto = new JTextArea("Hola");
		cajaDeTexto.setVisible(true);
		j.agregarComponente(cajaDeTexto);
		String res = cajaDeTexto.getText();
		Assert.assertEquals("Hola", res);
	}

}
