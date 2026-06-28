package ucr.proyectobd1.data;

import ucr.proyectobd1.model.Package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDAO {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertPackage(Package pkg) throws SQLException {
        String sql = "INSERT INTO PAQUETE (CODIGO, NOMBRE, VIGENCIA, PRECIO, CANTIDAD) VALUES (?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, pkg.getCode().trim());
            statement.setString(2, pkg.getName().trim());
            statement.setInt(3, pkg.getValidity());
            statement.setDouble(4, pkg.getPrice());
            statement.setInt(5, pkg.getQuantity());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("PRIMARY KEY") || e.getMessage().contains("Violation of PRIMARY KEY")) {
                throw new SQLException("El paquete con código '" + pkg.getCode() + "' ya existe.");
            }
            throw e;
        }
    }

    public void updatePackage(Package pkg) throws SQLException {
        String sql = "UPDATE PAQUETE SET NOMBRE = ?, VIGENCIA = ?, PRECIO = ?, CANTIDAD = ? WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, pkg.getName().trim());
            statement.setInt(2, pkg.getValidity());
            statement.setDouble(3, pkg.getPrice());
            statement.setInt(4, pkg.getQuantity());
            statement.setString(5, pkg.getCode().trim());
            statement.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    public void deletePackage(String code) throws SQLException {
        String sql = "DELETE FROM PAQUETE WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, code.trim());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("FOREIGN KEY")) {
                throw new SQLException("No se puede eliminar este paquete porque está relacionado con líneas móviles u otros registros.");
            }
            throw e;
        }
    }

    public Package searchPackage(String code) throws SQLException {
        String sql = "SELECT CODIGO, NOMBRE, VIGENCIA, PRECIO, CANTIDAD FROM PAQUETE WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, code.trim());
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String cod = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    int validity = resultSet.getInt(3);
                    double price = resultSet.getDouble(4);
                    int quantity = resultSet.getInt(5);

                    return new Package(cod, name, validity, price, quantity);
                }
            }

        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    public List<Package> getAllPackages() throws SQLException {
        List<Package> packages = new ArrayList<>();
        String sql = "SELECT CODIGO, NOMBRE, VIGENCIA, PRECIO, CANTIDAD FROM PAQUETE ORDER BY CODIGO";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String name = resultSet.getString(2);
                int validity = resultSet.getInt(3);
                double price = resultSet.getDouble(4);
                int quantity = resultSet.getInt(5);

                packages.add(new Package(code, name, validity, price, quantity));
            }

        } catch (SQLException e) {
            throw e;
        }

        return packages;
    }

    public boolean exists(String code) throws SQLException {
        String sql = "SELECT 1 FROM PAQUETE WHERE CODIGO = ?";
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
