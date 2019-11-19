package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class AdminEliminarSelecUsuario extends AppCompatActivity implements View.OnClickListener {

    CardView cardvEliminarCliente, cardvEliminarInstructor, cardvEliminarNutriologo;
    String stringCuenta;
    TextView txtvAgregar, txtvCliente, txtvNutriologo, txtvInstructor, txtvInfo1, txtvInfo2, txtvInfo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_eliminar_selec_usuario);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cardvEliminarCliente = (CardView) findViewById(R.id.cardvEliminarCliente_AdminEliminarSelecUsuario);
        cardvEliminarInstructor = (CardView) findViewById(R.id.cardvEliminarInstructor_AdminEliminarSelecUsuario);
        cardvEliminarNutriologo = (CardView) findViewById(R.id.cardvEliminarNutriologo_AdminEliminarSelecUsuario);

        txtvAgregar = (TextView) findViewById(R.id.txtvNoActionAgregar_AdminEliminarSelecUsuario);
        txtvCliente = (TextView) findViewById(R.id.txtVCliente_AdminEliminarSelecUsuario);
        txtvNutriologo = (TextView) findViewById(R.id.txtvNutriologo_AdminEliminarSelecUsuario);
        txtvInstructor = (TextView) findViewById(R.id.txtvInstructor_AdminEliminarSelecUsuario);

        txtvInfo1 = (TextView) findViewById(R.id.txtvU1info_AdminEliminarSelecUsuario);
        txtvInfo2 = (TextView) findViewById(R.id.txtvU2info_AdminEliminarSelecUsuario);
        txtvInfo3 = (TextView) findViewById(R.id.txtvU3info_AdminEliminarSelecUsuario);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");


        txtvCliente.setTypeface(Regular);
        txtvNutriologo.setTypeface(Regular);
        txtvInstructor.setTypeface(Regular);
        txtvAgregar.setTypeface(Thin);

        txtvInfo1.setTypeface(Light);
        txtvInfo1.setTypeface(Light);
        txtvInfo1.setTypeface(Light);

        cardvEliminarCliente.setOnClickListener(this);
        cardvEliminarInstructor.setOnClickListener(this);
        cardvEliminarNutriologo.setOnClickListener(this);

        recibirDatos();
    }
    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringCuenta =(String) bundle.get("REGISTRO");

    }

    @Override
    public void onClick(View v) {
        Intent intentEliminarCliente = new Intent(AdminEliminarSelecUsuario.this, EliminarCliente.class);
        Intent intentEliminarInstuctor = new Intent(AdminEliminarSelecUsuario.this, EliminarEntrenador.class);
        Intent intentEliminarNutriologo = new Intent(AdminEliminarSelecUsuario.this, EliminarNutriologo.class);


        switch (v.getId()) {
            case R.id.cardvEliminarCliente_AdminEliminarSelecUsuario:
                intentEliminarCliente.putExtra("REGISTRO",stringCuenta);
                startActivity(intentEliminarCliente);
                break;

            case R.id.cardvEliminarInstructor_AdminEliminarSelecUsuario:
                intentEliminarInstuctor.putExtra("REGISTRO",stringCuenta);
                startActivity(intentEliminarInstuctor);
                break;

            case R.id.cardvEliminarNutriologo_AdminEliminarSelecUsuario:
                intentEliminarNutriologo.putExtra("REGISTRO",stringCuenta);
                startActivity(intentEliminarNutriologo);
                break;

            default:
                break;

        }
    }

}

