package cajaDeHerramientas;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	private String host;
	private int puerto;
	private String mensajeError = null;
	private ObjectInputStream reciboMsg;
	private Socket client;
	private ObjectOutputStream outPutStream;
	private InputStream inputStream;
	private Usuario usu;
	private boolean estaConectado;
	public Socket obtenerSocketCliente() {
		return client;
	}

	public Cliente (String ip, int puerto ,Usuario usu) {
		try {
			this.host = ip;
			this.puerto = puerto;
			this.client = new Socket(host, puerto);
			this.estaConectado = true;
			this.usu = usu ;
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			this.mensajeError = "No se encontro ningun servidor al cual conectarse!";
			System.out.println(mensajeError);
		} catch (IOException e) {
			this.mensajeError = "No se encontro ningun servidor al cual conectarse!";
			System.out.println(mensajeError);
		}		
	}
	public Cliente(String host, int puerto) {

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
	}

	public String obtenerMsgErr() {
		return mensajeError;
	}

	public void enviarMsg(Msg consultaAlServidor) {
		try {
			this.outPutStream = new ObjectOutputStream(client.getOutputStream());
			outPutStream.writeObject(consultaAlServidor);
//			outPutStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al querer enviar peticion al sv"+consultaAlServidor.getAccion()+" - "+e);

		}

	}

	public Msg recibirMsg() {
		Msg obj =  null;
		try {
			reciboMsg = new ObjectInputStream(client.getInputStream());
			obj =(Msg) reciboMsg.readObject();
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
			outPutStream.close();
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

	public Usuario getUsu() {
		return usu;
	}
}
