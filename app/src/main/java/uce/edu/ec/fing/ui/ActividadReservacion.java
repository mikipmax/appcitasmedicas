package uce.edu.ec.fing.ui;
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
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;



import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import okhttp3.ResponseBody;
import retrofit2.Call;
import uce.edu.ec.fing.R;

import uce.edu.ec.fing.dto.EstadoReserva;
import uce.edu.ec.fing.dto.Medico;
import uce.edu.ec.fing.dto.Paciente;
import uce.edu.ec.fing.dto.Reservacion;
import uce.edu.ec.fing.dto.TipoPago;
import uce.edu.ec.fing.proxies.ProducerEstadoReservaProxy;
import uce.edu.ec.fing.proxies.ProducerMedicoProxy;
import uce.edu.ec.fing.proxies.ProducerPacienteProxy;
import uce.edu.ec.fing.proxies.ProducerReservacionProxy;
import uce.edu.ec.fing.proxies.ProducerTipoPagoProxy;
import uce.edu.ec.fing.utils.Util;


public class ActividadReservacion extends Fragment {

    public static ActividadReservacion newInstance() {
        ActividadReservacion fragmento = new ActividadReservacion();
        return fragmento;
    }

    public ActividadReservacion() {

    }

    //Creacción de variables de instancia de clase
    MaskedEditText fechaReservacion; //Caja de texto
    MaskedEditText horaReservacion;
    EditText descripcion;

    Spinner medicos;
    Spinner estadosReservacion;
    Spinner tiposPago;
    Spinner pacientes;

    ListView lista; //componente android para mostrar una lista de cosas
    Reservacion reservacionSeleccionada; // objeto FormaPago que esta enlazado a bd
    Medico medicoSeleccionado;
    EstadoReserva estadoReservaSeleccionado;
    TipoPago tipoPagoSeleccionado;
    Paciente pacienteSeleccionado;

    Button crear;
    Button cancelar;
    Button eliminar;
    //Se ejecuta cuando inicia la app
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_actividad_reservacion, container, false);

        fechaReservacion = (MaskedEditText) view.findViewById(R.id.txtFecha);
        horaReservacion = (MaskedEditText) view.findViewById(R.id.txtHora);
        descripcion = (EditText) view.findViewById(R.id.txtDescripcion);

        lista = (ListView) view.findViewById(R.id.listaReservacion);

        crear = (Button) view.findViewById(R.id.id_btnCrearReservacion);
        eliminar = (Button) view.findViewById(R.id.id_btnEliminarReservacion);
        cancelar = (Button) view.findViewById(R.id.id_btnCancelarReservacion);
        agregarSpinner(view);
        listar(); //llamamos método para listar formas de pago, apenas inicie la app

        //Este evento nos permite obtener un elemento de la lista que el usuario seleccione en la vista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                reservacionSeleccionada = (Reservacion) adapterView.getItemAtPosition(i); //guardamos el objeto seleccionado

                fechaReservacion.setText(String.valueOf(reservacionSeleccionada.reseFecha)); // le enviamos a nuestra caja de texto lo que selecciono el usuario
                horaReservacion.setText(String.valueOf(reservacionSeleccionada.reseHora));
                descripcion.setText(reservacionSeleccionada.reseDescripcion);

                medicos.setSelection(obtenerPosicionItem(medicos, reservacionSeleccionada.medico.toString()));
                estadosReservacion.setSelection(obtenerPosicionItem(estadosReservacion, reservacionSeleccionada.estadoReserva.toString()));
                tiposPago.setSelection(obtenerPosicionItem(tiposPago, reservacionSeleccionada.tipoPago.toString()));
                pacientes.setSelection(obtenerPosicionItem(pacientes, reservacionSeleccionada.paciente.toString()));

                crear.setText(Util.SACTUALIZAR);
                eliminar.setVisibility(View.VISIBLE);
                cancelar.setVisibility(View.VISIBLE);
            }
        });
        crear.setOnClickListener(view1 -> {

            if (crear.getText().toString().equals(Util.SCREAR))
                insertar();
            if (crear.getText().toString().equals(Util.SACTUALIZAR)) {
                actualizar();
                cancelar.setVisibility(View.GONE);
                eliminar.setVisibility(View.GONE);
                crear.setText(Util.SCREAR); //indicamos al usuario que ha vuelto al modo insertar
            }

        });

        cancelar.setOnClickListener(view2 -> {

            crear.setText(Util.SCREAR);
            eliminar.setVisibility(view.GONE);
            cancelar.setVisibility(View.GONE);
            limpiarTexto();
        });

        eliminar.setOnClickListener(view3->{
            eliminar();
            crear.setText(Util.SCREAR);
            eliminar.setVisibility(view.GONE);
            cancelar.setVisibility(View.GONE);
            limpiarTexto();
        });

        return view;
    }


    public void agregarSpinner(View view) {
        medicos = (Spinner) view.findViewById(R.id.spMedico);
        estadosReservacion = (Spinner) view.findViewById(R.id.spEstadoReservacion);
        tiposPago = (Spinner) view.findViewById(R.id.spTipoPago);
        pacientes = (Spinner) view.findViewById(R.id.spPaciente);

        //Implemento el setOnItemSelectedListener: para realizar acciones cuando se seleccionen los ítems
        medicos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                medicoSeleccionado = (Medico) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        estadosReservacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                estadoReservaSeleccionado = (EstadoReserva) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        tiposPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipoPagoSeleccionado = (TipoPago) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        pacientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pacienteSeleccionado = (Paciente) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        Call<List<Medico>> callMedico = ProducerMedicoProxy.producer().listarVigentes();
        Util.ejecutarSpinner(callMedico, getActivity(), medicos);

        Call<List<EstadoReserva>> callEstadoReserva = ProducerEstadoReservaProxy.producer().listar();
        Util.ejecutarSpinner(callEstadoReserva, getActivity(), estadosReservacion);

        Call<List<TipoPago>> callTipoPago = ProducerTipoPagoProxy.producer().listarVigentes();
        Util.ejecutarSpinner(callTipoPago, getActivity(), tiposPago);

        Call<List<Paciente>> callPaciente = ProducerPacienteProxy.producer().listar();
        Util.ejecutarSpinner(callPaciente, getActivity(), pacientes);


    }

    //Método que lista los tipos de pago
    public void listar() {
        Call<List<Reservacion>> call = ProducerReservacionProxy.producer().listar();
        Util.ejecutarLista(call, getActivity(), lista);
    }

    //para limpiar la caja de texto despues de cada actualización o inserción
    public void limpiarTexto() {
        fechaReservacion.setText("");
        horaReservacion.setText("");
        descripcion.setText("");
    }


    //Método para insertar registros en la tabla

    public void insertar() {
        String fechaReservacion = this.fechaReservacion.getText().toString();
        String horaReservacion = this.horaReservacion.getText().toString();
        String descripcion = this.descripcion.getText().toString();

        if (!fechaReservacion.isEmpty() && !horaReservacion.isEmpty()) { //verificamos que no este vacio la caja de texto
            Call<ResponseBody> call = ProducerReservacionProxy.producer().insertar(
                    new Reservacion(fechaReservacion, horaReservacion, descripcion,
                            medicoSeleccionado.mediId, pacienteSeleccionado.paciId,
                            estadoReservaSeleccionado.esreId, tipoPagoSeleccionado.tipaId));
            Util.ejecutarCud(call, getActivity(), Util.SINSERTADO);
            limpiarTexto();
            refrescarFragmento();

        } else {
            Toast.makeText(getActivity(), "Texto vacio", Toast.LENGTH_LONG).show();
        }
    }

    //Método para actualizar registros en la tabla
    public void actualizar() {
        String fechaReservacion = this.fechaReservacion.getText().toString();
        String horaReservacion = this.horaReservacion.getText().toString();
        String descripcion = this.descripcion.getText().toString();

        if (!fechaReservacion.isEmpty() && !horaReservacion.isEmpty() && reservacionSeleccionada.reseId != 0) { //validamos que el texto no este vacio y que si se trata de una actualización en base al id que no sea nulo
            Reservacion reservacion =
                    new Reservacion(reservacionSeleccionada.reseId, fechaReservacion, horaReservacion, descripcion,
                            medicoSeleccionado.mediId, pacienteSeleccionado.paciId,
                            estadoReservaSeleccionado.esreId, tipoPagoSeleccionado.tipaId);
            Call<ResponseBody> call = ProducerReservacionProxy.producer().actualizar(reservacion);
            Util.ejecutarCud(call, getActivity(), Util.SACTUALIZADO);
            limpiarTexto();
            refrescarFragmento();

        } else {
            Toast.makeText(getActivity(), "Texto Vacio", Toast.LENGTH_LONG).show();
        }

    }

    public void eliminar(){
        if (reservacionSeleccionada.reseId!=0){
            Call<ResponseBody> call = ProducerReservacionProxy.producer().eliminar(reservacionSeleccionada.reseId);
            Util.ejecutarCud(call, getActivity(), Util.SELIMINADO);
            limpiarTexto();
            refrescarFragmento();
        }
    }

    public static int obtenerPosicionItem(Spinner spinner, String valor) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String valor`

        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(valor)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }


    public void refrescarFragmento() {
        getActivity().getSupportFragmentManager().
                beginTransaction().replace(R.id.content_main, newInstance()).
                commit();
    }
}
