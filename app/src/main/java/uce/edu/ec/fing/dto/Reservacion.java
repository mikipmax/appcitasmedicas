package uce.edu.ec.fing.dto;

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

