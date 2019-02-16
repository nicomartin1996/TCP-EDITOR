package pruebasClases;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cajaDeHerramientas.ConexionBDLite;

public class Archivos {
	private ConexionBDLite conexion;
	private Connection con = null;
	public Archivos () {
		conexion = new ConexionBDLite("NicoBD.db", "engine","configuracion1");
		con = conexion.getConexion();
	}
    public byte[] convertirArchABytes(String file) {
        ByteArrayOutputStream bos = null;
        try {
        	
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
            
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }
//    public void leerArchivo(int materialId, String filename) {
//        // update sql
//        String selectSQL = "SELECT archivo FROM archivos WHERE cod=?";
//        ResultSet rs = null;
//        FileOutputStream fos = null;
//        Connection conn = null;
//        PreparedStatement pstmt = null;
// 
//        try {
//            pstmt = con.prepareStatement(selectSQL);
//            pstmt.setInt(1, materialId);
//            rs = pstmt.executeQuery();
// 
//            // write binary stream into file
//            File file = new File(filename);
//            fos = new FileOutputStream(file);
// 
//            System.out.println("Writing BLOB to file " + file.getAbsolutePath());
//            while (rs.next()) {
//                InputStream input = rs.getBinaryStream("archivo");
////                System.out.println(new String (rs.getBytes("archivo")));
//                byte[] buffer = new byte[1024];
//                while (input.read(buffer) > 0) {
//                    fos.write(buffer);
//                }
//            }
//        } catch (SQLException | IOException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
// 
//                if (conn != null) {
//                    conn.close();
//                }
//                if (fos != null) {
//                    fos.close();
//                }
// 
//            } catch (SQLException | IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
//    
    public String convertirBytes(int materialId) {
        String selectSQL = "SELECT archivo FROM archivos WHERE cod=?";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String res=null;
 
        try {
            pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, materialId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            	res = new String (rs.getBytes("archivo"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
 
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return res;
    }
    
    
}
