package pruebasClases;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import cajaDeHerramientas.Cliente;
import cajaDeHerramientas.Msg;
import cajaDeHerramientas.Usuario;
import junit.framework.Assert;
import paqueteDeDatos.PaqueteAgregarAmigo;
import paqueteDeDatos.PaqueteCrearDocumento;
import paqueteDeDatos.PaqueteDatosPersonalesAct;
import paqueteDeDatos.PaqueteEliminarAmigo;
import paqueteDeDatos.PaqueteEliminarArch;

class testFuncionalidadABM {
	Cliente cliente = new Cliente  ("localhost",5000);
	
	@Test
	void testAgregarAmigo() {
		PaqueteAgregarAmigo pckAmigo = new PaqueteAgregarAmigo("ALDANA", "Nico");
		cliente.enviarMsg(new Msg ("agregarAmigo",pckAmigo));
		Msg msg = cliente.recibirMsg();
		Assert.assertEquals("OK",msg.getAccion());
	}
	
	@Test
	void testCrearDoc() {
		Usuario usr = new Usuario ("123","nicolas","Nico");
		String contDeArchivo =" "; //Se crea el archivo vacio
		String nombreDoc ="prueba1";
		byte[] archivo = null;
		archivo = contDeArchivo.getBytes();
		Date laFechadeHoy = new Date();
		String fecha = new SimpleDateFormat("dd-MM-yyyy").format(laFechadeHoy);
		cliente.enviarMsg(new Msg("crearDocumento",new PaqueteCrearDocumento(usr.getEmail(),fecha,nombreDoc,archivo)));
		Msg mensajeRecibido = cliente.recibirMsg();
		Assert.assertEquals("OK",mensajeRecibido.getAccion());
	}
	
	@Test
	void testEliminarAmigo() {
		cliente.enviarMsg(new Msg("eliminarAmigo",new PaqueteEliminarAmigo("ALDANA", "Nico")));
		Msg msg = cliente.recibirMsg();
		Assert.assertEquals("OK",msg.getAccion());
	}
	
	@Test
	void testEliminarArch() {
		PaqueteEliminarArch pack = new PaqueteEliminarArch("Nico", 1);
		cliente.enviarMsg(new Msg ("eliminarArch",pack));
		Msg msg= cliente.recibirMsg();
		Assert.assertEquals("OK",msg.getAccion());
	}
	
	@Test 
	void testModDatosPers () {
		cliente.enviarMsg(new Msg("actualizarDatosPersonales", new PaqueteDatosPersonalesAct("prueba2",null,"123","Nico")));
		Msg msg = cliente.recibirMsg();
		Assert.assertEquals("OK",msg.getAccion());
	}

}
