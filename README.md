# Sistema de Gestión de Productos de Almacén

Este proyecto es una aplicación de escritorio desarrollada en **Java Swing** que permite gestionar usuarios y productos en un almacén. Se conecta a una base de datos **MySQL** y cuenta con funcionalidades como inicio de sesión, registro de usuarios, y operaciones CRUD para usuarios y productos.

## 📦 Características

- Inicio de sesión con validación de credenciales
- Registro de nuevos usuarios
- Menú principal de navegación
- Gestión de usuarios (crear, actualizar, eliminar, listar)
- Gestión de productos (crear, actualizar, eliminar, listar)
- Interfaz moderna y responsiva
- Conexión a base de datos MySQL

## 🖥️ Tecnologías utilizadas

- Java (JDK 8+)
- Swing (Interfaz gráfica)
- MySQL
- JDBC (conector a base de datos)

## 🔧 Requisitos previos

- JDK 8 o superior
- MySQL instalado
- Un IDE como IntelliJ, Eclipse o NetBeans
- Conector JDBC para MySQL (`mysql-connector-java`)

## ⚙️ Configuración del proyecto

1. **Clonar el repositorio** o descargar los archivos.

2. **Crear la base de datos** en MySQL:

```sql
CREATE DATABASE almacen;

USE almacen;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(15),
    correo VARCHAR(100),
    contrasena VARCHAR(255)
);

CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    marca VARCHAR(100),
    categoria VARCHAR(100),
    precio DECIMAL(10,2),
    cantidad INT
);
```
3. Configurar la conexión en Main.java:
   
String url = "jdbc:mysql://localhost:3306/almacen";
String usuario = "root";
String contraseña = "tu_contraseña";

📁 Estructura del proyecto

src/
│
├── Main.java
├── views/
│   ├── LoginView.java
│   ├── RegistroView.java
│   ├── MenuPrincipalView.java
│   ├── GestionUsuariosView.java
│   └── GestionProductosView.java
│
├── models/
│   ├── Usuario.java
│   └── Producto.java
│
└── dao/
    ├── UsuarioDAO.java
    └── ProductoDAO.java

📝 Notas
Asegúrate de que el servicio de MySQL esté corriendo antes de iniciar la aplicación.

Todos los campos de entrada están validados para evitar errores comunes.

🙌 Autor
Desarrollado por Angel Morillo 2023-0554.
