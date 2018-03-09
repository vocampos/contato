package br.com.contato.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.contato.R;

public class ListaAnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_anuncio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.lista_anuncio));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home :

                Intent intent = new Intent(ListaAnuncioActivity.this,
                        ListaContatoActivity.class);

                startActivity(intent);

                finish();

              return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
