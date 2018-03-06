package br.com.contato.dao;

import android.content.ContentValues;
import android.content.Context;

import br.com.contato.modelo.Categoria;

public class CategoriaDAO extends GenericoDAO <Categoria> {

    private static final String NOME_TABELA = "TB_CATEGORIA";
    private static final String CL_ID = "CL_ID";
    private static final String CL_DESCRICAO= "CL_DESCRICAO";

    public static final String SCRIPT_CRIAR_TABELA = "CREATE TABLE "+ NOME_TABELA + " ( " +
            CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CL_DESCRICAO  + " TEXT )";

    public static final String SCRIPT_DELETE = "DROP TABLE IF EXISTIS "+ NOME_TABELA;

    public static final String SCRIPT_INICIAL = "" +
            "INSERT INTO " + NOME_TABELA + "( " + CL_DESCRICAO + " ) VALUES ('Animais');" +
            "INSERT INTO " + NOME_TABELA + "( " + CL_DESCRICAO + " ) VALUES ('Automóveis');" +
            "INSERT INTO " + NOME_TABELA + "( " + CL_DESCRICAO + " ) VALUES ('Imóveis'); " +
            "INSERT INTO " + NOME_TABELA + "( " + CL_DESCRICAO + " ) VALUES ('Educação');" +
            "INSERT INTO " + NOME_TABELA + "( " + CL_DESCRICAO + " ) VALUES ('Esportes'); " ;

    public CategoriaDAO(Context context) {
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
    public ContentValues prepararContentValues(Categoria categoria) {
        ContentValues cv = new ContentValues();
        if (categoria.getId() > 0) {
            cv.put(CL_ID, categoria.getId());
        }
        cv.put(CL_DESCRICAO, categoria.getDescricao());
        return cv;
    }

    @Override
    public Categoria contentValuesParaModelo(ContentValues contentValues) {
        Categoria categoria = new Categoria();
        categoria.setId(contentValues.getAsLong(CL_ID));
        categoria.setDescricao(contentValues.getAsString(CL_DESCRICAO));
        return categoria;
    }

}
