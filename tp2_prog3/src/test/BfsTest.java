package test;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.Bfs;
import model.Grafo;

public class BfsTest {

	@Test
	public void esConexoBfsTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.agregarArista("A", "C", 20);
		g.agregarArista("C", "B", 10);
		g.agregarArista("B", "D", 10);
		Bfs bf = new Bfs(g);
		assertTrue(bf.esConexo());
	}
	
	@Test
	public void noEsConexoBfsTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.agregarArista("C", "D", 10);
		Bfs bf = new Bfs(g);
		assertFalse(bf.esConexo());
	}

}
