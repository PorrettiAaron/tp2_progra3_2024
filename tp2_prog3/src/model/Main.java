package model;

import controller.Bfs;
import controller.Dfs;

import java.util.ArrayList;
import java.util.List;

import controller.AgmPrim;
import exceptions.AristaInexistenteException;
import exceptions.VerticeInexistenteException;

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
		// g.agregarVertice("H");
		System.out.println("Grafo representado con lista de adyacencias");
		g.imprimirGrafo();

		System.out.println("Prueba de grafo conexo DFS -> " + df.esConexo());
		System.out.println("Prueba de grafo conexo BFS -> " + bf.esConexo());
		AgmPrim prim = new AgmPrim(g);
		prim.generarAgmPrim();
		System.out.println("AGM Prim");
		prim.printAgmPrim();
		System.out.println("AGM Prim despues de eliminar aristas");
		prim.dividirRegiones(2);
		prim.printAgmPrim();

		System.out.println("------------------");
		ArrayList<Arista> lista = (ArrayList<Arista>) prim.generarAgmPrim();
		System.out.println("primer elemento de la lista " + lista.get(0));
		if (lista.get(0).equals(new Arista("A", "F", 5))) {
			System.out.println("Okkkkkkk");
		}
		lista.sort(null);
		for (Arista a : lista) {
			System.out.println(a.toString());
		}
		System.out.println("------------------");

		try {
			try {
				System.out.println(g.getPesoArista("H", "F"));
			} catch (VerticeInexistenteException e) {
				System.out.println(e.getMessage());
			}
		} catch (AristaInexistenteException e) {
			System.out.println(e.getMessage());
		}
		
		
		Grafo t = new Grafo();
		t.agregarArista("A", "B", 10);
		t.agregarArista("A", "E", 1);
		t.agregarArista("B", "C", 5);
		t.agregarArista("B", "D", 15);
		t.agregarArista("C", "D", 20);
		t.agregarArista("D", "E", 25);
		AgmPrim ap = new AgmPrim(t);
		List<Arista> ag = ap.generarAgmPrim();
		ag.sort(null);
		for (Arista a : ag) {
			System.out.println(a.toString());
		}
		
	}
}
