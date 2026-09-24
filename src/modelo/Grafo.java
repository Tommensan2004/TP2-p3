package modelo;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private List<Provincia> provincias;
    private List<List<Arista>> adyacencia;
    private List<Arista> aristas;

    public Grafo() {
        this.provincias = new ArrayList<>();
        this.adyacencia = new ArrayList<>();
        this.aristas = new ArrayList<>();
    }

    /**
     * Agrega una nueva provincia asignándole un ID equivalente a su posición en la lista.
     */
    public int agregarProvincia(String nombre, double latitud, double longitud) {
        int id = provincias.size();
        Provincia provincia = new Provincia(id, nombre, latitud, longitud);
        provincias.add(provincia);
        adyacencia.add(new ArrayList<>());
        return id;
    }

    /**
     * Agrega una arista no dirigida entre los vértices origen y destino.
     */
    public void agregarArista(int origen, int destino, double peso) {
        validarVertice(origen);
        validarVertice(destino);

        Arista arista = new Arista(origen, destino, peso);
        if (!aristas.contains(arista)) {
            aristas.add(arista);
            adyacencia.get(origen).add(arista);
            adyacencia.get(destino).add(arista);
        }
    }

    private void validarVertice(int id) {
        if (id < 0 || id >= provincias.size()) {
            throw new IllegalArgumentException("El vértice con ID " + id + " no existe.");
        }
    }

    public int getCantidadVertices() {
        return provincias.size();
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public Provincia getProvincia(int id) {
        validarVertice(id);
        return provincias.get(id);
    }

    public List<Arista> getAristasAdyacentes(int idProvincia) {
        validarVertice(idProvincia);
        return adyacencia.get(idProvincia);
    }

    public List<Arista> getAristas() {
        return aristas;
    }
}