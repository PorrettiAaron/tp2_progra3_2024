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
	
	public List<Arista> getListaAgm() {
		return this.agm;
	}

	public void generarAgmPrim() {
		Set<String> visitados = new HashSet<>();
		PriorityQueue<Arista> pq = new PriorityQueue<>();
		agm = new ArrayList<>();
		String verticeInicial = listaAdyacencias.keySet().iterator().next();
		visitados.add(verticeInicial);
		for (Arista arista : listaAdyacencias.get(verticeInicial)) {
			pq.offer(arista);
		}
		while (!pq.isEmpty() && visitados.size() < listaAdyacencias.size()) {
			Arista arista = pq.poll();
			String destino = arista.getDestino();
			if (!visitados.contains(destino)) {
				visitados.add(destino);
				agm.add(arista);
				for (Arista aristaAdyacente : listaAdyacencias.get(destino)) {
					if (!visitados.contains(aristaAdyacente.getDestino())) {
						pq.offer(aristaAdyacente);
					}
				}
			}
		}
	}

	/** 
	 * Método para eliminar la arista de mayor peso del AGM
	 * y dividir en regiones 
	 * @param regiones -> cantidad de regiones que se desea
	 * dividir el arbol
	 * @return una lista de las aristas resultantes
	 */
	public List<Arista> dividirRegiones(int regiones) {
		if (regiones > listaAdyacencias.size()) {
			throw new IllegalArgumentException("Exede la cantidad maxima de regiones que es posible crear");
		}
		if (regiones > 1) {
			Arista aristaMasPesada = null;
			for (Arista arista : this.agm) {
				if (aristaMasPesada == null || arista.getPeso() > aristaMasPesada.getPeso()) {
					aristaMasPesada = arista;
				}
			}
			if (aristaMasPesada != null) {
				agm.remove(aristaMasPesada);
			}
			regiones--;
			dividirRegiones(regiones);
		}
		return agm;
	}

	/**
	 * SOLO PARA PRUEBAS DE CONSOLA
	 */
	public void printAgmPrim() {
        for (Arista a : this.agm) {
        	System.out.println(a.toString());
        }
	}
	
	private void verificarGrafo(Grafo grafo) {
		Bfs bf = new Bfs(grafo);
		if (!bf.esConexo()) 
			throw new IllegalArgumentException("No se puede realizar AGM de un grafo no conexo");
	}
	
}
