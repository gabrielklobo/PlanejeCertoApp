package com.example.labdesenvolvimento.planejecerto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class CardapioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);
        final List<String> nomeReceitas = new ArrayList<>();

        Bundle extras = getIntent().getExtras();


        Button vou = (Button) findViewById(R.id.button);
        Button nVou = (Button) findViewById(R.id.button3);


        String url = "http://192.168.43.61:9999/PLCerto/WS/Cardapio/ListarCardapios/" + extras.getString("data", "") + "&" +
                extras.getInt("idUnidade", 0) + "&" + extras.getInt("idServico", 0);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String msg = "";
                try {

                    JSONArray jsonObject = new JSONArray(response);
                    JSONArray result = jsonObject;

                    Log.e("**********", String.valueOf(result.length()));

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject collegeData = result.getJSONObject(i);


                        nomeReceitas.add(result.getJSONObject(i).toString());
                        Log.e("LISTAAAAAAAAAAAAAA ", result.getJSONObject(i).toString());

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

                Log.e("Sync Cardapios", msg);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Erro Sinc Cardapios", "ERRO");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


        nVou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardapioActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        vou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardapioActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void PesquisaCardapio(final Context context) {
        //Sincroniza sincroniza = new Sincroniza(context, 0);
        String url = "";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getJSON(response);
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

    private void getJSON(String response) {
        List<Cardapio> modelList = new ArrayList<>();
        Cardapio model;
        String msg;

        try {
            int i = 0;

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            Log.d("Cardapios JSON LENGHT", String.valueOf(result.length()));

            for (i = 0; i < result.length(); i++) {

                JSONObject collegeData = result.getJSONObject(i);

                model = new Cardapio();

                model.setNomeReceita(collegeData.getString("nomeReceita"));

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
