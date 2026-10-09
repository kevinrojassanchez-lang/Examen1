package cr.ac.proyecto.model;

public class Profesional extends Persona {

    private Especialidad especialidad;
    private String horario;

    public Profesional(String identificacion, String nombre,String telefono,
            String correo, Especialidad especialidad, String horario) {
        
        super(identificacion, nombre, telefono, correo);
        this.especialidad = especialidad;
        this.horario = horario;
    }

    public Especialidad getEspecialidad() { return especialidad; }
    public String getHorario() { return horario; }

    public void setEspecialidad(Especialidad especialidad) { 
        this.especialidad = especialidad; }

    public void setHorario(String horario) { this.horario = horario; }
}
