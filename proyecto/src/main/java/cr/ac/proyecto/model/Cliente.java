package cr.ac.proyecto.model;

public class Cliente extends Persona {

    public Cliente(String identificacion, String nombre,
            String telefono, String correo) {

        super(identificacion, nombre, telefono, correo);
    }

    @Override
    public String mostrarInformacion() {
        return "Cliente: " + getNombre()
                + " | Identificación: " + getIdentificacion();
    }
}
