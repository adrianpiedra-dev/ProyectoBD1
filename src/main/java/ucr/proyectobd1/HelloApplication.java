package ucr.proyectobd1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Sistema de Gestión de CRUDs");
        stage.setScene(scene);
        stage.show();
    }
}
