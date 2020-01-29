import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * La clase Pez, es un Sprite personalizado, se basa en la misma idea pero
 * aportando solo lo necesario para los peces, busqueda de comida,
 * colisiones,sistema de hambre
 * 
 * @author José Daniel
 *
 */
public class Pez {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// la variable nombre sirve para asignarle un nombre al pez;
	String nombre;
	// salud que le queda al pez
	int salud;
	// tipo de pez
	int tipo;
	protected int posX;
	protected int posY;
	protected int ancho;
	protected int alto;
	protected int velX;
	protected int velY;
	int direccionX = 0;
	int direccionY = 0;
	protected BufferedImage buffer;

	protected boolean haComido = false;
	protected boolean movimiento = false;
	protected boolean focuseado = false;
	protected boolean clickado = false;
	// Imagenes que usamos para tener todas las posiciones del pez dependiendo de a
	// donde se dirija
	Image imagenDerecha;
	Image imagenIzquierda;
	Image imagenMuerto;

	double referenciaTiempo = 0;
	double tiempoTranscurrido = 0;
	// booleanos que usamos para ahorrar memoria
	boolean usadoDerecha = false;
	boolean usadoIzquierda = false;
	boolean usadoMuerto = false;

	final Font fuentePeces = new Font("", Font.BOLD, 18);

	/**
	 * Constructor donde le pasamos lo básico y evitar repeticiones
	 *
	 */
	private Pez(int posX, int posY, int ancho, int alto, int velX, int velY, String nombre, int salud, int tipo) {
		this.posX = posX;
		this.posY = posY;
		this.ancho = ancho;
		this.alto = alto;
		this.velX = velX;
		this.velY = velY;
		this.nombre = nombre;
		this.salud = salud;
		this.tipo = tipo;

	}

	/**
	 * 
	 * Constructor que sirve para pasar una imagen y decirle si hay que
	 * redimensionarla
	 */
	public Pez(int posX, int posY, int ancho, int alto, int velX, int velY, Image imgConstructor, boolean redimensionar,
			String nombre, int salud, int tipo) {
		this(posX, posY, ancho, alto, velX, velY, nombre, salud, tipo);

		referenciaTiempo = System.nanoTime();
		pintarBuffer(imgConstructor, redimensionar);
	}

	/**
	 * Metodo que sirve para pintar el Buffer con la imagen que le pasamos en el
	 * constructor
	 * 
	 */
	public void pintarBuffer(Image imgConstructor, boolean redimensionar) {
		buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.drawImage(redimensionar ? imgConstructor.getScaledInstance(this.ancho, this.alto, Image.SCALE_SMOOTH)
				: imgConstructor, 0, 0, null);
		g.dispose();
	}

	/**
	 * El metodo movimiento pez funciona de la siguiente manera, si el pez está vivo
	 * y no se ha calculado ninguna posición, calcula una x y una y aleatorias, el
	 * pez se dirige a esa posición hasta que llega a ella y vuelve a calcular otra
	 * posición para moverse allí, esto simula un movimiento falso.
	 * 
	 * @param panelJuego
	 */
	public void movimientoPez(PanelJuego panelJuego) {

		if (usadoMuerto == false) {
			if (movimiento == false) {
				movimiento = true;
				// calculamos una x dentro de los límites de la pantalla
				direccionX = (int) (Math.random() * ((panelJuego.getWidth() - this.ancho) + 1));
				// calculamos una y dentro de los límites de la pantalla
				direccionY = (int) (Math.random() * ((panelJuego.getHeight() - this.alto) + 1));

			}

			if (posX > direccionX) {
				posX -= velX;
				// si no se ha usado la imagenIzquierda
				if (usadoIzquierda == false) {
					// cambiamos el buffer del pez por su imagen izquierda
					pintarBuffer(imagenIzquierda, true);
					// y le decimos que se está usando la imagen izquierda y no la derecha
					usadoIzquierda = true;
					usadoDerecha = false;
				}

			}
			// si el pez se estuviera moviendo a la derecha haría lo inverso a lo anterior
			if (posX < direccionX) {
				posX += velX;
				if (usadoDerecha == false) {
					pintarBuffer(imagenDerecha, true);
					usadoDerecha = true;
					usadoIzquierda = false;
				}

			}
			// para la altura no hace falta cambiar la imagen
			if (posY > direccionY) {

				posY -= velY;
			}
			if (posY < direccionY) {
				posY += velY;
			}
			// si llega a la x y a la Y se pone movimiento a false y así se vuelven a
			// calcular una x y una y
			if (posX == direccionX && posY == direccionY) {
				movimiento = false;
			}

		} else {
			// si el pez estuviera muerto, se le va sumando a su Y hasta que sube arriba del
			// todo
			if (posY > 0)
				posY -= 1;
		}

	}

	/**
	 * Este metodo se ejecuta desde el metodo PintarPantalla de los objetos que usan
	 * la interface pantalla, sirve para pintar el pez usando su buffer, además si
	 * se pone el ratón encima se coloca una barra de vida y el nombre del pez
	 * encima
	 * 
	 * @param g
	 */
	public void pintarEnMundo(Graphics g) {
		int posicionX = 0;
		g.drawImage(buffer, posX, posY, null);

		if (focuseado) {

			g.setColor(Color.red);
			for (int i = 0; i < salud; i++) {
				int posicionFinal = (posX + 10) + posicionX;

				posicionX += 10;

				g.fillRect(posicionFinal, posY + alto + 5, 20, 10);
			}

			g.setColor(Color.DARK_GRAY);
			g.setFont(fuentePeces);
			g.drawString(nombre, posX + 20, posY);
		}
	}

	/**
	 * Metodo que sirve para calcular si el pez colisiona con un Sprite
	 * 
	 */
	public boolean colisiona(Sprite otro) {
		boolean colisionX = posX < otro.posX ? (posX + ancho >= otro.posX) : (otro.posX + otro.ancho >= posX);

		boolean colisionY = posY < otro.posY ? (posY + alto >= otro.posY) : (otro.posY + otro.alto >= posY);

		return colisionX && colisionY;
	}

	/**
	 * El metodo buscar comida recibe un sprite y se encarga de hacer que el pez se
	 * dirija a las coordenadas de este sprite.
	 * 
	 * @param otro
	 */
	public void buscarComida(Sprite otro) {
		if (usadoMuerto == false) {
			if (posX > otro.posX) {
				posX -= velX;
				if (usadoIzquierda == false) {
					pintarBuffer(imagenIzquierda, true);
					usadoIzquierda = true;
					usadoDerecha = false;
				}

			}
			if (posX < otro.posX) {
				posX += velX;
				if (usadoDerecha == false) {
					pintarBuffer(imagenDerecha, true);
					usadoDerecha = true;
					usadoIzquierda = false;
				}

			}
			if (posY + alto > otro.posY) {
				posY -= velY;
			}
			if (posY + alto < otro.posY) {
				posY += velY;
			}

		}

	}

	/**
	 * El metodo hambre se encarga de hacer que cada 5 minutos el pez pierda uno de
	 * vida, si la salud llega a 0 entonces se coloca el sprite del pez bocaAbajo
	 */
	public void hambre() {
		tiempoTranscurrido = System.nanoTime();

		if (tiempoTranscurrido - referenciaTiempo >= 3e+11 && salud >= 1) {
			salud -= 1;

			referenciaTiempo = System.nanoTime();

		}

		if (salud == 0) {
			if (usadoMuerto == false) {
				pintarBuffer(imagenMuerto, true);
				usadoMuerto = true;
			}
		}
	}
/**
 * Metodo para añadir las imagenes que el pez necesita para realizar sus movimientos
 * 
 */
	public void añadirImagenes(Image imagen1, Image imagen2, Image imagen3) {
		this.imagenDerecha = imagen1;
		this.imagenIzquierda = imagen2;
		this.imagenMuerto = imagen3;
	}
}
