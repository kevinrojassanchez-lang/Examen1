# Corte 3 — Aplicación JavaFX integrada

## Pantallas implementadas

| Vista (FXML) | Controller | Propósito | Captura |
|---|---|---|---|
| `principal.fxml` | `PrincipalController` | Presenta el menú principal y permite acceder a las funciones de la aplicación. | `img/principal.png` |
| `registro.fxml` | `RegistroController` | Permite ingresar los datos necesarios para registrar una cita. | `img/registro.png` |
| `consulta.fxml` | `ConsultaController` | Muestra las citas registradas en una tabla para consultar su información. | `img/consulta.png` |
| `gestion.fxml` | `GestionController` | Permite gestionar las citas registradas, incluyendo cancelaciones, reprogramaciones y cambios de estado. | `img/gestion.png` |

## Navegación

La aplicación cuenta con una pantalla principal desde la cual se accede a las diferentes funcionalidades. La navegación se realiza mediante botones que permiten abrir las vistas de registro, consulta y gestión de citas.

Cada pantalla dispone de una opción para regresar al menú principal. De esta manera, el usuario puede cambiar entre las funcionalidades sin necesidad de cerrar la aplicación.

Las vistas se implementan mediante archivos FXML y la lógica de interacción se delega a sus respectivos controladores.

## Controllers

Cada controlador se encarga de gestionar los eventos de la interfaz correspondiente y de comunicarse con las clases del modelo.

- **PrincipalController:** administra las acciones del menú principal y la navegación hacia las demás pantallas.
- **RegistroController:** procesa los datos ingresados por el usuario, valida la información necesaria y solicita al gestor la creación de una cita.
- **ConsultaController:** obtiene las citas registradas mediante el gestor y actualiza la tabla de consulta.
- **GestionController:** permite seleccionar citas existentes y ejecutar operaciones de cancelación, reprogramación y cambio de estado, mostrando mensajes al usuario cuando corresponde.

Los controladores utilizan la clase `GestorCitas` para acceder a las operaciones del modelo. De esta manera, la interfaz no necesita implementar directamente las reglas de negocio.

## Datos compartidos entre vistas

Para conservar la información durante la navegación, la aplicación utiliza una única instancia compartida de `GestorCitas`, proporcionada mediante el método `App.getGestorCitas()`.

Los controladores de registro, consulta y gestión acceden a esa misma instancia. Así, las citas registradas pueden visualizarse posteriormente en la pantalla de consulta y utilizarse desde la pantalla de gestión.

La información se mantiene mientras la aplicación continúa ejecutándose. En esta implementación, la conservación de los datos depende del funcionamiento de la aplicación en memoria; no se documenta persistencia en una base de datos.

## Validaciones

| Vista | Campo o situación | Validación | Resultado |
|---|---|---|---|
| Registro | Campos obligatorios | Se comprueba que se ingresen los datos requeridos. | Se informa al usuario si falta información. |
| Registro | Correo electrónico | Se verifica el formato del correo ingresado. | Se muestra una alerta cuando el formato no es válido. |
| Registro | Fecha y hora | Se valida el formato de los datos y se comprueba la disponibilidad del horario mediante el modelo. | Se informa si los datos son incorrectos o el horario no está disponible. |
| Registro | Datos de la cita | Se comprueba que la información permita crear la cita. | Se muestra un mensaje de resultado de la operación. |
| Gestión | Reprogramación | Se procesa la nueva fecha y hora y se delegan las reglas de disponibilidad al gestor. | Se informa el resultado o el error correspondiente. |
| Gestión | Operaciones sobre citas | Las acciones se ejecutan mediante los métodos del gestor y respetan las reglas de negocio del modelo. | Se comunica al usuario el resultado de la operación. |

## Manejo de excepciones

La aplicación utiliza excepciones para comunicar situaciones que impiden realizar determinadas operaciones. Entre ellas se encuentran `HorarioNoDisponibleException` y `CitaNoModificableException`.

El flujo general es el siguiente:

**Usuario → Controller → GestorCitas → Modelo o excepción → Controller → Mensaje al usuario.**

Por ejemplo, al registrar una cita, el controlador procesa los datos del formulario e invoca `gestor.agregarCita(cita)`. Si el modelo determina que el horario no está disponible, puede lanzar `HorarioNoDisponibleException`. El controlador captura la excepción y muestra una alerta, evitando que el error se presente únicamente como una salida técnica en la consola.

De forma similar, las operaciones de cancelación y reprogramación gestionan las excepciones previstas por el modelo.

Fragmento ilustrativo del manejo de una excepción:

```java
try {
    gestor.agregarCita(cita);
    // Mostrar el resultado satisfactorio
} catch (HorarioNoDisponibleException e) {
    // Mostrar una alerta con el problema del horario
} catch (IllegalArgumentException e) {
    // Mostrar una alerta por datos no válidos
}
```

Este fragmento ilustra el flujo de manejo de errores; no sustituye el código completo del controlador.

## Relación interfaz–modelo

La aplicación utiliza una separación de responsabilidades entre la interfaz gráfica y la lógica del sistema.

- **Vista:** los archivos FXML definen los componentes y la estructura de las pantallas.
- **Controlador:** recibe las acciones del usuario, procesa los datos de entrada y coordina las operaciones.
- **Modelo:** contiene las clases que representan las personas, los servicios y las citas, así como las reglas de negocio.
- **GestorCitas:** centraliza las operaciones de registro, consulta, cancelación, reprogramación y actualización del estado de las citas.

Las clases del modelo no dependen directamente de los componentes visuales de JavaFX. Esto facilita mantener y probar las reglas de negocio independientemente de la interfaz.

## Decisiones relevantes

- Se mantuvieron separadas las vistas y sus respectivos controladores.
- Se utilizó FXML para definir las pantallas de la aplicación JavaFX.
- Se centralizaron las operaciones de las citas en `GestorCitas`.
- Se compartió una única instancia del gestor para mantener la coherencia de los datos entre las vistas.
- Se delegaron las validaciones de negocio al modelo cuando corresponde, evitando concentrar toda la lógica en la interfaz.
- Se utilizaron excepciones específicas para representar errores relacionados con la disponibilidad del horario y la modificación de citas.
- Se conservaron los métodos de acceso y modificación del modelo necesarios para las operaciones del sistema.

## Aspectos pendientes

- Incorporar las capturas reales de las cuatro pantallas en `docs/corte_3/img/`.
- Incorporar el diagrama UML final en `docs/corte_3/uml.png` o `docs/corte_3/uml.pdf`.
- Verificar que todas las rutas de las imágenes y del diagrama coincidan con los archivos entregados.

## UML final

Archivo previsto: `docs/corte_3/uml.png` o `docs/corte_3/uml.pdf`.

El diagrama debe representar las clases principales del modelo, sus atributos y métodos relevantes, las relaciones entre personas, clientes, profesionales, servicios y citas, y la participación de `GestorCitas` en las operaciones del sistema. También debe incluir las interfaces y excepciones relevantes para la gestión de citas.

## Distribución del trabajo

| Integrante | Aportes principales |
|---|---|
| Trabajo individual | Implementación e integración de las pantallas JavaFX, desarrollo de controladores, navegación entre vistas, conexión con el modelo, pruebas funcionales y documentación del proyecto. |
