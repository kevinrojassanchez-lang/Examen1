package cr.ac.proyecto.model;

import java.time.LocalDateTime;

public class Cita implements Reprogramable {

    private Cliente cliente;
    private Profesional profesional;
    private Servicio servicio;
    private LocalDateTime fechaHora;
    private EstadoCita estado;

    public Cita(Cliente cliente, Profesional profesional,Servicio servicio,
            LocalDateTime fechaHora) {
        
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

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora; }
    
    @Override
    public void reprogramar(LocalDateTime nuevaFecha) {
        this.fechaHora = nuevaFecha; }

    public void setEstado(EstadoCita estado) { this.estado = estado; }
}
