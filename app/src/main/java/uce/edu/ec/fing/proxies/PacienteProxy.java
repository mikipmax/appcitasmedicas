package uce.edu.ec.fing.proxies;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import uce.edu.ec.fing.dto.Paciente;


public interface PacienteProxy {
    @GET("pacientes")
    Call<List<Paciente>> listar();



    @POST("insertar")
    Call<ResponseBody> insertar(@Body Paciente entity);

    @PUT("actualizar")
    Call<ResponseBody> actualizar(@Body Paciente entity);
}
