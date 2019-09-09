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

public class InstructorEditarDia extends AppCompatActivity {

    CardView btnLunes, btnMartes, btnMiercoles, btnJueves, btnViernes, btnSabado, btnDomingo, btnCodigo;
    TextView txtvLunes, txtvMartes, txtvMiercoles, txtvJueves, txtViernes, txtvSabado, txtvDomingo;
    String registroCliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_editar_dia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnLunes = (CardView) findViewById(R.id.btnLunes_InstructorEditarDia);
        btnMartes = (CardView) findViewById(R.id.btnMartes_InstructorEditarDia);
        btnMiercoles = (CardView) findViewById(R.id.btnMiercoles_InstructorEditarDia);
        btnJueves = (CardView) findViewById(R.id.btnJueves_InstructorEditarDia);
        btnViernes = (CardView) findViewById(R.id.btnViernes_InstructorEditarDia);
        btnSabado = (CardView) findViewById(R.id.btnSabado_InstructorEditarDia);
        btnDomingo = (CardView) findViewById(R.id.btnDomingo_InstructorEditarDia);

        txtvLunes = (TextView) findViewById(R.id.txtvLunes_InstructorEditarDia);
        txtvMartes = (TextView) findViewById(R.id.txtvMartes_InstructorEditarDia);
        txtvMiercoles = (TextView) findViewById(R.id.txtvMiercoles_InstructorEditarDia);
        txtvJueves = (TextView) findViewById(R.id.txtvJueves_InstructorEditarDia);
        txtViernes = (TextView) findViewById(R.id.txtvViernes_InstructorEditarDia);
        txtvSabado = (TextView) findViewById(R.id.txtvSabado_InstructorEditarDia);
        txtvDomingo = (TextView) findViewById(R.id.txtvDomingo_InstructorEditarDia);

        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        txtvLunes.setTypeface(Regular);
        txtvMartes.setTypeface(Regular);
        txtvMiercoles.setTypeface(Regular);
        txtvJueves.setTypeface(Regular);
        txtvSabado.setTypeface(Regular);
        txtvDomingo.setTypeface(Regular);
        txtViernes.setTypeface(Regular);

        Toast.makeText(this,""+getIntent().getStringExtra("REGISTRO"),Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(InstructorEditarDia.this,InstructorModificarEjer.class);
                String Titulo = "Lunes";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","1");
                startActivity(intent);
            }
        });
    }

    private void clickbtnMartes() {
        btnMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorEditarDia.this,InstructorModificarEjer.class);
                String Titulo = "Martes";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","2");
                startActivity(intent);
            }
        });

    }

    private void clickbtnMiercoles() {
        btnMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorEditarDia.this,InstructorModificarEjer.class);
                String Titulo = "Miercoles";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","3");
                startActivity(intent);
            }
        });

    }

    private void clickbtnJueves() {
        btnJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorEditarDia.this,InstructorModificarEjer.class);
                String Titulo = "Jueves";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","4");
                startActivity(intent);
            }
        });

    }

    private void clickbtnViernes() {
        btnViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorEditarDia.this,InstructorModificarEjer.class);
                String Titulo = "Viernes";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","5");
                startActivity(intent);
            }
        });

    }

    private void clickbtnSabado() {
        btnSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorEditarDia.this,InstructorModificarEjer.class);
                String Titulo = "Sabado";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","6");
                startActivity(intent);
            }
        });
    }

    private void clickbtnDomingo() {
        btnDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorEditarDia.this,InstructorModificarEjer.class);
                String Titulo = "Domingo";
                intent.putExtra("REGISTRO",registroCliente);
                intent.putExtra("DIA",Titulo);
                intent.putExtra("DIANUM","7");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InstructorEditarDia.this,InstructorMenuCliente.class);
        intent.putExtra("REGISTRO",registroCliente);
        startActivity(intent);
    }
}
