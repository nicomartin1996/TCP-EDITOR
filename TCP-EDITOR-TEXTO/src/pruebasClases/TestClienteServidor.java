package pruebasClases;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import cajaDeHerramientas.Objeto;
import cajaDeHerramientas.Usuario;

class TestClienteServidor {

	@Test
	public void testComClienteSv() {
		Usuario usu = new Usuario ("192.168.1.51", 5000); //Creo el usuario - Cliente
		String obj = "Estoy enviando mi peticion";
		Objeto objAEnviar = new Objeto(obj);
		usu.enviarMsg(objAEnviar);
		System.out.println("Pase la barrear");
		Objeto objFeedback= usu.recibirMsg();
		String objEnString = (String) objFeedback.getObj();
		assertEquals("OK", objEnString);
	}

}
