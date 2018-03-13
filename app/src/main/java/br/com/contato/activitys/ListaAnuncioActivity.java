package br.com.contato.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.contato.R;
import br.com.contato.adapters.AnuncioAdapter;
import br.com.contato.dao.AnuncioDAO;
import br.com.contato.modelo.Anuncio;
import br.com.contato.modelo.Contato;

public class ListaAnuncioActivity extends AppCompatActivity {

    private ListView lvAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_anuncio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.lista_anuncio));

        lvAnuncios = (ListView) findViewById(R.id.lv_anuncios);

        lvAnuncios.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu,
                                            View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

                contextMenu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.deletar));
                contextMenu.add(Menu.NONE, 2, Menu.NONE, getString(R.string.alterar));
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Anuncio anuncioSelecionado = (Anuncio) lvAnuncios.getAdapter()
                .getItem(info.position);

        if (item.getItemId() == 1) {

            AnuncioDAO anuncioDAO = new AnuncioDAO(this);

            anuncioDAO.deletar(anuncioSelecionado);

            this.onResume();

        } else if (item.getItemId() == 2) {
            Intent intent = new Intent(ListaAnuncioActivity.this, AnuncioActivity.class);
            intent.putExtra("anuncioSelecionado", anuncioSelecionado);
            intent.putExtra("acao", "atualizar");

            startActivity(intent);
        }


        return super.onContextItemSelected(item);
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
