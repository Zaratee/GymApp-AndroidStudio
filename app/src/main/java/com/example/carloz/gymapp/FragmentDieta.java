package com.example.carloz.gymapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentDieta extends Fragment {

    RecyclerView listDesayuno, listAlmuerzo,listCena,listPasabocas;
    ArrayList<ItemAlimentoAsignado> listaPasabocas, listaDes, listaCena, listaAlmuerzo;
    String Nombre,Marca,Cantidad,Tiempo,CantidadTipo, bandera;
    TextView txtvCalorias, txtvProte, txtvCarboh, txtvGrasas,txtvCalTotalDesayuno, txtvCalTotalAlmuerzo, txtvCalTotalCena, txtvCalTotalPasabocas;

    int TCalDesayuno, TCalAlmuerzo, TCalCena, TCalPasaBocas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dieta, container, false);

        listDesayuno = (RecyclerView) v.findViewById(R.id.listvDesayuno_FragmentDieta);
        listAlmuerzo = (RecyclerView) v.findViewById(R.id.listvAlmuerzo_FragmentDieta);
        listCena = (RecyclerView) v.findViewById(R.id.listvCena_FragmentDieta);
        listPasabocas = (RecyclerView) v.findViewById(R.id.listvPasabocas_FragmentDieta);

        txtvCarboh = (TextView) v.findViewById(R.id.txtvCarbh_FragmentDieta);
        txtvGrasas = (TextView) v.findViewById(R.id.txtvGrasas_FragmentDieta);
        txtvProte = (TextView) v.findViewById(R.id.txtvProt_FragmentDieta);
        txtvCalorias = (TextView) v.findViewById(R.id.txtvCalorias_FragmentDieta);

        txtvCalTotalDesayuno = (TextView) v.findViewById(R.id.txtvCalTotalDesayuno_FragmentDieta);
        txtvCalTotalAlmuerzo = (TextView) v.findViewById(R.id.txtvCalTotalALmuerzo_FragmentDieta);
        txtvCalTotalCena = (TextView) v.findViewById(R.id.txtvCalTotalnCena_FragmentDieta);
        txtvCalTotalPasabocas = (TextView) v.findViewById(R.id.txtvCalTotalPasabocas_FragmentDieta);

        listaAlmuerzo = new ArrayList<>();
        listaPasabocas = new ArrayList<>();
        listaCena = new ArrayList<>();
        listaDes = new ArrayList<>();

        listDesayuno.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listAlmuerzo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listCena.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listPasabocas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        conexionBDInfoDesayuno();
        conexionBDInfoAlmuerzo();
        conexionBDInfoCena();
        conexionBDInfoPasabocas();
        conexionBDvalores();

        conexionBDInfoTotalCal();
        return v;
    }

    private void conexionBDInfoTotalCal() {
        //Desayuno
        {
            String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Dieta.php?registro="+Login.Registro+"&tipo=0";
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
                            Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(getContext());
            x.add(peticion);
        }

        //Almuerzo
        {
            String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Dieta.php?registro="+Login.Registro+"&tipo=1";
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
                            Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(getContext());
            x.add(peticion);

        }

        //Cena
        {
            String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Dieta.php?registro="+Login.Registro+"&tipo=2";
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
                            Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(getContext());
            x.add(peticion);

        }

        //Pasabocas
        {
            String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Dieta.php?registro="+Login.Registro+"&tipo=3";
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
                            Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(getContext());
            x.add(peticion);

        }

    }

    private void conexionBDvalores() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Calculo_Calorias_Dieta.php?registro="+Login.Registro;
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
                        Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(getContext());
        x.add(peticion);

    }
    private void conexionBDInfoPasabocas() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Dieta.php?registro="+Login.Registro+"&tipo=3";
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
                                    adapter.contexto= getContext();
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        Cantidad =clientes.getString("Cantidad");
                                        Tiempo =clientes.getString("Alimento_Tipo");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal

                                        listaPasabocas.add(new ItemAlimentoAsignado(Nombre,Marca,Cantidad,Tiempo,CantidadTipo,"3",""+cant*aliCal));
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
                        Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(getContext());
        x.add(peticion);

    }
    private void conexionBDInfoCena() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Dieta.php?registro="+Login.Registro+"&tipo=2";
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
                                    adapter.contexto= getContext();
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        Cantidad =clientes.getString("Cantidad");
                                        Tiempo =clientes.getString("Alimento_Tipo");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal

                                        listaCena.add(new ItemAlimentoAsignado(Nombre,Marca,Cantidad,Tiempo,CantidadTipo,"2",""+cant*aliCal));
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
                        Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(getContext());
        x.add(peticion);

    }

    private void conexionBDInfoAlmuerzo() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Dieta.php?registro="+Login.Registro+"&tipo=1";
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
                                    adapter.contexto= getContext();
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        Cantidad =clientes.getString("Cantidad");
                                        Tiempo =clientes.getString("Alimento_Tipo");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal

                                        listaAlmuerzo.add(new ItemAlimentoAsignado(Nombre,Marca,Cantidad,Tiempo,CantidadTipo,"1",""+cant*aliCal));
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
                        Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(getContext());
        x.add(peticion);

    }

    private void conexionBDInfoDesayuno() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Dieta.php?registro="+Login.Registro+"&tipo=0";
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
                                    adapter.contexto= getContext();
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        Cantidad =clientes.getString("Cantidad");
                                        Tiempo =clientes.getString("Alimento_Tipo");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        int cant = Integer.parseInt(clientes.getString("Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));


                                        listaDes.add(new ItemAlimentoAsignado(Nombre,Marca,Cantidad,Tiempo,CantidadTipo,"0",""+cant*aliCal));
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
                        Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(getContext());
        x.add(peticion);

    }


}
