package ucr.proyectobd1.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoBD1;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "ProyectoBD123";

    Connection con;

    public void connection(){
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("CONECTADO A LA BASE DE DATOS CON ÉXITO");
        } catch (SQLException e) {
            System.err.println("--- DETALLE DEL ERROR ---");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Código SQLState: " + e.getSQLState());
            System.err.println("-------------------------");
            throw new RuntimeException("ERROR AL CONECTARSE A LA BASE DE DATOS");
        }
    }
}
