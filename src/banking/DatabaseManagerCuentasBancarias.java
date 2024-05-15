package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class DatabaseManagerCuentasBancarias {

    private static final String URL = "jdbc:postgresql://localhost:5432/DBVulnerabilidades";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Daniel1128";

    public static void mostrarCuentasBancarias() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM cuenta_bancaria";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int idCuenta = resultSet.getInt("id_cuenta_bancaria");
                    int idProyecto = resultSet.getInt("id_proyecto");
                    String numeroCuenta = resultSet.getString("numero_cuenta");
                    String entidadBancaria = resultSet.getString("entidad_bancaria");
                    String ultimaActividad = resultSet.getString("ultima_actividad");

                    // Imprimir los datos de la cuenta bancaria
                    System.out.println("ID Cuenta: " + idCuenta);
                    System.out.println("ID Proyecto: " + idProyecto);
                    System.out.println("Número de Cuenta: " + numeroCuenta);
                    System.out.println("Entidad Bancaria: " + entidadBancaria);
                    System.out.println("Última Actividad: " + ultimaActividad);
                    System.out.println("--------------------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
