package com.example.carloz.gymapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ClienteSolicitudAlimento extends AppCompatActivity {

    Spinner spnCantidad;
    Button btnMandarSolicitud;
    TextView txtvTitulo;
    EditText etxtNombre, etxtMarca, etxtTipoAlimento, etxtCantidad, etxtMedida, etxtGrasas, etxtCarboh, etxtProteinas,
            etxtCalorias;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_solicitud_alimento);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvTitulo = (TextView) findViewById(R.id.txtvNoActionTitulo_ClienteSolicitudAlimento);
        
        etxtNombre = (EditText)findViewById(R.id.etxtNombreAlimento_ClienteSolicitudAlimento);
        etxtMarca = (EditText)findViewById(R.id.etxtMarcaAlimento_ClienteSolicitudAlimento);
        etxtTipoAlimento = (EditText)findViewById(R.id.etxtTipoAlimento_ClienteSolicitudAlimento);
        etxtCantidad = (EditText)findViewById(R.id.etxtCantidad_ClienteSolicitudAlimento);
        etxtMedida = (EditText)findViewById(R.id.etxtMedida_ClienteSolicitudAlimento);
        etxtGrasas = (EditText)findViewById(R.id.etxtGrasas_ClienteSolicitudAlimento);
        etxtCarboh = (EditText)findViewById(R.id.etxtCarboh_ClienteSolicitudAlimento);
        etxtProteinas = (EditText)findViewById(R.id.etxtProteinas_ClienteSolicitudAlimento);
        etxtCalorias = (EditText)findViewById(R.id.etxtCalorias_ClienteSolicitudAlimento);
        btnMandarSolicitud = (Button)findViewById(R.id.btnEnviar_ClienteSolicitudAlimento);

        usuario = getIntent().getStringExtra("USUARIO");
        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvTitulo.setTypeface(Thin);

        btnMandarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuario.equals("NUTRIOLOGO")){
                    revisarInfoCliente();
                }else {
                    //Toast.makeText(ClienteSolicitudAlimento.this, "Hola", Toast.LENGTH_SHORT).show();
                    clickBtnMandarSolicitudNutriologo();
                }
            }
        });
        if (!usuario.equals("NUTRIOLOGO")){
            final android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(this);
            alertbox.setMessage(" Nombre\n Marca \n Tipo de alimento \n Cantidad \n Medida");
            alertbox.setTitle("Datos necesarios para enviar la solicitud:");


            alertbox.setPositiveButton("Recibido",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                        }
                    });

            alertbox.show();
        }


    }

    private void revisarInfoCliente() {
        String Nombre = etxtNombre.getText().toString();
        String Marca = etxtMarca.getText().toString();
        String TipoAlimento = etxtTipoAlimento.getText().toString();
        String Cantidad = etxtCantidad.getText().toString();
        String Medida = etxtMedida.getText().toString();

        if (Nombre.isEmpty()){
            etxtNombre.setError("Rellenar Nombre");
        }
        if (Marca.isEmpty()){
            etxtMarca.setError("Rellenar Marca");
        }
        if (TipoAlimento.isEmpty()){
            etxtTipoAlimento.setError("Rellenar Marca");

        }
        if (Cantidad.isEmpty()){
            etxtCantidad.setError("Rellenar Cantidad");
        }
        if (Medida.isEmpty()){
            etxtMedida.setError("Rellenar Medida");
        }
        if (Nombre.isEmpty() && Marca.isEmpty() && TipoAlimento.isEmpty() && Cantidad.isEmpty() && Medida.isEmpty()){
            final android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(this);
            alertbox.setMessage(" Nombre\n Marca \n Tipo de alimento \n Cantidad \n Medida");
            alertbox.setTitle("Datos necesarios para enviar la solicitud:");


            alertbox.setPositiveButton("Recibido",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0,
                                            int arg1) {
                        }
                    });

            alertbox.show();

            return;
        }
        if (Nombre.isEmpty() || Marca.isEmpty() || TipoAlimento.isEmpty() || Cantidad.isEmpty() || Medida.isEmpty()){
        }else{
            //Toast.makeText(this, "ya", Toast.LENGTH_SHORT).show();
            clickBtnMandarSolicitud();

        }

    }


    private void clickBtnMandarSolicitudNutriologo() {

        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Agregar_Alimento.php?nombre="+etxtNombre.getText().toString().trim()
                +"&marca="+etxtMarca.getText().toString().trim()+ "&tipo="+etxtTipoAlimento.getText().toString().trim()
                +"&cantidad="+etxtCantidad.getText().toString().trim()+"&medida="+etxtMedida.getText().toString().trim()
                +"&grasas="+etxtGrasas.getText().toString().trim()+"&carbohidratos="+etxtCarboh.getText().toString()
                +"&proteinas="+etxtProteinas.getText().toString().trim()+"&calorias="+etxtCalorias.getText().toString().trim();
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
                                    switch (valor) {
                                        case "OK":
                                            Toast.makeText(ClienteSolicitudAlimento.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
                                            finish();
                                            break;
                                        case "ERROR":
                                            String Nombre = etxtNombre.getText().toString();
                                            String Marca = etxtMarca.getText().toString();
                                            String TipoAlimento = etxtTipoAlimento.getText().toString();
                                            String Cantidad = etxtCantidad.getText().toString();
                                            String Medida = etxtMedida.getText().toString();
                                            String Grasas = etxtGrasas.getText().toString();
                                            String Carboh = etxtCarboh.getText().toString();
                                            String Proteinas = etxtProteinas.getText().toString();
                                            String Calorias = etxtCalorias.getText().toString();

                                            if (Nombre.isEmpty()){
                                                etxtNombre.setError("Rellenar Nombre");
                                            }
                                            if (Marca.isEmpty()){
                                                etxtMarca.setError("Rellenar Marca");
                                            }
                                            if (TipoAlimento.isEmpty()){
                                                etxtTipoAlimento.setError("Rellenar Marca");

                                            }
                                            if (Cantidad.isEmpty()){
                                                etxtCantidad.setError("Rellenar Cantidad");
                                            }
                                            if (Medida.isEmpty()){
                                                etxtMedida.setError("Rellenar Medida");
                                            }

                                            if (Grasas.isEmpty()){
                                                etxtGrasas.setError("Rellenar Grasa");
                                            }
                                            if (Carboh.isEmpty()){
                                                etxtCarboh.setError("Rellenar Carbohidratos");
                                            }
                                            if (Proteinas.isEmpty()){
                                                etxtProteinas.setError("Rellenar Proteinas");
                                            }
                                            if (Calorias.isEmpty()){
                                                etxtCalorias.setError("Rellenar Calorias");
                                            }



                                            Toast.makeText(ClienteSolicitudAlimento.this, "Rellene los campos", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClienteSolicitudAlimento.this, "Error php", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteSolicitudAlimento.this);
        x.add(peticion);



    }

    private void clickBtnMandarSolicitud(){

                    String url = "http://thegymlife.online/php/cliente/Cliente_Ingresar_Encuesta_Alimento.php?nombre="+etxtNombre.getText().toString().trim()
                            +"&marca="+etxtMarca.getText().toString().trim()+ "&tipo="+etxtTipoAlimento.getText().toString().trim()
                            +"&cantidad="+etxtCantidad.getText().toString().trim()+"&medida="+etxtMedida.getText().toString().trim()
                            +"&grasas="+etxtGrasas.getText().toString().trim()+"&carbohidratos="+etxtCarboh.getText().toString()+"&proteinas="
                            +etxtProteinas.getText().toString().trim()+"&calorias="+etxtCalorias.getText().toString().trim();
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
                                                switch (valor) {
                                                    case "OK":
                                                        Toast.makeText(ClienteSolicitudAlimento.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                        break;
                                                    case "Error":
                                                        Toast.makeText(ClienteSolicitudAlimento.this, "Rellene los campos", Toast.LENGTH_SHORT).show();
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    , new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ClienteSolicitudAlimento.this, "Error php", Toast.LENGTH_SHORT).show();
                                }
                            });
                    RequestQueue x = Volley.newRequestQueue(ClienteSolicitudAlimento.this);
                    x.add(peticion);



    }
}
