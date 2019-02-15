package paqueteDeDatos;

import java.io.Serializable;

public class PaqueteAgregarAmigo implements Serializable {
	private String emailAmigo;
	private String email;
	public PaqueteAgregarAmigo(String emailAmigo, String email) {
		this.emailAmigo = emailAmigo;
		this.email = email;
	}
	public String getEmailAmigo() {
		return emailAmigo;
	}
	public void setEmailAmigo(String emailAmigo) {
		this.emailAmigo = emailAmigo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
