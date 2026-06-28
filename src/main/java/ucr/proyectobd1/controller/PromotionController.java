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
import ucr.proyectobd1.data.PromotionData;
import ucr.proyectobd1.model.Promotion;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PromotionController {

    @FXML
    private ImageView imageviewLogo;

    @FXML
    private Button btnBackMenu;

    @FXML
    private TableView<Promotion> tableviewPromotions;

    @FXML
    private TableColumn<Promotion, String> columnCode;

    @FXML
    private TableColumn<Promotion, String> columnName;

    @FXML
    private TableColumn<Promotion, String> columnDescription;

    @FXML
    private TableColumn<Promotion, Date> columnIDate;

    @FXML
    private TableColumn<Promotion, Date> columnFDate;

    @FXML
    private TableColumn<Promotion, Float> columnPDiscount;

    @FXML
    private TableColumn<Promotion, String> columnTypePromotion;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtDescription;

    @FXML
    private DatePicker dpIDate;

    @FXML
    private DatePicker dpFDate;

    @FXML
    private TextField txtPDiscount;

    @FXML
    private TextField txtTypePromotion;

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

    private PromotionData promotionData;
    private Promotion selectedPromotion = null;
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        promotionData = new PromotionData();

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);
        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnIDate.setCellValueFactory(new PropertyValueFactory<>("iDate"));
        columnFDate.setCellValueFactory(new PropertyValueFactory<>("fDate"));
        columnPDiscount.setCellValueFactory(new PropertyValueFactory<>("pDiscount"));
        columnTypePromotion.setCellValueFactory(new PropertyValueFactory<>("typePromotion"));

        loadPromotions();
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

        tableviewPromotions.setOnMouseClicked(e -> selectPromotion());
    }

    private void loadPromotions() {
        try {
            List<Promotion> promotions = promotionData.getAllPromotions();
            ObservableList<Promotion> observableList = FXCollections.observableArrayList(promotions);
            tableviewPromotions.setItems(observableList);
        } catch (Exception e) {
            showError("Error al cargar promociones", e.getMessage());
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
            Date iDate = Date.valueOf(dpIDate.getValue());
            Date fDate = Date.valueOf(dpFDate.getValue());
            float pDiscount = Float.parseFloat(txtPDiscount.getText().trim());
            String typePromotion = txtTypePromotion.getText().trim();

            if (isEditMode && selectedPromotion != null) {
                Promotion promo = new Promotion(code, name, description, iDate, fDate, pDiscount, typePromotion);
                promotionData.update(promo);
                showSuccess("Promoción actualizada exitosamente");
                isEditMode = false;
            } else {
                Promotion promo = new Promotion(code, name, description, iDate, fDate, pDiscount, typePromotion);
                if (promotionData.exist(code)) {
                    showError("Error", "Ya existe una promoción con el código " + code);
                    return;
                }
                promotionData.insert(promo);
                showSuccess("Promoción creada exitosamente");
            }

            loadPromotions();
            clearForm();

        } catch (NumberFormatException e) {
            showError("Error de formato", "Los números deben ser válidos");
        } catch (Exception e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleEdit() {
        if (selectedPromotion == null) {
            showError("Selección requerida", "Debe seleccionar una promoción de la tabla");
            return;
        }

        txtCode.setText(selectedPromotion.getCode());
        txtCode.setDisable(true);
        txtName.setText(selectedPromotion.getName());
        txtDescription.setText(selectedPromotion.getDescription());
        dpIDate.setValue(selectedPromotion.getiDate() != null ? selectedPromotion.getiDate().toLocalDate() : null);
        dpFDate.setValue(selectedPromotion.getfDate() != null ? selectedPromotion.getfDate().toLocalDate() : null);
        txtPDiscount.setText(String.valueOf(selectedPromotion.getpDiscount()));
        txtTypePromotion.setText(selectedPromotion.getTypePromotion());

        isEditMode = true;
        btnSave.setStyle("-fx-font-size: 12;");
    }

    private void handleDelete() {
        if (selectedPromotion == null) {
            showError("Selección requerida", "Debe seleccionar una promoción de la tabla");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Cuidado: Este registro podría estar relacionado con otras tablas.");
        alert.setContentText("¿Desea continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                promotionData.delete(selectedPromotion);
                showSuccess("Promoción eliminada exitosamente");
                loadPromotions();
                clearForm();
            } catch (Exception e) {
                showError("Error al eliminar", e.getMessage());
            }
        }
    }

    private void handleSearch() {
        String code = txtCode.getText().trim();
        if (code.isEmpty()) {
            showError("Campo requerido", "Ingrese el código de la promoción");
            return;
        }

        try {
            Promotion promo = promotionData.getPromotion(code);
            if (promo != null) {
                selectedPromotion = promo;
                txtCode.setText(promo.getCode());
                txtName.setText(promo.getName());
                txtDescription.setText(promo.getDescription());
                dpIDate.setValue(promo.getiDate() != null ? promo.getiDate().toLocalDate() : null);
                dpFDate.setValue(promo.getfDate() != null ? promo.getfDate().toLocalDate() : null);
                txtPDiscount.setText(String.valueOf(promo.getpDiscount()));
                txtTypePromotion.setText(promo.getTypePromotion());
                tableviewPromotions.getSelectionModel().clearSelection();
            } else {
                showError("No encontrado", "No existe promoción con código: " + code);
                clearForm();
            }
        } catch (Exception e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleClear() {
        clearForm();
        tableviewPromotions.getSelectionModel().clearSelection();
        selectedPromotion = null;
        isEditMode = false;
        txtCode.setDisable(false);
    }

    private void selectPromotion() {
        selectedPromotion = tableviewPromotions.getSelectionModel().getSelectedItem();
        if (selectedPromotion != null) {
            txtCode.setText(selectedPromotion.getCode());
            txtName.setText(selectedPromotion.getName());
            txtDescription.setText(selectedPromotion.getDescription());
            dpIDate.setValue(selectedPromotion.getiDate() != null ? selectedPromotion.getiDate().toLocalDate() : null);
            dpFDate.setValue(selectedPromotion.getfDate() != null ? selectedPromotion.getfDate().toLocalDate() : null);
            txtPDiscount.setText(String.valueOf(selectedPromotion.getpDiscount()));
            txtTypePromotion.setText(selectedPromotion.getTypePromotion());
            isEditMode = false;
            txtCode.setDisable(false);
        }
    }

    private boolean validateForm() {
        lblError.setText("");

        String code = txtCode.getText().trim();
        String name = txtName.getText().trim();
        LocalDate iDate = dpIDate.getValue();
        LocalDate fDate = dpFDate.getValue();
        String pDiscountStr = txtPDiscount.getText().trim();
        String typePromotion = txtTypePromotion.getText().trim();

        if (code.isEmpty() || name.isEmpty() || iDate == null || fDate == null || pDiscountStr.isEmpty() || typePromotion.isEmpty()) {
            showError("Campos requeridos", "Casi todos los campos son obligatorios");
            return false;
        }

        try {
            float pDiscount = Float.parseFloat(pDiscountStr);
            if (pDiscount < 0 || pDiscount > 100) {
                showError("Valor inválido", "El descuento debe estar entre 0 y 100");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Formato inválido", "El descuento debe ser un número válido");
            return false;
        }

        if (fDate.isBefore(iDate)) {
            showError("Fecha inválida", "La fecha final no puede ser menor a la fecha inicial");
            return false;
        }

        return true;
    }

    private void clearForm() {
        txtCode.setText("");
        txtName.setText("");
        txtDescription.setText("");
        dpIDate.setValue(null);
        dpFDate.setValue(null);
        txtPDiscount.setText("");
        txtTypePromotion.setText("");
        lblError.setText("");
        txtCode.setDisable(false);
        selectedPromotion = null;
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
            String css = getClass().getResource("/ucr/proyectobd1/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showError("Error", "No se pudo cargar la ventana: " + e.getMessage());
        }
    }
}
