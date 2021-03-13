package uce.edu.ec.fing.ui;

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
import uce.edu.ec.fing.dto.Especialidad;

import uce.edu.ec.fing.proxies.ProducerEspecialidadProxy;

import uce.edu.ec.fing.utils.Util;


public class ActividadEspecialidad extends Fragment {


    public static ActividadEspecialidad newInstance() {
        ActividadEspecialidad fragmento = new ActividadEspecialidad();
        return fragmento;
    }

    public ActividadEspecialidad() {

    }

    //Creacción de variables de instancia de clase


    EditText nombreEspecialidad; //Caja de texto
    ListView lista; //componente android para mostrar una lista de cosas
    Especialidad especialidadSeleccionada; // objeto FormaPago que esta enlazado a bd
    EditText precioEspecialidad;

    Button crear;
    Button cancelar;
    CheckBox estado;

    //Se ejecuta cuando inicia la app
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_actividad_especialidad, container, false);

        nombreEspecialidad = (EditText) view.findViewById(R.id.txtEspecialidad);
        precioEspecialidad = (EditText) view.findViewById(R.id.txtPrecio);
        lista = (ListView) view.findViewById(R.id.listaEspecialidad);

        estado = (CheckBox) view.findViewById(R.id.id_checkEspecialidad); //enlazamos al check del estado


        crear = (Button) view.findViewById(R.id.id_btnCrearEspecialidad);
        cancelar = (Button) view.findViewById(R.id.id_btnCancelarEspecialidad);
        listar(); //llamamos método para listar formas de pago, apenas inicie la app

        //Este evento nos permite obtener un elemento de la lista que el usuario seleccione en la vista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                especialidadSeleccionada = (Especialidad) adapterView.getItemAtPosition(i); //guardamos el objeto seleccionado
                estado.setVisibility(View.VISIBLE); //hacemos visible el check
                estado.setChecked(especialidadSeleccionada.espeVigente);
                nombreEspecialidad.setText(especialidadSeleccionada.espeNombre); // le enviamos a nuestra caja de texto lo que selecciono el usuario
                precioEspecialidad.setText(String.valueOf(especialidadSeleccionada.espePrecio));
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
        Call<List<Especialidad>> call = ProducerEspecialidadProxy.producer().listar();
        Util.ejecutarLista(call, getActivity(), lista);
    }

    //para limpiar la caja de texto despues de cada actualización o inserción
    public void limpiarTexto() {
        nombreEspecialidad.setText("");
        precioEspecialidad.setText("");
    }

    //Método para insertar registros en la tabla
    public void insertar() {
        String especialidad = this.nombreEspecialidad.getText().toString(); //obtenemos lo que el usuario digita en la caja de texto
        double precio = Double.valueOf(this.precioEspecialidad.getText().toString());
        if (!especialidad.isEmpty()) { //verificamos que no este vacio la caja de texto
            Call<ResponseBody> call = ProducerEspecialidadProxy.producer().insertar(new Especialidad(especialidad, precio, true));
            Util.ejecutarCud(call, getActivity(), Util.SINSERTADO);
            limpiarTexto();
            refrescarFragmento();

        } else {
            Toast.makeText(getActivity(), "Texto vacio", Toast.LENGTH_LONG).show();
        }
    }

    //Método para actualizar registros en la tabla
    public void actualizar() {
        String txtEspecialidad = this.nombreEspecialidad.getText().toString(); //obtenemos lo que el usuario digite
        double txtPrecio = Double.valueOf(this.precioEspecialidad.getText().toString());
        boolean estadoAux = estado.isChecked();

        if (!txtEspecialidad.isEmpty() && especialidadSeleccionada.espeId != 0) { //validamos que el texto no este vacio y que si se trata de una actualización en base al id que no sea nulo
            Especialidad especialidad = new Especialidad(especialidadSeleccionada.espeId, txtEspecialidad, txtPrecio, estadoAux); //almacenamos los cambios de la forma de pago con su respectivo id
            Call<ResponseBody> call = ProducerEspecialidadProxy.producer().actualizar(especialidad);
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
