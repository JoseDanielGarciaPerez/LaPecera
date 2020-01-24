
import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * VentanaPrincipal sirve como contenedor para el panel de juego.
 * 
 * @author jesusredondogarcia
 *
 */
public class VentanaPrincipal {

	// Sigo teniendo la ventana
	JFrame ventana;
	PanelJuego panelJuego;
	private final int ANCHO = 800;
	private final int ALTO = 500;
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(300, 200, ANCHO, ALTO);
		ventana.setResizable(false);
		ventana.setUndecorated(true);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	/**
	 * Método que inicializa todos los componentes de la ventana
	 */
	public void inicializarComponentes() {
		// Definimos el layout:
		ventana.setLayout(new GridLayout(1, 1));

		// PANEL JUEGO
		panelJuego = new PanelJuego(this);
		ventana.add(panelJuego);
	}

	/**
	 * Método que inicializa todos los listeners del programa.
	 */
	public void inicializarListeners() {

	}

	/**
	 * Método que realiza todas las llamadas necesarias para inicializar la ventana
	 * correctamente.
	 */
	public void inicializar() {
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}
	
	public void moverVentana(int x, int y) {
		ventana.setBounds(x, y, ANCHO, ALTO);
	}
}
