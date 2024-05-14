package controller;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import model.Arista;
import model.Grafo;

public class Bfs {
	
	private Map<String, List<Arista>> listaAdyacencias;
	
	public Bfs(Grafo grafo) {
		this.listaAdyacencias = grafo.getListaAdyacencias();
	}
	
	/**
	 * Método para verificar si el grafo es conexo utilizando BFS
	 * @return true si es conexo
	 * 			false si no es conexo
	 */
    public boolean esConexo() {
        if (listaAdyacencias.isEmpty()) {
            return false; 
        }
        Set<String> visitados = new HashSet<>();
        String verticeInicial = listaAdyacencias.keySet().iterator().next();
        bfs(verticeInicial, visitados);
        return visitados.size() == listaAdyacencias.size();
    }

    /**
     * Método BFS para recorrer el grafo
     * @param vertice
     * @param visitados
     */
    private void bfs(String vertice, Set<String> visitados) {
        Queue<String> cola = new LinkedList<>();
        cola.offer(vertice);
        visitados.add(vertice);
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (Arista arista : listaAdyacencias.get(actual)) {
                if (!visitados.contains(arista.getDestino())) {
                    visitados.add(arista.getDestino());
                    cola.offer(arista.getDestino());
                }
            }
        }
    }

}
