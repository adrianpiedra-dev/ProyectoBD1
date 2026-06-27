package ucr.proyectobd1.data;

import ucr.proyectobd1.connection.ConnectionDB;
import ucr.proyectobd1.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerData {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertCustomer(Customer customer) {
        String instructionSQL = "INSERT INTO CLIENTE (ID, NOMBRE, APELLIDO_1, APELLIDO_2, DIRECCION_EXACTA, CodDistrito, TIPO) VALUES (?,?,?,?,?,?,?)";
        String instructionSQL2 = "INSERT INTO CLIENTE_CORREO (ID_CLIENTE, CORREO) VALUES (?,?)";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instructionSQL);
            statement.setString(1, customer.getId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getLastName1());
            statement.setString(4, customer.getLastName2());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getCodDistrict());
            statement.setString(7, customer.getTypeCustomer());
            statement.execute();

            PreparedStatement statement2 = con.prepareStatement(instructionSQL2);
            statement2.setString(1, customer.getId());
            statement2.setString(2, customer.getEmail());
            statement2.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteCustomer(String id) {
        String instructionSQL1 = "DELETE FROM CLIENTE_CORREO WHERE ID_CLIENTE = ?";
        String instructionSQL2 = "DELETE FROM CLIENTE WHERE ID = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement1 = con.prepareStatement(instructionSQL1);
            statement1.setString(1, id);
            statement1.execute();

            PreparedStatement statement2 = con.prepareStatement(instructionSQL2);
            statement2.setString(1, id);
            statement2.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String searchCustomer(String code) {
        String result = "";
        String instructionSQL1 = "SELECT CLIENTE.ID, CLIENTE.NOMBRE, CLIENTE.APELLIDO_1, CLIENTE.APELLIDO_2, CLIENTE.DIRECCION_EXACTA, CANTON.Provincia, CANTON.NombreCanton, DISTRITO.Nombre_Distrito, CLIENTE_CORREO.CORREO, CLIENTE.TIPO FROM CLIENTE JOIN DISTRITO ON CLIENTE.CodDistrito = DISTRITO.CodDistrito JOIN CANTON ON DISTRITO.CodCanton = CANTON.CodCanton JOIN CLIENTE_CORREO ON CLIENTE.ID = CLIENTE_CORREO.ID_CLIENTE WHERE CLIENTE.ID = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instructionSQL1);
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String lastName1 = resultSet.getString(3);
                String lastName2 = resultSet.getString(4);
                String address = resultSet.getString(5);
                String province = resultSet.getString(6);
                String canton = resultSet.getString(7);
                String district = resultSet.getString(8);
                String email = resultSet.getString(9);
                String type = resultSet.getString(10);

                result += id + "," + name + "," + lastName1 + "," + lastName2 + "," + address + "," + province + "," + canton + "," + district + "," + email + "," + type;

            }

            return result;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String searchCustomerDistrict(String code){

        String result = "";

        String instructionSQL1 = "SELECT CLIENTE.ID, CLIENTE.NOMBRE, CLIENTE.APELLIDO_1, CLIENTE.APELLIDO_2, CLIENTE.DIRECCION_EXACTA, CANTON.Provincia, CANTON.NombreCanton, DISTRITO.Nombre_Distrito, CLIENTE_CORREO.CORREO, CLIENTE.TIPO FROM CLIENTE JOIN DISTRITO ON CLIENTE.CodDistrito = DISTRITO.CodDistrito JOIN CANTON ON DISTRITO.CodCanton = CANTON.CodCanton JOIN CLIENTE_CORREO ON CLIENTE.ID = CLIENTE_CORREO.ID_CLIENTE WHERE DISTRITO.CodDistrito = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instructionSQL1);
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String lastName1 = resultSet.getString(3);
                String lastName2 = resultSet.getString(4);
                String address = resultSet.getString(5);
                String province = resultSet.getString(6);
                String canton = resultSet.getString(7);
                String district = resultSet.getString(8);
                String email = resultSet.getString(9);
                String type = resultSet.getString(10);

                result += id + "," + name + "," + lastName1 + "," + lastName2 + "," + address + "," + province + "," + canton + "," + district + "," + email + "," + type;


            }

            return result;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> searchCustomersType(String type){

        List<String> list = new ArrayList<>();

        String instructionSQL1 = "SELECT CLIENTE.ID, CLIENTE.NOMBRE, CLIENTE.APELLIDO_1, CLIENTE.APELLIDO_2, CLIENTE.DIRECCION_EXACTA, CANTON.Provincia, CANTON.NombreCanton, DISTRITO.Nombre_Distrito, CLIENTE_CORREO.CORREO FROM CLIENTE JOIN DISTRITO ON CLIENTE.CodDistrito = DISTRITO.CodDistrito JOIN CANTON ON DISTRITO.CodCanton = CANTON.CodCanton JOIN CLIENTE_CORREO ON CLIENTE.ID = CLIENTE_CORREO.ID_CLIENTE WHERE CLIENTE.TIPO = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instructionSQL1);
            statement.setString(1, type);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String lastName1 = resultSet.getString(3);
                String lastName2 = resultSet.getString(4);
                String address = resultSet.getString(5);
                String province = resultSet.getString(6);
                String canton = resultSet.getString(7);
                String district = resultSet.getString(8);
                String email = resultSet.getString(9);

                String result = id + "," + name + "," + lastName1 + "," + lastName2 + "," + address + "," + province + "," + canton + "," + district + "," + email;
                list.add(result);
            }

            return list;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomerInfo(Customer customer){
        String instructionSQL = "UPDATE CLIENTE SET NOMBRE = ?, APELLIDO_1 = ?, APELLIDO_2 = ?, DIRECCION_EXACTA = ?, CodDistrito = ?, TIPO = ? WHERE ID = ?; ";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(instructionSQL)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getLastName1());
            statement.setString(3, customer.getLastName2());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getCodDistrict());
            statement.setString(6, customer.getTypeCustomer());

            statement.setString(7, customer.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmail(String idCustomer, String newEmail){
        String instructionSQL = "INSERT INTO CLIENTE_CORREO (ID_CLIENTE, CORREO) VALUES (?,?) ";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instructionSQL);
            statement.setString(1, idCustomer);
            statement.setString(2, newEmail);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmail(String idCustomer, String newEmail){
        String instructionSQL = "DELETE FROM CLIENTE_CORREO WHERE ID_CLIENTE = ? AND CORREO = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instructionSQL);
            statement.setString(1, idCustomer);
            statement.setString(2, newEmail);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateModifyCustomerEmail(String newEmail, String id, String oldEmail){
        String instructionSQL = "UPDATE CLIENTE_CORREO SET CORREO = ? WHERE ID_CLIENTE = ? AND CORREO = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instructionSQL);
            statement.setString(1, newEmail);
            statement.setString(2, id);
            statement.setString(3, oldEmail);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
