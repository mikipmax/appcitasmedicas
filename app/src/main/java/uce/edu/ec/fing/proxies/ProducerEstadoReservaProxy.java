package uce.edu.ec.fing.proxies;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uce.edu.ec.fing.utils.Util;

public class ProducerEstadoReservaProxy {
    private static Retrofit retrofit = null;

    public static EstadoReservaProxy producer() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://ms-sb-estado-reserva.herokuapp.com/").client(Util.client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(EstadoReservaProxy.class);
    }


}
