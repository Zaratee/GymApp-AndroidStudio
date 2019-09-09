package com.example.carloz.gymapp.adaptador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.carloz.gymapp.AdminModificarSelecUsuario;
import com.example.carloz.gymapp.AdminVisualizarQuejaInfo;
import com.example.carloz.gymapp.InstructorMenuCliente;
import com.example.carloz.gymapp.InstructorVisualizarEvaluacion;
import com.example.carloz.gymapp.Login;
import com.example.carloz.gymapp.NutriologoClienteAsignar;
import com.example.carloz.gymapp.NutriologoPerfil;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.admin_menu;
import com.example.carloz.gymapp.items.ItemClienteInstructor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdaptadorInstructorClientesAsignados extends RecyclerView.Adapter<AdaptadorInstructorClientesAsignados.ViewHolderClientes> {

    ArrayList<ItemClienteInstructor> listaClientes;
    public Context contexto;
    FirebaseAuth auth;

    public AdaptadorInstructorClientesAsignados(ArrayList<ItemClienteInstructor> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public ViewHolderClientes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listaclientes,null,false);
        return new ViewHolderClientes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderClientes viewHolderClientes, int i) {
        final int j = i;
        int registro = Integer.parseInt(listaClientes.get(i).getRegistro());
        viewHolderClientes.nombre.setText(listaClientes.get(i).getNombre()+" "+listaClientes.get(i).getApellido());
        viewHolderClientes.registro.setText(listaClientes.get(i).getRegistro());
        auth = FirebaseAuth.getInstance();

        String opcion = contexto.getClass().getSimpleName();
        String estado = listaClientes.get(j).getEstado();
        switch (opcion){
            case "InstructorPerfil":
                Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesClientes/"+listaClientes.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                switch (estado){
                    case "NUEVO":
                        viewHolderClientes.linearLayout.setBackgroundColor(Color.parseColor("#ACFFB6"));
                    break;
                    case "RUTINA":
                        viewHolderClientes.linearLayout.setBackgroundColor(Color.parseColor("#FFF1AC"));
                        break;
                }
                break;
            case "NutriologoPerfil":
                Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesClientes/"+listaClientes.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                break;
            case "EliminarCliente":
                Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesClientes/"+listaClientes.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                break;
            case "EliminarEntrenador":
                Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesInstructor/"+listaClientes.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                break;
            case "EliminarNutriologo":
                Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesNutriologo/"+listaClientes.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                break;
            case "AdminModificarUsuario":
                if (registro>=1000 && registro<3000){
                    Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesClientes/"+registro+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                }else if (registro>=3000 && registro<4000){
                    Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesInstructor/"+listaClientes.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                }else if (registro>=4000 && registro<5000){
                    Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesNutriologo/"+listaClientes.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                }
                break;
        }

        viewHolderClientes.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String opcion = contexto.getClass().getSimpleName();
                String estado = listaClientes.get(j).getEstado();

                switch (opcion){
                    case "InstructorPerfil":
                        switch (estado){
                            case "OK":
                            Intent intentInstructor = new Intent(contexto,InstructorMenuCliente.class);
                            intentInstructor.putExtra("REGISTRO",listaClientes.get(j).getRegistro());
                            contexto.startActivity(intentInstructor);
                            break;
                            case "NUEVO":
                                Intent intentInstructorNuevo = new Intent(contexto,InstructorVisualizarEvaluacion.class);
                                intentInstructorNuevo.putExtra("REGISTRO",listaClientes.get(j).getRegistro());
                                contexto.startActivity(intentInstructorNuevo);
                            break;
                            case "RUTINA":
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(contexto);
                                alertbox.setMessage("Encontrarse con el cliente  "+listaClientes.get(j).getRegistro()+" para revision");
                                alertbox.setTitle("Monitoreo");

                                alertbox.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0,
                                                                int arg1) {
                                            }
                                        });

                                alertbox.setPositiveButton("Recibido",
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0,
                                                                int arg1) {
                                                Intent intentInstructorNuevo = new Intent(contexto,InstructorMenuCliente.class);
                                                intentInstructorNuevo.putExtra("REGISTRO",listaClientes.get(j).getRegistro());
                                                contexto.startActivity(intentInstructorNuevo);
                                            }
                                        });

                                alertbox.show();
                        }

                        break;
                    case "NutriologoPerfil":

                        String email = Login.Registro+"@gymlife.com", password = "GymLife";

                        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                            Toast.makeText(contexto,"Complete todos los campos",Toast.LENGTH_SHORT).show();
                        }else {
                            auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(contexto, "Logeadoooo parse", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(contexto, "alo? " + auth.getUid(), Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(contexto, NutriologoClienteAsignar.class);
                                                intent.putExtra("REGISTRO", listaClientes.get(j).getRegistro());
                                                intent.putExtra("BANDERA", "1");
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                //intent.putExtra("CLIENTE",registroCliente);
                                                contexto.startActivity(intent);
                                            } else {
                                                Toast.makeText(contexto, "Usuario no existe", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                        break;
                    case "EliminarCliente":
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(contexto);
                        alertbox.setMessage("¿Estas seguro que deseas eliminar el usuario "+listaClientes.get(j).getRegistro()+"?");
                        alertbox.setTitle("Confirmación");

                        alertbox.setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                    }
                                });

                        alertbox.setPositiveButton("Eliminar",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        conexionBDELiminarCliente(j);
                                    }
                                });

                        alertbox.show();
                        break;
                    case "EliminarEntrenador":
                        AlertDialog.Builder alertboxx = new AlertDialog.Builder(contexto);
                        alertboxx.setMessage("¿Estas seguro que deseas eliminar el usuario "+listaClientes.get(j).getRegistro()+"?");
                        alertboxx.setTitle("Confirmación");

                        alertboxx.setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        Toast.makeText(contexto, "se va", Toast.LENGTH_SHORT).show();
                                        arg0.cancel();
                                    }
                                });

                        alertboxx.setPositiveButton("Eliminar",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        conexionBDELiminarEntrenador(j);
                                    }
                                });

                        alertboxx.show();
                        break;
                    case "EliminarNutriologo":
                        AlertDialog.Builder alertboxx1 = new AlertDialog.Builder(contexto);
                        alertboxx1.setMessage("¿Estas seguro que deseas eliminar el usuario "+listaClientes.get(j).getRegistro()+"?");
                        alertboxx1.setTitle("Confirmación");

                        alertboxx1.setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        Toast.makeText(contexto, "se va", Toast.LENGTH_SHORT).show();
                                        arg0.cancel();
                                    }
                                });

                        alertboxx1.setPositiveButton("Eliminar",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        conexionBDELiminarNutriologo(j);
                                    }
                                });

                        alertboxx1.show();
                        break;
                    case "AdminModificarUsuario":
                        Intent intentAdminModificar = new Intent(contexto,AdminModificarSelecUsuario.class);
                        intentAdminModificar.putExtra("REGISTRO",listaClientes.get(j).getRegistro());
                        contexto.startActivity(intentAdminModificar);
                        break;
                }

            }
        });

    }
    private void conexionFirebase() {


        }

    private void conexionBDELiminarNutriologo(int j) {
        String url = "http://thegymlife.online/php/admin/Administrador_Eliminar_Nutriologo.php?registro="+listaClientes.get(j).getRegistro();
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
                                    switch (valor){
                                        case "OK":
                                            Toast.makeText(contexto, "Entrenador eliminado", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(contexto,admin_menu.class);
                                            contexto.startActivity(intent);
                                            break;
                                        case "NUTRIOLOGOS":
                                            Toast.makeText(contexto, "Error: Unico nutriologo disponible", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(contexto, "Error", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(contexto,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(contexto);
        x.add(peticion);

    }

    private void conexionBDELiminarEntrenador(int j) {
        String url = "http://thegymlife.online/php/admin/Administrador_Eliminar_Entrenador.php?registro="+listaClientes.get(j).getRegistro();
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
                                    switch (valor){
                                        case "OK":
                                            Toast.makeText(contexto, "Entrenador eliminado", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(contexto,admin_menu.class);
                                            contexto.startActivity(intent);
                                            break;
                                        case "ENTRENADORES":
                                            Toast.makeText(contexto, "Error: Unico entrenador disponible", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "ERROR":
                                            Toast.makeText(contexto, "Error", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(contexto,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(contexto);
        x.add(peticion);

    }

    private void conexionBDELiminarCliente(int j) {
            String url = "http://thegymlife.online/php/admin/Administrador_Eliminar_Cliente.php?registro="+listaClientes.get(j).getRegistro();
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
                                        switch (valor){
                                            case "OK":
                                                Toast.makeText(contexto, "Cliente eliminado", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(contexto,admin_menu.class);
                                                contexto.startActivity(intent);
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
                            Toast.makeText(contexto,"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(contexto);
            x.add(peticion);

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderClientes extends RecyclerView.ViewHolder {

        TextView nombre,registro;
        LinearLayout linearLayout;
        ImageView foto;
        public ViewHolderClientes(@NonNull View itemView) {
            super(itemView);
            foto = (ImageView) itemView.findViewById(R.id.imgvCliente_listaclientes);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_listaclientes);
            registro = (TextView) itemView.findViewById(R.id.txtvRegistro_listaclientes);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_listaclientes);
        }
    }
}
