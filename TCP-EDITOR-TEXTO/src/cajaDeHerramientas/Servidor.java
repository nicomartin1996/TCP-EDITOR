package cajaDeHerramientas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Servidor {

	private int PORT = 5000;
	private static ServerSocket serverSocket;
	private static ArrayList<Usuario> usuariosConectados = new ArrayList<>();
	private static ArrayList<Documento> documentosDeUsuarios = new ArrayList<>();
	/**
	 * @param args the command line arguments
	 */
	public Servidor(int puerto) {
		this.PORT = puerto;
	}

	public static void main(String[] args) {
		Servidor sv = new Servidor(5000);
		try {
			serverSocket = new ServerSocket(sv.obtenerPuerto());
			// Socket de cliente
			Socket clientSocket;
			levantarArchivos();
			while (true) {
				System.out.println("Servidor esperando clientes!");
				clientSocket = serverSocket.accept();
				System.out.println("conexion aceptada!");
				HiloCliente hiloCliente = new HiloCliente(clientSocket,usuariosConectados,documentosDeUsuarios);
				hiloCliente.start();

			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
	private static void levantarArchivos() {
		ConexionBDLite conexion = new ConexionBDLite("NicoBD.db", "engine", "configuracion1");
		Connection con = conexion.getConexion();
		ArrayList<Documento> documentos = new ArrayList<>();
		ResultSet res= null;
		try {			
			String sql = "SELECT * FROM archivos WHERE 1";
			
			res = (ResultSet) conexion.Consulta(sql, con);
			while (res.next()) {
				Documento doc = new Documento(res.getInt(1),res.getString(2), res.getString(3),  res.getString(7), res.getString(4),res.getString(5), res.getBytes(6));
				documentosDeUsuarios.add(doc);
			}
			
			res.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private int obtenerPuerto() {
		// TODO Auto-generated method stub
		return this.PORT;
	}

}
