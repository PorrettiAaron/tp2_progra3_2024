package forms;
import java.util.*;

public class HashProvincia {
	private Map<Integer, String> provincias;

	public HashProvincia() {
		provincias = new HashMap<Integer, String>();
        
		provincias.put(0, "Ciudad de Buenos Aires");
        provincias.put(1, "Buenos Aires");
        provincias.put(2, "Cordoba");
        provincias.put(3, "Santa Fe");
        provincias.put(4, "Entre Rios");
        provincias.put(5, "Mendoza");
        provincias.put(6, "Tucuman");
        provincias.put(7, "Neuquen");
        provincias.put(8, "Chubut");
        provincias.put(9, "Salta");
        provincias.put(10, "San Juan");
        provincias.put(11, "San Luis");
        provincias.put(12, "La Pampa");
        provincias.put(13, "Formosa");
        provincias.put(14, "Chaco");
        provincias.put(15, "Corrientes");
        provincias.put(16, "Misiones");
        provincias.put(17, "Santiago del Estero");
        provincias.put(18, "Catamarca");
        provincias.put(19, "La Rioja");
        provincias.put(20, "Jujuy");
        provincias.put(21, "Tierra del Fuego");
        provincias.put(22, "Santa Cruz");
	}
	
	public String getProvincia(int i) {
		return provincias.get(i);
	}

}
