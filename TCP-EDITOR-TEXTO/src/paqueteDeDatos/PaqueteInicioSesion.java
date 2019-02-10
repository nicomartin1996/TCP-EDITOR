package paqueteDeDatos;

import java.io.Serializable;

public class PaqueteInicioSesion implements Serializable{
	private String usuario;
	private String email;
	private String pass;
	
	public PaqueteInicioSesion(String email,String nombreu,String pass) {
		this.email = email ;
		this.pass = pass ;
		this.usuario = nombreu;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
