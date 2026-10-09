package cr.ac.proyecto.model;

public class Servicio {

    private String codigo;
    private String nombre;
    private int duracion;
    private double precio;

    public Servicio(String codigo, String nombre, int duracion, double precio) {
        
        this.codigo = codigo;
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getDuracion()  { return duracion; }
    public double getPrecio() { return precio; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public void setPrecio(double precio) { this.precio = precio; }
}
