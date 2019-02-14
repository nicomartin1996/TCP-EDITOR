package cajaDeHerramientas;

import java.io.Serializable;

public class Documento implements Serializable{
	private int codigoDoc;
	private String emailCreador;
	private String usrCompartido;
	private String nombreArchivo;
	private String fechaMod;
	private String usrUltMod;
	private byte[] contenidoArchivo;
	private boolean docEnUso;
	private String usuarioEdita;
	public Documento(int codigo,String emailCreador, String usrCompartido, String nombreArchivo, String fechaMod,
			String usrUltMod, byte[] cont) {
		this.codigoDoc = codigo;
		this.emailCreador = emailCreador;
		this.usrCompartido = usrCompartido;
		this.nombreArchivo = nombreArchivo;
		this.fechaMod = fechaMod;
		this.usrUltMod = usrUltMod;
		this.contenidoArchivo = cont;
		this.docEnUso = false;
		this.usuarioEdita = null;
		
	}
	public String getEmailCreador() {
		return emailCreador;
	}
	public void setEmailCreador(String emailCreador) {
		this.emailCreador = emailCreador;
	}
	public String getUsrCompartido() {
		return usrCompartido;
	}
	public void setUsrCompartido(String usrCompartido) {
		this.usrCompartido = usrCompartido;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getFechaMod() {
		return fechaMod;
	}
	public void setFechaMod(String fechaMod) {
		this.fechaMod = fechaMod;
	}
	public String getUsrUltMod() {
		return usrUltMod;
	}
	public void setUsrUltMod(String usrUltMod) {
		this.usrUltMod = usrUltMod;
	}
	public byte[] getContenidoArchivo() {
		return contenidoArchivo;
	}
	public void setContenidoArchivo(byte[] contenidoArchivo) {
		this.contenidoArchivo = contenidoArchivo;
	}
	public int getCodigo() {
		return this.codigoDoc;
	}
	public boolean DocEnUso() {
		return docEnUso;
	}
	public void setDocEnUso(boolean docEnUso) {
		this.docEnUso = docEnUso;
	}
	public String getUsuarioEdita() {
		return usuarioEdita;
	}
	public void setUsuarioEdita(String usuarioEdita) {
		this.usuarioEdita = usuarioEdita;
	}
	

}
