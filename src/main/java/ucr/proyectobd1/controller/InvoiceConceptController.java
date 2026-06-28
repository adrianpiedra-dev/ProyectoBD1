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
import ucr.proyectobd1.data.InvoiceConceptDAO;
import ucr.proyectobd1.model.InvoiceConcept;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InvoiceConceptController {

    @FXML
    private ImageView imageviewLogo;

    @FXML
    private Button btnBackMenu;

    @FXML
    private TableView<InvoiceConcept> tableviewConcepts;

    @FXML
    private TableColumn<InvoiceConcept, String> columnInvoiceNumber;

    @FXML
    private TableColumn<InvoiceConcept, String> columnDescription;

    @FXML
    private TableColumn<InvoiceConcept, Double> columnAmount;

    @FXML
    private ComboBox<String> comboInvoiceNumber;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtAmount;

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

    private InvoiceConceptDAO conceptDAO;
    private InvoiceConcept selectedConcept = null;

    @FXML
    public void initialize() {
        conceptDAO = new InvoiceConceptDAO();

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);
        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        columnInvoiceNumber.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        loadInvoiceNumbers();
        loadConcepts();
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        btnBackMenu.setOnAction(e -> openWindow("/ucr/proyectobd1/hello-view.fxml", e));
        btnNew.setOnAction(e -> handleNew());
        btnSave.setOnAction(e -> handleSave());
        btnDelete.setOnAction(e -> handleDelete());
        btnSearch.setOnAction(e -> handleSearch());
        btnClear.setOnAction(e -> handleClear());

        tableviewConcepts.setOnMouseClicked(e -> selectConcept());
        comboInvoiceNumber.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadConceptsByInvoice(newVal);
            }
        });
    }

    private void loadInvoiceNumbers() {
        try {
            List<String> invoiceNumbers = conceptDAO.getAllInvoiceNumbers();
            ObservableList<String> observableList = FXCollections.observableArrayList(invoiceNumbers);
            comboInvoiceNumber.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al cargar facturas", e.getMessage());
        }
    }

    private void loadConcepts() {
        try {
            List<InvoiceConcept> concepts = conceptDAO.getAllInvoiceConcepts();
            ObservableList<InvoiceConcept> observableList = FXCollections.observableArrayList(concepts);
            tableviewConcepts.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al cargar conceptos", e.getMessage());
        }
    }

    private void loadConceptsByInvoice(String invoiceNumber) {
        try {
            List<InvoiceConcept> concepts = conceptDAO.searchByInvoiceNumber(invoiceNumber);
            ObservableList<InvoiceConcept> observableList = FXCollections.observableArrayList(concepts);
            tableviewConcepts.setItems(observableList);
        } catch (SQLException e) {
            showError("Error al buscar conceptos", e.getMessage());
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
            String invoiceNumber = comboInvoiceNumber.getValue();
            String description = txtDescription.getText().trim();
            double amount = Double.parseDouble(txtAmount.getText().trim());

            InvoiceConcept concept = new InvoiceConcept(invoiceNumber, description, amount);
            conceptDAO.insertInvoiceConcept(concept);
            showSuccess("Concepto agregado exitosamente");

            if (comboInvoiceNumber.getValue() != null) {
                loadConceptsByInvoice(comboInvoiceNumber.getValue());
            } else {
                loadConcepts();
            }
            clearForm();

        } catch (NumberFormatException e) {
            showError("Error de formato", "El monto debe ser un número válido");
        } catch (SQLException e) {
            showError("Error en la base de datos", e.getMessage());
        }
    }

    private void handleDelete() {
        if (selectedConcept == null) {
            showError("Selección requerida", "Debe seleccionar un concepto de la tabla");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Desea eliminar este concepto?");
        alert.setContentText("Factura: " + selectedConcept.getInvoiceNumber() + "\nConcepto: " + selectedConcept.getDescription());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                conceptDAO.deleteInvoiceConcept(selectedConcept.getInvoiceNumber(), selectedConcept.getDescription());
                showSuccess("Concepto eliminado exitosamente");
                if (comboInvoiceNumber.getValue() != null) {
                    loadConceptsByInvoice(comboInvoiceNumber.getValue());
                } else {
                    loadConcepts();
                }
                clearForm();
            } catch (SQLException e) {
                showError("Error al eliminar", e.getMessage());
            }
        }
    }

    private void handleSearch() {
        String invoiceNumber = comboInvoiceNumber.getValue();
        if (invoiceNumber == null || invoiceNumber.isEmpty()) {
            showError("Selección requerida", "Debe seleccionar una factura");
            return;
        }

        loadConceptsByInvoice(invoiceNumber);
    }

    private void handleClear() {
        clearForm();
        tableviewConcepts.getSelectionModel().clearSelection();
        selectedConcept = null;
    }

    private void selectConcept() {
        selectedConcept = tableviewConcepts.getSelectionModel().getSelectedItem();
        if (selectedConcept != null) {
            comboInvoiceNumber.setValue(selectedConcept.getInvoiceNumber());
            txtDescription.setText(selectedConcept.getDescription());
            txtAmount.setText(String.valueOf(selectedConcept.getAmount()));
        }
    }

    private boolean validateForm() {
        lblError.setText("");

        String invoiceNumber = comboInvoiceNumber.getValue();
        String description = txtDescription.getText().trim();
        String amountStr = txtAmount.getText().trim();

        if (invoiceNumber == null || invoiceNumber.isEmpty()) {
            showError("Campo requerido", "Debe seleccionar una factura");
            return false;
        }

        if (description.isEmpty() || amountStr.isEmpty()) {
            showError("Campos requeridos", "Todos los campos son obligatorios");
            return false;
        }

        if (description.length() > 255) {
            showError("Descripción muy larga", "La descripción no debe exceder 255 caracteres");
            return false;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount < 0) {
                showError("Valor inválido", "El monto no puede ser negativo");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Formato inválido", "El monto debe ser un número válido");
            return false;
        }

        return true;
    }

    private void clearForm() {
        txtDescription.setText("");
        txtAmount.setText("");
        lblError.setText("");
        selectedConcept = null;
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
