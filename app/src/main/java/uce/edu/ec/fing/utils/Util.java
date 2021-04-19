package uce.edu.ec.fing.utils;
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
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uce.edu.ec.fing.R;


public class Util {
    public static final String SCREAR = "Guardar";
    public static String SACTUALIZAR = "Actualizar";
    public static String SINSERTADO = "Insertado Correctamente";
    public static String SACTUALIZADO = "Actualizado Correctamente";
    public static String SELIMINADO="Eliminado Correctamente";

    public static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(3000, TimeUnit.SECONDS)
            .readTimeout(3000, TimeUnit.SECONDS).build();


    public static void ejecutarCud(Call<ResponseBody> call, FragmentActivity fragment, String mensaje) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(fragment, mensaje, Toast.LENGTH_LONG).show(); //mensaje para el usuario
                    return;
                }else{
                    Toast.makeText(fragment, "Código de error: "+ response.code(), Toast.LENGTH_LONG).show(); //mensaje para el usuario
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(fragment, "Algo salió mal: " + t.getMessage(), Toast.LENGTH_LONG).show(); //mensaje para el usuario
            }
        });
    }

    public static void ejecutarLista(Call call, FragmentActivity fragment, ListView listaObjeto) {
        call.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(fragment, "Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Object> lista = response.body();
                ArrayAdapter<Object> arrayAdapterForma; // Adaptador para enviar a una List y presentarlo en un ListView
                //preparamos arrayadapter para enviarle nuestra arraylist de tipo de pago
                arrayAdapterForma = new ArrayAdapter<Object>(fragment, android.R.layout.simple_list_item_1, lista);

                listaObjeto.setAdapter(arrayAdapterForma);

            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                Toast.makeText(fragment, "Algo salió mal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("asfasdf",t.getMessage());
            }
        });
    }

    public static void ejecutarSpinner(Call call, FragmentActivity fragment, Spinner listaObjeto) {
        call.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(fragment, "Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Object> lista = response.body();
                ArrayAdapter<Object> arrayAdapterForma; // Adaptador para enviar a una List y presentarlo en un ListView
                //preparamos arrayadapter para enviarle nuestra arraylist de tipo de pago
                arrayAdapterForma = new ArrayAdapter<Object>(fragment, android.R.layout.simple_spinner_item, lista);

                listaObjeto.setAdapter(arrayAdapterForma);

            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                Toast.makeText(fragment, "Algo salió mal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
