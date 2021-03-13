package uce.edu.ec.fing.dto;


public class Especialidad {

    public int espeId;
    public String espeNombre;
    public double espePrecio;
    public boolean espeVigente;

    public Especialidad(int espeId, String espeNombre, double espePrecio, boolean espeVigente) {
        this.espeId = espeId;
        this.espeNombre = espeNombre;
        this.espePrecio = espePrecio;
        this.espeVigente = espeVigente;
    }

    public Especialidad(String espeNombre, double espePrecio, boolean espeVigente) {
        this.espeNombre = espeNombre;
        this.espePrecio = espePrecio;
        this.espeVigente = espeVigente;
    }

    public Especialidad() {

    }

    @Override
    public String toString() {
        return espeNombre + " - " + espePrecio;
    }
}
