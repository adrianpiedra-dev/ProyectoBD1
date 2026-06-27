package ucr.proyectobd1.data;

import org.junit.jupiter.api.Test;
import ucr.proyectobd1.model.Customer;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class CustomerDataTest {

    CustomerData customerData = new CustomerData();

    @Test
    void insertCustomer() {
       Customer customer5 = new Customer("305550555", "DEISY", "SOLANO", "FERNANDEZ", "150 metros al norte de la Iglesia", "CART1-3", "TEST5@gmail.com", "PLATA");
        Customer customer6 = new Customer("306660666", "ROGER", "RODRIGUEZ", "AZOFEIFA", "Detras del cementerio", "CART1-2", "TEST6@gmail.com", "ORO");
        Customer customer7 = new Customer("307770777", "KARLA", "AZOFEIFA", "CORDOBA", "Finca El Potrillo", "SJ1-1", "TEST7@gmail.com", "BRONCE");
        Customer customer8 = new Customer("308880888", "SUSAN", "MORA", "RODRIGUEZ", "Diagonal al Super San Jose", "SJ1-2", "TEST8@gmail.com", "PLATINO");
        Customer customer9 = new Customer("309990999", "ROBERTO", "CORDOBA", "MEJIA", "200 metros sur de la terminal de buses", "CART1-5", "TEST9@gmail.com", "ORO");
        Customer customer10 = new Customer("305680723", "ADRIAN", "PIEDRA", "ARRIETA", "25 metros oeste de la Panaderia Los Cocos", "CAR1-1", "adriaparrieta516@gmail.com", "PLATINO");

        customerData.insertCustomer(customer5);
        customerData.insertCustomer(customer6);
        customerData.insertCustomer(customer7);
        customerData.insertCustomer(customer8);
        customerData.insertCustomer(customer9);
        customerData.insertCustomer(customer10);
    }

    @Test
    void deleteCustomer(){
        customerData.deleteCustomer("3-0568-0723");
    }

    @Test
    void searchCustomer() {
       String result = customerData.searchCustomer("305680723");
       System.out.println(result);
    }

    @Test
    void searchCustomerDistrict() {
        String result = customerData.searchCustomerDistrict("CAR1-1");
        System.out.println(result);
    }

    @Test
    void searchCustomersType() {
        List<String> list = customerData.searchCustomersType("PLATINO");
        for(int i=0; i < list.size(); i++){
            System.out.println(list.get(i).toString());
        }
    }

    @Test
    void updateCustomerInfo() {
        Customer customer = new Customer("305680723", "ADRIAN", "PIEDRA", "ARRIETA", "800 metros norte de la Catedral", "CART1-4", "adriaparrieta516@gmail.com", "PLATINO");
        customerData.updateCustomerInfo(customer);
    }

    @Test
    void addEmail() {
        customerData.addEmail("305680723", "ADRIAN.PIEDRAARRIETA@TEC.AC.CR");
    }

    @Test
    void deleteEmail() {
        customerData.deleteEmail("305680723", "ADRIAN.PIEDRAARRIETA@TEC.AC.CR");
    }

    @Test
    void updateModifyCustomerEmail() {
        customerData.updateModifyCustomerEmail("adrianparrieta516@outlook.com", "305680723", "adriaparrieta516@gmail.com");
    }
}