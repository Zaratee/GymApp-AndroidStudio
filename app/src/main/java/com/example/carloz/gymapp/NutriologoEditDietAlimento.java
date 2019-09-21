package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
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

public class NutriologoEditDietAlimento extends AppCompatActivity {
    
    TextView txtvMarca, txtvCarboH, txtvProte, txtvCalorias, txtvGrasas, txtvTipo, txtvNombre, txtvMedida;
    String stringCantidad, stringTiempo, stringID, stringLista,stringTipo;
    EditText etxtCantidad;
    RadioButton rdbtnDesayuno, rdbtnAlmuerzo, rdbtnCena, rdbtnPasaBocas;
    Button btnEditar, btnEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutriologo_edit_diet_alimento);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvMarca = findViewById(R.id.txtvMarca_NutriologoEditDietAlimento);
        txtvCarboH = findViewById(R.id.txtvCarbh_NutriologoEditDietAlimento);
        txtvProte = findViewById(R.id.txtvProteinas_NutriologoEditDietAlimento);
        txtvCalorias = findViewById(R.id.txtvCalorias_NutriologoEditDietAlimento);
        txtvGrasas = findViewById(R.id.txtvGrasas_NutriologoEditDietAlimento);
        txtvTipo = findViewById(R.id.txtvTipo_NutriologoEditDietAlimento);
        txtvNombre = findViewById(R.id.txtvAlimentoNombre_NutriologoEditDietAlimento);
        btnEditar = findViewById(R.id.btnEditar_NutriologoEditDietAlimento);
        etxtCantidad = findViewById(R.id.etxtCantidad_NutriologoEditDietAlimento);
        txtvMedida = findViewById(R.id.txtvMedida_NutriologoEditDietAlimento);
        btnEliminar = findViewById(R.id.btnEliminar_NutriologoEditDietAlimento);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvNombre.setTypeface(Thin);

        rdbtnDesayuno = findViewById(R.id.radiobtnDesayuno_NutriologoEditDietAlimento);
        rdbtnAlmuerzo = findViewById(R.id.radiobtnAlmuerzo_NutriologoEditDietAlimento);
        rdbtnCena = findViewById(R.id.radiobtnCena_NutriologoEditDietAlimento);
        rdbtnPasaBocas = findViewById(R.id.radiobtnPasabocas_NutriologoEditDietAlimento);

        recibirDatos();
        BDinfoAlimento();
        //Toast.makeText(this, ""+stringLista, Toast.LENGTH_SHORT).show();


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
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Modificar_Alimento_Cliente.php?lista="+stringLista+
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

                                            Toast.makeText(NutriologoEditDietAlimento.this,"Alimento editado",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(NutriologoEditDietAlimento.this,NutriologoEditarDieta.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(NutriologoEditDietAlimento.this,"Rellenar los campos",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NutriologoEditDietAlimento.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoEditDietAlimento.this);
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
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Eliminar_Alimento_Cliente.php?lista="+stringLista;
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

                                            Toast.makeText(NutriologoEditDietAlimento.this,"Alimento eliminado",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(NutriologoEditDietAlimento.this,NutriologoEditarDieta.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "Error":
                                            Toast.makeText(NutriologoEditDietAlimento.this,"Alimento no eliminado",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NutriologoEditDietAlimento.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoEditDietAlimento.this);
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
                                            Toast.makeText(NutriologoEditDietAlimento.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NutriologoEditDietAlimento.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoEditDietAlimento.this);
        x.add(peticion);



    }
}
