package presentador;

import modelo.AlgoritmoKruskal;
import modelo.Grafo;
import modelo.Regionalizador;
import vista.MainFrame;

public class MainPresenter {
    private MainFrame vista;
    private Grafo grafoOriginal;
    private Grafo agmActual;

    public MainPresenter(MainFrame vista) {
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser nula.");
        }
        this.vista = vista;
        this.grafoOriginal = new Grafo();

        // 1. Suscribir listeners a las acciones de la vista[cite: 7]
        this.vista.addAgregarProvinciaListener(e -> agregarProvincia());
        this.vista.addAgregarAristaListener(e -> agregarArista());
        this.vista.addCalcularRegionesListener(e -> calcularRegiones());

        // 2. Escuchar clics sobre el mapa para autocompletar coordenadas en los campos de texto
        this.vista.getMapPanel().setMapPanelListener((lat, lon) -> {
            this.vista.setCoordenadasClickeadas(lat, lon);
        });
    }

    private void agregarProvincia() {
        try {
            String nombre = vista.getNombreProvincia();
            double lat = Double.parseDouble(vista.getLatitud());
            double lon = Double.parseDouble(vista.getLongitud());

            int newId = grafoOriginal.agregarProvincia(nombre, lat, lon);
            vista.mostrarMensaje("Provincia '" + nombre + "' agregada con éxito. ID: " + newId);
            actualizarVistaMapa();
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("Error: Las coordenadas (latitud y longitud) deben ser valores numéricos válidos.");
        } catch (Exception ex) {
            vista.mostrarMensaje("Error al agregar provincia: " + ex.getMessage());
        }
    }

    private void agregarArista() {
        try {
            int origen = Integer.parseInt(vista.getOrigenId());
            int destino = Integer.parseInt(vista.getDestinoId());
            double peso = Double.parseDouble(vista.getPesoArista());

            grafoOriginal.agregarArista(origen, destino, peso);
            vista.mostrarMensaje("Conexión entre " + origen + " y " + destino + " agregada correctamente.");
            actualizarVistaMapa();
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("Error: Los IDs y el peso deben ser valores numéricos.");
        } catch (Exception ex) {
            vista.mostrarMensaje("Error al agregar conexión: " + ex.getMessage());
        }
    }

    private void calcularRegiones() {
        try {
            if (grafoOriginal.getCantidadVertices() == 0) {
                vista.mostrarMensaje("El grafo no contiene provincias para regionalizar.");
                return;
            }

            int k = vista.getKValue();
            
            // 1. Calcular el AGM mediante el Algoritmo de Kruskal
            agmActual = AlgoritmoKruskal.calcularAGM(grafoOriginal);
            
            // 2. Eliminar k-1 aristas pesadas y marcar las k componentes conexas (regiones) con BFS[cite: 9]
            Regionalizador.generarRegiones(agmActual, k);

            // 3. Notificar a la vista pasiva para actualizar el mapa[cite: 7]
            actualizarVistaMapa();
            vista.mostrarMensaje("Regiones generadas exitosamente para k = " + k);
        } catch (Exception ex) {
            vista.mostrarMensaje("Error al calcular regiones: " + ex.getMessage());
        }
    }

    private void actualizarVistaMapa() {
        // Muestra el AGM regionalizado si existe, o el grafo original en caso contrario
        Grafo grafoADibujar = (agmActual != null) ? agmActual : grafoOriginal;
        vista.getMapPanel().actualizarDatos(grafoADibujar.getProvincias(), grafoADibujar.getAristas());
    }
}