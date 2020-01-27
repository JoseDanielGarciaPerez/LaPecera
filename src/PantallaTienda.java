import java.awt.Font;
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
	BufferedImage imagenCerrar,fondoEscalado,imagenConcha,imagenBoton,imagenPayaso,imagenTortuga,imagenTiburon,imagenPrecioPez,imagenPrecioTortuga,imagenPrecioTiburon,imagenMoneda;
	private int dinero;
	Sprite cerrar,agarrar,botonComprarPayaso,botonComprarTortuga,botonComprarTiburon ,pez,tortuga,tiburon,pPez,pTortuga,pTiburon,dineroSprite;
	final Font fuentePeces = new Font("", Font.BOLD, 18);
	public PantallaTienda(PantallaJuego pecera,PanelJuego juego,VentanaPrincipal ventana,int dinero) {
		this.pecera=pecera;
		this.dinero=dinero;
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
			imagenPrecioPez = ImageIO.read(new File("Imagenes/precioPez.png"));
			imagenPrecioTortuga =ImageIO.read(new File("Imagenes/precioTortuga.png"));
			imagenPrecioTiburon =ImageIO.read(new File("Imagenes/precioTiburon.png"));
			imagenMoneda = ImageIO.read(new File("Imagenes/moneda.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrar = new Sprite(10, 10, 40, 40, 0, 0, imagenCerrar, true, true);
		
		agarrar = new Sprite(juego.getWidth() / 2 - 30, 0, 30, 30, 0, 0, imagenConcha, true, true);
		
		botonComprarPayaso  = new Sprite(400, 100, 300, 100, 0, 0, imagenBoton, true, true);
		
		botonComprarTortuga = new Sprite(400, 250, 300, 100, 0, 0, imagenBoton, true, true);
		
		botonComprarTiburon = new Sprite(400, 400, 300, 100, 0, 0, imagenBoton, true, true);
		
		pez = new Sprite(300, 100, 100, 100, 0, 0, imagenPayaso, true, true);
		
		tortuga  = new Sprite(300, 250, 100, 100, 0, 0, imagenTortuga, true, true);
		
		tiburon =  new Sprite(300, 400, 200, 100, 0, 0, imagenTiburon, true, true);
		
		pPez= new Sprite(100, 100, 200, 100, 0, 0, imagenPrecioPez, true, true);
		
		pTortuga = new Sprite(100, 250, 200, 100, 0, 0, imagenPrecioTortuga, true, true);
		
		pTiburon = new Sprite(100, 400, 200, 100, 0, 0, imagenPrecioTiburon, true, true);
				
		dineroSprite = new Sprite(10, juego.getHeight() - 60, 40, 40, 0, 0, imagenMoneda, true, true);		
		
		
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
	pPez.pintarEnMundo(g);
	pTiburon.pintarEnMundo(g);
	pTortuga.pintarEnMundo(g);
	
	dineroSprite.pintarEnMundo(g);

	g.setFont(fuentePeces);
	g.drawString(Integer.toString(dinero), 60, juego.getHeight() - 30);
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
			if(pecera.dineroValor>=120) {
			pecera.nuevoPez=true;
			pecera.tipoPez = 1;
			pecera.dineroValor-=120;
			juego.pantallaEjecucion = pecera;
			}
		}
		if(botonComprarTortuga.enBoton) {
			if(pecera.dineroValor>=600) {
			pecera.nuevoPez=true;
			pecera.tipoPez=2;
			pecera.dineroValor-=600;
			juego.pantallaEjecucion = pecera;
			}
		}
		
		if(botonComprarTiburon.enBoton) {
			if(pecera.dineroValor>=2500) {
			pecera.nuevoPez=true;
			pecera.tipoPez=3;
			pecera.dineroValor-=2500;
			juego.pantallaEjecucion = pecera;
			}
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
