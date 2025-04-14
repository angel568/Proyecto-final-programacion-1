// Main.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Cambia estos valores por los de tu propia base de datos
            String url = "jdbc:mysql://localhost:3306/almacen";
            String usuario = "root";
            String contraseña = "1831";

            Connection connection = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa");

            // Inicia el login pasándole la conexión
            new LoginView(connection).setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar a la base de datos.");
        }
    }
}



