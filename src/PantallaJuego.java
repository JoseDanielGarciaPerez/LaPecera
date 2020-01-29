
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * La clase PantallaJuego tiene dos funciones principales, una es la de servir
 * de pantalla principal donde siempre volverá y donde ocurriran la mayoria de
 * interraciones.<br>
 * Y una segunda función de servir de partida personal, en esta pecera, tenemos
 * variables que pertenecen al usuario y que almacenamos aquí por comodidad.
 * <br>
 * <br>
 * En esta clase encontramos una pecera donde podemos tener un número maximo de
 * 3 animales(Decisión para ahorrar recursos del sistema). <br>
 * <br>
 * La clase nos proporcionará las herramientas para alimentar a los peces,
 * venderlos y hacer que se reproduzcan, también nos encontraremos con dos
 * botones, uno para activar y desactivar la comida y otro para ir a la tienda.
 * 
 * <br>
 * <br>
 * Según se vaya invirtiendo dinero se van desbloqueando varias funcionalidades,
 * un contador de dinero automatico que nos sumará 100 cada minuto, un botón
 * para elegir nosotros un fondo personalizad y otro para elegir una canción
 * personalizada <br>
 * <br>
 * La clase PantallaJuego utiliza la interface Pantalla para heredar los metodos
 * que necesite dentro del motor del juego
 * 
 * @see Pantalla
 * 
 * @author José Daniel
 *
 */

public class PantallaJuego implements Pantalla {

	// Constante que utilizamos para designar el límite de peces
	static final int NUMEROMAXPECES = 3;
	// Panel padre donde realmente se utilizan los listeners, estos listener mandan
	// los inputs a travez de los metodos de la interface
	private PanelJuego juego;
	// Variable donde guardamos la ventanaPrincipal para poder moverla
	private VentanaPrincipal ventana;
	// variables para el fondo||

	private BufferedImage fondo;
	// variable que usamos si queremos reescalar el fondo
	private Image fondoEscalado;
	// variable que utilizamos para guardar el valor en entero del dinero que tiene
	// nuestro persona
	int dineroValor;
	// variable que utilizamos para guardar el valor en entero del dinero que ha
	// invertido nuestro personaje
	int dineroInvertido = 0;
	// ArrayList donde vamos añadiendo los peces que vamos comprando
	ArrayList<Pez> peces = new ArrayList<Pez>();
	// ArrayList donde vamos añadiendo la comida que vamos invocando
	private ArrayList<Sprite> comidas = new ArrayList<Sprite>();
	// ArrayList donde vamos añadiendo las burbujas que se van invocando
	private ArrayList<Sprite> burbujas = new ArrayList<Sprite>();
	// Imagenes que se utilizan en esta pantalla
	private BufferedImage imagenGalleta, imagenPezIzquierda, imagenPezDerecha, imagenComida, imagenCerrar,
			imagenBurbuja, imagenConcha, imagenBienvenida, imagenDone, imagenTexto, imagenMuerto, imagenTienda,
			imagenNuevoPayaso, imagenNuevoTortuga, imagenNuevoTiburon, imagenTortugaIzquierda, imagenTortugaDerecha,
			imagenTiburonIzquierda, imagenTiburonDerecha, imagenTortugaMuerta, imagenTiburonMuerto, imagenMoneda,
			imagenMenuVenta, imagenMenuRep, imagenDec1, imagenDec2, imagenImagen, fondoCargado, imagenMusica;
	// Sprite que se utilizan en este pantalla
	private Sprite comida, cerrar, agarrar, bienvenida, cerrarVentana, texto, tienda, dineroSprite, nuevoPayaso,
			nuevoTortuga, nuevoTiburon, menuVenta, menuRep, spriteDec1, spriteDec2, spriteImagen, spriteMusica;
	// Variables booleana que utilizamos para designar si se puede invocar un objeto
	// de tipo Sprite con la imagen de comida
	private boolean darComida = false;
	// Variable booleana que utilizamos para designar si se ha pulsado sobre la caja
	// de texto
	private boolean escribiendo = false;
	// variables booleanas que utilizamos para saber si se han seleccionado dos
	// peces para reproducirse
	private boolean sinSeleccionarPrimero = false;
	private boolean sinSeleccionarSegundo = false;
	// variables booleanas que usamos para saber si se han desbloqueado estos logros
	boolean dec1, dec2, generarDinero, imagenes, musicas;
	// variables donde almacenamos coordenadas
	int valorX, valorY;
	// variable booleana para saber si se está creando un pez
	boolean nuevoPez;
	// variable que usamos para almacenar de que tipo será el pez que vamos a crear
	// la siguiente vez
	int tipoPez = 0;
	// variables que usamos para almacenar el tiempo
	private double tiempoInicial = 0;
	private double contadorTiempo = 0;
	// variable String donde almacenamos lo que se escribe en la caja de texto
	String cadena = "";
	// rutas por defecto de donde sacamos el fondo y la musica
	String rutaFondo = "./Imagenes/fondo.jpg";
	String rutaMusica = "./Musica/aquario.wav";
	// variables que usamos para ver en el array que peces se han seleccionado, aquí
	// almacenamos su indice
	int primer = -1;
	int segundo = -1;
	// variable que usamos para saber el indice del último pez que fue clickado
	private int ultClickado = 0;
	// fuentes que usamos en el juego
	final Font fuenteNombre = new Font("", Font.BOLD, 60);
	final Font fuentePeces = new Font("", Font.BOLD, 18);
	// Objeto de tipo partida que usamos para cargar y guardar la partida
	Partida partida;
	// variables para guardar tiempos que usamos para el contador automatico de
	// dinero
	double referenciaTiempo = 0;
	double tiempoTranscurrido = 0;
	// objeto de tipo Sprite donde guardamos el sprite sobre el que se tiene el
	// ratón puesto encima
	Sprite spriteEnFocus;
	// objeto de tipo clip para reproducir camciones
	Clip sonido;

	/**
	 * En este constructor recibimos
	 * 
	 * @param juego   el panelJuego para poder trabajar con sus dimensiones
	 * @param ventana para poder trabajar con su posición
	 *
	 */
	public PantallaJuego(PanelJuego juego, VentanaPrincipal ventana) {
		this.ventana = ventana;
		inicializarPantalla(juego);
	}

	/**
	 * El metodo inicializarPantalla es el primer metodo que se llama desde el
	 * constructor cada vez que se crea un objeto nuevo, en este metodo cargamos
	 * todas las imagenes, musica, creamos algunos sprites necesarios y tomamos
	 * referencias de tiempo
	 */
	public void inicializarPantalla(PanelJuego juego) {
		// cargamos los datos de la partida
		partida = Datos.cargarDatos();
		// tomamos una referencia del tiempo de inicio
		tiempoInicial = System.nanoTime();
		this.juego = juego;
		// cargamos todas las imagenes
		try {
			imagenPezIzquierda = ImageIO.read(new File("./Imagenes/pez-izquierda.png"));
			imagenPezDerecha = ImageIO.read(new File("./Imagenes/pez-derecha.png"));

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
			imagenNuevoPayaso = ImageIO.read(new File("Imagenes/nuevoPayaso.png"));
			imagenNuevoTortuga = ImageIO.read(new File("Imagenes/nuevoTortuga.png"));
			imagenNuevoTiburon = ImageIO.read(new File("Imagenes/nuevoTiburon.png"));
			imagenTortugaIzquierda = ImageIO.read(new File("Imagenes/tortuga-izquierda.png"));
			imagenTortugaDerecha = ImageIO.read(new File("Imagenes/tortuga-derecha.png"));
			imagenTortugaMuerta = ImageIO.read(new File("Imagenes/tortuga-abajo.png"));
			imagenTiburonDerecha = ImageIO.read(new File("Imagenes/tiburon-derecha.png"));
			imagenTiburonIzquierda = ImageIO.read(new File("Imagenes/tiburon-izquierda.png"));
			imagenTiburonMuerto = ImageIO.read(new File("Imagenes/tiburon-abajo.png"));
			imagenMoneda = ImageIO.read(new File("Imagenes/moneda.png"));
			imagenMenuVenta = ImageIO.read(new File("Imagenes/menuVenta.png"));
			imagenMenuRep = ImageIO.read(new File("Imagenes/menuRep.png"));
			imagenDec1 = ImageIO.read(new File("Imagenes/dec1.png"));
			imagenDec2 = ImageIO.read(new File("Imagenes/dec2.png"));
			imagenImagen = ImageIO.read(new File("Imagenes/imagen.png"));
			imagenMusica = ImageIO.read(new File("Imagenes/musica.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// creamos los sprites que vamos a ir pintando por pantalla
		comida = new Sprite(juego.getWidth() - 60, juego.getHeight() - juego.getHeight() + 20, 40, 40, 0, 0,
				imagenComida, false, true, "comida");

		tienda = new Sprite(juego.getWidth() - 60, juego.getHeight() - juego.getHeight() + 80, 40, 40, 0, 0,
				imagenTienda, true, true, "tienda");
		cerrar = new Sprite(10, 10, 40, 40, 0, 0, imagenCerrar, true, true, "cerrar");

		agarrar = new Sprite(juego.getWidth() / 2 - 30, 0, 30, 30, 0, 0, imagenConcha, true, true, "agarrar");

		bienvenida = new Sprite(0, 0, 800, 500, 0, 0, imagenBienvenida, true, true, "bienvenida");

		texto = new Sprite(300, 310, 400, 100, 0, 0, imagenTexto, true, true, "texto");

		cerrarVentana = new Sprite(700, 420, 50, 50, 0, 0, imagenDone, true, true, "cerrarVentana");

		nuevoPayaso = new Sprite(0, 0, 800, 500, 0, 0, imagenNuevoPayaso, true, true, "nuevoPayaso");

		nuevoTortuga = new Sprite(0, 0, 800, 500, 0, 0, imagenNuevoTortuga, true, true, "nuevoTortuga");
		nuevoTiburon = new Sprite(0, 0, 800, 500, 0, 0, imagenNuevoTiburon, true, true, "nuevoTiburon");

		dineroSprite = new Sprite(10, juego.getHeight() - 60, 40, 40, 0, 0, imagenMoneda, true, true, "dineroSprite");

		menuVenta = new Sprite(80, juego.getHeight() - 100, 100, 100, 0, 0, imagenMenuVenta, true, false, "menuVenta");

		menuRep = new Sprite(200, juego.getHeight() - 100, 100, 100, 0, 0, imagenMenuRep, true, false, "menuRep");

		spriteDec1 = new Sprite(0, 100, 400, 400, 0, 0, imagenDec1, true, true, "dec1");

		spriteDec2 = new Sprite(600, 400, 100, 100, 0, 0, imagenDec2, true, true, "dec2");

		spriteImagen = new Sprite(740, 460, 40, 40, 0, 0, imagenImagen, true, false, "spriteImagen");

		spriteMusica = new Sprite(740, 300, 40, 40, 0, 0, imagenMusica, true, false, "spriteMusica");

		// cargamos la partida, si fuese igual a null, nuevPez sería true, si no fuese
		// null entonces vamos asignando las variables del objeto Partida a las
		// variables del objeto PantallaJuego
		if (partida == null) {
			nuevoPez = true;
		} else {
			nuevoPez = false;
			dineroValor = partida.getDinero();
			dineroInvertido = partida.getDineroInvertido();
			dec1 = partida.isDec1();
			dec2 = partida.isDec2();
			generarDinero = partida.isGenerarDinero();
			imagenes = partida.isPonerImagenes();
			musicas = partida.isPonerMusica();
			rutaMusica = partida.getRutaMusica();
			rutaFondo = partida.getRutaFondo();
			// creamos los peces dependiendo del tipo y de los que tengamos, además les
			// asignamos su vida, su nombre y su tipo
			for (int i = 0; i < partida.getPeces(); i++) {
				switch (partida.getTipos().get(i)) {
				case 1:
					peces.add(new Pez(juego.getWidth() / 4 + 100, juego.getWidth() / 4 - 30, 100, 100, 1, 1,
							imagenPezIzquierda, true, partida.getNombres().get(i), partida.getEstadisticas().get(i),
							partida.getTipos().get(i)));
					peces.get(i).añadirImagenes(imagenPezDerecha, imagenPezIzquierda, imagenMuerto);
					break;

				case 2:

					peces.add(new Pez(juego.getWidth() / 4 + 100, juego.getWidth() / 4 - 30, 100, 100, 1, 1,
							imagenTortugaIzquierda, true, partida.getNombres().get(i), partida.getEstadisticas().get(i),
							partida.getTipos().get(i)));
					peces.get(i).añadirImagenes(imagenTortugaDerecha, imagenTortugaIzquierda, imagenTortugaMuerta);
					break;
				case 3:
					peces.add(new Pez(juego.getWidth() / 4 + 100, juego.getWidth() / 4 - 30, 200, 100, 1, 1,
							imagenTiburonIzquierda, true, partida.getNombres().get(i), partida.getEstadisticas().get(i),
							partida.getTipos().get(i)));
					peces.get(i).añadirImagenes(imagenTiburonDerecha, imagenTiburonIzquierda, imagenTiburonMuerto);
					break;
				}
			}

		}
		// cargamos el fondo ahora porque si hubiesemos cargado una ruta personalizada
		// se hubiese sobreescrito con otra imagen ya que esa ruta se carga cuando se
		// carga la partida

		try {
			fondo = ImageIO.read(new File(rutaFondo));
			fondoEscalado = fondo.getScaledInstance(this.juego.getWidth(), this.juego.getHeight(),
					BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// hacemos lo mismo con el sonido, si no se ha puesto una canción personalizada,
		// se cargará la de por defecto
		sonido = getSound(rutaMusica);

	}

	/**
	 * El metodo pintarPantalla sirve para ir pintando en los gráficos los sprites,
	 * estos sprites se van actualizando constantemente , ya que sus dimensiones y
	 * posiciones cambian.
	 */
	public void pintarPantalla(Graphics g) {
		// pintamos el fondo
		rellenarFondo(g);
		// si se hubiese desbloqueado la primera recompensa
		if (dec1) {
			// se pinta este sprite por pantalla
			spriteDec1.pintarEnMundo(g);
		}
		// si se desbloquea la segunda recompensa
		if (dec2) {

			spriteDec2.pintarEnMundo(g);
		}
		// si se desbloquea la cuarta recompensa
		if (imagenes) {
			spriteImagen.pintarEnMundo(g);
			spriteImagen.visible = true;
			spriteImagen.pintarBuffer(imagenImagen, true);
		}
		// si se desbloquea la quinta recompensa
		if (musicas) {
			spriteMusica.pintarEnMundo(g);
			spriteMusica.visible = true;
			spriteMusica.pintarBuffer(imagenMusica, true);
		}
		// si no se está creando un nuevoPez o no es la primera vez que se entra al
		// juego, se pinta lo siguiente
		if (nuevoPez == false) {
			// se pintan los peces
			for (int i = 0; i < peces.size(); i++) {

				peces.get(i).pintarEnMundo(g);
				// además se comprueba si se ha clickado sobre ellos y si es así se pinta un
				// menu
				if (peces.get(i).clickado) {
					menuPez(g);
				}
			}
			// si se hubiese pintado alguna comida se pintan las comidas en pantalla
			if (comidas.size() > 0) {
				for (int i = 0; i < comidas.size(); i++) {
					comidas.get(i).pintarEnMundo(g);
				}
			}
			// pintamos botones que tienen efectos de agrandamiento por eso tenemos que
			// volver a pintar el Buffer
			comida.pintarEnMundo(g);
			comida.pintarBuffer(imagenComida, true);

			tienda.pintarEnMundo(g);
			tienda.pintarBuffer(imagenTienda, true);

			cerrar.pintarEnMundo(g);
			cerrar.pintarBuffer(imagenCerrar, true);

			dineroSprite.pintarEnMundo(g);
			// pintamos el dinero que tenemos
			g.setFont(fuentePeces);
			g.drawString(Integer.toString(dineroValor), 60, juego.getHeight() - 30);
		}
		// pintamos una concha que sirve para arrastrar la ventana
		agarrar.pintarEnMundo(g);
		// además vamos pintando las burbujas en pantalla cuando estas existan
		for (int i = 0; i < burbujas.size(); i++) {
			burbujas.get(i).pintarEnMundo(g);
		}
		// si nuevoPez está a true llamamos al metodo creador de Pez, que usa el tipo de
		// Pez y le pasamos los gráficos
		if (nuevoPez) {
			creadorDePez(tipoPez, g);
		}
	}

	/**
	 * El metodo ejecutarFrame sirve para ir usando un hilo que nos actualice en
	 * segundo plano las posiciones de los objetos que nosotros le digamosF
	 */
	public void ejecutarFrame() {
		try {
			// cada 25 milisegundos
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// si no existe ningún sprite de comida
		if (comidas.size() == 0) {
			// pintamos el movimiento de los peces
			for (int i = 0; i < peces.size(); i++) {
				peces.get(i).movimientoPez(juego);
				// llamamos al mettodo pintarBurbujas
				pintarBurbujas(tiempoInicial, peces.get(i));
				// y llamamos al metodo interno de los peces que sirve para que les entre hambre
				peces.get(i).hambre();

			}
			// si existiese comida en pantalla
		} else {
			for (int i = 0; i < peces.size(); i++) {
				// los peces buscarán la comida, en especificio el último sprite comida que
				// encuentren
				peces.get(i).buscarComida(comidas.get(comidas.size() - 1));
				// si colisionan con ese sprite
				if (peces.get(i).colisiona(comidas.get(comidas.size() - 1))) {
					// si la salud de los peces es menor de 10 y el pez sigue vivo
					if (peces.get(i).salud <= 10 && peces.get(i).usadoMuerto == false) {
						// se suma uno a la salud
						peces.get(i).salud += 1;
						// ganamos una moneda de manera simbolica si el pez tuviese hambre, sino no nos
						// da nada

						dineroValor += 1;
					}
					// borramos el sprite de la comida
					comidas.remove(comidas.size() - 1);
					// si no hubiese más sprite comida saldriamos del for para ahorrar espacio
					if (comidas.size() == 0)
						break;
				}
			}

		}
		// este metodo sirve para ir calculando la trayectoria de la comida si está
		// existe en el arraylist
		if (comidas.size() > 0) {
			for (int i = 0; i < comidas.size(); i++) {
				comidas.get(i).aplicarVelocidadComida(juego);
			}
		}
		// lo mismo con la burbuja
		if (burbujas.size() > 0) {
			for (int i = 0; i < burbujas.size(); i++) {
				burbujas.get(i).aplicarVelocidadBrbuja(juego);
				// si sale por encima de la pantalla la burbuja se borra
				if (burbujas.get(i).posY <= 0)
					burbujas.remove(i);
			}
		}
		// si se ha desbloqueado la tercera recompensa se llama al metodo
		// contadorTiempo();
		if (generarDinero) {
			contadorTiempo();
		}
		// con esto nos aseguramos que cuando la canción se acabe se vuelve a reproducir
		if (!sonido.isRunning()) {
			playSound(sonido);
		}
	}

	/**
	 * El metodo pulsarRatón trabaja conjuntamente con el metodo MoverRatón, además
	 * para ahorrar memoria solo hace caso al último sprite sobre el que se haya
	 * pasado el ratón
	 */
	public void pulsarRaton(MouseEvent e) {
		// si no se está creando un nuevoPez
		if (nuevoPez == false) {
			// el metodo darComida() se ejecuta siempre
			darComida(e);
			// si no se ha tocado nunca un sprite no se pasa por aquí
			if (spriteEnFocus != null) {
				// usando los nombres que tienen los sprites hacemos diferentes cosas si estos
				// son el último sprite sobre el que se ha pasado el ratón
				switch (spriteEnFocus.nombre) {

				case "comida":
					activarComida(e);
					break;
				case "cerrar":
					cerrarJuego(e);
					break;
				case "tienda":
					irATienda(e);
					break;
				case "menuVenta":
					menuVenta(e);
					break;
				case "menuRep":
					menuRep(e);
					break;
				case "spriteImagen":
					buscarImagenes();
					break;
				case "spriteMusica":
					buscarMusica();
					break;
				default:

					break;
				}
			}
			// además siempre estamos comprobando si se está pulsando sobre los peces o
			// sobre los menus que se despliegan cuando se pulsa sobre un pez
			for (int i = 0; i < peces.size(); i++) {
				// si se cumple una de estas condiciones entonces se pasa
				if (peces.get(i).focuseado || menuVenta.enBoton || menuRep.enBoton) {
					// si hubiese sido la primera condición la que se ha cumplido entonces ponemos
					// como el último pez clickado el indice de este pez
					if (peces.get(i).focuseado) {
						ultClickado = i;
						peces.get(i).clickado = true;
					}
					// si se cumplen las otras hacemos que estos menus ahora pueden detectar que se
					// pasa el ratón sobre ellos
					menuRep.visible = true;
					menuVenta.visible = true;

				} else {

					peces.get(i).clickado = false;

				}
			}

			// si se estuviera creando un nuevo pez
		} else

		// entonces solo nos interesa detectar otras condiciones, que se está pulsando
		// sobre la caja de texto y sobre el botón de aceptar
		if (spriteEnFocus != null) {

			switch (spriteEnFocus.nombre) {
			case "cerrarVentana":
				aceptarCrearPez(e);
				break;
			case "texto":
				escribir(e);
				break;
			}

		}
	}

	{

	}

	/**
	 * El metodo moverRatón se encarga de detectar donde se encuentra el ratón en
	 * todo momento, en mi caso lo utilizo para saber si el ratón se encuentra sobre
	 * algún objeto
	 */
	public void moverRaton(MouseEvent e) {
		// comprobamos si se está colocando el ratón sobre los peces
		for (int i = 0; i < peces.size(); i++) {
			if (e.getX() >= peces.get(i).posX && e.getX() <= peces.get(i).posX + peces.get(i).ancho
					&& e.getY() >= peces.get(i).posY && e.getY() <= peces.get(i).posY + peces.get(i).alto) {
				peces.get(i).focuseado = true;

			} else {
				peces.get(i).focuseado = false;

			}
		}
		// comprobamos si el ratón está sobre cualquiera de estos sprites,además le
		// pasamos un true o false si queremos que se ejecute un efecto cuando se ponga
		// el ratón sobre el sprite
		comprobarBoton(e, comida, true);
		comprobarBoton(e, cerrar, true);
		comprobarBoton(e, agarrar, true);
		comprobarBoton(e, cerrarVentana, true);
		comprobarBoton(e, texto, false);
		comprobarBoton(e, tienda, true);
		comprobarBoton(e, menuVenta, false);

		comprobarBoton(e, menuRep, false);
		comprobarBoton(e, spriteImagen, true);
		comprobarBoton(e, spriteMusica, true);
	}

	/**
	 * el metodo redimensionar sirve para reescalar la imagen de fondo si la
	 * pantalla cambiase de tamaño, en nuestro caso el tamaño es fijo así que da
	 * igual
	 */
	public void redimensionar() {
		fondoEscalado = fondo.getScaledInstance(this.juego.getWidth(), this.juego.getHeight(),
				BufferedImage.SCALE_SMOOTH);

	}

	/**
	 * El metodo pulsarTecla sirve para detectar que teclas se están pulsando
	 */
	public void pulsarTecla(KeyEvent tecla) {
		// si se ha pulsado sobre la caja de texto
		if (escribiendo) {
			// si la cadena fuese inferior a 6 caracteres y no se pulsase la tecla borrar
			if (cadena.length() <= 6 && tecla.getKeyCode() != 8)
				// vamos añadiendo los caracteres a la cadena
				cadena += tecla.getKeyChar();
			// si se pulsa la tecla borrar y la cadena fuese superior a 0, se ba borrando el
			// último caracter
			if (tecla.getKeyCode() == 8 && cadena.length() > 0) {
				cadena = cadena.substring(0, cadena.length() - 1);
			}
			// si se pone un cero se activa un truco para probar cosas
			if (tecla.getKeyCode() == 48) {
				dineroValor = 100000;
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
	 * Metodo que usamos junto al metodo {@link #moverRaton(MouseEvent)}, este
	 * metodo recibe el ratón, un sprite y si se quiere efecto
	 * 
	 * @param raton  ratón en pantalla
	 * @param sprite el sprite del que queramos saber algo
	 * @param efecto booleano que nos sirve para saber si queremos que ocurra un
	 *               efecto en ese sprite o no
	 */
	public void comprobarBoton(MouseEvent raton, Sprite sprite, Boolean efecto) {
		// si el ratón estuviese dentro de las coordenadas del sprite
		if (raton.getX() >= sprite.getPosX() && raton.getX() <= sprite.getPosX() + sprite.ancho
				&& raton.getY() >= sprite.getPosY() && raton.getY() <= sprite.getPosY() + sprite.alto
				&& sprite.visible) {
			// designamos ese sprite como el sprite en focus
			spriteEnFocus = sprite;
			// además modificamos su variable enBoton a true
			sprite.enBoton = true;
			// si efecto es igual a true cuando se ponga el ratón sobre él, su tamaño
			// aumentará
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

	/**
	 * El metodo arrastrarRatón sirve para poder mover la pantalla sin tener un
	 * marco superior
	 */
	public void arrastrarRaton(MouseEvent e) {
		// si se tiene el ratón encima de el sprite que sirve para agarrar
		if (agarrar.enBoton) {
			// modificamos las coordenadas desde la ventanaPrincipal usando las coordenadas
			// del rattón en pantalla, restando la mitad de la pantalla
			ventana.moverVentana(e.getXOnScreen() - 400, e.getYOnScreen());

		}

	}

	/**
	 * El metodo pintar burbuja se encarga de pintar burbujas en los peces cada X
	 * tiempo
	 * 
	 * @param tiempoTotal se la pasa el tiempo total
	 * @param pez         se le pasa el pez al que se le va añadir la burbuja
	 */
	public void pintarBurbujas(Double tiempoTotal, Pez pez) {
		// se toma la referencia actual
		contadorTiempo = System.nanoTime();
		// cuando la referencia actual menos el tiempoInicial sean mayor o igual a 10
		// segundos
		if (contadorTiempo - tiempoInicial >= 1e+10) {
			// añadimos una burbuja en las coordenadas del pez
			burbujas.add(new Sprite(pez.posX, pez.posY, 30, 30, 1, 1, imagenBurbuja, false, true, "burbujas"));
			// tomamos una nueva referencia actual
			tiempoInicial = System.nanoTime();

		}
	}

	/**
	 * Metodo que sirve para pintar lo que valga cadena en las coordenadas donde
	 * coloramos la caja de texto
	 * 
	 * @param g Graficos del panelJuego
	 */
	public void pintarNombre(Graphics g) {
		g.setFont(fuenteNombre);
		g.drawString(cadena, 340, 380);
	}

	/**
	 * El metodo crearPez se llama cuando se compra un pez nuevo, cuando se inicia
	 * por primera vez la partida o cuando dos peces crian. Dependiendo de divertos
	 * factores se crea un tipo de pez u otro.
	 */
	public void crearPez() {
		Pez pez;
		// si el número de peces actuales sigue siendo menor que el máximo
		if (peces.size() < NUMEROMAXPECES) {
			switch (tipoPez) {
			// si fuese 0 hace lo mismo que si fuese 1
			case 0:
				// crea un pez payaso
			case 1:

				pez = new Pez(juego.getWidth() / 4 + 100, juego.getWidth() / 4 - 30, 100, 100, 1, 1, imagenPezIzquierda,
						true, cadena, 10, 1);
				// le añade todas las imagenes que va a necesitar
				pez.añadirImagenes(imagenPezDerecha, imagenPezIzquierda, imagenMuerto);
				// añade el pez al array
				peces.add(pez);
				break;
			// crea una tortuga
			case 2:
				pez = new Pez(juego.getWidth() / 4 + 100, juego.getWidth() / 4 - 30, 100, 100, 1, 1,
						imagenTortugaIzquierda, true, cadena, 12, 2);
				pez.añadirImagenes(imagenTortugaDerecha, imagenTortugaIzquierda, imagenTortugaMuerta);
				peces.add(pez);
				break;
			// crea un tiburón
			case 3:
				pez = new Pez(juego.getWidth() / 4 + 100, juego.getWidth() / 4 - 30, 200, 100, 1, 1,
						imagenTiburonIzquierda, true, cadena, 8, 3);
				pez.añadirImagenes(imagenTiburonDerecha, imagenTiburonIzquierda, imagenTiburonMuerto);
				peces.add(pez);
			}
		}
	}

	/**
	 * El metodo guardar partida, crea un nuevo Objeto partida, asigna con sus
	 * setters todas las variables actuales y luego llama al metodo statico
	 * {@link Datos#guardarDatos(Partida)}de la clase Datos
	 */
	public void guardarPartida() {

		Partida partida = new Partida();

		partida.setPeces(peces.size());
		partida.setDinero(dineroValor);
		for (int i = 0; i < peces.size(); i++) {
			partida.setNombres(peces.get(i).nombre);
			partida.setEstadisticas(peces.get(i).salud);
			partida.setTipos(peces.get(i).tipo);
			partida.setDineroInvertido(dineroInvertido);
			partida.setDec1(dec1);
			partida.setDec2(dec2);
			partida.setGenerarDinero(generarDinero);
			partida.setPonerImagenes(imagenes);
			partida.setPonerMusica(musicas);
			partida.setRutaFondo(rutaFondo);
			partida.setRutaMusica(rutaMusica);
		}

		Datos.guardarDatos(partida);
	}

	/**
	 * Este metodo muestra una ventana diferente dependiendo del pez que se compre
	 * 
	 * @param tipo recibe el tipo de pez
	 * @param g    recibe los graficos
	 */
	public void creadorDePez(int tipo, Graphics g) {
		// dependiendo del tipo hace una cosa u otra
		switch (tipo) {
		// si el tipo fuese 0 entonces muestra un mensaje de bienvenida ya que es la
		// primera vez que se ejecuta el juego
		case 0:
			bienvenida.pintarEnMundo(g);

			cerrarVentana.pintarEnMundo(g);
			cerrarVentana.pintarBuffer(imagenDone, true);
			texto.pintarEnMundo(g);
			texto.pintarBuffer(imagenTexto, true);
			if (escribiendo) {
				pintarNombre(g);
			}
			break;
		// si el tipo fuese 1 se ha comprado un pez payaso
		case 1:
			nuevoPayaso.pintarEnMundo(g);
			cerrarVentana.pintarEnMundo(g);
			cerrarVentana.pintarBuffer(imagenDone, true);
			texto.pintarEnMundo(g);
			texto.pintarBuffer(imagenTexto, true);
			if (escribiendo) {
				pintarNombre(g);
			}
			break;
		// se ha comprado una tortuga
		case 2:
			nuevoTortuga.pintarEnMundo(g);
			cerrarVentana.pintarEnMundo(g);
			cerrarVentana.pintarBuffer(imagenDone, true);
			texto.pintarEnMundo(g);
			texto.pintarBuffer(imagenTexto, true);
			if (escribiendo) {
				pintarNombre(g);
			}
			break;
		// se ha comprado un tiburón
		case 3:
			nuevoTiburon.pintarEnMundo(g);
			cerrarVentana.pintarEnMundo(g);
			cerrarVentana.pintarBuffer(imagenDone, true);
			texto.pintarEnMundo(g);
			texto.pintarBuffer(imagenTexto, true);
			if (escribiendo) {
				pintarNombre(g);
			}
			break;
		}

	}

	/**
	 * El menuPez simplemente es una manera de encapsular el codigo para llamarlo
	 * desde la clase {@link #pintarPantalla(Graphics) y pintar estos gráficos}
	 * 
	 * @param g
	 */
	public void menuPez(Graphics g) {

		menuVenta.pintarEnMundo(g);
		menuRep.pintarEnMundo(g);

	}

	/**
	 * El metodo dar comida se llama desde el metodo
	 * {@link #pulsarRaton(MouseEvent)} , cuando se pulsa sobre la pantalla, se
	 * llama al siguiente metodo
	 * 
	 * @param e recibe el ratón para usarlo si fuese necesario
	 */
	public void darComida(MouseEvent e) {
		// si la variable darComida fuese true y no nos encontramos en ningún otro
		// sprite
		if (comida.enBoton == false && tienda.enBoton == false && menuVenta.enBoton == false && menuRep.enBoton == false
				&& agarrar.enBoton == false && spriteImagen.enBoton == false && spriteMusica.enBoton == false) {

			// cuando no tengamos el ratón sobre ninguno de estos elementos y demos click
			// desactivará la visibilidad de estos elementos y ya no se detectará si se pasa
			// sobre ellos
			menuVenta.visible = false;
			menuRep.visible = false;
			if (darComida == true) {
				// añadimos uno de comida al Array de comida en la posición donde esté el ratón
				comidas.add(new Sprite(e.getX(), e.getY(), 10, 10, 0, 1, imagenGalleta, true, true, "comida"));
			}
		} else {
			// si se pulsase sobre otro sprite también ponemos esta visibilidad a false
			menuVenta.visible = false;
			menuRep.visible = false;
		}
	}

	/**
	 * El metodo activar comida se llama cuando se pulsa en el metodo
	 * {@link #pulsarRaton(MouseEvent)} en el sprite {@link #comida}.<br>
	 * <br>
	 * El metodo se encarga de habilitar o deshabilitar el poder spawnear objetos
	 * Sprite comida
	 * 
	 * @param e recibe el ratón por si fuese necesario,
	 */
	public void activarComida(MouseEvent e) {
		if (comida.enBoton && darComida) {
			darComida = false;
		} else if (comida.enBoton && darComida == false) {
			darComida = true;
		}
	}

	/**
	 * Metodo que se llama cuando se pulsa en el metodo
	 * {@link #pulsarRaton(MouseEvent)} en el sprite {@link #cerrar}, el metodo
	 * sirve para cerrar la ventana guardando antes la partida
	 * 
	 * @param e recibe el ratón por si fuese necesario,
	 */
	public void cerrarJuego(MouseEvent e) {
		if (cerrar.enBoton) {
			guardarPartida();
			System.exit(0);
		}

	}

	/**
	 * Metodo que se llama cuando se pulsa en el metodo
	 * {@linkplain #pulsarRaton(MouseEvent) en el sprite {@link #tienda}}, este
	 * metodo nos lleva a la {@link PantallaTienda}
	 * 
	 * @param e recibe el ratón por si fuese necesario,
	 */
	public void irATienda(MouseEvent e) {
		if (tienda.enBoton) {
			juego.pantallaEjecucion = new PantallaTienda(this, juego, ventana);
		}
	}

	/**
	 * Metodo que se encarga de vender un pez si se pulsase sobre el sprite
	 * #{@link #menuVenta}
	 * 
	 * @param e
	 */
	public void menuVenta(MouseEvent e) {
		// si tenemos el ratón sobre el menu
		if (menuVenta.enBoton) {
			// y tenemos algún pez en el array
			if (peces.size() > 0) {
				// y el último clickado no es un pez que hayamos borrado
				if (ultClickado < peces.size()) {
					// entonces hacemos un switch con el tipo del último pez clickado y dependiendo
					// del tipo
					switch (peces.get(ultClickado).tipo) {
					case 1:
						//si el pez está vivo vale diferente que si está muerto
						if(peces.get(ultClickado).usadoMuerto==false) {
						dineroValor += 100;
						}else {
						dineroValor += 1;
						}
						break;
					case 2:
						if(peces.get(ultClickado).usadoMuerto==false) {
						dineroValor += 500;
						}else {
						dineroValor += 10;
						}
						break;
					case 3:
						if(peces.get(ultClickado).usadoMuerto==false) {
						dineroValor += 2000;
						}else {
						dineroValor += 100;
						}
						break;
					}
					// borramos el pez que se haya clickado por última vez
					peces.remove(ultClickado);
					// ponemos estas variables a false ya que si se hubiese selecciona un pez para
					// la reproducción y luego se hubiese vendido nos daría error
					sinSeleccionarPrimero = false;
					sinSeleccionarSegundo = false;
				}
			}
		}
	}
/**
 * Metodo que sirve para que dos peces efectuen la reproducción
 * @param e
 */
	public void menuRep(MouseEvent e) {
		
		if (menuRep.enBoton) {
			//si tenemos mínimo dos peces
			if (peces.size() >= 2) {
				//si el primero no ha sido seleccionado
				if (sinSeleccionarPrimero == false) {
					//ahora decimos que ha sido seleccionado
					sinSeleccionarPrimero = true;
					//pasamos a la variable primer el último clickado
					primer = ultClickado;
					//si el primero ya estuviese seleccionado
				} else {
					//ponemos al segundo el último clickado
					segundo = ultClickado;
					//ponemos a true el segundo seleccionado
					sinSeleccionarSegundo = true;

				}
				//si ambos han sido seleccionados y son diferentes el uno del otro
				if (sinSeleccionarPrimero == true && sinSeleccionarSegundo == true && primer != segundo) {
					//se comprueba que sean del mismo topo
					if (peces.get(primer).tipo == peces.get(segundo).tipo) {
						//entonces designamos el tipo del proximo pez como el tipo de cualquiera de los dos
						tipoPez = peces.get(primer).tipo;
						//ponemos nuevoPez a true
						
						nuevoPez = true;
						//ponemos la seleccion de ambos a false
						sinSeleccionarPrimero = false;
						sinSeleccionarSegundo = false;
					}
				}

			}

		}
	}
/**
 * Este metodo se llama cuando se pulsa sobre el botón de aceptar en la ventana de crear pez
 * @param e
 */
	public void aceptarCrearPez(MouseEvent e) {
		//si tuvieramos el ratón sobre el botón y hubiesemos escrito algo en la cadena
		if (cerrarVentana.enBoton && cadena.length() > 0) {
			//llamamos al metodo crearPez();
			crearPez();
			//ponemos la cadena a vacia
			cadena = "";
			//ponemos escribiendo a false
			escribiendo = false;
			//el sprite bienvenida a null
			bienvenida = null;
		
			nuevoPez = false;

		}
	}
/**
 * El metodo escribir simplemente se llama para activar y desactivar la escritura cuando clickamos encima de la caja de texto 
 * @param e
 */
	public void escribir(MouseEvent e) {
		if (texto.enBoton) {

			if (escribiendo) {
				escribiendo = false;
			} else {
				escribiendo = true;
			}
		}
	}
/**
 * Este metodo sirve para sumar 100 de dinero al total cada mínuto
 */
	public void contadorTiempo() {
		tiempoTranscurrido = System.nanoTime();

		if (tiempoTranscurrido - referenciaTiempo >= 6e+10) {
			dineroValor += 100;

			referenciaTiempo = System.nanoTime();

		}
	}
/**
 * Este metodo sirve para cargar una imagen desde un fichero y colocarla de fondo de la pecera por defecto
 */
	public void buscarImagenes() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG Images", "jpg");
		fileChooser.setFileFilter(imgFilter);

		int result = fileChooser.showOpenDialog(null);

		if (result != JFileChooser.CANCEL_OPTION) {

			File fileName = fileChooser.getSelectedFile();

			if ((fileName == null) || (fileName.getName().equals(""))) {

			} else {
				try {
					fondoCargado = ImageIO.read(new File(fileName.getAbsolutePath()));
					rutaFondo = fileName.getAbsolutePath();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fondoEscalado = fondoCargado.getScaledInstance(this.juego.getWidth(), this.juego.getHeight(),
						BufferedImage.SCALE_SMOOTH);

			}
		}
	}
	/**
	 * Metodo que sirve para extraer una pista de audio de un fichero y introducirla en un objeto clip, luego lo retorna
	 * @param file recibe una ruta por constructor del metodo
	 * @return devuelve un objeto de tipo clip donde va almacenado el fichero de audio
	 */
	public Clip getSound(String file) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(file));

			AudioFormat format = audioInputStream.getFormat();

			DataLine.Info info = new DataLine.Info(Clip.class, format);

			Clip sound;

			sound = (Clip) AudioSystem.getLine(info);
			sound.open(audioInputStream);
			return sound;
		}

		catch (Exception e)

		{

			return null;

		}

	}

	/**
	 * Metodo para reproductir un fichero de sonido, se le pasa el fichero
	 * 
	 * @param clip objeto con el sonido almacenado
	 */
	public static void playSound(Clip clip)

	{

		clip.stop();

		clip.setFramePosition(0);

		clip.start();

	}
/**
 * Metodo que sirve para buscar musica desde un fichero y colocarla como musica de fondo del juego por defecto
 */
	private void buscarMusica() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("WAV FILES", "wav");
		fileChooser.setFileFilter(imgFilter);

		int result = fileChooser.showOpenDialog(null);

		if (result != JFileChooser.CANCEL_OPTION) {

			File fileName = fileChooser.getSelectedFile();

			if ((fileName == null) || (fileName.getName().equals(""))) {

			} else {
				sonido.stop();
				sonido.close();
				sonido = getSound(fileName.getAbsolutePath());
				playSound(sonido);
				rutaMusica = fileName.getAbsolutePath();

			}
		}

	}

}
