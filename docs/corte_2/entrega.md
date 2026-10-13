# Corte 2 — Modelo orientado a objetos funcional

## Cambios al diseño

En este corte se implementó la lógica principal del modelo orientado a objetos para el sistema de gestión de citas.

Respecto al diseño inicial, se incorporó el método abstracto `mostrarInformacion()` en la clase `Persona`. Este método es implementado mediante sobrescritura en las clases `Cliente` y `Profesional`, permitiendo demostrar los conceptos de herencia y polimorfismo.

También se implementó la clase `GestorCitas`, encargada de administrar las citas, verificar la disponibilidad de los profesionales y controlar las operaciones de registro, reprogramación, cancelación y actualización del estado de las citas.

Se agregaron excepciones personalizadas para manejar los conflictos de horario y las operaciones no permitidas sobre las citas. Además, se creó la clase `PruebaModelo` para comprobar el funcionamiento de la lógica implementada.

El diagrama UML debe reflejar estos cambios, incluyendo el método abstracto de `Persona`, sus sobrescrituras y las relaciones entre las clases.

## Clases implementadas

| Clase | Paquete | Descripción |
|---|---|---|
| Persona | `cr.ac.proyecto.model` | Clase abstracta que representa los datos comunes de las personas. |
| Cliente | `cr.ac.proyecto.model` | Representa a un cliente y hereda de `Persona`. |
| Profesional | `cr.ac.proyecto.model` | Representa a un profesional, su especialidad y su horario. |
| Servicio | `cr.ac.proyecto.model` | Representa un servicio con código, nombre, duración y precio. |
| Cita | `cr.ac.proyecto.model` | Representa una cita con cliente, profesional, servicio, fecha y estado. |
| GestorCitas | `cr.ac.proyecto.model` | Administra las citas y aplica las reglas de disponibilidad y modificación. |
| Reprogramable | `cr.ac.proyecto.model` | Interfaz que define el método para reprogramar una cita. |
| EstadoCita | `cr.ac.proyecto.model` | Enumeración que representa los posibles estados de una cita. |
| Especialidad | `cr.ac.proyecto.model` | Enumeración con las especialidades disponibles para los profesionales. |
| HorarioNoDisponibleException | `cr.ac.proyecto.model` | Excepción personalizada para conflictos de horario. |
| CitaNoModificableException | `cr.ac.proyecto.model` | Excepción personalizada para operaciones no permitidas según el estado de la cita. |
| PruebaModelo | `cr.ac.proyecto` | Clase utilizada para ejecutar las pruebas del modelo. |

## Colecciones utilizadas

| Colección | Tipo (`List`, `Map`, …) | Clase donde se encuentra | Propósito |
|---|---|---|---|
| `citas` | `ArrayList<Cita>` | `GestorCitas` | Almacenar las citas registradas y permitir su administración. |
| `copia` | `ArrayList<Cita>` | `GestorCitas` | Devolver una copia de la lista de citas para consultar los registros sin entregar directamente la colección interna. |

## Ejemplo de herencia

```java
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
```

La clase `Cliente` hereda de `Persona` sus atributos y métodos comunes. Además, implementa su propia versión de `mostrarInformacion()`.

## Ejemplo de polimorfismo

```java
Persona persona1 = cliente;
Persona persona2 = profesional;

System.out.println(persona1.mostrarInformacion());
System.out.println(persona2.mostrarInformacion());
```

Se utilizan variables del tipo de la superclase `Persona` para referenciar objetos de las clases `Cliente` y `Profesional`. Al invocar `mostrarInformacion()`, cada objeto ejecuta su propia implementación del método.

## Interface

```java
public interface Reprogramable {
    void reprogramar(LocalDateTime nuevaFecha);
}
```

La clase `Cita` implementa la interfaz:

```java
public class Cita implements Reprogramable {
    @Override
    public void reprogramar(LocalDateTime nuevaFecha) {
        this.fechaHora = nuevaFecha;
    }
}
```

La interfaz `Reprogramable` establece un contrato para cambiar la fecha de una cita. La validación de las reglas de reprogramación se realiza mediante `GestorCitas`.

## Excepción personalizada

Ejemplo de la excepción para conflictos de horario:

```java
public class HorarioNoDisponibleException extends Exception {
    public HorarioNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
```

En `GestorCitas`, esta excepción se lanza cuando se intenta registrar una cita en un horario que se superpone con otra cita programada del mismo profesional.

También se utiliza `CitaNoModificableException` cuando se intenta cancelar, reprogramar, marcar como atendida o marcar como ausente una cita que no se encuentra en estado `PROGRAMADA`.

## Reglas implementadas

| Regla | Clase / método | Excepción que se lanza |
|---|---|---|
| No se permite registrar una cita nula. | `GestorCitas.agregarCita()` | `IllegalArgumentException` |
| La fecha de la cita debe ser futura. | `GestorCitas.agregarCita()` | `IllegalArgumentException` |
| No se permite registrar citas que se superpongan para el mismo profesional. | `GestorCitas.validarDisponibilidad()` | `HorarioNoDisponibleException` |
| Solo se pueden cancelar citas programadas. | `GestorCitas.cancelarCita()` | `CitaNoModificableException` |
| Solo se pueden reprogramar citas programadas. | `GestorCitas.reprogramarCita()` | `CitaNoModificableException` |
| Solo se pueden marcar como atendidas las citas programadas. | `GestorCitas.marcarAtendida()` | `CitaNoModificableException` |
| Solo se pueden marcar como ausentes las citas programadas. | `GestorCitas.marcarAusente()` | `CitaNoModificableException` |

## Evidencia de ejecución de la prueba del modelo

Clase de prueba: `cr.ac.proyecto.PruebaModelo`

La clase de prueba se ejecutó correctamente en NetBeans. La consola mostró el mensaje `BUILD SUCCESS`.

```text
===== PRUEBA DEL MODELO =====
--- HERENCIA Y POLIMORFISMO ---
Cliente: Ana Rodríguez | Identificación: 101
Profesional: Carlos Méndez | Especialidad: MEDICINA_GENERAL | Horario: 8:00 a 16:00

--- REGISTRO DE CITA ---
Cita registrada correctamente.

--- VALIDAR DISPONIBILIDAD ---
Conflicto detectado: El profesional ya tiene una cita en ese horario.

--- LISTADO DE CITAS ---
Total de citas: 1

--- REPROGRAMACIÓN ---
Cita reprogramada correctamente.

--- CANCELACIÓN ---
Cita cancelada correctamente.

--- VALIDAR EXCEPCION PERSONALIZADA ---
Excepción controlada: Solo se pueden cancelar citas programadas.

--- ESTADO FINAL ---
Estado de cita 1: CANCELADA
Citas almacenadas: 1
===== FIN DE LA PRUEBA =====

BUILD SUCCESS
```

Las pruebas permitieron comprobar la herencia, el polimorfismo, el uso de una interfaz, el registro de citas, la validación de disponibilidad, la reprogramación, la cancelación y el manejo de una excepción personalizada.

## Distribución del trabajo

| Integrante | Aportes principales |
|---|---|
| Kevin F. Rojas Sánchez | Implementación y prueba de las clases del modelo, aplicación de herencia y polimorfismo, uso de interfaces, creación de excepciones personalizadas, validación de reglas de negocio y documentación del Corte 2. |



