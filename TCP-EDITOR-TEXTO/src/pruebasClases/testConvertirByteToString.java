package pruebasClases;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

 class testConvertirByteToString {
	 
	 @Test
	 public void testConvertir() {
		 String ss = "hola123123125à@6ð97898123123";
		 byte[] ee = ss.getBytes();
		 String res = new String (ee);
		 System.out.println(res+"-"+ss+"-"+ee);
		 Assert.assertEquals("hola123123125à@6ð97898123123", res);
	 }
	 
	 @Test
	 public void testConvertir2() {
//		 String ss = "hola123123125à@6ð97898123123";
//		 byte[] ee = ss.getBytes();
		 String file = "text.txt";
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
	        
	        String sql = "INSERT INTO archivos (id,usrCreador,usrCompartido,fecUltMod,usrUltModifico,archivo) VALUES ('133','nico','aldana','2019-01-31','nico','"+bos+"',''pruebaGrabacion' );";
	        
	        
	        
	        
	 }

}
