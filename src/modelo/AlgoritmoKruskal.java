package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgoritmoKruskal {

    public static Grafo calcularAGM(Grafo grafoOriginal) {
        if (grafoOriginal == null || grafoOriginal.getCantidadVertices() == 0) {
            throw new IllegalArgumentException("El grafo no puede estar vacío.");
        }

        Grafo agm = new Grafo();
        
        // Copiamos las provincias con la misma correlación de IDs
        for (Provincia p : grafoOriginal.getProvincias()) {
            agm.agregarProvincia(p.getNombre(), p.getLatitud(), p.getLongitud());
        }

        List<Arista> aristasOrdenadas = new ArrayList<>(grafoOriginal.getAristas());
        Collections.sort(aristasOrdenadas);

        int numVertices = grafoOriginal.getCantidadVertices();
        UnionFind uf = new UnionFind(numVertices);
        int aristasAgregadas = 0;

        for (Arista arista : aristasOrdenadas) {
            if (uf.union(arista.getOrigen(), arista.getDestino())) {
                agm.agregarArista(arista.getOrigen(), arista.getDestino(), arista.getPeso());
                aristasAgregadas++;
                if (aristasAgregadas == numVertices - 1) {
                    break;
                }
            }
        }

        return agm;
    }
}