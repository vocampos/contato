package br.com.contato.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.contato.R;
import br.com.contato.dao.ContatoDAO;
import br.com.contato.modelo.Contato;

public class ListaContatoActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contato);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.lista_contato);

        ContatoDAO contatoDAO = ContatoDAO.getInstance(this);
        final List<Contato> listaContato = contatoDAO.listarTodos();

        ArrayAdapter<Contato> arrayAdapter = new ArrayAdapter<Contato>(this,
                android.R.layout.simple_list_item_1, listaContato);

        listView.setAdapter(arrayAdapter);

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contato, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.add_contato:
                Intent intent = new Intent(ListaContatoActivity.this, ContatoActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
