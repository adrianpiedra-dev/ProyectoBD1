package ucr.proyectobd1.data;

import ucr.proyectobd1.model.District;
import ucr.proyectobd1.model.TariffPlan;

import java.lang.invoke.StringConcatException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarrifPlanData {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertTarrifPlan(TariffPlan tariffPlan) {
        String instruccionSQL = "INSERT INTO PLAN_TARIFARIO (CODIGO, NOMBRE, DESCRIPCION, CUOTA_M, GIGABYTES, MINUTOS, MENSAJES, COSTO_EC, CODIGO_CC) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, tariffPlan.getCode());
            statement.setString(2, tariffPlan.getName());
            statement.setString(3, tariffPlan.getDescription());
            statement.setFloat(4, tariffPlan.getShare());
            statement.setInt(5, tariffPlan.getGigabytes());
            statement.setInt(6, tariffPlan.getMinutes());
            statement.setInt(7, tariffPlan.getMs());
            statement.setFloat(8, tariffPlan.getCostEC());
            statement.setString(9, tariffPlan.getCodeCC());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deletePT(String codP) {
        String instruccionSQL = "DELETE FROM PLAN_TARIFARIO WHERE CODIGO = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, codP);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public TariffPlan getTariffPlan(String code){
        String instruccionSQL = "SELECT * FROM PLAN_TARIFARIO WHERE CODIGO = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, code);;
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                float share = resultSet.getInt(4);
                int gigabytes = resultSet.getInt(5);
                int minutes = resultSet.getInt(6);
                int ms = resultSet.getInt(7);
                float costEC = resultSet.getInt(8);
                String codeCC = resultSet.getString(9);

                TariffPlan tariffPlan = new TariffPlan(code, name, description, share, gigabytes, minutes, ms, costEC, codeCC);
                return tariffPlan;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<TariffPlan> getAllTariffPlan (){
        List<TariffPlan> list = new ArrayList<>();

        String instruccionSQL = "SELECT * FROM PLAN_TARIFARIO";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String code = resultSet.getString(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                float share = resultSet.getInt(4);
                int gigabytes = resultSet.getInt(5);
                int minutes = resultSet.getInt(6);
                int ms = resultSet.getInt(7);
                float costEC = resultSet.getInt(8);
                String codeCC = resultSet.getString(9);

                TariffPlan tariffPlan = new TariffPlan(code, name, description, share, gigabytes, minutes, ms, costEC, codeCC);
                list.add(tariffPlan);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void update(TariffPlan tariffPlan){
        String instruccionSQL = "UPDATE PLAN_TARIFARIO SET NOMBRE = ?, DESCRIPCION = ?, CUOTA_M = ?, GIGABYTES = ?, MINUTOS = ?, MENSAJES = ?, COSTO_EC = ?, CODIGO_CC = ? WHERE CODIGO = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, tariffPlan.getName());
            statement.setString(2, tariffPlan.getDescription());
            statement.setFloat(3, tariffPlan.getShare());
            statement.setInt(4, tariffPlan.getGigabytes());
            statement.setInt(5, tariffPlan.getMinutes());
            statement.setInt(6, tariffPlan.getMs());
            statement.setFloat(7, tariffPlan.getCostEC());
            statement.setString(8, tariffPlan.getCodeCC());
            statement.setString(9, tariffPlan.getCode());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existPT(String cod){
        String instruccionSQL = "SELECT * FROM PLAN_TARIFARIO WHERE CODIGO = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, cod);;
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                System.out.println("SI EXISTE EL PLAN TARIFARIO BUSCADO");
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
