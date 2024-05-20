import banking.DatabaseManagerCuentasBancarias;
import banking.SeguridadCuentasDeBanco;
import password.DatabaseManagerContrasenas;
import reporting.DatabaseManagerReportes;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de gestión de proyectos.");

        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Mostrar opciones al usuario y leer su elección
        System.out.println("Seleccione una opción:");
        System.out.println("1. Mostrar contraseñas por proyecto");
        System.out.println("2. Mostrar cuentas bancarias");
        System.out.println("3. Mostrar cuentas bancarias encriptadas");
        System.out.println("4. Guardar reporte");
        System.out.println("0. Salir");
        System.out.print("Ingrese el número de la opción: ");
        int opcion = scanner.nextInt();

        // Manejar la opción del usuario utilizando un switch
        switch (opcion) {
            case 1:
                mostrarContraseñasPorProyecto();
                break;
            case 2:
                mostrarCuentasBancarias();
                guardarVulnerabilidadCuentaBancaria();
                break;
            case 3:
                cuentasBancariasEncriptadas();
                break;
            case 4:
                guardarReporte();
                break;
            case 0:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
        }

        // Cerrar el Scanner
        scanner.close();
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
                System.out.println("Contraseña para el proyecto " + idProyecto + ": " + mensaje);
            }
        }
        // Llamar al método para guardar las recomendaciones de contraseñas en la tabla vulnerabilidad
        DatabaseManagerContrasenas.guardarRecomendacionesEnVulnerabilidad();
    }


    // Método para mostrar las cuentas bancarias
    private static void mostrarCuentasBancarias() {
        // Llamar al método para mostrar las cuentas bancarias
        DatabaseManagerCuentasBancarias.mostrarCuentasBancarias();
    }

    private static void cuentasBancariasEncriptadas(){
        SeguridadCuentasDeBanco.mostrarCuentasBancarias();
    }

    public static void guardarReporte() {
        // Llamar al método para guardar el reporte
        DatabaseManagerReportes.guardarReporte();
    }

    public static void guardarVulnerabilidadCuentaBancaria(){
        SeguridadCuentasDeBanco.mostrarCuentasBancarias();
    }

}
