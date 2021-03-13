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

import uce.edu.ec.fing.dto.Paciente;
import uce.edu.ec.fing.proxies.ProducerPacienteProxy;
import uce.edu.ec.fing.utils.Util;


public class ActividadPaciente extends Fragment {


    public static ActividadPaciente newInstance() {
        ActividadPaciente fragmento = new ActividadPaciente();
        return fragmento;
    }

    public ActividadPaciente() {

    }

    //Creacción de variables de instancia de clase


    EditText nombresPaciente; //Caja de texto
    EditText apellidosPaciente; //Caja de texto
    EditText telefono; //Caja de texto
    EditText cedula; //Caja de texto
    EditText email; //Caja de texto
    EditText antecedentes; //Caja de texto
    ListView lista; //componente android para mostrar una lista de cosas
    Paciente pacienteSeleccionado; // objeto FormaPago que esta enlazado a bd


    Button crear;
    Button cancelar;


    //Se ejecuta cuando inicia la app
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_actividad_paciente, container, false);

        nombresPaciente = (EditText) view.findViewById(R.id.txtNombres);
        apellidosPaciente = (EditText) view.findViewById(R.id.txtApellidos);
        telefono = (EditText) view.findViewById(R.id.txtTelefono);
        cedula = (EditText) view.findViewById(R.id.txtCedula);
        email = (EditText) view.findViewById(R.id.txtEmail);
        antecedentes = (EditText) view.findViewById(R.id.txtAntecedentes);
        lista = (ListView) view.findViewById(R.id.listaPaciente);


        crear = (Button) view.findViewById(R.id.id_btnCrearPaciente);
        cancelar = (Button) view.findViewById(R.id.id_btnCancelarPaciente );
        listar(); //llamamos método para listar formas de pago, apenas inicie la app

        //Este evento nos permite obtener un elemento de la lista que el usuario seleccione en la vista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                pacienteSeleccionado = (Paciente) adapterView.getItemAtPosition(i); //guardamos el objeto seleccionado

                nombresPaciente.setText(pacienteSeleccionado.paciNombre); // le enviamos a nuestra caja de texto lo que selecciono el usuario
                apellidosPaciente.setText(pacienteSeleccionado.paciApellido);
                telefono.setText(pacienteSeleccionado.paciTelefono);
                cedula.setText(pacienteSeleccionado.paciCedula);
                email.setText(pacienteSeleccionado.paciEmail);
                antecedentes.setText(pacienteSeleccionado.paciAntecedentes);
                crear.setText(Util.SACTUALIZAR);
                cancelar.setVisibility(View.VISIBLE);
            }
        });
        crear.setOnClickListener(view1 -> {
            if (crear.getText().toString().equals(Util.SCREAR))
                insertar();
            if (crear.getText().toString().equals(Util.SACTUALIZAR)) {
                actualizar();

                cancelar.setVisibility(View.GONE);
                crear.setText(Util.SCREAR); //indicamos al usuario que ha vuelto al modo insertar
            }

        });

        cancelar.setOnClickListener(view2 -> {

            crear.setText(Util.SCREAR);
            cancelar.setVisibility(View.GONE);

            limpiarTexto();
        });

        return view;
    }


    //Método que lista los tipos de pago
    public void listar() {
        Call<List<Paciente>> call = ProducerPacienteProxy.producer().listar();
        Util.ejecutarLista(call, getActivity(), lista);
    }

    //para limpiar la caja de texto despues de cada actualización o inserción
    public void limpiarTexto() {
        nombresPaciente.setText("");
        apellidosPaciente.setText("");
        telefono.setText("");
        cedula.setText("");
        email.setText("");
        antecedentes.setText("");
    }

    //Método para insertar registros en la tabla
    public void insertar() {
        String nombresPaciente = this.nombresPaciente.getText().toString(); //obtenemos lo que el usuario digita en la caja de texto
        String apellidosPaciente = this.apellidosPaciente.getText().toString();
        String telefono = this.telefono.getText().toString();
        String cedula = this.cedula.getText().toString();
        String email = this.email.getText().toString();
        String antecedentes = this.antecedentes.getText().toString();
        if (!nombresPaciente.isEmpty()) { //verificamos que no este vacio la caja de texto
            Call<ResponseBody> call = ProducerPacienteProxy.producer().insertar(new Paciente(nombresPaciente, apellidosPaciente, telefono, cedula, email, antecedentes));
            Util.ejecutarCud(call, getActivity(), Util.SINSERTADO);
            limpiarTexto();
            refrescarFragmento();

        } else {
            Toast.makeText(getActivity(), "Texto vacio", Toast.LENGTH_LONG).show();
        }
    }

    //Método para actualizar registros en la tabla
    public void actualizar() {
        String nombresPaciente = this.nombresPaciente.getText().toString(); //obtenemos lo que el usuario digite

        String apellidosPaciente = this.apellidosPaciente.getText().toString();
        String telefono = this.telefono.getText().toString();
        String cedula = this.cedula.getText().toString();
        String email = this.email.getText().toString();
        String antecedentes = this.antecedentes.getText().toString();


        if (!nombresPaciente.isEmpty() && pacienteSeleccionado.paciId != 0) { //validamos que el texto no este vacio y que si se trata de una actualización en base al id que no sea nulo
            Paciente paciente = new Paciente(pacienteSeleccionado.paciId, nombresPaciente, apellidosPaciente, telefono, cedula, email, antecedentes); //almacenamos los cambios de la forma de pago con su respectivo id
            Call<ResponseBody> call = ProducerPacienteProxy.producer().actualizar(paciente);
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
