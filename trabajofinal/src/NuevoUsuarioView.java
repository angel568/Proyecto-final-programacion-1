import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NuevoUsuarioView extends JFrame {
    private JTextField txtUsuario, txtNombre, txtApellido, txtTelefono, txtCorreo;
    private JPasswordField txtContrasena, txtConfirmar;
    private Connection connection;
    private Runnable onSuccess;

    // Constructor principal
    public NuevoUsuarioView(Connection connection) {
        this(connection, null);
    }

    // Constructor con callback
    public NuevoUsuarioView(Connection connection, Runnable onSuccess) {
        this.connection = connection;
        this.onSuccess = onSuccess;

        setTitle("Nuevo Usuario");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // Importante: solo cerrar esta ventana
        setLayout(new BorderLayout());

        String[] labels = {"Usuario", "Nombre", "Apellido", "Teléfono", "Correo", "Contrasena", "Confirmar Contrasena"};
        JPanel panelCampos = new JPanel(new GridLayout(labels.length, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        for (String label : labels) {
            panelCampos.add(new JLabel(label + ":"));
            switch (label) {
                case "Usuario" -> { txtUsuario = new JTextField(); panelCampos.add(txtUsuario); }
                case "Nombre" -> { txtNombre = new JTextField(); panelCampos.add(txtNombre); }
                case "Apellido" -> { txtApellido = new JTextField(); panelCampos.add(txtApellido); }
                case "Teléfono" -> { txtTelefono = new JTextField(); panelCampos.add(txtTelefono); }
                case "Correo" -> { txtCorreo = new JTextField(); panelCampos.add(txtCorreo); }
                case "Contrasena" -> { txtContrasena = new JPasswordField(); panelCampos.add(txtContrasena); }
                case "Confirmar Contrasena" -> { txtConfirmar = new JPasswordField(); panelCampos.add(txtConfirmar); }
            }
        }

        JButton btnRegistrar = new JButton("Registrar");
        estilizarBoton(btnRegistrar, new Color(33, 80, 122));
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnRegistrar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        UsuarioDAO usuarioDAO = new UsuarioDAO(connection);

        btnRegistrar.addActionListener(e -> {
            String user = txtUsuario.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String tel = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String pass = new String(txtContrasena.getPassword());
            String confirm = new String(txtConfirmar.getPassword());

            if (user.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || tel.isEmpty() || correo.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
                return;
            }

            boolean registrado = usuarioDAO.insertarUsuario(user, nombre, apellido, tel, correo, pass);

            if (registrado) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
                if (onSuccess != null) {
                    onSuccess.run(); // Ejecutar recarga o actualización
                } else {
                    new LoginView(connection).setVisible(true); // Solo en flujo normal
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el usuario.");
            }
        });

        setVisible(true);
    }

    private void estilizarBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
    }

    // Conexión a la base de datos
    public static class Conexion {
        public static Connection getConnection() {
            try {
                String url = "jdbc:mysql://localhost:3306/tu_base_de_datos";
                String user = "root";
                String password = "1831";
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    // DAO para insertar usuario
    public static class UsuarioDAO {
        private final Connection connection;

        public UsuarioDAO(Connection connection) {
            this.connection = connection;
        }

        public boolean insertarUsuario(String usuario, String nombre, String apellido, String telefono, String correo, String contrasena) {
            String sql = "INSERT INTO usuarios (usuario, nombre, apellido, telefono, correo, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, usuario);
                stmt.setString(2, nombre);
                stmt.setString(3, apellido);
                stmt.setString(4, telefono);
                stmt.setString(5, correo);
                stmt.setString(6, contrasena);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void main(String[] args) {
        Connection connection = Conexion.getConnection();
        if (connection != null) {
            new NuevoUsuarioView(connection);
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
        }
    }
}
