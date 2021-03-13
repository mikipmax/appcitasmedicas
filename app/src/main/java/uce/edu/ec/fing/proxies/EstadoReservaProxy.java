package uce.edu.ec.fing.proxies;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uce.edu.ec.fing.dto.EstadoReserva;


public interface EstadoReservaProxy {
    @GET("estados-reserva")
    Call<List<EstadoReserva>> listar();



    @POST("insertar")
    Call<ResponseBody> insertar(@Body EstadoReserva entity);

    @PUT("actualizar")
    Call<ResponseBody> actualizar(@Body EstadoReserva entity);
}
