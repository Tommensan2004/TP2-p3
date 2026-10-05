package presentador;

import modelo.AlgoritmoKruskal;
import modelo.Grafo;
import modelo.Provincia;
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

        this.vista.addAgregarProvinciaListener(e -> agregarProvincia());
        this.vista.addAgregarAristaListener(e -> agregarArista());
        this.vista.addCalcularRegionesListener(e -> calcularRegiones());
        this.vista.addVerOriginalListener(e -> mostrarGrafoOriginal());

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
            recalcularSiCorresponde();
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
            recalcularSiCorresponde();
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
            
            agmActual = AlgoritmoKruskal.calcularAGM(grafoOriginal);
            
            Regionalizador.generarRegiones(agmActual, k);

            actualizarVistaMapa();
            vista.mostrarMensaje("Regiones generadas exitosamente para k = " + k);
        } catch (Exception ex) {
            vista.mostrarMensaje("Error al calcular regiones: " + ex.getMessage());
        }
    }
    
    public void mostrarGrafoOriginal() {
        this.agmActual = null;

        for (Provincia p : grafoOriginal.getProvincias()) {
            p.setRegion(0);
        }

        actualizarVistaMapa();
        vista.mostrarMensaje("Se ha vuelto a la visualización del grafo original.");
    }

    private void actualizarVistaMapa() {
        // Muestra el AGM regionalizado si existe, o el grafo original en caso contrario
        Grafo grafoADibujar = (agmActual != null) ? agmActual : grafoOriginal;
        vista.getMapPanel().actualizarDatos(grafoADibujar.getProvincias(), grafoADibujar.getAristas());
    }
    
    private void recalcularSiCorresponde() {
        if (agmActual != null) {
            agmActual = AlgoritmoKruskal.calcularAGM(grafoOriginal);
            Regionalizador.generarRegiones(agmActual, vista.getKValue());
        }
        actualizarVistaMapa();
    }
}
