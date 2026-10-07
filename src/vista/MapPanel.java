package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import modelo.Arista;
import modelo.Provincia;

public class MapPanel extends JPanel {
	private static final long serialVersionUID = 1L;

    // Color por cada región asignada
    private static final Color[] COLORES_REGIONES = {
        Color.BLACK, Color.RED, Color.BLUE, Color.GREEN,
        Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.PINK
    };

    private JMapViewer mapViewer;
    private List<Provincia> provincias;
    private List<Arista> aristas;
    private MapPanelListener listener;

    public interface MapPanelListener {
        void onMapaClicked(double latitud, double longitud);
    }

    public MapPanel() {
        setLayout(new java.awt.BorderLayout());
        // Las aristas se dibujan dentro del mapa, para evitar que se borren al hacer zoom o mover el mapa
        mapViewer = new JMapViewer() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarAristas(g);
            }
        };
        mapViewer.setDisplayPosition(new Coordinate(-38.416097, -63.616672), 4); // Centrado en Argentina
        add(mapViewer, java.awt.BorderLayout.CENTER);

        provincias = new ArrayList<>();
        aristas = new ArrayList<>();

        // Capturar clics sobre el mapa para registrar coordenadas
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && listener != null) {
                    Point p = e.getPoint();
                    Coordinate coord = (Coordinate) mapViewer.getPosition(p);
                    if (coord != null) {
                        listener.onMapaClicked(coord.getLat(), coord.getLon());
                    }
                }
            }
        });
    }

    public void setMapPanelListener(MapPanelListener listener) {
        this.listener = listener;
    }

    public void actualizarDatos(List<Provincia> provincias, List<Arista> aristas) {
        this.provincias = provincias;
        this.aristas = aristas;
        mapViewer.removeAllMapMarkers();

        for (Provincia p : provincias) {
            Color color = colorDeRegion(p.getRegion());

            String texto = p.getNombre() + " (" + p.getId() + ")";
            MapMarkerDot marker = new MapMarkerDot(texto, new Coordinate(p.getLatitud(), p.getLongitud()));
            marker.setBackColor(color);
            mapViewer.addMapMarker(marker);
        }
        repaint();
    }

    private Color colorDeRegion(int region) {
        return (region > 0 && region < COLORES_REGIONES.length)
                ? COLORES_REGIONES[region] : Color.DARK_GRAY;
    }

    private void dibujarAristas(Graphics g) {
        if (provincias != null && aristas != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            Stroke lineaContinua = new BasicStroke(2);
            Stroke lineaPunteada = new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10f, new float[] {6f, 6f}, 0f);

            for (Arista a : aristas) {
                Provincia orig = provincias.get(a.getOrigen());
                Provincia dest = provincias.get(a.getDestino());

                Point p1 = mapViewer.getMapPosition(orig.getLatitud(), orig.getLongitud(), false);
                Point p2 = mapViewer.getMapPosition(dest.getLatitud(), dest.getLongitud(), false);
                if (p1 == null || p2 == null) {
                    continue;
                }

                int regionOrig = orig.getRegion();
                int regionDest = dest.getRegion();

                if (regionOrig == 0 && regionDest == 0) {
                    // Grafo original
                    g2d.setColor(Color.BLUE);
                    g2d.setStroke(lineaContinua);
                } else if (regionOrig == regionDest) {
                    // Aristas con colores correspondientes
                    g2d.setColor(colorDeRegion(regionOrig));
                    g2d.setStroke(lineaContinua);
                } else {
                    // Arista punteada para marcar separacion de regiones
                    g2d.setColor(Color.GRAY);
                    g2d.setStroke(lineaPunteada);
                }
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
            g2d.dispose();
        }
    }
}
