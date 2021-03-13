package uce.edu.ec.fing.dto;


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
