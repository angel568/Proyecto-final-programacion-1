import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LoginView extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnLogin, btnRegistrar;
    private Connection connection;

    public LoginView(Connection connection) {
        this.connection = connection;

        setTitle("Login de Usuario");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));

        panelCampos.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelCampos.add(txtUsuario);

        panelCampos.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panelCampos.add(txtContrasena);

        btnLogin = new JButton("Iniciar sesión");
        btnRegistrar = new JButton("Registrarse");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistrar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse.");
                return;
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            boolean valido = usuarioDAO.validarLogin(usuario, contrasena);

            if (valido) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");
                new MenuPrincipalView(connection).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas");
            }
        });

        btnRegistrar.addActionListener(e -> {
            new RegistroView(connection).setVisible(true);
            this.dispose();
        });

        setVisible(true);
    }
}
