package com.example.carloz.gymapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.carloz.gymapp.adaptador.AdaptadorInstructorClientesAsignados;
import com.example.carloz.gymapp.items.ItemClienteInstructor;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class admin_menu extends AppCompatActivity implements View.OnClickListener {

    CardView cardvAgregar,cardvEliminar,cardvRegistro,cardvBuzon, cardvListaClientes;
    CircularImageView imgPerfil;
    String stringCuenta;
    TextView txtvAgregar, txtvAgregarInfo, txtvEliminar, txtvEliminarInfo, txtvRegistro,
    txtvRegistroInfo, txtvBuzon, txtvBuzonInfo,txtvListaUsuarios;
    int REQUEST_CAMERA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cardvAgregar = (CardView) findViewById(R.id.cardvAgregar_admin_menu);
        cardvEliminar = (CardView) findViewById(R.id.cardvEliminar_admin_menu);
        cardvRegistro = (CardView) findViewById(R.id.cardvRegistro_admin_menu);
        cardvBuzon = (CardView) findViewById(R.id.cardvBuzonQuejas_admin_menu);
        cardvListaClientes = (CardView) findViewById(R.id.cardvListaClientes_admin_menu);
        imgPerfil = (CircularImageView) findViewById(R.id.imgvUsuarioAdmin_admin_menu);

        txtvAgregar = (TextView) findViewById(R.id.txtvAgregar_admin_menu);
        txtvAgregarInfo = (TextView) findViewById(R.id.txtvAgregarinfo_admin_menu);
        txtvEliminar = (TextView) findViewById(R.id.txtvEliminar_admin_menu);
        txtvEliminarInfo = (TextView) findViewById(R.id.txtvEliminarInfo_admin_menu);
        txtvRegistro = (TextView) findViewById(R.id.txtvRegistro_admin_menu);
        txtvRegistroInfo = (TextView) findViewById(R.id.txtvRegistroInfo_admin_menu);
        txtvBuzon = (TextView) findViewById(R.id.txtvBuzon_admin_menu);
        txtvBuzonInfo = (TextView) findViewById(R.id.txtvBuzonInfo_admin_menu);
        txtvListaUsuarios = (TextView) findViewById(R.id.txtvClientes_admin_menu);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvAgregarInfo.setTypeface(Light);
        txtvEliminarInfo.setTypeface(Light);
        txtvRegistroInfo.setTypeface(Light);
        txtvBuzonInfo.setTypeface(Light);
        txtvAgregar.setTypeface(Regular);
        txtvEliminar.setTypeface(Regular);
        txtvRegistro.setTypeface(Regular);
        txtvBuzon.setTypeface(Regular);
        txtvListaUsuarios.setTypeface(Regular);

        imgPerfil.setOnClickListener(this);
        cardvAgregar.setOnClickListener(this);
        cardvEliminar.setOnClickListener(this);
        cardvRegistro.setOnClickListener(this);
        cardvBuzon.setOnClickListener(this);
        cardvListaClientes.setOnClickListener(this);



        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        String user = preferences.getString("usuario","nada");
        if (!user.equals("nada")){
            Login.Registro = user;
        }

        Glide.with(admin_menu.this).load("http://thegymlife.online/php/fotos/imagenesAdmin/5000.jpg")
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgPerfil);


        recibirDatos();

    }


    private void recibirDatos() {
        stringCuenta =Login.Registro;
    }

    @Override
public void onClick(View v) {
Intent intentAgregar = new Intent(admin_menu.this,Admin_menu_selecusu.class);
Intent intentEliminar = new Intent(admin_menu.this,AdminEliminarSelecUsuario.class);
Intent intentModificar = new Intent(admin_menu.this,AdminMenuModificarUsuarios.class);
Intent intentBuzon = new Intent(admin_menu.this,AdminMenuQuejasUsuarios.class);
Intent intentHistorial = new Intent(admin_menu.this,AdminHistorialClientes.class);

switch (v.getId()){
case R.id.cardvAgregar_admin_menu :

            if (checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                intentAgregar.putExtra("REGISTRO",stringCuenta);
                startActivity(intentAgregar);
            }else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                    Toast.makeText(admin_menu.this, "Se necesita dar permiso a la " +
                            "camara para escanear QR", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
            }


    break;
case R.id.cardvListaClientes_admin_menu:
    intentHistorial.putExtra("REGISTRO",stringCuenta);
    startActivity(intentHistorial);
    break;

case R.id.cardvEliminar_admin_menu:
    intentEliminar.putExtra("REGISTRO",stringCuenta);
    startActivity(intentEliminar);
    break;

case R.id.cardvRegistro_admin_menu:
    startActivity(intentModificar);
    break;

case R.id.cardvBuzonQuejas_admin_menu:
    startActivity(intentBuzon);
    break;

case R.id.imgvUsuarioAdmin_admin_menu:

    final AlertDialog.Builder mbuilder = new AlertDialog.Builder(admin_menu.this);
    final AlertDialog dialog;

    View view = getLayoutInflater().inflate(R.layout.perfiladmin, null);

    CircularImageView imgvPerfil = (CircularImageView) view.findViewById(R.id.imgvUsuario_PerfilAdmin);

    final TextView txtvNombre = (TextView) view.findViewById(R.id.txtvNombre_PerfilAdmin);
    final TextView txtvApellido = (TextView) view.findViewById(R.id.txtvApellido_PerfilAdmin);
    final TextView txtvRegistro = (TextView) view.findViewById(R.id.txtvRegistro_PerfilAdmin);
    TextView txtvNANombre = (TextView) view.findViewById(R.id.txtvNoActionNombre_PerfilAdmin);
    TextView txtvNARegistro = (TextView) view.findViewById(R.id.txtvNoActionRegistro_PerfilAdmin);

    TextView txtvCerrarSesion = (TextView) view.findViewById(R.id.txtvCerrarSesion_PerfilAdmin);

    TextView txtvActulizarContra = (TextView) view.findViewById(R.id.txtvActualizarContra_PerfilAdmin);

    CardView btnCerrarSesion = (CardView) view.findViewById(R.id.cardvCerrarSesion_PerfilAdmin);
    CardView btnActualizarContra = (CardView) view.findViewById(R.id.cardvActualizarContra_PerfilAdmin);

    Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
    Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
    Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");


    txtvCerrarSesion.setTypeface(Condensed);
    txtvActulizarContra.setTypeface(Condensed);

    txtvNANombre.setTypeface(Regular);
    txtvNARegistro.setTypeface(Regular);

    txtvNombre.setTypeface(Light);
    txtvApellido.setTypeface(Light);
    txtvRegistro.setTypeface(Light);

    txtvNombre.setText("Nombre");
    txtvApellido.setText("Apellido");
    txtvRegistro.setText("0000");
    Glide.with(admin_menu.this).load("http://thegymlife.online/php/fotos/imagenesAdmin/"+Login.Registro+".jpg")
            .apply(RequestOptions.skipMemoryCacheOf(true))
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvPerfil);

    String url = "http://thegymlife.online/php/admin/Administrador_Visualizar_perfil.php?registro="+Login.Registro;
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
                                String nombre = response.getString("Administrador_Nombre");
                                String apellido = response.getString("Administrador_Apellido");
                                switch(valor) {
                                    case "OK":
                                        txtvNombre.setText(nombre);
                                        txtvApellido.setText(apellido);
                                        txtvRegistro.setText(""+Login.Registro);
                                        break;
                                    case "ERROR":
                                        Toast.makeText(admin_menu.this,"Error Conexion",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(admin_menu.this,"Error php",Toast.LENGTH_SHORT).show();
                }
            });
    RequestQueue x = Volley.newRequestQueue(admin_menu.this);
    x.add(peticion);



    btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(admin_menu.this,Login.class);

            SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("usuario","nada");
            editor.putString("contrasena","nada");
            editor.putString("cuenta","nada");
            editor.commit();

            startActivity(intent);
            finish();
        }
    });
    btnActualizarContra.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(admin_menu.this,ActualizarContrasena.class);
            String cuenta = "Administrador";
            intent.putExtra("CUENTA", cuenta);
            intent.putExtra("REGISTRO",Login.Registro);
            startActivity(intent);

        }
    });

    mbuilder.setView(view);
    dialog = mbuilder.create();
    dialog.show();


    break;
default:
    break;
}

}
    public void onBackPressed(){
        moveTaskToBack(true);
    }

}
