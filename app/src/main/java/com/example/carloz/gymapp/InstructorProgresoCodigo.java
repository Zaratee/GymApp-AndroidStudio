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

public class InstructorProgresoCodigo extends AppCompatActivity {

    EditText etxtCodigo;
    Button btnContinuar;
    String stringRegistro;
    TextView txtvTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_progreso_codigo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etxtCodigo = findViewById(R.id.etxtCodigo_InstructorProgresoCodigo);
        btnContinuar = findViewById(R.id.btnEnviar_InstructorProgresoCodigo);
        txtvTitulo = findViewById(R.id.txtvTitulo_InstructorProgresoCodigo);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvTitulo.setTypeface(Thin);

        stringRegistro = getIntent().getStringExtra("REGISTRO");

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDCodigo();
            }
        });
    }

    private void conexionBDCodigo() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Verificar_Codigo_Cliente.php?registro="+stringRegistro+"&codigo="+etxtCodigo.getText().toString().trim();
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
                                    String valor = response.getString("Codigo");
                                    switch(valor) {
                                        case "OK":
                                            Intent intent = new Intent(InstructorProgresoCodigo.this,InstructorClienteRelizSele.class);
                                            intent.putExtra("REGISTRO",stringRegistro);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(InstructorProgresoCodigo.this,"Codigo incorrecto",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(InstructorProgresoCodigo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorProgresoCodigo.this);
        x.add(peticion);

    }
}
