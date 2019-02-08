package cajaDeHerramientas;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Usuario {
	private String host;
	private int puerto;
	private String mensajeError = null;
	private ObjectOutputStream salidaAServidor;
	private String msgSalida;
	private ObjectInputStream reciboMsg;
	private Socket client;
	private ObjectOutputStream outPutStream;
	private InputStream inputStream;

	public Socket obtenerSocketCliente() {
		return client;
	}

	public Usuario(String host, int puerto) {

		try {
			this.host = host;
			this.puerto = puerto;
			this.client = new Socket(host, puerto);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			this.mensajeError = "No se encontro ningun servidor al cual conectarse!";
			System.out.println(mensajeError);
		} catch (IOException e) {
			this.mensajeError = "No se encontro ningun servidor al cual conectarse!";
			System.out.println(mensajeError);
		}
		;
	}

	public String obtenerMsgErr() {
		return mensajeError;
	}

	public void enviarMsg(Object consultaAlServidor) {
		try {
			this.outPutStream = new ObjectOutputStream(client.getOutputStream());
			outPutStream.writeObject(consultaAlServidor);
//			outPutStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Objeto recibirMsg() {
		Objeto obj =  null;
		try {
			reciboMsg = new ObjectInputStream(client.getInputStream());
			obj =(Objeto) reciboMsg.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			this.mensajeError = "Comunicacion cerrada en recibir msg1. " + e;
			System.out.println(mensajeError);
		}
		return obj;
	}

	public void cerrarComunicacion() {

		try {
			reciboMsg.close();
			salidaAServidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(mensajeError);
			this.mensajeError = "problemas al cerrar comunicacion. " + e;
		}
	}

	public void desconectarse() {
		try {
			inputStream.close();
			outPutStream.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(mensajeError);
			this.mensajeError = "problemas al cerrar comunicacion. " + e;
		}
	}
}