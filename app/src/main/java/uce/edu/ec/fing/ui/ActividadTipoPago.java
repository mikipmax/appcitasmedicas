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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

import uce.edu.ec.fing.R;
import uce.edu.ec.fing.dto.TipoPago;
import uce.edu.ec.fing.proxies.ProducerTipoPagoProxy;
import uce.edu.ec.fing.utils.Util;


public class ActividadTipoPago extends Fragment {


    public static ActividadTipoPago newInstance() {
        ActividadTipoPago fragmento = new ActividadTipoPago();
        return fragmento;
    }

    public ActividadTipoPago() {

    }

    //Creacción de variables de instancia de clase


    EditText tipoPago; //Caja de texto
    ListView listaTipoPago; //componente android para mostrar una lista de cosas
    TipoPago tipoPagoSeleccionado; // objeto FormaPago que esta enlazado a bd


    Button crear;
    Button cancelar;
    CheckBox estado;

    //Se ejecuta cuando inicia la app
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_actividad_tipo_pago, container, false);

        tipoPago = (EditText) view.findViewById(R.id.txtTipoPago);
        listaTipoPago = (ListView) view.findViewById(R.id.listaTipoPago);

        estado = (CheckBox) view.findViewById(R.id.id_checkTipoPago); //enlazamos al check del estado


        crear = (Button) view.findViewById(R.id.id_btnCrearTipo);
        cancelar = (Button) view.findViewById(R.id.id_btnCancelar);
        listar(); //llamamos método para listar formas de pago, apenas inicie la app

        //Este evento nos permite obtener un elemento de la lista que el usuario seleccione en la vista
        listaTipoPago.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                tipoPagoSeleccionado = (TipoPago) adapterView.getItemAtPosition(i); //guardamos el objeto seleccionado
                estado.setVisibility(View.VISIBLE); //hacemos visible el check
                estado.setChecked(tipoPagoSeleccionado.tipaVigente);
                tipoPago.setText(tipoPagoSeleccionado.tipaNombre); // le enviamos a nuestra caja de texto lo que selecciono el usuario
                crear.setText(Util.SACTUALIZAR);
                cancelar.setVisibility(View.VISIBLE);
            }
        });
        crear.setOnClickListener(view1 -> {
            if (crear.getText().toString().equals(Util.SCREAR))
                insertar();
            if (crear.getText().toString().equals(Util.SACTUALIZAR)) {
                actualizar();
                estado.setVisibility(View.INVISIBLE); //perdemos el estado del check
                cancelar.setVisibility(View.INVISIBLE);
                crear.setText(Util.SCREAR); //indicamos al usuario que ha vuelto al modo insertar
            }

        });

        cancelar.setOnClickListener(view2 -> {

            crear.setText(Util.SCREAR);
            cancelar.setVisibility(View.INVISIBLE);
            estado.setVisibility(View.INVISIBLE); //perdemos el estado del check
            limpiarTexto();
        });

        return view;
    }


    //Método que lista los tipos de pago
    public void listar() {
        Call<List<TipoPago>> call = ProducerTipoPagoProxy.producer().listarTiposPago();
        Util.ejecutarLista(call, getActivity(), listaTipoPago);
    }

    //para limpiar la caja de texto despues de cada actualización o inserción
    public void limpiarTexto() {
        tipoPago.setText("");
    }

    //Método para insertar registros en la tabla
    public void insertar() {
        String tipoPago = this.tipoPago.getText().toString(); //obtenemos lo que el usuario digita en la caja de texto
        if (!tipoPago.isEmpty()) { //verificamos que no este vacio la caja de texto
            Call<ResponseBody> call = ProducerTipoPagoProxy.producer().insertar(new TipoPago(tipoPago, true));
            Util.ejecutarCud(call, getActivity(), Util.SINSERTADO);
            limpiarTexto();
            refrescarFragmento();

        } else {
            Toast.makeText(getActivity(), "Texto vacio", Toast.LENGTH_LONG).show();
        }
    }

    //Método para actualizar registros en la tabla
    public void actualizar() {
        String txtTipoPago = this.tipoPago.getText().toString(); //obtenemos lo que el usuario digite
        boolean estadoAux = estado.isChecked();

        if (!txtTipoPago.isEmpty() && tipoPagoSeleccionado.tipaId != 0) { //validamos que el texto no este vacio y que si se trata de una actualización en base al id que no sea nulo
            TipoPago tipoPago = new TipoPago(tipoPagoSeleccionado.tipaId, txtTipoPago, estadoAux); //almacenamos los cambios de la forma de pago con su respectivo id
            Call<ResponseBody> call = ProducerTipoPagoProxy.producer().actualizar(tipoPago);
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
