package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class InstructorVisualizarEvaluacion extends AppCompatActivity {


    TextView txtvTabaco,txtvAlcohol,txtvMedicamento, Codigo;

    RadioButton radioButtonSiPreg1,radioButtonNoPreg1,radioButtonSiPreg2,radioButtonNoPreg2,radioButtonSiPreg3,radioButtonNoPreg3,radioButtonSiPreg4,
            radioButtonNoPreg4,radioButtonSiPreg5,radioButtonNoPreg5,radioButtonSiPreg6,radioButtonNoPreg6;
    EditText etxtMedicamento, etxtEdad, etxtPeso, etxtEstatura, etxtMasa;
    TextInputLayout txtinputMedicamento;
    Button btnContinuar;
    String stringCuenta;
    TextView txtvTitulo;
    Integer confirmacionBoton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_visualizar_evaluacion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        txtvAlcohol = (TextView) findViewById(R.id.txtvTiempoAlcohol_InstructorVisualizarEvaluacion);
        txtvTabaco = (TextView) findViewById(R.id.txtvTiempoTabaco_InstructorVisualizarEvaluacion);
        Codigo = (TextView) findViewById(R.id.txtvCodigo_InstructorVisualizarEvaluacion);

        radioButtonSiPreg1 = (RadioButton) findViewById(R.id.radioButtonSiPregunta1_InstructorVisualizarEvaluacion);
        radioButtonNoPreg1 = (RadioButton) findViewById(R.id.radioButtonNoPregunta1_InstructorVisualizarEvaluacion);

        radioButtonSiPreg2 = (RadioButton) findViewById(R.id.radioButtonSiPregunta2_InstructorVisualizarEvaluacion);
        radioButtonNoPreg2 = (RadioButton) findViewById(R.id.radioButtonNoPregunta2_InstructorVisualizarEvaluacion);

        radioButtonSiPreg3 = (RadioButton) findViewById(R.id.radioButtonSiPregunta3_InstructorVisualizarEvaluacion);
        radioButtonNoPreg3 = (RadioButton) findViewById(R.id.radioButtonNoPregunta3_InstructorVisualizarEvaluacion);

        radioButtonSiPreg4 = (RadioButton) findViewById(R.id.radioButtonSiPregunta4_InstructorVisualizarEvaluacion);
        radioButtonNoPreg4 = (RadioButton) findViewById(R.id.radioButtonNoPregunta4_InstructorVisualizarEvaluacion);

        radioButtonSiPreg5 = (RadioButton) findViewById(R.id.radioButtonSiPregunta5_InstructorVisualizarEvaluacion);
        radioButtonNoPreg5 = (RadioButton) findViewById(R.id.radioButtonNoPregunta5_InstructorVisualizarEvaluacion);

        radioButtonSiPreg6 = (RadioButton) findViewById(R.id.radioButtonSiPregunta6_InstructorVisualizarEvaluacion);
        radioButtonNoPreg6 = (RadioButton) findViewById(R.id.radioButtonNoPregunta6_InstructorVisualizarEvaluacion);


        txtinputMedicamento = (TextInputLayout) findViewById(R.id.designCualMedicamento_InstructorVisualizarEvaluacion);
        btnContinuar = (Button) findViewById(R.id.btnContinuar_InstructorVisualizarEvaluacion);

        txtvTitulo = (TextView) findViewById(R.id.txtvNoActionTitulo_InstructorVisualizarEvaluacion);
        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvTitulo.setTypeface(Thin);

        etxtMasa = (EditText) findViewById(R.id.etxtMasa_InstructorVisualizarEvaluacion);
        etxtPeso = (EditText) findViewById(R.id.etxtPeso_InstructorVisualizarEvaluacion);
        etxtEstatura = (EditText) findViewById(R.id.etxtTalla_InstructorVisualizarEvaluacion);
        etxtEdad = (EditText) findViewById(R.id.etxtEdad_InstructorVisualizarEvaluacion);
        txtvMedicamento = (TextView) findViewById(R.id.txtvMedicamentoNombre_InstructorVisualizarEvaluacion);

        stringCuenta = getIntent().getStringExtra("REGISTRO");
        conexionBDRecibir();

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmacionBoton == 1 ){
                    Intent intent = new Intent(InstructorVisualizarEvaluacion.this,InstructorMenuCliente.class);
                    intent.putExtra("REGISTRO",stringCuenta);
                    startActivity(intent);
                }else {
                    finish();
                }

            }
        });
    }

    private void conexionBDRecibir() {

        String url = "http://thegymlife.online/php/instructor/Entrenador_Visualizar_Cliente_Encuesta.php?registro="+stringCuenta;

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
                                    String bdCardiovascular = response.getString("P_Cardiovascular");
                                    String bdMusculatura = response.getString("P_Musculatura");
                                    String bdMedicamento = response.getString("P_Medicamento");
                                    String bdMedicamentoNombre = response.getString("Nombre_Medicamento");
                                    String bdTabaco = response.getString("P_Tabaco");
                                    String bdTabacoTiempo = response.getString("Tipo_Tabaco");
                                    String bdAlcohol = response.getString("P_Alcohol");
                                    String bdAlcoholTiempo = response.getString("Tipo_Alcohol");
                                    String bdActividad = response.getString("P_Actividad");
                                    String bdEdad = response.getString("Edad");
                                    String bdPeso = response.getString("Peso");
                                    String bdEstatura = response.getString("Estatura");
                                    String bdGrasa = response.getString("Grasa_Corporal");
                                    String bdNombre = response.getString("Cliente_Nombre");
                                    String bdCodigo = response.getString("Cliente_Codigo");

                                    switch(valor) {
                                        case "OK":
                                            confirmacionBoton = 1;
                                            if (bdCardiovascular.equals("0")){
                                                radioButtonNoPreg1.setChecked(true);
                                                radioButtonSiPreg1.setChecked(false);
                                            }
                                            if (bdCardiovascular.equals("1")){
                                                radioButtonNoPreg1.setChecked(false);
                                                radioButtonSiPreg1.setChecked(true);
                                            }
                                            //=======================================
                                            if (bdMusculatura.equals("0")){
                                                radioButtonNoPreg2.setChecked(true);
                                                radioButtonSiPreg2.setChecked(false);
                                            }
                                            if (bdMusculatura.equals("1")){
                                                radioButtonNoPreg2.setChecked(false);
                                                radioButtonSiPreg2.setChecked(true);
                                            }

                                            //=======================================
                                            if (bdMedicamento.equals("0")){
                                                radioButtonNoPreg3.setChecked(true);
                                                radioButtonSiPreg3.setChecked(false);
                                            }
                                            if (bdMedicamento.equals("1")){
                                                radioButtonNoPreg3.setChecked(false);
                                                radioButtonSiPreg3.setChecked(true);
                                                txtvMedicamento.setText(bdMedicamentoNombre);
                                            }

                                            //=======================================
                                            if (bdTabaco.equals("0")){
                                                radioButtonNoPreg4.setChecked(true);
                                                radioButtonSiPreg4.setChecked(false);
                                            }
                                            if (bdTabaco.equals("1")){
                                                radioButtonNoPreg4.setChecked(false);
                                                radioButtonSiPreg4.setChecked(true);

                                                switch (bdTabacoTiempo) {
                                                    case "0":
                                                        txtvTabaco.setText("Diariamente");
                                                        break;
                                                    case "1":
                                                        txtvTabaco.setText("Semanalmente");
                                                        break;
                                                    case "2":
                                                        txtvTabaco.setText("Mensualmente");
                                                        break;
                                                }
                                            }

                                            //=======================================
                                            if (bdAlcohol.equals("0")){
                                                radioButtonNoPreg5.setChecked(true);
                                                radioButtonSiPreg5.setChecked(false);
                                            }
                                            if (bdAlcohol.equals("1")){
                                                radioButtonNoPreg5.setChecked(false);
                                                radioButtonSiPreg5.setChecked(true);

                                                switch (bdAlcoholTiempo) {
                                                    case "0":
                                                        txtvAlcohol.setText("Diariamente");
                                                        break;
                                                    case "1":
                                                        txtvAlcohol.setText("Semanalmente");
                                                        break;
                                                    case "2":
                                                        txtvAlcohol.setText("Mensualmente");
                                                        break;
                                                }
                                            }
                                            //=======================================
                                            if (bdActividad.equals("0")){
                                                radioButtonNoPreg6.setChecked(true);
                                                radioButtonSiPreg6.setChecked(false);
                                            }
                                            if (bdActividad.equals("1")){
                                                radioButtonNoPreg6.setChecked(false);
                                                radioButtonSiPreg6.setChecked(true);
                                                txtvMedicamento.setText(bdMedicamentoNombre);
                                            }
                                            etxtEdad.setText(bdEdad);
                                            etxtPeso.setText(bdPeso);
                                            etxtMasa.setText(bdGrasa);
                                            etxtEstatura.setText(bdEstatura);
                                            Codigo.setText(bdCodigo);


                                            break;
                                        case "Error":
                                            Toast.makeText(InstructorVisualizarEvaluacion.this,"Fallo de conexión",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(InstructorVisualizarEvaluacion.this,"Evaluación aun no realizada",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorVisualizarEvaluacion.this);
        x.add(peticion);

    }


}
