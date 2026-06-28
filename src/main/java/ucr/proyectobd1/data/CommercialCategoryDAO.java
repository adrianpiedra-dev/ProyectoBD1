package ucr.proyectobd1.data;

import ucr.proyectobd1.model.CommercialCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommercialCategoryDAO {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertCommercialCategory(CommercialCategory category) throws SQLException {
        String sql = "INSERT INTO CATEGORIA_COMERCIAL (CODIGO, DESCRIPCION, VELOCIDAD_M) VALUES (?,?,?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, category.getCode().trim());
            statement.setString(2, category.getDescription().trim());
            statement.setDouble(3, category.getSpeedMbps());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("PRIMARY KEY") || e.getMessage().contains("Violation of PRIMARY KEY")) {
                throw new SQLException("La categoría con código '" + category.getCode() + "' ya existe.");
            }
            throw e;
        }
    }

    public void updateCommercialCategory(CommercialCategory category) throws SQLException {
        String sql = "UPDATE CATEGORIA_COMERCIAL SET DESCRIPCION = ?, VELOCIDAD_M = ? WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, category.getDescription().trim());
            statement.setDouble(2, category.getSpeedMbps());
            statement.setString(3, category.getCode().trim());
            statement.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    public void deleteCommercialCategory(String code) throws SQLException {
        String sql = "DELETE FROM CATEGORIA_COMERCIAL WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, code.trim());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("FOREIGN KEY")) {
                throw new SQLException("No se puede eliminar esta categoría porque está relacionada con servicios u otros registros.");
            }
            throw e;
        }
    }

    public CommercialCategory searchCommercialCategory(String code) throws SQLException {
        String sql = "SELECT CODIGO, DESCRIPCION, VELOCIDAD_M FROM CATEGORIA_COMERCIAL WHERE CODIGO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, code.trim());
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String cod = resultSet.getString(1);
                    String description = resultSet.getString(2);
                    double speed = resultSet.getDouble(3);

                    return new CommercialCategory(cod, description, speed);
                }
            }

        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    public List<CommercialCategory> getAllCommercialCategories() throws SQLException {
        List<CommercialCategory> categories = new ArrayList<>();
        String sql = "SELECT CODIGO, DESCRIPCION, VELOCIDAD_M FROM CATEGORIA_COMERCIAL ORDER BY CODIGO";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String description = resultSet.getString(2);
                double speed = resultSet.getDouble(3);

                categories.add(new CommercialCategory(code, description, speed));
            }

        } catch (SQLException e) {
            throw e;
        }

        return categories;
    }

    public boolean exists(String code) throws SQLException {
        String sql = "SELECT 1 FROM CATEGORIA_COMERCIAL WHERE CODIGO = ?";
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
