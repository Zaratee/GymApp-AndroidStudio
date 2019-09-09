package com.example.carloz.gymapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClienteAlimentacion extends AppCompatActivity {

    String registronutri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_alimentacion);

        FloatingActionButton btnAgregar, btnChat;

        btnAgregar  = (FloatingActionButton) findViewById(R.id.floatingbtnAgregar_ClienteAlimentacion);
        btnChat  = (FloatingActionButton) findViewById(R.id.floatingbtnChat_ClienteAlimentacion);

        registronutri = getIntent().getStringExtra("REGISTRONUTRI");

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.menu_ClienteAlimentacion);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontenedor_ClienteAlimentacion, new FragmentDiario()).commit();
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClienteAlimentacion.this,ClienteSolicitudAlimento.class));
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.orderByChild("username").equalTo(registronutri).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot child : dataSnapshot.getChildren())
                        {
                            Intent intent = new Intent(ClienteAlimentacion.this,MessageActivity.class);
                            intent.putExtra("userid",child.getKey());
                            intent.putExtra("receptor",registronutri);
                            startActivity(intent);

                        }

                        // Toast.makeText(ClienteEjercicio.this, ""+dataSnapshot., Toast.LENGTH_SHORT).show();
                        //HashMap<String, String> hashMap = new HashMap<String ,String>(dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

   private BottomNavigationView.OnNavigationItemSelectedListener navListener =
           new BottomNavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                   Fragment fragment = null;
                   switch (menuItem.getItemId()) {
                       case R.id.navDiario_bottom:
                           fragment = new FragmentDieta();
                           break;
                       case R.id.navDieta_bottom:
                           fragment = new FragmentDiario();

                           break;
                   }
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontenedor_ClienteAlimentacion, fragment).commit();
                   return true;
               }
           };

    public void onBackPressed(){
        Intent intent = new Intent(ClienteAlimentacion.this,ClienteMenu.class);
        startActivity(intent);

    }



}