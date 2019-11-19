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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class AgregarClienteInfo extends AppCompatActivity {

    TextView nANombre, nARegistro, nATelefono, nARFID, nATitulo, Nombre, Registroo, Telefono, RFID,txtvContra;
    Button btnTomarFoto, btnAgregar;
    CircularImageView imgFoto;
    String stringTelefono, stringCuenta, stringFoto;


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

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvContra = findViewById(R.id.txtvContra_AgregarClienteInfo);

        nATitulo = (TextView) findViewById(R.id.txtvTitulo_AgregarClienteInfo);
        nANombre = (TextView) findViewById(R.id.txtvNoActionNombre_AgregarClienteInfo);
        nARegistro = (TextView) findViewById(R.id.txtvNoActionRegistro_AgregarClienteInfo);
        nARFID = (TextView) findViewById(R.id.txtvNoActionRFID_AgregarClienteInfo);
        nATelefono = (TextView) findViewById(R.id.txtvNoActionTelefono_AgregarClienteInfo);

        Nombre = (TextView) findViewById(R.id.txtvNombre_AgregarClienteInfo);
        Registroo = (TextView) findViewById(R.id.txtvRegistro_AgregarClienteInfo);
        Telefono = (TextView) findViewById(R.id.txtvTelefono_AgregarClienteInfo);
        RFID = (TextView) findViewById(R.id.txtvRFID_AgregarClienteInfo);

        btnTomarFoto = (Button) findViewById(R.id.btnTomarFoto_AgregarClienteInfo);
        btnAgregar = (Button) findViewById(R.id.btnAgregar_AgregarClienteInfo);

        imgFoto = (CircularImageView) findViewById(R.id.imgvFoto_AgregarClienteInfo);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        nANombre.setTypeface(Regular);
        nARegistro.setTypeface(Regular);
        nARFID.setTypeface(Regular);
        nATelefono.setTypeface(Regular);

        nATitulo.setTypeface(Thin);

        btnAgregar.setTypeface(Condensed);
        btnTomarFoto.setTypeface(Condensed);

        Nombre.setTypeface(Light);
        Registroo.setTypeface(Light);
        Telefono.setTypeface(Light);
        RFID.setTypeface(Light);
        auth = FirebaseAuth.getInstance();

        requestt = Volley.newRequestQueue(AgregarClienteInfo.this);
        recibirDatos();
        switch (stringCuenta){
            case "Cliente":
                nATitulo.setText("Cliente Informacion");
                txtvContra.setText("Gymlife123");
                RFID.setVisibility(View.VISIBLE);
                nARFID.setVisibility(View.VISIBLE);
                conexionBDInfoCliente();
                break;
            case "Instructor":
                txtvContra.setText("Entrenador123");
                nATitulo.setText("Entrenador Informacion");
                RFID.setVisibility(View.INVISIBLE);
                nARFID.setVisibility(View.INVISIBLE);
                conexionBDInfoInstructor();
                break;
            case "Nutriologo":
                txtvContra.setText("Nutriologo123");
                nATitulo.setText("Nutriologo Informacion");
                RFID.setVisibility(View.INVISIBLE);
                nARFID.setVisibility(View.INVISIBLE);
                conexionBDInfoNutriologo();
                break;
        }

        if (stringFoto.equals("0")){
            btnTomarFoto.setVisibility(View.INVISIBLE);
        }


        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // conexionSubirImagen();
                Intent intent = new Intent(AgregarClienteInfo.this,admin_menu.class);

                if (stringFoto.equals("1")){
                    uploadImage();
                    startActivity(intent);
                    Toast.makeText(AgregarClienteInfo.this, "Imagen Agregada", Toast.LENGTH_SHORT).show();
                }else if (stringFoto.equals("0")){
                    startActivity(intent);
                }

            }
        });

    }



    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL_Cliente,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta

                        //Toast.makeText(AgregarClienteInfo.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast

                        //Toast.makeText(AgregarClienteInfo.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

                //Obtener el nombre de la imagen
                String nombre = Registroo.getText().toString().trim();

                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_IMAGEN, imagen);
                params.put(KEY_NOMBRE, nombre);
                params.put(KEY_CUENTA, stringCuenta);

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




    private void mostrarDialogOpciones() {
        final CharSequence[] opciones = {"Tomar Foto", "Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(AgregarClienteInfo.this);
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
            String nombres = Registroo.getText().toString()+".jpg";

            path = Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombres;

            fileImage = new File(path);

            Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImage));

            startActivityForResult(intent,COD_FOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COD_SELECCIONA:
                Uri mipath = data.getData();
                imgFoto.setImageURI(mipath);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mipath);
                    imgFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(AgregarClienteInfo.this, new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path", ""+path);
                            }
                        });

                bitmap = BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);
                break;
        }
    }



    private void recibirDatos() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        stringTelefono = (String) bundle.get("TELEFONO");
        stringCuenta = (String) bundle.get("CUENTA");
        stringFoto = (String) bundle.get("FOTO");
    }

    private void conexionBDInfoCliente() {
            //Log.d("Error","inicioo");
            String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Cliente_Visualizar.php?telefono="+stringTelefono;
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
                                        String bdNombre = response.getString("Cliente_Nombre");
                                        String bdApellido = response.getString("Cliente_Apellido");
                                        String bdRegistro = response.getString("Cliente_Registro");
                                        String bdTelefono = response.getString("Cliente_Telefono");
                                        String bdRFID = response.getString("Cliente_RFID");
                                        Nombre.setText(bdNombre+" "+bdApellido);
                                        Registroo.setText(bdRegistro);
                                        Telefono.setText(bdTelefono);
                                        RFID.setText(bdRFID);
                                        IngresarUsuarioFirebase();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            , new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AgregarClienteInfo.this, "Error php", Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(AgregarClienteInfo.this);
            x.add(peticion);


    }
    public void IngresarUsuarioFirebase(){
        String txt_username = Registroo.getText().toString();
        String txt_email = Registroo.getText().toString()+"@gymlife.com";
        String txt_password = "GymLife";
        String txt_telefono = Registroo.getText().toString();

        registrar(txt_username,txt_email,txt_password,txt_telefono);
    }

    private void registrar(final String usuario, String email, final String password, final String telefono){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", usuario);
                            hashMap.put("telefono", telefono);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Toast.makeText(AgregarClienteInfo.this, "Firebase Usuario Creado", Toast.LENGTH_SHORT).show();
                                        /*Intent intent = new Intent(AgregarCliente.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();*/
                                    }
                                }
                            });
                        } else {
                            //Toast.makeText(AgregarClienteInfo.this, "No te puedes registrar con esos datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void conexionBDInfoNutriologo(){

        //Log.d("Error","inicioo");
        String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Nutriologo_Visualizar.php?telefono="+stringTelefono;
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
                                    String bdNombre = response.getString("Nutriologo_Nombre");
                                    String bdApellido = response.getString("Nutriologo_Apellido");
                                    String bdRegistro = response.getString("Nutriologo_Registro");
                                    String bdTelefono = response.getString("Nutriologo_Telefono");
                                    Nombre.setText(bdNombre+" "+bdApellido);
                                    Registroo.setText(bdRegistro);
                                    Telefono.setText(bdTelefono);
                                    IngresarUsuarioFirebase();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgregarClienteInfo.this, "Error php", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AgregarClienteInfo.this);
        x.add(peticion);


    }

    private void conexionBDInfoInstructor() {

                //Log.d("Error","inicioo");
                String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Entrenador_Visualizar.php?telefono="+stringTelefono;
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
                                            String bdNombre = response.getString("Entrenador_Nombre");
                                            String bdApellido = response.getString("Entrenador_Apellido");
                                            String bdRegistro = response.getString("Entrenador_Registro");
                                            String bdTelefono = response.getString("Entrenador_Telefono");
                                            Nombre.setText(bdNombre+" "+bdApellido);
                                            Registroo.setText(bdRegistro);
                                            Telefono.setText(bdTelefono);
                                            IngresarUsuarioFirebase();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AgregarClienteInfo.this, "Error php", Toast.LENGTH_SHORT).show();
                            }
                        });
                RequestQueue x = Volley.newRequestQueue(AgregarClienteInfo.this);
                x.add(peticion);


    }


}
