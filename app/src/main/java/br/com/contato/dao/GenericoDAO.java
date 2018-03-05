package br.com.contato.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.contato.modelo.Modelo;

public abstract class GenericoDAO <C extends Modelo> {

    private SQLiteDatabase dataBase;

    public GenericoDAO(Context context) {
        AppOpenHelper helper =  AppOpenHelper.getInstancia(context);
        dataBase = helper.getWritableDatabase();
    }

    public boolean salvar(C modelo) {
        if (modelo.getId() > 0)
            return atualizar(modelo);

        return inserir(modelo);
    }

    public boolean inserir (C modelo) {
        ContentValues cv = prepararContentValues(modelo);
        long id = dataBase.insert(getNomeTabela(), null, cv);
        modelo.setId(id);
        return id > 0;
    }

    public boolean atualizar (C modelo) {
        String [] valorCondicao = {String.valueOf(modelo.getId())};
        ContentValues cv = prepararContentValues(modelo);
        int qtd = dataBase.update(getNomeTabela(), cv, getNomeColunaPrimaryKey() +" = ? ", valorCondicao);
        return qtd > 0;
    }

    public void deletar(C modelo) {
        String[] valoresParaExcluir = {String.valueOf(modelo.getId())};
        dataBase.delete(getNomeTabela(), getNomeColunaPrimaryKey() + " =  ?", valoresParaExcluir);
    }

    public void deletarTodos() {
        dataBase.execSQL("DELETE FROM " + getNomeTabela());
    }

    public C buscarPorID(long id) {
        String queryOne = "SELECT * FROM " + getNomeTabela() + " where " + getNomeColunaPrimaryKey() + " = " + id;
        List<C> result = recuperarPorQuery(queryOne);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<C> recuperarPorQuery(String query) {

        Cursor cursor = dataBase.rawQuery(query, null);

        List<C> result = new ArrayList<C>();
        if (cursor.moveToFirst()) {
            do {
                ContentValues contentValues = new ContentValues();
                DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
                C modelo = contentValuesParaModelo(contentValues);
                result.add(modelo);
            } while (cursor.moveToNext());
        }
        return result;
    }


    public abstract String getNomeColunaPrimaryKey();
    public abstract String getNomeTabela();

    public abstract ContentValues prepararContentValues(C modelo);
    public abstract C contentValuesParaModelo(ContentValues contentValues);
}
