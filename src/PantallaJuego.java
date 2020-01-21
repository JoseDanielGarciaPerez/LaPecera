import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PantallaJuego implements Pantalla {

	private PanelJuego juego;
	// ||variables para el fondo||
	private BufferedImage fondo;
	private Image fondoEscalado;
	
	
	private BufferedImage imagenGalleta;
	private Sprite galleta;
	public PantallaJuego(PanelJuego juego) {
		inicializarPantalla(juego);
	}
	@Override
	public void inicializarPantalla(PanelJuego juego) {
		
		this.juego=juego;
		
		try {
			fondo = ImageIO.read(new File("Imagenes/fondo.jpg"));
			imagenGalleta = ImageIO.read(new File("Imagenes/Galleta.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		galleta = new Sprite(juego.getWidth()/2,juego.getHeight()/2,100,100,0,0,imagenGalleta,true, 0);
		System.out.println(this.juego.getWidth());
//		fondoEscalado = fondo.getScaledInstance(this.juego.getWidth(), this.juego.getHeight(), BufferedImage.SCALE_SMOOTH);

	}

	@Override
	public void pintarPantalla(Graphics g) {
	rellenarFondo(g);
	galleta.pintarEnMundo(g);

	}

	@Override
	public void ejecutarFrame() {
	

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void redimensionar() {
		fondoEscalado = fondo.getScaledInstance(this.juego.getWidth(), this.juego.getHeight(), BufferedImage.SCALE_SMOOTH);

	}

	@Override
	public void pulsarTecla(KeyEvent tecla) {
		// TODO Auto-generated method stub

	}
	
	public void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}

}
