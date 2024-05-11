package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.AristaInexistenteException;
import exceptions.VerticeInexistenteException;

public class Grafo {
	private Map<String, List<Arista>> listaAdyacencias;

	public Grafo() {
		listaAdyacencias = new HashMap<>();
	}

	public void agregarVertice(String vertice) {
		if (!listaAdyacencias.containsKey(vertice)) {
			listaAdyacencias.put(vertice, new ArrayList<>());
		}
	}

	public List<Arista> getAristas(String vertice) {
		return listaAdyacencias.get(vertice);
	}
	
	public Set<String> getVertices() {
		return listaAdyacencias.keySet();
	}
	
	public void agregarArista(String origen, String destino, int peso) {
		validarVertice(origen);
		validarVertice(destino);
		validarVerticesDistintos(origen, destino);
		validarPesoPositivo(peso);
		agregarVertice(origen);
		agregarVertice(destino);
		listaAdyacencias.get(origen).add(new Arista(origen, destino, peso));
		listaAdyacencias.get(destino).add(new Arista(destino, origen, peso));

	}
	
	public List<Arista> obtenerTodasAristas() {
	    List<Arista> todasAristas = new ArrayList<>();
	    for (List<Arista> aristas : listaAdyacencias.values()) {
	    	
	        todasAristas.addAll(aristas);
	    }
	    return todasAristas;
	}

	public void eliminarArista(Arista arista) {
		listaAdyacencias.get(arista.getOrigen()).remove(arista);
		listaAdyacencias.get(arista.getDestino()).remove(arista);
	}
	
	public void eliminarAristaEntreVertices(String origen, String destino){
		List<Arista> aristasOrigen = listaAdyacencias.get(origen);

	    for (Arista arista : aristasOrigen) {
	        if (arista.getDestino().equals(destino)) {
	        	eliminarArista(arista);
	            break;
	        }
	    }
	}
	
	/**
	 * Metodo para imprimir el grafo como lista de adyacencia PRUEBAS DE CONSOLA
	 */
	public void imprimirGrafo() {
		for (Map.Entry<String, List<Arista>> entry : listaAdyacencias.entrySet()) {
			String vertice = entry.getKey();
			System.out.print("Vértice : " + vertice + " -> ");
			for (Arista arista : entry.getValue()) {
				System.out.print(arista.getDestino() + ":" + arista.getPeso() + ", ");
			}
			System.out.println();
		}
	}

	public void cleanGrafo() {
		for (Map.Entry<String, List<Arista>> entry : listaAdyacencias.entrySet()) {
			entry.getValue().clear();
		}
	}
	
	public int getPesoArista(String origen, String destino)
			throws AristaInexistenteException, VerticeInexistenteException {
		validarVerticeExistente(origen);
		validarVerticeExistente(destino);
		List<Arista> listaAristas = listaAdyacencias.get(origen);
		for (Arista arista : listaAristas) {
			if (arista.getDestino().equals(destino)) {
				return arista.getPeso();
			}
		}
		throw new AristaInexistenteException("No existe arista entre " + origen + " y " + destino);
	}

	public Map<String, List<Arista>> getListaAdyacencias() {
		return listaAdyacencias;
	}
	
	private void validarVertice(String vertice) {
		if (vertice == null)
			throw new IllegalArgumentException("El Vertice no puede se NULL");
		if (vertice.isEmpty())
			throw new IllegalArgumentException("El Vertice no puede estar vacio");
	}

	private void validarVerticesDistintos(String origen, String destino) {
		if (origen.equals(destino))
			throw new IllegalArgumentException("El origen y el destino no pueden ser iguales");
	}

	private void validarPesoPositivo(int peso) {
		if (peso <= 0)
			throw new IllegalArgumentException("El peso entre vertices debe ser positivo");
	}

	private void validarVerticeExistente(String vertice) throws VerticeInexistenteException {
		if (!listaAdyacencias.containsKey(vertice))
			throw new VerticeInexistenteException("El Vertice no existe en el grafo");
	}

}
