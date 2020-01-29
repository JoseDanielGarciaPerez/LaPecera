import java.io.Serializable;
import java.util.ArrayList;

/**
 * La clase partida, sirve para crear objetos con todas las variables necesarias
 * para jugar una partida al juego, los objetos de la clase partida sirven para
 * almacenar en sus variables los datos de la partida y guardarlos en un fichero.
 * 
 * @author Daniel
 *
 */
public class Partida implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int peces;
	private ArrayList<String> nombres = new ArrayList<String>();
	private ArrayList<Integer> estadisticas = new ArrayList<Integer>();
	private ArrayList<Integer> tipos = new ArrayList<Integer>();
	private int dinero = 0;
	private int dineroInvertido = 0;
	private boolean dec1 = false;
	private boolean dec2 = false;
	private boolean generarDinero = false;
	private boolean ponerImagenes = false;
	private boolean ponerMusica = false;
	private String rutaFondo = "./Imagenes/fondo.jpg";
	private String rutaMusica = "./Musica/aquario.wav";

	public int getPeces() {
		return peces;
	}

	public void setPeces(int peces) {
		this.peces = peces;
	}

	public ArrayList<String> getNombres() {
		return nombres;
	}

	public void setNombres(String nombre) {
		nombres.add(nombre);
	}

	public ArrayList<Integer> getEstadisticas() {
		return estadisticas;
	}

	public void setEstadisticas(int salud) {
		estadisticas.add(salud);
	}

	public void setNombres(ArrayList<String> nombres) {
		this.nombres = nombres;
	}

	public void setEstadisticas(ArrayList<Integer> estadisticas) {
		this.estadisticas = estadisticas;
	}

	public ArrayList<Integer> getTipos() {
		return tipos;
	}

	public void setTipos(int tipo) {
		tipos.add(tipo);
	}

	public int getDinero() {
		return dinero;
	}

	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	public void setTipos(ArrayList<Integer> tipos) {
		this.tipos = tipos;
	}

	public int getDineroInvertido() {
		return dineroInvertido;
	}

	public void setDineroInvertido(int dineroInvertido) {
		this.dineroInvertido = dineroInvertido;
	}

	public boolean isDec1() {
		return dec1;
	}

	public void setDec1(boolean dec1) {
		this.dec1 = dec1;
	}

	public boolean isDec2() {
		return dec2;
	}

	public void setDec2(boolean dec2) {
		this.dec2 = dec2;
	}

	public boolean isGenerarDinero() {
		return generarDinero;
	}

	public void setGenerarDinero(boolean generarDinero) {
		this.generarDinero = generarDinero;
	}

	public boolean isPonerImagenes() {
		return ponerImagenes;
	}

	public void setPonerImagenes(boolean ponerImagenes) {
		this.ponerImagenes = ponerImagenes;
	}

	public String getRutaFondo() {
		return rutaFondo;
	}

	public void setRutaFondo(String rutaFondo) {
		this.rutaFondo = rutaFondo;
	}

	public String getRutaMusica() {
		return rutaMusica;
	}

	public void setRutaMusica(String rutaMusica) {
		this.rutaMusica = rutaMusica;
	}

	public boolean isPonerMusica() {
		return ponerMusica;
	}

	public void setPonerMusica(boolean ponerMusica) {
		this.ponerMusica = ponerMusica;
	}
}
