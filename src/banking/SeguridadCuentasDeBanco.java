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
                    int idProyecto = resultSet.getInt("id_proyecto");
                    String numeroCuenta = resultSet.getString("numero_cuenta");
                    // Verificar si la cuenta bancaria está encriptada
                    boolean encriptada = isBase64Encoded(numeroCuenta);
                    // Si la cuenta no está encriptada, generar un reporte de vulnerabilidad
                    if (!encriptada) {
                        generarReporteVulnerabilidad(connection, idProyecto, numeroCuenta);
                    }
                    // Imprimir los datos de la cuenta bancaria
                    imprimirDatosCuentaBancaria(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void generarReporteVulnerabilidad(Connection connection, int idProyecto, String numeroCuenta) throws SQLException {
        String tipoVulnerabilidad = "cuenta de banco no encriptada";
        String descripcionVulnerabilidad = "Cuenta de banco no encriptada encontrada en el proyecto " + idProyecto;
        String fechaDeteccion = obtenerFechaActual();
        String recomendacion = "La cuenta " + numeroCuenta + " del proyecto " + idProyecto + " es insegura porque no está encriptada.";

        // Insertar el nuevo registro en la tabla vulnerabilidad
        insertarReporteVulnerabilidad(connection, tipoVulnerabilidad, descripcionVulnerabilidad, fechaDeteccion, recomendacion);
    }

    private static void insertarReporteVulnerabilidad(Connection connection, String tipoVulnerabilidad, String descripcionVulnerabilidad, String fechaDeteccion, String recomendacion) throws SQLException {
        String query = "INSERT INTO vulnerabilidad (tipo_vulnerabilidad, descripcion_vulnerabilidad, fecha_deteccion, recomendacion_contrasena) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tipoVulnerabilidad);
            statement.setString(2, descripcionVulnerabilidad);
            // Convertir la fecha de tipo String a java.sql.Date
            java.sql.Date fecha = java.sql.Date.valueOf(fechaDeteccion);
            statement.setDate(3, fecha);
            statement.setString(4, recomendacion);
            statement.executeUpdate();
        }
    }

    private static void imprimirDatosCuentaBancaria(ResultSet resultSet) throws SQLException {
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

    public static boolean isBase64Encoded(String value) {
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

    private static String obtenerFechaActual() {
        // Obtener la fecha actual en formato SQL
        return java.time.LocalDate.now().toString();
    }
}
