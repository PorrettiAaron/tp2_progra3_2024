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
		validarVertice(vertice);
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
		if (getAristaExistente(origen, destino) == null) {
			listaAdyacencias.get(origen).add(new Arista(origen, destino, peso));
			listaAdyacencias.get(destino).add(new Arista(destino, origen, peso));
		} else {
			modificarPesoArista(origen, destino, peso);
			modificarPesoArista(destino, origen, peso);
		}

	}

	private void modificarPesoArista(String origen, String destino, int peso) {
		List<Arista> listaAristas = listaAdyacencias.get(origen);
		for (Arista arista : listaAristas) {
			if (arista.getDestino().equals(destino)) {
				arista.setPeso(peso);
			}
		}
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

	public void eliminarAristaEntreVertices(String origen, String destino) throws AristaInexistenteException {
		if (getAristaExistente(origen, destino) == null)
			throw new AristaInexistenteException("No existe arista entre " + origen + " y " + destino);
		eliminarArista(getAristaExistente(origen, destino));
	}

	public Map<String, String> imprimirGrafo() {
		Map<String, String> lista = new HashMap<String, String>();
		String linea;
		for (Map.Entry<String, List<Arista>> entry : listaAdyacencias.entrySet()) {
			String vertice = entry.getKey();
			linea = "";
			for (Arista arista : entry.getValue()) {
				linea += arista.getDestino() + ":" + arista.getPeso() + ", ";
			}
			lista.put(vertice, linea);
		}
		return lista;
	}

	public int getPesoArista(String origen, String destino) throws AristaInexistenteException, VerticeInexistenteException {
		validarVertice(origen);
		validarVertice(destino);
		validarVerticeExistente(origen);
		validarVerticeExistente(destino);
		if (getAristaExistente(origen, destino) == null)
			throw new AristaInexistenteException("No existe arista entre " + origen + " y " + destino);
		return getAristaExistente(origen, destino).getPeso();
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

	private void validarVerticeExistente(String vertice) throws VerticeInexistenteException {
		if (!listaAdyacencias.containsKey(vertice)) {
			throw new VerticeInexistenteException("El vertice no existe en el grafo");
		}
	}
	
	private void validarVerticesDistintos(String origen, String destino) {
		if (origen.equals(destino))
			throw new IllegalArgumentException("El origen y el destino no pueden ser iguales");
	}

	private void validarPesoPositivo(int peso) {
		if (peso <= 0)
			throw new IllegalArgumentException("El peso entre vertices debe ser positivo");
	}

	private Arista getAristaExistente(String origen, String destino) {
		List<Arista> listaAristas = listaAdyacencias.get(origen);
		for (Arista arista : listaAristas) {
			if (arista.getDestino().equals(destino)) {
				return arista;
			}
		}
		return null;
	}
}
