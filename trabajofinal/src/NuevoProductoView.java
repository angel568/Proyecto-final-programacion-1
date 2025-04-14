import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class NuevoProductoView extends JFrame {
    private JTextField txtNombre, txtMarca, txtCategoria, txtPrecio, txtCantidad;
    private JButton btnGuardar, btnCancelar;
    private Connection connection;

    public NuevoProductoView(Connection connection, Runnable onSuccess) {
        this.connection = connection;

        setTitle("Nuevo Producto");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel para los campos de texto
        JPanel panel = new JPanel(new GridLayout(6, 2));

        // Campos de texto
        txtNombre = new JTextField();
        txtMarca = new JTextField();
        txtCategoria = new JTextField();
        txtPrecio = new JTextField();
        txtCantidad = new JTextField();

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
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            // Crear un nuevo producto con los datos ingresados
            String nombre = txtNombre.getText();
            String marca = txtMarca.getText();
            String categoria = txtCategoria.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());

            Producto producto = new Producto(0, nombre, marca, categoria, precio, cantidad);

            // Usar ProductoDAO para agregar el producto a la base de datos
            ProductoDAO productoDAO = new ProductoDAO(connection);
            if (productoDAO.agregarProducto(producto)) {
                JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.");
                onSuccess.run();  // Actualizar la lista de productos
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar el producto.");
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
