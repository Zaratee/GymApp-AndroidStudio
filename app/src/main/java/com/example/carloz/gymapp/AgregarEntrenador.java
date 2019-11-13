package com.example.carloz.gymapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AgregarEntrenador extends AppCompatActivity {

    EditText etxtNombre, etxtApellido, etxtTelefono;
    Button btnAgregarUsuario;
    String stringCuenta, stringHorario, nombre, apellido, stringFoto,registro;
    RadioButton rdbtnMatutino,rdbtnVespertino, rdbtnSi, rdbtnNo;
    TextView txtvNAHorario,txtvAgregar;
    String registroBD="";
    int flag;

    //FirebaseAuth auth;
    //DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_entrenador);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etxtNombre = (EditText) findViewById(R.id.etxtNombre_AgregarEntrenador);
        etxtApellido = (EditText) findViewById(R.id.etxtApellidos_AgregarEntrenador);
        etxtTelefono = (EditText) findViewById(R.id.etxtTelefono_AgregarEntrenador);

        btnAgregarUsuario = (Button) findViewById(R.id.btnAgregarUsuario_AgregarEntrenador);

        rdbtnMatutino = (RadioButton) findViewById(R.id.radioButtonMatutino_AgregarEntrenador);
        rdbtnVespertino = (RadioButton) findViewById(R.id.radioButtonVespertino_AgregarEntrenador);
        rdbtnSi = (RadioButton) findViewById(R.id.radioButtonSi_AgregarEntrenador);
        rdbtnNo = (RadioButton) findViewById(R.id.radioButtonNo_AgregarEntrenador);

        txtvAgregar = (TextView) findViewById(R.id.txtvNoActionAgregar_AgregarEntrenador);
        txtvNAHorario = (TextView) findViewById(R.id.txtvNoActionHorario_AgregarEntrenador);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvAgregar.setTypeface(Thin);
        txtvNAHorario.setTypeface(Regular);
        btnAgregarUsuario.setTypeface(Condensed);

        rdbtnMatutino.setChecked(true);
        rdbtnSi.setChecked(true);

        Toast.makeText(AgregarEntrenador.this,"Rellene  todos los campos",Toast.LENGTH_SHORT).show();

        //auth = FirebaseAuth.getInstance();

        recibirDatos();
        clickAgregarUsuario();

    }



    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringCuenta =(String) bundle.get("REGISTRO");
    }



    private void clickAgregarUsuario(){
        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = etxtNombre.getText().toString().trim();
                apellido = etxtApellido.getText().toString().trim();

                if(etxtNombre.getText().toString().trim().equalsIgnoreCase("")){
                    etxtNombre.setError("Rellenar Nombre");
                }
                if(etxtApellido.getText().toString().trim().equalsIgnoreCase("")){
                    etxtApellido.setError("Rellenar Apellido");
                }
                if(etxtTelefono.getText().toString().trim().equalsIgnoreCase("")){
                    etxtTelefono.setError("Rellenar Telefono");
                }
                if(rdbtnMatutino.isChecked()){
                    // txtvHorario.setText("0");
                    stringHorario = "0";
                }
                if (rdbtnVespertino.isChecked()){
                    // txtvHorario.setText("1");
                    stringHorario = "1";
                }
                if (rdbtnSi.isChecked()){
                    stringFoto = "1";
                }
                if (rdbtnNo.isChecked()){
                    stringFoto = "0";
                }
                String sApellido = etxtApellido.getText().toString().trim();
                String sNombre = etxtNombre.getText().toString().trim();

                String sTelefono = etxtTelefono.getText().toString().trim();
                if (!sApellido.isEmpty() && !sNombre.isEmpty() && !sTelefono.isEmpty()){
                    if (sTelefono.length()<8 ){
                        Toast.makeText(AgregarEntrenador.this, "El teléfono tiene que ser de 8 a 10 digitos", Toast.LENGTH_SHORT).show();
                    }else {
                        conexionBD();
                    }
                }else {
                    Toast.makeText(AgregarEntrenador.this, "Rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void conexionBD() {
        //Log.d("Error","inicioo");
        String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Entrenador.php?registro="+registroBD+"&nombre="+etxtNombre.getText().toString().trim()
                +"&apellido="+etxtApellido.getText().toString().trim()+"&telefono="+etxtTelefono.getText().toString().trim()+"&horario="+stringHorario+"&foto="+stringFoto;
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
                                    if (flag==2){
                                        valor="OK";
                                    }
                                    switch(valor) {
                                        case "OK":
                                            Intent intent = new Intent(AgregarEntrenador.this, AgregarClienteInfo.class);
                                            intent.putExtra("TELEFONO", etxtTelefono.getText().toString());
                                            intent.putExtra("CUENTA", "Instructor");
                                            intent.putExtra("FOTO",stringFoto);
                                            startActivity(intent);
                                            break;
                                        case "USUARIO":
                                            verificarUsuarioEliminado();
                                            break;
                                        case "REGISTRO":
                                            Toast.makeText(AgregarEntrenador.this,"Entrenador ya existe",Toast.LENGTH_SHORT).show();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(AgregarEntrenador.this,"Rellene todos los campos",Toast.LENGTH_SHORT).show();
                                            break;
                                        case "PRE-REGISTRO":
                                            verificarUsuarioEliminado();
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
                        Toast.makeText(AgregarEntrenador.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AgregarEntrenador.this);
        x.add(peticion);

    }
    private void verificarUsuarioEliminado(){
        String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Visualizar_Registrado.php?telefono="+etxtTelefono.getText().toString();
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
                                    final String nombreBD = response.getString("Usuario_Nombre");
                                    final String apellidoBD = response.getString("Usuario_Apellido");
                                    registroBD = response.getString("Usuario_Registro");
                                    final String telefonoBD = response.getString("Usuario_Telefono");
                                    switch (valor) {
                                        case "OK":
                                            etxtNombre.setText(nombreBD);
                                            etxtApellido.setText(apellidoBD);
                                            etxtTelefono.setText(telefonoBD);
                                            etxtTelefono.setEnabled(false);

                                            verificarUsuarioAceptar(etxtTelefono.getText().toString());
                                            AlertDialog.Builder alertbox = new AlertDialog.Builder(AgregarEntrenador.this);
                                            alertbox.setMessage("Entrenador eliminado previamente con la siguiente información: \n\n" +
                                                    "Nombre: "+nombreBD +" "+apellidoBD+"\nRegistro: "+registroBD+"\nTeléfono: "+telefonoBD);
                                            alertbox.setTitle("Entrenador eliminado");
                                            flag=2;

                                            alertbox.setPositiveButton("Recibido",
                                                    new DialogInterface.OnClickListener() {

                                                        public void onClick(DialogInterface arg0,
                                                                            int arg1) {
                                                            etxtNombre.setText(nombreBD);
                                                            etxtApellido.setText(apellidoBD);
                                                            etxtTelefono.setText(telefonoBD);
                                                            etxtTelefono.setEnabled(false);
                                                        }
                                                    });

                                            alertbox.show();

                                            break;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgregarEntrenador.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AgregarEntrenador.this);
        x.add(peticion);

    }

    private void verificarUsuarioAceptar(String telefono) {
        String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Visualizar_Registrado_Aceptar.php?telefono="+telefono;
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
                                    switch (valor) {
                                        case "OK":
                                            Toast.makeText(AgregarEntrenador.this, "Usuario en estado Pre-Registro ", Toast.LENGTH_SHORT).show();
                                            break;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgregarEntrenador.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AgregarEntrenador.this);
        x.add(peticion);

    }





}
