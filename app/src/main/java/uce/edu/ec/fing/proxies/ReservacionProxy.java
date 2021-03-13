package uce.edu.ec.fing.proxies;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uce.edu.ec.fing.dto.Reservacion;


public interface ReservacionProxy {
    @GET("reservaciones")
    Call<List<Reservacion>> listar();

    @POST("insertar")
    Call<ResponseBody> insertar(@Body Reservacion entity);

    @PUT("actualizar")
    Call<ResponseBody> actualizar(@Body Reservacion entity);

    @DELETE("eliminar/{id}")
    Call<ResponseBody> eliminar(@Path("id") int id);
}
