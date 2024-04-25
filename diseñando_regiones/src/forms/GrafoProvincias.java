package forms;
import java.util.*;

public class GrafoProvincias {
	private ArrayList<HashSet<Integer>> provincias;
	private ArrayList<Arista> aristas;

	public GrafoProvincias() {
	    provincias = new ArrayList<HashSet<Integer>>();
	    aristas = new ArrayList<Arista>();
	}
	
	
	public void agregarArista(int i, int j, int peso) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);

		if (!existeArista(i, j)) {
			provincias.get(i).add(j);
			provincias.get(j).add(i);
		}
		
		Arista nuevo = new Arista(i, j, peso);
		if (!aristas.contains(nuevo)) {
			aristas.add(nuevo);
		}
	}

	

	// Informa si existe la arista especificada
	public boolean existeArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarDistintos(i, j);

		return provincias.get(i).contains(j) && provincias.get(j).contains(i);
	}
	

	// Cantidad de vertices
	public int tamano() {
		return provincias.size();
	}
	

	// Vecinos de un vertice
	public Set<Integer> vecinos(int i) {
		verificarVertice(i);
		return provincias.get(i);
	}


	public void eliminarArista(int vertice1, int vertice2) {
		verificarVertice(vertice1);
		verificarVertice(vertice2);
		verificarDistintos(vertice1, vertice2);

		provincias.get(vertice1).remove(vertice2);
		provincias.get(vertice2).remove(vertice1);

		for (int i = 0; i < aristas.size(); i++) {
			if (aristas.get(i).getVert1() == vertice1 && aristas.get(i).getVert2() == vertice2) {
				aristas.remove(i);
				break;
			}
		}
	}

	
	public double getPesoArista(int vertice1, int vertice2) {
		for (int i = 0; i < aristas.size(); i++) {
			if ((aristas.get(i).getVert1() == vertice1 && aristas.get(i).getVert2() == vertice2)
					|| (aristas.get(i).getVert1() == vertice2 && aristas.get(i).getVert2() == vertice1)) {
				return aristas.get(i).getPeso();
			}
		}
		throw new RuntimeException("No se encontro un peso entre " + vertice1 + " y " + vertice2);
	}

	public String toString() {
		StringBuilder st = new StringBuilder();
		for (int i = 0; i < aristas.size(); i++) {
			st.append("vertices: " + aristas.get(i).getVert1() + ", " + aristas.get(i).getVert2() + ", peso = "
					+ aristas.get(i).getPeso() + "\n");
		}
		return st.toString();
	}
	
	
	// Verifica que sea un vertice valido
	public void verificarVertice(int i) {
		if (i < 0)
			throw new IllegalArgumentException("El vertice no puede ser negativo: " + i);

		if (i >= provincias.size())
			throw new IllegalArgumentException("Los vertices deben estar entre 0 y |V|-1: " + i);
	}

	// Verifica que i y j sean distintos
	public void verificarDistintos(int i, int j) {
		if (i == j)
			throw new IllegalArgumentException("No se permiten loops: (" + i + ", " + j + ")");
	}


    
    

    public static void main(String[] args) {
    	GrafoProvincias grafo = new GrafoProvincias();

    }
}
