package paqueteDeDatos;

import java.io.Serializable;

public class PaqueteCrearDocumento implements Serializable {
	private String emailUsr;
	private String fecha;
	private byte[] arch;
	private String nombreArchivo;
	
	public PaqueteCrearDocumento(String emailUsr, String fecha,String name, byte[] arch) {
		this.emailUsr = emailUsr;
		this.fecha = fecha;
		this.arch = arch;
		this.nombreArchivo = name;
	}
	public byte[] getArch() {
		return arch;
	}
	public void setArch(byte[] arch) {
		this.arch = arch;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getEmailUsr() {
		return emailUsr;
	}
	public void setEmailUsr(String emailUsr) {
		this.emailUsr = emailUsr;
	}
	public String getNombre() {
		// TODO Auto-generated method stub
		return this.nombreArchivo;
	}


}
