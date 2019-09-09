package com.example.carloz.gymapp;

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

public class AgregarNutriologo extends AppCompatActivity {

    EditText etxtNombre, etxtApellido, etxtTelefono;
    Button btnAgregarUsuario;
    RadioButton rdbtnMatutino,rdbtnVespertino, rdbtnSi,rdbtnNo;
    String stringCuenta,stringHorario, stringFoto, registro;
    TextView txtvNAHorario,txtvAgregar;
    String nombre, apellido;

    //FirebaseAuth auth;
    //DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nutriologo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etxtNombre = (EditText) findViewById(R.id.etxtNombre_AgregarNutriologo);
        etxtApellido = (EditText) findViewById(R.id.etxtApellidos_AgregarNutriologo);
        etxtTelefono = (EditText) findViewById(R.id.etxtTelefono_AgregarNutriologo);

        btnAgregarUsuario = (Button) findViewById(R.id.btnAgregarUsuario_AgregarNutriologo);

        rdbtnMatutino = (RadioButton) findViewById(R.id.radioButtonMatutino_AgregarNutriologo);
        rdbtnVespertino = (RadioButton) findViewById(R.id.radioButtonVespertino_AgregarNutriologo);
        rdbtnSi = (RadioButton) findViewById(R.id.radioButtonSi_AgregarNutriologo);
        rdbtnNo = (RadioButton) findViewById(R.id.radioButtonNo_AgregarNutriologo);

        txtvAgregar = (TextView) findViewById(R.id.txtvNoActionAgregar_AgregarNutriologo);
        txtvNAHorario = (TextView) findViewById(R.id.txtvNoActionHorario_AgregarNutriologo);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvAgregar.setTypeface(Thin);
        txtvNAHorario.setTypeface(Regular);
        btnAgregarUsuario.setTypeface(Condensed);

        rdbtnMatutino.setChecked(true);
        rdbtnSi.setChecked(true);

        //auth = FirebaseAuth.getInstance();

        recibirDatos();
        clickAgregarUsuario();

        Toast.makeText(AgregarNutriologo.this,"Rellene los campos",Toast.LENGTH_SHORT).show();

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
                    etxtTelefono.setError("Rellenar Télefono");
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
                Toast.makeText(AgregarNutriologo.this, "Usuario Agregado", Toast.LENGTH_SHORT).show();
                conexionBD();
            }
        });
    }

    private void conexionBD() {
        //Log.d("Error","inicioo");
        String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Nutriologo.php?nombre="+etxtNombre.getText().toString().trim()+"&apellido="+etxtApellido.getText().toString().trim()+
                "&horario="+stringHorario+"&telefono="+etxtTelefono.getText().toString().trim()+"&foto="+stringFoto;
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
                                            Conexion2BD();
                                            break;
                                        case "REGISTRO":
                                            Toast.makeText(AgregarNutriologo.this,"Nutriologo ya existe",Toast.LENGTH_SHORT).show();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(AgregarNutriologo.this,"Rellene todos los campos",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AgregarNutriologo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AgregarNutriologo.this);
        x.add(peticion);

    }

    private void Conexion2BD(){
        //Log.d("Error","inicioo");
        String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Nutriologo_Visualizar.php?telefono="+etxtTelefono.getText().toString();
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
                                            Intent intent = new Intent(AgregarNutriologo.this, AgregarClienteInfo.class);
                                            intent.putExtra("TELEFONO", etxtTelefono.getText().toString());
                                            intent.putExtra("CUENTA", "Nutriologo");
                                            intent.putExtra("FOTO",stringFoto);
                                            registro = response.getString("Nutriologo_Registro");

                                            //IngresarUsuarioFirebase();
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(AgregarNutriologo.this,"Rellene todos los campos",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AgregarNutriologo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AgregarNutriologo.this);
        x.add(peticion);

    }
/*
    private void IngresarUsuarioFirebase() {
        String txt_username = etxtNombre.getText().toString();
        String txt_email = registro+"@gymlife.com";
        String txt_password = "GymLife";
        String txt_telefono = etxtTelefono.getText().toString();

        registrar(txt_username,txt_email,txt_password,txt_telefono);
    }

    private void registrar(final String usuario, String email, final String password, final String telefono) {
        auth.createUserWithEmailAndPassword(email, password)
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
                                        Toast.makeText(AgregarNutriologo.this, "Firebase Usuario Creado", Toast.LENGTH_SHORT).show();
                                        /*Intent intent = new Intent(AgregarCliente.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(AgregarNutriologo.this, "No te puedes registrar con esos datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

}
