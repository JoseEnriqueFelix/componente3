import java.awt.*;
import javax.swing.*;

public class VistaCombosDependientes extends JFrame {

    public VistaCombosDependientes() {
        super("Tarea Componentes");
        hazInterfaz();
    }

    private void hazInterfaz() {
        setSize(800, 300);
        this.setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        CombosDependientes c1 = new CombosDependientes();
        add(c1);
        add(new CombosDependientes("Sinaloa"));
        add(new CombosDependientes("Sinaloa", "Culiacán"));
        // JButton btn = new JButton("Sacar");
        // add(btn);
        // btn.addActionListener(e -> {
        //     System.out.println(c1.getUbicacion());
        // });
        setVisible(true);
    }
}
