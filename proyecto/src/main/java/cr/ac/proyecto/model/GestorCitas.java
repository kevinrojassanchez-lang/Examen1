package cr.ac.proyecto.model;

import cr.ac.proyecto.exception.CitaNoModificableException;
import cr.ac.proyecto.exception.HorarioNoDisponibleException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorCitas {

    private ArrayList<Cita> citas;

    public GestorCitas() {
        citas = new ArrayList<Cita>();
    }

    public void agregarCita(Cita cita)
            throws HorarioNoDisponibleException {

        if (cita == null) {
            throw new IllegalArgumentException(
                "La cita no puede ser nula."
            );
        }

        if (!cita.getFechaHora().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                "La cita debe programarse para una fecha futura."
            );
        }

        validarDisponibilidad(cita, null);
        citas.add(cita);
    }

    private void validarDisponibilidad(Cita nuevaCita, Cita ignorar)
            throws HorarioNoDisponibleException {

        LocalDateTime inicioNuevo = nuevaCita.getFechaHora();

        LocalDateTime finNuevo = inicioNuevo.plusMinutes(
            nuevaCita.getServicio().getDuracion()
        );

        for (int i = 0; i < citas.size(); i++) {

            Cita existente = citas.get(i);

            if (existente != ignorar) {

                if (existente.getProfesional().equals(
                        nuevaCita.getProfesional())
                        && existente.getEstado() == EstadoCita.PROGRAMADA) {

                    LocalDateTime inicioExistente = existente.getFechaHora();

                    LocalDateTime finExistente = inicioExistente.plusMinutes(
                            existente.getServicio().getDuracion()
                        );

                    boolean seTraslapan = inicioNuevo.isBefore(finExistente)
                        && finNuevo.isAfter(inicioExistente);

                    if (seTraslapan) {
                        throw new HorarioNoDisponibleException(
                            "El profesional ya tiene una cita en ese horario."
                        );
                    }
                }
            }
        }
    }

    public ArrayList<Cita> listarCitas() {

        ArrayList<Cita> copia = new ArrayList<Cita>();

        for (int i = 0; i < citas.size(); i++) {
            copia.add(citas.get(i));
        }

        return copia;
    }

    public void cancelarCita(Cita cita)
            throws CitaNoModificableException {

        if (cita == null || cita.getEstado() != EstadoCita.PROGRAMADA) {

            throw new CitaNoModificableException(
                "Solo se pueden cancelar citas programadas."
            );
        }

        // Se utiliza la interfaz Cancelable.
        cita.cancelar();
    }

    public void reprogramarCita(
            Cita cita, LocalDateTime nuevaFecha)
            throws CitaNoModificableException,
                   HorarioNoDisponibleException {

        if (cita == null || cita.getEstado() != EstadoCita.PROGRAMADA) {

            throw new CitaNoModificableException(
                "Solo se pueden reprogramar citas programadas."
            );
        }

        if (nuevaFecha == null || !nuevaFecha.isAfter(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                "La nueva fecha debe ser futura."
            );
        }

        LocalDateTime fechaAnterior = cita.getFechaHora();

        // Se utiliza la interfaz Reprogramable.
        cita.reprogramar(nuevaFecha);

        try {
            validarDisponibilidad(cita, cita);

        } catch (HorarioNoDisponibleException ex) {

            // Si hay conflicto, se recupera la fecha original.
            cita.setFechaHora(fechaAnterior);

            throw ex;
        }
    }

    public void marcarAtendida(Cita cita)
            throws CitaNoModificableException {

        if (cita == null || cita.getEstado() != EstadoCita.PROGRAMADA) {

            throw new CitaNoModificableException(
                "Solo se pueden atender citas programadas."
            );
        }

        cita.setEstado(EstadoCita.ATENDIDA);
    }

    public void marcarAusente(Cita cita)
            throws CitaNoModificableException {

        if (cita == null || cita.getEstado() != EstadoCita.PROGRAMADA) {

            throw new CitaNoModificableException(
                "Solo se pueden marcar como ausentes citas programadas."
            );
        }

        cita.setEstado(EstadoCita.AUSENTE);
    }
}
