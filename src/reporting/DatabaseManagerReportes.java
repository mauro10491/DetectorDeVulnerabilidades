package reporting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManagerReportes {

    // Establece las credenciales de la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/DBVulnerabilidades";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Daniel1128";

    public static void guardarReporte() {
        // Establecer la conexión utilizando las credenciales definidas localmente
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Definir la consulta SQL para seleccionar los datos de la tabla vulnerabilidad
            String query = "SELECT id_vulnerabilidad, tipo_vulnerabilidad, descripcion_vulnerabilidad, fecha_deteccion, recomendacion_vulnerabilidad FROM vulnerabilidad";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                // Iterar sobre los resultados y construir el contenido para el reporte
                while (resultSet.next()) {
                    int idVulnerabilidad = resultSet.getInt("id_vulnerabilidad");
                    String tipoVulnerabilidad = resultSet.getString("tipo_vulnerabilidad");
                    String descripcion = resultSet.getString("descripcion_vulnerabilidad");
                    String fechaDeteccion = resultSet.getString("fecha_deteccion");
                    String recomendacion = resultSet.getString("recomendacion_vulnerabilidad");

                    // Concatenar los datos en el formato deseado, incluyendo la recomendación de la contraseña
                    StringBuilder contenido = new StringBuilder();
                    contenido.append("Tipo de vulnerabilidad: ").append(tipoVulnerabilidad).append("\n")
                            .append("Descripción: ").append(descripcion).append("\n")
                            .append("Fecha de detección: ").append(fechaDeteccion).append("\n")
                            .append("Recomendación de la contraseña: ").append(recomendacion).append("\n\n");

                    // Insertar el nuevo registro en la tabla reporte
                    insertarReporte(connection, idVulnerabilidad, contenido.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertarReporte(Connection connection, int idVulnerabilidad, String contenido) throws SQLException {
        // Definir la consulta SQL para insertar un nuevo reporte
        String query = "INSERT INTO reporte (id_vulnerabilidad, fecha_reporte, contenido) VALUES (?, CURRENT_DATE, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Establecer los parámetros del PreparedStatement
            statement.setInt(1, idVulnerabilidad);
            statement.setString(2, contenido);

            // Ejecutar la consulta
            statement.executeUpdate();
        }
    }

}
