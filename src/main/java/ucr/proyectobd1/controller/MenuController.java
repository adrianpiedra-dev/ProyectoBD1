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
    private ImageView imageviewLogo;

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