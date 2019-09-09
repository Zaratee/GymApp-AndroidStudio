package com.example.carloz.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.zolad.zoominimageview.ZoomInImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentAyudaEjercicio extends Fragment {

    TextView txtvNombre,txtvAparato, txtvMusculos, txtvDescripcion;
    ZoomInImageView imgEjercicio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_cliente_ejercicio_ayuda_ejercicio, container, false);

        txtvNombre = v.findViewById(R.id.txtvNombreEjercicio_ClienteEjercicioAyudaEjercicio);
        txtvAparato = v.findViewById(R.id.txtvNombreAparato_ClienteEjercicioAyudaEjercicio);
        txtvMusculos = v.findViewById(R.id.txtvMusculosImplicados_ClienteEjercicioAyudaEjercicio);
        txtvDescripcion = v.findViewById(R.id.txtvDescripcion_ClienteEjercicioAyudaEjercicio);

        imgEjercicio = v.findViewById(R.id.imgvEjercicio_FragmentAyudaEjercicio);

        Glide.with(getContext()).load("http://thegymlife.online/php/fotos/imagenesEjercicio/"
                +ClienteEjercicio.idEjercicio+".JPG").apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(imgEjercicio);

        conexionBDInfo();
        Toast.makeText(getContext(), ""+ClienteEjercicio.idEjercicio, Toast.LENGTH_SHORT).show();
        return v;
    }

    private void conexionBDInfo() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Informacion_Ejercicio.php?ejercicio="+ClienteEjercicio.idEjercicio;
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
                                            String bdNombre = response.getString("Ejercicio_Nombre");
                                            String bdAparato = response.getString("Ejercicio_Aparato");
                                            String bdArea = response.getString("Ejercicio_Area");
                                            String bdDescripcion = response.getString("Ejercicio_Descripcion");

                                            txtvNombre.setText(bdNombre);
                                            txtvAparato.setText(bdAparato);
                                            txtvMusculos.setText(bdArea);
                                            txtvDescripcion.setText(bdDescripcion);
                                            break;
                                        case "Error":
                                            Toast.makeText(getContext(),"Sin Ejercicio",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(),"Sin Ejercicio",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(getContext());
        x.add(peticion);

    }
}