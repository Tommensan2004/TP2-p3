package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

    // Campos de Provincia
    private JTextField txtNombreProvincia;
    private JTextField txtLatitud;
    private JTextField txtLongitud;
    private JButton btnAgregarProvincia;

    // Campos de Conexión
    private JTextField txtOrigenId;
    private JTextField txtDestinoId;
    private JTextField txtPesoArista;
    private JButton btnAgregarArista;

    // Campos de Regionalización
    private JSpinner spinnerK;
    private JButton btnCalcularRegiones;
    private JButton btnVerOriginal; 

    public MainFrame() {
        setTitle("Sistema de Regionalización de Provincias - TP2");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Panel de Mapa en el centro
        mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);

        // 2. Panel de Control Lateral con Layout organizado
        JPanel panelControl = new JPanel(new GridBagLayout());
        panelControl.setPreferredSize(new Dimension(340, 720));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 1, 1, 1); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        Font fontTitulo = new Font("SansSerif", Font.BOLD, 13);

        // --- Carga de Provincia ---
        JLabel lblProv = new JLabel("-- Carga de Provincia --");
        lblProv.setFont(fontTitulo);
        gbc.gridwidth = 2;
        panelControl.add(lblProv, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panelControl.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombreProvincia = new JTextField(12);
        panelControl.add(txtNombreProvincia, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelControl.add(new JLabel("Latitud:"), gbc);
        gbc.gridx = 1;
        txtLatitud = new JTextField(12);
        panelControl.add(txtLatitud, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelControl.add(new JLabel("Longitud:"), gbc);
        gbc.gridx = 1;
        txtLongitud = new JTextField(12);
        panelControl.add(txtLongitud, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnAgregarProvincia = new JButton("Agregar Provincia");
        panelControl.add(btnAgregarProvincia, gbc);

        // --- Carga de Conexiones ---
        gbc.gridy++;
        JLabel lblCon = new JLabel("-- Carga de Conexiones --");
        lblCon.setFont(fontTitulo);
        panelControl.add(lblCon, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panelControl.add(new JLabel("ID Origen (int):"), gbc);
        gbc.gridx = 1;
        txtOrigenId = new JTextField(12);
        panelControl.add(txtOrigenId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelControl.add(new JLabel("ID Destino (int):"), gbc);
        gbc.gridx = 1;
        txtDestinoId = new JTextField(12);
        panelControl.add(txtDestinoId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelControl.add(new JLabel("Peso/Diferencia:"), gbc);
        gbc.gridx = 1;
        txtPesoArista = new JTextField(12);
        panelControl.add(txtPesoArista, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnAgregarArista = new JButton("Agregar Conexión");
        panelControl.add(btnAgregarArista, gbc);

        // --- Regionalización ---
        gbc.gridy++;
        JLabel lblReg = new JLabel("-- Regionalización --");
        lblReg.setFont(fontTitulo);
        panelControl.add(lblReg, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panelControl.add(new JLabel("Regiones (k):"), gbc);
        gbc.gridx = 1;
        spinnerK = new JSpinner(new SpinnerNumberModel(2, 1, 50, 1));
        panelControl.add(spinnerK, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnCalcularRegiones = new JButton("Generar K Regiones");
        panelControl.add(btnCalcularRegiones, gbc);

        // --- Botón de Grafo Original ---
        gbc.gridy++;
        btnVerOriginal = new JButton("Ver Grafo Original");
        panelControl.add(btnVerOriginal, gbc);

        add(panelControl, BorderLayout.EAST);
    }

    public MapPanel getMapPanel() { return mapPanel; }

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

    // Listeners para los botones (Passive View)
    public void addAgregarProvinciaListener(ActionListener action) { btnAgregarProvincia.addActionListener(action); }
    public void addAgregarAristaListener(ActionListener action) { btnAgregarArista.addActionListener(action); }
    public void addCalcularRegionesListener(ActionListener action) { btnCalcularRegiones.addActionListener(action); }
    public void addVerOriginalListener(ActionListener action) { btnVerOriginal.addActionListener(action); }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}