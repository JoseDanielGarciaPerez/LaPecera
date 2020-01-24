import java.io.Serializable;
import java.util.ArrayList;

public class Partida implements Serializable{

	
	private int peces;
	private ArrayList<String> nombres = new ArrayList<String>();
//	private ArrayList<String> estadisticas = new ArrayList<String>();
	
	
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
//	public ArrayList<String> getEstadisticas() {
//		return estadisticas;
//	}
//	public void setEstadisticas(ArrayList<String> estadisticas) {
//		this.estadisticas = estadisticas;
//	}
}
