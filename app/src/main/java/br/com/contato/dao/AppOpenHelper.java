package br.com.contato.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BANCO_DE_DADOS =  "contato.db";
    public static final int VERSAO_BANCO_DE_DADOS  =  1;

    private static AppOpenHelper instancia;

    public static AppOpenHelper getInstancia(Context context) {
        if (instancia == null) {
            instancia = new AppOpenHelper(context);
        }
        return instancia;
    }

    private AppOpenHelper(Context context) {
        super(context, NOME_BANCO_DE_DADOS, null, VERSAO_BANCO_DE_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContatoDAO.SCRIPT_CRIAR_TABELA);
        db.execSQL(CategoriaDAO.SCRIPT_CRIAR_TABELA);
        db.execSQL(AnuncioDAO.SCRIPT_CRIAR_TABELA);
        db.execSQL(CategoriaDAO.SCRIPT_INICIAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova) {
        db.execSQL(ContatoDAO.SCRIPT_DELETE);
        db.execSQL(CategoriaDAO.SCRIPT_DELETE);
        db.execSQL(AnuncioDAO.SCRIPT_DELETE);
        onCreate(db);
    }
}
