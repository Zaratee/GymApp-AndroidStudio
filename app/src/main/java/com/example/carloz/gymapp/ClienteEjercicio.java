package com.example.carloz.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.carloz.gymapp.Model.Receptor;
import com.example.carloz.gymapp.Model.User;
import com.example.carloz.gymapp.adaptador.UserAdapter;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteEjercicio extends AppCompatActivity {

    Button btnChat,btnAyuda, btnSig, btnInicio, btnParar;
    TextView txtvNombre, txtvRepeticiones, txtvSeries, txtvAparato, txtvPeso, txtvRepReal, txtvSeriesReal,txtvDescanso;
    String bdIdRutina, bdPolea, bdTDescanso, registroInstructor, rutina;
    Chronometer cronome,cronoDescanso;
    Integer serie=1, repe =1;
    public static String idEjercicio="0000", diaSemana ="0000";
    private boolean isResume;
    Handler handler;
    Handler handler2;
    long tMillils, tStart, tBuff, tUpdate = 0L;
    int sec, min, miliSec;

    long t2Millils, t2Start, t2Buff, t2Update = 0L;
    int sec2, min2, miliSec2, reloj;

    private UserAdapter userAdapter;
    private List<User> mUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_ejercicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnChat = (Button) findViewById(R.id.btnChat_ClienteEjercicio);
        txtvNombre = (TextView) findViewById(R.id.txtvNombre_ClienteEjercicio);
        txtvRepeticiones = (TextView) findViewById(R.id.txtvRepeticiones_ClienteEjercicio);
        txtvSeries = (TextView) findViewById(R.id.txtvSeries_ClienteEjercicio);
        txtvAparato = (TextView) findViewById(R.id.txtvAparato_ClienteEjercicio);
        txtvPeso = (TextView) findViewById(R.id.txtvPeso_ClienteEjercicio);
        cronome = (Chronometer) findViewById(R.id.chrono_ClienteEjercicio);
        cronoDescanso = (Chronometer) findViewById(R.id.chrono2_ClienteEjercicio);

        txtvRepReal = (TextView) findViewById(R.id.txtvRepeticionesReal_ClienteEjercicio);
        txtvSeriesReal = (TextView) findViewById(R.id.txtvSeriesReal_ClienteEjercicio);

        btnSig = (Button) findViewById(R.id.btnPlay_ClienteEjercicio);
        btnInicio = (Button) findViewById(R.id.btnInicio_ClienteEjercicio);
        btnParar = (Button) findViewById(R.id.btnSaltar_ClienteEjercicio);
        btnAyuda = (Button) findViewById(R.id.btnAyuda_ClienteEjercicio);
        txtvDescanso = (TextView) findViewById(R.id.txtvTiempoDescanso_ClienteEjercicio);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");

        handler = new Handler();
        handler2 = new Handler();
        btnInicio.setTypeface(Thin);
        btnSig.setTypeface(Thin);
        btnSig.setEnabled(false);
        btnParar.setVisibility(View.GONE);

        mUsers = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);

        registroInstructor = preferences.getString("clienteRegInstr","nada");
        //registroInstructor = getIntent().getStringExtra("REGISTROINSTRU");
        rutina = getIntent().getStringExtra("IDEJER");

        //eºToast.makeText(this, "Rutina :"+ rutina+" "+Login.Registro, Toast.LENGTH_SHORT).show();

        conexionBDEjeActual();
        

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSig.setEnabled(true);
                if (!isResume){
                    btnInicio.setText("PAUSA");
                    btnSig.setEnabled(true);
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    cronome.start();
                    isResume = true;
                    btnParar.setVisibility(View.GONE);

                }else {
                    btnInicio.setText("INICIO");
                    btnSig.setEnabled(false);
                    tBuff += tMillils;
                    handler.removeCallbacks(runnable);
                    cronome.stop();
                    isResume = false;
                    btnParar.setVisibility(View.GONE);
                }
            }
        });

        btnParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume){
                    tMillils = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec = 0;
                    min = 0;
                    miliSec = 0;
                    cronome.setText("00:00:00");
                }
            }
        });

        btnSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtvRepReal.setText(""+repe*serie);
                txtvSeriesReal.setText(""+serie);
                serie ++;
                comparacion();



            }
        });
        
        clickbtnChat();
        clickbtnAyuda();


    }

    private void comparacion() {
        if (txtvSeriesReal.getText().toString().equals(""+txtvSeries.getText().toString())){
            conexionBDRealizado();
            tBuff += tMillils;
            handler.removeCallbacks(runnable);
            cronome.stop();
            btnSig.setEnabled(false);
            btnParar.setEnabled(false);
            isResume = false;
            cronome.setVisibility(View.INVISIBLE);
            txtvNombre.setText("Tiempo de descanso");
            txtvAparato.setText("");
            txtvDescanso.setVisibility(View.VISIBLE);

            new CountDownTimer(50000, 1000) {

                public void onTick(long millisUntilFinished) {
                    txtvDescanso.setText(""+millisUntilFinished / 1000);
                    btnInicio.setEnabled(false);
                    btnParar.setEnabled(false);
                }
                public void onFinish() {
                    btnInicio.setEnabled(true);
                    btnParar.setEnabled(true);
                    txtvSeriesReal.setText("0");
                    txtvRepReal.setText("0");
                    serie=1;
                    repe =1;
                    txtvDescanso.setVisibility(View.INVISIBLE);
                    cronome.setVisibility(View.VISIBLE);
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    cronome.start();
                    isResume = true;
                    conexionBDRealizado();
                    conexionBDEjeActual();

                }
            }.start();

        }

    }



    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
                tMillils = SystemClock.uptimeMillis() - tStart;
                tUpdate = tBuff + tMillils;
                sec = (int) (tUpdate/1000);
                min = sec/60;
                sec = sec%60;
                miliSec = (int) (tUpdate%100);
                cronome.setText(String.format("%02d",min)+":"
                        +String.format("%02d",sec)+":"+String.format("%02d",miliSec));
                handler.postDelayed(this,60);


        }
    };

    private void conexionBDRealizado() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Realizado_Ejercicio.php?registro="+Login.Registro+
                "&rutina="+bdIdRutina+"&polea="+bdPolea;
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
                                            Toast.makeText(ClienteEjercicio.this, "Realizado", Toast.LENGTH_SHORT).show();

                                            break;
                                        case "RUTINA":
                                            Toast.makeText(ClienteEjercicio.this, "Rutina del dia terminada", Toast.LENGTH_SHORT).show();
                                            finish();
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
                        Toast.makeText(ClienteEjercicio.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteEjercicio.this);
        x.add(peticion);

    }

    private void conexionBDEjeActual() {

        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Ejercicio_Rutina.php?registro="+Login.Registro+"&rutina="+rutina;

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
                                            bdIdRutina = response.getString("id_Rutina");
                                            String bdNombre = response.getString("Ejercicio_Nombre");
                                            String bdAparato = response.getString("Ejercicio_Aparato");
                                            String bdRep = response.getString("Numero_Repeticiones");
                                            String bdSeri = response.getString("Numero_Series");
                                            bdTDescanso = response.getString("Ejercicio_Descanso");
                                            bdPolea = response.getString("Ejercicio_Polea");
                                            String bdPeso = response.getString("Numero_Peso");
                                            diaSemana = response.getString("Dia_Semana");
                                            idEjercicio = response.getString("Ejercicio_id");


                                            txtvNombre.setText(bdNombre);
                                            txtvSeries.setText(bdSeri);
                                            txtvRepeticiones.setText(bdRep);
                                            txtvAparato.setText(bdAparato);
                                            txtvPeso.setText(bdPeso);
                                            repe = Integer.parseInt(txtvRepeticiones.getText().toString());
                                            rutina = "0";

                                            break;
                                        case "RUTINA":
                                            btnSig.setEnabled(false);
                                            btnInicio.setEnabled(false);
                                            Toast.makeText(ClienteEjercicio.this, "Rutina del dia terminada", Toast.LENGTH_SHORT).show();
                                            cronoDescanso.setVisibility(View.VISIBLE);
                                            cronome.setVisibility(View.INVISIBLE);
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
                        Toast.makeText(ClienteEjercicio.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteEjercicio.this);
        x.add(peticion);

    }

    private void clickbtnAyuda() {
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteEjercicio.this,ClienteEjercicioAyuda.class);
                intent.putExtra("REGISTROINSTRU",registroInstructor);
                startActivity(intent);
            }
        });
    }

    private void clickbtnChat() {
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (registroInstructor.equals("0")) {
                    Toast.makeText(ClienteEjercicio.this, "Instructor no asignado", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.orderByChild("username").equalTo(registroInstructor).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot child : dataSnapshot.getChildren())
                            {
                                Intent intent = new Intent(ClienteEjercicio.this,MessageActivity.class);
                                intent.putExtra("userid",child.getKey());
                                intent.putExtra("receptor",registroInstructor);
                                startActivity(intent);

                            }

                            // Toast.makeText(ClienteEjercicio.this, ""+dataSnapshot., Toast.LENGTH_SHORT).show();
                            //HashMap<String, String> hashMap = new HashMap<String ,String>(dataSnapshot.getValue());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }



            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ClienteEjercicio.this,ClienteMenu.class);
        startActivity(intent);
    }
}
