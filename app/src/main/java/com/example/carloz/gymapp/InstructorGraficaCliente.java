package com.example.carloz.gymapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class InstructorGraficaCliente extends AppCompatActivity {

     WebView Webv;
    String registro, ejercicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_grafica_cliente);
        Webv = (WebView) findViewById(R.id.WebView_InstructorGraficaCliente);
        WebSettings webSettings = Webv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        registro = getIntent().getStringExtra("REGISTRO");
        ejercicio = getIntent().getStringExtra("ID");
        Webv.loadUrl("http://thegymlife.online/php/cliente/graficas/Grafica_Repeticiones.php?registro="+registro+"&ejercicio="+ejercicio);


    }
}
