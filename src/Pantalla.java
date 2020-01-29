import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * La interface Pantalla nos proporciona los metodos necesarios y globales que usaremos en todas las pantallas de nuestro juego
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
	
	public void arrastrarRaton(MouseEvent e);
	
	public void redimensionar();

	public void pulsarTecla(KeyEvent tecla);

}
