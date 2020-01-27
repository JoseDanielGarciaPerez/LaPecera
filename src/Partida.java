import java.io.Serializable;
import java.util.ArrayList;

public class Partida implements Serializable{

	
	private int peces;
	private ArrayList<String> nombres = new ArrayList<String>();
	private ArrayList<Integer> estadisticas = new ArrayList<Integer>();
	private ArrayList<Integer> tipos = new ArrayList<Integer>();
	
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
}
