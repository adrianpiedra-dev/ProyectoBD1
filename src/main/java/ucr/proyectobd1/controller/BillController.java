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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ucr.proyectobd1.data.BillData;
import ucr.proyectobd1.model.Bill;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BillController implements Initializable {

    @javafx.fxml.FXML
    private Button btnUpdate;
    @javafx.fxml.FXML
    private Button btnCreate;
    @javafx.fxml.FXML
    private TextField lblDiscount;
    @javafx.fxml.FXML
    private Button btnClean;
    @javafx.fxml.FXML
    private ImageView imageviewLogo;
    @javafx.fxml.FXML
    private Label lblError;
    @javafx.fxml.FXML
    private Button btnBackMenu;
    @javafx.fxml.FXML
    private Button btnSearch;
    @javafx.fxml.FXML
    private ComboBox <String> comboboxState;
    @javafx.fxml.FXML
    private TextField lblAmountF;
    @javafx.fxml.FXML
    private TextField lblNumber;
    @javafx.fxml.FXML
    private DatePicker dateDate;
    @javafx.fxml.FXML
    private DatePicker datePDate;
    @javafx.fxml.FXML
    private DatePicker dateDateP;
    @javafx.fxml.FXML
    private TextField lblTelephone;
    @javafx.fxml.FXML
    private TextField lblTax;
    @javafx.fxml.FXML
    private TextField lblAmount;
    @javafx.fxml.FXML
    private TextField lblPoints;

    BillData billData = new BillData();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image logo = new Image(getClass().getResourceAsStream("/ucr/proyectobd1/Logo.png"));
        imageviewLogo.setImage(logo);

        imageviewLogo.setFitWidth(200);
        imageviewLogo.setPreserveRatio(true);
        ObservableList<String> states = FXCollections.observableArrayList("PENDIENTE", "CANCELADO", "VENCIDO");
        comboboxState.setItems(states);
    }

    void operationsButtons(){
        btnBackMenu.setOnAction(e->{
            openWindow("/ucr/proyectobd1/hello-view.fxml", e);
        });
    }

    void createBill(){
        String number = java.util.UUID.randomUUID().toString().substring(0,10).toUpperCase();
        lblNumber.setText(number);
        String line = lblTelephone.getText();
        LocalDate dateSelected = dateDate.getValue();
        Date date = Date.valueOf(dateSelected);
        LocalDate dateVSelected = datePDate.getValue();
        Date dateV = Date.valueOf(dateVSelected);
        float amount = Float.parseFloat(lblAmount.getText());
        float tax = Float.parseFloat(lblTax.getText());
        String state = comboboxState.getValue();
        int pointRem = Integer.parseInt(lblPoints.getText());
        float discounts = Float.parseFloat(lblDiscount.getText());
        float fAmount = Float.parseFloat(lblAmountF.getText());
        LocalDate dateSelectedP = dateDateP.getValue(); //PUEDE SER NULO
        Date dateP = Date.valueOf(dateSelectedP); //PUEDE SER NULO

        if(line.isEmpty() || line.isBlank()){
            showError("ERROR", "DEBE DIGITAR EL NÚMERO DE FACTURA");
            return;
        }
        if(date == null){
            showError("ERROR", "DEBE SELECCIONAR LA FECHA DE EMISIÓN");
            return;
        }
        if(dateV == null){
            showError("ERROR", "DEBE SELECCIONAR LA FECHA DE VENCIMIENTO");
            return;
        }
        if(amount <=0){
            showError("ERROR", "DEBE DIGITAR UNA CANTIDAD A PAGAR VÁLIDA");
            return;
        }
        if(tax < 0){
            showError("ERROR", "DEBE DIGITAR UNA VALOR DE IMPUESTOS VÁLIDO");
            return;
        }
        if(state == null){
            showError("ERROR", "DEBE SELECCIONAR UN ESTADO");
            return;
        }
        if(pointRem < 0){
            showError("ERROR", "DEBE DIGITAR UN VALOR DE PUNTOS VÁLIDO");
            return;
        }
        //TODO DEBE REVISAR SI ESE CLIENTE TIENE LA CANTIDAD DE PUNTOS
        //TODO DEBE REDUCIRLE LA CANTIDAD DE PUNTOS A ESE CLIENTE
        if(discounts < 0){
            showError("ERROR", "DEBE DIGITAR UN VALOR DE DESCUENTO VÁLIDO");
            return;
        }
        if(fAmount <= 0 || fAmount > amount){
            showError("ERROR", "DEBE DIGITAR UN VALOR FINAL DE PAGO VÁLIDO");
            return;
        }
        if(billData.existPT(number)){
            showError("ERROR", "YA EXISTE ESA FACTURA");
            return;
        }

        //TODO DEBE REVISAR SI EXISTE ESA LÍNEA

        Bill bill = new Bill(number, line, date, dateV, amount, tax, state, pointRem, discounts, fAmount, dateP);
        billData.insertBill(bill);
        showSuccess("FACTURA CREADA CON ÉXITO");
    }

    void search(){
        String number = lblNumber.getText();

        if(number.isEmpty() || number.isBlank()){
            showError("ERROR", "DEBE DIGITAR UN NÚMERO DE FACTURA");
            return;
        }

        if(!billData.existPT(number)){
            showError("ERROR", "NO EXISTE ESA FACTURA");
            return;
        }

        Bill bill = billData.getBill(number);

        lblNumber.setText(bill.getNumber());
        lblTelephone.setText(bill.getTelephone());
        dateDate.setValue(LocalDate.parse(String.valueOf(bill.getDate())));
        datePDate.setValue(LocalDate.parse(String.valueOf(bill.getdDate())));
        lblAmount.setText(String.valueOf(bill.getAmount()));
        lblTax.setText(String.valueOf(bill.getTax()));
        comboboxState.setValue(bill.getState());
        lblPoints.setText(String.valueOf(bill.getPointRem()));
        lblDiscount.setText(String.valueOf(bill.getDiscounts()));
        lblAmountF.setText(String.valueOf(bill.getfAmount()));
        dateDateP.setValue(LocalDate.parse(String.valueOf(bill.getpDate())));
    }

    void update(){
        String number = lblNumber.getText();
        String line = lblTelephone.getText();
        LocalDate dateSelected = dateDate.getValue();
        Date date = Date.valueOf(dateSelected);
        LocalDate dateVSelected = datePDate.getValue();
        Date dateV = Date.valueOf(dateVSelected);
        float amount = Float.parseFloat(lblAmount.getText());
        float tax = Float.parseFloat(lblTax.getText());
        String state = comboboxState.getValue();
        int pointRem = Integer.parseInt(lblPoints.getText());
        float discounts = Float.parseFloat(lblDiscount.getText());
        float fAmount = Float.parseFloat(lblAmountF.getText());
        LocalDate dateSelectedP = dateDateP.getValue(); //PUEDE SER NULO
        Date dateP = Date.valueOf(dateSelectedP); //PUEDE SER NULO

        if(number.isEmpty() || number.isBlank()){
            showError("ERROR", "DEBE DIGITAR EL NÚMERO DE FACTURA");
            return;
        }
        if(line.isEmpty() || line.isBlank()){
            showError("ERROR", "DEBE DIGITAR EL NÚMERO DE FACTURA");
            return;
        }
        if(date == null){
            showError("ERROR", "DEBE SELECCIONAR LA FECHA DE EMISIÓN");
            return;
        }
        if(dateV == null){
            showError("ERROR", "DEBE SELECCIONAR LA FECHA DE VENCIMIENTO");
            return;
        }
        if(amount <=0){
            showError("ERROR", "DEBE DIGITAR UNA CANTIDAD A PAGAR VÁLIDA");
            return;
        }
        if(tax < 0){
            showError("ERROR", "DEBE DIGITAR UNA VALOR DE IMPUESTOS VÁLIDO");
            return;
        }
        if(state == null){
            showError("ERROR", "DEBE SELECCIONAR UN ESTADO");
            return;
        }
        if(pointRem < 0){
            showError("ERROR", "DEBE DIGITAR UN VALOR DE PUNTOS VÁLIDO");
            return;
        }
        //TODO DEBE REVISAR SI ESE CLIENTE TIENE LA CANTIDAD DE PUNTOS
        //TODO DEBE REDUCIRLE LA CANTIDAD DE PUNTOS A ESE CLIENTE
        if(discounts < 0){
            showError("ERROR", "DEBE DIGITAR UN VALOR DE DESCUENTO VÁLIDO");
            return;
        }
        if(fAmount <= 0 || fAmount > amount){
            showError("ERROR", "DEBE DIGITAR UN VALOR FINAL DE PAGO VÁLIDO");
            return;
        }
        if(!billData.existPT(number)){
            showError("ERROR", "NO EXISTE ESA FACTURA");
            return;
        }
        Bill bill = new Bill(number, line, date, dateV, amount, tax, state, pointRem, discounts, fAmount, dateP);
        billData.update(bill);
        showSuccess("FACTURA ACTUALIZADA CON ÉXITO");
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
