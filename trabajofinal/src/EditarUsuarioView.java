import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class EditarUsuarioView extends JFrame {
    public EditarUsuarioView(Connection connection, Usuario usuario, Runnable onSuccess) {
        setTitle("Editar Usuario");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel para los campos de texto
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        // Inicialización de los campos de texto con los datos del usuario
        JTextField txtUsuario = new JTextField(usuario.getUsuario());
        txtUsuario.setEditable(false);  // El usuario no debe poder cambiar su nombre de usuario

        JTextField txtNombre = new JTextField(usuario.getNombre());
        JTextField txtApellido = new JTextField(usuario.getApellido());
        JTextField txtTelefono = new JTextField(usuario.getTelefono());
        JTextField txtCorreo = new JTextField(usuario.getCorreo());
        JPasswordField txtContrasena = new JPasswordField(usuario.getContrasena());

        // Añadir etiquetas y campos de texto al panel
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Correo:"));
        panel.add(txtCorreo);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);

        // Botón para guardar cambios
        JButton btnGuardar = new JButton("Guardar cambios");
        btnGuardar.addActionListener(e -> {
            // Validación de campos
            if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtContrasena.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            // Actualización de los valores del usuario con lo ingresado en los campos
            usuario.setNombre(txtNombre.getText());
            usuario.setApellido(txtApellido.getText());
            usuario.setTelefono(txtTelefono.getText());
            usuario.setCorreo(txtCorreo.getText());
            usuario.setContrasena(new String(txtContrasena.getPassword()));

            // Usar DAO para actualizar la base de datos
            UsuarioDAO dao = new UsuarioDAO(connection);
            if (dao.actualizarUsuario(usuario)) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
                onSuccess.run();  // Llamar al callback onSuccess después de una actualización exitosa
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el usuario.");
            }
        });

        // Añadir botón de guardar cambios al panel
        panel.add(new JLabel());
        panel.add(btnGuardar);

        // Añadir panel al JFrame y mostrar
        add(panel);
        setVisible(true);
    }
}
