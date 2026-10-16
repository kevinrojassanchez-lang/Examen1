package cr.ac.proyecto.model;

import cr.ac.proyecto.exception.CitaNoModificableException;
import java.time.LocalDateTime;

public interface Reprogramable {

    void reprogramar(LocalDateTime nuevaFecha)
            throws CitaNoModificableException;
}
