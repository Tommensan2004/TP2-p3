package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
	
    private JMapViewer mapViewer;
    private List<Provincia> provincias;
    private List<Arista> aristas;
    private MapPanelListener listener;

    public interface MapPanelListener {
        void onMapaClicked(double latitud, double longitud);
    }

    public MapPanel() {
        setLayout(new java.awt.BorderLayout());
        mapViewer = new JMapViewer();
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

        // Color por cada región asignada
        Color[] coloresRegiones = {
            Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, 
            Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.PINK
        };

        for (Provincia p : provincias) {
            Color color = (p.getRegion() > 0 && p.getRegion() < coloresRegiones.length) 
                          ? coloresRegiones[p.getRegion()] : Color.DARK_GRAY;
            
            MapMarkerDot marker = new MapMarkerDot(p.getNombre(), new Coordinate(p.getLatitud(), p.getLongitud()));
            marker.setBackColor(color);
            mapViewer.addMapMarker(marker);
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Dibujar las aristas/conexiones directamente sobre el mapa
        if (provincias != null && aristas != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new java.awt.BasicStroke(2));

            for (Arista a : aristas) {
                Provincia orig = provincias.get(a.getOrigen());
                Provincia dest = provincias.get(a.getDestino());

                Point p1 = mapViewer.getMapPosition(orig.getLatitud(), orig.getLongitud(), false);
                Point p2 = mapViewer.getMapPosition(dest.getLatitud(), dest.getLongitud(), false);

                if (p1 != null && p2 != null) {
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
            g2d.dispose();
        }
    }
}