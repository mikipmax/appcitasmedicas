package uce.edu.ec.fing.dto;


public class Paciente {

    public int paciId;
    public String paciNombre;
    public String paciApellido;
    public String paciTelefono;
    public String paciCedula;
    public String paciEmail;
    public String paciAntecedentes;

    public Paciente() {
    }

    public Paciente(int paciId, String paciNombre, String paciApellido, String paciTelefono, String paciCedula, String paciEmail, String paciAntecedentes) {
        this.paciId = paciId;
        this.paciNombre = paciNombre;
        this.paciApellido = paciApellido;
        this.paciTelefono = paciTelefono;
        this.paciCedula = paciCedula;
        this.paciEmail = paciEmail;
        this.paciAntecedentes = paciAntecedentes;
    }

    public Paciente(String paciNombre, String paciApellido, String paciTelefono, String paciCedula, String paciEmail, String paciAntecedentes) {
        this.paciNombre = paciNombre;
        this.paciApellido = paciApellido;
        this.paciTelefono = paciTelefono;
        this.paciCedula = paciCedula;
        this.paciEmail = paciEmail;
        this.paciAntecedentes = paciAntecedentes;
    }

    @Override
    public String toString() {
        return "Paciente: "+paciNombre + " " + paciApellido + " " + paciEmail + " " + paciAntecedentes;
    }
}
