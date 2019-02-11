package cajaDeHerramientas;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private String email;
	private String usu;
	private String pass;
	private boolean estaConectado;

	public Usuario(String pass,String name,String email) {
		this.usu = name; 
		this.email = email; 
		this.pass = pass;
	}
	
	public Usuario(String name,String email) {
		this.usu = name; 
		this.email = email; 
	}
	
	public Usuario(String name,String email,boolean estaConectado) {
		this.usu = name; 
		this.email = email;
		this.setEstaConectado(estaConectado);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsu() {
		return usu;
	}

	public void setUsu(String usu) {
		this.usu = usu;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean EstaConectado() {
		return estaConectado;
	}

	public void setEstaConectado(boolean estaConectado) {
		this.estaConectado = estaConectado;
	}
}