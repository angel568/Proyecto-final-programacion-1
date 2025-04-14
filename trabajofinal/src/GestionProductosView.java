import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class GestionProductosView extends JFrame {
    private JTable tablaProductos;
    private JButton btnNuevo, btnActualizar, btnEliminar, btnVolver;
    private ProductoDAO productoDAO;
    private Connection connection;

    public GestionProductosView(Connection connection) {
        this.connection = connection;
        setTitle("Gestión de Productos");
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        productoDAO = new ProductoDAO(connection);

        String[] columnas = {"ID", "Nombre", "Marca", "Categoría", "Precio", "Cantidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modelo);
        cargarProductos(modelo);

        JScrollPane scrollPane = new JScrollPane(tablaProductos);

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
            new NuevoProductoView(connection, () -> cargarProductos((DefaultTableModel) tablaProductos.getModel()));
        });

        btnActualizar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaProductos.getValueAt(fila, 0);
                Producto producto = new Producto(
                        id,
                        (String) tablaProductos.getValueAt(fila, 1),
                        (String) tablaProductos.getValueAt(fila, 2),
                        (String) tablaProductos.getValueAt(fila, 3),
                        (double) tablaProductos.getValueAt(fila, 4),
                        (int) tablaProductos.getValueAt(fila, 5)
                );
                new EditarProductoView(connection, producto, () -> cargarProductos(modelo));
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaProductos.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    productoDAO.eliminarProducto(id);
                    cargarProductos(modelo);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un producto");
            }
        });

        btnVolver.addActionListener(e -> {
            new MenuPrincipalView(connection).setVisible(true);
            this.dispose();
        });

        setVisible(true);
    }

    private void cargarProductos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Producto> lista = productoDAO.obtenerProductos();
        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                    p.getId(), p.getNombre(), p.getMarca(), p.getCategoria(), p.getPrecio(), p.getCantidad()
            });
        }
    }
}
