package br.com.contato.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.contato.R;
import br.com.contato.dao.ContatoDAO;
import br.com.contato.modelo.Contato;
import br.com.contato.util.MaskEditUtil;

public class ContatoActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etCelular, etTelefone;
    private Button button;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        configurarComponentes();
        configurarValores();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configurarValores() {
        contato = (Contato) getIntent().getSerializableExtra("contatoSelecionado");
        if (contato != null) {
            etNome.setText(contato.getNome());
            etCelular.setText(contato.getCelular());
            etTelefone.setText(contato.getTelefone());
            etEmail.setText(contato.getEmail());
        }
    }

    private Contato recuperarContato() {
        if(contato == null) {
            contato = new Contato();
        }

        contato.setNome(etNome.getText().toString());
        contato.setEmail(etEmail.getText().toString());
        contato.setTelefone(etTelefone.getText().toString());
        contato.setCelular(etCelular.getText().toString());
        return contato;
    }

    private void configurarComponentes() {
        button = (Button) findViewById(R.id.salvar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = ContatoActivity.this;
                ContatoDAO dao = ContatoDAO.getInstance(context);
                Contato contato = recuperarContato();

                if (dao.salvar(contato)) {
                    Toast.makeText(context, context.getString(R.string.mensagem_sucesso), Toast.LENGTH_SHORT).show();
                }

            }
        });

        etNome = (EditText) findViewById(R.id.tv_nome);
        etEmail = (EditText) findViewById(R.id.tv_email);
        etCelular = (EditText) findViewById(R.id.tv_celular);
        etTelefone = (EditText) findViewById(R.id.tv_telefone);

        etCelular.addTextChangedListener(MaskEditUtil.mask(etCelular, MaskEditUtil.FORMAT_FONE));
        etTelefone.addTextChangedListener(MaskEditUtil.mask(etTelefone, MaskEditUtil.FORMAT_FONE));
    }





}
