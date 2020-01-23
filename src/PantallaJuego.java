import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PantallaJuego implements Pantalla {

	private PanelJuego juego;
	// ||variables para el fondo||
	private BufferedImage fondo;
	private Image fondoEscalado;
	private final int PECESINICIALES = 2;
	private ArrayList<Pez> peces = new ArrayList<Pez>();
	private ArrayList<Sprite> comidas = new ArrayList<Sprite>();
	private BufferedImage imagenGalleta,imagenPezIzquierda,imagenPezDerecha;
	
	private boolean darComida=true;
	public PantallaJuego(PanelJuego juego) {
		inicializarPantalla(juego);
	}
	@Override
	public void inicializarPantalla(PanelJuego juego) {
		
		this.juego=juego;
		
		try {
			imagenPezIzquierda = ImageIO.read(new File("./Imagenes/pez-izquierda.png"));
			imagenPezDerecha = ImageIO.read(new File("./Imagenes/pez-derecha.png"));
			fondo = ImageIO.read(new File("./Imagenes/fondo.jpg"));
			imagenGalleta = ImageIO.read(new File("Imagenes/galleta.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < PECESINICIALES; i++) {
			peces.add(new Pez(juego.getWidth()/4+100,juego.getWidth()/4-30,100,100,1,1,imagenPezIzquierda,true));
			peces.get(i).a�adirImagenes(imagenPezDerecha, imagenPezIzquierda);
		}
		
		fondoEscalado = fondo.getScaledInstance(this.juego.getWidth(), this.juego.getHeight(), BufferedImage.SCALE_SMOOTH);

	}

	@Override
	public void pintarPantalla(Graphics g) {
	rellenarFondo(g);
	for (int i = 0; i < peces.size(); i++) {
		
		peces.get(i).pintarEnMundo(g);
	}
	
	if(comidas.size()>0) {
		for (int i = 0; i < comidas.size(); i++) {
			comidas.get(i).pintarEnMundo(g);
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
		if(comidas.size()==0) {
		for (int i = 0; i < peces.size(); i++) {
			peces.get(i).movimientoPez(juego);
		}
		}else {
			for (int i = 0; i < peces.size(); i++) {
				
				peces.get(i).buscarComida(comidas.get(comidas.size()-1));
				if(peces.get(i).colisiona(comidas.get(comidas.size()-1))) {
					comidas.remove(comidas.size()-1);
					if(comidas.size()==0)
						break;
				}
			}
		}
	
		if(comidas.size()>0) {
			for (int i = 0; i < comidas.size(); i++) {
				comidas.get(i).aplicarVelocidad(juego);
			}
		}
		
		
		
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if(darComida==true) {
		comidas.add( new Sprite(e.getX(),e.getY(),10,10,0,1,imagenGalleta,true, 0));
		}else {
			
		}
		
		
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
		for (int i = 0; i < peces.size(); i++) {
			if(e.getX()>=peces.get(i).posX && e.getX()<=peces.get(i).posX+peces.get(i).ancho && e.getY()>=peces.get(i).posY && e.getY()<=peces.get(i).posY+peces.get(i).alto) {
				darComida=false;
			}else {
				darComida=true;
			}
		}

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