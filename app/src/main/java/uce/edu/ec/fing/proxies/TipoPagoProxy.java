package uce.edu.ec.fing.proxies;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uce.edu.ec.fing.dto.TipoPago;

public interface TipoPagoProxy {
    @GET("tipos-pago")
    Call<List<TipoPago>> listarTiposPago();

    @GET("tipos-pago-vigentes")
    Call<List<TipoPago>> listarVigentes();

    @POST("insertar/")
    Call<ResponseBody> insertar(@Body TipoPago tipoPago);

    @PUT("actualizar")
    Call<ResponseBody> actualizar( @Body TipoPago tipoPago);
}
