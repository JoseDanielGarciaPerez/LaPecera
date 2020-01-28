import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class PantallaInicio implements Pantalla {

	PanelJuego juego;
	VentanaPrincipal ventana;
	public PantallaInicio(PanelJuego juego,VentanaPrincipal ventana) {
		this.ventana=ventana;
		inicializarPantalla(juego);
	}
	@Override
	public void inicializarPantalla(PanelJuego juego) {
		this.juego=juego;

	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, juego.getWidth(),juego.getHeight());

	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			juego.pantallaEjecucion = new PantallaJuego(juego,ventana,0);
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

}
