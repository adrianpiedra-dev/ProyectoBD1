package ucr.proyectobd1.data;

import ucr.proyectobd1.model.Bill;
import ucr.proyectobd1.model.IncompatibilitiesPromotions;
import ucr.proyectobd1.model.PromotionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionTypeData {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insert(PromotionType promotionType) {
        String instruccionSQL = "INSERT INTO TIPO_PROMOCION (TipoPromocion, PorcentajeMaximo) VALUES (?,?)";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, promotionType.getCodeTP());
            statement.setFloat(2, promotionType.getPercentage());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void delete(String code) {
        String instruccionSQL = "DELETE FROM TIPO_PROMOCION WHERE TipoPromocion = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, code);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public PromotionType getPromotionType(String numberCode) {
        String instruccionSQL = "SELECT * FROM TIPO_PROMOCION WHERE TipoPromocion = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, numberCode);
            ;
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String code = resultSet.getString(1);
                float percentage = resultSet.getFloat(2);

                PromotionType promotionType = new PromotionType(code, percentage);
                return promotionType;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public boolean exist(String cod) {
        String instruccionSQL = "SELECT * FROM TIPO_PROMOCION WHERE tipoPromocion = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, cod);
            ;
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("SI EXISTE EL TIPO DE FACTURA");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //AÑADIR INCOMPATIBILIDAD

    public void addIncompatibility(String promotion1, String promotion2) {
        String instruccionSQL = "INSERT INTO PROMOCION_INCOMPATIBLE (COD_PROM_1, COD_PROM_1) VALUES (?,?)";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, promotion1);
            statement.setString(2, promotion2);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //ELIMINAR INCOMPATIBILIDAD
    public void deleteIncompatibility(String promotion1, String promotion2) {
        String instruccionSQL = "DELETE FROM PROMOCION_INCOMPATIBLE WHERE COD_PROM_1 = ? AND COD_PROM_2 = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, promotion1);
            statement.setString(2, promotion2);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //VER INCOMPATIBILIDADES
    public List<String> getIncompatibilties(String code){

        List<String> incompatibilities = new ArrayList<>();

        String instruccionSQL = "SELECT COD_PROM_2 FROM PROMOCION_INCOMPATIBLE WHERE COD_PROM_1";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String codeI = resultSet.getString(2);
                incompatibilities.add(code);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return incompatibilities;
    }

    public boolean existIncompatibilities(String code1, String code2){
        String instruccionSQL = "SELECT COD_PROM_2 FROM PROMOCION_INCOMPATIBLE WHERE COD_PROM_1 = ? AND COD_PROM_2 = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, code1);
            statement.setString(2, code2);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
               return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
