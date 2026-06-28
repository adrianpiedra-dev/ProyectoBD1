package ucr.proyectobd1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button btnOpenLocalities;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnCommercialCategory;
    @FXML
    private Button btnService;
    @FXML
    private Button btnPackage;
    @FXML
    private Button btnCustomerPhone;
    @FXML
    private Button btnInvoiceConcept;
    @FXML
    private Button btnPoints;
    @FXML
    private Button btnQueries;
    @FXML
    private ImageView imageviewLogo;
    @FXML
    private Button btnTarrifPlan;
    @FXML
    private Button btnBill;
    @FXML
    private Button btnPromotion;
    @FXML
    private Button btnConsumption;
    @FXML
    private Button btnVoiceCall;
    @FXML
    private Button btnLineService;
    @FXML
    private Button btnLinePackage;
    @FXML
    private Button btnIncompatibilities;
    @FXML
    private Button btnHistoricalTP;
    @FXML
    private Button btnMobileLine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        operationButtons();
    }

    void operationButtons() {

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);

        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        btnOpenLocalities.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/localities.fxml", actionEvent);
        });

        btnCustomers.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/customers.fxml", actionEvent);
        });

        btnCommercialCategory.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/commercial-category.fxml", actionEvent);
        });

        btnService.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/service.fxml", actionEvent);
        });

        btnPackage.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/package.fxml", actionEvent);
        });

        btnCustomerPhone.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/customer-phone.fxml", actionEvent);
        });

        btnInvoiceConcept.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/invoice-concept.fxml", actionEvent);
        });

        btnPoints.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/points.fxml", actionEvent);
        });

        btnQueries.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/queries.fxml", actionEvent);
        });

        btnTarrifPlan.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/tariff-plan.fxml", actionEvent);
        });

        btnBill.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/bill.fxml", actionEvent);
        });

        btnPromotion.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/promotion.fxml", actionEvent);
        });

        btnConsumption.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/consumption.fxml", actionEvent);
        });

        btnVoiceCall.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/voice-call.fxml", actionEvent);
        });

        btnLineService.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/line-service.fxml", actionEvent);
        });

        btnLinePackage.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/line-additional-package.fxml", actionEvent);
        });

        btnIncompatibilities.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/incompatibilities-promotions.fxml", actionEvent);
        });

        btnHistoricalTP.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/historical-tp.fxml", actionEvent);
        });

        btnMobileLine.setOnAction(actionEvent -> {
            openWindow("/ucr/proyectobd1/linea-movil.fxml", actionEvent);
        });
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