package br.com.contato.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import br.com.contato.R;
import br.com.contato.adapters.AnuncioAdapter;
import br.com.contato.dao.AnuncioDAO;
import br.com.contato.modelo.Anuncio;

public class ListaAnuncioActivity extends AppCompatActivity {

    private ListView lvAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_anuncio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.lista_anuncio));

        lvAnuncios = (ListView) findViewById(R.id.lv_anuncios);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AnuncioDAO anuncioDAO = new AnuncioDAO(this);

        List<Anuncio> listaAnuncios = anuncioDAO.listarTodos();

        AnuncioAdapter anuncioAdapter = new AnuncioAdapter(listaAnuncios, this);

        lvAnuncios.setAdapter(anuncioAdapter);
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
