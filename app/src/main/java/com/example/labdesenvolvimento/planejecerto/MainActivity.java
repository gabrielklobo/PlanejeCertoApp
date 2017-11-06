package com.example.labdesenvolvimento.planejecerto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.Cardapio;

public class MainActivity extends Activity {
    static List<String> unidade = new ArrayList<>();
    static List<String> servico = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button consultar = (Button) findViewById(R.id.button2);
        Spinner listaUnidade = (Spinner) findViewById(R.id.spinner);
        Spinner listaServico = (Spinner) findViewById(R.id.spinner2);
        Spinner listaData = (Spinner) findViewById(R.id.spinner3);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardapioActivity.class);
                startActivity(intent);
            }
        });

        listar(getApplicationContext(),"ListaUnidades");


    }

    public static void listar(final Context context, final String busca) {
        //Sincroniza sincroniza = new Sincroniza(context, 0);
        String url = "http://172.31.1.223:9999/WebService/WS/Cardapio/" + busca;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getJSON(response, busca);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Erro Sinc Cardapios", "ERRO");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        //requestQueue.stop();

    }

    private static void getJSON(String response, String busca) {
        List<String> modelList = new ArrayList<>();
        String msg;

        try {
            int i = 0;

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            Log.d("Cardapios JSON LENGHT", String.valueOf(result.length()));

            for (i = 0; i < result.length(); i++) {
                JSONObject collegeData = result.getJSONObject(i);
                if (busca.equalsIgnoreCase("ListaUnidades")){
                    unidade.add(result.getJSONObject(i).toString());
                }
                else if(busca.equalsIgnoreCase("ListaSevicos")){
                    servico.add(result.getJSONObject(i).toString());
                }
            }

            msg = "Sucesso! Cardapios Sincronizados. " + String.valueOf(result.length());

        } catch (
                JSONException e
                )

        {
            msg = "Erro! Sincronização Cardapios JSON " + e.getMessage();
        } catch (
                Exception e
                )

        {
            msg = "Erro! Sincronização Cardapios Exception " + e.getMessage();
        }

        Log.d("Sync Cardapios", msg);

    }
}

