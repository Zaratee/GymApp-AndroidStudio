package com.example.carloz.gymapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import android.widget.Toast;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Permission;


public class Login extends AppCompatActivity {


    EditText etxtUsuario, etxtContraseña;
    TextView txtVObtener_contraseña;
    Button btnIngresar;
    CheckBox chckbxSesion;


    public static String Registro="0000";

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtVObtener_contraseña = (TextView) findViewById(R.id.txtvObteninf_loginActivity);
        btnIngresar = (Button) findViewById(R.id.btnIngresar_loginActivity);

        etxtUsuario = (EditText) findViewById(R.id.etxtUsuario_loginActivity);
        etxtContraseña = (EditText) findViewById(R.id.etxtContraseña_loginActivity);

        chckbxSesion = (CheckBox) findViewById(R.id.chkboxMantenerSesion_loginActivity);

        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");
        btnIngresar.setTypeface(Condensed);

        auth = FirebaseAuth.getInstance();

        /*
        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario","nada");
        editor.commit();*/

        cargarPreferencias();
        clickBtn_Ingresar();
        clickTxtvV();


    }

private void cargarPreferencias() {
SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);

String user = preferences.getString("usuario","nada");
String pass = preferences.getString("contrasena","nada");
String cuenta = preferences.getString("cuenta","nada");

if (user.equals("nada")){
    //Toast.makeText(this, "nada", Toast.LENGTH_SHORT).show();
}else {
    switch (cuenta){
        case "instructor":
            startActivity(new Intent(Login.this,InstructorPerfil.class) );
            break;
        case "nutriologo":
            startActivity(new Intent(Login.this,NutriologoPerfil.class) );
            break;
        case "admin":
            startActivity(new Intent(Login.this,admin_menu.class) );
            break;
        case "cliente":
            startActivity(new Intent(Login.this,ClienteMenu.class) );
            break;
        case "clientenuevo":
            startActivity(new Intent(Login.this,ActualizarContraCliente.class) );
            break;
        case "contraInstructor":
            Intent intentContraInst = new Intent(Login.this,ActualizarContrasena.class);
            intentContraInst.putExtra("REGISTRO",user);
            intentContraInst.putExtra("CUENTA","contraInstructor");
            startActivity(intentContraInst);
            break;
        case "contraNutriologo":
            Intent intentContraNutri = new Intent(Login.this,ActualizarContrasena.class);
            intentContraNutri.putExtra("REGISTRO",user);
            intentContraNutri.putExtra("CUENTA","contraNutriologo");
            startActivity(intentContraNutri);
            break;
        case "contraCliente":
            Intent intentContraCliente = new Intent(Login.this,ActualizarContrasena.class);
            intentContraCliente.putExtra("REGISTRO",user);
            intentContraCliente.putExtra("CUENTA","contraCliente");
            startActivity(intentContraCliente);
            break;
        case "clienteEncu":
            startActivity(new Intent(Login.this,EncuestaClienteNuevo.class));
            break;
        case "clienteEncuCodigo":
            startActivity(new Intent(Login.this,EncuestaCodigoCliente.class));

    }
}
}

    private void guardarPreferencias(String cuenta) {
        SharedPreferences preferences = getSharedPreferences("Cuemta",Context.MODE_PRIVATE);

        String usuario = etxtUsuario.getText().toString();
        String contra = etxtContraseña.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario",usuario);
        editor.putString("contrasena",contra);
        editor.putString("cuenta",cuenta);
        editor.commit();
    }

    private void camposllenos() {
        if(etxtUsuario.getText().toString().trim().equalsIgnoreCase("")){
            etxtUsuario.setError("Rellenar Usuario");
        }
        if(etxtContraseña.getText().toString().trim().equalsIgnoreCase("")){
            etxtContraseña.setError("Rellenar Contraseña");
        }
    }


    private void clickBtn_Ingresar() {

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = etxtUsuario.getText().toString()+"@gymlife.com";
                String txtPassword = etxtContraseña.getText().toString();
                if (txtEmail.isEmpty() || txtPassword.isEmpty()){
                    camposllenos();
                    return;
                }else{
                    conexionBD();
                    camposllenos();
                }

            }
        });
    }

    private void clickTxtvV() {
        String stringObtener = "Obtener informacion";
        SpannableString spannOlvidar = new SpannableString(stringObtener);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                openDialog();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(true);
            }
        };
        spannOlvidar.setSpan(clickableSpan, 0, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtVObtener_contraseña.setText(spannOlvidar);
        txtVObtener_contraseña.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void openDialog() {
        DialogBox dialogBox = new DialogBox();
        dialogBox.show(getSupportFragmentManager(), "Dialogo contraseña");
    }

    public void onBackPressed() {
    }

    private void conexionBD() {
final Intent intentClienteNuevo = new Intent(Login.this,ActualizarContraCliente.class);
final Intent intentClienteNuevoCodigo = new Intent(Login.this,EncuestaCodigoCliente.class);
final Intent intentClienteViejo = new Intent(Login.this,ClienteMenu.class);
final Intent intentInstructor = new Intent(Login.this,InstructorPerfil.class);
final Intent intentNutriologo = new Intent(Login.this,NutriologoPerfil.class);
final Intent intentAdministrador = new Intent(Login.this,admin_menu.class);
String url = "http://thegymlife.online/php/sistema/login_consulta.php?registro="+ etxtUsuario.getText().toString()+
        "&contrasena="+etxtContraseña.getText().toString();
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
    case "CODIGO":
        Registro = etxtUsuario.getText().toString();
        intentClienteNuevoCodigo.putExtra("REGISTRO",etxtUsuario.getText().toString());
        if (chckbxSesion.isChecked()){
            guardarPreferencias("clienteEncuCodigo");
        }
        startActivity(intentClienteNuevoCodigo);
        break;
    case "OK":

        final int registro  = Integer.parseInt(etxtUsuario.getText().toString());
        if (registro>=3000 && registro<4000)
        {
            Registro = etxtUsuario.getText().toString();
            intentInstructor.putExtra("REGISTRO",etxtUsuario.getText().toString());

            if (chckbxSesion.isChecked()){
                guardarPreferencias("instructor");
            }
            startActivity(intentInstructor);

        }else  if (registro>=4000 && registro<5000)
        {
            Registro = etxtUsuario.getText().toString();
            intentNutriologo.putExtra("REGISTRO",etxtUsuario.getText().toString());
            if (chckbxSesion.isChecked()){
                guardarPreferencias("nutriologo");
            }
            startActivity(intentNutriologo);

        }else  if (registro>=5000 && registro<6000)
        {
            Registro = etxtUsuario.getText().toString();
            intentAdministrador.putExtra("REGISTRO",etxtUsuario.getText().toString());
            if (chckbxSesion.isChecked()){
                guardarPreferencias("admin");
            }
            startActivity(intentAdministrador);
        }

        break;
    case "CLIENTE":
        Registro = etxtUsuario.getText().toString();
        intentClienteViejo.putExtra("REGISTRO",etxtUsuario.getText().toString());
        if (chckbxSesion.isChecked()){
            guardarPreferencias("cliente");
        }
        startActivity(intentClienteViejo);

        break;
    case "NUEVO":
        Registro = etxtUsuario.getText().toString();
        intentClienteNuevo.putExtra("REGISTRO",etxtUsuario.getText().toString());
        if (chckbxSesion.isChecked()){
            guardarPreferencias("clientenuevo");
        }
        startActivity(intentClienteNuevo);

        break;
    case "CONTRASENA":

        final int registroo  = Integer.parseInt(etxtUsuario.getText().toString());
        if (registroo>=3000 && registroo<4000)
        {
            Registro = etxtUsuario.getText().toString();
            Intent intentContraInst = new Intent(Login.this,ActualizarContrasena.class);
            intentContraInst.putExtra("REGISTRO",etxtUsuario.getText().toString());
            intentContraInst.putExtra("CUENTA","contraInstructor");

            if (chckbxSesion.isChecked()){
                guardarPreferencias("contraInstructor");
            }
            startActivity(intentContraInst);

        }else  if (registroo>=4000 && registroo<5000)
        {
            Registro = etxtUsuario.getText().toString();
            Intent intentContraNutri = new Intent(Login.this,ActualizarContrasena.class);
            intentContraNutri.putExtra("REGISTRO",etxtUsuario.getText().toString());
            intentContraNutri.putExtra("CUENTA","contraNutriologo");

            if (chckbxSesion.isChecked()){
                guardarPreferencias("contraNutriologo");
            }
            startActivity(intentContraNutri);

        }else if (registroo>=1000 && registroo<3000)
        {
            Registro = etxtUsuario.getText().toString();
            Intent intentContraCliente = new Intent(Login.this,ActualizarContrasena.class);
            intentContraCliente.putExtra("REGISTRO",etxtUsuario.getText().toString());
            intentContraCliente.putExtra("CUENTA","contraCliente");

            if (chckbxSesion.isChecked()){
                guardarPreferencias("contraCliente");
            }
            startActivity(intentContraCliente);
        }
        break;
    case "ERROR":
        Toast.makeText(Login.this,"Usuario no existe",Toast.LENGTH_SHORT).show();
        break;
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
            Toast.makeText(Login.this,error.getMessage(),Toast.LENGTH_SHORT).show();
        }
    });
        RequestQueue x = Volley.newRequestQueue(Login.this);
        x.add(peticion);
    }
    }

