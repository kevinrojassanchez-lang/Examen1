package cr.ac.proyecto.controller;

import cr.ac.proyecto.App;
import cr.ac.proyecto.model.Cita;
import cr.ac.proyecto.model.GestorCitas;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConsultaController {

    private final GestorCitas gestor = App.getGestorCitas();

    @FXML
    private TableView<Cita> tablaCitas;

    @FXML
    private TableColumn<Cita, String> colCliente;

    @FXML
    private TableColumn<Cita, String> colProfesional;

    @FXML
    private TableColumn<Cita, String> colServicio;

    @FXML
    private TableColumn<Cita, String> colFecha;

    @FXML
    private TableColumn<Cita, String> colEstado;

    @FXML
    private void initialize() {
        colCliente.setCellValueFactory(datos
                -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getCliente().getNombre()));

        colProfesional.setCellValueFactory(datos
                -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getProfesional().getNombre()));

        colServicio.setCellValueFactory(datos
                -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getServicio().getNombre()));

        colFecha.setCellValueFactory(datos
                -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getFechaHora().format(
                                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));

        colEstado.setCellValueFactory(datos
                -> new javafx.beans.property.SimpleStringProperty(
                        datos.getValue().getEstado().toString()));

        actualizarTabla();
    }

    @FXML
    private void actualizarTabla() {
        tablaCitas.setItems(
                FXCollections.observableArrayList(gestor.listarCitas()));
    }

    @FXML
    private void volverMenu() {
        try {
            App.cambiarVista("principal");
        } catch (IOException | NullPointerException
                | IllegalStateException e) {
            mostrarError("No se pudo regresar al menú principal.");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
