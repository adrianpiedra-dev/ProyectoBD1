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
import ucr.proyectobd1.model.CommercialCategory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CommercialCategoryController {

    @FXML
    private ImageView imageviewLogo;

    @FXML
    private Button btnBackMenu;

    @FXML
    private TableView<CommercialCategory> tableviewCategories;

    @FXML
    private TableColumn<CommercialCategory, String> columnCode;

    @FXML
    private TableColumn<CommercialCategory, String> columnDescription;

    @FXML
    private TableColumn<CommercialCategory, Double> columnSpeed;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtSpeed;

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

    private CommercialCategoryDAO categoryDAO;
    private CommercialCategory selectedCategory = null;
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        categoryDAO = new CommercialCategoryDAO();

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);
        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnSpeed.setCellValueFactory(new PropertyValueFactory<>("speedMbps"));

        loadCategories();
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

        tableviewCategories.setOnMouseClicked(e -> selectCategory());
    }

    private void loadCategories() {
        try {
            List<CommercialCategory> categories = categoryDAO.getAllCommercialCategories();
            ObservableList<CommercialCategory> observableList = FXCollections.observableArrayList(categories);
            tableviewCategories.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al cargar categorías", e.getMessage());
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
            String description = txtDescription.getText().trim();
            double speed = Double.parseDouble(txtSpeed.getText().trim());

            if (isEditMode && selectedCategory != null) {
                CommercialCategory category = new CommercialCategory(code, description, speed);
                categoryDAO.updateCommercialCategory(category);
                showSuccess("Categoría actualizada exitosamente");
                isEditMode = false;
            } else {
                CommercialCategory category = new CommercialCategory(code, description, speed);
                categoryDAO.insertCommercialCategory(category);
                showSuccess("Categoría creada exitosamente");
            }

            loadCategories();
            clearForm();

        } catch (NumberFormatException e) {
            showError("Error de formato", "La velocidad debe ser un número válido");
        } catch (SQLException e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleEdit() {
        if (selectedCategory == null) {
            showError("Selección requerida", "Debe seleccionar una categoría de la tabla");
            return;
        }

        txtCode.setText(selectedCategory.getCode());
        txtCode.setDisable(true);
        txtDescription.setText(selectedCategory.getDescription());
        txtSpeed.setText(String.valueOf(selectedCategory.getSpeedMbps()));

        isEditMode = true;
        btnSave.setStyle("-fx-font-size: 12;");
    }

    private void handleDelete() {
        if (selectedCategory == null) {
            showError("Selección requerida", "Debe seleccionar una categoría de la tabla");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Cuidado: Este registro está relacionado con otras tablas.");
        alert.setContentText("Su eliminación puede afectar la integridad de la información.\n\n¿Desea continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                categoryDAO.deleteCommercialCategory(selectedCategory.getCode());
                showSuccess("Categoría eliminada exitosamente");
                loadCategories();
                clearForm();
            } catch (SQLException e) {
                showError("Error al eliminar", e.getMessage());
            }
        }
    }

    private void handleSearch() {
        String code = txtCode.getText().trim();
        if (code.isEmpty()) {
            showError("Campo requerido", "Ingrese el código de la categoría");
            return;
        }

        try {
            CommercialCategory category = categoryDAO.searchCommercialCategory(code);
            if (category != null) {
                selectedCategory = category;
                txtCode.setText(category.getCode());
                txtDescription.setText(category.getDescription());
                txtSpeed.setText(String.valueOf(category.getSpeedMbps()));
                tableviewCategories.getSelectionModel().clearSelection();
            } else {
                showError("No encontrado", "No existe categoría con código: " + code);
                clearForm();
            }
        } catch (SQLException e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleClear() {
        clearForm();
        tableviewCategories.getSelectionModel().clearSelection();
        selectedCategory = null;
        isEditMode = false;
        txtCode.setDisable(false);
    }

    private void selectCategory() {
        selectedCategory = tableviewCategories.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            txtCode.setText(selectedCategory.getCode());
            txtDescription.setText(selectedCategory.getDescription());
            txtSpeed.setText(String.valueOf(selectedCategory.getSpeedMbps()));
            isEditMode = false;
            txtCode.setDisable(false);
        }
    }

    private boolean validateForm() {
        lblError.setText("");

        String code = txtCode.getText().trim();
        String description = txtDescription.getText().trim();
        String speedStr = txtSpeed.getText().trim();

        if (code.isEmpty() || description.isEmpty() || speedStr.isEmpty()) {
            showError("Campos requeridos", "Todos los campos son obligatorios");
            return false;
        }

        if (code.length() > 20) {
            showError("Código muy largo", "El código no debe exceder 20 caracteres");
            return false;
        }

        if (description.length() > 255) {
            showError("Descripción muy larga", "La descripción no debe exceder 255 caracteres");
            return false;
        }

        try {
            double speed = Double.parseDouble(speedStr);
            if (speed < 0) {
                showError("Valor inválido", "La velocidad no puede ser negativa");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Formato inválido", "La velocidad debe ser un número válido");
            return false;
        }

        return true;
    }

    private void clearForm() {
        txtCode.setText("");
        txtDescription.setText("");
        txtSpeed.setText("");
        lblError.setText("");
        txtCode.setDisable(false);
        selectedCategory = null;
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
