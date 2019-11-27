package com.example.carloz.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorAlimentoAsignado;
import com.example.carloz.gymapp.items.ItemAlimentoAsignado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NutriologoClienteAsignar extends AppCompatActivity {

    Button btnMasDesayuno, btnMasAlmuerzo, btnMasCena, btnMasPasaBocas, btnChat, btnEditar;
    RecyclerView listDesayuno, listAlmuerzo,listCena,listPasabocas;
    ArrayList<ItemAlimentoAsignado> listaPasabocas, listaDes, listaCena, listaAlmuerzo;
    String Nombre,Marca,Cantidad,Tiempo,CantidadTipo, bandera;
    TextView txtvCalorias, txtvProte, txtvCarboh, txtvGrasas,txtvCalTotalDesayuno, txtvCalTotalAlmuerzo, txtvCalTotalCena, txtvCalTotalPasabocas;
    int TCalDesayuno, TCalAlmuerzo, TCalCena, TCalPasaBocas;

    public static String registroCliente="0000";
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutriologo_cliente_asignar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        btnChat = (Button) findViewById(R.id.btnChat_NutriologoClienteAsignar);
        btnMasDesayuno = (Button) findViewById(R.id.btnAgregarDesayuno_NutriologoClienteAsignar);
        btnMasAlmuerzo = (Button) findViewById(R.id.btnAgregarAlmuerzo_NutriologoClienteAsignar);
        btnMasCena = (Button) findViewById(R.id.btnAgregarCena_NutriologoClienteAsignar);
        btnMasPasaBocas = (Button) findViewById(R.id.btnAgregarPasabocas_NutriologoClienteAsignar);
        btnEditar = (Button) findViewById(R.id.btnEditarDieta_NutriologoClienteAsignar);

        txtvCalTotalDesayuno = (TextView) findViewById(R.id.txtvTotalCaloriasDesayuno_NutriologoClienteAsignar);
        txtvCalTotalAlmuerzo = (TextView) findViewById(R.id.txtvTotalCaloriasALmuerzo_NutriologoClienteAsignar);
        txtvCalTotalCena = (TextView) findViewById(R.id.txtvTotalCaloriasCena_NutriologoClienteAsignar);
        txtvCalTotalPasabocas = (TextView) findViewById(R.id.txtvTotalCaloriasPasabocas_NutriologoClienteAsignar);

        listDesayuno = (RecyclerView) findViewById(R.id.listvDesayuno_NutriologoClienteAsignar);
        listAlmuerzo = (RecyclerView) findViewById(R.id.listvAlmuerzo_NutriologoClienteAsignar);
        listCena = (RecyclerView) findViewById(R.id.listvCena_NutriologoClienteAsignar);
        listPasabocas = (RecyclerView) findViewById(R.id.listvPasabocas_NutriologoClienteAsignar);

        txtvCarboh = (TextView) findViewById(R.id.txtvCarbh_NutriologoClienteAsignar);
        txtvGrasas = (TextView) findViewById(R.id.txtvGrasas_NutriologoClienteAsignar);
        txtvProte = (TextView) findViewById(R.id.txtvProt_NutriologoClienteAsignar);
        txtvCalorias = (TextView) findViewById(R.id.txtvCalorias_NutriologoClienteAsignar);

        registroCliente = getIntent().getStringExtra("REGISTRO");
        //Toast.makeText(this, "2: "+registroCliente, Toast.LENGTH_SHORT).show();
        bandera = getIntent().getStringExtra("BANDERA");



        SharedPreferences preferences = getSharedPreferences("Cuemta", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("InstBandera", bandera);
        editor.commit();

        String banderiña = preferences.getString("InstBandera","nada");

        if (banderiña.equals("1")) {
            SharedPreferences preferences2 = getSharedPreferences("Cuemta", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = preferences2.edit();
            editor2.putString("NutritClientReg", registroCliente);
            editor2.putString("InstRef", Login.Registro);
            editor2.putString("InstBandera", bandera);
            editor2.commit();

            bandera = "0";

        }else{
            registroCliente = preferences.getString("NutritClientReg","nada");
        }




        listaAlmuerzo = new ArrayList<>();
        listaPasabocas = new ArrayList<>();
        listaCena = new ArrayList<>();
        listaDes = new ArrayList<>();

        listDesayuno.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        listAlmuerzo.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        listCena.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        listPasabocas.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        //Toast.makeText(this, "3: "+registroCliente, Toast.LENGTH_SHORT).show();
        auth = FirebaseAuth.getInstance();

        clickbtnMasDesayuno();
        clickbtnMasAlmuerzo();
        clickbtnMasCena();
        clickbtnPasaBocas();
        conexionBDDesayuno();
        conexionBDAlmuerzo();
        conexionBDCena();
        conexionBDPasabocas();
        conexionBDvalores();
        clickbtnChat();

        conexionBDInfoTotalCal();

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriologoClienteAsignar.this,NutriologoEditarDieta.class);
                startActivity(intent);
            }
        });
    }
    private void conexionBDInfoTotalCal() {
        //Desayuno
        {
            String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Lista_Alimentos_Cliente_Tipo.php?registro="+registroCliente+"&tipo=0";

            url = url.replaceAll(" ", "%20");

            JsonObjectRequest peticion = new JsonObjectRequest
                    (
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //String valor = response.getString("Estado");
                                        JSONArray jsonArray = response.getJSONArray("Alimentos");
                                        TCalDesayuno = 0;
                                        for (int i =0; i<jsonArray.length();i++){
                                            JSONObject clientes = jsonArray.getJSONObject(i);
                                            int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                            int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));

                                            TCalDesayuno = (cant*aliCal)+TCalDesayuno;

                                        }
                                        if (jsonArray.length() ==0){
                                            txtvCalTotalDesayuno.setText("0");
                                        }else {
                                            txtvCalTotalDesayuno.setText(""+TCalDesayuno);
                                        }



                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            , new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(NutriologoClienteAsignar.this,"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(NutriologoClienteAsignar.this);
            x.add(peticion);
        }

        //Almuerzo
        {
            String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Lista_Alimentos_Cliente_Tipo.php?registro="+registroCliente+"&tipo=1";

            url = url.replaceAll(" ", "%20");

            JsonObjectRequest peticion = new JsonObjectRequest
                    (
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //String valor = response.getString("Estado");
                                        JSONArray jsonArray = response.getJSONArray("Alimentos");
                                        TCalAlmuerzo = 0;
                                        for (int i =0; i<jsonArray.length();i++){
                                            JSONObject clientes = jsonArray.getJSONObject(i);
                                            int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                            int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));

                                            TCalAlmuerzo = (cant*aliCal)+TCalAlmuerzo;

                                        }

                                        if (jsonArray.length() ==0){
                                            txtvCalTotalAlmuerzo.setText("0");
                                        }else {
                                            txtvCalTotalAlmuerzo.setText(""+TCalAlmuerzo);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            , new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(NutriologoClienteAsignar.this,"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(NutriologoClienteAsignar.this);
            x.add(peticion);

        }

        //Cena
        {
            String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Lista_Alimentos_Cliente_Tipo.php?registro="+registroCliente+"&tipo=2";
            url = url.replaceAll(" ", "%20");

            JsonObjectRequest peticion = new JsonObjectRequest
                    (
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //String valor = response.getString("Estado");
                                        JSONArray jsonArray = response.getJSONArray("Alimentos");
                                        TCalCena = 0;
                                        for (int i =0; i<jsonArray.length();i++){
                                            JSONObject clientes = jsonArray.getJSONObject(i);
                                            int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                            int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));

                                            TCalCena = (cant*aliCal)+TCalCena;

                                        }

                                        if (jsonArray.length() ==0){
                                            txtvCalTotalCena.setText("0");
                                        }else {
                                            txtvCalTotalCena.setText(""+TCalCena);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            , new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(NutriologoClienteAsignar.this,"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(NutriologoClienteAsignar.this);
            x.add(peticion);

        }

        //Pasabocas
        {
            String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Lista_Alimentos_Cliente_Tipo.php?registro="+registroCliente+"&tipo=3";
            url = url.replaceAll(" ", "%20");

            JsonObjectRequest peticion = new JsonObjectRequest
                    (
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //String valor = response.getString("Estado");
                                        JSONArray jsonArray = response.getJSONArray("Alimentos");
                                        TCalPasaBocas = 0;
                                        for (int i =0; i<jsonArray.length();i++){
                                            JSONObject clientes = jsonArray.getJSONObject(i);
                                            int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                            int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));

                                            TCalPasaBocas = (cant*aliCal)+TCalPasaBocas;

                                        }

                                        if (jsonArray.length() ==0){
                                            txtvCalTotalPasabocas.setText("0");
                                        }else {
                                            txtvCalTotalPasabocas.setText(""+TCalPasaBocas);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            , new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(NutriologoClienteAsignar.this,"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(NutriologoClienteAsignar.this);
            x.add(peticion);

        }

    }


    private void clickbtnChat() {
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.orderByChild("username").equalTo(registroCliente).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Intent intent = new Intent(NutriologoClienteAsignar.this, MessageActivity.class);
                            intent.putExtra("userid", child.getKey());
                            intent.putExtra("receptor",registroCliente);
                            startActivity(intent);
                        }

                        // Toast.makeText(ClienteEjercicio.this, ""+dataSnapshot., Toast.LENGTH_SHORT).show();
                        //HashMap<String, String> hashMap = new HashMap<String ,String>(dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(NutriologoClienteAsignar.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        }








    private void conexionBDvalores() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Conteo_Calorias_Alimentos_Cliente.php?registro="+registroCliente;
        url = url.replaceAll(" ", "%20");

        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String grasas = response.getString("Grasas");
                                    String carboh = response.getString("Carbohidratos");
                                    String prote = response.getString("Proteinas");
                                    String calorias = response.getString("Calorias");

                                    txtvProte.setText(prote);
                                    txtvGrasas.setText(grasas);
                                    txtvCalorias.setText(calorias);
                                    txtvCarboh.setText(carboh);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(NutriologoClienteAsignar.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoClienteAsignar.this);
        x.add(peticion);

    }

    private void conexionBDPasabocas() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Lista_Alimentos_Cliente_Tipo.php?registro="+registroCliente+"&tipo=3";
        url = url.replaceAll(" ", "%20");

        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //String valor = response.getString("Estado");
                                    JSONArray jsonArray = response.getJSONArray("Alimentos");

                                    AdaptadorAlimentoAsignado adapter = new AdaptadorAlimentoAsignado(listaPasabocas);
                                    adapter.contexto= NutriologoClienteAsignar.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        Cantidad =clientes.getString("Cantidad");
                                        Tiempo =clientes.getString("Alimento_Tipo");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                        int cantAli = Integer.parseInt(clientes.getString("Alimento_Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal


                                        listaPasabocas.add(new ItemAlimentoAsignado(Nombre,Marca,""+cant*cantAli,Tiempo,CantidadTipo,"3",""+cant*aliCal));
                                    }

                                    listPasabocas.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(NutriologoClienteAsignar.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoClienteAsignar.this);
        x.add(peticion);

    }

    private void conexionBDCena() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Lista_Alimentos_Cliente_Tipo.php?registro="+registroCliente+"&tipo=2";
        url = url.replaceAll(" ", "%20");

        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //String valor = response.getString("Estado");
                                    JSONArray jsonArray = response.getJSONArray("Alimentos");

                                    AdaptadorAlimentoAsignado adapter = new AdaptadorAlimentoAsignado(listaCena);
                                    adapter.contexto= NutriologoClienteAsignar.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        Cantidad =clientes.getString("Cantidad");
                                        Tiempo =clientes.getString("Alimento_Tipo");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                        int cantAli = Integer.parseInt(clientes.getString("Alimento_Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal


                                        listaCena.add(new ItemAlimentoAsignado(Nombre,Marca,""+cant*cantAli,Tiempo,CantidadTipo,"2",""+cant*aliCal));
                                    }

                                    listCena.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(NutriologoClienteAsignar.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoClienteAsignar.this);
        x.add(peticion);

    }

    private void conexionBDAlmuerzo() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Lista_Alimentos_Cliente_Tipo.php?registro="+registroCliente+"&tipo=1";
        url = url.replaceAll(" ", "%20");

        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //String valor = response.getString("Estado");
                                    JSONArray jsonArray = response.getJSONArray("Alimentos");

                                    AdaptadorAlimentoAsignado adapter = new AdaptadorAlimentoAsignado(listaAlmuerzo);
                                    adapter.contexto= NutriologoClienteAsignar.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        Cantidad =clientes.getString("Cantidad");
                                        Tiempo =clientes.getString("Alimento_Tipo");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                        int cantAli = Integer.parseInt(clientes.getString("Alimento_Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal

                                        listaAlmuerzo.add(new ItemAlimentoAsignado(Nombre,Marca,""+cant*cantAli,Tiempo,CantidadTipo,"1",""+cant*aliCal));
                                    }

                                    listAlmuerzo.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(NutriologoClienteAsignar.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoClienteAsignar.this);
        x.add(peticion);

    }

    private void conexionBDDesayuno() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Lista_Alimentos_Cliente_Tipo.php?registro="+registroCliente+"&tipo=0";
        url = url.replaceAll(" ", "%20");

        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //String valor = response.getString("Estado");
                                    JSONArray jsonArray = response.getJSONArray("Alimentos");

                                    AdaptadorAlimentoAsignado adapter = new AdaptadorAlimentoAsignado(listaDes);
                                    adapter.contexto= NutriologoClienteAsignar.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        Cantidad =clientes.getString("Cantidad");
                                        Tiempo =clientes.getString("Alimento_Tipo");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                        int cantAli = Integer.parseInt(clientes.getString("Alimento_Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal

                                        listaDes.add(new ItemAlimentoAsignado(Nombre,Marca,""+cant*cantAli,Tiempo,CantidadTipo,"0",""+cant*aliCal));
                                    }

                                    listDesayuno.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(NutriologoClienteAsignar.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoClienteAsignar.this);
        x.add(peticion);

    }

    private void clickbtnMasDesayuno() {
        btnMasDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriologoClienteAsignar.this,NutriologoClienteAsignarComida.class);
                String Titulo = "Desayuno";
                intent.putExtra("VARIABLE",Titulo);
                startActivity(intent);
            }
        });
    }

    private void clickbtnMasAlmuerzo() {
        btnMasAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriologoClienteAsignar.this,NutriologoClienteAsignarComida.class);
                String Titulo = "Almuerzo";
                intent.putExtra("VARIABLE",Titulo);
                startActivity(intent);
            }
        });
    }

    private void clickbtnMasCena() {
        btnMasCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriologoClienteAsignar.this,NutriologoClienteAsignarComida.class);
                String Titulo = "Cena";
                intent.putExtra("VARIABLE",Titulo);
                startActivity(intent);
            }
        });
    }


    private void clickbtnPasaBocas() {
        btnMasPasaBocas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriologoClienteAsignar.this,NutriologoClienteAsignarComida.class);
                String Titulo = "Pasa bocas";
                intent.putExtra("VARIABLE",Titulo);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NutriologoClienteAsignar.this,NutriologoPerfil.class);
        startActivity(intent);
    }
}
