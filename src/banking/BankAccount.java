package banking;

public class BankAccount {
    private int idCuenta;
    private int idProyecto;
    private String numeroCuenta;
    private String entidadBancaria;
    private String ultimaActividad;
    private boolean autenticacionMultifactor;

    // Constructor
    public BankAccount(int idCuenta, int idProyecto, String numeroCuenta, String entidadBancaria, String ultimaActividad, boolean autenticacionMultifactor) {
        this.idCuenta = idCuenta;
        this.idProyecto = idProyecto;
        this.numeroCuenta = numeroCuenta;
        this.entidadBancaria = entidadBancaria;
        this.ultimaActividad = ultimaActividad;
        this.autenticacionMultifactor = autenticacionMultifactor;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getEntidadBancaria() {
        return entidadBancaria;
    }

    public void setEntidadBancaria(String entidadBancaria) {
        this.entidadBancaria = entidadBancaria;
    }

    public String getUltimaActividad() {
        return ultimaActividad;
    }

    public void setUltimaActividad(String ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    public boolean isAutenticacionMultifactor() {
        return autenticacionMultifactor;
    }

    public void setAutenticacionMultifactor(boolean autenticacionMultifactor) {
        this.autenticacionMultifactor = autenticacionMultifactor;
    }
}
