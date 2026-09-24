package modelo;

import java.util.Objects;

public class Arista implements Comparable<Arista> {
    private int origen;
    private int destino;
    private double peso; // Distancia o peso de similaridad

    public Arista(int origen, int destino, double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("El peso de la arista no puede ser negativo.");
        }
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public int compareTo(Arista otra) {
        return Double.compare(this.peso, otra.peso);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Arista arista = (Arista) obj;
        return (origen == arista.origen && destino == arista.destino) ||
               (origen == arista.destino && destino == arista.origen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.min(origen, destino), Math.max(origen, destino));
    }
}