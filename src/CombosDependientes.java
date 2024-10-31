import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;

public class CombosDependientes extends JPanel implements ActionListener {
    private JComboBox<String> JcbEstados, JcbCiudades, JcbColonias;
    private JLabel lblEstados, lblCiudades, lblColonias;
    private ConectividadBDCombosDependientes conexion;
    private boolean valido;

    public CombosDependientes() {
        this(null, null);
    }

    public CombosDependientes(String estado) {
        this(estado, null);
    }

    public CombosDependientes(String estado, String ciudad) {
        valido = false;
        conexion = new ConectividadBDCombosDependientes();
        hazInterfaz(estado, ciudad);
        hazEscuchas();
    }

    public String getUbicacion() {
        if (valido)
            return "Estado : " + JcbEstados.getSelectedItem() + ", ciudad : " + JcbCiudades.getSelectedItem()
                    + ", colonia : " + JcbColonias.getSelectedItem();
        return "No valido. Deben estar todos los combos seleccionados";
    }

    private void hazInterfaz(String estado, String ciudad) {
        // estados
        lblEstados = new JLabel("Estados");
        JcbEstados = new JComboBox<>();
        JcbEstados.addItem("Seleccione");
        String select = "SELECT nombreEstado FROM estados";
        conexion.asignarValoresJComboBox(JcbEstados, select, "nombreEstado");
        if (estado != null) {
            for (int i = 0; i < JcbEstados.getItemCount(); i++) {
                if (JcbEstados.getItemAt(i).equalsIgnoreCase(estado))
                    JcbEstados.setSelectedIndex(i);
            }
        }

        // ciudades
        lblCiudades = new JLabel("Ciudades");
        JcbCiudades = new JComboBox<>();
        if (estado != null && JcbEstados.getSelectedIndex() != 0) {
            JcbCiudades.addItem("Seleccione");
            String consulta = "SELECT IdEstado from estados where nombreEstado = '" + JcbEstados.getSelectedItem()
                    + "'";
            String idEstado = conexion.consultarID(consulta);
            consulta = "SELECT nombreCiudad FROM ciudades WHERE IdEstado = " + idEstado;
            conexion.asignarValoresJComboBox(JcbCiudades, consulta, "nombreCiudad");
            JcbCiudades.setEnabled(true);
        }

        // colonias
        lblColonias = new JLabel("Colonias");
        JcbColonias = new JComboBox<>();

        if (estado != null && JcbEstados.getSelectedIndex() != 0 && ciudad != null) {
            for (int i = 0; i < JcbCiudades.getItemCount(); i++) {
                if (JcbCiudades.getItemAt(i).equalsIgnoreCase(ciudad))
                    JcbCiudades.setSelectedIndex(i);
            }
            JcbColonias.addItem("Seleccione");
            String consulta = "SELECT IdEstado from estados where nombreEstado = '" + JcbEstados.getSelectedItem()
                    + "'";
            String idEstado = conexion.consultarID(consulta);
            consulta = "SELECT IdCiudad from ciudades where IdEstado = " + idEstado + " AND nombreCiudad = '"
                    + JcbCiudades.getSelectedItem() + "'";
            String idCiudad = conexion.consultarID(consulta);
            consulta = "SELECT nombreColonia FROM colonias WHERE IdEstado = " + idEstado + " AND IdCiudad = "
                    + idCiudad;
            conexion.asignarValoresJComboBox(JcbColonias, consulta, "nombreColonia");
        }

        if (estado == null || JcbEstados.getItemCount() <= 1 || JcbEstados.getSelectedIndex() == 0) {
            JcbCiudades.removeAllItems();
            JcbCiudades.setEnabled(false);
        }

        if (ciudad == null || JcbCiudades.getItemCount() <= 1 || JcbCiudades.getSelectedIndex() == 0) {
            JcbColonias.removeAllItems();
            JcbColonias.setEnabled(false);
        }

        add(lblEstados);
        add(JcbEstados);
        add(lblCiudades);
        add(JcbCiudades);
        add(lblColonias);
        add(JcbColonias);

    }

    private void hazEscuchas() {
        JcbEstados.addActionListener(this);
        JcbCiudades.addActionListener(this);
        JcbColonias.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JcbEstados) {
            JcbCiudades.removeAllItems();
            JcbColonias.removeAllItems();
            valido = false;
            if (JcbEstados.getSelectedIndex() == 0) {
                JcbCiudades.setEnabled(false);
                JcbColonias.setEnabled(false);
                return;
            }
            JcbCiudades.addItem("Seleccione");
            String consulta = "SELECT IdEstado from estados where nombreEstado = '" + JcbEstados.getSelectedItem()
                    + "'";
            String idEstado = conexion.consultarID(consulta);
            consulta = "SELECT nombreCiudad FROM ciudades WHERE IdEstado = " + idEstado;
            conexion.asignarValoresJComboBox(JcbCiudades, consulta, "nombreCiudad");
            JcbCiudades.setEnabled(true);
            return;
        }

        if (e.getSource() == JcbCiudades) {
            JcbColonias.removeAllItems();
            valido = false;
            if (JcbCiudades.getSelectedIndex() == 0) {
                JcbColonias.setEnabled(false);
                return;
            }
            JcbColonias.addItem("Seleccione");
            String consulta = "SELECT IdEstado from estados where nombreEstado = '" + JcbEstados.getSelectedItem()
                    + "'";
            String idEstado = conexion.consultarID(consulta);
            consulta = "SELECT IdCiudad from ciudades where IdEstado = " + idEstado + " AND nombreCiudad = '"
                    + JcbCiudades.getSelectedItem() + "'";
            String idCiudad = conexion.consultarID(consulta);
            consulta = "SELECT nombreColonia FROM colonias WHERE IdEstado = " + idEstado + " AND IdCiudad = "
                    + idCiudad;
            conexion.asignarValoresJComboBox(JcbColonias, consulta, "nombreColonia");
            JcbColonias.setEnabled(true);
            return;
        }

        if (e.getSource() == JcbColonias) {
            valido = (JcbColonias.getSelectedIndex() == 0) ? false : true;
            return;
        }
    }
}
