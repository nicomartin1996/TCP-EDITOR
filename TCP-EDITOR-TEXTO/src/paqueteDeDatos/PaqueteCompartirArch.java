package paqueteDeDatos;

import java.io.Serializable;

public class PaqueteCompartirArch implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4630963663610069624L;
	private String usrCompartido;
	private int codArch;
	public PaqueteCompartirArch(String usrCompartido, int codArch) {

		this.usrCompartido = usrCompartido;
		this.codArch = codArch;
	}
	public String getUsrCompartido() {
		return usrCompartido;
	}
	public void setUsrCompartido(String usrCompartido) {
		this.usrCompartido = usrCompartido;
	}
	public int getCodArch() {
		return codArch;
	}
	public void setCodArch(int codArch) {
		this.codArch = codArch;
	}
	
	

}
