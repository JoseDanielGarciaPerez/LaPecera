import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * 
 * @author José Daniel García Pérez
 *
 */
public interface Pantalla {

	public void inicializarPantalla(PanelJuego juego);

	public void pintarPantalla(Graphics g);

	public void ejecutarFrame();

	// listeners
	public void pulsarRaton(MouseEvent e);

	public void moverRaton(MouseEvent e);

	public void redimensionar();

	public void pulsarTecla(KeyEvent tecla);

}
