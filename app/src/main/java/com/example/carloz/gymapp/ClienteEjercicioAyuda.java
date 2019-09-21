package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ClienteEjercicioAyuda extends AppCompatActivity {

    String idEjercicio, diaSemana;
    FloatingActionButton btnListaRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_ejercicio_ayuda);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnListaRutina = findViewById(R.id.floatingbtnRutina_ClienteEjercicioAyuda);
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.menu_ClienteEjercicioAyuda);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontenedor_ClienteEjercicio, new FragmentAyudaEjercicio()).commit();
        idEjercicio = getIntent().getStringExtra("REGISTRO");
        diaSemana = getIntent().getStringExtra("DIASEMANA");

        btnListaRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClienteEjercicioAyuda.this,ClienteListaEjercicio.class));
            }
        });

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navEjercicio_bottom:
                            fragment = new FragmentAyudaEjercicio();
                            break;
                        case R.id.navRutina_bottom:
                            fragment = new FragmentAyudaRutina();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontenedor_ClienteEjercicio, fragment).commit();
                    return true;
                }
            };
}
