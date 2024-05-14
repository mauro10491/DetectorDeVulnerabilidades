package password;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManagerContrasenas {

    // Establece las credenciales de la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/DBVulnerabilidades";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Daniel1128";

    // Método para obtener las contraseñas y sus recomendaciones por proyecto
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
                    // Obtener la recomendación para la contraseña actual
                    String recomendacion = obtenerRecomendacion(contraseña);
                    // Crear un mensaje con la contraseña y la recomendación
                    String mensaje = "Contraseña para el proyecto " + idProyecto + ": " + contraseña + " - " + recomendacion;
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

    // Método para obtener la recomendación para una contraseña dada
    private static String obtenerRecomendacion(String contraseña) {
        StringBuilder recomendacion = new StringBuilder();
        // Verifica si la contraseña cumple con ciertos criterios y agrega recomendaciones en función de eso
        if (contraseña.length() < 8) {
            recomendacion.append("La contraseña debe tener al menos 8 caracteres.\n");
        }
        if (!contieneCaracteresEspeciales(contraseña)) {
            recomendacion.append("La contraseña debe contener al menos un carácter especial (!@#$%^&*).\n");
        }
        if (!contieneMayusculas(contraseña)) {
            recomendacion.append("La contraseña debe contener al menos una letra mayúscula.\n");
        }
        if (!contieneMinusculas(contraseña)) {
            recomendacion.append("La contraseña debe contener al menos una letra minúscula.\n");
        }
        if (!contieneDigitos(contraseña)) {
            recomendacion.append("La contraseña debe contener al menos un dígito (0-9).\n");
        }
        if (recomendacion.length() == 0) {
            recomendacion.append("La contraseña es segura.");
        }
        // Devuelve la recomendación en forma de cadena
        return recomendacion.toString();
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
