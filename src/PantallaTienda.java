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
	BufferedImage imagenCerrar,fondoEscalado,imagenConcha,imagenBoton,imagenPayaso,imagenTortuga,imagenTiburon;
	
	Sprite cerrar,agarrar,botonComprarPayaso,botonComprarTortuga,botonComprarTiburon ,pez,tortuga,tiburon;
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
			imagenBoton = ImageIO.read(new File("Imagenes/botonComprar.png"));
			imagenPayaso = ImageIO.read(new File("Imagenes/pez-izquierda.png"));
			imagenTortuga =  ImageIO.read(new File("Imagenes/tortuga-Izquierda.png"));
			imagenTiburon = ImageIO.read(new File("Imagenes/tiburon-izquierda.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrar = new Sprite(10, 10, 40, 40, 0, 0, imagenCerrar, true, 0);
		
		agarrar = new Sprite(juego.getWidth() / 2 - 30, 0, 30, 30, 0, 0, imagenConcha, true, 0);
		
		botonComprarPayaso  = new Sprite(400, 100, 300, 100, 0, 0, imagenBoton, true, 0);
		
		botonComprarTortuga = new Sprite(400, 250, 300, 100, 0, 0, imagenBoton, true, 0);
		
		botonComprarTiburon = new Sprite(400, 400, 300, 100, 0, 0, imagenBoton, true, 0);
		
		pez = new Sprite(300, 100, 100, 100, 0, 0, imagenPayaso, true, 0);
		
		tortuga  = new Sprite(300, 250, 100, 100, 0, 0, imagenTortuga, true, 0);
		
		tiburon =  new Sprite(300, 400, 200, 100, 0, 0, imagenTiburon, true, 0);
		
		
	}

	@Override
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
		if(botonComprarPayaso.enBoton) {
			pecera.nuevoPez=true;
			pecera.tipoPez = 1;
			juego.pantallaEjecucion = pecera;
		}
		if(botonComprarTortuga.enBoton) {
			pecera.nuevoPez=true;
			pecera.tipoPez=2;
			juego.pantallaEjecucion = pecera;
		}
		
		if(botonComprarTiburon.enBoton) {
			pecera.nuevoPez=true;
			pecera.tipoPez=3;
			juego.pantallaEjecucion = pecera;
		}

	}

	@Override
	public void moverRaton(MouseEvent e) {
		comprobarBoton(e, cerrar, true,1);
		comprobarBoton(e, agarrar, false,0);
		comprobarBoton(e, botonComprarPayaso, true,2);
		comprobarBoton(e, botonComprarTortuga, true, 2);
		comprobarBoton(e, botonComprarTiburon, true, 2);
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
	
	public void comprobarBoton(MouseEvent raton, Sprite sprite, Boolean efecto,int diseño) {
		if (raton.getX() >= sprite.getPosX() && raton.getX() <= sprite.getPosX() + sprite.ancho
				&& raton.getY() >= sprite.getPosY() && raton.getY() <= sprite.getPosY() + sprite.alto) {

			
			sprite.enBoton = true;
			if (efecto) {
				switch(diseño) {
				case 1:
				sprite.ancho = 50;
				sprite.alto = 50;
				break;
				case 2:
					sprite.ancho=310;
					sprite.alto = 110;
					break;
				}
			}
		} else {
			sprite.enBoton = false;
			if (efecto) {
				switch(diseño) {
				case 1:
				sprite.ancho = 40;
				sprite.alto = 40;
				break;
				case 2:
					sprite.ancho = 300;
					sprite.alto=100;
					break;
				}
			}
		}
	}
	
	public void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}

}
