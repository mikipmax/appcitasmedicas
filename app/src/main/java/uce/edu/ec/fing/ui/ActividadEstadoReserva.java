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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import uce.edu.ec.fing.R;

import uce.edu.ec.fing.dto.EstadoReserva;
import uce.edu.ec.fing.proxies.ProducerEstadoReservaProxy;
import uce.edu.ec.fing.utils.Util;


public class ActividadEstadoReserva extends Fragment {


    public static ActividadEstadoReserva newInstance() {
        ActividadEstadoReserva fragmento = new ActividadEstadoReserva();
        return fragmento;
    }

    public ActividadEstadoReserva() {

    }

    //Creacción de variables de instancia de clase


    EditText nombreEstadoReserva; //Caja de texto
    ListView lista; //componente android para mostrar una lista de cosas
    EstadoReserva estadoReservaSeleccionada; // objeto FormaPago que esta enlazado a bd


    Button crear;
    Button cancelar;


    //Se ejecuta cuando inicia la app
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_actividad_estado_reserva, container, false);

        nombreEstadoReserva = (EditText) view.findViewById(R.id.txtEstadoReserva);

        lista = (ListView) view.findViewById(R.id.listaEstadoReserva);

        crear = (Button) view.findViewById(R.id.id_btnCrearEstadoReserva);
        cancelar = (Button) view.findViewById(R.id.id_btnCancelarEstadoReserva);
        listar(); //llamamos método para listar formas de pago, apenas inicie la app

        //Este evento nos permite obtener un elemento de la lista que el usuario seleccione en la vista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                estadoReservaSeleccionada = (EstadoReserva) adapterView.getItemAtPosition(i); //guardamos el objeto seleccionado
                nombreEstadoReserva.setText(estadoReservaSeleccionada.esreEstado); // le enviamos a nuestra caja de texto lo que selecciono el usuario
                crear.setText(Util.SACTUALIZAR);
                cancelar.setVisibility(View.VISIBLE);
            }
        });
        crear.setOnClickListener(view1 -> {
            if (crear.getText().toString().equals(Util.SCREAR))
                insertar();
            if (crear.getText().toString().equals(Util.SACTUALIZAR)) {
                actualizar();

                cancelar.setVisibility(View.INVISIBLE);
                crear.setText(Util.SCREAR); //indicamos al usuario que ha vuelto al modo insertar
            }

        });

        cancelar.setOnClickListener(view2 -> {

            crear.setText(Util.SCREAR);
            cancelar.setVisibility(View.INVISIBLE);

            limpiarTexto();
        });

        return view;
    }


    //Método que lista los tipos de pago
    public void listar() {
        Call<List<EstadoReserva>> call = ProducerEstadoReservaProxy.producer().listar();
        Util.ejecutarLista(call, getActivity(), lista);
    }

    //para limpiar la caja de texto despues de cada actualización o inserción
    public void limpiarTexto() {
        nombreEstadoReserva.setText("");

    }

    //Método para insertar registros en la tabla
    public void insertar() {
        String estadoReserva = this.nombreEstadoReserva.getText().toString(); //obtenemos lo que el usuario digita en la caja de texto

        if (!estadoReserva.isEmpty()) { //verificamos que no este vacio la caja de texto
            Call<ResponseBody> call = ProducerEstadoReservaProxy.producer().insertar(new EstadoReserva(estadoReserva));
            Util.ejecutarCud(call, getActivity(), Util.SINSERTADO);
            limpiarTexto();
            refrescarFragmento();

        } else {
            Toast.makeText(getActivity(), "Texto vacio", Toast.LENGTH_LONG).show();
        }
    }

    //Método para actualizar registros en la tabla
    public void actualizar() {
        String txtEstadoReserva = this.nombreEstadoReserva.getText().toString(); //obtenemos lo que el usuario digite


        if (!txtEstadoReserva.isEmpty() && estadoReservaSeleccionada.esreId != 0) { //validamos que el texto no este vacio y que si se trata de una actualización en base al id que no sea nulo
            EstadoReserva estadoReserva = new EstadoReserva(estadoReservaSeleccionada.esreId, txtEstadoReserva); //almacenamos los cambios de la forma de pago con su respectivo id
            Call<ResponseBody> call = ProducerEstadoReservaProxy.producer().actualizar(estadoReserva);
            Util.ejecutarCud(call, getActivity(), Util.SACTUALIZADO);
            limpiarTexto();
            refrescarFragmento();

        } else {
            Toast.makeText(getActivity(), "Texto Vacio", Toast.LENGTH_LONG).show();
        }

    }

    public void refrescarFragmento() {
        getActivity().getSupportFragmentManager().
                beginTransaction().replace(R.id.content_main, newInstance()).
                commit();
    }
}
