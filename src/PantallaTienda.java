import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * La clase PantallaTienda muestra un menu con botones donde podemos comprar
 * diferentes peces o acceder al menu de bonificaciones
 * 
 * @author José Daniel
 *
 */
public class PantallaTienda implements Pantalla {

	PantallaJuego pecera;
	PanelJuego juego;
	VentanaPrincipal ventana;
	BufferedImage imagenCerrar, fondoEscalado, imagenConcha, imagenBoton, imagenPayaso, imagenTortuga, imagenTiburon,
			imagenPrecioPez, imagenPrecioTortuga, imagenPrecioTiburon, imagenMoneda, imagenBonificacion;

	Sprite cerrar, agarrar, botonComprarPayaso, botonComprarTortuga, botonComprarTiburon, pez, tortuga, tiburon, pPez,
			pTortuga, pTiburon, dineroSprite, botonBon;
	final Font fuentePeces = new Font("", Font.BOLD, 18);
	Sprite spriteEnFocus;

	public PantallaTienda(PantallaJuego pecera, PanelJuego juego, VentanaPrincipal ventana) {
		this.pecera = pecera;

		this.ventana = ventana;
		inicializarPantalla(juego);
	}

	@Override
	/**
	 * Este metodo se encarga de cargar todas las imagenes y crear los sprites
	 * necesarios
	 */
	public void inicializarPantalla(PanelJuego juego) {
		this.juego = juego;
		try {

			imagenCerrar = ImageIO.read(new File("Imagenes/cerrar.png"));
			fondoEscalado = ImageIO.read(new File("Imagenes/fondoTienda.png"));
			imagenConcha = ImageIO.read(new File("Imagenes/concha.png"));
			imagenBoton = ImageIO.read(new File("Imagenes/botonComprar.png"));
			imagenPayaso = ImageIO.read(new File("Imagenes/pez-izquierda.png"));
			imagenTortuga = ImageIO.read(new File("Imagenes/tortuga-Izquierda.png"));
			imagenTiburon = ImageIO.read(new File("Imagenes/tiburon-izquierda.png"));
			imagenPrecioPez = ImageIO.read(new File("Imagenes/precioPez.png"));
			imagenPrecioTortuga = ImageIO.read(new File("Imagenes/precioTortuga.png"));
			imagenPrecioTiburon = ImageIO.read(new File("Imagenes/precioTiburon.png"));
			imagenMoneda = ImageIO.read(new File("Imagenes/moneda.png"));
			imagenBonificacion = ImageIO.read(new File("Imagenes/bonificacion.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrar = new Sprite(10, 10, 40, 40, 0, 0, imagenCerrar, true, true, "cerrar");

		agarrar = new Sprite(juego.getWidth() / 2 - 30, 0, 30, 30, 0, 0, imagenConcha, true, true, "agarrar");

		botonComprarPayaso = new Sprite(400, 100, 300, 100, 0, 0, imagenBoton, true, true, "botonComprarPayaso");

		botonComprarTortuga = new Sprite(400, 250, 300, 100, 0, 0, imagenBoton, true, true, "botonComprarTortuga");

		botonComprarTiburon = new Sprite(400, 400, 300, 100, 0, 0, imagenBoton, true, true, "botonComprarTiburon");

		pez = new Sprite(300, 100, 100, 100, 0, 0, imagenPayaso, true, true, "pez");

		tortuga = new Sprite(300, 250, 100, 100, 0, 0, imagenTortuga, true, true, "tortuga");

		tiburon = new Sprite(300, 400, 200, 100, 0, 0, imagenTiburon, true, true, "tiburon");

		pPez = new Sprite(100, 100, 200, 100, 0, 0, imagenPrecioPez, true, true, "pPez");

		pTortuga = new Sprite(100, 250, 200, 100, 0, 0, imagenPrecioTortuga, true, true, "pTortuga");

		pTiburon = new Sprite(100, 400, 200, 100, 0, 0, imagenPrecioTiburon, true, true, "pTiburon");

		dineroSprite = new Sprite(10, juego.getHeight() - 60, 40, 40, 0, 0, imagenMoneda, true, true, "dineroSprite");

		botonBon = new Sprite(juego.getWidth() - 60, 10, 40, 40, 0, 0, imagenBonificacion, true, true, "botonBon");
	}

	/**
	 * El metodo pintar Pantalla hace lo mismo que en PantallaJuego
	 */
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		cerrar.pintarEnMundo(g);
		cerrar.pintarBuffer(imagenCerrar, true);
		agarrar.pintarEnMundo(g);

		pez.pintarEnMundo(g);
		botonComprarPayaso.pintarEnMundo(g);
		botonComprarPayaso.pintarBuffer(imagenBoton, true);
		tortuga.pintarEnMundo(g);
		botonComprarTortuga.pintarEnMundo(g);
		botonComprarTortuga.pintarBuffer(imagenBoton, true);
		tiburon.pintarEnMundo(g);
		botonComprarTiburon.pintarEnMundo(g);
		botonComprarTiburon.pintarBuffer(imagenBoton, true);
		pPez.pintarEnMundo(g);
		pTiburon.pintarEnMundo(g);
		pTortuga.pintarEnMundo(g);

		dineroSprite.pintarEnMundo(g);

		botonBon.pintarEnMundo(g);
		botonBon.pintarBuffer(imagenBonificacion, true);

		g.setFont(fuentePeces);
		g.drawString(Integer.toString(pecera.dineroValor), 60, juego.getHeight() - 30);
	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub

	}

	/**
	 * El metodo pulsarRatón trabaja conjuntamente con el metodo MoverRatón, además
	 * para ahorrar memoria solo hace caso al último sprite sobre el que se haya
	 * pasado el ratón
	 */
	public void pulsarRaton(MouseEvent e) {
		if (spriteEnFocus != null) {
			switch (spriteEnFocus.nombre) {
			case "cerrar":
				cerrarVentana(e);
				break;
			case "botonComprarPayaso":
				comprarPez(e);
				break;
			case "botonComprarTortuga":
				comprarTortuga(e);
				break;
			case "botonComprarTiburon":
				comprarTiburon(e);
				break;
			case "botonBon":
				abrirBonificacion(e);
				break;

			}
		}

	}

	/**
	 * El metodo moverRatón se encarga de detectar donde se encuentra el ratón en
	 * todo momento, en mi caso lo utilizo para saber si el ratón se encuentra sobre
	 * algún objeto
	 */
	public void moverRaton(MouseEvent e) {
		comprobarBoton(e, cerrar, true, 1);
		comprobarBoton(e, agarrar, false, 0);
		comprobarBoton(e, botonComprarPayaso, true, 2);
		comprobarBoton(e, botonComprarTortuga, true, 2);
		comprobarBoton(e, botonComprarTiburon, true, 2);
		comprobarBoton(e, botonBon, true, 1);
	}

	/**
	 * El metodo arrastrarRatón sirve para poder mover la pantalla sin tener un
	 * marco superior
	 */
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

	/**
	 * Metodo que usamos junto al metodo {@link #moverRaton(MouseEvent)}, este
	 * metodo recibe el ratón, un sprite y si se quiere efecto
	 * 
	 * @param raton  ratón en pantalla
	 * @param sprite el sprite del que queramos saber algo
	 * @param efecto booleano que nos sirve para saber si queremos que ocurra un
	 *               efecto en ese sprite o no
	 * @param diseño al haber botones más grandes debemos decirle que diseño
	 *               necesitamos
	 */
	public void comprobarBoton(MouseEvent raton, Sprite sprite, Boolean efecto, int diseño) {
		if (raton.getX() >= sprite.getPosX() && raton.getX() <= sprite.getPosX() + sprite.ancho
				&& raton.getY() >= sprite.getPosY() && raton.getY() <= sprite.getPosY() + sprite.alto) {

			spriteEnFocus = sprite;
			sprite.enBoton = true;
			if (efecto) {
				switch (diseño) {
				case 1:
					sprite.ancho = 50;
					sprite.alto = 50;
					break;
				case 2:
					sprite.ancho = 310;
					sprite.alto = 110;
					break;
				}
			}
		} else {
			sprite.enBoton = false;
			if (efecto) {
				switch (diseño) {
				case 1:
					sprite.ancho = 40;
					sprite.alto = 40;
					break;
				case 2:
					sprite.ancho = 300;
					sprite.alto = 100;
					break;
				}
			}
		}
	}

	/**
	 * Metodo que se llama para pintar el fondo de la pantalla
	 * 
	 * @param g
	 */
	public void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}

	/**
	 * Metodo que nos devuelve a la pantallaJuego
	 * 
	 * @param e
	 */
	public void cerrarVentana(MouseEvent e) {
		juego.pantallaEjecucion = pecera;
	}

	/**
	 * Si se pulsa sobre el botón de comprar pez se llama a este metodo que se
	 * encarga de descontar el valor del pez , si tenemos suficiente y de
	 * devolvernos a la pantallaJuego para darle nombre a nuestro pez
	 * 
	 * @param e
	 */
	public void comprarPez(MouseEvent e) {
		if (botonComprarPayaso.enBoton && pecera.peces.size() < PantallaJuego.NUMEROMAXPECES) {
			if (pecera.dineroValor >= 120) {
				pecera.nuevoPez = true;
				pecera.tipoPez = 1;
				pecera.dineroValor -= 120;
				juego.pantallaEjecucion = pecera;
			}
		}
	}
	/**
	 * Si se pulsa sobre el botón de comprar tortuga se llama a este metodo que se
	 * encarga de descontar el valor de la tortuga , si tenemos suficiente y de
	 * devolvernos a la pantallaJuego para darle nombre a nuestra tortuga
	 * 
	 * @param e
	 */
	public void comprarTortuga(MouseEvent e) {
		if (botonComprarTortuga.enBoton && pecera.peces.size() < PantallaJuego.NUMEROMAXPECES) {
			if (pecera.dineroValor >= 600) {
				pecera.nuevoPez = true;
				pecera.tipoPez = 2;
				pecera.dineroValor -= 600;
				juego.pantallaEjecucion = pecera;
			}
		}
	}
	/**
	 * Si se pulsa sobre el botón de comprar tiburón se llama a este metodo que se
	 * encarga de descontar el valor del tiburón , si tenemos suficiente y de
	 * devolvernos a la pantallaJuego para darle nombre a nuestro tiburón
	 * 
	 * @param e
	 */
	public void comprarTiburon(MouseEvent e) {
		if (botonComprarTiburon.enBoton && pecera.peces.size() < PantallaJuego.NUMEROMAXPECES) {
			if (pecera.dineroValor >= 2500) {
				pecera.nuevoPez = true;
				pecera.tipoPez = 3;
				pecera.dineroValor -= 2500;
				juego.pantallaEjecucion = pecera;
			}
		}
	}
/**
 * Este metodo nos lleva a la pantallaBonificación
 * @param e
 */
	public void abrirBonificacion(MouseEvent e) {
		juego.pantallaEjecucion = new PantallaBonificaciones(pecera, juego, ventana);
	}

}
