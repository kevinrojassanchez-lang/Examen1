package cr.ac.proyecto;

import cr.ac.proyecto.model.*;
import cr.ac.proyecto.exception.*;
import java.time.LocalDateTime;

public class PruebaModelo {

    public static void main(String[] args) {

        System.out.println("===== PRUEBA DEL MODELO =====");

        // 1. Crear objetos
        Cliente cliente = new Cliente(
                "101", "Ana Rodríguez",
                "8888-1111", "ana@gmail.com"
        );

        Profesional profesional = new Profesional(
                "201", "Carlos Méndez",
                "8888-2222", "carlos@gmail.com",
                Especialidad.MEDICINA_GENERAL, "8:00 a 16:00"
        );

        Servicio servicio = new Servicio(
                "S01", "Consulta general", 30, 15000
        );

        GestorCitas gestor = new GestorCitas();

        // 2. Herencia y polimorfismo
        System.out.println("\n--- HERENCIA Y POLIMORFISMO ---");

        Persona persona1 = cliente;
        Persona persona2 = profesional;

        System.out.println(persona1.mostrarInformacion());
        System.out.println(persona2.mostrarInformacion());

        // 3. Registrar una cita
        System.out.println("\n--- REGISTRO DE CITA ---");

        LocalDateTime fecha = LocalDateTime.now().plusDays(2);

        Cita cita1 = new Cita(
                cliente, profesional, servicio, fecha
        );

        try {
            gestor.agregarCita(cita1);
            System.out.println("Cita registrada correctamente.");
        } catch (HorarioNoDisponibleException
                | IllegalArgumentException ex) {
            System.out.println("Error al registrar: " + ex.getMessage());
        }

        // 4. Intentar registrar otra cita en el mismo horario
        System.out.println("\n--- VALIDAR DISPONIBILIDAD ---");

        Cita cita2 = new Cita(
                cliente, profesional, servicio, fecha
        );

        try {
            gestor.agregarCita(cita2);
            System.out.println("Segunda cita registrada.");
        } catch (HorarioNoDisponibleException ex) {
            System.out.println("Conflicto detectado: " + ex.getMessage());
        }

        // 5. Consultar las citas
        System.out.println("\n--- LISTADO DE CITAS ---");
        System.out.println("Total de citas: " + gestor.listarCitas().size());

        // 6. Reprogramar la primera cita
        System.out.println("\n--- REPROGRAMACIÓN ---");

        try {
            gestor.reprogramarCita(
                    cita1, LocalDateTime.now().plusDays(3)
            );
            System.out.println("Cita reprogramada correctamente.");
        } catch (CitaNoModificableException
                | HorarioNoDisponibleException ex) {
            System.out.println("Error al reprogramar: " + ex.getMessage());
        }

        // 7. Cancelar la primera cita
        System.out.println("\n--- CANCELACIÓN ---");

        try {
            gestor.cancelarCita(cita1);
            System.out.println("Cita cancelada correctamente.");
        } catch (CitaNoModificableException ex) {
            System.out.println("Error al cancelar: " + ex.getMessage());
        }
        
        // 8. Intentar cancelar nuevamente una cita cancelada
        System.out.println("\n--- VALIDAR EXCEPCION PERSONALIZADA ---");

        try {
            gestor.cancelarCita(cita1);
            System.out.println("Error: se permitió cancelar nuevamente.");
        } catch (CitaNoModificableException ex) {
            System.out.println("Excepción controlada: " + ex.getMessage());
        }

        // 9. Comprobar el estado final
        System.out.println("\n--- ESTADO FINAL ---");
        System.out.println("Estado de cita 1: " + cita1.getEstado());
        System.out.println("Citas almacenadas: " + gestor.listarCitas().size());

        System.out.println("\n===== FIN DE LA PRUEBA =====");
    }
}
