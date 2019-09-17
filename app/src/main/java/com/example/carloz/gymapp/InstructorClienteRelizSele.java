package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class InstructorClienteRelizSele extends AppCompatActivity {

    CardView btnGrafica, btnLista;
    TextView txtvGrafica, txtvLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_cliente_reliz_sele);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnLista = (CardView) findViewById(R.id.btnListRealiz_InstructorEditarDia);
        btnGrafica = (CardView) findViewById(R.id.btnGraficaRealizado_InstructorEditarDia);

        txtvGrafica = (TextView) findViewById(R.id.txtvGraficaRealizado_InstructorEditarDia);
        txtvLista = (TextView) findViewById(R.id.txtvListRealiz_InstructorEditarDia);

        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        txtvGrafica.setTypeface(Regular);
        txtvLista.setTypeface(Regular);

        final String registro = getIntent().getStringExtra("REGISTRO");
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorClienteRelizSele.this,InstructorRelizLista.class);
                intent.putExtra("REGISTRO",registro);
                startActivity(intent);
            }
        });

        btnGrafica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorClienteRelizSele.this,InstructorMusculoGrafica.class);
                intent.putExtra("REGISTRO",registro);
                startActivity(intent);
            }
        });
    }

}
