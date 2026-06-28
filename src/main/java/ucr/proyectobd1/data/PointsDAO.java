package ucr.proyectobd1.data;

import ucr.proyectobd1.model.Points;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PointsDAO {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertPoints(Points points) throws SQLException {
        String sql = "INSERT INTO PUNTOS (TELEFONO_LM, FECHA, CANTIDAD, SALDO) VALUES (?,?,?,?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, points.getMobileLinePhone().trim());
            statement.setDate(2, points.getDate());
            statement.setDouble(3, points.getQuantity());
            statement.setDouble(4, points.getBalance());
            statement.execute();

        } catch (SQLException e) {
            if (e.getMessage().contains("FOREIGN KEY")) {
                throw new SQLException("La línea móvil especificada no existe.");
            }
            throw e;
        }
    }

    public void deletePoints(String mobileLinePhone, java.sql.Date date) throws SQLException {
        String sql = "DELETE FROM PUNTOS WHERE TELEFONO_LM = ? AND FECHA = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, mobileLinePhone.trim());
            statement.setDate(2, date);
            statement.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Points> searchByMobileLinePhone(String mobileLinePhone) throws SQLException {
        List<Points> points = new ArrayList<>();
        String sql = "SELECT TELEFONO_LM, FECHA, CANTIDAD, SALDO FROM PUNTOS WHERE TELEFONO_LM = ? ORDER BY FECHA DESC";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, mobileLinePhone.trim());
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String phone = resultSet.getString(1);
                    Date date = resultSet.getDate(2);
                    double quantity = resultSet.getDouble(3);
                    double balance = resultSet.getDouble(4);
                    points.add(new Points(phone, date, quantity, balance));
                }
            }

        } catch (SQLException e) {
            throw e;
        }

        return points;
    }

    public List<Points> getAllPoints() throws SQLException {
        List<Points> points = new ArrayList<>();
        String sql = "SELECT TELEFONO_LM, FECHA, CANTIDAD, SALDO FROM PUNTOS ORDER BY TELEFONO_LM, FECHA DESC";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String phone = resultSet.getString(1);
                Date date = resultSet.getDate(2);
                double quantity = resultSet.getDouble(3);
                double balance = resultSet.getDouble(4);
                points.add(new Points(phone, date, quantity, balance));
            }

        } catch (SQLException e) {
            throw e;
        }

        return points;
    }

    public List<String> getAllMobileLinePhones() throws SQLException {
        List<String> phones = new ArrayList<>();
        String sql = "SELECT DISTINCT TELEFONO FROM LINEA_MOVIL ORDER BY TELEFONO";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                phones.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw e;
        }

        return phones;
    }
}
