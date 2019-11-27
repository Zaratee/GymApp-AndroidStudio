package com.example.carloz.gymapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class FragmentDiario extends Fragment {

    Button btnMasDesayuno, btnMasAlmuerzo, btnMasCena, btnMasPasaBocas, btnEdiatDiario;
    RecyclerView listDesayuno, listAlmuerzo,listCena,listPasabocas;
    ArrayList<ItemAlimentoAsignado> listaPasabocas, listaDes, listaCena, listaAlmuerzo;
    String Nombre,Marca,Cantidad,Tiempo,CantidadTipo, bandera;
    TextView txtvCalorias, txtvProte, txtvCarboh, txtvGrasas, txtvCalTotalDesayuno, txtvCalTotalAlmuerzo, txtvCalTotalCena, txtvCalTotalPasabocas;

    int TCalDesayuno, TCalAlmuerzo, TCalCena, TCalPasaBocas;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diario, container, false);

        btnMasDesayuno = (Button) v.findViewById(R.id.btnAgregarDesayuno_FragmentDiario);
        btnMasAlmuerzo = (Button) v.findViewById(R.id.btnAgregarAlmuerzo_FragmentDiario);
        btnMasCena = (Button) v.findViewById(R.id.btnAgregarCena_FragmentDiario);
        btnMasPasaBocas = (Button) v.findViewById(R.id.btnAgregarPasabocas_FragmentDiario);
        btnEdiatDiario = (Button) v.findViewById(R.id.btnEditarDieta_FragmentDiario);


        listDesayuno = (RecyclerView) v.findViewById(R.id.listvDesayuno_FragmentDiario);
        listAlmuerzo = (RecyclerView) v.findViewById(R.id.listvAlmuerzo_FragmentDiario);
        listCena = (RecyclerView) v.findViewById(R.id.listvCena_FragmentDiario);
        listPasabocas = (RecyclerView) v.findViewById(R.id.listvPasabocas_FragmentDiario);

        txtvCarboh = (TextView) v.findViewById(R.id.txtvCarbh_FragmentDiario);
        txtvGrasas = (TextView) v.findViewById(R.id.txtvGrasas_FragmentDiario);
        txtvProte = (TextView) v.findViewById(R.id.txtvProt_FragmentDiario);
        txtvCalorias = (TextView) v.findViewById(R.id.txtvCalorias_FragmentDiario);

        txtvCalTotalDesayuno = (TextView) v.findViewById(R.id.txtvTotalCaloriasDesayuno_FragmentDiario);
        txtvCalTotalAlmuerzo = (TextView) v.findViewById(R.id.txtvTotalCaloriasALmuerzo_FragmentDiario);
        txtvCalTotalCena = (TextView) v.findViewById(R.id.txtvTotalCaloriasCena_FragmentDiario);
        txtvCalTotalPasabocas = (TextView) v.findViewById(R.id.txtvTotalCaloriasPasabocas_FragmentDiario);

        listaAlmuerzo = new ArrayList<>();
        listaPasabocas = new ArrayList<>();
        listaCena = new ArrayList<>();
        listaDes = new ArrayList<>();

        listDesayuno.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listAlmuerzo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listCena.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listPasabocas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        clickbtnMasDesayuno();
        clickbtnMasAlmuerzo();
        clickbtnMasCena();
        clickTxtvV();
        clickbtnPasaBocas();

        conexionBDInfoDesayuno();
        conexionBDInfoAlmuerzo();
        conexionBDInfoCena();
        conexionBDInfoPasabocas();
        conexionBDvalores();
        conexionBDInfoTotalCal();
        
        btnEdiatDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ClienteAlimEditarDiario.class));
            }
        });

        return v;
    }

    private void conexionBDInfoTotalCal() {
        //Desayuno
        {
            String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Diarios.php?registro="+Login.Registro+"&tipo=0";

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
            String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Diarios.php?registro="+Login.Registro+"&tipo=1";
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
            String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Diarios.php?registro="+Login.Registro+"&tipo=2";
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
            String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Diarios.php?registro="+Login.Registro+"&tipo=3";
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
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Caluclo_Calorias_Diario.php?registro="+Login.Registro;
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
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Diarios.php?registro="+Login.Registro+"&tipo=3";
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
                                        int cantAli = Integer.parseInt(clientes.getString("Alimento_Cantidad"));
                                        listaPasabocas.add(new ItemAlimentoAsignado(Nombre,Marca,""+cant*cantAli,Tiempo,CantidadTipo,"3",""+cant*aliCal));
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
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Diarios.php?registro="+Login.Registro+"&tipo=2";
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
                                        int cantAli = Integer.parseInt(clientes.getString("Alimento_Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal

                                        listaCena.add(new ItemAlimentoAsignado(Nombre,Marca,""+cant*cantAli,Tiempo,CantidadTipo,"2",""+cant*aliCal));
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
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Diarios.php?registro="+Login.Registro+"&tipo=1";
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
                                        int cantAli = Integer.parseInt(clientes.getString("Alimento_Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal

                                        listaAlmuerzo.add(new ItemAlimentoAsignado(Nombre,Marca,""+cant*cantAli,Tiempo,CantidadTipo,"1",""+cant*aliCal));
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
        String url = "http://thegymlife.online/php/cliente/Cliente_Visualizar_Alimentos_Diarios.php?registro="+Login.Registro+"&tipo=0";
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
                                        int cantAli = Integer.parseInt(clientes.getString("Alimento_Cantidad"));
                                        int aliCal = Integer.parseInt(clientes.getString("Alimento_Calorias"));//""+cant*aliCal

                                        listaDes.add(new ItemAlimentoAsignado(Nombre,Marca,""+cant*cantAli,Tiempo,CantidadTipo,"0",""+cant*aliCal));
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

    private void clickbtnMasDesayuno(){
        btnMasDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ClienteBuscarAlimento.class);
                String Titulo = "Desayuno";
                intent.putExtra("VARIABLE",Titulo);
                startActivity(intent);
            }
        });
    }

    private void clickbtnMasAlmuerzo(){
        btnMasAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ClienteBuscarAlimento.class);
                String Titulo = "Almuerzo";
                intent.putExtra("VARIABLE",Titulo);
                startActivity(intent);
            }
        });
    }

    private void clickbtnMasCena(){
        btnMasCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ClienteBuscarAlimento.class);
                String Titulo = "Cena";
                intent.putExtra("VARIABLE",Titulo);
                startActivity(intent);
            }
        });
    }

    private void clickbtnPasaBocas(){
        btnMasPasaBocas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ClienteBuscarAlimento.class);
                String Titulo = "Pasa bocas";
                intent.putExtra("VARIABLE",Titulo);
                startActivity(intent);
            }
        });
    }

    private void clickTxtvV(){
        String stringObtener = "Agregar alimento";
        SpannableString spannOlvidar = new SpannableString(stringObtener);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick( View widget) {
                Intent intent = new Intent(getActivity(),ClienteSolicitudAlimento.class);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(true );
            }
        };
        spannOlvidar.setSpan(clickableSpan,0,16,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
