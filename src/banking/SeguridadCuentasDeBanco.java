package banking;

import java.sql.*;
import java.util.Base64;

public class SeguridadCuentasDeBanco {

    private static final String URL = "jdbc:postgresql://localhost:5432/DBVulnerabilidades";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Daniel1128";

    public static void mostrarCuentasBancarias() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM cuenta_bancaria";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String numeroCuenta = resultSet.getString("numero_cuenta");
                    // Imprimir los datos de la cuenta bancaria
                    System.out.println("Número de Cuenta: " + numeroCuenta);
                    System.out.println("¿Es Base64? " + isBase64Encoded(numeroCuenta));
                    System.out.println("--------------------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static boolean isBase64Encoded(String value) {
        try {
            // Intenta decodificar la cadena como Base64 y luego verifica si el resultado es una cadena imprimible
            byte[] decoded = Base64.getDecoder().decode(value);
            String decodedString = new String(decoded);
            return decodedString.matches("[\\p{Print}]+");
        } catch (IllegalArgumentException e) {
            // Si la decodificación falla, asumimos que no es Base64
            return false;
        }
    }
}
