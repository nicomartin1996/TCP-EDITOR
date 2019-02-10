package cajaDeHerramientas;

import java.io.Serializable;

public class Msg implements Serializable {
	private Object obj ;
	private String accion;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Msg(String accion,Object obj) {
		this.obj = obj;
		this.accion = accion;
	}
	
	public Object getObj() {
		return obj;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
}
