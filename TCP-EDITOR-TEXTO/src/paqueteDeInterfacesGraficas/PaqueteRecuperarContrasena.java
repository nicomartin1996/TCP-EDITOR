package paqueteDeInterfacesGraficas;

import java.io.Serializable;

public class PaqueteRecuperarContrasena implements Serializable{
	private String email;
	private String pass;
	private String respu;
	public PaqueteRecuperarContrasena(String email, String pass, String respu) {
		this.email = email;
		this.pass = pass;
		this.respu = respu;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getRespu() {
		return respu;
	}
	public void setRespu(String respu) {
		this.respu = respu;
	}
	


}
