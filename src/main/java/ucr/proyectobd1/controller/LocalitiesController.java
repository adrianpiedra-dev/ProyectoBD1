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
import ucr.proyectobd1.data.CantonData;
import ucr.proyectobd1.data.DistrictData;
import ucr.proyectobd1.model.Canton;
import ucr.proyectobd1.model.District;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private Button btnCreateCanton;

    CantonData cantonData = new CantonData();
    @javafx.fxml.FXML
    private Button btnBackMenu;
    @javafx.fxml.FXML
    private Label lblErrorSearchCanton;
    @javafx.fxml.FXML
    private Label lblErrorDeleteCanton;
    @javafx.fxml.FXML
    private Button btnCleanDC;
    @javafx.fxml.FXML
    private Button btnDeleteCanton;
    @javafx.fxml.FXML
    private TextField txtCodeCantonDelete;
    @javafx.fxml.FXML
    private Button btnCleanCantonSearch;
    @javafx.fxml.FXML
    private Button btnCreateDistrict;
    @javafx.fxml.FXML
    private TableColumn <District, String> columnNameDistricts;
    @javafx.fxml.FXML
    private Button btnCleanSearchDistrict;
    @javafx.fxml.FXML
    private TextField txtCodeCantonSearchDistrict;
    @javafx.fxml.FXML
    private TextField txtNameDistrictCreate;
    @javafx.fxml.FXML
    private Button btnCleanDistrictC;
    @javafx.fxml.FXML
    private TableColumn <Canton, String> columnNameCantons;
    @javafx.fxml.FXML
    private TableColumn <Canton, String> columnIdCantons;
    @javafx.fxml.FXML
    private TableColumn <Canton, String> columnProvinceCantons;
    @javafx.fxml.FXML
    private TableView<Canton>tableViewCantons;
    @javafx.fxml.FXML
    private TableColumn <District, String> columnCodeDistrict;
    @javafx.fxml.FXML
    private TextField txtIdDistrictCreate;
    @javafx.fxml.FXML
    private Button btnDeleteDistrict;
    @javafx.fxml.FXML
    private TableColumn <District, String> columnCodeCantonDistrict;
    @javafx.fxml.FXML
    private ComboBox <String> comboboxCantonsCD;
    @javafx.fxml.FXML
    private Button btnCleanDD;
    @javafx.fxml.FXML
    private TableView <District> tableViewDistricts;
    @javafx.fxml.FXML
    private Button btnSearchCantons;
    @javafx.fxml.FXML
    private TextField txtCodeDistrictDelete;
    @javafx.fxml.FXML
    private Button btnSearchDistrict;
    @javafx.fxml.FXML
    private ComboBox<String> comboboxProvincesCC;
    @javafx.fxml.FXML
    private ComboBox<String> comboboxProvincesVC;

    DistrictData districtData = new DistrictData();
    @javafx.fxml.FXML
    private Label lblErrorCreateDistrict;
    @javafx.fxml.FXML
    private ComboBox <String> comboboxProvincesCD;
    @javafx.fxml.FXML
    private Label lblErrorDeleteDistrict;
    @javafx.fxml.FXML
    private Label lblErrorSearchDistrict;
    @javafx.fxml.FXML
    private ImageView imageviewLogo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);

        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        ObservableList<String> provinces = FXCollections.observableArrayList("San Jose", "Cartago", "Heredia", "Alajuela", "Puntarenas", "Guanacaste", "Limon");
        comboboxProvincesCC.setItems(provinces);
        comboboxProvincesVC.setItems(provinces);
        comboboxProvincesCD.setItems(provinces);

        comboboxProvincesCD.getSelectionModel().selectedItemProperty().addListener((observable, valorAnterior, province) -> {
            if (province != null) {
                addCantonsBoxCD(province);
            } else {
                comboboxCantonsCD.getItems().clear();
            }
        });

        columnIdCantons.setCellValueFactory(new PropertyValueFactory<>("code"));
        columnNameCantons.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProvinceCantons.setCellValueFactory(new PropertyValueFactory<>("province"));


        columnCodeDistrict.setCellValueFactory(new PropertyValueFactory<>("cod"));
        columnNameDistricts.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCodeCantonDistrict.setCellValueFactory(new PropertyValueFactory<>("codCanton"));

        operationsButtons();
    }

    void operationsButtons(){
        btnCreateCanton.setOnAction(e->{
            createCanton();
        });

        btnCleanCanton.setOnAction(e->{
            lblErrorCanton.setText("");
            txtIdCanton.clear();
            txtNameCanton.clear();
        });

        btnDeleteCanton.setOnAction(e->{
            deleteCanton();
        });

        btnCleanDC.setOnAction(e->{
            lblErrorDeleteCanton.setText("");
            txtCodeCantonDelete.clear();
        });

        btnCleanCantonSearch.setOnAction(e->{
            lblErrorSearchCanton.setText("");
        });

        btnSearchCantons.setOnAction(e->{
            searchCantons();
        });

        btnCleanCantonSearch.setOnAction(e->{
            tableViewCantons.getItems().clear();
        });

        btnCreateDistrict.setOnAction(e->{
            createDistrict();
        });

        btnCleanDistrictC.setOnAction(e->{
            txtNameDistrictCreate.clear();
            txtIdDistrictCreate.clear();
            lblErrorCreateDistrict.setText("");
            comboboxCantonsCD.getItems().clear();
        });

        btnDeleteDistrict.setOnAction(e->{
            deleteDistrict();
        });

        btnCleanDD.setOnAction(e->{
            txtCodeDistrictDelete.clear();
        });

        btnSearchDistrict.setOnAction(e->{
            searchDistrict();
        });

        btnCleanSearchDistrict.setOnAction(e->{
            txtCodeCantonSearchDistrict.clear();
            tableViewDistricts.getItems().clear();
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
        String province = comboboxProvincesCC.getValue().toLowerCase();

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

    void searchCantons(){
        String province = comboboxProvincesVC.getValue().toLowerCase();
        List<Canton> cantons = cantonData.searchCanton(province);
        ObservableList<Canton> listObservable = FXCollections.observableArrayList(cantons);
        tableViewCantons.setItems(listObservable);
    }

    void addCantonsBoxCD(String province){

        String provinceFormated = province.toLowerCase();

        List<Canton> cantonsList = cantonData.searchCanton(provinceFormated);
        List<String> cantonsCode = new ArrayList<>();

        for(int i=0; i < cantonsList.size(); i++){
            cantonsCode.add(cantonsList.get(i).getCode());
        }

        ObservableList<String> observableList = FXCollections.observableList(cantonsCode);

        comboboxCantonsCD.setItems(observableList);
    }
    void createDistrict(){
        String code = txtIdDistrictCreate.getText();
        String name = txtNameDistrictCreate.getText();
        String canton = comboboxCantonsCD.getValue();

        if(code.isEmpty() || code.isBlank()){
            lblErrorCreateDistrict.setText("ERROR:DEBE DIGITAR EL CÓDIGO DEL DISTRITO");
            return;
        } else if (name.isBlank() || name.isEmpty()) {
            lblErrorCreateDistrict.setText("ERROR:DEBE DIGITAR EL NOMBRE DEL DISTRITO");
            return;
        } else if (districtData.existDistrict(code)) {
            lblErrorCreateDistrict.setText("ERROR: YA EXISTE UN DISTRITO CON ESE NOMBRE");
            return;
        }else{
            District district = new District(code, name, canton);
            districtData.insertDistrict(district);
            lblErrorCreateDistrict.setText("DISTRITO CREADO CON ÉXITO");
        }
    }

    void deleteDistrict(){
        String code = txtCodeDistrictDelete.getText();

        if(code.isEmpty() || code.isBlank()){
            lblErrorDeleteDistrict.setText("ERROR: DEBE INGRESAR EL CÓDIGO DEL DISTRITO");
            return;
        }else if(!districtData.existDistrict(code)){
            lblErrorDeleteDistrict.setText("ERROR: NO EXISTE ESE DISTRITO");
        }else{

            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar Eliminación");
            alerta.setHeaderText("¿Está seguro de que desea eliminar este distrito?");
            alerta.setContentText("Esta acción podría eliminar registros asociados en cascada y no se puede deshacer.");

            Optional<ButtonType> resultado = alerta.showAndWait();


            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                districtData.deleteDistrict(code);
            }

        }
    }

    void searchDistrict(){
        String canton = txtCodeCantonSearchDistrict.getText();

        if(canton.isEmpty() || canton.isBlank()){
            lblErrorSearchDistrict.setText("ERROR: ESE CANTÓN NO EXISTE");
            return;
        }else{
            List<District> districts = districtData.getDistricts(canton);
            ObservableList<District> observableList = FXCollections.observableList(districts);
            tableViewDistricts.setItems(observableList);
        }
    }



}
