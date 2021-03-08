package uce.edu.ec.fing.dto;


public class TipoPago {

    public int tipaId;
    public String tipaNombre;
    public boolean tipaVigente;

    public TipoPago(String tipoPago, boolean b) {
        this.tipaNombre = tipoPago;
        this.tipaVigente = b;
    }

    public TipoPago() {

    }

    @Override
    public String toString() {
        return tipaNombre;
    }

}
