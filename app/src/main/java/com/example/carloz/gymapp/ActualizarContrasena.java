package com.example.carloz.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class ActualizarContrasena extends AppCompatActivity {

    EditText etxtContraseña, etxtConfirmarContra;
    Button  btnConfirmar;
    TextView txtvTitulo, ContraCaracteres, Contranumero;
    String stringCuenta, contra1,contra2,TipodeCuenta;
    int num1=0,num2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_contrasena);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etxtContraseña = (EditText) findViewById(R.id.etxtContraseña_ActualizarContrasena);
        etxtConfirmarContra = (EditText) findViewById(R.id.etxtContraseñaConfirm_ActualizarContrasena);
        btnConfirmar = (Button) findViewById(R.id.btnContinuar_ActualizarContrasena);
        ContraCaracteres = (TextView) findViewById(R.id.txtvCaracteres_ActualizarContraCliente);
        Contranumero = (TextView) findViewById(R.id.txtvNumero_ActualizarContraCliente);
        txtvTitulo = (TextView) findViewById(R.id.txtvNoActionTitulo_ActualizarContrasena);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvTitulo.setTypeface(Thin);
        btnConfirmar.setTypeface(Condensed);


        obtenerdatos();
        etxtContraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass = etxtContraseña.getText().toString();
                validarContra(pass);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        clickbtnContinuar();


    }

    private void validarContra(String contra) {
        Pattern numeros = Pattern.compile("[0-9]");
        if (!numeros.matcher(contra).find()){
            Contranumero.setTextColor(Color.RED);
            num1 = 0;
        } else {
            num1 = 1;
            Contranumero.setTextColor(Color.GREEN);
        }

        if (contra.length() < 6){
            ContraCaracteres.setTextColor(Color.RED);
            num1 = 0;
        }else {

            ContraCaracteres.setTextColor(Color.GREEN);
            num2 = 1;
        }
    }

    private void obtenerdatos() {
        Bundle bundle ;
        bundle = getIntent().getExtras();
        TipodeCuenta = (String) bundle.get("CUENTA");
        stringCuenta =(String) bundle.get("REGISTRO");
    }

    private void camposllenosBDCliente() {
        contra1 = etxtContraseña.getText().toString().trim();
        contra2 = etxtConfirmarContra.getText().toString().trim();

        if(contra1.isEmpty() && contra2.isEmpty()){
            Toast.makeText(ActualizarContrasena.this,"Rellene los campos",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!contra1.contentEquals(contra2)){
            Toast.makeText(ActualizarContrasena.this,"Las contraseñas no son iguales",Toast.LENGTH_SHORT).show();
            return;
        }
        int num = num1 + num2;
        if (num <= 1 ){
            Toast.makeText(ActualizarContrasena.this, "Contraseña no valida", Toast.LENGTH_SHORT).show();
            return;
        }
        conexionBDCliente();
    }
    private void camposllenosBDNutriologo() {
        contra1 = etxtContraseña.getText().toString().trim();
        contra2 = etxtConfirmarContra.getText().toString().trim();
        if(contra1.isEmpty() && contra2.isEmpty()){
            Toast.makeText(ActualizarContrasena.this,"Rellene los campos",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!contra1.contentEquals(contra2)){
            Toast.makeText(ActualizarContrasena.this,"Las contraseñas no son iguales",Toast.LENGTH_SHORT).show();
            return;
        }
        int num = num1 + num2;
        if (num <= 1 ){
            Toast.makeText(ActualizarContrasena.this, "Contraseña no valida", Toast.LENGTH_SHORT).show();
            return;
        }
        conexionBDNutriologo();
    }

    private void camposllenosBDInstructor() {
        contra1 = etxtContraseña.getText().toString().trim();
        contra2 = etxtConfirmarContra.getText().toString().trim();
        if(contra1.isEmpty() && contra2.isEmpty()){
            Toast.makeText(ActualizarContrasena.this,"Rellene los campos",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!contra1.contentEquals(contra2)){
            Toast.makeText(ActualizarContrasena.this,"Las contraseñas no son iguales",Toast.LENGTH_SHORT).show();
            return;
        }
        int num = num1 + num2;
        if (num <= 1 ){
            Toast.makeText(ActualizarContrasena.this, "Contraseña no valida", Toast.LENGTH_SHORT).show();
            return;
        }
        conexionBDInstructor();
    }

    private void clickbtnContinuar() {
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(TipodeCuenta){
                    case "Cliente":
                        camposllenosBDCliente();
                        break;
                    case "Instructor":
                        camposllenosBDInstructor();
                        break;
                    case "Nutriólogo":
                        camposllenosBDNutriologo();
                        break;
                    case "Administrador":
                        camposllenosBDAdministrador();
                        break;
                    case "ModificarCliente":
                        camposllenosBDCliente();
                        break;
                    case "ModificarInstructor":
                        camposllenosBDInstructor();
                        break;
                    case "ModificarNutriologo":
                        camposllenosBDNutriologo();
                        break;
                    case "contraInstructor":
                        camposllenosBDInstructor();
                        break;
                    case "contraNutriologo":
                        camposllenosBDNutriologo();
                        break;
                    case "contraCliente":
                        camposllenosBDCliente();
                        break;
                    }

                }
        });

    }


    private void camposllenosBDAdministrador() {
        contra1 = etxtContraseña.getText().toString().trim();
        contra2 = etxtConfirmarContra.getText().toString().trim();
        if(contra1.isEmpty() && contra2.isEmpty()){
            Toast.makeText(ActualizarContrasena.this,"Rellene los campos",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!contra1.contentEquals(contra2)){
            Toast.makeText(ActualizarContrasena.this,"Las contraseñas no son iguales",Toast.LENGTH_SHORT).show();
            return;
        }
        int num = num1 + num2;
        if (num <= 1 ){
            Toast.makeText(ActualizarContrasena.this, "Contraseña no valida", Toast.LENGTH_SHORT).show();
            return;
        }
        conexionBDAdministrador();
    }

    private void conexionBDAdministrador() {
        String url = "http://thegymlife.online/php/admin/Administrador_Modificar_Contrasena.php?registro="+stringCuenta+"&contrasena="+ etxtContraseña.getText().toString();
        final Intent intentCliente = new Intent(ActualizarContrasena.this,ClientePerfil.class);
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
                                            Toast.makeText(ActualizarContrasena.this,"Contraseña Actualizada",Toast.LENGTH_SHORT).show();
                                            finish();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(ActualizarContrasena.this,"Contraseña no actualizada",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ActualizarContrasena.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ActualizarContrasena.this);
        x.add(peticion);
    }

    private void conexionBDCliente() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Modificar_Contrasena.php?registro="+stringCuenta+"&contrasena="+ etxtContraseña.getText().toString();
        final Intent intentCliente = new Intent(ActualizarContrasena.this,ClientePerfil.class);
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
                                            if (TipodeCuenta.equals("contraCliente")){
                                                SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);

                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("usuario",stringCuenta);
                                                editor.putString("cuenta","cliente");
                                                editor.commit();

                                                startActivity(new Intent(ActualizarContrasena.this,ClienteMenu.class));
                                            }
                                            Toast.makeText(ActualizarContrasena.this,"Contraseña Actualizada",Toast.LENGTH_SHORT).show();

                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(ActualizarContrasena.this,"Contraseña no actualizada",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ActualizarContrasena.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ActualizarContrasena.this);
        x.add(peticion);

    }

    private void conexionBDNutriologo() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Modificar_Contrasena.php?registro="+stringCuenta+"&contrasena="+ etxtContraseña.getText().toString();
        //final Intent  intentNutriologo= new Intent(ActualizarContrasena.this,NutriologoPerfil.class);
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
                                            if (TipodeCuenta.equals("contraNutriologo")){
                                            SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);

                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("usuario",stringCuenta);
                                            editor.putString("cuenta","nutriologo");
                                            editor.commit();

                                            startActivity(new Intent(ActualizarContrasena.this,NutriologoPerfil.class));
                                        }
                                            Toast.makeText(ActualizarContrasena.this,"Contraseña Actualizada",Toast.LENGTH_SHORT).show();
                                            //startActivity(intentNutriologo);
                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(ActualizarContrasena.this,"Contraseña no actualizada",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ActualizarContrasena.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ActualizarContrasena.this);
        x.add(peticion);

    }

    private void conexionBDInstructor() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Modificar_Contrasena.php?registro="+stringCuenta+"&contrasena="+ etxtContraseña.getText().toString();
        //final Intent  intentNutriologo= new Intent(ActualizarContrasena.this,InstructorPerfil.class);
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
                                            if (TipodeCuenta.equals("contraInstructor")){
                                                SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);

                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("usuario",stringCuenta);
                                                editor.putString("cuenta","instructor");
                                                editor.commit();

                                                startActivity(new Intent(ActualizarContrasena.this,InstructorPerfil.class));
                                            }
                                            Toast.makeText(ActualizarContrasena.this,"Contraseña Actualizada",Toast.LENGTH_SHORT).show();
                                            //startActivity(intentNutriologo);
                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(ActualizarContrasena.this,"Contraseña no actualizada",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ActualizarContrasena.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ActualizarContrasena.this);
        x.add(peticion);

    }

    public void onBackPressed(){

        switch (TipodeCuenta){
            case "Cliente":
                finish();
                break;
            case "Administrador":
                finish();
                break;
            case "Instructor":
                finish();
                break;
            case "Nutriólogo":
                finish();
                break;
            case "ModificarCliente":
                finish();
                break;
            case "ModificarInstructor":
                finish();
                break;
            case "ModificarNutriologo":
                finish();
                break;
            case "contraInstructor":
                moveTaskToBack(true);
                break;
            case "contraNutriologo":
                moveTaskToBack(true);
                break;
            case "contraCliente":
                moveTaskToBack(true);
                break;
            default:
                moveTaskToBack(true);
                break;
        }
    }



}
