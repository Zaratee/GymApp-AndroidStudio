package com.example.carloz.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class EncuestaClienteNuevo extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    Spinner listvTabaco,listvAlcohol;

    RadioButton radioButtonSiPreg1,radioButtonNoPreg1,radioButtonSiPreg2,radioButtonNoPreg2,radioButtonSiPreg3,radioButtonNoPreg3,radioButtonSiPreg4,
            radioButtonNoPreg4,radioButtonSiPreg5,radioButtonNoPreg5,radioButtonSiPreg6,radioButtonNoPreg6;
    EditText etxtMedicamento, etxtEdad, etxtPeso, etxtEstatura, etxtMasa;
    TextInputLayout txtinputMedicamento;
    Button btnContinuar;
    String strgEdad, strgPeso, strgEstatura, strgMasa, Tabaco, Alcohol, stringCuenta, preg1="", preg2="", preg3="", preg4="", preg5="", preg6="", medi="adios";
    Integer resCardiovascular, resMosculatura, resMedicamento, resTabaco, resTabacoMenu, resAlcohol, resAlcoholmenu, resActividad;
    TextView txtvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_cliente_nuevo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        listvTabaco = (Spinner) findViewById(R.id.spinnerTabaco_EncuestaClienteNuevo);
        listvAlcohol = (Spinner) findViewById(R.id.spinnerAlcohol_EncuestaClienteNuevo);

        radioButtonSiPreg1 = (RadioButton) findViewById(R.id.radioButtonSiPregunta1_Encuesta);
        radioButtonNoPreg1 = (RadioButton) findViewById(R.id.radioButtonNoPregunta1_Encuesta);

        radioButtonSiPreg2 = (RadioButton) findViewById(R.id.radioButtonSiPregunta2_Encuesta);
        radioButtonNoPreg2 = (RadioButton) findViewById(R.id.radioButtonNoPregunta2_Encuesta);

        radioButtonSiPreg3 = (RadioButton) findViewById(R.id.radioButtonSiPregunta3_Encuesta);
        radioButtonNoPreg3 = (RadioButton) findViewById(R.id.radioButtonNoPregunta3_Encuesta);

        radioButtonSiPreg4 = (RadioButton) findViewById(R.id.radioButtonSiPregunta4_Encuesta);
        radioButtonNoPreg4 = (RadioButton) findViewById(R.id.radioButtonNoPregunta4_Encuesta);

        radioButtonSiPreg5 = (RadioButton) findViewById(R.id.radioButtonSiPregunta5_Encuesta);
        radioButtonNoPreg5 = (RadioButton) findViewById(R.id.radioButtonNoPregunta5_Encuesta);

        radioButtonSiPreg6 = (RadioButton) findViewById(R.id.radioButtonSiPregunta6_Encuesta);
        radioButtonNoPreg6 = (RadioButton) findViewById(R.id.radioButtonNoPregunta6_Encuesta);

        etxtMedicamento = (EditText) findViewById(R.id.etxtCualMedicamento_EncuestaClienteNuevo);
        txtinputMedicamento = (TextInputLayout) findViewById(R.id.designCualMedicamento_EncuestaClienteNuevo);
        btnContinuar = (Button) findViewById(R.id.btnContinuar_EncuestaCliente);

        txtvTitulo = (TextView) findViewById(R.id.txtvNoActionTitulo_EncuestaClienteNuevo);

        etxtMasa = (EditText) findViewById(R.id.etxtMasa_EncuestaClienteNuevo);
        etxtPeso = (EditText) findViewById(R.id.etxtPeso_EncuestaClienteNuevo);
        etxtEstatura = (EditText) findViewById(R.id.etxtTalla_EncuestaClienteNuevo);
        etxtEdad = (EditText) findViewById(R.id.etxtEdad_EncuestaClienteNuevo);
        etxtMedicamento = (EditText) findViewById(R.id.etxtCualMedicamento_EncuestaClienteNuevo);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        btnContinuar.setTypeface(Condensed);
        txtvTitulo.setTypeface(Thin);


        ArrayAdapter<CharSequence> adapterTabaco = ArrayAdapter.createFromResource(EncuestaClienteNuevo.this,R.array.frecuencia,android.R.layout.simple_spinner_item);
        listvTabaco.setAdapter(adapterTabaco);
        ArrayAdapter<CharSequence> adapterAlcohol = ArrayAdapter.createFromResource(EncuestaClienteNuevo.this,R.array.frecuencia,android.R.layout.simple_spinner_item);
        listvAlcohol.setAdapter(adapterAlcohol);

        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cuenta","clienteEncu");
        editor.commit();

        String user = preferences.getString("usuario","nada");
        if (!user.equals("nada")){
            Login.Registro = user;
        }

        Configuracion();
        ClickbtnContinuar();
        recibirDatos();


        listvAlcohol.setOnItemSelectedListener(this);
        listvTabaco.setOnItemSelectedListener(this);


    }

    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringCuenta = Login.Registro;

    }


    private void ClickbtnContinuar() {
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioButtonSiPreg1.isChecked()) {
                    resCardiovascular = 1;
                    preg1 = "hola";

                } else if (radioButtonNoPreg1.isChecked()) {
                    resCardiovascular = 0;
                    preg1 = "hola";
                }

                if (radioButtonSiPreg2.isChecked()) {
                    resMosculatura = 1;
                    preg2 = "hola";
                } else if (radioButtonNoPreg2.isChecked()) {
                    resMosculatura = 0;
                    preg2 = "hola";
                }

                if (radioButtonSiPreg3.isChecked()) {
                    resMedicamento = 1;
                    preg3 = "hola";
                    medi = etxtMedicamento.getText().toString().trim();

                } else if (radioButtonNoPreg3.isChecked()) {
                    resMedicamento = 0;
                    preg3 = "hola";
                }

                if (radioButtonSiPreg4.isChecked()) {
                    resTabaco = 1;
                    preg4 = "hola";
                } else if (radioButtonNoPreg4.isChecked()) {
                    resTabaco = 0;
                    preg4 = "hola";
                }

                if (radioButtonSiPreg5.isChecked()) {
                    resAlcohol = 1;
                    preg5 = "hola";
                } else if (radioButtonNoPreg5.isChecked()) {
                    resAlcohol = 0;
                    preg5 = "hola";
                }

                if (radioButtonSiPreg6.isChecked()) {
                    resActividad = 1;
                    preg6 = "hola";
                } else if (radioButtonNoPreg6.isChecked()) {
                    preg6 = "hola";
                    resActividad = 0;
                }

                Tabaco = listvTabaco.getSelectedItem().toString();

                switch (Tabaco) {
                    case "Diariamente":
                        resTabacoMenu = 0;
                        break;
                    case "Semanalmente":
                        resTabacoMenu = 1;
                        break;
                    case "Mensualmente":
                        resTabacoMenu = 2;
                        break;
                }

                if (radioButtonSiPreg5.isChecked()) {
                    resAlcohol = 1;
                } else if (radioButtonNoPreg5.isChecked()) {
                    resAlcohol = 0;
                }

                Alcohol = listvAlcohol.getSelectedItem().toString();
                switch (Alcohol) {
                    case "Diariamente":
                        resAlcoholmenu = 0;
                        break;
                    case "Semanalmente":
                        resAlcoholmenu = 1;
                        break;
                    case "Mensualmente":
                        resAlcoholmenu = 2;
                        break;
                }



                if (radioButtonSiPreg6.isChecked()) {
                    resActividad = 1;
                } else if (radioButtonNoPreg6.isChecked()) {
                    resActividad = 0;
                }

                if (etxtMasa.getText().toString().trim().equalsIgnoreCase("")) {
                    etxtMasa.setError("Rellenar Grasa");
                }else if (Integer.parseInt(etxtMasa.getText().toString())<2){
                    etxtMasa.setError("Porcentaje mínimo 2%");
                }
                if (etxtEstatura.getText().toString().trim().equalsIgnoreCase("")) {
                    etxtEstatura.setError("Rellenar Estatura");
                }else if (Integer.parseInt(etxtEstatura.getText().toString())<100){
                    etxtEstatura.setError("Estatura mínima 100cm");
                }
                if (etxtPeso.getText().toString().trim().equalsIgnoreCase("")) {
                    etxtPeso.setError("Rellenar Apellido");
                }else if (Integer.parseInt(etxtPeso.getText().toString())<35){
                    etxtPeso.setError("Peso mínimo 35kg");
                }

                if (etxtEdad.getText().toString().trim().equalsIgnoreCase("")) {
                    etxtEdad.setError("Rellenar Edad");
                }else if (Integer.parseInt(etxtEdad.getText().toString())<14){
                    etxtEdad.setError("Edad mínima 14");
                }

                strgMasa = etxtMasa.getText().toString().trim();
                strgEstatura = etxtEstatura.getText().toString().trim();
                strgPeso = etxtPeso.getText().toString().trim();
                strgEdad = etxtEdad.getText().toString().trim();
                medi = etxtMedicamento.getText().toString().trim();


                if(strgMasa.isEmpty() || strgEstatura.isEmpty() || strgPeso.isEmpty() || strgEdad.isEmpty()
                        || preg1.isEmpty() || preg2.isEmpty() || preg3.isEmpty() || preg4.isEmpty() || preg5.isEmpty()
                        || preg6.isEmpty() || medi.isEmpty() &&  !radioButtonNoPreg3.isChecked()){
                    Toast.makeText(EncuestaClienteNuevo.this,"Completar los campos",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if (Integer.parseInt(etxtEdad.getText().toString())<14){
                        etxtEdad.setError("Edad mínima 14");
                        return;
                    }
                    if (Integer.parseInt(etxtEstatura.getText().toString())<100){
                        etxtEstatura.setError("Estatura mínima 100cm");
                        return;
                    }
                    if (Integer.parseInt(etxtPeso.getText().toString())<35){
                        etxtPeso.setError("Peso mínimo 35kg");
                        return;
                    }
                    if (Integer.parseInt(etxtMasa.getText().toString())<2){
                        etxtMasa.setError("Porcentaje mínimo 2%");
                        return;
                    }

                    Toast.makeText(EncuestaClienteNuevo.this,"Conectado",Toast.LENGTH_SHORT).show();
                }

                if (strgMasa.isEmpty() || strgEstatura.isEmpty() || strgPeso.isEmpty() || strgEdad.isEmpty()
                        || preg1.isEmpty() || preg2.isEmpty() || preg3.isEmpty() || preg4.isEmpty() || preg5.isEmpty()
                        || preg6.isEmpty() || medi.isEmpty() && radioButtonSiPreg3.isChecked()) {
                    conexionBD();
                    return;
                }else{
                    conexionBD();
                }

            }
        });
    }



    private void Configuracion(){
        //pregunta3
        radioButtonSiPreg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etxtMedicamento.setVisibility(View.VISIBLE);
                txtinputMedicamento.setVisibility(View.VISIBLE);
                medi="";
            }
        });
        radioButtonNoPreg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etxtMedicamento.setVisibility(View.INVISIBLE);
                txtinputMedicamento.setVisibility(View.INVISIBLE);
            }
        });
        //pregunta4
        radioButtonSiPreg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listvTabaco.setVisibility(View.VISIBLE);
                listvTabaco.setVisibility(View.VISIBLE);
            }
        });
        radioButtonNoPreg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listvTabaco.setVisibility(View.INVISIBLE);
                listvTabaco.setVisibility(View.INVISIBLE);
            }
        });
        //pregunta5
        radioButtonSiPreg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listvAlcohol.setVisibility(View.VISIBLE);
                listvAlcohol.setVisibility(View.VISIBLE);
            }
        });
        radioButtonNoPreg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listvAlcohol.setVisibility(View.INVISIBLE);
                listvAlcohol.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void conexionBD() {

        String url = "http://thegymlife.online/php/cliente/Cliente_Ingresar_Encuesta.php?registro="+stringCuenta+"&cardiovascular="+resCardiovascular+
                "&mosculatura="+resMosculatura+"&medicamento="+resMedicamento+"&medicamento_Select="+etxtMedicamento.getText().toString()+"&tabaco="
                +resTabaco+"&tabaco_Select="+resTabacoMenu+"&alcohol="+resAlcohol+"&alcohol_Select=" +resAlcoholmenu+"&actividad="+resActividad+"&edad="
                +etxtEdad.getText().toString()+"&peso="+etxtPeso.getText().toString()+"&estatura="+etxtEstatura.getText().toString()+"&grasa="+etxtMasa.getText().toString();
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
                                            Intent intent = new Intent(EncuestaClienteNuevo.this,EncuestaCodigoCliente.class);
                                            intent.putExtra("REGISTRO",stringCuenta);
                                            startActivity(intent);
                                            break;
                                        case "ERROR":
                                            Toast.makeText(EncuestaClienteNuevo.this,"Usuario no existe",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EncuestaClienteNuevo.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(EncuestaClienteNuevo.this);
        x.add(peticion);
    }

    public void onBackPressed(){

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

