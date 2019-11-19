package com.example.carloz.gymapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientePerfil extends AppCompatActivity {

    CardView btnProgreso,btnCambiarContraseña,btnCerrarSesion, btnCodigo;
    String stringCuenta, bdRegistro,bdNombre,bdApellido,bdEdad,bdPeso,bdEstatura,bdGrasaCorporal, nutriNombre,nutriApell,
    instNombre, instApellido,codigoCliente, stringRegInfo, stringRegNutri;
    TextView NARegistro, NANombre, NAEdad, NAGrasa, NAEstatura, NAPeso , Estatura, Edad, Nombre, Registro, Grasa, Titulo,
            Peso, Progreso, ActualizarContra, CerrarSesion, RegInst, RegNutr, Codigo;
    ImageView imgPerfil, btnInst, btnNutri;
    Integer valor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnProgreso = (CardView) findViewById(R.id.cardvProgreso_ClientePerfil);
        btnCambiarContraseña = (CardView) findViewById(R.id.cardvActualizarContra_ClientePerfil);
        btnCerrarSesion = (CardView) findViewById(R.id.cardvCerrarSesion_ClientePerfil);
        btnCodigo = (CardView) findViewById(R.id.cardvCodigo_ClientePerfil);

        imgPerfil = (ImageView) findViewById(R.id.imgVFotoCliente_PerfilCliente);

        btnInst = (ImageView) findViewById(R.id.btninfoInst);
        btnNutri = (ImageView) findViewById(R.id.btnInfoNutri);

        NARegistro = (TextView) findViewById(R.id.txtvNoActionCodigo_ClientePerfil);
        NANombre = (TextView) findViewById(R.id.txtvNoActionNombre_ClientePerfil);
        NAEdad = (TextView) findViewById(R.id.txtvNoActionEdad_ClientePerfil);
        NAGrasa = (TextView) findViewById(R.id.txtvNoActionGrasa_ClientePerfil);
        NAEstatura = (TextView) findViewById(R.id.txtvNoActionEstatura_ClientePerfil);
        NAPeso = (TextView) findViewById(R.id.txtvNoActionPeso_ClientePerfil);

        Estatura = (TextView) findViewById(R.id.txtvEstatura_ClientePerfil);
        Edad = (TextView) findViewById(R.id.txtvEdad_ClientePerfil);
        Nombre = (TextView) findViewById(R.id.txtvNombre_ClientePerfil);

        Registro = (TextView) findViewById(R.id.txtvCodigo_ClientePerfil);
        Grasa = (TextView) findViewById(R.id.txtvMasa_ClientePerfil);
        Titulo = (TextView) findViewById(R.id.txtvNoActionTitulo_ClientePerfil);
        Peso = (TextView) findViewById(R.id.txtvPeso_ClientePerfil);

        Progreso = (TextView) findViewById(R.id.txtvProgreso_ClientePerfil);

        ActualizarContra = (TextView) findViewById(R.id.txtvActualizarContra_ClientePerfil);
        CerrarSesion = (TextView) findViewById(R.id.txtvCerrarSesion_ClientePerfil);
        Codigo = (TextView) findViewById(R.id.txtvCodigoo_ClientePerfil);

        RegInst = (TextView) findViewById(R.id.txtvInst_ClientePerfil);
        RegNutr = (TextView) findViewById(R.id.txtvNutri_ClientePerfil);


        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Light = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface Regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        Progreso.setTypeface(Condensed);
        ActualizarContra.setTypeface(Condensed);
        CerrarSesion.setTypeface(Condensed);
        Codigo.setTypeface(Condensed);

        Titulo.setTypeface(Thin);

        NARegistro.setTypeface(Regular);
        NANombre.setTypeface(Regular);
        NAEdad.setTypeface(Regular);
        NAGrasa.setTypeface(Regular);
        NAEstatura.setTypeface(Regular);
        NAPeso.setTypeface(Regular);

        Nombre.setTypeface(Light);
        Estatura.setTypeface(Light);

        Edad.setTypeface(Light);
        Registro.setTypeface(Light);
        Grasa.setTypeface(Light);
        Peso.setTypeface(Light);

        Glide.with(this).load("http://thegymlife.online/php/fotos/imagenesClientes/"+Login.Registro+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgPerfil);


        recibirDatos();
        ClickbtnCerrarSesion();
        ClickbtnCambiarContraseña();
        ClickbtnProgreso();

        if (stringRegInfo.equals("0")){
            RegInst.setText("No asignado");
            btnInst.setVisibility(View.INVISIBLE);
        }else{
            RegInst.setText(stringRegInfo);
            btnInst.setVisibility(View.VISIBLE);

        }
        if (stringRegNutri.equals("0")){
            RegNutr.setText("No asignado");
            btnNutri.setVisibility(View.INVISIBLE);
        }else{
            RegNutr.setText(stringRegNutri);
            btnNutri.setVisibility(View.VISIBLE);

        }

        btnNutri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valor=1;
                alertinfo();
            }
        });

        btnInst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valor=2;
                alertinfo();
            }
        });
        btnCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDCodigo();
            }

        });
    }

    private void conexionBDCodigo() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Obtener_Codigo.php?registro="+Login.Registro;
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
                                    codigoCliente = response.getString("Codigo");
                                    android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(ClientePerfil.this);
                                    alertbox.setMessage("Codigo: "+codigoCliente);
                                    alertbox.setTitle("Código actualizado");


                                    alertbox.setPositiveButton("Recibido",
                                            new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface arg0,
                                                                    int arg1) {
                                                }
                                            });

                                    alertbox.show();

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
                        Toast.makeText(ClientePerfil.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClientePerfil.this);
        x.add(peticion);

    }

    private void alertinfo() {


        AlertDialog.Builder mbuilder = new AlertDialog.Builder(ClientePerfil.this);
        AlertDialog dialog;

        View view = getLayoutInflater().inflate(R.layout.cliente_perfil_instnutri, null);
        TextView txtvNombre = (TextView) view.findViewById(R.id.txtvNombre_Perfil);
        TextView txtvRegistro = (TextView) view.findViewById(R.id.txtvRegistro_Perfil);
        ImageView imgvFoto = (ImageView) view.findViewById(R.id.imgvUsuario_ClientePerfil);


        if (valor==1){
            txtvNombre.setText(nutriNombre+""+nutriApell);
            txtvRegistro.setText(RegNutr.getText().toString());

            Glide.with(ClientePerfil.this).load("http://thegymlife.online/php/fotos/imagenesNutriologo/"+txtvRegistro.getText().toString()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvFoto);

        }else if (valor==2){
            txtvNombre.setText(instNombre+" "+instApellido);
            txtvRegistro.setText(RegInst.getText().toString());

            Glide.with(ClientePerfil.this).load("http://thegymlife.online/php/fotos/imagenesInstructor/"+txtvRegistro.getText().toString()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgvFoto);
        }

        mbuilder.setView(view);
        dialog = mbuilder.create();
        dialog.show();
    }

    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringCuenta =(String)bundle.get("REGISTRO");
        bdRegistro =(String)bundle.get("BDREGISTRO");
        bdNombre =(String)bundle.get("BDNOMBRE");
        bdApellido =(String)bundle.get("BDAPELLIDO");
        bdEdad =(String)bundle.get("BDEDAD");
        bdPeso =(String)bundle.get("BDPESO");
        bdEstatura =(String)bundle.get("BDESTATURA");
        bdGrasaCorporal =(String)bundle.get("BDGRASA");


        stringRegInfo = bundle.getString("REGINST");
        stringRegNutri = bundle.getString("REGNUTRI");

        nutriNombre = bundle.getString("NUTRNOMB");
        nutriApell = bundle.getString("NUTRAPELL");
        instApellido = bundle.getString("INSTAPELL");
        instNombre = bundle.getString("INSTNOMB");


        Registro.setText(bdRegistro);
        Nombre.setText(bdNombre+" "+bdApellido);
        Edad.setText(bdEdad);
        Peso.setText(bdPeso);
        Estatura.setText(bdEstatura);
        Grasa.setText(bdGrasaCorporal);

    }

    private void ClickbtnProgreso() {
        btnProgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientePerfil.this,ClienteAvanceMusculo.class);
                startActivity(intent);
            }
        });
    }

    private void ClickbtnCambiarContraseña() {
        btnCambiarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientePerfil.this,ActualizarContrasena.class);
                String cuenta = "Cliente";
                intent.putExtra("CUENTA", cuenta);
                intent.putExtra("REGISTRO",stringCuenta);
                startActivity(intent);
            }
        });
    }
    private void ClickbtnCerrarSesion() {
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientePerfil.this,Login.class);
                SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("usuario","nada");
                editor.putString("contrasena","nada");
                editor.putString("cuenta","nada");
                editor.putString("clienteRegInstr","nada");
                editor.putString("clienteRegNutri","nada");
                editor.commit();
                startActivity(intent);
            }
        });
    }


}
