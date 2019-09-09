package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class InstructorCrearQueja extends AppCompatActivity {

    Button btnEnviar;
    TextView txtvTitulo;
    String stringCuenta;
    EditText etxtQueja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_crear_queja);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnEnviar = (Button) findViewById(R.id.btnEnviar_InstructorCrearQueja);
        txtvTitulo = (TextView) findViewById(R.id.txtvTitulo_InstructorCrearQueja);
        etxtQueja = (EditText) findViewById(R.id.etxtQueja_InstructorCrearQueja);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        stringCuenta=Login.Registro;

        txtvTitulo.setTypeface(Thin);
        btnEnviar.setTypeface(Condensed);

        clickbtnEnviar();
    }


    private void clickbtnEnviar() {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    camposLlenos();
                }
            }
        });
    }
    private void camposLlenos() {
        String queja = etxtQueja.getText().toString();
        if (queja.isEmpty())
            etxtQueja.setError("Relllenar Queja");
        else
            conexionBD();
    }
    private void conexionBD() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Ingresar_Queja.php?registro="+stringCuenta+"&queja="+ etxtQueja.getText().toString();
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

                                            Toast.makeText(InstructorCrearQueja.this,"Queja enviada",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(InstructorCrearQueja.this,InstructorBuzonQuejas.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(InstructorCrearQueja.this,"Queja no enviada",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(InstructorCrearQueja.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorCrearQueja.this);
        x.add(peticion);

    }


}
