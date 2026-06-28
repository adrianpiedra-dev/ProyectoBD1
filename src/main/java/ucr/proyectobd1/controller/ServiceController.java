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
import ucr.proyectobd1.data.CommercialCategoryDAO;
import ucr.proyectobd1.data.ServiceDAO;
import ucr.proyectobd1.model.CommercialCategory;
import ucr.proyectobd1.model.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServiceController {

    @FXML
    private ImageView imageviewLogo;

    @FXML
    private Button btnBackMenu;

    @FXML
    private TableView<Service> tableviewServices;

    @FXML
    private TableColumn<Service, String> columnCode;

    @FXML
    private TableColumn<Service, String> columnName;

    @FXML
    private TableColumn<Service, String> columnDescription;

    @FXML
    private TableColumn<Service, Double> columnCost;

    @FXML
    private TableColumn<Service, String> columnCategory;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtCost;

    @FXML
    private ComboBox<String> comboCategory;

    @FXML
    private Label lblError;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    private ServiceDAO serviceDAO;
    private CommercialCategoryDAO categoryDAO;
    private Service selectedService = null;
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        serviceDAO = new ServiceDAO();
        categoryDAO = new CommercialCategoryDAO();

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);
        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnCost.setCellValueFactory(new PropertyValueFactory<>("monthlyCost"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("categoryCode"));

        loadCategories();
        loadServices();
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        btnBackMenu.setOnAction(e -> openWindow("/ucr/proyectobd1/hello-view.fxml", e));
        btnNew.setOnAction(e -> handleNew());
        btnSave.setOnAction(e -> handleSave());
        btnEdit.setOnAction(e -> handleEdit());
        btnDelete.setOnAction(e -> handleDelete());
        btnSearch.setOnAction(e -> handleSearch());
        btnClear.setOnAction(e -> handleClear());

        tableviewServices.setOnMouseClicked(e -> selectService());
    }

    private void loadCategories() {
        try {
            List<CommercialCategory> categories = categoryDAO.getAllCommercialCategories();
            ObservableList<String> categoryCodes = FXCollections.observableArrayList();
            for (CommercialCategory cat : categories) {
                categoryCodes.add(cat.getCode());
            }
            comboCategory.setItems(categoryCodes);
        } catch (SQLException e) {
            showError("Error al cargar categorías", e.getMessage());
        }
    }

    private void loadServices() {
        try {
            List<Service> services = serviceDAO.getAllServices();
            ObservableList<Service> observableList = FXCollections.observableArrayList(services);
            tableviewServices.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al cargar servicios", e.getMessage());
        }
    }

    private void handleNew() {
        clearForm();
        isEditMode = false;
        btnSave.setStyle("-fx-font-size: 12;");
    }

    private void handleSave() {
        if (!validateForm()) {
            return;
        }

        try {
            String code = txtCode.getText().trim();
            String name = txtName.getText().trim();
            String description = txtDescription.getText().trim();
            double cost = Double.parseDouble(txtCost.getText().trim());
            String categoryCode = comboCategory.getValue();

            if (isEditMode && selectedService != null) {
                Service service = new Service(code, name, description, cost, categoryCode);
                serviceDAO.updateService(service);
                showSuccess("Servicio actualizado exitosamente");
                isEditMode = false;
            } else {
                Service service = new Service(code, name, description, cost, categoryCode);
                serviceDAO.insertService(service);
                showSuccess("Servicio creado exitosamente");
            }

            loadServices();
            clearForm();

        } catch (NumberFormatException e) {
            showError("Error de formato", "El costo debe ser un número válido");
        } catch (SQLException e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleEdit() {
        if (selectedService == null) {
            showError("Selección requerida", "Debe seleccionar un servicio de la tabla");
            return;
        }

        txtCode.setText(selectedService.getCode());
        txtCode.setDisable(true);
        txtName.setText(selectedService.getName());
        txtDescription.setText(selectedService.getDescription());
        txtCost.setText(String.valueOf(selectedService.getMonthlyCost()));
        comboCategory.setValue(selectedService.getCategoryCode());

        isEditMode = true;
        btnSave.setStyle("-fx-font-size: 12;");
    }

    private void handleDelete() {
        if (selectedService == null) {
            showError("Selección requerida", "Debe seleccionar un servicio de la tabla");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Cuidado: Este registro está relacionado con otras tablas.");
        alert.setContentText("Su eliminación puede afectar la integridad de la información.\n\n¿Desea continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                serviceDAO.deleteService(selectedService.getCode());
                showSuccess("Servicio eliminado exitosamente");
                loadServices();
                clearForm();
            } catch (SQLException e) {
                showError("Error al eliminar", e.getMessage());
            }
        }
    }

    private void handleSearch() {
        String code = txtCode.getText().trim();
        if (code.isEmpty()) {
            showError("Campo requerido", "Ingrese el código del servicio");
            return;
        }

        try {
            Service service = serviceDAO.searchService(code);
            if (service != null) {
                selectedService = service;
                txtCode.setText(service.getCode());
                txtName.setText(service.getName());
                txtDescription.setText(service.getDescription());
                txtCost.setText(String.valueOf(service.getMonthlyCost()));
                comboCategory.setValue(service.getCategoryCode());
                tableviewServices.getSelectionModel().clearSelection();
            } else {
                showError("No encontrado", "No existe servicio con código: " + code);
                clearForm();
            }
        } catch (SQLException e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleClear() {
        clearForm();
        tableviewServices.getSelectionModel().clearSelection();
        selectedService = null;
        isEditMode = false;
        txtCode.setDisable(false);
    }

    private void selectService() {
        selectedService = tableviewServices.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            txtCode.setText(selectedService.getCode());
            txtName.setText(selectedService.getName());
            txtDescription.setText(selectedService.getDescription());
            txtCost.setText(String.valueOf(selectedService.getMonthlyCost()));
            comboCategory.setValue(selectedService.getCategoryCode());
            isEditMode = false;
            txtCode.setDisable(false);
        }
    }

    private boolean validateForm() {
        lblError.setText("");

        String code = txtCode.getText().trim();
        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();
        String costStr = txtCost.getText().trim();
        String category = comboCategory.getValue();

        if (code.isEmpty() || name.isEmpty() || description.isEmpty() || costStr.isEmpty() || category == null) {
            showError("Campos requeridos", "Todos los campos son obligatorios");
            return false;
        }

        if (code.length() > 20) {
            showError("Código muy largo", "El código no debe exceder 20 caracteres");
            return false;
        }

        if (name.length() > 100) {
            showError("Nombre muy largo", "El nombre no debe exceder 100 caracteres");
            return false;
        }

        if (description.length() > 255) {
            showError("Descripción muy larga", "La descripción no debe exceder 255 caracteres");
            return false;
        }

        try {
            double cost = Double.parseDouble(costStr);
            if (cost < 0) {
                showError("Valor inválido", "El costo no puede ser negativo");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Formato inválido", "El costo debe ser un número válido");
            return false;
        }

        return true;
    }

    private void clearForm() {
        txtCode.setText("");
        txtName.setText("");
        txtDescription.setText("");
        txtCost.setText("");
        comboCategory.setValue(null);
        lblError.setText("");
        txtCode.setDisable(false);
        selectedService = null;
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
