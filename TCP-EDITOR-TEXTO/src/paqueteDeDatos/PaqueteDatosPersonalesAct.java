package paqueteDeDatos;

import java.io.Serializable;

public class PaqueteDatosPersonalesAct implements Serializable{
	private String usrMod;
	private String passwMod;
	private String pregunta;
	private String email;
	
	public PaqueteDatosPersonalesAct(String usrMod, String passwMod,String pregunta,String email) {
		this.usrMod = usrMod;
		this.pregunta = pregunta;
		this.passwMod = passwMod;
		this.email = email;
	}
	public String getRespuesta() {
		return pregunta;
	}

	public void setRespuesta(String s) {
		pregunta = s;
	}
	public String getUsrMod() {
		return usrMod;
	}
	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}
	public String getPasswMod() {
		return passwMod;
	}
	public void setPasswMod(String passwMod) {
		this.passwMod = passwMod;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}
	public void setEmail(String email) {
		// TODO Auto-generated method stub
		this.email = email;
	}
	
}
