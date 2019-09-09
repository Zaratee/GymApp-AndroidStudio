package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ClienteDietaDiarioEdit extends AppCompatActivity {

    TextView txtvMarca, txtvCarboH, txtvProte, txtvCalorias, txtvGrasas, txtvTipo, txtvNombre, txtvMedida;
    String stringCantidad, stringTiempo, stringID, stringLista,stringTipo;
    EditText etxtCantidad;
    RadioButton rdbtnDesayuno, rdbtnAlmuerzo, rdbtnCena, rdbtnPasaBocas;
    Button btnEditar, btnEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_dieta_diario_edit);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        txtvMarca = findViewById(R.id.txtvMarca_ClienteDietaDiarioEdit);
        txtvCarboH = findViewById(R.id.txtvCarbh_ClienteDietaDiarioEdit);
        txtvProte = findViewById(R.id.txtvProteinas_ClienteDietaDiarioEdit);
        txtvCalorias = findViewById(R.id.txtvCalorias_ClienteDietaDiarioEdit);
        txtvGrasas = findViewById(R.id.txtvGrasas_ClienteDietaDiarioEdit);
        txtvTipo = findViewById(R.id.txtvTipo_ClienteDietaDiarioEdit);
        txtvNombre = findViewById(R.id.txtvAlimentoNombre_ClienteDietaDiarioEdit);
        btnEditar = findViewById(R.id.btnEditar_ClienteDietaDiarioEdit);
        etxtCantidad = findViewById(R.id.etxtCantidad_ClienteDietaDiarioEdit);
        txtvMedida = findViewById(R.id.txtvMedida_ClienteDietaDiarioEdit);
        btnEliminar = findViewById(R.id.btnEliminar_ClienteDietaDiarioEdit);

        rdbtnDesayuno = findViewById(R.id.radiobtnDesayuno_ClienteDietaDiarioEdit);
        rdbtnAlmuerzo = findViewById(R.id.radiobtnAlmuerzo_ClienteDietaDiarioEdit);
        rdbtnCena = findViewById(R.id.radiobtnCena_ClienteDietaDiarioEdit);
        rdbtnPasaBocas = findViewById(R.id.radiobtnPasabocas_ClienteDietaDiarioEdit);

        recibirDatos();
        BDinfoAlimento();
        Toast.makeText(this, ""+stringLista, Toast.LENGTH_SHORT).show();


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conBDEliminarAlimento();
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbtnDesayuno.isChecked()) {
                    stringTipo = "0";
                }
                if (rdbtnAlmuerzo.isChecked()){
                    stringTipo="1";
                }
                if (rdbtnCena.isChecked()){
                    stringTipo="2";
                }
                if (rdbtnPasaBocas.isChecked()){
                    stringTipo="3";
                }

                conBDEditarAlimento();
            }
        });


    }
    private void conBDEditarAlimento() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Modificar_Alimento.php?lista="+stringLista+
                "&cantidad="+etxtCantidad.getText().toString().trim()+"&tipo="+stringTipo;
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
                                    String valor = response.getString("Estado");
                                    switch(valor) {
                                        case "OK":

                                            Toast.makeText(ClienteDietaDiarioEdit.this,"Alimento editado",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ClienteDietaDiarioEdit.this,ClienteAlimEditarDiario.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(ClienteDietaDiarioEdit.this,"Rellenar los campos",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ClienteDietaDiarioEdit.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteDietaDiarioEdit.this);
        x.add(peticion);

    }

    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringTiempo = (String) bundle.get("HORA");
        stringID = (String) bundle.get("ID");
        stringCantidad =(String) bundle.get("CANTIDAD");
        stringLista = (String) bundle.get("LISTA");
        etxtCantidad.setText(stringCantidad);
        if (stringTiempo.equals("0")){
            rdbtnDesayuno.setChecked(true);
            rdbtnAlmuerzo.setChecked(false);
            rdbtnCena.setChecked(false);
            rdbtnPasaBocas.setChecked(false);
        }else if (stringTiempo.equals("1")){
            rdbtnDesayuno.setChecked(false);
            rdbtnAlmuerzo.setChecked(true);
            rdbtnCena.setChecked(false);
            rdbtnPasaBocas.setChecked(false);
        }else if (stringTiempo.equals("2")){
            rdbtnDesayuno.setChecked(false);
            rdbtnAlmuerzo.setChecked(false);
            rdbtnCena.setChecked(true);
            rdbtnPasaBocas.setChecked(false);
        }else if (stringTiempo.equals("3")){
            rdbtnDesayuno.setChecked(false);
            rdbtnAlmuerzo.setChecked(false);
            rdbtnCena.setChecked(false);
            rdbtnPasaBocas.setChecked(true);
        }

    }

    private void conBDEliminarAlimento() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Eliminar_Alimento_Diario.php?lista="+stringLista;
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
                                    String valor = response.getString("Estado");
                                    switch(valor) {
                                        case "OK":

                                            Toast.makeText(ClienteDietaDiarioEdit.this,"Alimento eliminado",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ClienteDietaDiarioEdit.this,ClienteAlimentacion.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(ClienteDietaDiarioEdit.this,"Alimento no eliminado",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ClienteDietaDiarioEdit.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteDietaDiarioEdit.this);
        x.add(peticion);

    }



    private void BDinfoAlimento() {

        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Alimento_Cliente.php?lista="+stringID;
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
                                    String nombre = response.getString("Alimento_Nombre");
                                    String valor = response.getString("Estado");
                                    String Marca = response.getString("Alimento_Marca");
                                    String CarboH = response.getString("Alimento_Carbohidratos");
                                    String Prote = response.getString("Alimento_Proteinas");
                                    String Grasas = response.getString("Alimento_Grasas");
                                    String Tipo = response.getString("Alimento_Tipo");
                                    String Calorias = response.getString("Alimento_Calorias");
                                    String Medida = response.getString("Alimento_Medida");
                                    switch(valor) {
                                        case "OK":
                                            txtvNombre.setText(nombre);
                                            txtvCalorias.setText(Calorias);
                                            txtvCarboH.setText(CarboH);
                                            txtvGrasas.setText(Grasas);
                                            txtvProte.setText(Prote);
                                            txtvMarca.setText(Marca);
                                            txtvTipo.setText(Tipo);
                                            txtvMedida.setText(Medida);
                                            break;
                                        case "Error":
                                            Toast.makeText(ClienteDietaDiarioEdit.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ClienteDietaDiarioEdit.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteDietaDiarioEdit.this);
        x.add(peticion);



    }
}
