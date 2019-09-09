package com.example.carloz.gymapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NutriologoPuente extends AppCompatActivity {

    String stringRegCliente;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutriologo_puente);

        String registroInstructor = getIntent().getStringExtra("CLIENTE");
        
        recibirDatos();
        Toast.makeText(this, ""+stringRegCliente, Toast.LENGTH_SHORT).show();
        btn = findViewById(R.id.prueba);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickbtnChat();
            }
        });

    }

    private void clickbtnChat() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent3 = new Intent(NutriologoPuente.this,MessageActivity.class);
                intent3.putExtra("userid","GthAuWutghZVqQW5k6iaVjqfoxy2");
                startActivity(intent3);*/
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.orderByChild("username").equalTo("1000").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot child : dataSnapshot.getChildren())
                        {
                            Intent intent = new Intent(NutriologoPuente.this,MessageActivity.class);
                            intent.putExtra("userid",child.getKey());
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
    private void recibirDatos() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        stringRegCliente = (String) bundle.get("CLIENTE");
        //Toast.makeText(this, "prueba "+stringRegCliente, Toast.LENGTH_SHORT).show();
    }

}
