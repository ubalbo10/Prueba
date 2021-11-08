package com.example.prueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.prueba.interfaces.apiServices
import com.example.prueba.models.trabajadorResponse
import com.example.prueba.models.user
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //definicion de variables y widgets
        val retrofit:Retrofit;

        val btn_ingresar=findViewById<Button>(R.id.btn_ingresar);
        val edit_user=findViewById<EditText>(R.id.edit_usuario);
        val edit_pass=findViewById<EditText>(R.id.edit_password);

        //declaracion e inicializacion de retrofit para hacer peticiones a la api
        retrofit = Retrofit.Builder()
            .baseUrl(apiServices.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //logica al hacer click en ingresar
        btn_ingresar.setOnClickListener {
            //Toast.makeText(this,edit_user.text,Toast.LENGTH_LONG);

            //asegurandonos de que no esten vacias las cajas de texto
            if(edit_pass.text.toString()==null || edit_user.text.toString()==null){
                Toast.makeText(this,"datos vacios",Toast.LENGTH_LONG);
            }

            //creando el objeto que enviaremos para realizar la consulta en la api
            var usuario:user= user(edit_user.text.toString(),edit_pass.text.toString())
            Log.i("user",usuario.password)


            //realizando la peticion a la api para verificar el ingreso del usuario
            val services = retrofit.create(apiServices::class.java)
            services.obtenerUsuario(usuario)?.
            enqueue(object:Callback<trabajadorResponse?>{
                override fun onResponse(
                    call: Call<trabajadorResponse?>,
                    response: Response<trabajadorResponse?>
                    ) {
                    //logica si la consulta se hace con exito

                    if(response.isSuccessful){
                        var respuesta=response.body();

                        //guardando los datos del usuario logueado
                        Nombre=respuesta!!.nombres;
                        Apellido=respuesta!!.apellidos;
                        Puesto=respuesta!!.puesto;
                        Edad=respuesta!!.edad.toString();

                        Log.i("nombre",respuesta.toString())

                        //lanzamos la siguiente actividad donde generaremos el codigo QR
                        val intent=Intent(applicationContext,MainActivity::class.java);
                        startActivity(intent);
                        //cerramos la actividad
                        finish();
                    }else{
                        Toast.makeText(applicationContext,"usuario invalido",Toast.LENGTH_SHORT).show();
                        Log.i("no encontrado","user invalid")
                    }
                }

                override fun onFailure(call: Call<trabajadorResponse?>, t: Throwable) {
                    Log.i("error","no conecta la api")
                    //t.message
                    Log.i("errormensaje",t.message.toString())

                }

            } )





        }
    }
    companion object {
         var Nombre = ""
         var Apellido = "d"
         var Edad = "d"
         var Puesto = "d"


    }
}