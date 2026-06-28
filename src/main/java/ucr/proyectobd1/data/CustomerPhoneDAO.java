package ucr.proyectobd1.data;

import ucr.proyectobd1.model.CustomerPhone;
import ucr.proyectobd1.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerPhoneDAO {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertCustomerPhone(CustomerPhone customerPhone) throws SQLException {
        String sql = "INSERT INTO CLIENTE_TELEFONO_C (ID_CLIENTE, TELEFONO_C) VALUES (?,?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, customerPhone.getCustomerId().trim());
            statement.setString(2, customerPhone.getPhone().trim());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("PRIMARY KEY") || e.getMessage().contains("Violation of PRIMARY KEY")) {
                throw new SQLException("Este teléfono ya existe para el cliente.");
            }
            if (e.getMessage().contains("FOREIGN KEY")) {
                throw new SQLException("El cliente especificado no existe.");
            }
            throw e;
        }
    }

    public void deleteCustomerPhone(String customerId, String phone) throws SQLException {
        String sql = "DELETE FROM CLIENTE_TELEFONO_C WHERE ID_CLIENTE = ? AND TELEFONO_C = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, customerId.trim());
            statement.setString(2, phone.trim());
            statement.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    public List<CustomerPhone> searchByCustomerId(String customerId) throws SQLException {
        List<CustomerPhone> phones = new ArrayList<>();
        String sql = "SELECT ID_CLIENTE, TELEFONO_C FROM CLIENTE_TELEFONO_C WHERE ID_CLIENTE = ? ORDER BY TELEFONO_C";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, customerId.trim());
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String id = resultSet.getString(1);
                    String phone = resultSet.getString(2);
                    phones.add(new CustomerPhone(id, phone));
                }
            }

        } catch (SQLException e) {
            throw e;
        }

        return phones;
    }

    public List<CustomerPhone> getAllCustomerPhones() throws SQLException {
        List<CustomerPhone> phones = new ArrayList<>();
        String sql = "SELECT ID_CLIENTE, TELEFONO_C FROM CLIENTE_TELEFONO_C ORDER BY ID_CLIENTE, TELEFONO_C";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String customerId = resultSet.getString(1);
                String phone = resultSet.getString(2);
                phones.add(new CustomerPhone(customerId, phone));
            }

        } catch (SQLException e) {
            throw e;
        }

        return phones;
    }

    public boolean exists(String customerId, String phone) throws SQLException {
        String sql = "SELECT 1 FROM CLIENTE_TELEFONO_C WHERE ID_CLIENTE = ? AND TELEFONO_C = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, customerId.trim());
            statement.setString(2, phone.trim());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> getAllCustomerIds() throws SQLException {
        List<String> customerIds = new ArrayList<>();
        String sql = "SELECT DISTINCT ID FROM CLIENTE ORDER BY ID";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                customerIds.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw e;
        }

        return customerIds;
    }
}
