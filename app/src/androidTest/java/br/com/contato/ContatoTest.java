package br.com.contato;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import br.com.contato.dao.ContatoDAO;
import br.com.contato.modelo.Contato;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ContatoTest{

    private ContatoDAO dao;

    @Test
    public void testarCRUD() {
        dao = ContatoDAO.getInstace(getContext());
        Contato contato = gerarContato();
        inserir(contato);
        alterar(contato);
        buscarPorId(contato);
        excluir(contato);
        assertTrue(listarTodos().size() == 0);
    }

    public void inserir(Contato contato) {
        dao.inserir(contato);
    }

    public void alterar(Contato contato) {
        contato.setNome("ANDROID ALTERADO");
        dao.atualizar(contato);
    }

    public void buscarPorId(Contato contato) {
        dao.recuperar(contato.getId());
    }

    public List<Contato> listarTodos() {
       return dao.listarTodos();
    }

    public void excluir(Contato contato) {
        dao.deletar(contato.getId());
    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    private Contato gerarContato() {
        Contato contato = new Contato();
        contato.setNome("Nome do Contato " + new Random().nextInt(100));
        contato.setCelular("(61) 99999-9999");
        contato.setTelefone("(62) 99999-9999");
        contato.setEmail("email@gmail.com");
        return contato;
    }
}
