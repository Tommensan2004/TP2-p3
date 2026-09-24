package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
    private MapPanel mapPanel;
    private JTextField txtNombreProvincia;
    private JTextField txtLatitud;
    private JTextField txtLongitud;
    private JButton btnAgregarProvincia;
    
    private JTextField txtOrigenId;
    private JTextField txtDestinoId;
    private JTextField txtPesoArista;
    private JButton btnAgregarArista;

    private JSpinner spinnerK;
    private JButton btnCalcularRegiones;

    public MainFrame() {
        setTitle("Sistema de Regionalización de Provincias - TP2");
        setSize(1300, 720); // Dentro del límite máximo de 1366x768
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);

        // Panel de control lateral
        JPanel panelControl = new JPanel();
        panelControl.setPreferredSize(new Dimension(320, 720));
        panelControl.setLayout(new GridLayout(15, 1, 5, 5));

        // Controles de Provincia
        panelControl.add(new JLabel("-- Carga de Provincia --"));
        txtNombreProvincia = new JTextField();
        panelControl.add(new JLabel("Nombre:"));
        panelControl.add(txtNombreProvincia);
        txtLatitud = new JTextField();
        panelControl.add(new JLabel("Latitud:"));
        panelControl.add(txtLatitud);
        txtLongitud = new JTextField();
        panelControl.add(new JLabel("Longitud:"));
        panelControl.add(txtLongitud);
        btnAgregarProvincia = new JButton("Agregar Provincia");
        panelControl.add(btnAgregarProvincia);

        // Controles de Arista / Similitud
        panelControl.add(new JLabel("-- Carga de Conexiones --"));
        txtOrigenId = new JTextField();
        panelControl.add(new JLabel("ID Origen (int):"));
        panelControl.add(txtOrigenId);
        txtDestinoId = new JTextField();
        panelControl.add(new JLabel("ID Destino (int):"));
        panelControl.add(txtDestinoId);
        txtPesoArista = new JTextField();
        panelControl.add(new JLabel("Peso/Diferencia:"));
        panelControl.add(txtPesoArista);
        btnAgregarArista = new JButton("Agregar Conexión");
        panelControl.add(btnAgregarArista);

        // Controles de Regionalización
        panelControl.add(new JLabel("-- Regionalización --"));
        spinnerK = new JSpinner(new SpinnerNumberModel(2, 1, 50, 1));
        panelControl.add(new JLabel("Regiones (k):"));
        panelControl.add(spinnerK);
        btnCalcularRegiones = new JButton("Generar K Regiones");
        panelControl.add(btnCalcularRegiones);

        add(panelControl, BorderLayout.EAST);
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public String getNombreProvincia() { return txtNombreProvincia.getText(); }
    public String getLatitud() { return txtLatitud.getText(); }
    public String getLongitud() { return txtLongitud.getText(); }
    public String getOrigenId() { return txtOrigenId.getText(); }
    public String getDestinoId() { return txtDestinoId.getText(); }
    public String getPesoArista() { return txtPesoArista.getText(); }
    public int getKValue() { return (int) spinnerK.getValue(); }

    public void setCoordenadasClickeadas(double lat, double lon) {
        txtLatitud.setText(String.valueOf(lat));
        txtLongitud.setText(String.valueOf(lon));
    }

    public void addAgregarProvinciaListener(ActionListener action) { btnAgregarProvincia.addActionListener(action); }
    public void addAgregarAristaListener(ActionListener action) { btnAgregarArista.addActionListener(action); }
    public void addCalcularRegionesListener(ActionListener action) { btnCalcularRegiones.addActionListener(action); }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}