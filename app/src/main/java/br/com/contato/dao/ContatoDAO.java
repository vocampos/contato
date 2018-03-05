package br.com.contato.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.contato.modelo.Contato;

public class ContatoDAO {

    private static final String NOME_TABELA = "TB_CONTATO";
    private static final String CL_ID = "CL_ID";
    private static final String CL_NOME = "CL_NOME";
    private static final String CL_EMAIL = "CL_EMAIL";
    private static final String CL_TELEFONE = "CL_TELEFONE";
    private static final String CL_CELULAR = "CL_CELULAR";
    private static final String CL_FOTO = "CL_FOTO";

    public static final String SCRIPT_CRIAR_TABELA = "CREATE TABLE "+ NOME_TABELA + " ( " +
            CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CL_NOME  + " TEXT , " +
            CL_EMAIL  + " TEXT , " +
            CL_TELEFONE  + " TEXT , " +
            CL_CELULAR  + " TEXT , " +
            CL_FOTO  + " TEXT )";

    public static final String SCRIPT_DELETE = "DROP TABLE IF EXISTIS "+ NOME_TABELA;


    private AppOpenHelper openHelper;
    private SQLiteDatabase database;

    private static ContatoDAO instace;

    public static ContatoDAO getInstace(Context context) {
        if (instace == null) {
            instace = new ContatoDAO(context);
        }
        return instace;
    }

    private ContatoDAO(Context context) {
        this.openHelper = AppOpenHelper.getInstancia(context);
        this.database = openHelper.getWritableDatabase();
    }

    public boolean salvar(Contato contato) {
        if (contato.getId() > 0)
            return atualizar(contato);

        return inserir(contato);
    }

    public boolean inserir(Contato contato) {
        ContentValues cv = prepararContentValues(contato);
        Long id = database.insert(NOME_TABELA, null, cv);
        contato.setId(id.intValue());
        return id > 0;
    }

    public Contato recuperar(long id) {
        String colunas[] = { CL_ID, CL_NOME, CL_EMAIL, CL_TELEFONE, CL_CELULAR, CL_FOTO };
        String[] valorCondicoes = {String.valueOf(id)};

        Cursor cursor = database.query(NOME_TABELA, colunas, CL_ID + "=?", valorCondicoes, null, null, null);
        List<Contato> contato = converterCursor(cursor);

        if (contato.size() > 0) {
            return contato.get(0);
        }

        return null;
    }

    public List<Contato> listarTodos() {
        String sql = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = database.rawQuery(sql, null);
        return converterCursor(cursor);
    }

    public boolean atualizar(Contato contato) {
        ContentValues cv = prepararContentValues(contato);
        String [] valorCondicoes = {String.valueOf(contato.getId())};
        int qtdAlteracao = database.update(NOME_TABELA, cv, CL_ID + "=?", valorCondicoes);
        return qtdAlteracao > 0;
    }

     public boolean deletar(long id) {
         String[] valoresParaSubstituir = {String.valueOf(id)};
         int delete =  database.delete(NOME_TABELA, CL_ID + "=?", valoresParaSubstituir);
         return delete > 0;
     }

    private ContentValues prepararContentValues(Contato contato) {
        ContentValues cv = new ContentValues();
        if (contato.getId() > 0) {
            cv.put(CL_ID, contato.getId());
        }
        cv.put(CL_NOME, contato.getNome());
        cv.put(CL_EMAIL, contato.getEmail());
        cv.put(CL_TELEFONE, contato.getTelefone());
        cv.put(CL_CELULAR, contato.getCelular());

        return cv;
    }

    private List<Contato> converterCursor(Cursor cursor) {

        List<Contato> contatos  = new ArrayList<Contato>();

        if (cursor == null) {
            return contatos;

        } else {

            if (cursor.moveToFirst()) {
                do {
                    int indexID = cursor.getColumnIndex(CL_ID);
                    int indexNome = cursor.getColumnIndex(CL_NOME);
                    int indexEmail = cursor.getColumnIndex(CL_EMAIL);
                    int indexTelefone = cursor.getColumnIndex(CL_TELEFONE);
                    int indexCelular = cursor.getColumnIndex(CL_TELEFONE);
                    int indexFoto = cursor.getColumnIndex(CL_TELEFONE);

                    Contato contato = new Contato();
                    contato.setId(cursor.getInt(indexID));
                    contato.setNome(cursor.getString(indexNome));
                    contato.setEmail(cursor.getString(indexEmail));
                    contato.setTelefone(cursor.getString(indexTelefone));
                    contato.setCelular(cursor.getString(indexCelular));

                    contatos.add(contato);

                } while (cursor.moveToNext());

                cursor.close();
            }

        }

        return contatos;
    }


}
