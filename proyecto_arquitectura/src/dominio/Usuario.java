package dominio;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String telefono;
    private String rol;

    public Usuario(int id, String nombre, String correo, String telefono, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getRol() { return rol; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", rol=" + rol + "}";
    }
}