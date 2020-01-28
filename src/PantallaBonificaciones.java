import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PantallaBonificaciones implements Pantalla {

	PantallaJuego pecera;
	PanelJuego juego;
	VentanaPrincipal ventana;
	int dinero;
	
	BufferedImage imagenBarraVacia,imagenBarra1,imagenBarra2,imagenBarra3,imagenBarra4,imagenBarra5,imagenBarra6,imagenCerrar,imagenTexto,fondoEscalado,imagenDone,imagenMoneda;
	String cadena="";
	boolean escribiendo;
	Sprite barra,cerrar,spriteEnFocus,texto,botonAceptar,dineroSprite;
	final Font fuenteNombre = new Font("", Font.BOLD, 60);
	final Font fuentePeces = new Font("", Font.BOLD, 18);
	
	public PantallaBonificaciones(PantallaJuego pecera,PanelJuego juego,VentanaPrincipal ventana,int dinero) {
		this.pecera=pecera;
		this.ventana=ventana;
		this.dinero=dinero;
		inicializarPantalla(juego);
		
	}
	@Override
	public void inicializarPantalla(PanelJuego juego) {
		this.juego=juego;
		
		try {
			imagenBarraVacia = ImageIO.read(new File("Imagenes/barraVacia.png"));
			imagenBarra1 = ImageIO.read(new File("Imagenes/barra1.png"));
			imagenBarra2 = ImageIO.read(new File("Imagenes/barra2.png"));
			imagenBarra3 = ImageIO.read(new File("Imagenes/barra3.png"));
			imagenBarra4 = ImageIO.read(new File("Imagenes/barra4.png"));
			imagenBarra5 = ImageIO.read(new File("Imagenes/barra5.png"));
			imagenBarra6 = ImageIO.read(new File("Imagenes/barra6.png"));
			imagenCerrar = ImageIO.read(new File("Imagenes/cerrar.png"));
			imagenTexto = ImageIO.read(new File("Imagenes/texto.png"));
			fondoEscalado = ImageIO.read(new File("Imagenes/fondoTienda.png"));
			imagenDone = ImageIO.read(new File("Imagenes/done.png"));
			imagenMoneda = ImageIO.read(new File("Imagenes/moneda.png"));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrar = new Sprite(10, 10, 40, 40, 0, 0, imagenCerrar, true, true, "cerrar");
		texto = new Sprite(200, 310, 400, 100, 0, 0, imagenTexto, true, true, "texto");
		botonAceptar  = new Sprite(600, 320, 40, 40, 0, 0, imagenTexto, true, true, "botonAceptar");
		dineroSprite = new Sprite(10, juego.getHeight() - 60, 40, 40, 0, 0, imagenMoneda, true, true,"dineroSprite");	
		
		
		
		
		
	}

	@Override
	public void pintarPantalla(Graphics g) {
		pintarFondo(g);
		cerrar.pintarEnMundo(g);
		cerrar.pintarBuffer(imagenCerrar, true);
		
		texto.pintarEnMundo(g);
		pintarBarra(g);
		if(escribiendo) {
			pintarNombre(g);
		}
		botonAceptar.pintarEnMundo(g);
		botonAceptar.pintarBuffer(imagenDone, true);
		
		dineroSprite.pintarEnMundo(g);
		
		g.setFont(fuentePeces);
		g.drawString(Integer.toString(dinero), 60, juego.getHeight() - 30);
		g.drawString(Integer.toString(pecera.dineroInvertido), 150, juego.getHeight() - 30);
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
			case "texto" : escribir(e);
			
			break;
			case "botonAceptar": aniadirFondos();
				break;
			}
		}
	}

	@Override
	public void moverRaton(MouseEvent e) {
		comprobarBoton(e, cerrar, true,1);
		comprobarBoton(e, texto, false, 0);
		comprobarBoton(e, botonAceptar, true, 1);
		
	}
	

	@Override
	public void arrastrarRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void redimensionar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarTecla(KeyEvent tecla) {
		
		if (escribiendo) {
		if( cadena.length()<=4 && (tecla.getKeyCode()==48 || tecla.getKeyChar()==49 || tecla.getKeyChar()==50 || tecla.getKeyChar()==51
		|| tecla.getKeyChar()==52 || tecla.getKeyChar()==53|| tecla.getKeyChar()==54 || tecla.getKeyChar()==55 || tecla.getKeyChar()==56 || tecla.getKeyChar()==57))
				cadena += tecla.getKeyChar();
				
			

			if (tecla.getKeyCode() == 8 && cadena.length() > 0) {
				cadena = cadena.substring(0, cadena.length() - 1);
			}

		}

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
	
	public void cerrarVentana(MouseEvent e) {
		if(cerrar.enBoton) {

		juego.pantallaEjecucion = pecera;
		}
	}
	
	public void pintarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}
	
	public void escribir(MouseEvent e) {
		if (texto.enBoton) {

			if (escribiendo) {
				escribiendo = false;
			} else {
				escribiendo = true;
			}
		}
	}
	
	public void pintarNombre(Graphics g) {
		g.setFont(fuenteNombre);
		g.drawString(cadena, 240, 380);
	}
	
	public void pintarBarra(Graphics g){
		if(pecera.dineroInvertido>=0 && pecera.dineroInvertido<100) {
			barra = new Sprite(0, 100, 800, 300, 0, 0, imagenBarraVacia, true, true, "barraVacia");
			barra.pintarEnMundo(g);
		}else if(pecera.dineroInvertido>= 100 && pecera.dineroInvertido<500) {
			barra = new Sprite(0, 100, 800, 300, 0, 0, imagenBarra1, true, true, "barra1");
			barra.pintarEnMundo(g);
			pecera.dec1=true;
		}else if(pecera.dineroInvertido>= 500 && pecera.dineroInvertido<1000) {
			barra = new Sprite(0, 100, 800, 300, 0, 0, imagenBarra2, true, true, "barra2");
			barra.pintarEnMundo(g);
			pecera.dec1=true;
			pecera.dec2=true;
		}else if(pecera.dineroInvertido>= 1000 && pecera.dineroInvertido<5000) {
			barra = new Sprite(0, 100, 800, 300, 0, 0, imagenBarra3, true, true, "barra3");
			barra.pintarEnMundo(g);
			pecera.dec1=true;
			pecera.dec2=true;
			pecera.generarDinero=true;
		}else if(pecera.dineroInvertido>= 5000 && pecera.dineroInvertido<=10000) {
			barra = new Sprite(0, 100, 800, 300, 0, 0, imagenBarra4, true, true, "barra4");
			barra.pintarEnMundo(g);
			pecera.dec1=true;
			pecera.dec2=true;
			pecera.generarDinero=true;
			pecera.imagenes=true;
		}else if(pecera.dineroInvertido>= 10000 && pecera.dineroInvertido<=15000) {
			barra = new Sprite(0, 100, 800, 300, 0, 0, imagenBarra5, true, true, "barra5");
			barra.pintarEnMundo(g);
			pecera.dec1=true;
			pecera.dec2=true;
			pecera.generarDinero=true;
			pecera.imagenes=true;
			pecera.musicas=true;
		}else if(pecera.dineroInvertido>= 15000) {
			barra = new Sprite(0, 100, 800, 300, 0, 0, imagenBarra6, true, true, "barra6");
			barra.pintarEnMundo(g);
			pecera.dec1=true;
			pecera.dec2=true;
			pecera.generarDinero=true;
			pecera.imagenes=true;
			pecera.musicas=true;
		}
		
	}
	
	public void aniadirFondos() {
		if(botonAceptar.enBoton && cadena.length()>0) {
		int dineroAInvertir = Integer.parseInt(cadena);
		if(dineroAInvertir>pecera.dineroValor) {
			
		}else {
		pecera.dineroValor-=dineroAInvertir;
		dinero = pecera.dineroValor;
		pecera.dineroInvertido+=dineroAInvertir;
		
		cadena="";
		escribiendo=false;
		}
		}
	}
	
	
}
