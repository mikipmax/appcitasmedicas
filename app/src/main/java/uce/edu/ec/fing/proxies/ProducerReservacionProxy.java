package uce.edu.ec.fing.proxies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uce.edu.ec.fing.utils.Util;

public class ProducerReservacionProxy {
    private static Retrofit retrofit = null;

    public static ReservacionProxy producer() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://ms-sb-reservacion.herokuapp.com/").client(Util.client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ReservacionProxy.class);
    }


}
