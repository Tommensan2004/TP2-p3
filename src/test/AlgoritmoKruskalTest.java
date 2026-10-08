package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import modelo.AlgoritmoKruskal;
import modelo.Arista;
import modelo.Grafo;

public class AlgoritmoKruskalTest {
    private Grafo grafo;

    @Before
    public void setUp() {
        // Grafo de 4 vertices formando un ciclo con una diagonal
        grafo = new Grafo();
        grafo.agregarProvincia("Buenos Aires", -34.6, -58.38); // ID 0
        grafo.agregarProvincia("Cordoba", -31.4, -64.18);      // ID 1
        grafo.agregarProvincia("Santa Fe", -31.6, -60.7);       // ID 2
        grafo.agregarProvincia("Mendoza", -32.8, -68.8);       // ID 3

        grafo.agregarArista(0, 1, 1.0); // BA - Cordoba
        grafo.agregarArista(1, 2, 2.0); // Cordoba - Santa Fe
        grafo.agregarArista(2, 3, 3.0); // Santa Fe - Mendoza
        grafo.agregarArista(3, 0, 4.0); // Mendoza - BA (Arista mas pesada)
        grafo.agregarArista(0, 2, 5.0); // Diagonal BA - Santa Fe
    }

    @Test
    public void calcularAGMCantidadAristasTest() {
        Grafo agm = AlgoritmoKruskal.calcularAGM(grafo);

        //tenemos 4 vértices, el AGM debe tener exactamente V - 1 = 3 aristas
        assertEquals(4, agm.getCantidadVertices());
        assertEquals(3, agm.getAristas().size());
    }

    @Test
    public void calcularAGMPesoTotalMinimoTest() {
        Grafo agm = AlgoritmoKruskal.calcularAGM(grafo);

        // el peso minimo para conectar los 4 vertices, es 6
        double pesoTotal = 0.0;
        for (Arista a : agm.getAristas()) {
            pesoTotal += a.getPeso();
        }

        assertEquals(6.0, pesoTotal, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcularAGMGrafoVacioTest() {
        Grafo vacio = new Grafo();
        AlgoritmoKruskal.calcularAGM(vacio);
    }
}