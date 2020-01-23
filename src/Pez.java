import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Pez {

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
	
	protected boolean movimiento = false;
	
	Image imagenDerecha;
	Image imagenIzquierda;
	
	boolean usadoDerecha=false;
	boolean usadoIzquierda=false;

	private Pez(int posX, int posY, int ancho, int alto, int velX, int velY) {
		this.posX = posX;
		this.posY = posY;
		this.ancho = ancho;
		this.alto = alto;
		this.velX = velX;
		this.velY = velY;
	}

	public Pez(int posX, int posY, int ancho, int alto, int velX, int velY, Color color) {
		this(posX, posY, ancho, alto, velX, velY);
		pintarBuffer(color);
	}
	
	public Pez(int posX, int posY, int ancho, int alto, int velX, int velY, Image imgConstructor,
			boolean redimensionar) {
		this(posX, posY, ancho, alto, velX, velY);
	
		pintarBuffer(imgConstructor, redimensionar);
	}
	
	

	public void pintarBuffer(Image imgConstructor, boolean redimensionar) {
		buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.drawImage(redimensionar ? imgConstructor.getScaledInstance(this.ancho, this.alto, Image.SCALE_SMOOTH)
				: imgConstructor, 0, 0, null);
		g.dispose();
	}

	private void pintarBuffer(Color color) {
		buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, this.ancho, this.alto);
		g.dispose();
	}

	public void movimientoPez(PanelJuego panelJuego) {
		
			
			
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
				System.out.println("PosY: "+posY);
				System.out.println("DireccionY: "+direccionY);
				posY -=velY;
			}
			if(posY<direccionY) {
				posY+=velY;
			}
			
			if(posX == direccionX && posY == direccionY) {
				movimiento=false;
			}
			
				
		

	}

	public void pintarEnMundo(Graphics g) {

		g.drawImage(buffer, posX, posY, null);
	}

	public boolean colisiona(Sprite otro) {
		boolean colisionX = posX < otro.posX ? (posX + ancho >= otro.posX) : (otro.posX + otro.ancho >= posX);

		boolean colisionY = posY < otro.posY ? (posY + alto >= otro.posY) : (otro.posY + otro.alto >= posY);

		return colisionX && colisionY;
	}
	
	public void buscarComida(Sprite otro) {
		
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
		
		if(posX == otro.posX && posY == otro.posY) {
			movimiento=false;
		}
	}


		
	public void añadirImagenes(Image imagen1,Image imagen2) {
		this.imagenDerecha = imagen1;
		this.imagenIzquierda = imagen2;
	}
}
