package ConnectioDB;

import password.AnalizadorDeContraseñas;

public class main {
    
    public static void main(String[] args) {
        AnalizadorDeContraseñas analizador = new AnalizadorDeContraseñas();
        CConexion objConexion = new CConexion();
        //objConexion.establecerConexion();
        //objConexion.consulta();
        analizador.consultarContraseña();
        analizador.SugerenciaDeMejoraDeContraseña();
        
    }
}
