package password;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManagerContrasenas {

    // Establece las credenciales de la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/DBVulnerabilidades";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Daniel1128";

    // Método para guardar las recomendaciones de contraseñas vulnerables en la tabla vulnerabilidad
    public static void guardarRecomendacionesEnVulnerabilidad() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Obtener las contraseñas vulnerables y sus recomendaciones por proyecto
            Map<String, List<String>> contraseñasVulnerablesPorProyecto = obtenerContrasenasVulnerables();

            // Iterar sobre el mapa e insertar las recomendaciones en la tabla vulnerabilidad
            for (Map.Entry<String, List<String>> entry : contraseñasVulnerablesPorProyecto.entrySet()) {
                String idProyecto = entry.getKey();
                List<String> mensajes = entry.getValue();
                for (String mensaje : mensajes) {
                    insertarRecomendacionEnVulnerabilidad(connection, idProyecto, mensaje);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todas las contraseñas por proyecto
    public static Map<String, List<String>> obtenerContrasenasPorProyecto() {
        // Mapa para almacenar las contraseñas por proyecto
        Map<String, List<String>> contraseñasPorProyecto = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Consulta SQL para seleccionar todas las contraseñas de la tabla contrasena_guardada
            String query = "SELECT id_proyecto, contrasena FROM contrasena_guardada";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                // Iterar sobre los resultados de la consulta
                while (resultSet.next()) {
                    // Obtener el ID del proyecto y la contraseña de la fila actual
                    int idProyecto = resultSet.getInt("id_proyecto");
                    String contraseña = resultSet.getString("contrasena");
                    // Crear un mensaje con la contraseña
                    String mensaje = "Contraseña para el proyecto " + idProyecto + ": " + contraseña;
                    // Verificar si el proyecto ya tiene una lista de contraseñas
                    if (!contraseñasPorProyecto.containsKey(Integer.toString(idProyecto))) {
                        // Si no hay lista, crear una nueva lista
                        contraseñasPorProyecto.put(Integer.toString(idProyecto), new ArrayList<>());
                    }
                    // Agregar el mensaje al proyecto correspondiente en el mapa
                    contraseñasPorProyecto.get(Integer.toString(idProyecto)).add(mensaje);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devolver el mapa con las contraseñas por proyecto
        return contraseñasPorProyecto;
    }

    // Método para obtener las contraseñas vulnerables y sus recomendaciones por proyecto
    public static Map<String, List<String>> obtenerContrasenasVulnerables() {
        // Mapa para almacenar las contraseñas vulnerables por proyecto
        Map<String, List<String>> contraseñasVulnerablesPorProyecto = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Consulta SQL para seleccionar todas las contraseñas de la tabla contrasena_guardada
            String query = "SELECT id_proyecto, contrasena FROM contrasena_guardada";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                // Iterar sobre los resultados de la consulta
                while (resultSet.next()) {
                    // Obtener el ID del proyecto y la contraseña de la fila actual
                    int idProyecto = resultSet.getInt("id_proyecto");
                    String contraseña = resultSet.getString("contrasena");
                    // Obtener la recomendación para la contraseña actual
                    String recomendacion = obtenerRecomendacion(contraseña);
                    if (!recomendacion.isEmpty()) {
                        // Crear un mensaje con la contraseña y la recomendación
                        String mensaje = "Contraseña para el proyecto " + idProyecto + ": " + contraseña + " - " + recomendacion;
                        // Verificar si el proyecto ya tiene una lista de contraseñas vulnerables
                        if (!contraseñasVulnerablesPorProyecto.containsKey(Integer.toString(idProyecto))) {
                            // Si no hay lista, crear una nueva lista
                            contraseñasVulnerablesPorProyecto.put(Integer.toString(idProyecto), new ArrayList<>());
                        }
                        // Agregar el mensaje al proyecto correspondiente en el mapa
                        contraseñasVulnerablesPorProyecto.get(Integer.toString(idProyecto)).add(mensaje);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devolver el mapa con las contraseñas vulnerables por proyecto
        return contraseñasVulnerablesPorProyecto;
    }

    // Método para obtener la recomendación para una contraseña dada
    private static String obtenerRecomendacion(String contraseña) {
        List<String> recomendaciones = new ArrayList<>();

        // Verifica si la contraseña cumple con ciertos criterios y agrega recomendaciones en función de eso
        if (contraseña.length() < 8) {
            recomendaciones.add("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!contieneCaracteresEspeciales(contraseña)) {
            recomendaciones.add("La contraseña debe contener al menos un carácter especial (!@#$%^&*).");
        }
        if (!contieneMayusculas(contraseña)) {
            recomendaciones.add("La contraseña debe contener al menos una letra mayúscula.");
        }
        if (!contieneMinusculas(contraseña)) {
            recomendaciones.add("La contraseña debe contener al menos una letra minúscula.");
        }
        if (!contieneDigitos(contraseña)) {
            recomendaciones.add("La contraseña debe contener al menos un dígito (0-9).");
        }
        if (recomendaciones.isEmpty()) {
            // Si no hay recomendaciones, la contraseña es segura
            return "";
        }

        // Devuelve la recomendación en forma de cadena, concatenando todas las recomendaciones
        return String.join("\n", recomendaciones);
    }

    // Método para insertar una recomendación en la tabla vulnerabilidad
    private static void insertarRecomendacionEnVulnerabilidad(Connection connection, String idProyecto, String recomendacion) throws SQLException {
        // Verificar si hay recomendaciones antes de insertar
        if (!recomendacion.isEmpty()) {
            // SQL para insertar la recomendación concatenada en la tabla vulnerabilidad
            String query = "INSERT INTO vulnerabilidad (tipo_vulnerabilidad, descripcion_vulnerabilidad, fecha_deteccion, recomendacion_vulnerabilidad) " +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Configurar los parámetros del SQL
                statement.setString(1, "Contraseña débil"); // Ejemplo de tipo de vulnerabilidad
                statement.setString(2, "Contraseña débil encontrada en el proyecto " + idProyecto); // Ejemplo de descripción de vulnerabilidad
                statement.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Fecha de detección actual
                statement.setString(4, recomendacion); // Recomendación de la contraseña

                // Ejecutar el SQL
                statement.executeUpdate();
            }
        }
    }

    // Métodos auxiliares para verificar si la contraseña contiene ciertos tipos de caracteres
    private static boolean contieneCaracteresEspeciales(String contraseña) {
        return contraseña.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    private static boolean contieneMayusculas(String contraseña) {
        return contraseña.matches(".*[A-Z].*");
    }

    private static boolean contieneMinusculas(String contraseña) {
        return contraseña.matches(".*[a-z].*");
    }

    private static boolean contieneDigitos(String contraseña) {
        return contraseña.matches(".*\\d.*");
    }
}
