package ucr.proyectobd1.data;

import ucr.proyectobd1.model.Bill;
import ucr.proyectobd1.model.TariffPlan;

import java.sql.*;

public class BillData {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    public void insertBill(Bill bill) {
        String instruccionSQL = "INSERT INTO FACTURA (NUMERO, TELEFONO_LM, FECHA, FECHA_V, MONTO, IMPUESTOS, ESTADO_P, PUNTOS_REM, DESCUENTOS, MONTO_F, FECHA_P) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, bill.getNumber());
            statement.setString(2, bill.getTelephone());
            statement.setDate(3, bill.getDate());
            statement.setDate(4, bill.getdDate());
            statement.setFloat(5, bill.getAmount());
            statement.setFloat(6, bill.getTax());
            statement.setString(7, bill.getState());
            statement.setInt(8, bill.getPointRem());
            statement.setFloat(9, bill.getDiscounts());
            statement.setFloat(10, bill.getfAmount());
            statement.setDate(11, bill.getpDate());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //SE ELIMINÓ EL DELETE FACTURAS POR CUESTIONES DE SEGURIDAD

    public Bill getBill(String numberCode){
        String instruccionSQL = "SELECT * FROM FACTURA WHERE NUMERO = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, numberCode);;
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                String number = resultSet.getString(1);
                String telephone = resultSet.getString(2);
                Date date  = resultSet.getDate(3);
                Date dDate = resultSet.getDate(4);
                float amount = resultSet.getFloat(5);
                float tax = resultSet.getFloat(6);
                String state = resultSet.getString(7);
                int pointRem = resultSet.getInt(8);
                float discounts = resultSet.getFloat(9);
                float fAmount = resultSet.getFloat(10);
                Date pDate = resultSet.getDate(11);

                Bill bill = new Bill(number, telephone, date, dDate, amount, tax, state, pointRem, discounts, fAmount, pDate);
                return bill;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void update(Bill bill){
        String instruccionSQL = "UPDATE FACTURA SET ESTADO_P = ?, FECHA_P = ? WHERE NUMERO = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, bill.getState());;
            statement.setDate(2, bill.getpDate());
            statement.setString(3, bill.getNumber());
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existPT(String cod){
        String instruccionSQL = "SELECT * FROM FACTURA WHERE NUMERO = ?";

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = con.prepareStatement(instruccionSQL);
            statement.setString(1, cod);;
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                System.out.println("SI EXISTE LA FACTURA BUSCADA");
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
