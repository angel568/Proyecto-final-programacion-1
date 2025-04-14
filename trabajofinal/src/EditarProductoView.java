import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class EditarProductoView extends JFrame {
    private JTextField txtNombre, txtMarca, txtCategoria, txtPrecio, txtCantidad;
    private JButton btnGuardar, btnCancelar;
    private Producto producto;
    private Connection connection;

    public EditarProductoView(Connection connection, Producto producto, Runnable onSuccess) {
        this.connection = connection;
        this.producto = producto;

        setTitle("Editar Producto");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel para los campos de texto
        JPanel panel = new JPanel(new GridLayout(6, 2));

        // Inicialización de los campos de texto con los datos del producto
        txtNombre = new JTextField(producto.getNombre());
        txtMarca = new JTextField(producto.getMarca());
        txtCategoria = new JTextField(producto.getCategoria());
        txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));
        txtCantidad = new JTextField(String.valueOf(producto.getCantidad()));

        // Añadir etiquetas y campos de texto
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Marca:"));
        panel.add(txtMarca);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);

        // Botón de guardar
        btnGuardar = new JButton("Guardar cambios");
        btnGuardar.addActionListener(e -> {
            // Actualización de los valores del producto con lo ingresado en los campos
            producto.setNombre(txtNombre.getText());
            producto.setMarca(txtMarca.getText());
            producto.setCategoria(txtCategoria.getText());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setCantidad(Integer.parseInt(txtCantidad.getText()));

            // Usar ProductoDAO para actualizar la base de datos
            ProductoDAO productoDAO = new ProductoDAO(connection);
            if (productoDAO.actualizarProducto(producto)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado");
                onSuccess.run();  // Llamar al callback onSuccess después de una actualización exitosa
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar");
            }
        });

        // Botón de cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // Añadir al frame
        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }
}
