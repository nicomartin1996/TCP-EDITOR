package cajaDeHerramientas;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {
	static Connection conexion = null;
	
	public static Connection getConexion() {
		
		String url = "jdbc:sqlserver://localhost\\NICOING;databaseName=BaseDatos_Nico;";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conexion = DriverManager.getConnection(url, "engine", "configuracion1");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conexion;
	}
	
	public Object Consulta(String consulta,Connection conexion) throws SQLException {
		Statement state;
		ResultSet respuesta = null;
		if(consulta.contains("SELECT")) {
			try {
				state = conexion.createStatement();
				respuesta = state.executeQuery(consulta);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return respuesta;
		}else {
			//es consulta de INSERT UPDATE O DELETE
			state = conexion.createStatement();
			
			return state.execute(consulta);
		}
		
	}

}