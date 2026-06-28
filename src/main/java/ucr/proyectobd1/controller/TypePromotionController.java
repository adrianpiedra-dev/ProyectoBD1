package ucr.proyectobd1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ucr.proyectobd1.data.PromotionTypeData;
import ucr.proyectobd1.data.TarrifPlanData;
import ucr.proyectobd1.model.PromotionType;
import ucr.proyectobd1.model.TariffPlan;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TypePromotionController implements Initializable {
    @javafx.fxml.FXML
    private Button btnClean;
    @javafx.fxml.FXML
    private ImageView imageviewLogo;
    @javafx.fxml.FXML
    private TextField lblPercentage;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private Button btnSearch;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Label lblError;
    @javafx.fxml.FXML
    private TextField lblCode;
    @javafx.fxml.FXML
    private ComboBox <String> comboboxIncompatibilities;
    @javafx.fxml.FXML
    private Button btnBackMenu;

    PromotionTypeData promotionTypeData = new PromotionTypeData();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);

        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);
    }

    void operationsButtons(){
        btnBackMenu.setOnAction(e->{
            openWindow("/ucr/proyectobd1/hello-view.fxml", e);
        });
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

    void create(){
        String code = lblCode.getText();
        float percentage = Float.parseFloat(lblPercentage.getText());

        if(code.isBlank() || code.isEmpty()){
            showError("ERROR", "DEBE DIGITAR UN CÓDIGO");
            return;
        }

        if(percentage <= 0){
            showError("ERROR", "DEBE DIGITAR UN PORCENTAJE VÁLIDO");
            return;
        }
        if(promotionTypeData.exist(code)){
            showError("ERROR", "YA EXISTE ESE TIPO DE PROMOCIÓN");
        }

        PromotionType promotionType = new PromotionType(code, percentage);
        promotionTypeData.insert(promotionType);
        showSuccess("TIPO DE PROMOCIÓN CREADO CON ÉXITO");
    }

    void delete(){
        String code = lblCode.getText();
        if(code.isBlank() || code.isEmpty()){
            showError("ERROR", "DEBE DIGITAR UN CÓDIGO");
            return;
        }
        if(!promotionTypeData.exist(code)){
            showError("ERROR", "NO EXISTE ESE TIPO DE PROMOCIÓN");
        }

        promotionTypeData.delete(code);
        showSuccess("TIPO ELIMINADO CON ÉXITO");
    }

    void search(){
        String code = lblCode.getText();
        if(code.isBlank() || code.isEmpty()){
            showError("ERROR", "DEBE DIGITAR UN CÓDIGO");
            return;
        }
        if(!promotionTypeData.exist(code)){
            showError("ERROR", "NO EXISTE ESE TIPO DE PROMOCIÓN");
        }

        PromotionType promotionType = promotionTypeData.getPromotionType(code);
    }

    void addIncompatibility(){
        String type1 = lblCode.getText();
        String type2 = comboboxIncompatibilities.getValue();

        if(type1.isEmpty() || type1.isBlank()){
            showError("TITULO", "DEBE AGREGAR EL CÓDIGO DEL TIPO");
            return;
        }

        if(type2 == null){
            showError("TITULO", "DEBE AGREGAR EL CÓDIGO DEL TIPO");
            return;
        }

        promotionTypeData.addIncompatibility(type1, type2);
        showSuccess("INCOMPATIBILIDAD AÑADIDA CON ÉXITO");
    }


}
