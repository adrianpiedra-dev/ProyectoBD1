package ucr.proyectobd1.data;

import ucr.proyectobd1.model.Canton;
import ucr.proyectobd1.model.District;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DistrictData {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertDistrict(District district) {
        String instruccionSQL = "INSERT INTO DISTRITO (CodDistrito, Nombre_Distrito, CodCanton) VALUES (?,?,?)";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, district.getCod());
            statement.setString(2, district.getName());
            statement.setString(3, district.getCodCanton());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteDistrict(String codDistrict) {
        String instruccionSQL = "DELETE FROM DISTRITO WHERE CodDistrito = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, codDistrict);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<District> getDistricts (String codCanton){

        List<District> districts = new ArrayList<>();

        String instruccionSQL = "SELECT * FROM DISTRITO WHERE CodCanton = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, codCanton);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String code = resultSet.getString(1);
                String name = resultSet.getString(2);
                String canton = resultSet.getString(3);

                District district = new District(code, name, canton);

                districts.add(district);
            }

            return districts;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existDistrict(String codDistrict){
        String instruccionSQL = "SELECT * FROM DISTRITO WHERE CodDistrito = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, codDistrict);;
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                System.out.println("SI EXISTE EL DISTRITO BUSCADO");
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
