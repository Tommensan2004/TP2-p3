package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import modelo.AlgoritmoKruskal;
import modelo.Grafo;
import modelo.Regionalizador;

public class RegionalizadorTest {
    private Grafo agm;

    @Before
    public void setUp() {
        Grafo grafoOriginal = new Grafo();
        grafoOriginal.agregarProvincia("P1", 0, 0); // ID 0
        grafoOriginal.agregarProvincia("P2", 0, 0); // ID 1
        grafoOriginal.agregarProvincia("P3", 0, 0); // ID 2
        grafoOriginal.agregarProvincia("P4", 0, 0); // ID 3
        
        grafoOriginal.agregarArista(0, 1, 1.0);
        grafoOriginal.agregarArista(1, 2, 10.0); // Arista mas pesada
        grafoOriginal.agregarArista(2, 3, 2.0);
        
        // Grafo: 0 <--> 1 = 1.0 ; 1 <--> 2 = 10.0 ; 2 <--> 3 = 3.0

        agm = AlgoritmoKruskal.calcularAGM(grafoOriginal);
    }

    @Test
    public void generarDosRegionesTest() {
        Regionalizador.generarRegiones(agm, 2);

        int regionGrupo1 = agm.getProvincia(0).getRegion();
        int regionGrupo2 = agm.getProvincia(2).getRegion();

        assertEquals(regionGrupo1, agm.getProvincia(1).getRegion());
        assertEquals(regionGrupo2, agm.getProvincia(3).getRegion());
        assertNotEquals(regionGrupo1, regionGrupo2);
    }

    @Test
    public void generarKIgualAVerticesTest() {
        // Para k = 4, cada provincia queda aislada en su propia región
        Regionalizador.generarRegiones(agm, 4);

        int r0 = agm.getProvincia(0).getRegion();
        int r1 = agm.getProvincia(1).getRegion();
        int r2 = agm.getProvincia(2).getRegion();
        int r3 = agm.getProvincia(3).getRegion();

        assertNotEquals(r0, r1);
        assertNotEquals(r1, r2);
        assertNotEquals(r2, r3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void kInvalidoMenorAUnoTest() {
        Regionalizador.generarRegiones(agm, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void kInvalidoMayorAVerticesTest() {
        Regionalizador.generarRegiones(agm, 10);
    }
}