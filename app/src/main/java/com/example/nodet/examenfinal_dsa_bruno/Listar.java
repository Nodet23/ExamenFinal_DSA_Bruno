package com.example.nodet.examenfinal_dsa_bruno;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Listar extends AppCompatActivity {

    private TextView textView;
    private TextView Master, Master2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        /* Explicacion del codigo:
      tras coger el nombre de la mainActivity, hacemos la consulta a la API // he intentado separar la consulta en una clase aparte pero no he podido, asi que la he dejado aqui
         Creamos objetos Usuarios por cada follower que tenga, de estos Usuario, cogemos su login y su url de la imagen y lo volcamos en una lista para despues mostrarla
         Al crear Usuario de los followers y no del propio usuario del cual se quiere saber sus followers, no puedo obtener su imagen
         Tras obtener 2 listas con las imagenes y las urls se les envia a un Adaptador custom para que muestre sus nombres e imagenes junto a los followers que tiene

    */
        try{
            Intent intent = getIntent();
            String user = intent.getStringExtra("user");
            Master = (TextView) findViewById(R.id.textView2);
            Master.setText(user);





            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create());


            Retrofit retrofit =
                    builder.client(httpClient.build()).build();

            APIRest interfazAPI = retrofit.create(APIRest.class);

            Call<List<Usuario>> call = interfazAPI.loadFollowers(user);

            call.enqueue(new Callback<List<Usuario>>() {

                @Override

                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    textView = (TextView) findViewById(R.id.textView3);
                    if(response.code()==200){
                        List<Usuario> followers = (List<Usuario>)response.body();

                        List<String>nombres=new ArrayList<String>();
                        List<String>list_URLs=new ArrayList<String>();

                        ListView listView = (ListView) findViewById(R.id.listView);
                        ImageView imageView = (ImageView) findViewById(R.id.imageView);

                        for (int i = 0; i < followers.size(); i++) {
                            nombres.add(followers.get(i).getLogin());
                            list_URLs.add(followers.get(i).getAvatar());
                        }


                        CustomListAdapter adapter=new CustomListAdapter(Listar.this, nombres, list_URLs);
                        listView.setAdapter(adapter);

                        Picasso.with(getApplicationContext()).load("https://avatars1.githubusercontent.com/u/25772512?v=3").resize(270, 200).into(imageView); // al no crear un usuario para el login del cual se quiere hacer la consulta he puesto una imagen standard para este
                        // si quiese coger tambien su imagen de la API, deberia crear un modelo distinto que sea Usuario que tenga otra clase de followers y realizar 2 consultas una para obtener su imagen, y otra para obtener la informacion de
                        //... 2 consultas, una para obtener su imagen y la otra para obteener la informacion de sus followers, por falta de tiempo lo he dejado asi.

                    }
                    else {
                        Toast.makeText(Listar.this, "HA HABIDO UN ERROR INESPERADO EN LA CONSULTA: "+response.code(), Toast.LENGTH_SHORT).show(); // se informa al usuario que el usuario no existe
                        Listar.this.finish();
                    }
                    textView.setVisibility(View.INVISIBLE);// se oculta el mensaje del text view asociado al LOADER cuando ya se ha cargado la info


                }

                @Override

                public void onFailure(Call<List<Usuario>> call, Throwable t) {

                    Log.e("RequestCall", "Peticion denegada");
                    Listar.this.finish();
                }

            });





        }catch (Exception e){
            Toast.makeText(Listar.this, "NO SE HA PODIDO ESTABLECER LA CONEXION CON EL SERVIDOR", Toast.LENGTH_SHORT).show(); // se informa al usuario que la API no esta disponible
        }
    }
}
