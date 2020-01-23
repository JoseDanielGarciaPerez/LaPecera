
import java.awt.Graphics;

import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelJuego extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Objeto de la interface Pantalla que será el objeto que estará cargado en
	// memoria y será la pantalla que se mostrará
	Pantalla pantallaEjecucion;
	
	

	// En el contructor vamos a colocar lo necesario para que funcione el juego
	public PanelJuego() {
		
		// Al comienzo del juego queremos que se muestre la pantalla de inicio
		pantallaEjecucion = new PantallaInicio(this);

		// HILO

		new Thread(this).start();

		// este metodo se llama para que funcione correctamente el reconocimiento de
		// teclas por pantalla
		this.setFocusable(true);
		// Listeners que añadimos para detectar lo que se hace con el ratón
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// Se llama al metodo pulsarRatón cuando se pulsa el ratón
				pantallaEjecucion.pulsarRaton(e);

			}
		});
		// Listener para controlar la redimensión
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Se llama al metodo redimensionar de la pantalla en ejecución
				pantallaEjecucion.redimensionar();
			}
		});
		// Listener para detectar el movimiento del ratón
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// se llama al metodo mover ratón de la pantalla en ejecución
				pantallaEjecucion.moverRaton(e);
			}

			// si se arrastrase el ratón se llamaría al metodo mouseMoved
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});
		// listener para saber que teclas se pulsan por pantalla
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent arg0) {

				pantallaEjecucion.pulsarTecla(arg0);

			}
		});
	}

	// MÃ©todo que se llama automÃ¡ticamente al hacer repaint
	@Override
	public void paintComponent(Graphics g) {
		// se llama al metodo pintar pantalla de la pantalla en ejecución
		pantallaEjecucion.pintarPantalla(g);
	}

	@Override
	public void run() {
		while (true) {
			// hacemos repaint
			repaint();

			Toolkit.getDefaultToolkit().sync();
			// llamamos al metodo ejecutar frame de la pantalla en ejecución
			pantallaEjecucion.ejecutarFrame();

		}

	}

}
