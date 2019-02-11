package cajaDeHerramientas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import paqueteDeDatos.PaqueteInicioSesion;

public class HiloCliente extends Thread{

	private Socket cliente;
	private int idSesion;
	private static ArrayList<Usuario> usuariosConectados = new ArrayList<>();

	// En el constructor recibe y guarda los parámetros que sean necesarios.
	// En este caso una lista con toda la conversación y el socket que debe
	// atender.
	public HiloCliente(int idSesion, Socket cliente,ArrayList<Usuario> usuariosConectados) {
		this.cliente = cliente;
		this.idSesion = idSesion;
		HiloCliente.usuariosConectados = usuariosConectados;
	}

	@Override
	public void run() {

		try {
			ObjectInputStream reciboMsg ;
			ObjectOutputStream salidaACliente = null;
			while (true) {
				
				/* Recibo Consulta de cliente */
			    reciboMsg = new ObjectInputStream(cliente.getInputStream());
				Msg msgRecibo =(Msg)reciboMsg.readObject();
				
				PaqueteInicioSesion packInicio = (PaqueteInicioSesion)msgRecibo.getObj();
				System.out.println("El mensaje: "+msgRecibo.getAccion()+" objeto: "+packInicio.getUsuario());
				Msg resultado = procesarConsulta(msgRecibo);

				/* Envio respuesta al Cliente */
				salidaACliente = new ObjectOutputStream(cliente.getOutputStream());
				salidaACliente.writeObject(resultado); // Se debe cerrar

			}

		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Problemas al querer leer otra petición: " + ex.getMessage());
		}

	}

	/**
	 * procesa peticion del cliente y retorna resultado
	 * 
	 * @return String
	 */

	public Msg procesarConsulta(Msg msg) {
		ConexionBD conexion = new ConexionBD();
		Connection con = ConexionBD.getConexion();
		Msg result = new Msg("NO_OK", null);
		
//		String info =  (String)msg.getObj();
		String consulta = msg.getAccion();
		
//		String[] infoUnzip = info.split("-");
		PaqueteInicioSesion userQuePidePeticion = (PaqueteInicioSesion)msg.getObj();
		String[] infoUnzip = new String[3];
		infoUnzip[0] = userQuePidePeticion.getEmail();
		infoUnzip[1] = userQuePidePeticion.getPass();
		infoUnzip[2] = userQuePidePeticion.getUsuario();
		if (consulta.equals("actualizarPosicion")) {

		}
		if (consulta.equals("listaUsuarios")) {
			
			ArrayList<String> emails = obtenerAmigos(infoUnzip[0], conexion, con);
			Iterator<String> it = emails.iterator();
			ArrayList<Usuario> usuariosAmigos = new ArrayList<>();
			while (it.hasNext()) {
				Iterator<Usuario> it2 = usuariosConectados.iterator();
				String emailAmigo = it.next();
				boolean estaConectado = false;
				while(it2.hasNext()) {
					
					Usuario usuarioAux = it2.next();
					if (usuarioAux.getEmail().equals(emailAmigo)) {
						estaConectado = true;	
					}
				}
				usuariosAmigos.add(new Usuario(infoUnzip[2],emailAmigo,estaConectado));
	
			}
			result = new Msg("OK", usuariosAmigos);

		}

		if (consulta.equals("registrar")) {

//			System.out.println("1: " + infoUnzip[0] + " 2:" + infoUnzip[1] + " Datos");
			if (existeCliente(infoUnzip[0], infoUnzip[1], conexion, con) == false) {
				result = new Msg("OK", null);
			}
		}

		if (consulta.equals("iniciarsesion")) {
//			PaqueteInicioSesion userQuePidePeticion = (PaqueteInicioSesion)msg.getObj();
//			String[] infoUnzip = new String[3];
//			infoUnzip[0] = userQuePidePeticion.getEmail();
//			infoUnzip[1] = userQuePidePeticion.getPass();
//			infoUnzip[2] = userQuePidePeticion.getUsuario();

			ResultSet res = null;
			Integer cantidad = 0;
//			String sqlExiste = "SELECT count(*) as Cantidad FROM usuarios WHERE usuario = '" + infoUnzip[2]
//					+ "' and pass = '" + infoUnzip[1] + "' and email = '"+infoUnzip[0]+"'";
			String sqlExiste = "SELECT count(*) as Cantidad FROM usuarios WHERE email = '" + infoUnzip[0]
					+ "' and pass = '" + infoUnzip[1] + "'";
			try {
				res = (ResultSet) conexion.Consulta(sqlExiste, con);
				res.next();
				cantidad = res.getInt(1);
			} catch (SQLException e) {

				System.out.println(e);
				return result;
			}

			if (cantidad > 0) {
				usuariosConectados.add(new Usuario(infoUnzip[2],infoUnzip[0]));
				result = new Msg("OK", null);

			} else {
				
			}

		}

		if (consulta.equals("actualizarUsuariosConectados")) {
			result = new Msg("OK", null);

		}

		if (consulta.equals("4")) {

		}
		if (consulta.equals("5")) {

		}

		return result;
	}
	
	public static ArrayList<String> obtenerAmigos(String email, ConexionBD conexion, Connection con) {
		ResultSet res = null;
		ArrayList<String> emailAmigos = null;
		String sqlAmigos = "SELECT usuarioAmigo FROM amigos WHERE usuario = '" +email+ "'";
		try {
			res = (ResultSet) conexion.Consulta(sqlAmigos, con);
			emailAmigos = new ArrayList<>();
			while (res.next()) {
				emailAmigos.add(res.getString(1));	
			}	
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return emailAmigos;
	}
	
	public static boolean existeCliente(String email, String password, ConexionBD conexion, Connection con) {
		ResultSet res = null;
		Integer cantidad = 0;
		String sqlExiste = "SELECT count(*) as Cantidad FROM usuarios WHERE email = '" + email + "' ";
		try {
			res = (ResultSet) conexion.Consulta(sqlExiste, con);
			res.next();
			cantidad = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (cantidad > 0) {
			return true;
		} else {
			if (cantidad <= 0) {
				sqlExiste = "INSERT INTO usuarios (email,pass) VALUES ('" + email + "','" + password + "')";
				System.out.println("sql insert:" + sqlExiste);
				try {
					if (((boolean) conexion.Consulta(sqlExiste, con)) == false) {
						System.out.println("se inserto correctamente");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
