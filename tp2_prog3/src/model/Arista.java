package model;

import java.io.Serializable;

public class Arista implements Comparable<Arista>, Serializable {
	private static final long serialVersionUID = 1L;
	private String origen;
    private String destino;
    private int peso;


    public Arista(String origen, String destino, int peso) {
        this.origen = origen;
    	this.destino = destino;
        this.peso = peso;
    }

	public String getDestino() {
		return destino;
	}

	public int getPeso() {
		return peso;
	}
	
	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Arista getArista(String destino) {
		return this;
	}

	public String getOrigen() {
		return origen;
	}
	
    @Override
    public int compareTo(Arista otraArista) {
        return Integer.compare(this.peso, otraArista.peso);
    }
	
    public boolean equals(Arista otraArista) {
    	return (this.origen.equals(otraArista.getOrigen()) &&
    			this.destino.equals(otraArista.getDestino()));
    }
    
    @Override
    public String toString() {
    	return (this.origen + ", " + this.destino + " -> " + this.peso);
    }
    
  
 }
