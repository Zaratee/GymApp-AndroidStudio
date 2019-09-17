package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InstructorAsignarRutinaDia extends AppCompatActivity {

    CardView btnLunes, btnMartes, btnMiercoles, btnJueves, btnViernes, btnSabado, btnDomingo, btnCodigo;
    TextView txtvLunes, txtvMartes, txtvMiercoles, txtvJueves, txtViernes, txtvSabado, txtvDomingo;
    String registroCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_asignar_rutina_dia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnLunes = (CardView) findViewById(R.id.btnLunes_InstructorAsignarRutinaDia);
        btnMartes = (CardView) findViewById(R.id.btnMartes_InstructorAsignarRutinaDia);
        btnMiercoles = (CardView) findViewById(R.id.btnMiercoles_InstructorAsignarRutinaDia);
        btnJueves = (CardView) findViewById(R.id.btnJueves_InstructorAsignarRutinaDia);
        btnViernes = (CardView) findViewById(R.id.btnViernes_InstructorAsignarRutinaDia);
        btnSabado = (CardView) findViewById(R.id.btnSabado_InstructorAsignarRutinaDia);
        btnDomingo = (CardView) findViewById(R.id.btnDomingo_InstructorAsignarRutinaDia);

        txtvLunes = (TextView) findViewById(R.id.txtvLunes_InstructorAsignarRutinaDia);
        txtvMartes = (TextView) findViewById(R.id.txtvMartes_InstructorAsignarRutinaDia);
        txtvMiercoles = (TextView) findViewById(R.id.txtvMiercoles_InstructorAsignarRutinaDia);
        txtvJueves = (TextView) findViewById(R.id.txtvJueves_InstructorAsignarRutinaDia);
        txtViernes = (TextView) findViewById(R.id.txtvViernes_InstructorAsignarRutinaDia);
        txtvSabado = (TextView) findViewById(R.id.txtvSabado_InstructorAsignarRutinaDia);
        txtvDomingo = (TextView) findViewById(R.id.txtvDomingo_InstructorAsignarRutinaDia);

        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        txtvLunes.setTypeface(Regular);
        txtvMartes.setTypeface(Regular);
        txtvMiercoles.setTypeface(Regular);
        txtvJueves.setTypeface(Regular);
        txtvSabado.setTypeface(Regular);
        txtvDomingo.setTypeface(Regular);
        txtViernes.setTypeface(Regular);

        //Toast.makeText(this,""+getIntent().getStringExtra("REGISTRO"),Toast.LENGTH_SHORT).show();
        registroCliente = getIntent().getStringExtra("REGISTRO");

        clickbtnLunes();
        clickbtnMartes();
        clickbtnMiercoles();
        clickbtnJueves();
        clickbtnViernes();
        clickbtnSabado();
        clickbtnDomingo();

    }

    private void clickbtnLunes() {
        btnLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorAsignarRutinaDia.this,InstructorAsignarSeleccionarMusculo.class);
                String Titulo = "Lunes";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","2");
                startActivity(intent);
            }
        });
    }

    private void clickbtnMartes() {
        btnMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorAsignarRutinaDia.this,InstructorAsignarSeleccionarMusculo.class);
                String Titulo = "Martes";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","3");
                startActivity(intent);
            }
        });

    }

    private void clickbtnMiercoles() {
        btnMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorAsignarRutinaDia.this,InstructorAsignarSeleccionarMusculo.class);
                String Titulo = "Miercoles";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","4");
                startActivity(intent);
            }
        });

    }

    private void clickbtnJueves() {
        btnJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorAsignarRutinaDia.this,InstructorAsignarSeleccionarMusculo.class);
                String Titulo = "Jueves";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","5");
                startActivity(intent);
            }
        });


    }

    private void clickbtnViernes() {
        btnViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorAsignarRutinaDia.this,InstructorAsignarSeleccionarMusculo.class);
                String Titulo = "Viernes";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","6");
                startActivity(intent);
            }
        });

    }

    private void clickbtnSabado() {
        btnSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorAsignarRutinaDia.this,InstructorAsignarSeleccionarMusculo.class);
                String Titulo = "Sabado";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","7");
                startActivity(intent);
            }
        });

    }

    private void clickbtnDomingo() {
        btnDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorAsignarRutinaDia.this,InstructorAsignarSeleccionarMusculo.class);
                String Titulo = "Domingo";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","1");
                startActivity(intent);
            }
        });

    }

    private void clickbtnCodigo() {

    }
}
