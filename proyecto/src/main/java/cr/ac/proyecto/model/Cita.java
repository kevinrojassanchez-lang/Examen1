package cr.ac.proyecto.model;

import cr.ac.proyecto.exception.CitaNoModificableException;
import java.time.LocalDateTime;

public class Cita implements Reprogramable, Cancelable {

    private Cliente cliente;
    private Profesional profesional;
    private Servicio servicio;
    private LocalDateTime fechaHora;
    private EstadoCita estado;

    public Cita(Cliente cliente, Profesional profesional,
            Servicio servicio, LocalDateTime fechaHora) {

        this.cliente = cliente;
        this.profesional = profesional;
        this.servicio = servicio;
        this.fechaHora = fechaHora;
        this.estado = EstadoCita.PROGRAMADA;
    }

    public Cliente getCliente() { return cliente; }
    public Profesional getProfesional() { return profesional; }
    public Servicio getServicio() { return servicio; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public EstadoCita getEstado() { return estado; }
    public void setFechaHora(LocalDateTime fechaHora) {this.fechaHora = fechaHora; }
    public void setEstado(EstadoCita estado) { this.estado = estado;}

    @Override
    public void reprogramar(LocalDateTime nuevaFecha)
            throws CitaNoModificableException {

        if (estado != EstadoCita.PROGRAMADA) {
            throw new CitaNoModificableException(
                "Solo se pueden reprogramar citas programadas."
            );
        }

        if (nuevaFecha == null) {
            throw new IllegalArgumentException(
                "La nueva fecha no puede ser nula."
            );
        }

        this.fechaHora = nuevaFecha;
    }

    @Override
    public void cancelar() throws CitaNoModificableException {

        if (estado != EstadoCita.PROGRAMADA) {
            throw new CitaNoModificableException(
                "Solo se pueden cancelar citas programadas."
            );
        }

        this.estado = EstadoCita.CANCELADA;
    }    
}

