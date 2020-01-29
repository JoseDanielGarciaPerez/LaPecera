import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * La clase Datos es una clase encarga de tener dos metodos estaticos, uno que
 * guarda la partida, guardando un objeto de tipo partida en un fichero, y otro
 * metodo para cargar desde un fichero un objeto de tipo Partida
 * 
 * @author José Daniel
 *
 */
public class Datos {
	//ruta donde se guarda el fichero
	static File archivo = new File("./Partidas/partida.obj");
/**
 * Metodo muy simple para escribir un objeto en el directorio dado
 * @param partida recibe un objeto de tipo partida
 */
	public static void guardarDatos(Partida partida) {
		try {

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./Partidas/partida.obj"));
			oos.writeObject(partida);
			oos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * Metodo muy simple para cargar desde un fichero datos y introducirlos en un objeto de tipo partida el cual es devuelto
 * @return
 */
	public static Partida cargarDatos() {
		ObjectInputStream ois;
		Partida partida = null;
		try {
			if (!archivo.exists()) {

			} else {
				ois = new ObjectInputStream(new FileInputStream("./Partidas/partida.obj"));

				partida = (Partida) ois.readObject();

				ois.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return partida;

	}
}
