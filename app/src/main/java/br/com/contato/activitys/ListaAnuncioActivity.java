package br.com.contato.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.contato.R;

public class ListaAnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_anuncio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
