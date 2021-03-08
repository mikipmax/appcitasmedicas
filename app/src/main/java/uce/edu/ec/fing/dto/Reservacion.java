package uce.edu.ec.fing.dto;

import java.time.LocalDate;
import java.time.LocalTime;
//Pojo Reservacion (Entidad central), se aplica patrón activeRecord
public class Reservacion {

	public int reseId;
	public LocalDate reseFecha;
	public LocalTime reseHora;
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
	
}
