package forms;
import java.util.*;

public class GrafoProvincias {
	private ArrayList<HashSet<Integer>> provincias;
	private ArrayList<Arista> aristas;

	public GrafoProvincias() {
	    provincias = new ArrayList<HashSet<Integer>>();
	    aristas = new ArrayList<Arista>();
	    
	    inicializarProvincias();
	}
	
	
	public void inicializarProvincias() {
		for (int i = 0; i < 23; i++) {
		    HashSet<Integer> nuevaProvincia = new HashSet<>();
		    provincias.add(nuevaProvincia);
		}
	}
	
	public void agregarArista(int i, int j, int peso) {
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

		return provincias.get(i).contains(j) && provincias.get(j).contains(i);
	}
	

	// Cantidad de vertices
	public int tamano() {
		return provincias.size();
	}
	

	public Set<Integer> vecinos(int i) {
		return provincias.get(i);
	}


	public void eliminarArista(int vertice1, int vertice2) {

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

	
    public static void main(String[] args) {
    	GrafoProvincias grafo = new GrafoProvincias();
    	HashProvincia nombres_provincias = new HashProvincia();
    	
    	
    	grafo.agregarArista(1, 0, 10);
    	grafo.agregarArista(5, 10, 80);
    	
    	System.out.println(grafo.getPesoArista(1, 0));
    	
    	for (int i = 0; i < grafo.tamano(); i++) {
    		String textoAMostrar = "La provincia " + nombres_provincias.getProvincia(i); 
    		
    		
    		HashSet<Integer> provinciasVecinas = grafo.provincias.get(i);
    		
    		if(provinciasVecinas.size() == 0) {
    			textoAMostrar = textoAMostrar.concat(" no posee aristas.");
    		}
    		
    		else {
    			textoAMostrar = textoAMostrar.concat(" tiene aristas con las provincias:");
    		}
    		System.out.println(textoAMostrar);
    		
    	    
    	    for (Integer provinciaVecina : provinciasVecinas) {
    	        System.out.println("   " + nombres_provincias.getProvincia(provinciaVecina) + " con un peso de " + grafo.getPesoArista(provinciaVecina, i));
    	    }
    	}
    }
}
