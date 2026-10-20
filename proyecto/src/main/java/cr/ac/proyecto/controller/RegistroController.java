package cr.ac.proyecto.controller;

import cr.ac.proyecto.App;
import cr.ac.proyecto.exception.HorarioNoDisponibleException;
import cr.ac.proyecto.model.Cita;
import cr.ac.proyecto.model.Cliente;
import cr.ac.proyecto.model.Especialidad;
import cr.ac.proyecto.model.GestorCitas;
import cr.ac.proyecto.model.Profesional;
import cr.ac.proyecto.model.Servicio;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class RegistroController {

    private final GestorCitas gestor = App.getGestorCitas();

    private ArrayList<Profesional> profesionales =
            new ArrayList<Profesional>();

    private ArrayList<Servicio> servicios =
            new ArrayList<Servicio>();

    @FXML
    private TextField txtIdentificacionCliente;

    @FXML
    private TextField txtNombreCliente;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private ComboBox<Profesional> cmbProfesional;

    @FXML
    private ComboBox<Servicio> cmbServicio;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField txtHora;

    @FXML
    private Label lblMensaje;

    @FXML
    private void initialize() {

        Profesional profesional = new Profesional(
                "PRO001",
                "Profesional de prueba",
                "8888-1111",
                "profesional@ejemplo.com",
                Especialidad.MEDICINA_GENERAL,
                "Lunes a viernes"
        );

        profesionales.add(profesional);

        Servicio servicio = new Servicio(
                "SER001",
                "Consulta general",
                30,
                15000.00
        );

        servicios.add(servicio);

        cmbProfesional.getItems().addAll(profesionales);
        cmbServicio.getItems().addAll(servicios);

        cmbProfesional.setConverter(
                new StringConverter<Profesional>() {

            @Override
            public String toString(Profesional p) {
                if (p == null) {
                    return "";
                }

                return p.getNombre() + " - " + p.getEspecialidad();
            }

            @Override
            public Profesional fromString(String texto) {
                return null;
            }
        });

        cmbServicio.setConverter(
                new StringConverter<Servicio>() {

            @Override
            public String toString(Servicio s) {
                if (s == null) {
                    return "";
                }

                return s.getNombre() + " ("
                        + s.getDuracion() + " minutos)";
            }

            @Override
            public Servicio fromString(String texto) {
                return null;
            }
        });
    }

    @FXML
    private void guardarCita() {

        try {
            String identificacion =
                    txtIdentificacionCliente.getText().trim();

            String nombre = txtNombreCliente.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();

            if (identificacion.isEmpty()
                    || nombre.isEmpty()
                    || telefono.isEmpty()
                    || correo.isEmpty()
                    || cmbProfesional.getValue() == null
                    || cmbServicio.getValue() == null
                    || dpFecha.getValue() == null
                    || txtHora.getText().trim().isEmpty()) {

                mostrarMensaje(
                        Alert.AlertType.WARNING,
                        "Campos obligatorios",
                        "Complete todos los campos antes de guardar."
                );
                return;
            }

            if (!correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                mostrarMensaje(
                        Alert.AlertType.WARNING,
                        "Correo inválido",
                        "Ingrese una dirección de correo válida."
                );
                return;
            }

            LocalTime hora = LocalTime.parse(
                    txtHora.getText().trim()
            );

            LocalDateTime fechaHora = LocalDateTime.of(
                    dpFecha.getValue(),
                    hora
            );

            Cliente cliente = new Cliente(
                    identificacion,
                    nombre,
                    telefono,
                    correo
            );

            Profesional profesional = cmbProfesional.getValue();
            Servicio servicio = cmbServicio.getValue();

            Cita cita = new Cita(
                    cliente,
                    profesional,
                    servicio,
                    fechaHora
            );

            gestor.agregarCita(cita);

            mostrarMensaje(
                    Alert.AlertType.INFORMATION,
                    "Cita registrada",
                    "La cita se registró correctamente.\n"
                    + "Cliente: " + nombre + "\n"
                    + "Profesional: " + profesional.getNombre() + "\n"
                    + "Servicio: " + servicio.getNombre() + "\n"
                    + "Fecha y hora: " + fechaHora
            );

            limpiarFormulario();

        } catch (DateTimeParseException e) {
            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Fecha u hora inválida",
                    "Verifique la fecha y escriba la hora como HH:mm, "
                    + "por ejemplo: 14:30."
            );

        } catch (HorarioNoDisponibleException e) {
            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Horario no disponible",
                    e.getMessage()
            );

        } catch (IllegalArgumentException e) {
            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "No se pudo registrar",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void volverMenu() {

        try {
            App.cambiarVista("principal");

        } catch (IOException | NullPointerException
                | IllegalStateException e) {

            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    "Error de navegación",
                    "No se pudo regresar al menú principal."
            );
        }
    }

    private void limpiarFormulario() {

        txtIdentificacionCliente.clear();
        txtNombreCliente.clear();
        txtTelefono.clear();
        txtCorreo.clear();

        cmbProfesional.getSelectionModel().clearSelection();
        cmbServicio.getSelectionModel().clearSelection();

        dpFecha.setValue(null);
        txtHora.clear();

        lblMensaje.setText("Cita registrada correctamente.");
    }

    private void mostrarMensaje(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();

        lblMensaje.setText(titulo);
    }
}