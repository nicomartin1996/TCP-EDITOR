package paqueteDeDatos;

import java.io.Serializable;

public class PaqueteEliminarAmigo implements Serializable{
	
	private String usrAEliminar;
	private String usr;
	
	public PaqueteEliminarAmigo(String usrAEliminar, String usr) {

		this.usrAEliminar = usrAEliminar;
		this.usr = usr;
	}
	public String getUsrAEliminar() {
		return usrAEliminar;
	}
	public void setUsrAEliminar(String usrAEliminar) {
		this.usrAEliminar = usrAEliminar;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}	
}
