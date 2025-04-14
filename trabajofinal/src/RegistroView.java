import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class RegistroView extends JFrame {
    private JTextField txtUsuario, txtNombre, txtApellido, txtTelefono, txtCorreo;
    private JPasswordField txtContrasena, txtConfirmar;

    private final Connection connection;

    public RegistroView(Connection connection) {
        this.connection = connection;

        setTitle("Registro de Usuario");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

            boolean registrado = usuarioDAO.registrarUsuario(user, nombre, apellido, tel, correo, pass);

            if (registrado) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
                new LoginView(connection).setVisible(true); // Asumiendo que LoginView también recibe conexión
                this.dispose();
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
}
