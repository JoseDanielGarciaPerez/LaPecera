import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Pez implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nombre;
	int salud;
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
	protected final int MOVERSE = 8;
	
	protected boolean comida = false;
	protected boolean haComido=false;
	protected boolean movimiento = false;
	protected boolean focuseado = false;
	Image imagenDerecha;
	Image imagenIzquierda;
	Image imagenMuerto;
	
	double referenciaTiempo = 0;
	double tiempoTranscurrido =0;
	boolean usadoDerecha=false;
	boolean usadoIzquierda=false;
	boolean usadoMuerto = false;
	
	final Font fuentePeces = new Font("", Font.BOLD, 18);
	
	private Pez(int posX, int posY, int ancho, int alto, int velX, int velY,String nombre,int salud,int tipo) {
		this.posX = posX;
		this.posY = posY;
		this.ancho = ancho;
		this.alto = alto;
		this.velX = velX;
		this.velY = velY;
		this.nombre=nombre;
		this.salud=salud;
		this.tipo=tipo;
		
		
	}

	
	
	public Pez(int posX, int posY, int ancho, int alto, int velX, int velY, Image imgConstructor,
			boolean redimensionar,String nombre,int salud,int tipo) {
		this(posX, posY, ancho, alto, velX, velY,nombre,salud,tipo);
	
		referenciaTiempo=System.nanoTime();
		pintarBuffer(imgConstructor, redimensionar);
	}
	
	

	public void pintarBuffer(Image imgConstructor, boolean redimensionar) {
		buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.drawImage(redimensionar ? imgConstructor.getScaledInstance(this.ancho, this.alto, Image.SCALE_SMOOTH)
				: imgConstructor, 0, 0, null);
		g.dispose();
	}



	public void movimientoPez(PanelJuego panelJuego) {
		
			
			if(usadoMuerto==false) {
			if(movimiento==false) {
				movimiento=true;
				
			direccionX = (int) (Math.random() * ((panelJuego.getWidth()-this.ancho)+1));
			
			direccionY = (int) (Math.random() * ((panelJuego.getHeight()-this.alto)+1));
			
			}
			
			if(posX>direccionX) {
				posX -=velX;
				if(usadoIzquierda==false) {
				pintarBuffer(imagenIzquierda, true);
				usadoIzquierda=true;
				usadoDerecha=false;
				}
				
			}
			if(posX<direccionX) {
				posX+=velX;
				if(usadoDerecha==false) {
				pintarBuffer(imagenDerecha, true);
				usadoDerecha=true;
				usadoIzquierda=false;
				}
				
				
			}
			if(posY>direccionY) {
				
				posY -=velY;
			}
			if(posY<direccionY) {
				posY+=velY;
			}
			
			if(posX == direccionX && posY == direccionY) {
				movimiento=false;
			}
			
				
			}else {
				if(posY>0)
				posY-=1;
			}

	}

	public void pintarEnMundo(Graphics g) {
		int posicionX =0;
		g.drawImage(buffer, posX, posY, null);
		if(focuseado) {
		g.setColor(Color.red);
		for (int i = 0; i < salud; i++) {
		int posicionFinal = (posX+10)+posicionX;
		
		posicionX +=10;
		
		g.fillRect(posicionFinal, posY+alto+5, 20, 10);
		}
		
		g.setColor(Color.DARK_GRAY);
		g.setFont(fuentePeces);
		g.drawString(nombre, posX+20, posY);
		}
	}

	public boolean colisiona(Sprite otro) {
		boolean colisionX = posX < otro.posX ? (posX + ancho >= otro.posX) : (otro.posX + otro.ancho >= posX);

		boolean colisionY = posY < otro.posY ? (posY + alto >= otro.posY) : (otro.posY + otro.alto >= posY);

		return colisionX && colisionY;
	}
	
	public void buscarComida(Sprite otro) {
		if(usadoMuerto==false) {
		if(posX>otro.posX) {
			posX -=velX;
			if(usadoIzquierda==false) {
			pintarBuffer(imagenIzquierda, true);
			usadoIzquierda=true;
			usadoDerecha=false;
			}
			
		}
		if(posX<otro.posX) {
			posX+=velX;
			if(usadoDerecha==false) {
			pintarBuffer(imagenDerecha, true);
			usadoDerecha=true;
			usadoIzquierda=false;
			}
			
			
		}
		if(posY+alto>otro.posY) {
			posY -=velY;
		}
		if(posY+alto<otro.posY) {
			posY+=velY;
		}
		
		
		}
			
		
	}
	
	
	public void hambre() {
		tiempoTranscurrido=System.nanoTime();
		
		if(tiempoTranscurrido-referenciaTiempo >= 3e+11  && salud>=1) {
			salud-=1;
			
			referenciaTiempo=System.nanoTime();
			
		}
		
		if(salud==0) {
			if(usadoMuerto==false) {
				pintarBuffer(imagenMuerto, true);
				usadoMuerto=true;
			}
		}
	}


		
	public void añadirImagenes(Image imagen1,Image imagen2,Image imagen3) {
		this.imagenDerecha = imagen1;
		this.imagenIzquierda = imagen2;
		this.imagenMuerto=imagen3;
	}
}
