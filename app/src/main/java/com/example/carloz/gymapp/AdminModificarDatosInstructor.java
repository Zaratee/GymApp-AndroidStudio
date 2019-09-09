package com.example.carloz.gymapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class AdminModificarDatosInstructor extends AppCompatActivity {

    EditText etxtNombre, etxtApellido, etxtTelefono;
    String Cuenta;
    CircularImageView imgvUsuario;
    TextView txtvTitulo, txtvHora;
    Button btnModificar, btnTomarFoto;
    CheckBox chckBoxHorario;

    private static final String CARPETA_PRINCIPAL = "misImagenes/";
    private static final String CARPETA_IMAGEN = "imagenesGYM";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private String path;
    File fileImage;
    Bitmap bitmap;

    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 20;

    RequestQueue requestt;

    private String UPLOAD_URL_Cliente = "http://thegymlife.online/php/fotos/Subir_ImagenCliente.php";

    private int PICK_IMAGE_REQUEST = 1;

    private String KEY_IMAGEN = "foto";
    private String KEY_NOMBRE = "nombre";
    private String KEY_CUENTA = "cuenta";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modificar_datos_instructor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etxtNombre = (EditText) findViewById(R.id.etxtNombre_AdminModificarDatosInstructor);
        etxtApellido = (EditText) findViewById(R.id.etxtApellido_AdminModificarDatosInstructor);
        etxtTelefono = (EditText) findViewById(R.id.etxtTelefono_AdminModificarDatosInstructor);
        txtvHora = (TextView) findViewById(R.id.txtvHorario_AdminModificarDatosInstructor);

        txtvTitulo = (TextView) findViewById(R.id.txtvTitulo_AdminModificarDatosInstructor);

        btnModificar = (Button) findViewById(R.id.btnModificarCliente_AdminModificarDatosInstructor);
        btnTomarFoto = (Button) findViewById(R.id.btnTomarFoto_AdminModificarDatosInstructor);
        chckBoxHorario = (CheckBox) findViewById(R.id.chkboxCambiarHorario_AdminModificarNutriologo);

        imgvUsuario = (CircularImageView) findViewById(R.id.imgvUsuario_AdminModificarDatosInstructor);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvTitulo.setTypeface(Thin);
        btnTomarFoto.setTypeface(Condensed);
        btnModificar.setTypeface(Condensed);

        Cuenta = getIntent().getStringExtra("REGISTRO");
        Glide.with(AdminModificarDatosInstructor.this).load("http://thegymlife.online/php/fotos/imagenesInstructor/"+Cuenta+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvUsuario);

        conexionBDInfoCliente();
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDModificarInfo();

                if (chckBoxHorario.isChecked()){
                    conexionBDCambiarHorario();
                }
            }
        });

    }

    private void conexionBDModificarInfo() {

        String url = "http://thegymlife.online/php/admin/Administrador_Modificar_Entrenador_Datos.php?registro=" +
                Cuenta+"&nombre="+etxtNombre.getText().toString().trim()+"&apellido="+etxtApellido.getText().toString().trim()
                +"&telefono="+etxtTelefono.getText().toString().trim();
        url = url.replaceAll(" ", "%20");
        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String valor = response.getString("Estado");
                                    switch(valor) {
                                        case "OK":
                                            Toast.makeText(AdminModificarDatosInstructor.this,"Instructor actualizado",Toast.LENGTH_SHORT).show();
                                            uploadImage();
                                            Intent intent = new Intent(AdminModificarDatosInstructor.this,admin_menu.class);
                                            startActivity(intent);
                                            break;
                                        case "TELEFONO":
                                            Toast.makeText(AdminModificarDatosInstructor.this, "Teléfono ya asignado", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(AdminModificarDatosInstructor.this,"Rellenar los datos",Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(AdminModificarDatosInstructor.this,"Error conexion",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminModificarDatosInstructor.this);
        x.add(peticion);


    }

    private void conexionBDCambiarHorario() {

        String url = "http://thegymlife.online/php/admin/Administrador_Modificar_Entrenador_Horario.php?registro="+Cuenta;
        url = url.replaceAll(" ", "%20");

        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String valor = response.getString("Estado");
                                    switch(valor) {
                                        case "OK":
                                            Toast.makeText(AdminModificarDatosInstructor.this, "Horario cambiado", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(AdminModificarDatosInstructor.this,"Horario no cambiado",Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(AdminModificarDatosInstructor.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminModificarDatosInstructor.this);
        x.add(peticion);

    }

    private void conexionBDInfoCliente() {
        String url = "http://thegymlife.online/php/admin/Administrador_Visualizar_Modificar_Entrenador_Datos.php?registro="+Cuenta;
        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String valor = response.getString("Estado");
                                    String bdRegistro = Cuenta;
                                    String bdNombre = response.getString("Entrenador_Nombre");
                                    String bdApellido = response.getString("Entrenador_Apellido");
                                    String bdHorario = response.getString("Entrenador_Horario");
                                    String bdTelefono = response.getString("Entrenador_Telefono");


                                    switch(valor) {
                                        case "OK":

                                            etxtNombre.setText(bdNombre);
                                            etxtApellido.setText(bdApellido);
                                            etxtTelefono.setText(bdTelefono);
                                            if (bdHorario.equals("0")){
                                                txtvHora.setText("Matutino");
                                                chckBoxHorario.setText("Cambiar horario a Vespertino");
                                            }
                                            if (bdHorario.equals("1")){
                                                txtvHora.setText("Vespertino");
                                                chckBoxHorario.setText("Cambiar horario a Matutino");
                                            }

                                            break;
                                        case "ERROR":
                                            Toast.makeText(AdminModificarDatosInstructor.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(AdminModificarDatosInstructor.this,"Error conexion",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminModificarDatosInstructor.this);
        x.add(peticion);


    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL_Cliente,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(AdminModificarDatosInstructor.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        //Toast.makeText(AdminModificarDatosCliente.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

                //Obtener el nombre de la imagen
                String nombre = Cuenta.trim();

                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_IMAGEN, imagen);
                params.put(KEY_NOMBRE, Cuenta);
                params.put(KEY_CUENTA, "Instructor");

                //Parámetros de retorno
                return params;
            }
        };
        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
        loading.dismiss();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COD_SELECCIONA:
                Uri mipath = data.getData();
                imgvUsuario.setImageURI(mipath);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mipath);
                    imgvUsuario.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(AdminModificarDatosInstructor.this, new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path", ""+path);
                            }
                        });

                bitmap = BitmapFactory.decodeFile(path);
                imgvUsuario.setImageBitmap(bitmap);
                break;
        }
    }
    private void mostrarDialogOpciones() {
        final CharSequence[] opciones = {"Tomar Foto", "Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(AdminModificarDatosInstructor.this);
        builder.setTitle("Elige una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Tomar Foto")){
                    abrirCamara();
                }else{
                    if (opciones[which].equals("Elegir de Galeria")){
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccionar"),COD_SELECCIONA);
                    }else {
                        dialog.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void abrirCamara() {
        File miFile = new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();
        if (isCreada == false){
            isCreada = miFile.mkdirs();
        }
        if (isCreada == true){
            String nombres = Cuenta+".jpg";

            path = Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombres;

            fileImage = new File(path);

            Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImage));

            startActivityForResult(intent,COD_FOTO);
        }
    }
}
