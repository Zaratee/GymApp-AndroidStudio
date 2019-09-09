package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class AdminMenuQuejasUsuarios extends AppCompatActivity implements View.OnClickListener {

    CardView cardvAgregarCliente, cardvAgregarInstructor, cardvAgregarNutriologo;
    TextView txtvAgregar, txtvCliente, txtvNutriologo, txtvInstructor;
    String stringCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_quejas_usuarios);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cardvAgregarCliente = (CardView) findViewById(R.id.cardvAgregarCliente_AdminMenuQuejasUsuarios);
        cardvAgregarInstructor = (CardView) findViewById(R.id.cardvAgregarInstructor_AdminMenuQuejasUsuarios);
        cardvAgregarNutriologo = (CardView) findViewById(R.id.cardvAgregarNutriologo_AdminMenuQuejasUsuarios);

        txtvAgregar = (TextView) findViewById(R.id.txtvNoActionAgregar_AdminMenuQuejasUsuarios);
        txtvCliente = (TextView) findViewById(R.id.txtVCliente_AdminMenuQuejasUsuarios);
        txtvNutriologo = (TextView) findViewById(R.id.txtvNutriologo_AdminMenuQuejasUsuarios);
        txtvInstructor = (TextView) findViewById(R.id.txtvInstructor_AdminMenuQuejasUsuarios);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        txtvCliente.setTypeface(Regular);
        txtvNutriologo.setTypeface(Regular);
        txtvInstructor.setTypeface(Regular);
        txtvAgregar.setTypeface(Thin);

        cardvAgregarCliente.setOnClickListener(this);
        cardvAgregarInstructor.setOnClickListener(this);
        cardvAgregarNutriologo.setOnClickListener(this);

        stringCuenta = Login.Registro;

    }

    @Override
    public void onClick(View v) {
        Intent intentQuejaCliente = new Intent(AdminMenuQuejasUsuarios.this, AdminMenuQuejasCliente.class);
        Intent intentQuejaInstuctor = new Intent(AdminMenuQuejasUsuarios.this, AdminMenuQuejasInstructor.class);
        Intent intentQuejaNutriologo = new Intent(AdminMenuQuejasUsuarios.this, AdminMenuQuejasNutriologo.class);

        switch (v.getId()) {
            case R.id.cardvAgregarCliente_AdminMenuQuejasUsuarios:
                startActivity(intentQuejaCliente);
                break;

            case R.id.cardvAgregarInstructor_AdminMenuQuejasUsuarios:
                startActivity(intentQuejaInstuctor);
                break;

            case R.id.cardvAgregarNutriologo_AdminMenuQuejasUsuarios:
                intentQuejaNutriologo.putExtra("REGISTRO",stringCuenta);
                startActivity(intentQuejaNutriologo);
                break;

            default:
                break;

        }
    }

}
