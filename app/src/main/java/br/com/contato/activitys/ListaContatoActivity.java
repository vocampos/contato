package br.com.contato.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.contato.R;
import br.com.contato.dao.ContatoDAO;
import br.com.contato.modelo.Contato;

public class ListaContatoActivity extends AppCompatActivity {

    private ListView listView;
    private List<Contato> listaContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contato);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.lista_contato);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {

                Contato contato = listaContato.get(position);

                Intent intent = new Intent(ListaContatoActivity.this, ContatoActivity.class);
                intent.putExtra("contatoSelecionado", contato);

                startActivity(intent);
            }
        });

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

                /*
                1º parâmetro: Refere-se ao groupId.
                2º parâmetro: Nesse parâmetro enviamos o ItemId que é justamente o id do menu, ou seja, é por meio dele que identificaremos esse menu.
                3º parâmetro: Nele informamos o order, ou seja, a posição que queremos ordenar o menu.
                4º parâmetro: Indica o nome que será exibido para o menu.
                */
                contextMenu.add(Menu.NONE, 1, Menu.NONE, "deletar");
                contextMenu.add(Menu.NONE, 2, Menu.NONE, "Alterar");
            }

        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Toast.makeText(this, item.getItemId() + " " + item.getTitle(), Toast.LENGTH_SHORT).show();

        return super.onContextItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        ContatoDAO contatoDAO = ContatoDAO.getInstance(this);

        listaContato = contatoDAO.listarTodos();

        ArrayAdapter<Contato> arrayAdapter = new ArrayAdapter<Contato>(this,
                android.R.layout.simple_list_item_1, listaContato);

        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contato, menu);

        MenuItem pesquisaItem = menu.findItem(R.id.pesquisar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(pesquisaItem);
        searchView.setQueryHint(getString(R.string.pesquisar));

        MenuItemCompat.setOnActionExpandListener(pesquisaItem, new SearchViewExpandListener(this));
        MenuItemCompat.setActionView(pesquisaItem, searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                alertarMensagem();
                return true;

            case R.id.add_contato:
                startActivity(new Intent(ListaContatoActivity.this, ContatoActivity.class));
                return true;

            case R.id.add_anuncio:
                startActivity(new Intent(ListaContatoActivity.this, AnuncioActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private class SearchViewExpandListener implements MenuItemCompat.OnActionExpandListener {

        private Context context;

        public SearchViewExpandListener(Context context) {
            this.context = context;
        }

        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            return false;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            return false;
        }
    }

    private void alertarMensagem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(getString(R.string.deseja_sair))
                .setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finish();

                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);

            }
        });
        builder.create().show();

    }
}