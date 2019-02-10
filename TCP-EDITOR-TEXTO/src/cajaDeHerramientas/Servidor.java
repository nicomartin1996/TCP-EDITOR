package cajaDeHerramientas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

	private int PORT = 5000;
	private static ServerSocket serverSocket;
	private static ArrayList<Usuario> usuariosConectados = new ArrayList<>();

	/**
	 * @param args the command line arguments
	 */
	public Servidor(int puerto) {
		this.PORT = puerto;
	}

	public static void main(String[] args) {
		Servidor sv = new Servidor(5000);
		int idSesion = 0;
		try {
			serverSocket = new ServerSocket(sv.obtenerPuerto());
			// Socket de cliente
			Socket clientSocket;
			while (true) {

				System.out.println("Servidor esperando clientes!");
				clientSocket = serverSocket.accept();
				System.out.println("conexion aceptada!");
				HiloCliente hiloCliente = new HiloCliente(idSesion, clientSocket,usuariosConectados);
				hiloCliente.start();
				idSesion++;

			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private int obtenerPuerto() {
		// TODO Auto-generated method stub
		return this.PORT;
	}

}
