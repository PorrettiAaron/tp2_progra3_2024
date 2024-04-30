package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import model.Arista;
import model.Grafo;

public class AgmPrim {

	private Map<String, List<Arista>> listaAdyacencias;
	private List<Arista> agm;

	public AgmPrim(Grafo grafo) {
		verificarGrafo(grafo);
		this.listaAdyacencias = grafo.getListaAdyacencias();
	}

	// Método para encontrar el AGM utilizando el algoritmo de Prim
	public List<Arista> generarAgmPrim() {
		// Inicializar las estructuras de datos
		Set<String> visitados = new HashSet<>();
		PriorityQueue<Arista> pq = new PriorityQueue<>();
		agm = new ArrayList<>();

		// Seleccionar un vértice inicial (cualquier vértice)
		String verticeInicial = listaAdyacencias.keySet().iterator().next();
		visitados.add(verticeInicial);

		// Agregar las aristas adyacentes al vértice inicial a la cola de prioridad
		for (Arista arista : listaAdyacencias.get(verticeInicial)) {
			pq.offer(arista);
		}

		// Mientras la cola de prioridad no esté vacía y no se hayan visitado todos los
		// vértices
		while (!pq.isEmpty() && visitados.size() < listaAdyacencias.size()) {
			// Obtener la arista con el peso más bajo de la cola de prioridad
			Arista arista = pq.poll();
			String destino = arista.getDestino();

			// Si el destino no ha sido visitado, agregar la arista al AGM
			if (!visitados.contains(destino)) {
				visitados.add(destino);
				agm.add(arista);

				// Agregar las aristas adyacentes al vértice recién visitado a la cola de
				// prioridad
				for (Arista aristaAdyacente : listaAdyacencias.get(destino)) {
					if (!visitados.contains(aristaAdyacente.getDestino())) {
						pq.offer(aristaAdyacente);
					}
				}
			}
		}
		return agm;
	}

	/** 
	 * Método para eliminar la arista de mayor peso del AGM
	 * y dividir en regiones 
	 * @param regiones -> cantidad de regiones que se desea
	 * dividir el arbol
	 * @return una lista de las aristas resultantes
	 */
	public List<Arista> dividirRegiones(int regiones) {
		// Identificar la arista con el mayor peso en el AGM
		if (regiones > 1) {
			Arista aristaMasPesada = null;
			for (Arista arista : agm) {
				if (aristaMasPesada == null || arista.getPeso() > aristaMasPesada.getPeso()) {
					aristaMasPesada = arista;
				}
			}

			// Eliminar la arista con el mayor peso del AGM
			if (aristaMasPesada != null) {
				agm.remove(aristaMasPesada);
			}
			regiones--;
			dividirRegiones(regiones);
		}
		return agm;
	}

	public void printAgmPrim() {
        for (Arista a : agm) {
        	System.out.println(a.toString());
        }
	}
	
	/** Validar si el grafo es conexo
	 * 
	 * @param grafo
	 */
	private void verificarGrafo(Grafo grafo) {
		Bfs bf = new Bfs(grafo);
		if (!bf.esConexo()) 
			throw new IllegalArgumentException("No se puede realizar AGM de un grafo no conexo");
	}

}
