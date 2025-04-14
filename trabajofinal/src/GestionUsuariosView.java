import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class GestionUsuariosView extends JFrame {
    private JTable tablaUsuarios;
    private JButton btnNuevo, btnActualizar, btnEliminar, btnVolver;
    private UsuarioDAO usuarioDAO;
    private Connection connection;

    public GestionUsuariosView(Connection connection) {
        this.connection = connection;
        setTitle("Gestión de Usuarios");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        usuarioDAO = new UsuarioDAO(connection);

        String[] columnas = {"ID", "Usuario", "Nombre", "Apellido", "Teléfono", "Correo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaUsuarios = new JTable(modelo);
        cargarUsuarios(modelo);

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        btnNuevo = new JButton("Nuevo");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnVolver = new JButton("Volver");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnNuevo.addActionListener(e -> {
            new NuevoUsuarioView(connection, () -> cargarUsuarios((DefaultTableModel) tablaUsuarios.getModel()));
        });

        btnActualizar.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila >= 0) {
                String id = tablaUsuarios.getValueAt(fila, 0).toString();
                Usuario usuario = new Usuario(
                        id,
                        (String) tablaUsuarios.getValueAt(fila, 1),
                        (String) tablaUsuarios.getValueAt(fila, 2),
                        (String) tablaUsuarios.getValueAt(fila, 3),
                        (String) tablaUsuarios.getValueAt(fila, 4),
                        (String) tablaUsuarios.getValueAt(fila, 5)
                );
                new EditarUsuarioView(connection, usuario, () -> cargarUsuarios(modelo));
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaUsuarios.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    usuarioDAO.eliminarUsuarioPorId(Integer.parseInt(String.valueOf(id)));
                    cargarUsuarios(modelo);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            }
        });

        btnVolver.addActionListener(e -> {
            new MenuPrincipalView(connection).setVisible(true);
            this.dispose();
        });
        setVisible(true);
    }

    private void cargarUsuarios(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Usuario> lista = usuarioDAO.obtenerTodosLosUsuarios();
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{
                    u.getId(), u.getNombreUsuario(), u.getNombre(), u.getApellido(), u.getTelefono(), u.getCorreo()
            });
        }
    }
}
