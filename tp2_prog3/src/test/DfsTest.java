package test;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.Dfs;
import model.Grafo;

public class DfsTest {
	
	@Test
	public void esConexoBfsTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.agregarArista("A", "C", 20);
		g.agregarArista("C", "B", 10);
		g.agregarArista("B", "D", 10);
		Dfs df = new Dfs(g);
		assertTrue(df.esConexo());
	}
	
	@Test
	public void noEsConexoBfsTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.agregarArista("C", "D", 10);
		Dfs df = new Dfs(g);
		assertFalse(df.esConexo());
	}
}
