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
import ucr.proyectobd1.data.CantonData;
import ucr.proyectobd1.data.CustomerData;
import ucr.proyectobd1.data.DistrictData;
import ucr.proyectobd1.model.Canton;
import ucr.proyectobd1.model.Customer;
import ucr.proyectobd1.model.CustomerCompl;
import ucr.proyectobd1.model.District;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomersController {

    @FXML
    private Button btnBackMenu;
    @FXML
    private Button btnDeleteEmail;
    @FXML
    private TextField lblCodeDeleteCustomer;
    @FXML
    private ComboBox <String> comboBoxCantonUpdate;
    @FXML
    private ComboBox <String> comboboxProvinceUpdate;
    @FXML
    private Button btnAddEmail;
    @FXML
    private ComboBox <String> comboboxTypeCustomerCreate;
    @FXML
    private TextField lblLastname1CustomerCreate;
    @FXML
    private TableColumn <CustomerCompl, String> columnDistricViewCustomer;
    @FXML
    private Button btnViewCustomer;
    @FXML
    private TextField lblLastname2CreateCustomer;
    @FXML
    private TextField txtOldEmail;
    @FXML
    private Button btnCleanCustomerCreate;
    @FXML
    private TableColumn <CustomerCompl, String> columnLastname2Questions;
    @FXML
    private Button btnFilterDT;
    @FXML
    private Button btnCleanDeleteCustomer;
    @FXML
    private TextField txtIdViewCustomer;
    @FXML
    private TextField txtIdEmail;
    @FXML
    private ComboBox <String> comboboxCantonCreateCustomer;
    @FXML
    private TableColumn <CustomerCompl, String> columnProvinceViewCustomer;
    @FXML
    private TextField txtIdCustomerUpdate;
    @FXML
    private TextField txtLastName1Update;
    @FXML
    private TableColumn <CustomerCompl, String> columnAddressQuestions;
    @FXML
    private TableColumn <CustomerCompl, String> columnDistrictQuestions;
    @FXML
    private TableColumn <CustomerCompl, String> columnTypeViewCustomer;
    @FXML
    private ComboBox <String> comboboxProvinceCustomerCreate;
    @FXML
    private TableColumn <CustomerCompl, String> columnIdQuestions;
    @FXML
    private Label lblErrorCreateCustomer;
    @FXML
    private TableView <CustomerCompl> tableviewQuestions;
    @FXML
    private TableColumn <CustomerCompl, String> columnLastname1Questions;
    @FXML
    private Button btnUpdateEmail;
    @FXML
    private TableColumn <CustomerCompl, String> columnNameQuestions;
    @FXML
    private ComboBox <String> comboBoxProvinceQuestions;
    @FXML
    private ComboBox <String> comboboxTypeQuestions;
    @FXML
    private ComboBox <String> comboboxDistrictQuestions;
    @FXML
    private Button btnCreateCustomer;
    @FXML
    private ComboBox <String> comboboxDistrictCustomerCreate;
    @FXML
    private TableColumn <CustomerCompl, String> columnEmailViewCustomer;
    @FXML
    private Button btnFilterDistrict;
    @FXML
    private TableColumn <CustomerCompl, String> columnCantonQuestions;
    @FXML
    private TableColumn <CustomerCompl, String> columnProvinceQuestions;
    @FXML
    private Label lblErrorUpdate;
    @FXML
    private Button btnDeleteCustomer;
    @FXML
    private TableColumn <CustomerCompl, String> columnCantonViewCustomer;
    @FXML
    private TextField lblLastname2Update;
    @FXML
    private TextField txtIdCustomerCreate;
    @FXML
    private ComboBox <String> comboboxDistrictUpdate;
    @FXML
    private TableColumn <CustomerCompl, String> columnIdViewCustomer;
    @FXML
    private Button btnFilterType;
    @FXML
    private TextField lblNameCustomerCreate;
    @FXML
    private Label lblErrorEmail;
    @FXML
    private ComboBox <String> comboboxTypeUpdate;
    @FXML
    private TextField txtNameUpdate;
    @FXML
    private TableColumn <CustomerCompl, String> columnAddressViewCustomer;
    @FXML
    private Button btnCleanViewCustomer;
    @FXML
    private Button btnUpdateCustomer;
    @FXML
    private TableColumn <CustomerCompl, String> columnEmailQuestions;
    @FXML
    private Button btnCleanUpdateCustomer;
    @FXML
    private Label lblErrorViewCustomer;
    @FXML
    private TableView <CustomerCompl> tableviewViewCustomer;
    @FXML
    private TextArea textareaAddressCustomerCreate;
    @FXML
    private TextArea textareaAddressUpdate;
    @FXML
    private TableColumn <CustomerCompl, String> columnNameViewCustomer;
    @FXML
    private TextField txtNewEmail;
    @FXML
    private ComboBox <String> comboboxCantonQuestions;
    @FXML
    private Label lblErrorDeleteCustomer;

    CustomerData customerData = new CustomerData();
    CantonData cantonData = new CantonData();
    DistrictData districtData = new DistrictData();

    @FXML
    private TextField txtEmailCreateCustomer;
    @FXML
    private Button btnSearchCustomerUpdate;
    @FXML
    private TableColumn <CustomerCompl, String> columnLastname2View;
    @FXML
    private TableColumn <CustomerCompl, String> columnLastname1View;
    @FXML
    private Button btnCleanEmail;
    @FXML
    private Label lblErrorQuestions;
    @FXML
    private TableColumn <CustomerCompl, String> columnTypeQuestions;
    @FXML
    private Button btnCleanQuestions;
    @FXML
    private ImageView imageviewLogo;

    @FXML
    public void initialize() {

        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);

        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);

        ObservableList<String> provinces = FXCollections.observableArrayList("San Jose", "Cartago", "Heredia", "Alajuela", "Puntarenas", "Guanacaste", "Limon");
        comboboxProvinceCustomerCreate.setItems(provinces);
        comboboxProvinceCustomerCreate.getSelectionModel().selectedItemProperty().addListener((observable, valorAnterior, province) -> {
            if (province != null) {
                addCantonsBoxCreateCustomer(province);
            } else {
                comboboxCantonCreateCustomer.getItems().clear();
            }
        });

        comboboxCantonCreateCustomer.getSelectionModel().selectedItemProperty().addListener((observable, valorAnterior, canton) -> {
            if (canton != null) {
                addDistrictBoxCreateCustomer(canton);
            } else {
                comboboxDistrictCustomerCreate.getItems().clear();
            }
        });

        ObservableList<String> typesCustomer = FXCollections.observableArrayList("ORO", "BRONCE", "PLATA", "PLATINO");
        comboboxTypeCustomerCreate.setItems(typesCustomer);

        comboboxTypeUpdate.setItems(typesCustomer);


        comboboxProvinceUpdate.setItems(provinces);

        comboboxProvinceUpdate.getSelectionModel().selectedItemProperty().addListener((observable, valorAnterior, province) -> {
            if (province != null) {
                addCantonsBoxUpdate(province);
            } else {
                comboBoxCantonUpdate.getItems().clear();
            }
        });

        comboBoxCantonUpdate.getSelectionModel().selectedItemProperty().addListener((observable, valorAnterior, canton) -> {
            if (canton != null) {
                addDistrictBoxUpdate(canton);
            } else {
                comboboxDistrictUpdate.getItems().clear();
            }
        });


        columnIdViewCustomer.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNameViewCustomer.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastname1View.setCellValueFactory(new PropertyValueFactory<>("lastName1"));
        columnLastname2View.setCellValueFactory(new PropertyValueFactory<>("lastName2"));
        columnAddressViewCustomer.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnDistricViewCustomer.setCellValueFactory(new PropertyValueFactory<>("district"));
        columnProvinceViewCustomer.setCellValueFactory(new PropertyValueFactory<>("province"));
        columnCantonViewCustomer.setCellValueFactory(new PropertyValueFactory<>("canton"));
        columnEmailViewCustomer.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnTypeViewCustomer.setCellValueFactory(new PropertyValueFactory<>("type"));

        comboBoxProvinceQuestions.setItems(provinces);


        comboBoxProvinceQuestions.getSelectionModel().selectedItemProperty().addListener((observable, valorAnterior, province) -> {
            if (province != null) {
                addCantonsBoxQuestion((String) province);
            } else {
                comboboxCantonQuestions.getItems().clear();
            }
        });

        comboboxCantonQuestions.getSelectionModel().selectedItemProperty().addListener((observable, valorAnterior, canton) -> {
            if (canton != null) {
                addDistrictBoxQuestion((String) canton);
            } else {
                comboboxDistrictQuestions.getItems().clear();
            }
        });

        comboboxTypeQuestions.setItems(typesCustomer);

        columnIdQuestions.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNameQuestions.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastname1Questions.setCellValueFactory(new PropertyValueFactory<>("lastName1"));
        columnLastname2Questions.setCellValueFactory(new PropertyValueFactory<>("lastName2"));
        columnAddressQuestions.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnDistrictQuestions.setCellValueFactory(new PropertyValueFactory<>("district"));
        columnProvinceQuestions.setCellValueFactory(new PropertyValueFactory<>("province"));
        columnCantonQuestions.setCellValueFactory(new PropertyValueFactory<>("canton"));
        columnEmailQuestions.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnTypeQuestions.setCellValueFactory(new PropertyValueFactory<>("type"));

        operationsButtons();

        if (btnBackMenu != null) {
            btnBackMenu.setOnAction(e -> {
                openWindow("/ucr/proyectobd1/hello-view.fxml", e);
            });
        }
    }

    private void addDistrictBoxQuestion(String canton) {
        String cantonFormated = canton.toLowerCase();

        List<District> districtList = districtData.getDistricts(cantonFormated);
        List<String> districtsCode = new ArrayList<>();

        for(int i=0; i < districtList.size(); i++){
            districtsCode.add(districtList.get(i).getCod());
        }

        ObservableList<String> observableList = FXCollections.observableList(districtsCode);

        comboboxDistrictQuestions.setItems(observableList);
    }

    private void addCantonsBoxQuestion(String province) {
        String provinceFormated = province.toLowerCase();

        List<Canton> cantonsList = cantonData.searchCanton(provinceFormated);
        List<String> cantonsCode = new ArrayList<>();

        for(int i=0; i < cantonsList.size(); i++){
            cantonsCode.add(cantonsList.get(i).getCode());
        }

        ObservableList<String> observableList = FXCollections.observableList(cantonsCode);

        comboboxCantonQuestions.setItems(observableList);
    }

    private void addDistrictBoxUpdate(String canton) {
        String cantonFormated = canton.toLowerCase();

        List<District> districtList = districtData.getDistricts(cantonFormated);
        List<String> districtsCode = new ArrayList<>();

        for(int i=0; i < districtList.size(); i++){
            districtsCode.add(districtList.get(i).getCod());
        }

        ObservableList<String> observableList = FXCollections.observableList(districtsCode);

        comboboxDistrictUpdate.setItems(observableList);
    }

    private void addCantonsBoxUpdate(String province) {
        String provinceFormated = province.toLowerCase();

        List<Canton> cantonsList = cantonData.searchCanton(provinceFormated);
        List<String> cantonsCode = new ArrayList<>();

        for(int i=0; i < cantonsList.size(); i++){
            cantonsCode.add(cantonsList.get(i).getCode());
        }

        ObservableList<String> observableList = FXCollections.observableList(cantonsCode);

        comboBoxCantonUpdate.setItems(observableList);
    }

    private void addDistrictBoxCreateCustomer(String canton) {
        String cantonFormated = canton.toLowerCase();

        List<District> districtList = districtData.getDistricts(cantonFormated);
        List<String> districtsCode = new ArrayList<>();

        for(int i=0; i < districtList.size(); i++){
            districtsCode.add(districtList.get(i).getCod());
        }

        ObservableList<String> observableList = FXCollections.observableList(districtsCode);

        comboboxDistrictCustomerCreate.setItems(observableList);
    }

    private void addCantonsBoxCreateCustomer(String province) {

        String provinceFormated = province.toLowerCase();

        List<Canton> cantonsList = cantonData.searchCanton(provinceFormated);
        List<String> cantonsCode = new ArrayList<>();

        for(int i=0; i < cantonsList.size(); i++){
            cantonsCode.add(cantonsList.get(i).getCode());
        }

        ObservableList<String> observableList = FXCollections.observableList(cantonsCode);

        comboboxCantonCreateCustomer.setItems(observableList);
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

    void operationsButtons(){
        btnCreateCustomer.setOnAction(e->{
            createCustomer();
        });

        btnCleanCustomerCreate.setOnAction(e->{
            txtIdCustomerCreate.clear();
            lblNameCustomerCreate.clear();
            lblLastname1CustomerCreate.clear();
            lblLastname2CreateCustomer.clear();
            textareaAddressCustomerCreate.clear();
            comboboxCantonCreateCustomer.getItems().clear();
            comboboxDistrictCustomerCreate.getItems().clear();
            comboboxTypeCustomerCreate.getItems().clear();
            txtEmailCreateCustomer.clear();
            lblErrorCreateCustomer.setText("");

        });

        btnDeleteCustomer.setOnAction(e->{
            deleteCustomer();
        });

        btnCleanDeleteCustomer.setOnAction(e->{
            lblCodeDeleteCustomer.clear();
            lblErrorDeleteCustomer.setText("");
        });

        btnSearchCustomerUpdate.setOnAction(e->{
            updateCustomer();
        });

        btnCleanUpdateCustomer.setOnAction(e->{
            txtNameUpdate.clear();
            textareaAddressUpdate.clear();
            lblErrorUpdate.setText("");
            txtIdCustomerUpdate.clear();
            lblLastname2Update.clear();
            txtLastName1Update.clear();
        });

        btnViewCustomer.setOnAction(e->{
            viewCustomer();
        });

        btnCleanViewCustomer.setOnAction(e->{
            txtIdViewCustomer.clear();
            tableviewViewCustomer.getItems().clear();
            lblErrorViewCustomer.setText("");
        });

        btnAddEmail.setOnAction(e->{
            addEmail();
        });

        btnDeleteEmail.setOnAction(e->{
            deleteEmail();
        });

        btnUpdateEmail.setOnAction(e->{
            updateEmail();
        });

        btnCleanEmail.setOnAction(e->{
            txtOldEmail.clear();
            lblErrorEmail.setText("");
            txtIdEmail.clear();
            txtNewEmail.clear();
        });

        btnFilterDistrict.setOnAction(e->{
            filterForDistrict();
        });

        btnFilterType.setOnAction(e->{
            filterForType();
        });

        btnFilterDT.setOnAction(e->{
            filterForTypeAndDistrict();
        });

        btnCleanQuestions.setOnAction(e->{
            tableviewQuestions.getItems().clear();
        });
    }

    void createCustomer(){
        String id = txtIdCustomerCreate.getText().toLowerCase();
        String name = lblNameCustomerCreate.getText().toLowerCase();
        String lastName1 = lblLastname1CustomerCreate.getText().toLowerCase();
        String lastName2 = lblLastname2CreateCustomer.getText().toLowerCase();
        String address = textareaAddressCustomerCreate.getText().toLowerCase();
        String district = comboboxDistrictCustomerCreate.getValue();
        String type = comboboxTypeCustomerCreate.getValue();
        String email = txtEmailCreateCustomer.getText().toLowerCase();

        if(id.isBlank() || id.isEmpty()){
            lblErrorCreateCustomer.setText("ERROR: EL ID NO PUEDE ESTAR VACÍO");
            return;
        } else if (name.isEmpty() || name.isBlank()) {
            lblErrorCreateCustomer.setText("ERROR: EL NOMBRE NO PUEDE ESTAR VACÍO");
            return;
        } else if (lastName1.isBlank() || lastName1.isEmpty() || lastName2.isEmpty() || lastName2.isBlank()) {
            lblErrorCreateCustomer.setText("ERROR: NINGÚN APELLIDO PUEDE ESTAR VACÍO");
            return;
        } else if (district.isEmpty() || district.isBlank()) {
            lblErrorCreateCustomer.setText("ERROR: DEBE SELECCIONAR UN DISTRITO");
            return;
        } else if (type.isBlank() || type.isEmpty()) {
            lblErrorCreateCustomer.setText("ERROR: DEBE SELECCIONAR UN TIPO DE CLIENTE");
            return;
        } else if (email.isEmpty() || email.isBlank()) {
            lblErrorCreateCustomer.setText("ERROR: DEBE DIGITAR UN CORREO");
            return;

        } else if (customerData.existCustomer(id)) {
            lblErrorCreateCustomer.setText("ERROR: YA ESTE ESE CLIENTE");
            return;
        }else{
            Customer customer = new Customer(id, name, lastName1, lastName2, address, district, email, type);
            customerData.insertCustomer(customer);
            lblErrorCreateCustomer.setText("CLIENTE CREADO CON ÉXITO");
            return;
        }

    }

    void deleteCustomer(){
        String id = lblCodeDeleteCustomer.getText();

        if(id.isBlank() || id.isEmpty()){
            lblErrorDeleteCustomer.setText("ERROR: DEBE DIGITAR UN ID");
            return;
        } else if (!customerData.existCustomer(id)) {
            lblErrorDeleteCustomer.setText("ERROR: NO EXISTE ESE CLIENTE");
            return;
        }else{
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar Eliminación");
            alerta.setHeaderText("¿Está seguro de que desea eliminar este cliente?");
            alerta.setContentText("Esta acción podría eliminar registros asociados en cascada y no se puede deshacer.");

            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                customerData.deleteCustomer(id);
                lblErrorDeleteCustomer.setText("CLIENTE ELIMINADO CON ÉXITO");
                return;
            }
        }
    }

    void updateCustomer(){
        String id = txtIdCustomerUpdate.getText();

        if(id.isBlank() || id.isEmpty()){
            lblErrorUpdate.setText("ERROR: DEBE DIGITAR UN ID");
            return;
        } else if (!customerData.existCustomer(id)) {
            lblErrorUpdate.setText("ERROR: NO EXISTE ESE CLIENTE");
        } else {
            String informationCustomer = customerData.searchCustomer(id);

            String [] parts = informationCustomer.split("&");

            txtNameUpdate.setText(parts[1]);
            txtLastName1Update.setText(parts[2]);
            lblLastname2Update.setText(parts[3]);
            textareaAddressUpdate.setText(parts[4]);

            comboboxProvinceUpdate.setValue(parts[5]);
            comboBoxCantonUpdate.setValue(parts[6]);
            comboboxDistrictUpdate.setValue(parts[7]);

            comboboxTypeUpdate.setValue(parts[9]);

            btnUpdateCustomer.setOnAction(e->{
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Confirmar Actualización");
                alerta.setHeaderText("¿Está seguro de que desea actualizar este cliente?");
                alerta.setContentText("Esta acción podría modificar registros asociados en cascada y no se puede deshacer.");

                Optional<ButtonType> resultado = alerta.showAndWait();

                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    String name = txtNameUpdate.getText();
                    String lastName1 = txtLastName1Update.getText();
                    String lastName2 = lblLastname2Update.getText();
                    String address = textareaAddressUpdate.getText();
                    String district = comboboxDistrictUpdate.getValue();
                    String type = comboboxTypeUpdate.getValue();

                    Customer customer = new Customer(id, name, lastName1, lastName2, address, district, parts[8], type);
                    customerData.updateCustomerInfo(customer);
                    lblErrorUpdate.setText("CLIENTE ACTUALIZADO CON ÉXITO");

                }


            });
        }
    }

    void viewCustomer(){
        String id = txtIdViewCustomer.getText();

        if(id.isBlank() || id.isEmpty()){
            lblErrorViewCustomer.setText("ERROR: DEBE DIGITAR UN ID");
        } else if (!customerData.existCustomer(id)) {
            lblErrorViewCustomer.setText("ERROR: CLIENTE NO EXISTE");
        }else{
            String informationCustomer = customerData.searchCustomer(id);
            String [] parts = informationCustomer.split("&");

            CustomerCompl customer = new CustomerCompl(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
            List<CustomerCompl> customerList = new ArrayList<>();
            customerList.add(customer);
            ObservableList<CustomerCompl> customers = FXCollections.observableList(customerList);
            tableviewViewCustomer.setItems(customers);
        }
    }

    void addEmail(){
        String id = txtIdEmail.getText();
        String email = txtNewEmail.getText();

        if(id.isBlank() || id.isEmpty()){
            lblErrorEmail.setText("ERROR: DEBE DIGITAR UN ID");
        } else if (email.isEmpty() || email.isBlank()) {
            lblErrorEmail.setText("ERROR: DEBE DIGITAR UN NUEVO CORREO");
        } else if (!customerData.existCustomer(id)) {
            lblErrorEmail.setText("ERROR: NO EXISTE ESE CLIENTE");
        } else if (customerData.existCustomerEmail(id, email)) {
            lblErrorEmail.setText("ERROR: YA ESTA REGISTRADO ESE CORREO EN ESE CLIENTE");
        }else{
            customerData.addEmail(id, email);
            lblErrorEmail.setText("CORREO AÑADIDO CON ÉXITO");
        }
    }
    
    void deleteEmail(){
        String id = txtIdEmail.getText();
        String email = txtOldEmail.getText();

        if(id.isBlank() || id.isEmpty()){
            lblErrorEmail.setText("ERROR: DEBE DIGITAR UN ID");
        } else if (email.isEmpty() || email.isBlank()) {
            lblErrorEmail.setText("ERROR: DEBE DIGITAR EL CORREO ACTUAL");
        } else if (!customerData.existCustomer(id)) {
            lblErrorEmail.setText("ERROR: NO EXISTE ESE CLIENTE");
        } else if (!customerData.existCustomerEmail(id, email)) {
            lblErrorEmail.setText("ERROR: ESE CLIENTE NO TIENE ESE CORREO REGISTRADO");
        }else{
            customerData.deleteEmail(id, email);
            lblErrorEmail.setText("CORREO ELIMINADO CON ÉXITO");
        }
    }
    
    void updateEmail(){
        String id = txtIdEmail.getText();
        String newEmail = txtNewEmail.getText(); 
        String oldEmail = txtOldEmail.getText();

        if(id.isBlank() || id.isEmpty()){
            lblErrorEmail.setText("ERROR: DEBE DIGITAR UN ID");
        } else if (oldEmail.isEmpty() || oldEmail.isBlank()) {
            lblErrorEmail.setText("ERROR: DEBE DIGITAR EL CORREO ACTUAL");
        } else if (newEmail.isBlank() || newEmail.isEmpty()) {
            lblErrorEmail.setText("ERROR: DEBE DIGITAR EL CORREO NUEVO");
        } else if (!customerData.existCustomer(id)) {
            lblErrorEmail.setText("ERROR: NO EXISTE ESE CLIENTE");
        } else if (!customerData.existCustomerEmail(id, oldEmail)) {
            lblErrorEmail.setText("ERROR: ESE CLIENTE NO TIENE ESE CORREO REGISTRADO");
        }else{
            customerData.updateModifyCustomerEmail(newEmail, id, oldEmail);
            lblErrorEmail.setText("CORREO MODIFICADO CON ÉXITO");
        }
    }

    void filterForDistrict(){
        String district = comboboxDistrictQuestions.getValue();

        if(district == null){
            lblErrorQuestions.setText("ERROR: DEBE SELECCIONAR UN DISTRITO");
        }else{
            List<CustomerCompl> informationCustomer = customerData.searchCustomerDistrict(district);
            ObservableList<CustomerCompl> observableList = FXCollections.observableList(informationCustomer);
            tableviewQuestions.setItems(observableList);

        }
    }

    void filterForType(){
        String type = comboboxTypeQuestions.getValue();

        if(type == null){
            lblErrorQuestions.setText("ERROR: DEBE SELECCIONAR UN TIPO DE CLIENTE");
        }else{
            List<CustomerCompl> informationCustomer = customerData.searchCustomersType(type);
            ObservableList<CustomerCompl> observableList = FXCollections.observableList(informationCustomer);
            tableviewQuestions.setItems(observableList);
        }
    }

    void filterForTypeAndDistrict(){
        String type = comboboxTypeQuestions.getValue();
        String district = comboboxDistrictQuestions.getValue();

        if(type == null){
            lblErrorQuestions.setText("ERROR: DEBE SELECCIONAR UN TIPO DE CLIENTE");
        } else if (district == null) {
            lblErrorQuestions.setText("ERROR: DEBE SELECCIONAR UN DISTRITO");
        } else{
            List<CustomerCompl> informationCustomer = customerData.searchTypeDistrict(type, district);
            ObservableList<CustomerCompl> observableList = FXCollections.observableList(informationCustomer);
            tableviewQuestions.setItems(observableList);
        }
    }
}
