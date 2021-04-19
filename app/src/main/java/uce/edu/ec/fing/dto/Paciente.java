package uce.edu.ec.fing.dto;
/*
***UNIVERSIDAD CENTRAL DEL ECUADOR***
***FACULTAD DE INGENIERIA Y CIENCIAS APLICADAS***
***CARRERAS: INGENIERÍA EN COMPUTACIÓN GRÁFICA​E INGENIERÍA INFORMÁTICA***
***MATERIA: DISPOSITIVOS MÓVILES***

Grupo N: 5
Integrantes:  -Nicolalde Estefanía ​Correo: jenicolaldep@uce.edu.ec
              -Ponce Michael​​Correo: mfponce@uce.edu.ec
              -Sánchez Jonathan​​Correo: jjsanchezl1@uce.edu.ec
              -Tituaña Mayra​Correo: mrtituana@uce.edu.ec
Descripción: La aplicación DATAMED se manifiesta de forma similar a una agenda, y tiene el propósito
*            de agilizar el proceso de consulta. Consume microservicios alojados en un servidor de
*            cloud gratuito, permite a los administrativos registrar las citas médicas de
*            los pacientes brindando la facilidad de gestionar parámetros como: la fecha,
*            especialista, área y tipo de pago.
*/

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
