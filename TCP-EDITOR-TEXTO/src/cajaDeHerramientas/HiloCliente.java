package cajaDeHerramientas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import paqueteDeDatos.PaqueteAgregarAmigo;
import paqueteDeDatos.PaqueteCompartirArch;
import paqueteDeDatos.PaqueteCrearDocumento;
import paqueteDeDatos.PaqueteDatosPersonalesAct;
import paqueteDeDatos.PaqueteEliminarAmigo;
import paqueteDeDatos.PaqueteEliminarArch;
import paqueteDeDatos.PaqueteGuardarDocumento;
import paqueteDeDatos.PaqueteInicioSesion;
import paqueteDeDatos.PaqueteRecuperarContrasena;
import paqueteDeDatos.PaqueteRegistracion;

public class HiloCliente extends Thread{

	private Socket cliente;
	private boolean estaConectado;
	private static ArrayList<Usuario> usuariosConectados = new ArrayList<>();
	private static ArrayList<Documento> documentosUsuarios = new ArrayList<>();


	// En el constructor recibe y guarda los parámetros que sean necesarios.
	// En este caso una lista con toda la conversación y el socket que debe
	// atender.
	public HiloCliente(Socket cliente,ArrayList<Usuario> usuariosConectados,ArrayList<Documento> docUsuarios) {
		this.cliente = cliente;
		HiloCliente.usuariosConectados = usuariosConectados;
		documentosUsuarios = docUsuarios;
		this.estaConectado = true;
	}

	@Override
	public void run() {

		try {
			ObjectInputStream reciboMsg = null ;
			ObjectOutputStream salidaACliente = null;
			
			while (estaConectado) {
				
				/* Recibo Consulta de cliente */
			    reciboMsg = new ObjectInputStream(cliente.getInputStream());
				Msg msgRecibo =(Msg)reciboMsg.readObject();
				Msg resultado = procesarConsulta(msgRecibo);

				/* Envio respuesta al Cliente */
				salidaACliente = new ObjectOutputStream(cliente.getOutputStream());
				salidaACliente.writeObject(resultado); // Se debe cerrar

			}
			
			reciboMsg.close();
			salidaACliente.close();
			cliente.close();
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Problemas al querer leer otra petición: " + ex.getMessage());
		}

	}

	/**
	 * procesa peticion del cliente y retorna resultado
	 * 
	 * @return String
	 */

	
	
	public Msg procesarConsulta(Msg msg) {
		
		ConexionBDLite conexion = new ConexionBDLite("NicoBD.db", "engine", "configuracion1");
		Connection con = conexion.getConexion();
		Msg result = new Msg("NO_OK", null);
		String consulta = msg.getAccion();
		
		if (consulta.equals("existeArch")) {
			try {
				String sql = "SELECT 1 from archivos where cod = '"+(String)msg.getObj()+"'";
				ResultSet res;
				res = (ResultSet) conexion.Consulta(sql, con);
				if (res.next()) {
					if (res.getInt(1) > 0) {
						result = new Msg ("OK",null);
					}
				}
				res.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		if (consulta.equals("fecUltModUsuarios")) {
			ArrayList<String> fechas = new ArrayList<>();
			String codigo  = (String) msg.getObj();
			int cod = Integer.parseInt(codigo);
			Iterator<Documento> it= documentosUsuarios.iterator();
			while (it.hasNext()) {
				Documento aux = it.next();
				if (aux.getCodigo() == cod) {
					fechas.add(aux.getFechaMod());
				}
			}
			try {
				
				String sql = "SELECT fecUltMod FROM usuarioXArchivo WHERE codArchivo= '"+cod+"'";
				ResultSet res = (ResultSet) conexion.Consulta(sql, con);
				while (res.next()) {
					fechas.add(res.getString("fecUltMod"));
				}
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			result = new Msg ("OK",fechas);	
			
		}
		
		if (consulta.equals("listaIntegranteDoc")) {
			
			ArrayList<String> integrantes = new ArrayList<>();
			String codigo  = (String) msg.getObj();
			int cod = Integer.parseInt(codigo);
			Iterator<Documento> it= documentosUsuarios.iterator();
			while (it.hasNext()) {
				Documento aux = it.next();
				if (aux.getCodigo() == cod) {
					integrantes.add(aux.getEmailCreador());
				}
			}
			try {
				
				String sql = "SELECT usrCompartido FROM usuarioXArchivo WHERE codArchivo= '"+cod+"'";
				ResultSet res = (ResultSet) conexion.Consulta(sql, con);
				while (res.next()) {
					integrantes.add(res.getString("usrCompartido"));
				}
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			result = new Msg ("OK",integrantes);				
		}
		
		if (consulta.equals("usuarioEditaDoc")) {
			String codigo  = (String) msg.getObj();
			int cod = Integer.parseInt(codigo);
			boolean seSigue = true;
			Iterator<Documento> it= documentosUsuarios.iterator();
			while (it.hasNext() && seSigue) {
				Documento aux = it.next();
				if (aux.getCodigo() == cod ) {
					result = new Msg("OK",aux.getUsuarioEdita());
					seSigue = false;
				}
			}	
		}
		
		if (consulta.equals("nombreUsuario")) {
			String email  = (String) msg.getObj();
			String sql = "SELECT usuario FROM usuarios WHERE email = '"+email+"'";
			ResultSet res= null;
			try {
				res = (ResultSet) conexion.Consulta(sql, con);
				if (res.next()) {
					result = new Msg ("OK",res.getString(1));
				}
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (consulta.equals("creadorArch")) {
			try {
				String sql = "SELECT usrCreador from archivos where cod = '"+(String)msg.getObj()+"'";
				ResultSet res;
				res = (ResultSet) conexion.Consulta(sql, con);
				if (res.next()) {
					result = new Msg ("OK",res.getString(1));
				}
				res.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if (consulta.equals("eliminarArch")) {
			try {
				
				PaqueteEliminarArch pck = (PaqueteEliminarArch) msg.getObj();
				
				if (puedeEliminar(pck.getCodArch(),pck.getEmail(),conexion,con)) {
					
					String sql = "DELETE FROM archivos WHERE cod = '"+pck.getCodArch()+"' AND usrCreador = '"+pck.getEmail()+"'";
//					System.out.println(sql);
					if (((boolean) conexion.Consulta(sql, con)) == true) {
						sql = "DELETE FROM usuarioXArchivo WHERE codArchivo = '"+pck.getCodArch()+"'";
						conexion.Consulta(sql, con);
						removerArchDeLista(pck.getCodArch());
						result = new Msg("OK",null);
					}
				}
	
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
		
		if (consulta.equals("agregarAmigo")) {
			PaqueteAgregarAmigo emailAmigo = (PaqueteAgregarAmigo) msg.getObj();
			try {
				//Debe existir el usuario en el Sistema para agregarlo como amigo
				if (existeCliente(new PaqueteRegistracion(null, null, null, null, emailAmigo.getEmailAmigo()),conexion,con) == true) {
					String sql ="INSERT INTO amigos (usuario,usuarioAmigo) values ('"+emailAmigo.getEmail()+"','"+emailAmigo.getEmailAmigo()+"')";
					if (((boolean) conexion.Consulta(sql, con)) == true) {
//						System.out.println("se inserto correctamente");
					}
					result = new Msg ("OK",null);	
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		if (consulta.equals("compartirArch")) {
			
			PaqueteCompartirArch packComp = (PaqueteCompartirArch)	msg.getObj();
			try {
				String usr = packComp.getUsrCompartido();
				int cod = packComp.getCodArch();
				String sql = "INSERT INTO usuarioXArchivo (codArchivo,usrCompartido,fecUltMod) VALUES ( '"+packComp.getCodArch()+"' ,'"+packComp.getUsrCompartido()+"',null)";
				if (((boolean) conexion.Consulta(sql, con)) == true) {
//					System.out.println("se inserto correctamente");
				}
				result = new Msg ("OK",null);
			} catch (Exception e) {
				System.out.println("No pudo insertar el usuarioxarc");
			}
		}
		
		if (consulta.equals("guardarDocumento")) {
			
			boolean esUsrCreador = false;
			PaqueteGuardarDocumento datosAGuardar = (PaqueteGuardarDocumento)msg.getObj();
			int cod = datosAGuardar.getCodigoArch();
			String email = datosAGuardar.getEmailUsr();
			String fecha = datosAGuardar.getFecMod();
			byte[] archEnBytes = datosAGuardar.getArchMod();
			/// Actualizo lista de Documentos del Server.
			Iterator<Documento> it= documentosUsuarios.iterator();
			int idCodListDoc = 0;
			while (it.hasNext()) {
				Documento aux = it.next();
				if (aux.getCodigo() == cod && aux.DocEnUso() == true) {
					result = new Msg ("OK",null);
					aux.setDocEnUso(false);
					aux.setContenidoArchivo(archEnBytes);
					aux.setUsuarioEdita("");
					if (aux.getEmailCreador().equals(email)) {
						aux.setFechaMod(fecha);
						esUsrCreador = true;
					}
					documentosUsuarios.set(idCodListDoc, aux);
				}
				idCodListDoc++;
			}
			
			////// Actualizo Tablas.
			
			try {
				
	            PreparedStatement ps;
	            String sql = null;
				if (esUsrCreador) {
					
					sql ="UPDATE archivos SET fecUltMod = ?,archivo=? WHERE  cod =?";
					ps = con.prepareStatement(sql);
		            ps.setString(1,fecha);
		            ps.setBytes(2, archEnBytes);
		            ps.setInt(3, cod);
		            
					if (ps.executeUpdate() == 1) {
						result = new Msg("OK", null);
					}
				}else{
					
					sql ="UPDATE archivos SET archivo=? WHERE  cod =?";
					ps = con.prepareStatement(sql);
		            ps.setBytes(1, archEnBytes);
		            ps.setInt(2, cod);
		            
					if (ps.executeUpdate() == 1) {
						
						sql ="UPDATE usuarioXArchivo SET fecUltMod = '"+fecha+"' WHERE  codArchivo = '"+cod+"' AND usrCompartido = '"+email+"' ";
						if (((boolean) conexion.Consulta(sql, con)) == true) {
//							System.out.println("Se actualizo correctamente");
							result = new Msg("OK", null);
						}
						
					}	
				}
	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if (consulta.equals("actualizarDatosPersonales")) {
			PaqueteDatosPersonalesAct pck = (PaqueteDatosPersonalesAct) msg.getObj();
			if (pasoSeguridad(pck.getEmail(),pck.getRespuesta(),conexion,con)) {
				//Respuesta ha contestado correctamente!
				try {
					String set = "";
					if (pck.getPasswMod() != null && !pck.getPasswMod().equals("") ) {
						set +="pass = '"+pck.getPasswMod()+"'";
					}				
					if (pck.getUsrMod()!= null && !pck.getUsrMod().equals("")) {
						if (!set.equals("")) {
							set+=",";
						}
						set +="usuario = '"+pck.getUsrMod()+"'";
					}
					String sql="UPDATE usuarios SET "+set+" WHERE email = '"+pck.getEmail()+"';";
//					System.out.println(sql);
					if (((boolean) conexion.Consulta(sql, con)) == true) {
//						System.out.println("Se actualizo correctamente");
						result = new Msg("OK", null);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		if (consulta.equals("edicionDoc")) {
			String codigoYEmail  = (String) msg.getObj();
			String[] info = codigoYEmail.split("-");
			int cod = Integer.parseInt(info[0]);
			String usuarioEdita = info[1];
//			System.out.println("Mis datos: "+cod+"- "+usuarioEdita);
			Iterator<Documento> it= documentosUsuarios.iterator();
			int idCodListDoc = 0;
			while (it.hasNext()) {
				Documento aux = it.next();
				if (aux.getCodigo() == cod && aux.DocEnUso() == false) {
					
					aux.setDocEnUso(true);
					aux.setUsuarioEdita(usuarioEdita);
//					System.out.println(aux.getUsuarioEdita());
					result = new Msg ("OK",aux);
					documentosUsuarios.set(idCodListDoc, aux);
				}else if (aux.getCodigo() == cod && aux.DocEnUso()) {
					result = new Msg("EnUso",aux);
				}
				idCodListDoc++;
			}
		}
		if (consulta.equals("listarDoc")) {
			PaqueteInicioSesion userQuePidePeticion = (PaqueteInicioSesion)msg.getObj();
			String[] infoUnzip = new String[3];
			infoUnzip[0] = userQuePidePeticion.getEmail();
			infoUnzip[1] = userQuePidePeticion.getPass();
			infoUnzip[2] = userQuePidePeticion.getUsuario();
			ArrayList<Documento> documentos = new ArrayList<>();
			try {			
				String sql = "SELECT * FROM archivos WHERE usrCreador = '"+infoUnzip[0]+"'";
				ResultSet res;
				res = (ResultSet) conexion.Consulta(sql, con);
				while (res.next()) {
					Documento doc = new Documento(res.getInt(1),res.getString(2), res.getString(3),  res.getString(7), res.getString(4),res.getString(5), res.getBytes(6));
					documentos.add(doc);
				}
				
				sql = "SELECT a.* FROM archivos as a INNER JOIN usuarioXArchivo as b ON a.cod = b.codArchivo  WHERE b.usrCompartido = '"+infoUnzip[0]+"'";
				res = (ResultSet) conexion.Consulta(sql, con);
				int cant =0;
				while (res.next()) {
					cant++;
					Documento doc = new Documento(res.getInt(1),res.getString(2), res.getString(3),  res.getString(7), res.getString(4),res.getString(5), res.getBytes(6));
					documentos.add(doc);
				}
				
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = new Msg("OK", documentos);
		}
		if (consulta.equals("crearDocumento")) {
			PaqueteCrearDocumento crearDoc = (PaqueteCrearDocumento)msg.getObj();
			String emailUsr = crearDoc.getEmailUsr();
			String fecha = crearDoc.getFecha();
			String nombreArchivo = crearDoc.getNombre();
			byte[] archivoEnBytes = crearDoc.getArch();
			int inc= autoIncremento("SELECT cod FROM archivos ORDER BY cod DESC", conexion, con);
			try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO archivos (cod,usrCreador,usrCompartido,fecUltMod,usrUltModifico,archivo,nombreArchivo) VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1,inc);
            ps.setString(2, emailUsr);
            ps.setString(3, null);
            ps.setString(4, fecha);
            ps.setString(5, emailUsr);
            ps.setBytes(6, archivoEnBytes);
            ps.setString(7, nombreArchivo);
            
			if (ps.executeUpdate() == 1) {
				documentosUsuarios.add(new Documento(inc,emailUsr,null,nombreArchivo,fecha,emailUsr,archivoEnBytes));
//				System.out.println("Se insertó correctamente!");
				result = new Msg("OK", null);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (consulta.equals("listaUsuarios")) {
			PaqueteInicioSesion userQuePidePeticion = (PaqueteInicioSesion)msg.getObj();
			String[] infoUnzip = new String[3];
			infoUnzip[0] = userQuePidePeticion.getEmail();
			infoUnzip[1] = userQuePidePeticion.getPass();
			infoUnzip[2] = userQuePidePeticion.getUsuario();
			
			ArrayList<String> emails = obtenerAmigos(infoUnzip[0], conexion, con);
			Iterator<String> it = emails.iterator();
			ArrayList<Usuario> usuariosAmigos = new ArrayList<>();
			while (it.hasNext()) {
				Iterator<Usuario> it2 = usuariosConectados.iterator();
				String emailAmigo = it.next();
				boolean estaConectado = false;
				while(it2.hasNext()) {
					
					Usuario usuarioAux = it2.next();
					if (usuarioAux.getEmail().equals(emailAmigo)) {
						estaConectado = true;	
					}
				}
				usuariosAmigos.add(new Usuario(infoUnzip[2],emailAmigo,estaConectado));
	
			}
			result = new Msg("OK", usuariosAmigos);

		}
		
		if (consulta.equals("eliminarAmigo")) {
			
			PaqueteEliminarAmigo pck = (PaqueteEliminarAmigo)msg.getObj();
			String sql = "DELETE FROM amigos WHERE usuario = '"+pck.getUsr()+"' AND usuarioAmigo = '"+pck.getUsrAEliminar()+"'";
//			System.out.println(sql);
			try {
				if (((boolean) conexion.Consulta(sql, con)) == true) {
					result = new Msg ("OK",null);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if (consulta.equals("registrar")) {
			PaqueteRegistracion reg = (PaqueteRegistracion)msg.getObj();
			if (existeCliente(reg, conexion, con) == false) {
				result = new Msg("OK", null);
				String sqlExiste = "INSERT INTO usuarios (email,pass,usuario,nya,respuestaSeguridad) VALUES ('" + reg.getEmail() + "','" + reg.getPass() + "','" + reg.getUsr() + "','" + reg.getNya() + "','" + reg.getRespuesta()+ "')";
//				System.out.println("sql insert:" + sqlExiste);
				try {
					if (((boolean) conexion.Consulta(sqlExiste, con)) == false) {
//						System.out.println("se inserto correctamente");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}

		if (consulta.equals("iniciarsesion")) {
			PaqueteInicioSesion userQuePidePeticion = (PaqueteInicioSesion)msg.getObj();
			String[] infoUnzip = new String[3];
			infoUnzip[0] = userQuePidePeticion.getEmail();
			infoUnzip[1] = userQuePidePeticion.getPass();
			infoUnzip[2] = userQuePidePeticion.getUsuario();

			ResultSet res = null;
			Integer cantidad = 0;
//			String sqlExiste = "SELECT count(*) as Cantidad FROM usuarios WHERE usuario = '" + infoUnzip[2]
//					+ "' and pass = '" + infoUnzip[1] + "' and email = '"+infoUnzip[0]+"'";
			String sqlExiste = "SELECT count(*) as Cantidad FROM usuarios WHERE email = '" + infoUnzip[0]
					+ "' and pass = '" + infoUnzip[1] + "'";
			try {
				res = (ResultSet) conexion.Consulta(sqlExiste, con);
				res.next();
				cantidad = res.getInt(1);
				res.close();
			} catch (SQLException e) {

				System.out.println(e);
				return result;
			}

			if (cantidad > 0) {
				usuariosConectados.add(new Usuario(infoUnzip[2],infoUnzip[0]));
				result = new Msg("OK", null);

			} else {
				
			}

		}
		
		if (consulta.equals("recuperarContraseña")) {
			
			PaqueteRecuperarContrasena pck = (PaqueteRecuperarContrasena) msg.getObj();
			if (pasoSeguridad(pck.getEmail(),pck.getRespu(),conexion,con)) {
				//Respuesta ha contestado correctamente!
				try {

					String sql="UPDATE usuarios SET pass = '"+pck.getPass()+"' WHERE email = '"+pck.getEmail()+"';";
//					System.out.println(sql);
					if (((boolean) conexion.Consulta(sql, con)) == true) {
//						System.out.println("Se actualizo correctamente");
						result = new Msg("OK", null);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		}
		if (consulta.equals("Salir")) {
			String email = (String)msg.getObj();
			boolean estaEditando = false;
			Iterator<Documento> itDoc= documentosUsuarios.iterator();
			boolean seSigue = true;
			
			while (itDoc.hasNext() && seSigue) {
				Documento aux = itDoc.next();
				if (aux.getUsuarioEdita().equals(email)) {
					estaEditando = true;
					seSigue = false;
				}
			}
			
			if (estaEditando == false) {
				//No esta editando
				seSigue = true;
				estaConectado = false;
				////////////////Elimino de la lista de usuarios conectados /////////////////
				Iterator<Usuario> it = usuariosConectados.iterator();
				while (it.hasNext() && seSigue) {
					
					Usuario aux = it.next();
					if (aux.getEmail().equals(email)) {
						usuariosConectados.remove(aux);
						seSigue = false;
					}	
				}
				result = new Msg("OK", null);
				////////////////////// Fin /////////////////////////////////////////////////////
			}
	
		}
		if (consulta.equals("5")) {

		}
		
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean pasoSeguridad(String email, String respuesta,ConexionBDLite conexion,Connection con) {
		int cantidad = 0;
		String sql = "SELECT count(*) as cantidad FROM usuarios WHERE email = '"+email+"' AND respuestaSeguridad = '"+respuesta+"';";
//		System.out.println(sql);
		try {
			ResultSet res = null;
			res = (ResultSet) conexion.Consulta(sql, con);
			if (res.next()) {
				cantidad = res.getInt(1);
			}
			res.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cantidad > 0 ? true : false;
	}

	private Documento removerArchDeLista(int codArch) {
		boolean seSigue = true;
		Documento aux = null;
		Iterator<Documento> it= documentosUsuarios.iterator();
		while (it.hasNext() && seSigue) {
			aux = it.next();
			if (aux.getCodigo() == codArch ) {
				documentosUsuarios.remove(aux);
				seSigue = false;
			}
		}
		return aux;
	}

	private boolean puedeEliminar(int cod, String usr,ConexionBDLite conexion,Connection con) {
		
		int cantidad = 0;
		String sql = "SELECT count(*) as cantidad FROM archivos WHERE cod = '"+cod+"' AND usrCreador = '"+usr+"'";
		try {
			ResultSet res = null;
			res = (ResultSet) conexion.Consulta(sql, con);
			if (res.next()) {
				cantidad = res.getInt(1);
			}
			res.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cantidad > 0 ? true : false;
	}

	public static int autoIncremento (String sql,ConexionBDLite conexion,Connection con) {
		ResultSet res = null;
		Integer cantidad = 0;
		try {
			res = (ResultSet) conexion.Consulta(sql, con);
			if (res.next()) {
				cantidad = res.getInt(1);
			}
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cantidad+1;
	}
	
	public static ArrayList<String> obtenerAmigos(String email, ConexionBDLite conexion, Connection con) {
		ResultSet res = null;
		ArrayList<String> emailAmigos = null;
		String sqlAmigos = "SELECT usuarioAmigo FROM amigos WHERE usuario = '" +email+ "'";
		try {
			res = (ResultSet) conexion.Consulta(sqlAmigos, con);
			emailAmigos = new ArrayList<>();
			while (res.next()) {
				emailAmigos.add(res.getString(1));	
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return emailAmigos;
	}
	
	public static boolean existeCliente(PaqueteRegistracion paq, ConexionBDLite conexion, Connection con) {
		
		ResultSet res = null;
		Integer cantidad = 0;
		String sqlExiste = "SELECT count(*) as Cantidad FROM usuarios WHERE email = '" + paq.getEmail() + "'";
//		System.out.println("hola estoy verificando"+sqlExiste);
		try {
			res = (ResultSet) conexion.Consulta(sqlExiste, con);
			res.next();
			cantidad = res.getInt(1);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (cantidad > 0) {
			return true;
		}
		return false;
	}
}
