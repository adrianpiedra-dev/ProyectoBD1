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
import ucr.proyectobd1.data.QueriesDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class QueriesController {

    @FXML
    private ImageView imageviewLogo;

    @FXML
    private Button btnBackMenu;

    @FXML
    private ComboBox<String> comboQueries;

    @FXML
    private Button btnExecute;

    @FXML
    private TableView<Map<String, Object>> tableviewResults;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblError;

    private QueriesDAO queriesDAO;

    private static final String QUERY_1 = "Total facturado por cliente (SUM)";
    private static final String QUERY_2 = "Cliente y Teléfono (UPPER y CONCAT)";
    private static final String QUERY_3 = "Auditoría de Creación";

    @FXML
    public void initialize() {
        queriesDAO = new QueriesDAO();

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);
        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        ObservableList<String> queries = FXCollections.observableArrayList(QUERY_1, QUERY_2, QUERY_3);
        comboQueries.setItems(queries);
        comboQueries.getSelectionModel().selectFirst();

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        btnBackMenu.setOnAction(e -> openWindow("/ucr/proyectobd1/hello-view.fxml", e));
        btnExecute.setOnAction(e -> handleExecuteQuery());
    }

    private void handleExecuteQuery() {
        String selectedQuery = comboQueries.getValue();
        if (selectedQuery == null) {
            showError("Selección requerida", "Seleccione una consulta");
            return;
        }

        try {
            List<Map<String, Object>> results = null;
            String title = "";

            switch (selectedQuery) {
                case QUERY_1:
                    results = queriesDAO.getInvoiceAmountByCustomer();
                    title = "Total facturado por cada cliente";
                    break;
                case QUERY_2:
                    results = queriesDAO.getCustomerFullNameWithPhone();
                    title = "Clientes con teléfono en mayúsculas";
                    break;
                case QUERY_3:
                    results = queriesDAO.getAuditInfo();
                    title = "Información de Auditoría";
                    break;
            }

            lblTitle.setText(title);
            displayResults(results);

        } catch (SQLException e) {
            showError("Error en la consulta", e.getMessage());
        }
    }

    private void displayResults(List<Map<String, Object>> results) {
        tableviewResults.getColumns().clear();

        if (results.isEmpty()) {
            showError("Sin resultados", "La consulta no retornó datos");
            return;
        }

        Map<String, Object> firstRow = results.get(0);
        int columnIndex = 0;

        for (String columnName : firstRow.keySet()) {
            TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
            final int colIdx = columnIndex;

            column.setCellValueFactory(cellData -> {
                Object value = cellData.getValue().get(columnName);
                return new javafx.beans.property.SimpleObjectProperty<>(value);
            });

            column.setPrefWidth(150.0);
            tableviewResults.getColumns().add(column);
            columnIndex++;
        }

        ObservableList<Map<String, Object>> observableResults = FXCollections.observableArrayList(results);
        tableviewResults.setItems(observableResults);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        lblError.setText(title + ": " + message);
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
