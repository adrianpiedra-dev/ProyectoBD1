package ucr.proyectobd1.data;

import ucr.proyectobd1.model.Canton;
import ucr.proyectobd1.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CantonData {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertCanton(Canton canton) {
        String instruccionSQL = "INSERT INTO CANTON (CodCanton, NombreCanton, Provincia) VALUES (?,?,?)";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, canton.getCode().toLowerCase());
            statement.setString(2, canton.getName().toLowerCase());
            statement.setString(3, canton.getProvince().toLowerCase());
            statement.execute();

            System.out.println("CANTÓN CREADO CON ÉXITO");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCanton(String codeCanton){
        String instruccionSQL1 = "DELETE FROM DISTRITO WHERE CodCanton = ?";
        String instruccionSQL2 = "DELETE FROM CANTON WHERE CodCanton = ?";
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement1 = con.prepareStatement(instruccionSQL1);
            statement1.setString(1, codeCanton.toLowerCase());
            statement1.execute();

            PreparedStatement statement2 = con.prepareStatement(instruccionSQL2);
            statement2.setString(1, codeCanton.toLowerCase());
            statement2.execute();

            System.out.println("CANTÓN ELIMINADO CON ÉXITO");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Canton> searchCanton (String province){

        List<Canton> cantons = new ArrayList<>();

        String instruccionSQL = "SELECT CodCanton, NombreCanton, Provincia FROM CANTON WHERE Provincia = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(instruccionSQL)) {

            statement.setString(1, province.toLowerCase());

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String cod = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    String prov = resultSet.getString(3);

                    Canton canton = new Canton(cod, name, prov);
                    cantons.add(canton);
                }
            }

            return cantons;

        } catch (SQLException e) {
            System.out.println("Error al buscar los cantones con provincia " + province + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean existCanton(String codeCanton) {

        String instruccionSQL = "SELECT CodCanton, NombreCanton, Provincia FROM CANTON WHERE CodCanton = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = con.prepareStatement(instruccionSQL)) {

            statement.setString(1, codeCanton);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    System.out.println("CONSULTA REALIZADA CON ÉXITO. SÍ EXISTE ESE CANTÓN (Mismo código o mismo nombre)");
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del cantón: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Si llegó hasta aquí, significa que el resultSet estaba vacío, por ende el cantón NO existe
        System.out.println("CONSULTA REALIZADA CON ÉXITO. NO EXISTE ESE CANTÓN");
        return false;
    }

}
