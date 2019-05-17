package com.example.bayrakquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewSonuc, textViewYuzdeSonuc;
    private MaterialButton buttonTekrar;
    private int dogruSayac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewSonuc = findViewById(R.id.textViewSonuc);
        textViewYuzdeSonuc = findViewById(R.id.textViewYuzdeSonuc);
        buttonTekrar = findViewById(R.id.buttonTekrar);

        dogruSayac = getIntent().getIntExtra("dogruSayac", 0);

        textViewSonuc.setText(dogruSayac + " DOĞRU " + (5 - dogruSayac) + " YANLIŞ");
        textViewYuzdeSonuc.setText("% " + (dogruSayac * 100) / 5 + " Başarı");

        buttonTekrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, QuizActivity.class));
                finish();
            }
        });
    }

}
