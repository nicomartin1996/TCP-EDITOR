package cajaDeHerramientas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

	private int PORT = 5000;
	private static ServerSocket serverSocket;
	private static ArrayList<String> usuariosConectados = new ArrayList<>();

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
//				HiloCliente hiloCliente = new HiloCliente(idSesion, clientSocket);
//				hiloCliente.start();
				idSesion++;
				
				
				ObjectInputStream reciboMsg ;
				ObjectOutputStream salidaACliente = null;

					/* Recibo Consulta de cliente */
					System.out.println("Hola entre11 antes");
					reciboMsg = new ObjectInputStream(clientSocket.getInputStream());
//					if (reciboMsg != null) {
						System.out.println("Hola entre");
						Objeto msgRecibo =(Objeto)reciboMsg.readObject();
//						Objeto obj = (Objeto) msgRecibo;
						System.out.println(msgRecibo.getObj()+" - el obj");
//						System.out.println("Siendo Sv, Recibo parametro = " + c.getSolicitud() + ", " + c.getResultado());

//						String resultado = procesarConsulta("","");
//						if (resultado.equals("OK")) {
	//
//							//Resultados
	//
//						} else {
	//
//							//Resultados
//						}
//						
						System.out.println(msgRecibo+" - el mensjae que recibi");

						/* Envio respuesta al Cliente */
						String obj1 = "OK";
						Objeto o = new Objeto(obj1);
						salidaACliente = new ObjectOutputStream(clientSocket.getOutputStream());
						salidaACliente.writeObject(o); // Se debe cerrar
						salidaACliente.flush();

			}
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println(ex);
		}
	}

	private int obtenerPuerto() {
		// TODO Auto-generated method stub
		return this.PORT;
	}

}
