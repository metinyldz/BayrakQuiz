package com.example.bayrakquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class QuizActivity extends AppCompatActivity {
    private TextView textViewDogru, textViewYanlis, textViewSoruSayi;
    private ImageView ımageViewBayrak;
    private Button buttonA, buttonB, buttonC, buttonD;
    private ArrayList<Bayraklar> sorular;
    private ArrayList<Bayraklar> yanlisSecenekler;
    private Bayraklar dogruSoru;
    private Veritabani vt;
    //Soru sayaçları
    private int soruSayac = 0;
    private int dogruSayac = 0;
    private int yanlisSayac = 0;

    private ArrayList<Bayraklar> secenekler = new ArrayList<>();
    private HashSet<Bayraklar> secenekleriKaristirmaListe = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewDogru = findViewById(R.id.tv_dogru);
        textViewYanlis = findViewById(R.id.tv_yanlis);
        textViewSoruSayi = findViewById(R.id.soruSayisi);
        ımageViewBayrak = findViewById(R.id.bayrak);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);

        vt = new Veritabani(this);

        sorular = new BayraklarDao().rasgele5getir(vt);

        soruYukle();

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonA);
                soruSayacKontrol();
                Log.e("SAYAC", String.valueOf(soruSayac));
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonB);
                soruSayacKontrol();
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonC);
                soruSayacKontrol();
            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonD);
                soruSayacKontrol();
            }
        });
    }

    public void soruYukle() {
        textViewSoruSayi.setText((soruSayac + 1) + ". SORU");
        textViewDogru.setText("Doğru : " + (dogruSayac));
        textViewYanlis.setText("Yanlış : " + (yanlisSayac));

        dogruSoru = sorular.get(soruSayac);

        yanlisSecenekler = new BayraklarDao().rasgele3YanlisSecenekGetir(vt, dogruSoru.getBayrak_id());

        ımageViewBayrak.setImageResource(getResources().getIdentifier(dogruSoru.getBayrak_resim()
                , "drawable", getPackageName()));

        //Tüm secenekleri hashset yardımıyla karıştırma
        secenekleriKaristirmaListe.clear();
        secenekleriKaristirmaListe.add(dogruSoru);//Doğru secenek
        secenekleriKaristirmaListe.add(yanlisSecenekler.get(0));
        secenekleriKaristirmaListe.add(yanlisSecenekler.get(1));
        secenekleriKaristirmaListe.add(yanlisSecenekler.get(2));

        //Hashset ile butonlara dinamik şekilde yazı yazdıramadığımızdan arraylist dönüşümü yaptık.
        secenekler.clear();

        for (Bayraklar b : secenekleriKaristirmaListe) {
            secenekler.add(b);
        }
        //Secenekleri buttonlara yerleştirdik.
        buttonA.setText(secenekler.get(0).getBayrak_ad());
        buttonB.setText(secenekler.get(1).getBayrak_ad());
        buttonC.setText(secenekler.get(2).getBayrak_ad());
        buttonD.setText(secenekler.get(3).getBayrak_ad());

    }

    public void dogruKontrol(Button button) {

        String buttonYazi = button.getText().toString();
        String dogruCevap = dogruSoru.getBayrak_ad();
        Log.e("Doğru", dogruCevap);
        Log.e("ButtonYazi", buttonYazi);

        if (buttonYazi.equals(dogruCevap)) {
            dogruSayac++;
        } else {
            yanlisSayac++;
        }

        textViewDogru.setText("Doğru : " + (dogruSayac));
        textViewYanlis.setText("Yanlış : " + (yanlisSayac));
    }

    public void soruSayacKontrol() {

        soruSayac++;

        //soru sayısı 5 olduysa sonuca git
        if (soruSayac != 5) {
            soruYukle();
        } else {
            Intent i = new Intent(QuizActivity.this, ResultActivity.class);
            i.putExtra("dogruSayac", dogruSayac);
            startActivity(i);
            finish();
        }
    }
}
