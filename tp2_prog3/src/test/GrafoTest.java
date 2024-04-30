package test;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.AristaInexistenteException;
import exceptions.VerticeInexistenteException;
import model.Grafo;

public class GrafoTest {

	@Test(expected = IllegalArgumentException.class)
	public void verticeOrigenVacioTest() {
		Grafo g = new Grafo();
		g.agregarArista("", "B", 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void verticeDestinoVacioTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "", 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void verticeOrigenNullTest() {
		Grafo g = new Grafo();
		g.agregarArista(null, "B", 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void verticeDestinoNullTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", null, 10);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void verticesIgualesTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "A", 10);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void pesoCeroTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 0);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void pesoNegativoTest() {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", -10);
	}
	
	@Test(expected = AristaInexistenteException.class)
	public void aristaInexistenteTest() throws AristaInexistenteException, VerticeInexistenteException {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.agregarArista("B", "C", 20);
		g.getPesoArista("A", "C");
	}
	
	@Test(expected = VerticeInexistenteException.class) 
	public void verticeOrigenInexistenteTest() throws AristaInexistenteException, VerticeInexistenteException {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.getPesoArista("D", "B");
	}
	
	@Test(expected = VerticeInexistenteException.class) 
	public void verticeDestinoInexistenteTest() throws AristaInexistenteException, VerticeInexistenteException {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.getPesoArista("A", "C");
	}
	
	
	@Test
	public void obtenerPesoAristaTest() throws AristaInexistenteException, VerticeInexistenteException {
		Grafo g = new Grafo();
		g.agregarArista("A", "B", 10);
		g.agregarArista("B", "C", 20);
		assertEquals(g.getPesoArista("A", "B"), 10);
	}
	
	
	
	
}
