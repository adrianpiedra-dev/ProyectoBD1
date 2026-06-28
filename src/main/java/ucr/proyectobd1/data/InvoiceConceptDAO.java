package ucr.proyectobd1.data;

import ucr.proyectobd1.model.InvoiceConcept;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceConceptDAO {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertInvoiceConcept(InvoiceConcept concept) throws SQLException {
        String sql = "INSERT INTO FACTURA_CONCEPTO (NUMERO, DESCRIPCION, MONTO_C) VALUES (?,?,?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, concept.getInvoiceNumber().trim());
            statement.setString(2, concept.getDescription().trim());
            statement.setDouble(3, concept.getAmount());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("PRIMARY KEY") || e.getMessage().contains("Violation of PRIMARY KEY")) {
                throw new SQLException("Este concepto ya existe para la factura.");
            }
            if (e.getMessage().contains("FOREIGN KEY")) {
                throw new SQLException("La factura especificada no existe.");
            }
            throw e;
        }
    }

    public void deleteInvoiceConcept(String invoiceNumber, String description) throws SQLException {
        String sql = "DELETE FROM FACTURA_CONCEPTO WHERE NUMERO = ? AND DESCRIPCION = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, invoiceNumber.trim());
            statement.setString(2, description.trim());
            statement.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    public List<InvoiceConcept> searchByInvoiceNumber(String invoiceNumber) throws SQLException {
        List<InvoiceConcept> concepts = new ArrayList<>();
        String sql = "SELECT NUMERO, DESCRIPCION, MONTO_C FROM FACTURA_CONCEPTO WHERE NUMERO = ? ORDER BY DESCRIPCION";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, invoiceNumber.trim());
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String number = resultSet.getString(1);
                    String description = resultSet.getString(2);
                    double amount = resultSet.getDouble(3);
                    concepts.add(new InvoiceConcept(number, description, amount));
                }
            }

        } catch (SQLException e) {
            throw e;
        }

        return concepts;
    }

    public List<InvoiceConcept> getAllInvoiceConcepts() throws SQLException {
        List<InvoiceConcept> concepts = new ArrayList<>();
        String sql = "SELECT NUMERO, DESCRIPCION, MONTO_C FROM FACTURA_CONCEPTO ORDER BY NUMERO, DESCRIPCION";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String number = resultSet.getString(1);
                String description = resultSet.getString(2);
                double amount = resultSet.getDouble(3);
                concepts.add(new InvoiceConcept(number, description, amount));
            }

        } catch (SQLException e) {
            throw e;
        }

        return concepts;
    }

    public List<String> getAllInvoiceNumbers() throws SQLException {
        List<String> invoiceNumbers = new ArrayList<>();
        String sql = "SELECT DISTINCT NUMERO FROM FACTURA ORDER BY NUMERO";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                invoiceNumbers.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw e;
        }

        return invoiceNumbers;
    }

    public boolean exists(String invoiceNumber, String description) throws SQLException {
        String sql = "SELECT 1 FROM FACTURA_CONCEPTO WHERE NUMERO = ? AND DESCRIPCION = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, invoiceNumber.trim());
            statement.setString(2, description.trim());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            throw e;
        }
    }
}
