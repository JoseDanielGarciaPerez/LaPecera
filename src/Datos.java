import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Datos {
static File archivo = new File("./Partidas/partida.obj");
	
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
	
	public static Partida cargarDatos() {
		ObjectInputStream ois;
		Partida partida=null;
		try {
			if(!archivo.exists()) {
				
			}else {
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
