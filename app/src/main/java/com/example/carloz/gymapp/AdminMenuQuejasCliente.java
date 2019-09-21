package com.example.carloz.gymapp;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorAdminListaQuejas;
import com.example.carloz.gymapp.adaptador.AdaptadorInstructorClientesAsignados;
import com.example.carloz.gymapp.items.ItemClienteInstructor;
import com.example.carloz.gymapp.items.ItemListaQuejasAdmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminMenuQuejasCliente extends AppCompatActivity {

    TextView txtvTitulo;
    String registro, nombre, quejaRegistro,apellido;
    public static String Usuario="0000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_quejas_cliente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.menu_AdminMenuQuejasCliente);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Usuario = getIntent().getStringExtra("USUARIO");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontenedor_AdminMenuQuejasCliente, new FragmentQuejasRealizado()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navRealizado_bottom:
                            fragment = new FragmentQuejasRealizado();
                            break;
                        case R.id.navNoRealizado_bottom:
                            fragment = new FragmentQuejasNoRealizado();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontenedor_AdminMenuQuejasCliente, fragment).commit();
                    return true;
                }
            };
}
