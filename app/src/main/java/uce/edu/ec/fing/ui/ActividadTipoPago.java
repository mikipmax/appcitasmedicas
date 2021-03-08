package uce.edu.ec.fing.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uce.edu.ec.fing.R;
import uce.edu.ec.fing.dto.TipoPago;
import uce.edu.ec.fing.proxies.ProducerTipoPagoProxy;


@SuppressLint("NewApi")
public class ActividadTipoPago extends Fragment {


    public static ActividadTipoPago newInstance() {
        Bundle args = new Bundle();
        ActividadTipoPago fragmento = new ActividadTipoPago();
        fragmento.setArguments(args);
        return fragmento;
    }

    public ActividadTipoPago() {

    }

    //Creacción de variables de instancia de clase

    ArrayAdapter<TipoPago> arrayAdapterForma; // Adaptador para enviar a una List y presentarlo en un ListView
    EditText tipoPago; //Caja de texto
    ListView listaTipoPago; //componente android para mostrar una lista de cosas
    TipoPago tipoPagoSeleccionado; // objeto FormaPago que esta enlazado a bd


    private final String SCREAR = "Guardar";
    private final String SACTUALIZAR = "Actualizar";
    Button crear;
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

        listarTipoPago(); //llamamos método para listar formas de pago, apenas inicie la app

        //Este evento nos permite obtener un elemento de la lista que el usuario seleccione en la vista
        listaTipoPago.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                tipoPagoSeleccionado = (TipoPago) adapterView.getItemAtPosition(i); //guardamos el objeto seleccionado
                estado.setVisibility(View.VISIBLE); //hacemos visible el check
                estado.setChecked(tipoPagoSeleccionado.tipaVigente);
                tipoPago.setText(tipoPagoSeleccionado.tipaNombre); // le enviamos a nuestra caja de texto lo que selecciono el usuario
                crear.setText(SACTUALIZAR);
            }
        });
        crear.setOnClickListener(view1 -> {
            if (crear.getText().toString().equals(SCREAR))

                insertar();

            else {
                actualizar();
                estado.setVisibility(View.INVISIBLE); //perdemos el estado del check
            }

        });
        return view;
    }


    //Método que lista los tipos de pago
    public void listarTipoPago() {
        Call<List<TipoPago>> call = ProducerTipoPagoProxy.producer().listarTiposPago();
        call.enqueue(new Callback<List<TipoPago>>() {
            @Override
            public void onResponse(Call<List<TipoPago>> call, Response<List<TipoPago>> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getActivity(), "Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<TipoPago> lista = response.body();
                //preparamos arrayadapter para enviarle nuestra arraylist de tipo de pago
                arrayAdapterForma = new ArrayAdapter<TipoPago>(getActivity(), android.R.layout.simple_list_item_1, lista);
                listaTipoPago.setAdapter(arrayAdapterForma);
            }

            @Override
            public void onFailure(Call<List<TipoPago>> call, Throwable t) {
                Toast.makeText(getActivity(), "Algo salió mal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    //para limpiar la caja de texto despues de cada actualización o inserción
    public void limpiarTexto() {
        tipoPago.setText("");
    }

    //Método para insertar registros en la tabla
    public void insertar() {
        String tipoPago = this.tipoPago.getText().toString(); //obtenemos lo que el usuario digita en la caja de texto
        if (!tipoPago.isEmpty()) { //verificamos que no este vacio la caja de texto
            Call<TipoPago> call = ProducerTipoPagoProxy.producer().insertar(new TipoPago(tipoPago, true));
            call.enqueue(new Callback<TipoPago>() {
                @Override
                public void onResponse(Call<TipoPago> call, Response<TipoPago> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Insertado Correctamente", Toast.LENGTH_LONG).show(); //mensaje para el usuario
                    }
                }

                @Override
                public void onFailure(Call<TipoPago> call, Throwable t) {
                    Toast.makeText(getActivity(), "Algo salió mal: " + t.getMessage(), Toast.LENGTH_LONG).show(); //mensaje para el usuario
                }
            });
            limpiarTexto();
            listarTipoPago(); //una vez que se inserte actualizamos la lista

        } else {
            Toast.makeText(getActivity(), "Texto vacio", Toast.LENGTH_LONG).show();
        }
    }

    //Método para actualizar registros en la tabla
    public void actualizar() {
      /*  String txtFormaPago = this.tipoPago.getText().toString(); //obtenemos lo que el usuario digite
        int estadoAux=estado.isChecked()?1:0;
        try {
            if (!txtFormaPago.isEmpty() &&!tipoPagoSeleccionado.idFormaPago.isEmpty()) { //validamos que el texto no este vacio y que si se trata de una actualización en base al id que no sea nulo
                FormaPago formaPago = new FormaPago(tipoPagoSeleccionado.idFormaPago, txtFormaPago, estadoAux ); //almacenamos los cambios de la forma de pago con su respectivo id

                datos.getDb().beginTransaction(); //iniciamos la transacción
                datos.actualizarFormaPago(formaPago); //actualizamos el registro
                datos.getDb().setTransactionSuccessful(); //en pocas un commit
                datos.getDb().endTransaction(); //cerramos la transacción
                limpiarTexto();
                listarFormaPago();
                Toast.makeText(getActivity(), "Actualizado Correctamente", Toast.LENGTH_LONG).show();//mensaje para el usuario
            } else {
                Toast.makeText(getActivity(), "Algo salió mal", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Algo salió mal", Toast.LENGTH_LONG).show(); //por si algo sale mal
        }finally {
            crear.setText(SCREAR); //indicamos al usuario que ha vuelto al modo insertar
        }
*/
    }
}
