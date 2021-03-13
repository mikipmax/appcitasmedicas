package uce.edu.ec.fing.proxies;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uce.edu.ec.fing.dto.Especialidad;


public interface EspecialidadProxy {
    @GET("especialidades")
    Call<List<Especialidad>> listar();

    @GET("especialidades-vigentes")
    Call<List<Especialidad>> listarVigentes();

    @POST("especialidad/insertar")
    Call<ResponseBody> insertar(@Body Especialidad entity);

    @PUT("especialidad/actualizar")
    Call<ResponseBody> actualizar(@Body Especialidad entity);
}
