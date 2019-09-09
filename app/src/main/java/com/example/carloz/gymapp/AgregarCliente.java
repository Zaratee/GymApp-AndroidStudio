package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class AgregarCliente extends AppCompatActivity {

    EditText etxtNombre, etxtApellido, etxtRFID, etxtTelefono;
    Button  btnAgregarUsuario;
    RadioButton rdbtnMatutino, rdbtnVespertino, rdbtnSi, rdbtnNo;
    String stringCuenta, stringHorario, stringNutriologo, stringFoto, registro;
    TextView txtvAgregar,txtvNAAsignar,txtvNAHorario;
    CheckBox Nutriologo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etxtNombre = (EditText) findViewById(R.id.etxtNombre_AgregarCliente);
        etxtApellido = (EditText) findViewById(R.id.etxtApellidos_AgregarCliente);
        etxtRFID = (EditText) findViewById(R.id.etxtRFDI_AgregarCliente);
        etxtTelefono = (EditText) findViewById(R.id.etxtTelefono_AgregarCliente);

        Nutriologo = (CheckBox) findViewById(R.id.chkboxNutriologo_AgregarCliente);

        btnAgregarUsuario = (Button) findViewById(R.id.btnAgregarUsuario_AgregarCliente);


        rdbtnMatutino = (RadioButton) findViewById(R.id.radioButtonMatutino_AgregarCliente);
        rdbtnVespertino = (RadioButton) findViewById(R.id.radioButtonVespertino_AgregarCliente);
        rdbtnSi = (RadioButton) findViewById(R.id.radioButtonSi_AgregarCliente);
        rdbtnNo = (RadioButton) findViewById(R.id.radioButtonNo_AgregarCliente);

        txtvAgregar = (TextView) findViewById(R.id.txtvNoActionAgregar_AgregarCliente);
        txtvNAAsignar = (TextView) findViewById(R.id.txtvNoActionAsignar_AgregarCliente);
        txtvNAHorario = (TextView) findViewById(R.id.txtvNoActionHorario_AgregarCliente);




        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvAgregar.setTypeface(Thin);
        txtvNAHorario.setTypeface(Regular);
        txtvNAAsignar.setTypeface(Regular);
        btnAgregarUsuario.setTypeface(Condensed);


        rdbtnMatutino.setChecked(true);
        rdbtnSi.setChecked(true);
        stringCuenta = Login.Registro;
        clickAgregarUsuario();
    }

    private void clickAgregarUsuario() {
        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etxtNombre.getText().toString().trim().equalsIgnoreCase("")){
                    etxtNombre.setError("Rellenar Nombre");
                }
                if(etxtApellido.getText().toString().trim().equalsIgnoreCase("")){
                    etxtApellido.setError("Rellenar Apellido");
                }
                if(etxtTelefono.getText().toString().trim().equalsIgnoreCase("")){
                    etxtTelefono.setError("Rellenar Telefono");
                }
                if(etxtRFID.getText().toString().trim().equalsIgnoreCase("")){
                    etxtRFID.setError("Rellenar RFID");
                }

                if (rdbtnMatutino.isChecked()) {
                    // txtvHorario.setText("0");
                    stringHorario = "0";
                }
                if (rdbtnVespertino.isChecked()) {
                    // txtvHorario.setText("1");
                    stringHorario = "1";
                }
                if (Nutriologo.isChecked()){
                    stringNutriologo="1";
                }else{
                    stringNutriologo="0";
                }
                if (rdbtnSi.isChecked()){
                    stringFoto = "1";
                }
                if (rdbtnNo.isChecked()){
                    stringFoto = "0";
                }
                Toast.makeText(AgregarCliente.this, "Usuario Agregado", Toast.LENGTH_SHORT).show();
                conexionBD();
            }
        });
    }

    private void conexionBD() {
        //Log.d("Error","inicioo");

        String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Cliente.php?nombre="+etxtNombre.getText().toString().trim() + "&apellido="
                + etxtApellido.getText().toString().trim() + "&horario=" + stringHorario.trim() +"&RFID="+etxtRFID.getText().toString().trim()+"&telefono="
                +etxtTelefono.getText().toString().trim() +"&nutriologo="+stringNutriologo+"&foto="+stringFoto;
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
                                            Conexion2BD();
                                            break;
                                        case "REGISTRO":
                                            Toast.makeText(AgregarCliente.this,"Cliente ya existe",Toast.LENGTH_SHORT).show();
                                            break;
                                        case "HORARIO":
                                            Toast.makeText(AgregarCliente.this,"Error: Debe agregar primero un Instructor y un Nutriologo",Toast.LENGTH_SHORT).show();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(AgregarCliente.this,"Rellene todos los campos",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AgregarCliente.this, "Error php", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AgregarCliente.this);
        x.add(peticion);

    }

    private void Conexion2BD() {
        String url = "http://thegymlife.online/php/admin/Usuario_Ingresar_Cliente_Visualizar.php?telefono="+etxtTelefono.getText().toString();
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
                                            Intent intent = new Intent(AgregarCliente.this, AgregarClienteInfo.class);
                                            intent.putExtra("TELEFONO", etxtTelefono.getText().toString());
                                            intent.putExtra("CUENTA", "Cliente");
                                            intent.putExtra("FOTO",stringFoto);
                                            registro = response.getString("Cliente_Registro");


                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(AgregarCliente.this,"Rellene todos los campos",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AgregarCliente.this, "Error php", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AgregarCliente.this);
        x.add(peticion);

    }




}
