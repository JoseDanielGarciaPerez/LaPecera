import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PantallaInicio implements Pantalla {

	PanelJuego juego;
	public PantallaInicio(PanelJuego juego) {
		inicializarPantalla(juego);
	}
	@Override
	public void inicializarPantalla(PanelJuego juego) {
		this.juego=juego;

	}

	@Override
	public void pintarPantalla(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarTecla(KeyEvent tecla) {
		// TODO Auto-generated method stub

	}

}
