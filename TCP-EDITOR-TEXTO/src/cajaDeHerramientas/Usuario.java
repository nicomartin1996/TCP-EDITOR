package cajaDeHerramientas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Usuario {
	private String host;
	private int puerto;
	private String mensajeError = null;
	private PrintWriter salidaAServidor;
	private String msgSalida;
	private BufferedReader reciboMsg;
	private Socket client;
	private OutputStream outPutStream;
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

	public void enviarMsg() {
		try {
			this.outPutStream = client.getOutputStream();
			this.msgSalida = "el msg";
			salidaAServidor = new PrintWriter(outPutStream);
			salidaAServidor.println(msgSalida);
			salidaAServidor.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String recibirMsg() {
		try {
			this.inputStream = client.getInputStream();
			this.reciboMsg = new BufferedReader(new InputStreamReader(inputStream));
			String msgRecibo = reciboMsg.readLine();
			//Decodificar el msg		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.mensajeError = "Comunicacion cerrada en recibir msg. " + e;
			System.out.println(mensajeError);
		}
		return "msgRecibido";
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