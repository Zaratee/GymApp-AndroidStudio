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
    TextView txtvCalorias, txtvProte, txtvCarboh, txtvGrasas;

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

        return v;
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

                                        listaPasabocas.add(new ItemAlimentoAsignado(Nombre,Marca,Cantidad,Tiempo,CantidadTipo,"3"));
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

                                        listaCena.add(new ItemAlimentoAsignado(Nombre,Marca,Cantidad,Tiempo,CantidadTipo,"2"));
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

                                        listaAlmuerzo.add(new ItemAlimentoAsignado(Nombre,Marca,Cantidad,Tiempo,CantidadTipo,"1"));
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

                                        listaDes.add(new ItemAlimentoAsignado(Nombre,Marca,Cantidad,Tiempo,CantidadTipo,"0"));
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
