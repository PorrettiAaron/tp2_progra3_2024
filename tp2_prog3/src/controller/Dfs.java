package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Arista;
import model.Grafo;

public class Dfs {
	
	private Map<String, List<Arista>> listaAdyacencias;
	
	public Dfs(Grafo grafo) {
		this.listaAdyacencias = grafo.getListaAdyacencias();
	}
	
    /**
     * Metodos para verificar si el grafo es conexo utilizando DFS
     * @return
     */
    public boolean esConexo() {
        // Verificar si el grafo está vacío
        if (listaAdyacencias.isEmpty()) {
            return false;
        }

        Set<String> visitados = new HashSet<>();
        String verticeInicial = listaAdyacencias.keySet().iterator().next();
        dfs(verticeInicial, visitados);
        return visitados.size() == listaAdyacencias.size();
    }

    /**
     * Metodo DFS para recorrer el grafo
     * @param vertice
     * @param visitados
     */
    private void dfs(String vertice, Set<String> visitados) {
        // Marcar el vértice como visitado
        visitados.add(vertice);

        // Recorrer las aristas adyacentes al vértice
        for (Arista arista : listaAdyacencias.get(vertice)) {
            String destino = arista.getDestino();
            // Si el destino no ha sido visitado, continuar con el DFS
            if (!visitados.contains(destino)) {
                dfs(destino, visitados);
            }
        }
    }
}
