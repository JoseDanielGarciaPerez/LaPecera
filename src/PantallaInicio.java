import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * La clase PantallaInicio es lo primero que se muestra cuando se carga el juego, simplemente tiene una imagen y un listener para comenzar la partida
 * @author José Daniel
 *
 */
public class PantallaInicio implements Pantalla {

	PanelJuego juego;
	VentanaPrincipal ventana;
	private BufferedImage fondo;
	Image fondoEscalado;
	Color colorLetraInicio= Color.WHITE;
	Clip sonido;
	final Font fuenteNombre = new Font("", Font.BOLD, 60);
	
	public PantallaInicio(PanelJuego juego,VentanaPrincipal ventana) {
		this.ventana=ventana;
		inicializarPantalla(juego);
	}
	/**
	 * En este metodo se carga la musica y el fondo que se van a utilizar en esta pantalla
	 */
	public void inicializarPantalla(PanelJuego juego) {
		this.juego=juego;
		
		try {
			fondo = ImageIO.read(new File("./Imagenes/fondoInicio.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sonido=getSound("./Musica/musicaInicio.wav");
		
		
	}

	/**
	 * Al ser el fondo del tamaño justo, se dibuja la imagen en los gráficos del panelJuego
	 */
	public void pintarPantalla(Graphics g) {
		
		g.drawImage(fondo, 0, 0, null);
		
		
		
		

	}

	/**
	 * En este metodo nos encargamos de ver que la canción siga sonando, si no lo está entonces volvemos a ponerla a sonar
	 */
	public void ejecutarFrame() {
		
		if(!sonido.isRunning()) {
			playSound(sonido);
		}

	}

	/**
	 * Si se pulsa click izquierdo, paramos la musica y creamos una pantallaJuego pasandole el panelJuego y la ventanaPrincipal
	 */
	public void pulsarRaton(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			sonido.stop();
			sonido.close();
			juego.pantallaEjecucion = new PantallaJuego(juego,ventana);
		}

	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void redimensionar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarTecla(KeyEvent tecla) {
		// TODO Auto-generated method stub

	}
	@Override
	public void arrastrarRaton(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	 * @param clip objeto con el sonido almacenado
	 */
	public static void playSound(Clip clip)

	{

		clip.stop();

		clip.setFramePosition(0);

		clip.start();

	}

}
