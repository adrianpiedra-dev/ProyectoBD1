package ucr.proyectobd1.data;

import ucr.proyectobd1.model.QueryResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueriesDAO {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public List<Map<String, Object>> getInvoiceAmountByCustomer() throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT CLIENTE.ID, CLIENTE.NOMBRE, CLIENTE.APELLIDO_1, CLIENTE.APELLIDO_2, " +
                     "SUM(FACTURA.MONTO_F) as TOTAL_FACTURADO " +
                     "FROM CLIENTE " +
                     "LEFT JOIN LINEA_MOVIL ON CLIENTE.ID = LINEA_MOVIL.ID_CLIENTE " +
                     "LEFT JOIN FACTURA ON LINEA_MOVIL.TELEFONO = FACTURA.TELEFONO_LM " +
                     "GROUP BY CLIENTE.ID, CLIENTE.NOMBRE, CLIENTE.APELLIDO_1, CLIENTE.APELLIDO_2 " +
                     "ORDER BY TOTAL_FACTURADO DESC";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("ID", resultSet.getString(1));
                row.put("Nombre", resultSet.getString(2));
                row.put("Apellido 1", resultSet.getString(3));
                row.put("Apellido 2", resultSet.getString(4));
                row.put("Total Facturado", resultSet.getDouble(5));
                results.add(row);
            }

        } catch (SQLException e) {
            throw e;
        }

        return results;
    }

    public List<Map<String, Object>> getCustomerFullNameWithPhone() throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT UPPER(CONCAT(CLIENTE.NOMBRE, ' ', CLIENTE.APELLIDO_1, ' ', CLIENTE.APELLIDO_2)) as NOMBRE_COMPLETO, " +
                     "CLIENTE_TELEFONO_C.TELEFONO_C " +
                     "FROM CLIENTE " +
                     "JOIN CLIENTE_TELEFONO_C ON CLIENTE.ID = CLIENTE_TELEFONO_C.ID_CLIENTE " +
                     "ORDER BY NOMBRE_COMPLETO, CLIENTE_TELEFONO_C.TELEFONO_C";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                String fullName = resultSet.getString(1);
                String phone = resultSet.getString(2);
                row.put("Cliente - Teléfono", fullName + " - " + phone);
                results.add(row);
            }

        } catch (SQLException e) {
            throw e;
        }

        return results;
    }

    public List<Map<String, Object>> getAuditInfo() throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = "SELECT USUARIO_CREACION, FECHA_CREACION, ESTADO FROM CLIENTE " +
                     "WHERE USUARIO_CREACION IS NOT NULL AND FECHA_CREACION IS NOT NULL " +
                     "ORDER BY FECHA_CREACION DESC";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("Usuario Creación", resultSet.getString(1));
                row.put("Fecha Creación", resultSet.getDate(2));
                row.put("Estado", resultSet.getString(3));
                results.add(row);
            }

        } catch (SQLException e) {
            throw e;
        }

        return results;
    }
}
