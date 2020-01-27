
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PantallaJuego implements Pantalla, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanelJuego juego;
	// ||variables para el fondo||
	private BufferedImage fondo;
	private Image fondoEscalado;

	private ArrayList<Pez> peces = new ArrayList<Pez>();
	private ArrayList<Sprite> comidas = new ArrayList<Sprite>();
	private ArrayList<Sprite> burbujas = new ArrayList<Sprite>();
	private BufferedImage imagenGalleta, imagenPezIzquierda, imagenPezDerecha, imagenComida, imagenCerrar,
			imagenBurbuja, imagenConcha, imagenBienvenida, imagenDone, imagenTexto, imagenMuerto,imagenTienda;
	private Sprite comida, cerrar, agarrar, bienvenida, cerrarVentana, texto,tienda,dinero;
	private boolean darComida = false;
	private boolean escribiendo = false;
	
	int valorX, valorY;
	private VentanaPrincipal ventana;
	private boolean partidaInicida;
	private double tiempoInicial = 0;
	private double contadorTiempo = 0;
	String cadena = "";
	final Font fuenteNombre = new Font("", Font.BOLD, 60);
	final Font fuentePeces = new Font("", Font.BOLD, 18);

	public PantallaJuego(PanelJuego juego, VentanaPrincipal ventana) {
		this.ventana = ventana;
		inicializarPantalla(juego);
	}

	@Override
	public void inicializarPantalla(PanelJuego juego) {
		Partida partida = Datos.cargarDatos();

		tiempoInicial = System.nanoTime();
		this.juego = juego;

		try {
			imagenPezIzquierda = ImageIO.read(new File("./Imagenes/pez-izquierda.png"));
			imagenPezDerecha = ImageIO.read(new File("./Imagenes/pez-derecha.png"));
			fondo = ImageIO.read(new File("./Imagenes/fondo.jpg"));
			imagenGalleta = ImageIO.read(new File("Imagenes/galleta.png"));
			imagenComida = ImageIO.read(new File("Imagenes/comida.png"));
			imagenCerrar = ImageIO.read(new File("Imagenes/cerrar.png"));
			imagenBurbuja = ImageIO.read(new File("Imagenes/burbujas.png"));
			imagenConcha = ImageIO.read(new File("Imagenes/concha.png"));
			imagenBienvenida = ImageIO.read(new File("Imagenes/bienvenida.png"));
			imagenDone = ImageIO.read(new File("Imagenes/done.png"));
			imagenTexto = ImageIO.read(new File("Imagenes/texto.png"));
			imagenMuerto = ImageIO.read(new File("Imagenes/pez-abajo.png"));
			imagenTienda = ImageIO.read(new File("Imagenes/tienda.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		comida = new Sprite(juego.getWidth() - 60, juego.getHeight() - juego.getHeight() + 20, 40, 40, 0, 0,
				imagenComida, false, 0);
		
		tienda = new Sprite(juego.getWidth() - 60, juego.getHeight() - juego.getHeight() + 80, 40, 40, 0, 0,
				imagenTienda, true, 0);
		cerrar = new Sprite(10, 10, 40, 40, 0, 0, imagenCerrar, true, 0);

		agarrar = new Sprite(juego.getWidth() / 2 - 30, 0, 30, 30, 0, 0, imagenConcha, true, 0);

		bienvenida = new Sprite(0, 0, 800, 500, 0, 0, imagenBienvenida, true, 0);

		texto = new Sprite(300, 310, 400, 100, 0, 0, imagenTexto, true, 0);
		fondoEscalado = fondo.getScaledInstance(this.juego.getWidth(), this.juego.getHeight(),
				BufferedImage.SCALE_SMOOTH);

		cerrarVentana = new Sprite(700, 420, 50, 50, 0, 0, imagenDone, true, 0);

		if (partida == null) {
			partidaInicida = false;
		} else {
			partidaInicida = true;

			for (int i = 0; i < partida.getPeces(); i++) {
				peces.add(new Pez(juego.getWidth() / 4 + 100, juego.getWidth() / 4 - 30, 100, 100, 1, 1,
						imagenPezIzquierda, true, partida.getNombres().get(i)));
				peces.get(i).aņadirImagenes(imagenPezDerecha, imagenPezIzquierda, imagenMuerto);
			}
		}

	}

	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		if (partidaInicida) {
			for (int i = 0; i < peces.size(); i++) {

				peces.get(i).pintarEnMundo(g);
			}

			if (comidas.size() > 0) {
				for (int i = 0; i < comidas.size(); i++) {
					comidas.get(i).pintarEnMundo(g);
				}
			}
			comida.pintarEnMundo(g);
			comida.pintarBuffer(imagenComida, true);
			
			tienda.pintarEnMundo(g);
			tienda.pintarBuffer(imagenTienda, true);

			cerrar.pintarEnMundo(g);
			cerrar.pintarBuffer(imagenCerrar, true);
		}
		agarrar.pintarEnMundo(g);

		for (int i = 0; i < burbujas.size(); i++) {
			burbujas.get(i).pintarEnMundo(g);
		}

		if (partidaInicida == false) {

			bienvenida.pintarEnMundo(g);
			cerrarVentana.pintarEnMundo(g);
			cerrarVentana.pintarBuffer(imagenDone, true);
			texto.pintarEnMundo(g);
			texto.pintarBuffer(imagenTexto, true);
			if (escribiendo) {
				pintarNombre(g);
			}
		}

	}

	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (comidas.size() == 0) {
			for (int i = 0; i < peces.size(); i++) {
				peces.get(i).movimientoPez(juego);
				pintarBurbujas(tiempoInicial, peces.get(i));
				peces.get(i).hambre();

			}
		} else {
			for (int i = 0; i < peces.size(); i++) {

				peces.get(i).buscarComida(comidas.get(comidas.size() - 1));
				if (peces.get(i).colisiona(comidas.get(comidas.size() - 1))) {
					if (peces.get(i).salud <= 10)
						peces.get(i).salud += 1;
					comidas.remove(comidas.size() - 1);
					if (comidas.size() == 0)
						break;
				}
			}
		}

		if (comidas.size() > 0) {
			for (int i = 0; i < comidas.size(); i++) {
				comidas.get(i).aplicarVelocidadComida(juego);
			}
		}
		if (burbujas.size() > 0) {
			for (int i = 0; i < burbujas.size(); i++) {
				burbujas.get(i).aplicarVelocidadBrbuja(juego);
				if (burbujas.get(i).posY <= 0)
					burbujas.remove(i);
			}
		}
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if (partidaInicida) {
			if (darComida == true && comida.enBoton == false) {
				comidas.add(new Sprite(e.getX(), e.getY(), 10, 10, 0, 1, imagenGalleta, true, 0));
			} else {

			}
			if (comida.enBoton && darComida) {
				darComida = false;
			} else if (comida.enBoton && darComida == false) {
				darComida = true;
			}

			if (cerrar.enBoton) {
				guardarPartida();
				System.exit(0);
			}
			
			if(tienda.enBoton) {
				juego.pantallaEjecucion = new PantallaTienda(this,juego,ventana);
			}
		} else {
			if (cerrarVentana.enBoton && cadena.length() > 0) {
				crearPez();
				cadena = "";
				bienvenida = null;
				partidaInicida = true;

			}

			if (texto.enBoton) {
				if (escribiendo) {
					escribiendo = false;
				} else {
					escribiendo = true;
				}
			}
		}

	}

	@Override
	public void moverRaton(MouseEvent e) {
		for (int i = 0; i < peces.size(); i++) {
			if (e.getX() >= peces.get(i).posX && e.getX() <= peces.get(i).posX + peces.get(i).ancho
					&& e.getY() >= peces.get(i).posY && e.getY() <= peces.get(i).posY + peces.get(i).alto) {
				peces.get(i).focuseado = true;

			} else {
				peces.get(i).focuseado = false;

			}
		}

		comprobarBoton(e, comida, true);
		comprobarBoton(e, cerrar, true);
		comprobarBoton(e, agarrar, true);
		comprobarBoton(e, cerrarVentana, true);
		comprobarBoton(e, texto, false);
		comprobarBoton(e, tienda, true);

	}

	@Override
	public void redimensionar() {
		fondoEscalado = fondo.getScaledInstance(this.juego.getWidth(), this.juego.getHeight(),
				BufferedImage.SCALE_SMOOTH);

	}

	@Override
	public void pulsarTecla(KeyEvent tecla) {

		if (escribiendo) {
			if (cadena.length() <= 6 && tecla.getKeyCode() != 8)
				cadena += tecla.getKeyChar();

			if (tecla.getKeyCode() == 8 && cadena.length() > 0) {
				cadena = cadena.substring(0, cadena.length() - 1);
			}

		}

	}

	public void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
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

	@Override
	public void arrastrarRaton(MouseEvent e) {

		if (agarrar.enBoton) {

			ventana.moverVentana(e.getXOnScreen() - 400, e.getYOnScreen());

		}

	}

	public void pintarBurbujas(Double tiempoTotal, Pez pez) {
		contadorTiempo += System.nanoTime();

		if (((contadorTiempo / 1e9) - (tiempoInicial / 1e9)) >= 100000000) {

			burbujas.add(new Sprite(pez.posX, pez.posY, 30, 30, 1, 1, imagenBurbuja, false, 0));
			tiempoInicial = System.nanoTime();
			contadorTiempo = 0;
		}
	}

	public void pintarNombre(Graphics g) {
		g.setFont(fuenteNombre);
		g.drawString(cadena, 340, 380);
	}

	public void crearPez() {

		Pez pez = new Pez(juego.getWidth() / 4 + 100, juego.getWidth() / 4 - 30, 100, 100, 1, 1, imagenPezIzquierda,
				true, cadena);
		pez.aņadirImagenes(imagenPezDerecha, imagenPezIzquierda, imagenMuerto);
		peces.add(pez);

	}

	public void guardarPartida() {
		Partida partida = new Partida();

		partida.setPeces(peces.size());
		for (int i = 0; i < peces.size(); i++) {
			partida.setNombres(peces.get(i).nombre);
		}
		Datos.guardarDatos(partida);
	}

}
