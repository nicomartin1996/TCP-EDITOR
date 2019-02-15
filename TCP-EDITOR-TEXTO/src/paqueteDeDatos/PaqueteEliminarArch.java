package paqueteDeDatos;

import java.io.Serializable;

public class PaqueteEliminarArch implements Serializable{
	private String email;
	private int codArch;
	
	public PaqueteEliminarArch(String email, int codArch) {
		this.email = email;
		this.codArch = codArch;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCodArch() {
		return codArch;
	}
	public void setCodArch(int codArch) {
		this.codArch = codArch;
	}
	
	
	

}
