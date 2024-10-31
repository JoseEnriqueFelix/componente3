import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;

import java.sql.ResultSet;

public class ConectividadBDCombosDependientes {

    private final static String URL = "jdbc:mysql://localhost:3306/ubicaciones";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "contraseña";
    private Connection connection;

    public ConectividadBDCombosDependientes() {
        conectarBaseDeDatos();
    }

    public void conectarBaseDeDatos() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Exito");
        } catch (SQLException ex) {
            System.out.println("Error");
            ex.printStackTrace();
        }
    }

    public void asignarValoresJComboBox(JComboBox<String> combo, String select, String campo) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(select);

            while (rs.next()) {
                String aux = rs.getString(campo);
                combo.addItem(aux);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String consultarID(String query) {
        String s = "";
        Statement st = null;
        ResultSet rs = null;
        try {
            System.out.println(query);
            st = connection.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            s = rs.getInt(1) + "";
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return s;
    }
}
