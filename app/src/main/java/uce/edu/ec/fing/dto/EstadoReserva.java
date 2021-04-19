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
public class EstadoReserva {

    public int esreId;
    public String esreEstado;

    public EstadoReserva(int esreId, String esreEstado) {
        this.esreId = esreId;
        this.esreEstado = esreEstado;
    }

    public EstadoReserva(String esreEstado) {
        this.esreEstado = esreEstado;
    }

    public EstadoReserva() {
    }

    @Override
    public String toString() {
        return esreEstado;
    }
}
