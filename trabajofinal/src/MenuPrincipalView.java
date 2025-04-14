import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MenuPrincipalView extends JFrame {
    private JButton btnGestionUsuarios, btnGestionProductos, btnCerrarSesion;
    private Connection connection;

    public MenuPrincipalView(Connection connection) {
        this.connection = connection;

        setTitle("Menú Principal");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        btnGestionUsuarios = new JButton("Gestión de Usuarios");
        btnGestionProductos = new JButton("Gestión de Productos");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGestionUsuarios);
        panelBotones.add(btnGestionProductos);
        panelBotones.add(btnCerrarSesion);

        add(panelBotones, BorderLayout.CENTER);

        btnGestionUsuarios.addActionListener(e -> {
            new GestionUsuariosView(connection).setVisible(true);
            this.dispose();
        });

        btnGestionProductos.addActionListener(e -> {
            new GestionProductosView(connection).setVisible(true);
            this.dispose();
        });

        btnCerrarSesion.addActionListener(e -> {
            new LoginView(connection).setVisible(true);
            this.dispose();
        });

        setVisible(true);
    }
}
