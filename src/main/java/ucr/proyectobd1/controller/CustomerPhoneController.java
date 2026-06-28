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
import ucr.proyectobd1.data.CustomerPhoneDAO;
import ucr.proyectobd1.model.CustomerPhone;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerPhoneController {

    @FXML
    private ImageView imageviewLogo;

    @FXML
    private Button btnBackMenu;

    @FXML
    private TableView<CustomerPhone> tableviewPhones;

    @FXML
    private TableColumn<CustomerPhone, String> columnCustomerId;

    @FXML
    private TableColumn<CustomerPhone, String> columnPhone;

    @FXML
    private ComboBox<String> comboCustomerId;

    @FXML
    private TextField txtPhone;

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

    private CustomerPhoneDAO phoneDAO;
    private CustomerPhone selectedPhone = null;

    @FXML
    public void initialize() {
        phoneDAO = new CustomerPhoneDAO();

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);
        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        columnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        loadCustomerIds();
        loadPhones();
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        btnBackMenu.setOnAction(e -> openWindow("/ucr/proyectobd1/hello-view.fxml", e));
        btnNew.setOnAction(e -> handleNew());
        btnSave.setOnAction(e -> handleSave());
        btnDelete.setOnAction(e -> handleDelete());
        btnSearch.setOnAction(e -> handleSearch());
        btnClear.setOnAction(e -> handleClear());

        tableviewPhones.setOnMouseClicked(e -> selectPhone());
        comboCustomerId.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadPhonesByCustomer(newVal);
            }
        });
    }

    private void loadCustomerIds() {
        try {
            List<String> customerIds = phoneDAO.getAllCustomerIds();
            ObservableList<String> observableList = FXCollections.observableArrayList(customerIds);
            comboCustomerId.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al cargar clientes", e.getMessage());
        }
    }

    private void loadPhones() {
        try {
            List<CustomerPhone> phones = phoneDAO.getAllCustomerPhones();
            ObservableList<CustomerPhone> observableList = FXCollections.observableArrayList(phones);
            tableviewPhones.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al cargar teléfonos", e.getMessage());
        }
    }

    private void loadPhonesByCustomer(String customerId) {
        try {
            List<CustomerPhone> phones = phoneDAO.searchByCustomerId(customerId);
            ObservableList<CustomerPhone> observableList = FXCollections.observableArrayList(phones);
            tableviewPhones.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al buscar teléfonos", e.getMessage());
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
            String customerId = comboCustomerId.getValue();
            String phone = txtPhone.getText().trim();

            CustomerPhone customerPhone = new CustomerPhone(customerId, phone);
            phoneDAO.insertCustomerPhone(customerPhone);
            showSuccess("Teléfono agregado exitosamente");

            if (comboCustomerId.getValue() != null) {
                loadPhonesByCustomer(comboCustomerId.getValue());
            } else {
                loadPhones();
            }
            clearForm();

        } catch (SQLException e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleDelete() {
        if (selectedPhone == null) {
            showError("Selección requerida", "Debe seleccionar un teléfono de la tabla");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Desea eliminar este teléfono?");
        alert.setContentText("Cliente: " + selectedPhone.getCustomerId() + "\nTeléfono: " + selectedPhone.getPhone());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                phoneDAO.deleteCustomerPhone(selectedPhone.getCustomerId(), selectedPhone.getPhone());
                showSuccess("Teléfono eliminado exitosamente");
                if (comboCustomerId.getValue() != null) {
                    loadPhonesByCustomer(comboCustomerId.getValue());
                } else {
                    loadPhones();
                }
                clearForm();
            } catch (SQLException e) {
                showError("Error al eliminar", e.getMessage());
            }
        }
    }

    private void handleSearch() {
        String customerId = comboCustomerId.getValue();
        if (customerId == null || customerId.isEmpty()) {
            showError("Selección requerida", "Debe seleccionar un cliente");
            return;
        }

        loadPhonesByCustomer(customerId);
    }

    private void handleClear() {
        clearForm();
        tableviewPhones.getSelectionModel().clearSelection();
        selectedPhone = null;
    }

    private void selectPhone() {
        selectedPhone = tableviewPhones.getSelectionModel().getSelectedItem();
        if (selectedPhone != null) {
            comboCustomerId.setValue(selectedPhone.getCustomerId());
            txtPhone.setText(selectedPhone.getPhone());
        }
    }

    private boolean validateForm() {
        lblError.setText("");

        String customerId = comboCustomerId.getValue();
        String phone = txtPhone.getText().trim();

        if (customerId == null || customerId.isEmpty()) {
            showError("Campo requerido", "Debe seleccionar un cliente");
            return false;
        }

        if (phone.isEmpty()) {
            showError("Campo requerido", "Debe ingresar un teléfono");
            return false;
        }

        if (phone.length() > 20) {
            showError("Teléfono muy largo", "El teléfono no debe exceder 20 caracteres");
            return false;
        }

        if (!phone.matches("^[0-9\\-\\+\\s]+$")) {
            showError("Formato inválido", "El teléfono debe contener solo números, guiones y espacios");
            return false;
        }

        return true;
    }

    private void clearForm() {
        txtPhone.setText("");
        lblError.setText("");
        selectedPhone = null;
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
