package br.com.contato.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.contato.R;
import br.com.contato.modelo.Anuncio;


public class AnuncioAdapter extends BaseAdapter {

    private List<Anuncio> anuncios;
    private Activity tela;

    public AnuncioAdapter(List<Anuncio> lista, Activity tela) {
        this.anuncios = lista;
        this.tela = tela;
    }

    @Override
    public int getCount() {
        return anuncios.size();
    }

    @Override
    public Anuncio getItem(int i) {
        return anuncios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View itemLista = tela.getLayoutInflater()
                .inflate(R.layout.item_lista_anuncio, viewGroup, false);

        ImageView imAnuncio = (ImageView) itemLista.findViewById(R.id.im_anuncio);
        TextView tvTitulo = (TextView) itemLista.findViewById(R.id.tv_titulo);
        TextView tvDescricao = (TextView)itemLista.findViewById(R.id.tv_descricao);
        TextView tvPreco = (TextView) itemLista.findViewById(R.id.tv_preco);

        Anuncio anuncio = getItem(position) ;

        tvTitulo.setText(anuncio.getTitulo());
        tvPreco.setText(anuncio.getPreco());
        tvDescricao.setText(anuncio.getDescricao());

        return itemLista;
    }
}
