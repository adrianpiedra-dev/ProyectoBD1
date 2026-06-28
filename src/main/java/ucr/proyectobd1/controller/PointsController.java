package ucr.proyectobd1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ucr.proyectobd1.data.PointsDAO;
import ucr.proyectobd1.model.Points;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PointsController {

    @FXML
    private ImageView imageviewLogo;

    @FXML
    private Button btnBackMenu;

    @FXML
    private TableView<Points> tableviewPoints;

    @FXML
    private TableColumn<Points, String> columnMobilePhone;

    @FXML
    private TableColumn<Points, Date> columnDate;

    @FXML
    private TableColumn<Points, Double> columnQuantity;

    @FXML
    private TableColumn<Points, Double> columnBalance;

    @FXML
    private ComboBox<String> comboMobilePhone;

    @FXML
    private DatePicker datePickerDate;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtBalance;

    @FXML
    private Label lblError;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    private PointsDAO pointsDAO;
    private Points selectedPoints = null;

    @FXML
    public void initialize() {
        pointsDAO = new PointsDAO();

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);
        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        columnMobilePhone.setCellValueFactory(new PropertyValueFactory<>("mobileLinePhone"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        loadMobileLinePhones();
        loadPoints();
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        btnBackMenu.setOnAction(e -> openWindow("/ucr/proyectobd1/hello-view.fxml", e));
        btnNew.setOnAction(e -> handleNew());
        btnSave.setOnAction(e -> handleSave());
        btnDelete.setOnAction(e -> handleDelete());
        btnSearch.setOnAction(e -> handleSearch());
        btnClear.setOnAction(e -> handleClear());

        tableviewPoints.setOnMouseClicked(e -> selectPoints());
        comboMobilePhone.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadPointsByMobilePhone(newVal);
            }
        });
    }

    private void loadMobileLinePhones() {
        try {
            List<String> phones = pointsDAO.getAllMobileLinePhones();
            ObservableList<String> observableList = FXCollections.observableArrayList(phones);
            comboMobilePhone.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al cargar líneas móviles", e.getMessage());
        }
    }

    private void loadPoints() {
        try {
            List<Points> points = pointsDAO.getAllPoints();
            ObservableList<Points> observableList = FXCollections.observableArrayList(points);
            tableviewPoints.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al cargar puntos", e.getMessage());
        }
    }

    private void loadPointsByMobilePhone(String mobilePhone) {
        try {
            List<Points> points = pointsDAO.searchByMobileLinePhone(mobilePhone);
            ObservableList<Points> observableList = FXCollections.observableArrayList(points);
            tableviewPoints.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al buscar puntos", e.getMessage());
        }
    }

    private void handleNew() {
        clearForm();
    }

    private void handleSave() {
        if (!validateForm()) {
            return;
        }

        try {
            String mobilePhone = comboMobilePhone.getValue();
            Date date = Date.valueOf(datePickerDate.getValue());
            double quantity = Double.parseDouble(txtQuantity.getText().trim());
            double balance = Double.parseDouble(txtBalance.getText().trim());

            Points points = new Points(mobilePhone, date, quantity, balance);
            pointsDAO.insertPoints(points);
            showSuccess("Puntos agregados exitosamente");

            if (comboMobilePhone.getValue() != null) {
                loadPointsByMobilePhone(comboMobilePhone.getValue());
            } else {
                loadPoints();
            }
            clearForm();

        } catch (NumberFormatException e) {
            showError("Error de formato", "La cantidad y saldo deben ser números válidos");
        } catch (SQLException e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleDelete() {
        if (selectedPoints == null) {
            showError("Selección requerida", "Debe seleccionar un registro de puntos de la tabla");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Desea eliminar este registro de puntos?");
        alert.setContentText("Línea: " + selectedPoints.getMobileLinePhone() + "\nFecha: " + selectedPoints.getDate());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                pointsDAO.deletePoints(selectedPoints.getMobileLinePhone(), selectedPoints.getDate());
                showSuccess("Puntos eliminados exitosamente");
                if (comboMobilePhone.getValue() != null) {
                    loadPointsByMobilePhone(comboMobilePhone.getValue());
                } else {
                    loadPoints();
                }
                clearForm();
            } catch (SQLException e) {
                showError("Error al eliminar", e.getMessage());
            }
        }
    }

    private void handleSearch() {
        String mobilePhone = comboMobilePhone.getValue();
        if (mobilePhone == null || mobilePhone.isEmpty()) {
            showError("Selección requerida", "Debe seleccionar una línea móvil");
            return;
        }

        loadPointsByMobilePhone(mobilePhone);
    }

    private void handleClear() {
        clearForm();
        tableviewPoints.getSelectionModel().clearSelection();
        selectedPoints = null;
    }

    private void selectPoints() {
        selectedPoints = tableviewPoints.getSelectionModel().getSelectedItem();
        if (selectedPoints != null) {
            comboMobilePhone.setValue(selectedPoints.getMobileLinePhone());
            datePickerDate.setValue(selectedPoints.getDate().toLocalDate());
            txtQuantity.setText(String.valueOf(selectedPoints.getQuantity()));
            txtBalance.setText(String.valueOf(selectedPoints.getBalance()));
        }
    }

    private boolean validateForm() {
        lblError.setText("");

        String mobilePhone = comboMobilePhone.getValue();
        LocalDate date = datePickerDate.getValue();
        String quantityStr = txtQuantity.getText().trim();
        String balanceStr = txtBalance.getText().trim();

        if (mobilePhone == null || mobilePhone.isEmpty()) {
            showError("Campo requerido", "Debe seleccionar una línea móvil");
            return false;
        }

        if (date == null) {
            showError("Campo requerido", "Debe seleccionar una fecha");
            return false;
        }

        if (quantityStr.isEmpty() || balanceStr.isEmpty()) {
            showError("Campos requeridos", "Todos los campos son obligatorios");
            return false;
        }

        try {
            double quantity = Double.parseDouble(quantityStr);
            if (quantity < 0) {
                showError("Valor inválido", "La cantidad no puede ser negativa");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Formato inválido", "La cantidad debe ser un número válido");
            return false;
        }

        try {
            double balance = Double.parseDouble(balanceStr);
            if (balance < 0) {
                showError("Valor inválido", "El saldo no puede ser negativo");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Formato inválido", "El saldo debe ser un número válido");
            return false;
        }

        return true;
    }

    private void clearForm() {
        datePickerDate.setValue(null);
        txtQuantity.setText("");
        txtBalance.setText("");
        lblError.setText("");
        selectedPoints = null;
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        lblError.setText(title + ": " + message);
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openWindow(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showError("Error", "No se pudo cargar la ventana: " + e.getMessage());
        }
    }
}
