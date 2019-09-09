package com.example.carloz.gymapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.zolad.zoominimageview.ZoomInImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class InstructorRealizInfo extends AppCompatActivity {

    String realizadoID, bdEjeID;
    TextView txtvNombre, txtvFecha, txtvDia, txtvSeriesInst, txtvRepInst, txtvSeriesReal, txtvRepReal,
            txtvPesoReal, txtvPesoInst, txtvEtapa, txtvDificultad;
    ZoomInImageView imgvPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_realiz_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvNombre = (TextView) findViewById(R.id.txtvNombre_InstructorRealizInfo);
        txtvFecha = (TextView) findViewById(R.id.txtvFecha_InstructorRealizInfo);
        txtvDia = (TextView) findViewById(R.id.txtvDia_InstructorRealizInfo);
        txtvSeriesInst = (TextView) findViewById(R.id.txtvSeries_InstructorRealizInfo);
        txtvRepInst = (TextView) findViewById(R.id.txtvRepeticiones_InstructorRealizInfo);
        txtvSeriesReal = (TextView) findViewById(R.id.txtvSeriesReal_InstructorRealizInfo);
        txtvRepReal = (TextView) findViewById(R.id.txtvRepeticionesReal_InstructorRealizInfo);
        txtvPesoReal = (TextView) findViewById(R.id.txtvPesoReal_InstructorRealizInfo);
        txtvPesoInst = (TextView) findViewById(R.id.txtvPesoInstructor_InstructorRealizInfo);
        txtvEtapa = (TextView) findViewById(R.id.txtvEtapa_InstructorRealizInfo);
        txtvDificultad = (TextView) findViewById(R.id.txtvDificultad_InstructorRealizInfo);
        imgvPerfil = (ZoomInImageView) findViewById(R.id.imgvEjercicio_InstructorRealizInfo);

        realizadoID = getIntent().getStringExtra("IDREALIZADO");

        conexionBDInfo();


    }

    private void conexionBDInfo() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Visualizar_Realizado_Cliente.php?realizado="+realizadoID;
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
                                            bdEjeID = response.getString("Ejercicio_id");
                                            String bdNombre = response.getString("Ejercicio_Nombre");
                                            String bdFecha = response.getString("Fecha_Realizada");
                                            String bdDia = response.getString("Dia_Semana");
                                            String bdSeriesInst = response.getString("Numero_Series");
                                            String bdSeriesReal = response.getString("Series_Totales");
                                            String bdRepeInst = response.getString("Numero_Repeticiones");
                                            String bdRepeReal = response.getString("Repeticiones_Totales");
                                            String bdPesoInst = response.getString("Numero_Peso");
                                            String bdPesoReal = response.getString("Peso_Realizado");
                                            String bdEtapa = response.getString("Etapa");
                                            String bdDificultad = response.getString("Dificultad");

                                            txtvNombre.setText(bdNombre);
                                            txtvFecha.setText(bdFecha);
                                            switch (bdDia){
                                                case "1":
                                                    txtvDia.setText("Lunes");
                                                    break;
                                                case "2":
                                                    txtvDia.setText("Martes");
                                                    break;
                                                case "3":
                                                    txtvDia.setText("Miercoles");
                                                    break;
                                                case "4":
                                                    txtvDia.setText("Jueves");
                                                    break;
                                                case "5":
                                                    txtvDia.setText("Viernes");
                                                    break;
                                                case "6":
                                                    txtvDia.setText("Sábado");
                                                    break;
                                                case "7":
                                                    txtvDia.setText("Domingo");
                                                    break;
                                            }


                                            txtvSeriesInst.setText(bdSeriesInst);
                                            txtvSeriesReal.setText(bdSeriesReal);
                                            txtvRepInst.setText(bdRepeInst);
                                            txtvRepReal.setText(bdRepeReal);
                                            txtvPesoInst.setText(bdPesoInst);
                                            txtvPesoReal.setText(bdPesoReal);
                                            txtvEtapa.setText(bdEtapa);
                                            txtvDificultad.setText(bdDificultad);

                                            Glide.with(InstructorRealizInfo.this).load("http://thegymlife.online/php/fotos/imagenesEjercicio/"+bdEjeID+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                                                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvPerfil);


                                            break;

                                        case "Error":
                                            Toast.makeText(InstructorRealizInfo.this,"Error Conexion",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(InstructorRealizInfo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorRealizInfo.this);
        x.add(peticion);

    }
}
