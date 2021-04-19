package uce.edu.ec.fing.proxies;
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
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uce.edu.ec.fing.utils.Util;

public class ProducerEspecialidadProxy {
    private static Retrofit retrofit = null;

    public static EspecialidadProxy producer() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://ms-sb-medico-especialidad.herokuapp.com/").client(Util.client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(EspecialidadProxy.class);
    }


}
