package cajaDeHerramientas;

import java.io.Serializable;

public class Objeto implements Serializable{
	private Object obj ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Objeto(Object obj) {
		this.obj = obj;
	}
	
	public Object getObj() {
		return obj;
	}
}
