import banking.DatabaseManagerCuentasBancarias;
import banking.SeguridadCuentasDeBanco;
import password.DatabaseManagerContrasenas;
import reporting.DatabaseManagerReportes;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de gestión de proyectos.");

        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Mostrar opciones al usuario y leer su elección
            System.out.println("Seleccione una opción:");
            System.out.println("1. Mostrar contraseñas por proyecto");
            System.out.println("2. Guardar contraseñas vulnerables");
            System.out.println("3. Mostrar cuentas bancarias");
            System.out.println("4. Mostrar cuentas bancarias encriptadas");
            System.out.println("5. Guardar cuentas bancarias vulnerables");
            System.out.println("6. Guardar reporte");
            System.out.println("0. Salir");
            System.out.print("Ingrese el número de la opción: ");
            int opcion = scanner.nextInt();

            // Manejar la opción del usuario utilizando un switch
            switch (opcion) {
                case 1:
                    mostrarContraseñasPorProyecto();
                    break;
                case 2:
                    guardarContrasenasVulnerables();
                    break;
                case 3:
                    mostrarCuentasBancarias();
                    break;
                case 4:
                    mostrarCuentasBancariasEncriptadas();
                    break;
                case 5:
                    guardarVulnerabilidadCuentaBancaria();
                    break;
                case 6:
                    guardarReporte();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }

    // Método para mostrar las contraseñas por proyecto
    private static void mostrarContraseñasPorProyecto() {
        // Obtener contraseñas y recomendaciones por proyecto
        Map<String, List<String>> contraseñasPorProyecto = DatabaseManagerContrasenas.obtenerContrasenasPorProyecto();

        // Iterar sobre el mapa e imprimir las recomendaciones
        for (Map.Entry<String, List<String>> entry : contraseñasPorProyecto.entrySet()) {
            String idProyecto = entry.getKey();
            List<String> mensajes = entry.getValue();
            // Iterar sobre la lista de mensajes para imprimir cada uno
            for (String mensaje : mensajes) {
                System.out.println(idProyecto + ": " + mensaje);
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    // Método para guardar contraseñas vulnerables
    public static void guardarContrasenasVulnerables() {
        DatabaseManagerContrasenas.guardarRecomendacionesEnVulnerabilidad();
    }

    // Método para mostrar las cuentas bancarias
    private static void mostrarCuentasBancarias() {
        DatabaseManagerCuentasBancarias.mostrarCuentasBancarias();
    }

    // Método para mostrar solo las cuentas bancarias encriptadas
    private static void mostrarCuentasBancariasEncriptadas() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DBVulnerabilidades", "postgres", "Daniel1128")) {
            String query = "SELECT * FROM cuenta_bancaria";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String numeroCuenta = resultSet.getString("numero_cuenta");
                    if (SeguridadCuentasDeBanco.isBase64Encoded(numeroCuenta)) {
                        SeguridadCuentasDeBanco.imprimirDatosCuentaBancaria(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar la vulnerabilidad de la cuenta bancaria
    public static void guardarVulnerabilidadCuentaBancaria() {
        SeguridadCuentasDeBanco.verificarYGuardarVulnerabilidades();
    }

    // Método para guardar el reporte
    public static void guardarReporte() {
        DatabaseManagerReportes.guardarReporte();
    }
}
