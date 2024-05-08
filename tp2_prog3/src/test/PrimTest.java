package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import controller.AgmPrim;
import model.Arista;
import model.Grafo;

public class PrimTest {

	@Test(expected = IllegalArgumentException.class)
	public void agmPrimGrafoNoConexoTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.agregarVertice("C");
		new AgmPrim(g);
	}

	
	@Test
	public void agmPrimGrafoConexoTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		new AgmPrim(g);
	}
	
	@Test
	public void agmPrimGrafoTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.agregarArista("A", "E", 1);
		g.agregarArista("B", "C", 5);
		g.agregarArista("B", "D", 15);
		g.agregarArista("C", "D", 20);
		g.agregarArista("D", "E", 25);
		AgmPrim ap = new AgmPrim(g);
		ap.generarAgmPrim();
		List<Arista> agm = ap.getListaAgm();
		agm.sort(null);
		assertTrue(agm.get(0).equals(new Arista("A", "E", 1)));
		assertTrue(agm.get(1).equals(new Arista("B", "C", 5)));
		assertTrue(agm.get(2).equals(new Arista("A", "B", 10)));
		assertTrue(agm.get(3).equals(new Arista("B", "D", 15)));
	}
	
}
