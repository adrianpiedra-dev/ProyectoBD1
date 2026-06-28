package ucr.proyectobd1.data;

import ucr.proyectobd1.model.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertService(Service service) throws SQLException {
        String sql = "INSERT INTO SERVICIO (CODIGO, NOMBRE, DESCRIPCION, COSTO_M, CATEGORIA) VALUES (?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, service.getCode().trim());
            statement.setString(2, service.getName().trim());
            statement.setString(3, service.getDescription().trim());
            statement.setDouble(4, service.getMonthlyCost());
            statement.setString(5, service.getCategoryCode().trim());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("PRIMARY KEY") || e.getMessage().contains("Violation of PRIMARY KEY")) {
                throw new SQLException("El servicio con código '" + service.getCode() + "' ya existe.");
            }
            if (e.getMessage().contains("FOREIGN KEY")) {
                throw new SQLException("La categoría comercial especificada no existe.");
            }
            throw e;
        }
    }

    public void updateService(Service service) throws SQLException {
        String sql = "UPDATE SERVICIO SET NOMBRE = ?, DESCRIPCION = ?, COSTO_M = ?, CATEGORIA = ? WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, service.getName().trim());
            statement.setString(2, service.getDescription().trim());
            statement.setDouble(3, service.getMonthlyCost());
            statement.setString(4, service.getCategoryCode().trim());
            statement.setString(5, service.getCode().trim());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("FOREIGN KEY")) {
                throw new SQLException("La categoría comercial especificada no existe.");
            }
            throw e;
        }
    }

    public void deleteService(String code) throws SQLException {
        String sql = "DELETE FROM SERVICIO WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, code.trim());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("FOREIGN KEY")) {
                throw new SQLException("No se puede eliminar este servicio porque está relacionado con líneas móviles u otros registros.");
            }
            throw e;
        }
    }

    public Service searchService(String code) throws SQLException {
        String sql = "SELECT CODIGO, NOMBRE, DESCRIPCION, COSTO_M, CATEGORIA FROM SERVICIO WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, code.trim());
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String cod = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    double cost = resultSet.getDouble(4);
                    String category = resultSet.getString(5);

                    return new Service(cod, name, description, cost, category);
                }
            }

        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    public List<Service> getAllServices() throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT CODIGO, NOMBRE, DESCRIPCION, COSTO_M, CATEGORIA FROM SERVICIO ORDER BY CODIGO";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                double cost = resultSet.getDouble(4);
                String category = resultSet.getString(5);

                services.add(new Service(code, name, description, cost, category));
            }

        } catch (SQLException e) {
            throw e;
        }

        return services;
    }

    public boolean exists(String code) throws SQLException {
        String sql = "SELECT 1 FROM SERVICIO WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, code.trim());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            throw e;
        }
    }
}
