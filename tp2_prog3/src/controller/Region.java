package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import model.Arista;
import model.Grafo;

public class Region {

	private Grafo grafoRegiones;
	List<Set<String>> regiones;
	
	public Region(Grafo grafo, List<Arista> listaAristas) {
		grafoRegiones = new Grafo();
		for (Map.Entry<String, List<Arista>> entry : grafo.getListaAdyacencias().entrySet()) {
			grafoRegiones.agregarVertice(entry.getKey());
		}
		for (Arista arista : listaAristas) {
			this.grafoRegiones.agregarArista(arista.getOrigen(), arista.getDestino(), arista.getPeso());
		}
	}
	
    private Set<String> obtenerComponentesConexos(String vertice, Set<String> visitados) {
        Set<String> componenteConexo = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.offer(vertice);
        componenteConexo.add(vertice);
        visitados.add(vertice);
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (Arista arista : grafoRegiones.getAristas(actual)) {
                String destino = arista.getDestino();
                if (!visitados.contains(destino)) {
                    cola.offer(destino);
                    visitados.add(destino);
                    componenteConexo.add(destino);
                }
            }
        }
        return componenteConexo;
    }
    
    public List<Set<String>> generarRegiones() {
    	regiones = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        for (String vertice : grafoRegiones.getVertices()) {
            if (!visitados.contains(vertice)) {
                Set<String> componenteConexo = obtenerComponentesConexos(vertice, visitados);
                regiones.add(componenteConexo);
            }
        }
        return regiones;
    }
    

}
