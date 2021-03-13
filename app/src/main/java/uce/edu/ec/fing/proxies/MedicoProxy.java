package uce.edu.ec.fing.proxies;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uce.edu.ec.fing.dto.Medico;


public interface MedicoProxy {
    @GET("medicos")
    Call<List<Medico>> listar();
    @GET("medicos-vigentes")
    Call<List<Medico>> listarVigentes();
    @POST("medico/insertar")
    Call<ResponseBody> insertar(@Body Medico entity);

    @PUT("medico/actualizar")
    Call<ResponseBody> actualizar(@Body Medico entity);
}
