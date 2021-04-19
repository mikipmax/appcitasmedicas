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
import java.time.LocalDate;
import java.time.LocalTime;

//Pojo Reservacion (Entidad central), se aplica patrón activeRecord
public class Reservacion {

    public int reseId;
    public String reseFecha;
    public String reseHora;
    public String reseDescripcion;
    public int mediId;
    public int paciId;
    public int esreId;
    public int tipaId;
    //Para visualizar información de tablas hijas
    public TipoPago tipoPago;
    public Paciente paciente;
    public Medico medico;
    public EstadoReserva estadoReserva;

    public Reservacion() {
    }

    public Reservacion(int reseId, String reseFecha, String reseHora, String reseDescripcion, int mediId, int paciId, int esreId, int tipaId) {
        this.reseId = reseId;
        this.reseFecha = reseFecha;
        this.reseHora = reseHora;
        this.reseDescripcion = reseDescripcion;
        this.mediId = mediId;
        this.paciId = paciId;
        this.esreId = esreId;
        this.tipaId = tipaId;
    }

    public Reservacion(String reseFecha, String reseHora, String reseDescripcion, int mediId, int paciId, int esreId, int tipaId) {

        this.reseFecha = reseFecha;
        this.reseHora = reseHora;
        this.reseDescripcion = reseDescripcion;
        this.mediId = mediId;
        this.paciId = paciId;
        this.esreId = esreId;
        this.tipaId = tipaId;
    }

    @Override
    public String toString() {
        return reseFecha+" a las "+reseHora+" para: "+paciente+ " con "+medico+" y pagará con: "+tipoPago;
    }
}

