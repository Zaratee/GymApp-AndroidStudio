package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class InstructorMusculoGrafica extends AppCompatActivity {

    CardView btnBrazoyAnt, btnPectorales, btnPiernas, btnHombros, btnEspalda, btnGluteos, btnAbdominales;
    String registroCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_musculo_grafica);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnBrazoyAnt = (CardView) findViewById(R.id.btnBrazos_InstructorMusculoGrafica);
        btnPectorales = (CardView) findViewById(R.id.btnPectorales_InstructorMusculoGrafica);
        btnPiernas = (CardView) findViewById(R.id.btnPiernas_InstructorMusculoGrafica);
        btnHombros = (CardView) findViewById(R.id.btnHombros_InstructorMusculoGrafica);
        btnEspalda = (CardView) findViewById(R.id.btnEspalda_InstructorMusculoGrafica);
        btnGluteos = (CardView) findViewById(R.id.btnGluteos_InstructorMusculoGrafica);
        btnAbdominales = (CardView) findViewById(R.id.btnAbdominales_InstructorMusculoGrafica);

        registroCliente = getIntent().getStringExtra("REGISTRO");


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
                Intent intent = new Intent(InstructorMusculoGrafica.this,InstructorAsignarEjercicio.class);
                String Titulo = "Abdominales";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

    private void clickbtnGluteos() {
        btnGluteos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMusculoGrafica.this,InstructorAsignarEjercicio.class);
                String Titulo = "Gluteos";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });
    }

    private void clickbtnEspalda() {
        btnEspalda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMusculoGrafica.this,InstructorAsignarEjercicio.class);
                String Titulo = "Espalda";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });
    }

    private void clickbtnHombros() {
        btnHombros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMusculoGrafica.this,InstructorAsignarEjercicio.class);
                String Titulo = "Hombros";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

    private void clickbtnPiernas() {
        btnPiernas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMusculoGrafica.this,InstructorAsignarEjercicio.class);
                String Titulo = "Piernas";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

    private void clickbtnPectorales() {
        btnPectorales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMusculoGrafica.this,InstructorAsignarEjercicio.class);
                String Titulo = "Pectorales";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

    private void clickbtnBrazoyAnt() {
        btnBrazoyAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMusculoGrafica.this,InstructorAsignarEjercicio.class);
                String Titulo = "Brazos y antebrazos";
                intent.putExtra("MUSCULO",Titulo);
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIANUM","20");
                startActivity(intent);
            }
        });

    }

}

