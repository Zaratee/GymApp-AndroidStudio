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

public class ActualizarContraCliente extends AppCompatActivity {

    EditText etxtContraseña, etxtConfirmarContraseña;
    Button btnContinuar;
    String stringCuenta, contra1,contra2;
    TextView txtvTitulo, ContraCaracteres, Contranumero;
    int num1=0,num2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_contra_cliente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnContinuar = (Button) findViewById(R.id.btnContinuar_ActualizarContraCliente);
        etxtContraseña = (EditText) findViewById(R.id.etxtContraseña_ActualizarContraCliente);
        etxtConfirmarContraseña = (EditText) findViewById(R.id.etxtContraseñaConfirm_ActualizarContraCliente);
        txtvTitulo = (TextView) findViewById(R.id.txtvNoActionActualizarContra_ActualizarContraCliente);
        ContraCaracteres = (TextView) findViewById(R.id.txtvCaracteres_ActualizarContraCliente);
        Contranumero = (TextView) findViewById(R.id.txtvNumero_ActualizarContraCliente);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvTitulo.setTypeface(Thin);
        btnContinuar.setTypeface(Condensed);
        stringCuenta = Login.Registro;


        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        String user = preferences.getString("usuario","nada");
        if (!user.equals("nada")){
            Login.Registro = user;
        }

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

        clickBtnContinuar();



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


    private void clickBtnContinuar(){

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra1 = etxtContraseña.getText().toString().trim();
                contra2 = etxtConfirmarContraseña.getText().toString().trim();
                int num = num1 + num2;
                if(contra1.isEmpty() && contra2.isEmpty()){
                    Toast.makeText(ActualizarContraCliente.this,"Rellene los campos",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!contra1.contentEquals(contra2)){
                    Toast.makeText(ActualizarContraCliente.this,"Las contraseñas no son iguales",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (num <= 1 ){
                    Toast.makeText(ActualizarContraCliente.this, "Contraseña no valida", Toast.LENGTH_SHORT).show();
                    return;
                }
                conexionBD();
            }
        });
    }



    private void conexionBD() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Modificar_Contrasena.php?registro="+stringCuenta+"&contrasena="+ etxtContraseña.getText().toString();
        final Intent iniciarCliente = new Intent(ActualizarContraCliente.this,EncuestaClienteNuevo.class);
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
                                            iniciarCliente.putExtra("REGISTRO",stringCuenta);
                                            startActivity(iniciarCliente);
                                            break;
                                        case "Error":
                                            Toast.makeText(ActualizarContraCliente.this,"Contraseña no actualizada",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ActualizarContraCliente.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ActualizarContraCliente.this);
        x.add(peticion);

    }
    public void onBackPressed() {
    }

}
