package com.example.labdesenvolvimento.planejecerto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CardapioActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);


        Button vou = (Button) findViewById(R.id.button);
        Button nVou = (Button) findViewById(R.id.button3);
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
}
