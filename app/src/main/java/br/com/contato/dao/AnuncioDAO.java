package br.com.contato.dao;

import android.content.ContentValues;
import android.content.Context;

import br.com.contato.modelo.Anuncio;
import br.com.contato.modelo.Categoria;

public class AnuncioDAO extends GenericoDAO <Anuncio> {

    private static final String NOME_TABELA = "TB_ANUNCIO";
    private static final String CL_ID = "CL_ID";
    private static final String CL_TITULO = "CL_TITULO";
    private static final String CL_DESCRICAO = "CL_DESCRICAO";
    private static final String CL_PRECO = "CL_PRECO";
    private static final String CL_CATEGORIA = "CL_CATEGORIA";
    private static final String CL_FOTO = "CL_FOTO";

    public static final String SCRIPT_CRIAR_TABELA = "CREATE TABLE "+ NOME_TABELA + " ( " +
            CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CL_TITULO  + " TEXT , " +
            CL_DESCRICAO  + " TEXT , " +
            CL_PRECO  + " TEXT , " +
            CL_CATEGORIA  + " INTEGER , " +
            CL_FOTO  + " TEXT )";

    public static final String SCRIPT_DELETE = "DROP TABLE IF EXISTIS "+ NOME_TABELA;

    public AnuncioDAO(Context context) {
        super(context);
    }

    @Override
    public String getNomeColunaPrimaryKey() {
        return CL_ID;
    }

    @Override
    public String getNomeTabela() {
        return NOME_TABELA;
    }

    @Override
    public ContentValues prepararContentValues(Anuncio anuncio) {
        ContentValues cv = new ContentValues();

        if (anuncio.getId() > 0) {
            cv.put(CL_ID, anuncio.getId());
        }

        cv.put(CL_DESCRICAO, anuncio.getDescricao());
        cv.put(CL_PRECO, anuncio.getPreco());
        cv.put(CL_TITULO, anuncio.getTitulo());
        cv.put(CL_FOTO, anuncio.getFoto());

        if (anuncio.getCategoria() != null
                && anuncio.getCategoria().getId() != -1 ) {

            cv.put(CL_CATEGORIA, anuncio.getCategoria().getId());
        }

        return cv;
    }

    @Override
    public Anuncio contentValuesParaModelo(ContentValues cv) {
        Anuncio anuncio = new Anuncio();
        anuncio.setId(cv.getAsLong(CL_ID));
        anuncio.setTitulo(cv.getAsString(CL_TITULO));
        anuncio.setPreco(cv.getAsString(CL_PRECO));
        anuncio.setDescricao(cv.getAsString(CL_DESCRICAO));
        anuncio.setFoto(cv.getAsString(CL_FOTO));

        Long idCategoria = cv.getAsLong(CL_CATEGORIA);

        if (idCategoria > 0) {

            CategoriaDAO categoriaDAO = new CategoriaDAO(getContext());

            Categoria categoria = categoriaDAO.buscarPorID(idCategoria);

            anuncio.setCategoria(categoria);
        }

        return anuncio;
    }
}
