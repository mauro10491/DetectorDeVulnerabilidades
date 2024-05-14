package ConnectioDB;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CConexion {

    Connection conectar = null;
    String user = "postgres";
    String contraseña = "1234";
    String db = "libros";
    String ip = "localhost";
    String puerto = "5432";

    String cadena = "jdbc:postgresql://" + ip + ":" + puerto + "/" + db;

    public Connection establecerConexion() {
        try {
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(cadena, user, contraseña);
            System.out.println("Conexion correcta");
        } catch (Exception e) {
            System.out.println(e);
        }
        return conectar;
    }

    public void consulta() {
        try {
            conectar = DriverManager.getConnection(cadena, user, contraseña);

            Statement statement = conectar.createStatement();

            String query = "SELECT * FROM proyecto";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String resultado = resultSet.getString("id_proyecto");
                System.out.println(resultado);
            }
            resultSet.close();
            statement.close();

            conectar.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
