import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PantallaTienda implements Pantalla {

	
	PantallaJuego pecera;
	PanelJuego juego;
	VentanaPrincipal ventana;
	BufferedImage imagenCerrar,fondoEscalado,imagenConcha;
	
	Sprite cerrar,agarrar;
	public PantallaTienda(PantallaJuego pecera,PanelJuego juego,VentanaPrincipal ventana) {
		this.pecera=pecera;
		this.ventana=ventana;
		inicializarPantalla(juego);
	}
	@Override
	
	public void inicializarPantalla(PanelJuego juego) {
		this.juego=juego;
		try {
			imagenCerrar = ImageIO.read(new File("Imagenes/cerrar.png"));
			fondoEscalado = ImageIO.read(new File("Imagenes/fondoTienda.png"));
			imagenConcha = ImageIO.read(new File("Imagenes/concha.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrar = new Sprite(10, 10, 40, 40, 0, 0, imagenCerrar, true, 0);
		
		agarrar = new Sprite(juego.getWidth() / 2 - 30, 0, 30, 30, 0, 0, imagenConcha, true, 0);
	}

	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
	cerrar.pintarEnMundo(g);
	cerrar.pintarBuffer(imagenCerrar, true);
	agarrar.pintarEnMundo(g);

	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if (cerrar.enBoton) {
			juego.pantallaEjecucion = pecera;
			
		}

	}

	@Override
	public void moverRaton(MouseEvent e) {
		comprobarBoton(e, cerrar, true);
		comprobarBoton(e, agarrar, false);

	}

	@Override
	public void arrastrarRaton(MouseEvent e) {

		if (agarrar.enBoton) {

			ventana.moverVentana(e.getXOnScreen() - 400, e.getYOnScreen());

		}

	}

	@Override
	public void redimensionar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarTecla(KeyEvent tecla) {
		// TODO Auto-generated method stub

	}
	
	public void comprobarBoton(MouseEvent raton, Sprite sprite, Boolean efecto) {
		if (raton.getX() >= sprite.getPosX() && raton.getX() <= sprite.getPosX() + sprite.ancho
				&& raton.getY() >= sprite.getPosY() && raton.getY() <= sprite.getPosY() + sprite.alto) {

			sprite.enBoton = true;
			if (efecto) {
				sprite.ancho = 50;
				sprite.alto = 50;
			}
		} else {
			sprite.enBoton = false;
			if (efecto) {
				sprite.ancho = 40;
				sprite.alto = 40;
			}
		}
	}
	
	public void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}

}
