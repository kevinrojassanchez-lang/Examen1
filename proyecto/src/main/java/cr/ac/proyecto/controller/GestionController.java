package cr.ac.proyecto.controller;

import cr.ac.proyecto.App;
import cr.ac.proyecto.exception.CitaNoModificableException;
import cr.ac.proyecto.exception.HorarioNoDisponibleException;
import cr.ac.proyecto.model.Cita;
import cr.ac.proyecto.model.EstadoCita;
import cr.ac.proyecto.model.GestorCitas;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GestionController {

    private final GestorCitas gestor = App.getGestorCitas();

    @FXML
    private TableView<Cita> tablaCitas;

    @FXML
    private TableColumn<Cita, String> colCliente;

    @FXML
    private TableColumn<Cita, String> colProfesional;

    @FXML
    private TableColumn<Cita, String> colFecha;

    @FXML
    private TableColumn<Cita, EstadoCita> colEstado;

    @FXML
    private DatePicker dpNuevaFecha;

    @FXML
    private TextField txtNuevaHora;

    @FXML
    private ComboBox<EstadoCita> cmbEstado;

    @FXML
    private void initialize() {
        colCliente.setCellValueFactory(datos
                -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getCliente().getNombre()));

        colProfesional.setCellValueFactory(datos
                -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getProfesional().getNombre()));

        colFecha.setCellValueFactory(datos
                -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getFechaHora().toString()));

        colEstado.setCellValueFactory(datos
                -> new javafx.beans.property.SimpleObjectProperty<>(
                        datos.getValue().getEstado()));

        cmbEstado.setItems(FXCollections.observableArrayList(
                EstadoCita.values()));

        actualizarTabla();
    }

    @FXML
    private void actualizarTabla() {
        tablaCitas.setItems(
                FXCollections.observableArrayList(gestor.listarCitas()));
    }

    @FXML
    private void cancelarCita() {
        Cita cita = tablaCitas.getSelectionModel().getSelectedItem();

        if (cita == null) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Seleccione una cita", "Seleccione una cita de la tabla.");
            return;
        }

        try {
            gestor.cancelarCita(cita);
            actualizarTabla();
            mostrarMensaje(Alert.AlertType.INFORMATION,
                    "Cita cancelada", "La cita se canceló correctamente.");
        } catch (CitaNoModificableException e) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "No se puede cancelar", e.getMessage());
        }
    }

    @FXML
    private void reprogramarCita() {
        Cita cita = tablaCitas.getSelectionModel().getSelectedItem();

        if (cita == null) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Seleccione una cita", "Seleccione una cita de la tabla.");
            return;
        }

        if (dpNuevaFecha.getValue() == null
                || txtNuevaHora.getText().trim().isEmpty()) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Datos incompletos",
                    "Seleccione la nueva fecha e ingrese la hora.");
            return;
        }

        try {
            LocalTime hora = LocalTime.parse(txtNuevaHora.getText().trim());
            LocalDateTime nuevaFecha = LocalDateTime.of(
                    dpNuevaFecha.getValue(), hora);

            gestor.reprogramarCita(cita, nuevaFecha);
            actualizarTabla();

            mostrarMensaje(Alert.AlertType.INFORMATION,
                    "Cita reprogramada",
                    "La cita se reprogramó correctamente.");

        } catch (DateTimeParseException e) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Hora inválida", "Escriba la hora en formato HH:mm.");
        } catch (HorarioNoDisponibleException
                | CitaNoModificableException e) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "No se pudo reprogramar", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Fecha inválida", e.getMessage());
        }
    }

    @FXML
    private void cambiarEstado() {
        Cita cita = tablaCitas.getSelectionModel().getSelectedItem();
        EstadoCita nuevoEstado = cmbEstado.getValue();

        if (cita == null || nuevoEstado == null) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "Datos incompletos",
                    "Seleccione una cita y el estado que desea asignar.");
            return;
        }

        try {
            if (nuevoEstado == EstadoCita.CANCELADA) {
                gestor.cancelarCita(cita);
            } else if (nuevoEstado == EstadoCita.ATENDIDA) {
                gestor.marcarAtendida(cita);
            } else if (nuevoEstado == EstadoCita.AUSENTE) {
                gestor.marcarAusente(cita);
            } else {
                mostrarMensaje(Alert.AlertType.INFORMATION,
                        "Estado seleccionado",
                        "Para programar una cita nueva, utilice Registro.");
                return;
            }

            actualizarTabla();
            mostrarMensaje(Alert.AlertType.INFORMATION,
                    "Estado actualizado",
                    "El estado de la cita se actualizó correctamente.");

        } catch (CitaNoModificableException e) {
            mostrarMensaje(Alert.AlertType.WARNING,
                    "No se pudo cambiar el estado", e.getMessage());
        }
    }

    @FXML
    private void volverMenu() {
        try {
            App.cambiarVista("principal");
        } catch (IOException | NullPointerException
                | IllegalStateException e) {
            mostrarMensaje(Alert.AlertType.ERROR,
                    "Error de navegación",
                    "No se pudo regresar al menú principal.");
        }
    }

    private void mostrarMensaje(
            Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
