package com.example.carloz.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InstructorMenuCliente extends AppCompatActivity {

    CardView btnChat, btnAsignarRutina, btnEditarRutina, btnEvaluacionCliente, btnEjercicioRealizado;
    TextView txtvChat, txtvAsignarRutina, txtvEditarRutina, txtvEjercicioRealizado, txtvEvaluacionCliente;
    String registroCliente;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_menu_cliente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnChat = (CardView) findViewById(R.id.btnChat_InstructorMenuCliente);
        btnAsignarRutina = (CardView) findViewById(R.id.btnAsignarRutina_InstructorMenuCliente);
        btnEditarRutina = (CardView) findViewById(R.id.btnEditarRutina_InstructorMenuCliente);
        btnEvaluacionCliente = (CardView) findViewById(R.id.btnEvaluacionCliente_InstructorMenuCliente);
        btnEjercicioRealizado = (CardView) findViewById(R.id.btnEjercicioRelaizado_InstructorMenuCliente);

        txtvChat = (TextView) findViewById(R.id.txtvChat_InstructorMenuCliente);
        txtvAsignarRutina = (TextView) findViewById(R.id.txtvRutina_InstructorMenuCliente);
        txtvEditarRutina = (TextView) findViewById(R.id.txtvEditarRutina_InstructorMenuCliente);
        txtvEjercicioRealizado = (TextView) findViewById(R.id.txtvEjercicioRealizado_InstructorMenuCliente);
        txtvEvaluacionCliente = (TextView) findViewById(R.id.txtvEvaluacionCliente_InstructorMenuCliente);

        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        auth = FirebaseAuth.getInstance();

        txtvChat.setTypeface(Regular);
        txtvAsignarRutina.setTypeface(Regular);
        txtvEditarRutina.setTypeface(Regular);
        txtvEjercicioRealizado.setTypeface(Regular);
        txtvEvaluacionCliente.setTypeface(Regular);

        Toast.makeText(this,""+getIntent().getStringExtra("REGISTRO"),Toast.LENGTH_SHORT).show();
        registroCliente = getIntent().getStringExtra("REGISTRO");





        clickbtnChat();
        clickbtnAsignarRutina();
        clickbtnEditarRutina();
        clickbtnEvaluacionCliente();
        clickbtnEjercicioRealizado();
        conexionFirebase();
    }

    private void conexionFirebase() {
        String email = Login.Registro+"@gymlife.com", password = "GymLife";

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(InstructorMenuCliente.this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
        }else {
            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(InstructorMenuCliente.this, "Logeadoooo parse", Toast.LENGTH_SHORT).show();
                                /*Intent intent = new Intent(InstructorMenuCliente.this,ClienteEjercicio.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                //intent.putExtra("REGISTROINSTRU",registroInstru);
                                startActivity(intent);*/
                            }else {
                                Toast.makeText(InstructorMenuCliente.this,"Usuario no existe",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }



    private void clickbtnEjercicioRealizado() {
        btnEjercicioRealizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMenuCliente.this,InstructorProgresoCodigo.class);
                intent.putExtra("REGISTRO",registroCliente);
                startActivity(intent);
            }
        });

    }

    private void clickbtnEvaluacionCliente() {
        btnEvaluacionCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMenuCliente.this,InstructorVisualizarEvaluacion.class);
                intent.putExtra("REGISTRO",registroCliente);
                startActivity(intent);
            }
        });


    }

    private void clickbtnEditarRutina() {
        btnEditarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMenuCliente.this,InstructorEditarDia.class);
                intent.putExtra("REGISTRO",registroCliente);
                startActivity(intent);
            }
        });

    }

    private void clickbtnAsignarRutina() {
        btnAsignarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorMenuCliente.this,InstructorAsignarRutinaDia.class);
                intent.putExtra("REGISTRO",registroCliente);
                startActivity(intent);
            }
        });
    }



    private void clickbtnChat() {
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.orderByChild("username").equalTo(registroCliente).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot child : dataSnapshot.getChildren())
                        {
                            Intent intent = new Intent(InstructorMenuCliente.this,MessageActivity.class);
                            intent.putExtra("userid",child.getKey());
                            intent.putExtra("receptor",registroCliente);
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


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,InstructorPerfil.class));
    }
}
