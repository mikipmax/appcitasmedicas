package uce.edu.ec.fing.proxies;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uce.edu.ec.fing.dto.TipoPago;

public class ProducerTipoPagoProxy {

    public static TipoPagoProxy producer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ms-sb-tipo-pago.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit.create(TipoPagoProxy.class);


    }


}
