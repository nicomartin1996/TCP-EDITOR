package paqueteDeInterfacesGraficas;

import cajaDeHerramientas.Cliente;

public class ControlPantallas {
	private static boolean fun = false;
	private static PantallaEditor pantallaPrincipal;
	private static PanelPrincipal panelPrincipal;
	private static Cliente userCli = null;
	

	public static void iniciarEditor() {
		fun = true;
		inicializar();
	}

	public static void inicializar() {
		crearPantallaInicio();
	}

	public static void crearPantallaInicio() {
		PantallaSesion pantallaInicio = new PantallaSesion(); // Primer pantalla a mostrarse.
		pantallaInicio.setVisible(true);

		while (fun && (pantallaInicio.obtenerAccionLanzada() != "Ingresar")) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		userCli = pantallaInicio.obtenerSocketCliente();
		pantallaInicio.dispose();
		panelPrincipal = new PanelPrincipal(800, 600, userCli, pantallaInicio.getUsuario());
		setPantallaPrincipal(new PantallaEditor("Editor En Tiempo Real", panelPrincipal, 800, 600));
	}

	public static void cerrarEditor() {
		fun = false;
	}

	public static void actualizar() {
		panelPrincipal.actualizar();
	}

	public static void dibujar() {
		panelPrincipal.dibujar();

	}

	public static void buclePrincipal() {
		while (fun) {

			try {
				actualizar();
				dibujar();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		iniciarEditor();
		buclePrincipal();

	}

	public static PantallaEditor getPantallaPrincipal() {
		return pantallaPrincipal;
	}

	public static void setPantallaPrincipal(PantallaEditor pantallaIniciada) {
		pantallaPrincipal = pantallaIniciada;
	}
}
