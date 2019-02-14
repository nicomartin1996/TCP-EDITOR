package paqueteDeDatos;

import java.io.Serializable;

public class PaqueteRegistracion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nya;
	private String usr;
	private String respuesta;
	private String pass;
	private String email;
	
	
	public PaqueteRegistracion(String nya, String usr, String respuesta, String pass, String email) {
		this.nya = nya;
		this.usr = usr;
		this.respuesta = respuesta;
		this.pass = pass;
		this.email = email;
	}
	public String getNya() {
		return nya;
	}
	public void setNya(String nya) {
		this.nya = nya;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
