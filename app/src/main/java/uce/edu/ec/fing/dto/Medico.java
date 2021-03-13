package uce.edu.ec.fing.dto;

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
