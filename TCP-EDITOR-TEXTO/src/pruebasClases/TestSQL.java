package pruebasClases;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import cajaDeHerramientas.ConexionBDLite;

class TestSQL {

//	@Test
//	void testDibujarComponente() {
//		PanelPrincipal j= new PanelPrincipal(800, 600, new Cliente("192.168.1.51", 5000),new Usuario ("email","drug"));
//		PantallaEditor pantalla = new PantallaEditor("Nico", j, 800, 600);
//		JTextArea cajaDeTexto = new JTextArea("Hola");
//		cajaDeTexto.setVisible(true);
//		j.agregarComponente(cajaDeTexto);
//		String res = cajaDeTexto.getText();
//		Assert.assertEquals("Hola", res);
//	}
	
	//Prueba de SQLite
	@Test
	void testManejoBD () {
		ConexionBDLite conexion = new ConexionBDLite("NicoBD.db", "engine","configuracion1");
		Connection con = conexion.getConexion();
//		String sql = "CREATE TABLE usuarios (email varchar(100) PRIMARY KEY,usuario varchar(100),pass varchar(100));";
//		String sql = "CREATE TABLE amigos (usuario varchar(100),usuarioAmigo varchar(100),PRIMARY KEY(usuario,usuarioAmigo));";
//		String sql = "INSERT INTO usuarios (email,usuario,pass) VALUES ('aldana','aldana','123')";
//		String sql = "INSERT INTO amigos (usuario,usuarioAmigo) VALUES ('nico','aldana')";
//		String sql = "INSERT INTO amigos (usuario,usuarioAmigo) VALUES ('aldana','nico')";
//		String sql = "SELECT * FROM usuarios";
//		String sql = "SELECT * FROM amigos";
//		String sql = "CREATE TABLE archivos (\r\n" +
//				"  cod int PRIMARY KEY,\r\n" +
//				"  usrCreador      varchar(100),\r\n" + 
//				"  usrCompartido   varchar(100),\r\n" + 
//				"  fecUltMod       date,\r\n" + 
//				"  usrUltModifico  varchar(100),\r\n" + 
//				"  archivo         blob,\r\n" + 
//		        "  nombreArchivo   varchar(100)\r\n" +
//				");";
//		String sql = "DROP TABLE archivos";
//		String sql = "INSERT INTO archivos (usrCreador,usrCompartido,fecUltMod,usrUltModifico,archivo) VALUES ('nico','aldana','2019-01-31','nico',null );";
//		String sql = "SELECT * FROM archivos";
		String sql = "DELETE FROM archivos";
		try {
//			ResultSet res = (ResultSet) conexion.Consulta(sql, con);
			if (((boolean) conexion.Consulta(sql, con)) == true) {
				System.out.println("Se creo la tabla");	
			}
//			while (res.next()) {
//				System.out.println(res.getInt(1)+ " - "+res.getString(2));
////				System.out.println(res.getString(1)+ " - "+res.getString(2)+" - "+res.getString(3));
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
