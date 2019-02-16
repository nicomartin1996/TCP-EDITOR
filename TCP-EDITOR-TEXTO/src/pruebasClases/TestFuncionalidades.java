package pruebasClases;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import cajaDeHerramientas.Cliente;
import cajaDeHerramientas.Msg;
import cajaDeHerramientas.Usuario;
import junit.framework.Assert;
import paqueteDeDatos.PaqueteCompartirArch;
import paqueteDeDatos.PaqueteGuardarDocumento;

@SuppressWarnings("deprecation")
class TestFuncionalidades {
	Cliente cliente = new Cliente("localhost",5000);
	
	@Test
	void testExistenciaArch() {
		cliente.enviarMsg(new Msg ("existeArch","1"));
		Msg msg = cliente.recibirMsg();
		Assert.assertEquals("OK", msg.getAccion());
	}

	@Test
	void testCerrarDoc() {
		Usuario usr = new Usuario ("123","nicolas","Nico");
		Date laFechadeHoy = new Date();
		String fecha = new SimpleDateFormat("dd-MM-yyyy").format(laFechadeHoy);
		System.out.println(fecha);
		String arch = "HOLAAA";
		byte[] archMod = arch.getBytes();
		String docSeleccionado = "1";
		cliente.enviarMsg(new Msg ("guardarDocumento",new PaqueteGuardarDocumento(fecha, usr.getEmail(), archMod, Integer.parseInt(docSeleccionado))));
		Msg msgRecibido = cliente.recibirMsg();
		Assert.assertEquals("OK",msgRecibido.getAccion());
	}
	
	@Test
	void testModoEdicion() {
		String info = "1-Nico";
		Usuario usr = new Usuario ("123","nicolas","Nico");
		cliente.enviarMsg(new Msg ("edicionDoc",info));
		Msg msgRecibido = cliente.recibirMsg();
		Assert.assertEquals("OK",msgRecibido.getAccion());
	}

	@Test
	void testCompartir() {
		cliente.enviarMsg(new Msg ("compartirArch",new PaqueteCompartirArch("Ariel", 1)));
		Msg msg = cliente.recibirMsg();
		Assert.assertEquals("OK",msg.getAccion());
	}
		
}
