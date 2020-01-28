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
	BufferedImage imagenCerrar,fondoEscalado,imagenConcha,imagenBoton,imagenPayaso,imagenTortuga,imagenTiburon,imagenPrecioPez,imagenPrecioTortuga,imagenPrecioTiburon,imagenMoneda,imagenBonificacion;
	private int dinero;
	Sprite cerrar,agarrar,botonComprarPayaso,botonComprarTortuga,botonComprarTiburon ,pez,tortuga,tiburon,pPez,pTortuga,pTiburon,dineroSprite,botonBon;
	final Font fuentePeces = new Font("", Font.BOLD, 18);
	Sprite spriteEnFocus;
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
			imagenBonificacion= ImageIO.read(new File("Imagenes/bonificacion.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrar = new Sprite(10, 10, 40, 40, 0, 0, imagenCerrar, true, true,"cerrar");
		
		agarrar = new Sprite(juego.getWidth() / 2 - 30, 0, 30, 30, 0, 0, imagenConcha, true, true,"agarrar");
		
		botonComprarPayaso  = new Sprite(400, 100, 300, 100, 0, 0, imagenBoton, true, true,"botonComprarPayaso");
		
		botonComprarTortuga = new Sprite(400, 250, 300, 100, 0, 0, imagenBoton, true, true,"botonComprarTortuga");
		
		botonComprarTiburon = new Sprite(400, 400, 300, 100, 0, 0, imagenBoton, true, true,"botonComprarTiburon");
		
		pez = new Sprite(300, 100, 100, 100, 0, 0, imagenPayaso, true, true,"pez");
		
		tortuga  = new Sprite(300, 250, 100, 100, 0, 0, imagenTortuga, true, true,"tortuga");
		
		tiburon =  new Sprite(300, 400, 200, 100, 0, 0, imagenTiburon, true, true,"tiburon");
		
		pPez= new Sprite(100, 100, 200, 100, 0, 0, imagenPrecioPez, true, true,"pPez");
		
		pTortuga = new Sprite(100, 250, 200, 100, 0, 0, imagenPrecioTortuga, true, true,"pTortuga");
		
		pTiburon = new Sprite(100, 400, 200, 100, 0, 0, imagenPrecioTiburon, true, true,"pTiburon");
				
		dineroSprite = new Sprite(10, juego.getHeight() - 60, 40, 40, 0, 0, imagenMoneda, true, true,"dineroSprite");		
		
		botonBon = new Sprite(juego.getWidth()-60,10,40,40,0,0,imagenBonificacion,true,true,"botonBon");
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
	
	botonBon.pintarEnMundo(g);
	botonBon.pintarBuffer(imagenBonificacion, true);

	g.setFont(fuentePeces);
	g.drawString(Integer.toString(dinero), 60, juego.getHeight() - 30);
	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if(spriteEnFocus!=null) {
		switch(spriteEnFocus.nombre) {
		case "cerrar": cerrarVentana(e);
			break;
		case "botonComprarPayaso": comprarPez(e);
		break;
		case "botonComprarTortuga": comprarTortuga(e);
		break;
		case "botonComprarTiburon": comprarTiburon(e);
		break;
		case "botonBon": abrirBonificacion(e);
		break;
			
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
		comprobarBoton(e, botonBon, true, 1);
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

			spriteEnFocus = sprite;
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
	
	public void cerrarVentana(MouseEvent e) {
		juego.pantallaEjecucion = pecera;
	}
	
	public void comprarPez(MouseEvent e) {
		if(botonComprarPayaso.enBoton) {
			if(pecera.dineroValor>=120) {
			pecera.nuevoPez=true;
			pecera.tipoPez = 1;
			pecera.dineroValor-=120;
			juego.pantallaEjecucion = pecera;
			}
		}
	}
	
	public void comprarTortuga(MouseEvent e) {
		if(botonComprarTortuga.enBoton) {
			if(pecera.dineroValor>=600) {
			pecera.nuevoPez=true;
			pecera.tipoPez=2;
			pecera.dineroValor-=600;
			juego.pantallaEjecucion = pecera;
			}
		}
	}
	
	public void comprarTiburon(MouseEvent e) {
		if(botonComprarTiburon.enBoton) {
			if(pecera.dineroValor>=2500) {
			pecera.nuevoPez=true;
			pecera.tipoPez=3;
			pecera.dineroValor-=2500;
			juego.pantallaEjecucion = pecera;
			}
		}
	}
	
	public void abrirBonificacion(MouseEvent e) {
		juego.pantallaEjecucion = new PantallaBonificaciones(pecera, juego, ventana, dinero);
	}

}
