package modelo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Regionalizador {

    public static void generarRegiones(Grafo agm, int k) {
        int n = agm.getCantidadVertices();
        if (k <= 0 || k > n) {
            throw new IllegalArgumentException("El valor de k debe estar entre 1 y " + n);
        }

        // 1. Obtener las aristas del AGM y ordenarlas para descartar las k - 1 más pesadas
        List<Arista> aristasAGM = new ArrayList<>(agm.getAristas());
        Collections.sort(aristasAGM);

        Grafo bosqueResultante = new Grafo();
        for (Provincia p : agm.getProvincias()) {
            p.setRegion(0);
            bosqueResultante.agregarProvincia(p.getNombre(), p.getLatitud(), p.getLongitud());
        }

        int aristasAMantener = aristasAGM.size() - (k - 1);
        for (int i = 0; i < aristasAMantener; i++) {
            Arista a = aristasAGM.get(i);
            bosqueResultante.agregarArista(a.getOrigen(), a.getDestino(), a.getPeso());
        }

        // 2. BFS para marcar componentes conexas
        Set<Integer> visitados = new HashSet<>();
        int idRegionActual = 1;

        for (int i = 0; i < n; i++) {
            if (!visitados.contains(i)) {
                bfsMarcarRegion(bosqueResultante, i, idRegionActual, visitados, agm);
                idRegionActual++;
            }
        }
    }

    private static void bfsMarcarRegion(Grafo bosque, int inicio, int regionId, Set<Integer> visitados, Grafo agmOriginal) {
        Queue<Integer> cola = new ArrayDeque<>();
        cola.add(inicio);
        visitados.add(inicio);

        while (!cola.isEmpty()) {
            int actual = cola.poll();
            agmOriginal.getProvincia(actual).setRegion(regionId);

            // Recorrer las aristas incidentes para obtener los vecinos adyacentes
            for (Arista arista : bosque.getAristasAdyacentes(actual)) {
                int vecino = (arista.getOrigen() == actual) ? arista.getDestino() : arista.getOrigen();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
    }
}