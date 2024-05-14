package password;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalizadorDeContraseñas {

    Connection conectar = null;
    String user = "postgres";
    String contraseña = "1234";
    String db = "libros";
    String ip = "localhost";
    String puerto = "5432";
    String resultado;
    List<String> contraseñasRecuperadas = new ArrayList<>();

    String cadena = "jdbc:postgresql://" + ip + ":" + puerto + "/" + db;

    public void consultarContraseña() {
        try {
            conectar = DriverManager.getConnection(cadena, user, contraseña);

            Statement statement = conectar.createStatement();

            String query = "SELECT * FROM contrasena_guardada";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                resultado = resultSet.getString("contrasena");
                contraseñasRecuperadas.add(resultado);
                System.out.println(resultado);
            }
            resultSet.close();
            statement.close();

            conectar.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    
    public boolean contraseñaFuerte(List<String> password) {
        for (String contraseña : password) {
            if (contraseña.length() >= 8 &&
                contieneCaracteresEspeciales(contraseña) &&
                contieneDigitos(contraseña) &&
                contieneMayusculas(contraseña)) {
                return true; // Retorna true al encontrar la primera contraseña fuerte
            }
        }
        return false;
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

    public void SugerenciaDeMejoraDeContraseña() {
        if (contraseñaFuerte(contraseñasRecuperadas)) {
            System.out.println("La contraseña es correcta");
            
        }else{
            System.out.println("La Contraseña es incorrecta");
        }
    }

    public boolean contraseñaComun(String password) {
        return false;
    }
}
