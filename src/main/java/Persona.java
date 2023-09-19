import org.apache.spark.sql.Row;

public class Persona {

    private String apellido;
    private Long edad;
    private String dni;
    private String email;
    private String nombre;

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

    public Long getEdad() {
        return edad;
    }

    public void setEdad(Long edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean validarEmail(){
        // Mediante una expresión regular
        return email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.([a-zA-Z]{2,4})+$");
    }

    public static Persona crearPersona(Row fila){
        Persona p = new Persona();
        p.setApellido(fila.getString(0));
        p.setDni(fila.getString(1));
        p.setEdad(fila.getLong(2));
        p.setEmail(fila.getString(3));
        p.setNombre(fila.getString(4));
        return p;
    }
}