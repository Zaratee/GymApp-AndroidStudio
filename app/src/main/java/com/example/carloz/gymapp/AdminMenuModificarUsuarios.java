package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class AdminMenuModificarUsuarios extends AppCompatActivity implements View.OnClickListener {

    CardView cardvAgregarCliente, cardvAgregarInstructor, cardvAgregarNutriologo;
    String stringCuenta;
    TextView  txtvCliente, txtvNutriologo, txtvInstructor, txtvTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_modificar_usuarios);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cardvAgregarCliente = (CardView) findViewById(R.id.cardvAgregarCliente_AdminMenuModificarUsuarios);
        cardvAgregarInstructor = (CardView) findViewById(R.id.cardvAgregarInstructor_AdminMenuModificarUsuarios);
        cardvAgregarNutriologo = (CardView) findViewById(R.id.cardvAgregarNutriologo_AdminMenuModificarUsuarios);


        txtvCliente = (TextView) findViewById(R.id.txtVCliente_AdminMenuModificarUsuarios);
        txtvNutriologo = (TextView) findViewById(R.id.txtvNutriologo_AdminMenuModificarUsuarios);
        txtvInstructor = (TextView) findViewById(R.id.txtvInstructor_AdminMenuModificarUsuarios);
        txtvTitulo = (TextView) findViewById(R.id.txtvTitulor_AdminMenuModificarUsuarios);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        txtvTitulo.setTypeface(Thin);
        txtvCliente.setTypeface(Regular);
        txtvNutriologo.setTypeface(Regular);
        txtvInstructor.setTypeface(Regular);
        stringCuenta =Login.Registro;


        cardvAgregarCliente.setOnClickListener(this);
        cardvAgregarInstructor.setOnClickListener(this);
        cardvAgregarNutriologo.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        Intent intentAgregarCliente = new Intent(AdminMenuModificarUsuarios.this, AdminModificarUsuario.class);
        Intent intentAgregarInstuctor = new Intent(AdminMenuModificarUsuarios.this, AdminModificarUsuario.class);
        Intent intentAgregarNutriologo = new Intent(AdminMenuModificarUsuarios.this, AdminModificarUsuario.class);


        switch (v.getId()) {
            case R.id.cardvAgregarCliente_AdminMenuModificarUsuarios:
                intentAgregarCliente.putExtra("Cuenta", "Cliente");
                startActivity(intentAgregarCliente);
                break;

            case R.id.cardvAgregarInstructor_AdminMenuModificarUsuarios:
                intentAgregarInstuctor.putExtra("Cuenta", "Instructor");
                startActivity(intentAgregarInstuctor);
                break;

            case R.id.cardvAgregarNutriologo_AdminMenuModificarUsuarios:
                intentAgregarNutriologo.putExtra("Cuenta", "Nutriologo");
                startActivity(intentAgregarNutriologo);
                break;

            default:
                break;

        }
    }
    }

