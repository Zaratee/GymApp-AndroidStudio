package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class ClienteAvanceMusculo extends AppCompatActivity {


    CardView btnBrazoyAnt, btnPectorales, btnPiernas, btnHombros, btnEspalda, btnGluteos, btnAbdominales;
    String registroCliente;
    TextView txtvBrazoyAnt, txtvHombros, txtvEspalda, txtvPiernas, txtvGluteos, txtvAbdominales, txtvPectorales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_avance_musculo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        btnBrazoyAnt = (CardView) findViewById(R.id.btnBrazos_ClienteAvanceMusculo);
        btnPectorales = (CardView) findViewById(R.id.btnPectorales_ClienteAvanceMusculo);
        btnPiernas = (CardView) findViewById(R.id.btnPiernas_ClienteAvanceMusculo);
        btnHombros = (CardView) findViewById(R.id.btnHombros_ClienteAvanceMusculo);
        btnEspalda = (CardView) findViewById(R.id.btnEspalda_ClienteAvanceMusculo);
        btnGluteos = (CardView) findViewById(R.id.btnGluteos_ClienteAvanceMusculo);
        btnAbdominales = (CardView) findViewById(R.id.btnAbdominales_ClienteAvanceMusculo);

        txtvBrazoyAnt = (TextView) findViewById(R.id.txtvBrazo_ClienteAvanceMusculo);
        txtvEspalda = (TextView) findViewById(R.id.txtvEspalda_ClienteAvanceMusculo);
        txtvPiernas = (TextView) findViewById(R.id.txtvPiernas_ClienteAvanceMusculo);
        txtvGluteos = (TextView) findViewById(R.id.txtvGluteos_ClienteAvanceMusculo);
        txtvAbdominales = (TextView) findViewById(R.id.txtvCerrarSesion_ClienteAvanceMusculo);
        txtvHombros = (TextView) findViewById(R.id.txtvHombros_ClienteAvanceMusculo);
        txtvPectorales = (TextView) findViewById(R.id.txtvActualizarContra_ClienteAvanceMusculo);


        registroCliente = getIntent().getStringExtra("REGISTRO");

        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvBrazoyAnt.setTypeface(Condensed);
        txtvEspalda.setTypeface(Condensed);
        txtvPiernas.setTypeface(Condensed);
        txtvGluteos.setTypeface(Condensed);
        txtvAbdominales.setTypeface(Condensed);
        txtvHombros.setTypeface(Condensed);
        txtvPectorales.setTypeface(Condensed);


        clickbtnBrazoyAnt();
        clickbtnPectorales();
        clickbtnPiernas();
        clickbtnHombros();
        clickbtnEspalda();
        clickbtnGluteos();
        clickbtnAbdominales();
    }

    private void clickbtnAbdominales() {
        btnAbdominales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteAvanceMusculo.this,InstructorAsignarEjercicio.class);
                String Titulo = "Abdominales";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",Login.Registro);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

    private void clickbtnGluteos() {
        btnGluteos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteAvanceMusculo.this,InstructorAsignarEjercicio.class);
                String Titulo = "Gluteos";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",Login.Registro);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });
    }

    private void clickbtnEspalda() {
        btnEspalda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteAvanceMusculo.this,InstructorAsignarEjercicio.class);
                String Titulo = "Espalda";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",Login.Registro);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });
    }

    private void clickbtnHombros() {
        btnHombros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteAvanceMusculo.this,InstructorAsignarEjercicio.class);
                String Titulo = "Hombros";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",Login.Registro);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

    private void clickbtnPiernas() {
        btnPiernas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteAvanceMusculo.this,InstructorAsignarEjercicio.class);
                String Titulo = "Piernas";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",Login.Registro);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

    private void clickbtnPectorales() {
        btnPectorales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteAvanceMusculo.this,InstructorAsignarEjercicio.class);
                String Titulo = "Pectorales";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",Login.Registro);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

    private void clickbtnBrazoyAnt() {
        btnBrazoyAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteAvanceMusculo.this,InstructorAsignarEjercicio.class);
                String Titulo = "Brazos y antebrazos";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",Login.Registro);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

}



