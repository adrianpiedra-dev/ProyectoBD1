module ucr.proyectobd1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    // Lo vas a ocupar para conectar la Base de Datos

    opens ucr.proyectobd1.model to javafx.base, javafx.fxml;
    // Permite que JavaFX lea los controladores para las interfaces FXML
    opens ucr.proyectobd1.controller to javafx.fxml;
    exports ucr.proyectobd1.controller;

    // Esto probablemente ya lo tienes para tu clase principal
    opens ucr.proyectobd1 to javafx.fxml;
    exports ucr.proyectobd1;
}
