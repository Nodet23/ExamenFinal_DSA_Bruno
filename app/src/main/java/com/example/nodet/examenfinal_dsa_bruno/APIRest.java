package com.example.nodet.examenfinal_dsa_bruno;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
/**
 * Created by nodet on 1/6/17.
 */

public interface APIRest {



    @GET("/users/{user}/followers")
    Call<List<Usuario>> loadFollowers(
            @Path("user") String user
    );

    /*@GET("/users/{user}")
    Call<UsuarioMaster> getUser(
            @Path("user") String user
    );*/

}
