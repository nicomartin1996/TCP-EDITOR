package pruebasClases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import cajaDeHerramientas.ConexionBDLite;
import junit.framework.Assert;

 class testConvertirByteToString {
	 
	 @Test
	 public void testConvertir() {
		 String ss = "hola123123125à@6ð97898123123";
		 byte[] ee = ss.getBytes();
		 String res = new String (ee);
//		 System.out.println(res+"-"+ss+"-"+ee);
		 Assert.assertEquals("hola123123125à@6ð97898123123", res);
	 }
	 
	 @Test
	 public void testConvertir2() {
			ConexionBDLite conexion = new ConexionBDLite("NicoBD.db", "engine","configuracion1");
			Connection con = conexion.getConexion();
			ResultSet result = null;
			Archivos arch = new Archivos();
			String ase = "Este es mi nueva prueba";
			byte[] archivoBytes = ase.getBytes();
			try {
	            PreparedStatement ps = con.prepareStatement("INSERT INTO archivos (cod,usrCreador,usrCompartido,fecUltMod,usrUltModifico,archivo,nombreArchivo) VALUES (?,?,?,?,?,?,?)");
	            ps.setInt(1,545);
	            ps.setString(2, null);
	            ps.setString(3, null);
	            ps.setString(4, null);
	            ps.setString(5, null);
	            ps.setBytes(6, archivoBytes);
	            ps.setString(7, "Ejemplar");
	            ps.executeUpdate();
				String resultado = arch.convertirBytes(12);
				System.out.println("El resultado es: "+resultado);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		          
	 }

}
