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
import ucr.proyectobd1.data.PackageDAO;
import ucr.proyectobd1.model.Package;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PackageController {

    @FXML
    private ImageView imageviewLogo;

    @FXML
    private Button btnBackMenu;

    @FXML
    private TableView<Package> tableviewPackages;

    @FXML
    private TableColumn<Package, String> columnCode;

    @FXML
    private TableColumn<Package, String> columnName;

    @FXML
    private TableColumn<Package, Integer> columnValidity;

    @FXML
    private TableColumn<Package, Double> columnPrice;

    @FXML
    private TableColumn<Package, Integer> columnQuantity;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtValidity;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

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

    private PackageDAO packageDAO;
    private Package selectedPackage = null;
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        packageDAO = new PackageDAO();

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);
        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnValidity.setCellValueFactory(new PropertyValueFactory<>("validity"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        loadPackages();
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

        tableviewPackages.setOnMouseClicked(e -> selectPackage());
    }

    private void loadPackages() {
        try {
            List<Package> packages = packageDAO.getAllPackages();
            ObservableList<Package> observableList = FXCollections.observableArrayList(packages);
            tableviewPackages.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al cargar paquetes", e.getMessage());
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
            int validity = Integer.parseInt(txtValidity.getText().trim());
            double price = Double.parseDouble(txtPrice.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());

            if (isEditMode && selectedPackage != null) {
                Package pkg = new Package(code, name, validity, price, quantity);
                packageDAO.updatePackage(pkg);
                showSuccess("Paquete actualizado exitosamente");
                isEditMode = false;
            } else {
                Package pkg = new Package(code, name, validity, price, quantity);
                packageDAO.insertPackage(pkg);
                showSuccess("Paquete creado exitosamente");
            }

            loadPackages();
            clearForm();

        } catch (NumberFormatException e) {
            showError("Error de formato", "Los números deben ser válidos");
        } catch (SQLException e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleEdit() {
        if (selectedPackage == null) {
            showError("Selección requerida", "Debe seleccionar un paquete de la tabla");
            return;
        }

        txtCode.setText(selectedPackage.getCode());
        txtCode.setDisable(true);
        txtName.setText(selectedPackage.getName());
        txtValidity.setText(String.valueOf(selectedPackage.getValidity()));
        txtPrice.setText(String.valueOf(selectedPackage.getPrice()));
        txtQuantity.setText(String.valueOf(selectedPackage.getQuantity()));

        isEditMode = true;
        btnSave.setStyle("-fx-font-size: 12;");
    }

    private void handleDelete() {
        if (selectedPackage == null) {
            showError("Selección requerida", "Debe seleccionar un paquete de la tabla");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Cuidado: Este registro está relacionado con otras tablas.");
        alert.setContentText("Su eliminación puede afectar la integridad de la información.\n\n¿Desea continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                packageDAO.deletePackage(selectedPackage.getCode());
                showSuccess("Paquete eliminado exitosamente");
                loadPackages();
                clearForm();
            } catch (SQLException e) {
                showError("Error al eliminar", e.getMessage());
            }
        }
    }

    private void handleSearch() {
        String code = txtCode.getText().trim();
        if (code.isEmpty()) {
            showError("Campo requerido", "Ingrese el código del paquete");
            return;
        }

        try {
            Package pkg = packageDAO.searchPackage(code);
            if (pkg != null) {
                selectedPackage = pkg;
                txtCode.setText(pkg.getCode());
                txtName.setText(pkg.getName());
                txtValidity.setText(String.valueOf(pkg.getValidity()));
                txtPrice.setText(String.valueOf(pkg.getPrice()));
                txtQuantity.setText(String.valueOf(pkg.getQuantity()));
                tableviewPackages.getSelectionModel().clearSelection();
            } else {
                showError("No encontrado", "No existe paquete con código: " + code);
                clearForm();
            }
        } catch (SQLException e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleClear() {
        clearForm();
        tableviewPackages.getSelectionModel().clearSelection();
        selectedPackage = null;
        isEditMode = false;
        txtCode.setDisable(false);
    }

    private void selectPackage() {
        selectedPackage = tableviewPackages.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            txtCode.setText(selectedPackage.getCode());
            txtName.setText(selectedPackage.getName());
            txtValidity.setText(String.valueOf(selectedPackage.getValidity()));
            txtPrice.setText(String.valueOf(selectedPackage.getPrice()));
            txtQuantity.setText(String.valueOf(selectedPackage.getQuantity()));
            isEditMode = false;
            txtCode.setDisable(false);
        }
    }

    private boolean validateForm() {
        lblError.setText("");

        String code = txtCode.getText().trim();
        String name = txtName.getText().trim();
        String validityStr = txtValidity.getText().trim();
        String priceStr = txtPrice.getText().trim();
        String quantityStr = txtQuantity.getText().trim();

        if (code.isEmpty() || name.isEmpty() || validityStr.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
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

        try {
            int validity = Integer.parseInt(validityStr);
            if (validity <= 0) {
                showError("Valor inválido", "La vigencia debe ser positiva");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Formato inválido", "La vigencia debe ser un número entero");
            return false;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price < 0) {
                showError("Valor inválido", "El precio no puede ser negativo");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Formato inválido", "El precio debe ser un número válido");
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                showError("Valor inválido", "La cantidad debe ser positiva");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Formato inválido", "La cantidad debe ser un número entero");
            return false;
        }

        return true;
    }

    private void clearForm() {
        txtCode.setText("");
        txtName.setText("");
        txtValidity.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        lblError.setText("");
        txtCode.setDisable(false);
        selectedPackage = null;
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
