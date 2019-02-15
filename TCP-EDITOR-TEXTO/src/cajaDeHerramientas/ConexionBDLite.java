package cajaDeHerramientas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBDLite {
	
	static Connection conexion = null;
	private static String url ;
	private static String usuario;
	private static String pass;
	public ConexionBDLite(String urlBD,String usu,String pass) {
		url = urlBD;
		usuario = usu;
		this.pass = pass;
	}	
	public static Connection getConexion() {
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection("jdbc:sqlite:"+url, usuario, pass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conexion;
	}
	
	public Object Consulta(String consulta,Connection conexion) throws SQLException {
		Statement state= conexion.createStatement();
		if (!consulta.contains("SELECT")) {
			 state.executeUpdate(consulta);
//			 System.out.println("Resultado; "+res);
			 return true;
		}
		return state.executeQuery(consulta);
	}
}
