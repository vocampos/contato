package br.com.contato.activitys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.com.contato.R;
import br.com.contato.dao.CategoriaDAO;
import br.com.contato.modelo.Anuncio;
import br.com.contato.modelo.Categoria;
import br.com.contato.util.Util;


public class AnuncioActivity extends AppCompatActivity {

    private EditText edTitulo, edPreco, edDescricao;
    private ImageView imFoto;
    private Spinner spCategoria;
    private List<Categoria> listaCategorias;
    private Categoria categoriaSelecionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recuperarCamposDeTela();
    }

    private void recuperarCamposDeTela() {
        edTitulo = (EditText) findViewById(R.id.ed_titulo);
        edPreco = (EditText) findViewById(R.id.ed_preco);
        edDescricao = (EditText) findViewById(R.id.ed_descricao);
        imFoto = (ImageView) findViewById(R.id.iv_foto);

        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AnuncioActivity.this, "OK", Toast.LENGTH_SHORT).show();
            }
        });

        spCategoria = (Spinner) findViewById(R.id.sp_categoria);


        CategoriaDAO categoriaDAO = new CategoriaDAO(this);
        listaCategorias = categoriaDAO.listarTodos();

        Categoria categoria = new Categoria();
        categoria.setId(-1);
        categoria.setDescricao(" -- Categoria --");

        listaCategorias.add(0, categoria);

        ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<Categoria>(
                this, android.R.layout.simple_spinner_dropdown_item, listaCategorias);

        spCategoria.setAdapter(adapterCategoria);

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                categoriaSelecionada = listaCategorias.get(position);
                Toast.makeText(AnuncioActivity.this, categoriaSelecionada.getDescricao(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void onClickSalvar(View view) {

    }

    private Anuncio recuperarValoresDosCamposTela() {
        return null;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                alertarMensagem();
                return;
            }
        }
        if (requestCode == 0) {

        }
    }

    private void alertarMensagem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Confirma as permissoes?")
                .setNegativeButton("NAO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });

        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Util.validate(AnuncioActivity.this, 0, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE});
            }
        });
        builder.create().show();

    }

}
