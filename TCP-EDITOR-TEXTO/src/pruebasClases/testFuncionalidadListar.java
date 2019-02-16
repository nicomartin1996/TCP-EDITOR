package pruebasClases;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import cajaDeHerramientas.Cliente;
import cajaDeHerramientas.Documento;
import cajaDeHerramientas.Msg;
import cajaDeHerramientas.Usuario;
import junit.framework.Assert;
import paqueteDeDatos.PaqueteInicioSesion;

class testFuncionalidadListar {
	Cliente cliente = new Cliente ("localhost",5000);
	@Test
	void testlistaDoc() {
		Usuario usr = new Usuario ("123","nicolas","Nico");
		cliente.enviarMsg(new Msg("listarDoc",new PaqueteInicioSesion(usr.getEmail(),usr.getUsu(),usr.getPass())));
		Msg msg = cliente.recibirMsg();
		ArrayList<Documento> listd = (ArrayList<Documento>) msg.getObj();
		Assert.assertEquals(1,listd.get(0).getCodigo() );
		Assert.assertEquals(2, listd.get(1).getCodigo());
	}
	
	@Test
	void testlistaUsr() {
		Usuario usr = new Usuario ("123","nicolas","Nico");
		cliente.enviarMsg(new Msg("listaUsuarios",new PaqueteInicioSesion(usr.getEmail(),usr.getUsu(),usr.getPass())));
		Msg msg = cliente.recibirMsg();
		ArrayList<Usuario> listd = (ArrayList<Usuario>) msg.getObj();
		Assert.assertEquals("Ariel",listd.get(1).getEmail() );
	}

}
