package com.example.prueba

import android.Manifest.permission.CAMERA
import android.Manifest.permission_group.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.json.JSONObject
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.Visibility
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.jar.Manifest
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.text.Layout
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        SolicitarPermiso();

        //recuperando la data del usuario a mostrar
        var nombre=LoginActivity.Nombre;
        var apellido=LoginActivity.Apellido;
        var puesto=LoginActivity.Puesto;
        var edad=LoginActivity.Edad;

        //generando el json de la data
        var text = "{\"nombre\":\"${nombre}\",\"apellido\":\"${apellido}\"," +
                "\"puesto\":\"${puesto}\",\"edad\":\"${edad}\"}" ;
        //var text=JSONObject(JSON_STRING);

        //declarando los widgets
        var btn=findViewById<Button>(R.id.button);
        var imaageview=findViewById<ImageView>(R.id.imageView);
        var comentario=findViewById<EditText>(R.id.comentario_usuario);
        var btn_enviar=findViewById<Button>(R.id.btn_enviar_Correo);
        var layoutComentario=findViewById<TextInputLayout>(R.id.layout_comentario);


        //ocultando los widgets
        layoutComentario.visibility=View.GONE;
        btn_enviar.visibility=View.GONE;


        //se ejecuta esto al darle generar codigo QR
        btn.setOnClickListener {
            /* Formateando el json para generar el codigo QR*/
            val writer:MultiFormatWriter= MultiFormatWriter();
            val matriz:BitMatrix=writer.encode(text,BarcodeFormat.QR_CODE,350,350);
            val encoder:BarcodeEncoder= BarcodeEncoder();
            val bitmap=encoder.createBitmap(matriz)

            //asignando el codigo QR al widged imageView para mostrarlo al usuario
            imaageview.setImageBitmap(bitmap);

            //ocultando el boton de generar
            btn.visibility=View.GONE;

            //mostrando boton de enviar y caja de comentarios

            layoutComentario.visibility=View.VISIBLE
            btn_enviar.visibility=View.VISIBLE;



        }

        //se ejecuta esto al darle al boton enviar
        btn_enviar.setOnClickListener {

            SolicitarPermiso();
            imaageview.buildDrawingCache()
            val bitmap: Bitmap = imaageview.getDrawingCache()


            /***** COMPARTIR IMAGEN
             *
             * transformamos el bitmat a imagen con extension PNG*/
            try {

                val file = File(imaageview.getContext().externalCacheDir, "$bitmap.png")
                var fOut: FileOutputStream? = null
                fOut = FileOutputStream(file)
                //conversion de bitmap a .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.flush()
                fOut.close()
                file.setReadable(true, false)

                /* creacion del intent para poder abrir la app externa */
                val intent = Intent(Intent.ACTION_SEND)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                //definimos el archivo que adjuntaremos pasandole la direccion de la imagen
                // atravez de Uri.fromfile
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                //garantizando que tenemos los permisos de seguridad
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                //definiendo el formato del archivo que pasaremos
                intent.type = "image/png"
                //agregando asunto en el correo
                intent.putExtra(Intent.EXTRA_SUBJECT, "Datos del trabajador");
                //agregando el cuerpo del mensaje con los comentario realizados por el usuario
                intent.putExtra(Intent.EXTRA_TEXT,comentario.text.toString())

                //lanzando la app externa
                startActivity(intent)
                //startActivityForResult(intent,225)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }



    }


    private fun SolicitarPermiso() {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            //El permiso no está aceptado.
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                200)
        } else {
            //El permiso está aceptado.

        }
    }
}