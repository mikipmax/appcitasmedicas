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
//Pojo Médico, usando patrón activeRecord
public class Medico {

    public int mediId;
    public boolean mediVigente;
    public String mediNombre;
    public String mediApellido;
    public String mediTelefono;
    public String mediEmail;
    public String mediCedula;
    public Especialidad especialidad;

    public Medico(int mediId, boolean mediVigente, String mediNombre, String mediApellido, String mediTelefono, String mediEmail, String mediCedula, Especialidad especialidad) {
        this.mediId = mediId;
        this.mediVigente = mediVigente;
        this.mediNombre = mediNombre;
        this.mediApellido = mediApellido;
        this.mediTelefono = mediTelefono;
        this.mediEmail = mediEmail;
        this.mediCedula = mediCedula;
        this.especialidad = especialidad;
    }

    public Medico(boolean mediVigente, String mediNombre, String mediApellido, String mediTelefono, String mediEmail, String mediCedula, Especialidad especialidad) {
        this.mediVigente = mediVigente;
        this.mediNombre = mediNombre;
        this.mediApellido = mediApellido;
        this.mediTelefono = mediTelefono;
        this.mediEmail = mediEmail;
        this.mediCedula = mediCedula;
        this.especialidad = especialidad;
    }

    public Medico() {
    }

    @Override
    public String toString() {
        return "Médico: "+mediNombre + " " + mediApellido + " " +
                mediTelefono + " Especialidad: "+especialidad;
    }
}
