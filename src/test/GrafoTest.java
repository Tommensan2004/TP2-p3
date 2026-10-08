package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import modelo.Grafo;

public class GrafoTest {
    private Grafo grafo;

    @Before
    public void setUp() {
        grafo = new Grafo();
        grafo.agregarProvincia("Buenos Aires", -34.6, -58.38);
        grafo.agregarProvincia("Cordoba", -31.4, -64.18);
        grafo.agregarProvincia("Santa Fe", -31.6, -60.7);
    }

    @Test
    public void agregarProvinciaTest() {
        assertEquals(3, grafo.getCantidadVertices());
        assertEquals("Buenos Aires", grafo.getProvincia(0).getNombre());
    }

    @Test
    public void agregarAristaValidaTest() {
        grafo.agregarArista(0, 1, 10.5); // Buenos Aires - Cordoba
        
        assertEquals(1, grafo.getAristas().size());
        assertEquals(1, grafo.getAristasAdyacentes(0).size());
        assertEquals(10.5, grafo.getAristas().get(0).getPeso(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarAristaVerticeInexistenteTest() {
        // Debe lanzar excepcion por indice fuera de rango
        grafo.agregarArista(0, 99, 5.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarAristaPesoNegativoTest() {
        grafo.agregarArista(0, 1, -2.5);
    }
}