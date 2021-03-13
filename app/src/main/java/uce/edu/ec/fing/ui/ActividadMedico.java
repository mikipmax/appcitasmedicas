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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import uce.edu.ec.fing.R;
import uce.edu.ec.fing.dto.Especialidad;
import uce.edu.ec.fing.dto.Medico;
import uce.edu.ec.fing.proxies.ProducerEspecialidadProxy;
import uce.edu.ec.fing.proxies.ProducerMedicoProxy;
import uce.edu.ec.fing.utils.Util;


public class ActividadMedico extends Fragment {

    public static ActividadMedico newInstance() {
        ActividadMedico fragmento = new ActividadMedico();
        return fragmento;
    }

    public ActividadMedico() {

    }

    //Creacción de variables de instancia de clase
    EditText nombresMedico; //Caja de texto
    EditText apellidosMedico;
    EditText telefonoMedico;
    EditText cedulaMedico;
    EditText emailMedico;
    Spinner especialidades;

    ListView lista; //componente android para mostrar una lista de cosas
    Medico medicoSeleccionado; // objeto FormaPago que esta enlazado a bd
    Especialidad especialidadSeleccionada;

    Button crear;
    Button cancelar;
    CheckBox estado;

    //Se ejecuta cuando inicia la app
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_actividad_medico, container, false);

        nombresMedico = (EditText) view.findViewById(R.id.txtNombresMedico);
        apellidosMedico = (EditText) view.findViewById(R.id.txtApellidosMedico);
        telefonoMedico = (EditText) view.findViewById(R.id.txtTelefonoMedicp);
        cedulaMedico = (EditText) view.findViewById(R.id.txtCedulaMedico);
        emailMedico = (EditText) view.findViewById(R.id.txtEmailMedico);

        lista = (ListView) view.findViewById(R.id.listaMedico);

        estado = (CheckBox) view.findViewById(R.id.id_checkMedico); //enlazamos al check del estado


        crear = (Button) view.findViewById(R.id.id_btnCrearMedico);
        cancelar = (Button) view.findViewById(R.id.id_btnCancelarMedico);
        agregarSpinner(view);
        listar(); //llamamos método para listar formas de pago, apenas inicie la app

        //Este evento nos permite obtener un elemento de la lista que el usuario seleccione en la vista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                medicoSeleccionado = (Medico) adapterView.getItemAtPosition(i); //guardamos el objeto seleccionado
                estado.setVisibility(View.VISIBLE); //hacemos visible el check
                estado.setChecked(medicoSeleccionado.mediVigente);
                nombresMedico.setText(medicoSeleccionado.mediNombre); // le enviamos a nuestra caja de texto lo que selecciono el usuario
                apellidosMedico.setText(medicoSeleccionado.mediApellido);
                telefonoMedico.setText(medicoSeleccionado.mediTelefono);
                cedulaMedico.setText(medicoSeleccionado.mediCedula);
                emailMedico.setText(medicoSeleccionado.mediEmail);
                especialidades.setSelection(obtenerPosicionItem(especialidades, medicoSeleccionado.especialidad.toString()));
                crear.setText(Util.SACTUALIZAR);
                cancelar.setVisibility(View.VISIBLE);
            }
        });
        crear.setOnClickListener(view1 -> {
            if (crear.getText().toString().equals(Util.SCREAR))
                insertar();
            if (crear.getText().toString().equals(Util.SACTUALIZAR)) {
                actualizar();
                estado.setVisibility(View.GONE); //perdemos el estado del check
                cancelar.setVisibility(View.GONE);
                crear.setText(Util.SCREAR); //indicamos al usuario que ha vuelto al modo insertar
            }

        });

        cancelar.setOnClickListener(view2 -> {

            crear.setText(Util.SCREAR);
            cancelar.setVisibility(View.GONE);
            estado.setVisibility(View.GONE); //perdemos el estado del check
            limpiarTexto();
        });

        return view;
    }


    public void agregarSpinner(View view) {
        especialidades = (Spinner) view.findViewById(R.id.spEspecialidad);


        //Implemento el setOnItemSelectedListener: para realizar acciones cuando se seleccionen los ítems
        especialidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                especialidadSeleccionada = (Especialidad) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Call<List<Especialidad>> call = ProducerEspecialidadProxy.producer().listarVigentes();
        Util.ejecutarSpinner(call, getActivity(), especialidades);


    }

    //Método que lista los tipos de pago
    public void listar() {
        Call<List<Medico>> call = ProducerMedicoProxy.producer().listar();
        Util.ejecutarLista(call, getActivity(), lista);
    }

    //para limpiar la caja de texto despues de cada actualización o inserción
    public void limpiarTexto() {
        nombresMedico.setText("");
        apellidosMedico.setText("");
        telefonoMedico.setText("");
        cedulaMedico.setText("");
        emailMedico.setText("");
    }


    //Método para insertar registros en la tabla
    public void insertar() {
        String nombreMedico = this.nombresMedico.getText().toString(); //obtenemos lo que el usuario digita en la caja de texto
        String apellidoMedico = this.apellidosMedico.getText().toString();
        String telefonoMedico = this.telefonoMedico.getText().toString();
        String cedulaMedico = this.cedulaMedico.getText().toString();
        String emailMedico = this.emailMedico.getText().toString();

        if (!nombreMedico.isEmpty()) { //verificamos que no este vacio la caja de texto
            Call<ResponseBody> call = ProducerMedicoProxy.producer().insertar(
                    new Medico(true, nombreMedico, apellidoMedico, telefonoMedico, emailMedico, cedulaMedico, especialidadSeleccionada));
            Util.ejecutarCud(call, getActivity(), Util.SINSERTADO);
            limpiarTexto();
            refrescarFragmento();

        } else {
            Toast.makeText(getActivity(), "Texto vacio", Toast.LENGTH_LONG).show();
        }
    }

    //Método para actualizar registros en la tabla
    public void actualizar() {
        String nombreMedico = this.nombresMedico.getText().toString(); //obtenemos lo que el usuario digita en la caja de texto
        String apellidoMedico = this.apellidosMedico.getText().toString();
        String telefonoMedico = this.telefonoMedico.getText().toString();
        String cedulaMedico = this.cedulaMedico.getText().toString();
        String emailMedico = this.emailMedico.getText().toString();


        boolean estadoAux = estado.isChecked();

        if (!nombreMedico.isEmpty() && medicoSeleccionado.mediId != 0) { //validamos que el texto no este vacio y que si se trata de una actualización en base al id que no sea nulo
            Medico medico =
                    new Medico(medicoSeleccionado.mediId, estadoAux,
                            nombreMedico, apellidoMedico, telefonoMedico, cedulaMedico, emailMedico, especialidadSeleccionada); //almacenamos los cambios de la forma de pago con su respectivo id
            Call<ResponseBody> call = ProducerMedicoProxy.producer().actualizar(medico);
            Util.ejecutarCud(call, getActivity(), Util.SACTUALIZADO);
            limpiarTexto();
            refrescarFragmento();

        } else {
            Toast.makeText(getActivity(), "Texto Vacio", Toast.LENGTH_LONG).show();
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
