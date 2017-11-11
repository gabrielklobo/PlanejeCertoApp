package com.example.labdesenvolvimento.planejecerto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

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
import Model.Servico;
import Model.Unidade;

public class MainActivity extends Activity {
    static List<Unidade> unidadeL = new ArrayList<>();
    static List<Servico> servicoL = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button consultar = (Button) findViewById(R.id.button2);
        final Spinner listaUnidade = (Spinner) findViewById(R.id.spinner);
        final Spinner listaServico = (Spinner) findViewById(R.id.spinner3);
//        final Spinner listaData = (Spinner) findViewById(R.id.spinner3);

        listar(this, "ListaUnidades");
        listar(this, "ListaServicos");

        ArrayAdapter<Unidade> spinnerArrayAdapterUni = new ArrayAdapter<Unidade>(this, android.R.layout.simple_spinner_dropdown_item, unidadeL);



        spinnerArrayAdapterUni.setDropDownViewResource(android.R.layout.simple_spinner_item);
        listaUnidade.setAdapter(spinnerArrayAdapterUni);


        ArrayAdapter<Servico> spinnerArrayAdapterServ = new ArrayAdapter<Servico>(this, android.R.layout.simple_spinner_dropdown_item, servicoL);

        spinnerArrayAdapterServ.setDropDownViewResource(android.R.layout.simple_spinner_item);
        listaServico.setAdapter(spinnerArrayAdapterServ);

//
//        consultar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Create the text message with a string
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra("idUnidade", listaUnidade.getSelectedItem().toString());
//                sendIntent.putExtra("idServico", listaServico.getSelectedItem().toString());
//                sendIntent.putExtra("data", listaData.getSelectedItem().toString());
//
//                Intent intent = new Intent(MainActivity.this, CardapioActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        listaUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
//                //pega nome pela posição
//
//                //imprime um Toast na tela com o nome que foi selecionado
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


    }

    public static void listar(final Context context, final String busca) {
        //Sincroniza sincroniza = new Sincroniza(context, 0);
        //String url = "http://172.31.1.223:9999/PLCerto/WS/Cardapio/" + busca;
        String url = "http://192.168.25.95:9999/PLCerto/WS/Cardapio/" + busca;
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
        Unidade unidade;
        Servico servico;
        String msg;

        try {
            JSONArray jsonObject = new JSONArray(response);
            JSONArray result = jsonObject;

            Log.e("**********", String.valueOf(result.length()));

            for (int i = 0; i < result.length(); i++) {
                JSONObject collegeData = result.getJSONObject(i);

                if (busca.equalsIgnoreCase("ListaUnidades")) {
                    // unidade.add(result.getJSONObject(i).toString());

                    unidade = new Unidade();

                    unidade.setUnidadeId(collegeData.getInt("unidadeId"));
                    unidade.setUnidadeNome(collegeData.getString("unidadeNome"));

                    try {

                        // salvarMateriais(model);
                        unidadeL.add(unidade);
                    } catch (Exception e) {
                        Log.d("Erro add unidade ", "Erro add unidade " + e.getMessage());
                    }
                    Log.e("TESSTEEEEEEEEEEEE ", unidadeL.get(0).getUnidadeNome());
                } else if (busca.equalsIgnoreCase("ListaSevicos")) {
                    //servico.add(result.getJSONObject(i).toString());

                    servico = new Servico();

                    servico.setServicoId(collegeData.getInt("servicoId"));
                    servico.setServicoNome(collegeData.getString("servicoNome"));

                    try {

                        // salvarMateriais(model);
                        servicoL.add(servico);
                    } catch (Exception e) {
                        Log.d("Erro add serviço ", "Erro add serviço " + e.getMessage());
                    }
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

        Log.e("Sync Cardapios", msg);

    }
}

