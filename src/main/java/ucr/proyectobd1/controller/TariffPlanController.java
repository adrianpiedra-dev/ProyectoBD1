package ucr.proyectobd1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ucr.proyectobd1.data.CommercialCategoryDAO;
import ucr.proyectobd1.data.TarrifPlanData;
import ucr.proyectobd1.model.CommercialCategory;
import ucr.proyectobd1.model.TariffPlan;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TariffPlanController implements Initializable {
    @javafx.fxml.FXML
    private TextField lblCuota;
    @javafx.fxml.FXML
    private Button btnUpdate;
    @javafx.fxml.FXML
    private TableColumn columnCuute;
    @javafx.fxml.FXML
    private TextField lblCost;
    @javafx.fxml.FXML
    private TableColumn <TariffPlan, String> columnCodeCC;
    @javafx.fxml.FXML
    private Button btnClean;
    @javafx.fxml.FXML
    private ImageView imageviewLogo;
    @javafx.fxml.FXML
    private TextField lblName;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private TextField lblMinutes;
    @javafx.fxml.FXML
    private ComboBox <String> comboboxCodeCC;
    @javafx.fxml.FXML
    private TableColumn <TariffPlan, String> columnCode;
    @javafx.fxml.FXML
    private TableColumn <TariffPlan, String> columnDescription;
    @javafx.fxml.FXML
    private Label lblError;
    @javafx.fxml.FXML
    private Button btnBackMenu;
    @javafx.fxml.FXML
    private Button btnSearch;
    @javafx.fxml.FXML
    private TableView <TariffPlan> tableViewPT;
    @javafx.fxml.FXML
    private TableColumn <TariffPlan, String> columnGB;
    @javafx.fxml.FXML
    private TextField lblGigabites;
    @javafx.fxml.FXML
    private TableColumn <TariffPlan, String> columnMS;
    @javafx.fxml.FXML
    private TextField lblDescription;
    @javafx.fxml.FXML
    private TextField lblCode;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private TableColumn <TariffPlan, String> columnCost;
    @javafx.fxml.FXML
    private TextField lblMS;
    @javafx.fxml.FXML
    private TableColumn <TariffPlan, String> columnMinutes;
    @javafx.fxml.FXML
    private TableColumn <TariffPlan, String> columnName;

    TarrifPlanData tariffPlanData = new TarrifPlanData();
    CommercialCategoryDAO commercialCategoryDAO = new CommercialCategoryDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
            imageviewLogo.setImage(logo);

            imageviewLogo.setFitWidth(200);
            imageviewLogo.setPreserveRatio(true);

            List<CommercialCategory> listComercialC = commercialCategoryDAO.getAllCommercialCategories();

            ObservableList<String> observableListCC = FXCollections.observableArrayList();
            for (CommercialCategory cc : listComercialC) {
                observableListCC.add(cc.getCode());
            }

            comboboxCodeCC.setItems(observableListCC);

            columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
            columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            columnCost.setCellValueFactory(new PropertyValueFactory<>("share"));
            columnGB.setCellValueFactory(new PropertyValueFactory<>("gigabytes"));
            columnMinutes.setCellValueFactory(new PropertyValueFactory<>("minutes"));
            columnMS.setCellValueFactory(new PropertyValueFactory<>("ms"));
            columnCost.setCellValueFactory(new PropertyValueFactory<>("costEC"));
            columnCodeCC.setCellValueFactory(new PropertyValueFactory<>("codeCC"));

            operationButtons();

        } catch (SQLException e) {
            showError("ERROR DE BASE DE DATOS", "No se pudieron cargar las categorías comerciales: " + e.getMessage());
        }
    }

    void operationButtons(){
        btnSave.setOnAction(e->{
            create();
        });

        btnDelete.setOnAction(e->{
            deleteTariffPlan();
        });

        btnSearch.setOnAction(e->{
            getTariffPlan();
        });

        btnUpdate.setOnAction(e->{
            updateTariffPlan();
        });

        btnClean.setOnAction(e->{
            lblCode.clear();
            lblName.clear();
            lblDescription.clear();
            lblCuota.clear();
            lblGigabites.clear();
            lblMinutes.clear();
            lblMS.clear();
            lblCost.clear();
            tableViewPT.getItems().clear();
        });

        btnBackMenu.setOnAction(e->{
            openWindow("/ucr/proyectobd1/hello-view.fxml", e);
        });
    }

    void create() {
        if (lblCode.getText().isBlank() || lblName.getText().isBlank() || lblDescription.getText().isBlank()) {
            showError("ERROR", "DEBE LLENAR LOS CAMPOS DE CÓDIGO, NOMBRE Y DESCRIPCIÓN");
            return;
        }

        if (comboboxCodeCC.getValue() == null) {
            showError("ERROR", "DEBE SELECCIONAR UN CÓDIGO DE CC");
            return;
        }

        String code = lblCode.getText();
        String name = lblName.getText();
        String description = lblDescription.getText();
        String codeCC = comboboxCodeCC.getValue();

        float share;
        int gigabytes, minutes, ms;
        float costEC;

        try {
            share = Float.parseFloat(lblCuota.getText());
            gigabytes = Integer.parseInt(lblGigabites.getText());
            minutes = Integer.parseInt(lblMinutes.getText());
            ms = Integer.parseInt(lblMS.getText());
            costEC = Float.parseFloat(lblCost.getText());
        } catch (NumberFormatException e) {
            showError("ERROR DE FORMATO", "Los campos numéricos (Cuota, Gigabytes, Minutos, Mensajes, Costo) deben contener solo números válidos.");
            return;
        }

        if (share <= 0 || gigabytes <= 0 || minutes <= 0 || ms <= 0 || costEC <= 0) {
            showError("ERROR", "Los valores numéricos deben ser mayores a cero.");
            return;
        }

        if (tariffPlanData.existPT(code)) {
            showError("ERROR", "ESE PLAN TARIFARIO YA EXISTE");
            return;
        }

        TariffPlan tariffPlan = new TariffPlan(code, name, description, share, gigabytes, minutes, ms, costEC, codeCC);

        tariffPlanData.insertTarrifPlan(tariffPlan);
        showSuccess("PLAN DE TARIFA CREADO CON ÉXITO");
    }

    void deleteTariffPlan(){
        String code = lblCode.getText();

        if(code.isEmpty() || code.isBlank()){
            showError("ERROR", "DEBE DIGITAR UN CÓDIGO");
            return;
        }

        if(!tariffPlanData.existPT(code)){
            showError("ERROR", "PLAN TARIFARIO NO EXISTENTE");
            return;
        }

        tariffPlanData.deletePT(code);
        showSuccess("PLAN TARIFARIO ELIMINADO CON ÉXITO");
    }

    void getTariffPlan(){
        String code = lblCode.getText();

        if(code.isEmpty() || code.isBlank()){
            showError("ERROR", "DEBE DIGITAR UN CÓDIGO");
            return;
        }

        if(!tariffPlanData.existPT(code)){
            showError("ERROR", "PLAN TARIFARIO NO EXISTENTE");
            return;
        }

        TariffPlan tariffPlan = tariffPlanData.getTariffPlan(code);
        List<TariffPlan> tariffPlanList = new ArrayList<>();
        tariffPlanList.add(tariffPlan);
        ObservableList<TariffPlan> observableList = FXCollections.observableList(tariffPlanList);
        tableViewPT.setItems(observableList);

        lblCode.setText(tariffPlan.getCode());
        lblName.setText(tariffPlan.getName());
        lblDescription.setText(tariffPlan.getDescription());
        comboboxCodeCC.setValue(tariffPlan.getCodeCC());
        lblCuota.setText(String.valueOf(tariffPlan.getShare()));
        lblGigabites.setText(String.valueOf(tariffPlan.getGigabytes()));
        lblMinutes.setText(String.valueOf(tariffPlan.getMinutes()));
        lblMS.setText(String.valueOf(tariffPlan.getMs()));
        lblCost.setText(String.valueOf(tariffPlan.getCostEC()));
    }

    void updateTariffPlan(){
        if (lblCode.getText().isBlank() || lblName.getText().isBlank() || lblDescription.getText().isBlank()) {
            showError("ERROR", "DEBE LLENAR LOS CAMPOS DE CÓDIGO, NOMBRE Y DESCRIPCIÓN");
            return;
        }

        if (comboboxCodeCC.getValue() == null) {
            showError("ERROR", "DEBE SELECCIONAR UN CÓDIGO DE CC");
            return;
        }

        String code = lblCode.getText();
        String name = lblName.getText();
        String description = lblDescription.getText();
        String codeCC = comboboxCodeCC.getValue();

        float share;
        int gigabytes, minutes, ms;
        float costEC;

        try {
            share = Float.parseFloat(lblCuota.getText());
            gigabytes = Integer.parseInt(lblGigabites.getText());
            minutes = Integer.parseInt(lblMinutes.getText());
            ms = Integer.parseInt(lblMS.getText());
            costEC = Float.parseFloat(lblCost.getText());
        } catch (NumberFormatException e) {
            showError("ERROR DE FORMATO", "Los campos numéricos (Cuota, Gigabytes, Minutos, Mensajes, Costo) deben contener solo números válidos.");
            return;
        }

        if (share <= 0 || gigabytes <= 0 || minutes <= 0 || ms <= 0 || costEC <= 0) {
            showError("ERROR", "Los valores numéricos deben ser mayores a cero.");
            return;
        }

        if (!tariffPlanData.existPT(code)) {
            showError("ERROR", "ESE PLAN TARIFARIO NO EXISTE");
            return;
        }

        TariffPlan tariffPlan = new TariffPlan(code, name, description, share, gigabytes, minutes, ms, costEC, codeCC);

        tariffPlanData.update(tariffPlan);

        showSuccess("PLAN TARIFARIO ACTUALIZADO CON ÉXITO");
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


    private void openWindow(String rutaFxml, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            String css = getClass().getResource("/ucr/proyectobd1/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setScene(scene);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar la ventana: " + rutaFxml);
            e.printStackTrace();
        }
    }


}
