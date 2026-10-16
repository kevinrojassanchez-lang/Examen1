package cr.ac.proyecto.model;

import cr.ac.proyecto.exception.CitaNoModificableException;

public interface Cancelable {

    void cancelar() throws CitaNoModificableException;
}
