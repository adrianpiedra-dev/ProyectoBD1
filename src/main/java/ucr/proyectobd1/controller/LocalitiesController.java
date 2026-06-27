package ucr.proyectobd1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ucr.proyectobd1.data.CantonData;
import ucr.proyectobd1.model.Canton;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LocalitiesController implements Initializable {
    @javafx.fxml.FXML
    private Button btnCleanCanton;
    @javafx.fxml.FXML
    private Label lblErrorCanton;
    @javafx.fxml.FXML
    private TextField txtNameCanton;
    @javafx.fxml.FXML
    private TextField txtIdCanton;
    @javafx.fxml.FXML
    private TextField txtProvince;
    @javafx.fxml.FXML
    private Button btnCreateCanton;

    CantonData cantonData = new CantonData();
    @javafx.fxml.FXML
    private Button btnBackMenu;
    @javafx.fxml.FXML
    private Button btnSearchCanton;
    @javafx.fxml.FXML
    private Label lblErrorSearchCanton;
    @javafx.fxml.FXML
    private Label lblErrorDeleteCanton;
    @javafx.fxml.FXML
    private Button btnCleanDC;
    @javafx.fxml.FXML
    private Button btnDeleteCanton;
    @javafx.fxml.FXML
    private TextField txtCodCantonSearch;
    @javafx.fxml.FXML
    private TextField txtCodeCantonDelete;
    @javafx.fxml.FXML
    private Button btnCleanCantonSearch;
    @javafx.fxml.FXML
    private TextArea txtResultCantonSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        operationsButtons();
    }

    void operationsButtons(){
        btnCreateCanton.setOnAction(e->{
            createCanton();
        });

        btnCleanCanton.setOnAction(e->{
            lblErrorCanton.setText("");
            txtIdCanton.clear();
            txtProvince.clear();
            txtNameCanton.clear();
        });

        btnDeleteCanton.setOnAction(e->{
            deleteCanton();
        });

        btnCleanDC.setOnAction(e->{
            lblErrorDeleteCanton.setText("");
            txtCodeCantonDelete.clear();
        });

        btnSearchCanton.setOnAction(e->{
            searchCanton();
        });

        btnCleanCantonSearch.setOnAction(e->{
            lblErrorSearchCanton.setText("");
            txtResultCantonSearch.setText("");
            txtCodCantonSearch.clear();
        });

        btnBackMenu.setOnAction(e->{
            openWindow("/ucr/proyectobd1/hello-view.fxml", e);
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
    void createCanton(){
        String idCanton = txtIdCanton.getText();
        String nameCanton = txtNameCanton.getText();
        String province = txtProvince.getText();

        if(idCanton.isEmpty() && idCanton.isBlank()){
            lblErrorCanton.setText("ERROR: EL ID DEL CANTÓN NO PUEDE ESTAR VACÍO");
            return;
        } else if(nameCanton.isEmpty() && nameCanton.isBlank()){
            lblErrorCanton.setText("ERROR: EL NOMBRE DEL CANTÓN NO PUEDE ESTAR VACÍO");
            return;
        }else if(province.isEmpty() && province.isBlank()){
            lblErrorCanton.setText("ERROR: LA PROVINCIA NO PUEDE ESTAR VACÍO");
            return;
        }else{

            //TODO REVISA SI ESE CANTÓN YA EXISTE
            Canton canton = new Canton(idCanton, nameCanton, province);

            if(!cantonData.existCanton(canton.getCode())){
                cantonData.insertCanton(canton);
                lblErrorCanton.setText("CANTÓN CREADO CON ÉXITO");
            }else{
                lblErrorCanton.setText("ERROR: CANTON YA EXISTENTE");
            }

        }
    }

    void deleteCanton(){
        String code = txtCodeCantonDelete.getText();

        if(code.isEmpty() || code.isBlank()){
            lblErrorDeleteCanton.setText("ERROR: DEBE INGRESAR EL CÓDIGO DEL CANTÓN");
            return;
        }else if(!cantonData.existCanton(code)){
            lblErrorDeleteCanton.setText("ERROR: NO EXISTE ESE CANTÓN");
        }else{

            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar Eliminación");
            alerta.setHeaderText("¿Está seguro de que desea eliminar este cantón?");
            alerta.setContentText("Esta acción podría eliminar distritos asociados en cascada y no se puede deshacer.");

            Optional<ButtonType> resultado = alerta.showAndWait();


            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                cantonData.deleteCanton(code);
            }

        }
    }

    void searchCanton(){
        String code = txtCodCantonSearch.getText();

        if(code.isEmpty() || code.isBlank()){
            lblErrorSearchCanton.setText("ERROR: DEBE INGRESAR EL CÓDIGO DEL CANTÓN");
            return;
        }else if(!cantonData.existCanton(code)){
            lblErrorSearchCanton.setText("ERROR: NO EXISTE ESE CANTÓN");
        }else{

        }
    }



}
