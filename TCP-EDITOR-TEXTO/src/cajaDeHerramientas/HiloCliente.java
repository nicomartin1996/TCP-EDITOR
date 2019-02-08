package cajaDeHerramientas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HiloCliente extends Thread{

	private Socket cliente;
	private int idSesion;
	private static ArrayList<String> usuariosConectados = new ArrayList<>();
	private int i = 0;
//	private InputStream in;
//	private OutputStream out;
	private static boolean puedeActualizar;

	// En el constructor recibe y guarda los par�metros que sean necesarios.
	// En este caso una lista con toda la conversaci�n y el socket que debe
	// atender.
//	public HiloCliente(int idSesion, Socket cliente, ArrayList<Jugador> jugadoresConectados) {
	public HiloCliente(int idSesion, Socket cliente) {
		this.cliente = cliente;
		this.idSesion = idSesion;
		puedeActualizar = true;
//		try {
//			this.out = cliente.getOutputStream(); // Se debe cerrar
//			this.in = cliente.getInputStream();
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}
	}

	@Override
	public void run() {

		try {
			i++;
			ObjectInputStream reciboMsg ;
			ObjectOutputStream salidaACliente = null;
			while (true) {
				/* Recibo Consulta de cliente */
				System.out.println("Hola entre11 antes");
				reciboMsg = new ObjectInputStream(cliente.getInputStream());
//				if (reciboMsg != null) {
					System.out.println("Hola entre");
					Objeto msgRecibo =(Objeto)reciboMsg.readObject();
//					Objeto obj = (Objeto) msgRecibo;
					System.out.println(msgRecibo.getObj()+" - el obj");
//					System.out.println("Siendo Sv, Recibo parametro = " + c.getSolicitud() + ", " + c.getResultado());

//					String resultado = procesarConsulta("","");
//					if (resultado.equals("OK")) {
//
//						//Resultados
//
//					} else {
//
//						//Resultados
//					}
//					
					System.out.println(msgRecibo+" - el mensjae que recibi");

					/* Envio respuesta al Cliente */
					String obj1 = "OK";
					salidaACliente.writeObject(obj1); // Se debe cerrar
//					salidaACliente.flush();

//				}
			}

		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Problemas al querer leer otra petici�n: " + ex.getMessage());
		}

	}

	/**
	 * procesa peticion del cliente y retorna resultado
	 * 
	 * @return String
	 */

	public String procesarConsulta(String consulta, String info) {
		ConexionBD conexion = new ConexionBD();
		Connection con = ConexionBD.getConexion();
		String result = "NO_OK";
		String[] infoUnzip = info.split("-");
//		System.out.println("Consulta: " + consulta + "");

		if (consulta.equals("actualizarPosicion")) {

		}

		if (consulta.equals("registrar")) {

//			System.out.println("1: " + infoUnzip[0] + " 2:" + infoUnzip[1] + " Datos");
			if (existeCliente(infoUnzip[0], infoUnzip[1], conexion, con) == false) {
				result = "OK";
			}
		}

		if (consulta.equals("iniciarsesion")) {

			ResultSet res = null;
			Integer cantidad = 0;
			String sqlExiste = "SELECT count(*) as Cantidad FROM usuarios WHERE usuario = '" + infoUnzip[0]
					+ "' and pass = '" + infoUnzip[1] + "' ";
			try {
				res = (ResultSet) conexion.Consulta(sqlExiste, con);
				res.next();
				cantidad = res.getInt(1);
			} catch (SQLException e) {

				System.out.println(e);
				return result;
			}

			if (cantidad > 0) {
				result = "OK";

			} else {
				
			}

		}

		if (consulta.equals("actualizarUsuariosConectados")) {
			result = "OK";

		}

		if (consulta.equals("4")) {

		}
		if (consulta.equals("5")) {

		}

		return result;
	}

	public static boolean existeCliente(String usuario, String password, ConexionBD conexion, Connection con) {
		ResultSet res = null;
		Integer cantidad = 0;
		String sqlExiste = "SELECT count(*) as Cantidad FROM usuarios WHERE usuario = '" + usuario + "' ";
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
				sqlExiste = "INSERT INTO usuarios (usuario,pass) VALUES ('" + usuario + "','" + password + "')";
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