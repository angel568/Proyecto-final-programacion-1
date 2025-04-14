public class Usuario {
    private int id;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String usuario;
    private String contrasena;

    // Constructor con parámetros
    public Usuario(int id, String nombreUsuario, String nombre, String apellido, String telefono, String correo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Usuario(String usuario, String nombre, String apellido, String telefono, String correo, String contrasena) {
    }
    // Getters

    public int getId() {
        return id;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }
}
