package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_menu_selecusu extends AppCompatActivity implements View.OnClickListener {

    CardView cardvAgregarCliente, cardvAgregarInstructor, cardvAgregarNutriologo;
    String stringCuenta;
    TextView txtvAgregar, txtvCliente, txtvNutriologo, txtvInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_selecusu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cardvAgregarCliente = (CardView) findViewById(R.id.cardvAgregarCliente_Admin_menu_selecusu);
        cardvAgregarInstructor = (CardView) findViewById(R.id.cardvAgregarInstructor_Admin_menu_selecusu);
        cardvAgregarNutriologo = (CardView) findViewById(R.id.cardvAgregarNutriologo_Admin_menu_selecusu);

        txtvAgregar = (TextView) findViewById(R.id.txtvNoActionAgregar_Admin_menu_selecusu);
        txtvCliente = (TextView) findViewById(R.id.txtVCliente_Admin_menu_selecusu);
        txtvNutriologo = (TextView) findViewById(R.id.txtvNutriologo_Admin_menu_selecusu);
        txtvInstructor = (TextView) findViewById(R.id.txtvInstructor_Admin_menu_selecusu);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        txtvCliente.setTypeface(Regular);
        txtvNutriologo.setTypeface(Regular);
        txtvInstructor.setTypeface(Regular);
        txtvAgregar.setTypeface(Thin);

        cardvAgregarCliente.setOnClickListener(this);
        cardvAgregarInstructor.setOnClickListener(this);
        cardvAgregarNutriologo.setOnClickListener(this);

        recibirDatos();
    }

    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringCuenta =(String) bundle.get("REGISTRO");

    }

    @Override
    public void onClick(View v) {
        Intent intentAgregarCliente = new Intent(Admin_menu_selecusu.this, AgregarCliente.class);
        Intent intentAgregarInstuctor = new Intent(Admin_menu_selecusu.this, AgregarEntrenador.class);
        Intent intentAgregarNutriologo = new Intent(Admin_menu_selecusu.this, AgregarNutriologo.class);


        switch (v.getId()) {
            case R.id.cardvAgregarCliente_Admin_menu_selecusu:
                intentAgregarCliente.putExtra("REGISTRO",stringCuenta);
                startActivity(intentAgregarCliente);
                break;

            case R.id.cardvAgregarInstructor_Admin_menu_selecusu:
                intentAgregarInstuctor.putExtra("REGISTRO",stringCuenta);
                startActivity(intentAgregarInstuctor);
                break;

            case R.id.cardvAgregarNutriologo_Admin_menu_selecusu:
                intentAgregarNutriologo.putExtra("REGISTRO",stringCuenta);
                startActivity(intentAgregarNutriologo);
                break;

            default:
                break;

        }
    }

}