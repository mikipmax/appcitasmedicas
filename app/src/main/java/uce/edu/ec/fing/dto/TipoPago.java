package uce.edu.ec.fing.dto;


public class TipoPago {

    public int tipaId;
    public String tipaNombre;
    public boolean tipaVigente;

    public TipoPago(String tipoPago, boolean b) {
        this.tipaNombre = tipoPago;
        this.tipaVigente = b;
    }

    public TipoPago(int tipaId, String tipaNombre, boolean tipaVigente) {
        this.tipaId = tipaId;
        this.tipaNombre = tipaNombre;
        this.tipaVigente = tipaVigente;
    }

    public TipoPago() {

    }

    @Override
    public String toString() {
        return tipaNombre;
    }

}
