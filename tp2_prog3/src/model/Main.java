package model;

import controller.Bfs;
import controller.Dfs;
import controller.Region;
import controller.AgmPrim;


/**
 * CLASE SOLO PARA PUEBAS DE CONSOLA
 */
public class Main {

	public static void main(String args[]) {
		Grafo g = new Grafo();
		Dfs df = new Dfs(g);
		Bfs bf = new Bfs(g);

		g.agregarArista("A", "D", 20);
		g.agregarArista("A", "F", 5);
		g.agregarArista("A", "G", 10);
		g.agregarArista("F", "G", 15);
		g.agregarArista("F", "D", 10);
		g.agregarArista("D", "C", 15);
		g.agregarArista("C", "E", 10);
		g.agregarArista("C", "B", 10);
		g.agregarArista("E", "B", 15);
		//g.agregarVertice("H"); 
		System.out.println("Grafo representado con lista de adyacencias");
		g.imprimirGrafo();

		System.out.println("Prueba de grafo conexo DFS -> " + df.esConexo());
		System.out.println("Prueba de grafo conexo BFS -> " + bf.esConexo());
		AgmPrim prim = new AgmPrim(g);
		prim.generarAgmPrim();
		System.out.println("Arbol generador minimo");
		prim.printAgmPrim();
		System.out.println("Division de regiones por medio de eliminar aristas");
		prim.dividirRegiones(7);
		prim.printAgmPrim();
		
		Region r = new Region(g, prim.getListaAgm());
		r.imprimirRegiones(r.generarRegiones());
		
		g.imprimirGrafo();
	}
}
