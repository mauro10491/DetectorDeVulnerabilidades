package password;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class AnalizadorDeContraseñas {
    private List<String> contraseñasComunes;

    public AnalizadorDeContraseñas(List<String> contraseñasComunes) {
        this.contraseñasComunes = contraseñasComunes;
    }

    public boolean contraseñaFuerte(String password) {
        return password.length() >= 8 && contieneCaracteresEspeciales(password) && contieneDigitos(password) && contieneMayusculas(password);
    }

    private boolean contieneCaracteresEspeciales(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    private boolean contieneDigitos(String password) {
        return password.matches(".*\\d.*");
    }

    private boolean contieneMayusculas(String password) {
        return password.matches(".*[A-Z].*");
    }

    public String sugerenciaDeMejoraDeContraseña(String password) {
        if (!contraseñaFuerte(password)) {
            return "Consider using a mix of uppercase, lowercase, numbers, and special characters.";
        }
        return "Your password is strong.";
    }

    public boolean contraseñaComun(String password) {
        return contraseñasComunes.contains(password);
    }

    public static class DatabaseManagerArchivos {

        public static void obtenerDatos() {
            // URL de conexión a la base de datos, nombre de usuario y contraseña
            String url = "jdbc:postgresql://localhost:5432/DBVulnerabilidades";
            String user = "postgres";
            String password = "Daniel1128";

            try {
                // Establecer la conexión con la base de datos
                Connection connection = DriverManager.getConnection(url, user, password);

                // Crear un objeto Statement para enviar consultas SQL a la base de datos
                Statement statement = connection.createStatement();

                // Ejecutar una consulta SQL y guardar el resultado en un objeto ResultSet
                String query = "SELECT * FROM archivo";
                ResultSet resultSet = statement.executeQuery(query);

                // Procesar el ResultSet y mostrar los resultados por consola
                while (resultSet.next()) {
                    // Obtener datos de cada fila
                    int idArchivo = resultSet.getInt("id_archivo");
                    String tipoArchivo = resultSet.getString("tipo_archivo");
                    String publicKey = resultSet.getString("public_key");
                    String privateKey = resultSet.getString("private_key");
                    String nombreArchivo = resultSet.getString("nombre_archivo");
                    int tamanoArchivo = resultSet.getInt("tamano_archivo");

                    // Mostrar los datos por consola
                    System.out.println("ID Archivo: " + idArchivo);
                    System.out.println("Tipo Archivo: " + tipoArchivo);
                    System.out.println("Public Key: " + publicKey);
                    System.out.println("Private Key: " + privateKey);
                    System.out.println("Nombre Archivo: " + nombreArchivo);
                    System.out.println("Tamaño Archivo: " + tamanoArchivo);
                    System.out.println("--------------------------------------");
                }

                // Cerrar el ResultSet y el Statement
                resultSet.close();
                statement.close();

                // Cerrar la conexión
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
