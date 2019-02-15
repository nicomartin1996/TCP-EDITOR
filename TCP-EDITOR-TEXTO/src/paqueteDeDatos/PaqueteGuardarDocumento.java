package paqueteDeDatos;

import java.io.Serializable;

public class PaqueteGuardarDocumento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fecMod;
	private String emailUsr;
	private byte[] archMod;
	private int codigoArch;
	public PaqueteGuardarDocumento(String fecMod, String emailUsr, byte[] archMod, int codigoArch) {

		this.fecMod = fecMod;
		this.emailUsr = emailUsr;
		this.archMod = archMod;
		this.codigoArch = codigoArch;
	}
	public String getFecMod() {
		return fecMod;
	}
	public void setFecMod(String fecMod) {
		this.fecMod = fecMod;
	}
	public String getEmailUsr() {
		return emailUsr;
	}
	public void setEmailUsr(String emailUsr) {
		this.emailUsr = emailUsr;
	}
	public byte[] getArchMod() {
		return archMod;
	}
	public void setArchMod(byte[] archMod) {
		this.archMod = archMod;
	}
	public int getCodigoArch() {
		return codigoArch;
	}
	public void setCodigoArch(int codigoArch) {
		this.codigoArch = codigoArch;
	}
	
	
	
	
}
