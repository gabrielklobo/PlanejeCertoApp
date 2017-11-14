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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Cardapio;
import Model.Servico;
import Model.Unidade;

public class MainActivity extends Activity {
    static List<Unidade> unidadeL;
    static List<Servico> servicoL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unidadeL = new ArrayList<>();
        servicoL = new ArrayList<>();
        List<String> datas = new ArrayList<>();
        Button consultar = (Button) findViewById(R.id.button2);
        final Spinner listaUnidade = (Spinner) findViewById(R.id.spinner);
        final Spinner listaServico = (Spinner) findViewById(R.id.spinner2);
        final Spinner listaData = (Spinner) findViewById(R.id.spinner3);

//        Date dataHoje = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        datas.add("02/01/17");
        datas.add("01/09/16");
        datas.add("10/02/17");

//       listar(this, "ListaUnidades");
//        listar(this, "ListaServicos");

        Unidade u1 = new Unidade();
        u1.setUnidadeId(2);
        u1.setUnidadeNome("Restaurante 1");
        Unidade u2 = new Unidade();
        u2.setUnidadeId(3);
        u2.setUnidadeNome("Restaurante 2");
        Unidade u3 = new Unidade();
        u3.setUnidadeId(3);
        u3.setUnidadeNome("Restaurante 3");

        unidadeL.add(u1);
        unidadeL.add(u2);
        unidadeL.add(u3);

        Servico s = new Servico();
        s.setServicoId(1);
        s.setServicoNome("Almoco Geral");

        Servico s1 = new Servico();
        s1.setServicoId(2);
        s1.setServicoNome("Jantar Dietetica");

        Servico s2 = new Servico();
        s2.setServicoId(3);
        s2.setServicoNome("Ceia Geral");

        Servico s3 = new Servico();
        s3.setServicoId(4);
        s3.setServicoNome("Desjejum Geral");

        servicoL.add(s);
        servicoL.add(s1);
        servicoL.add(s2);
        servicoL.add(s3);


//        for (int i = 0; i < 100; i++) {
//            Servico s = new Servico();
//            s.setServicoNome(String.valueOf(i));
//            servicoL.add(s);
//        }

        ArrayAdapter<Unidade> spinnerArrayAdapterUni = new ArrayAdapter<Unidade>(this, android.R.layout.simple_spinner_dropdown_item, unidadeL);


        spinnerArrayAdapterUni.setDropDownViewResource(android.R.layout.simple_spinner_item);
        listaUnidade.setAdapter(spinnerArrayAdapterUni);


        ArrayAdapter<Servico> spinnerArrayAdapterServ = new ArrayAdapter<Servico>(this, android.R.layout.simple_spinner_dropdown_item, servicoL);

        spinnerArrayAdapterServ.setDropDownViewResource(android.R.layout.simple_spinner_item);
        listaServico.setAdapter(spinnerArrayAdapterServ);

        ArrayAdapter<String> spinnerArrayAdapterData = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, datas);

        spinnerArrayAdapterData.setDropDownViewResource(android.R.layout.simple_spinner_item);
        listaData.setAdapter(spinnerArrayAdapterData);

//
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the text message with a string
                Intent sendIntent = new Intent(MainActivity.this, CardapioActivity.class);
                //sendIntent.setAction(Intent.ACTION_SEND);
                Unidade u = (Unidade) listaUnidade.getSelectedItem();
                Servico s = (Servico) listaServico.getSelectedItem();
                sendIntent.putExtra("idUnidade", u.getUnidadeId());
                sendIntent.putExtra("idServico", s.getServicoId());
                sendIntent.putExtra("data", listaData.getSelectedItem().toString().replace("/",""));
                Toast.makeText(MainActivity.this, String.valueOf(u.getUnidadeId()) + " " + String.valueOf(s.getServicoId() + " " + listaData.getSelectedItem().toString()), Toast.LENGTH_LONG).show();
                startActivity(sendIntent);

//
//                Intent intent = new Intent(MainActivity.this, CardapioActivity.class);
//
            }
        });
//        listaUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
//                //pega nome pela posição
//                String nome = parent.getItemAtPosition(posicao).getClass().toString();
//                Unidade p = (Unidade) parent.getSelectedItem();
//
//                //imprime um Toast na tela com o nome que foi selecionado
//                Toast.makeText(MainActivity.this, "Nome Selecionado: " + p.getUnidadeId(), Toast.LENGTH_LONG).show();
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
        String url = "http://192.168.43.61:9999/PLCerto/WS/Cardapio/" + busca;
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

                } else if (busca.equalsIgnoreCase("ListaServicos")) {
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

