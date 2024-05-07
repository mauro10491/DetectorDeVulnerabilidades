package password;

import java.util.Arrays;
import java.util.List;

public class AnalizadorDeContraseñas {
    
    private List<String> ContraseñasComunes = Arrays.asList("123456", "password", "12345678");
    
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
     
    public String SugerenciaDeMejoraDeContraseña(String password) {
        if (!contraseñaFuerte(password)) {
            return "Consider using a mix of uppercase, lowercase, numbers, and special characters.";
        }
        return "Your password is strong.";
    }
     
    public boolean contraseñaComun(String password) {
        return ContraseñasComunes.contains(password);
    }
}
