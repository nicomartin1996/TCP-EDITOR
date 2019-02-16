package pruebasClases;

import org.junit.jupiter.api.Test;

import cajaDeHerramientas.Cliente;
import cajaDeHerramientas.Msg;
import junit.framework.Assert;

class TestClienteServidor {
	Cliente usu = new Cliente("localhost", 5000);
	@Test
	public void testComClienteSv() {
		
		String obj = "Estoy enviando mi peticion";
		usu.enviarMsg(new Msg("enviandoprueba", obj));
		Msg msg = usu.recibirMsg();
		String objEnString = (String) msg.getObj();
		Assert.assertEquals("OK", objEnString);
	}

}
