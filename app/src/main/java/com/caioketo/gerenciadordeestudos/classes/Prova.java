package com.caioketo.gerenciadordeestudos.classes;

import android.content.ContentValues;
import android.database.Cursor;

import com.caioketo.gerenciadordeestudos.database.TabelaProva;
import com.caioketo.gerenciadordeestudos.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Caio on 17/09/2014.
 */
public class Prova extends Base {
    private Materia materia;
    private long data;
    private String conteudo;
    private double nota;
    private String variavel;

    public Materia getMateria() {
        return this.materia;
    }

    public long getData() {
        return this.data;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public double getNota() {
        return this.nota;
    }

    public String getVariavel() {
        return this.variavel;
    }

    public void setVariavel(String _variavel) {
        this.variavel = _variavel;
    }

    public void setMateria(Materia _materia) {
        this.materia = _materia;
    }

    public void setMateriaById(int id) {
        this.materia = Util.getMateriaById(id);
    }

    public void setData(long _data) {
        this.data = _data;
    }

    public void setConteudo(String _conteudo) {
        this.conteudo = _conteudo;
    }

    public void setNota(double _nota) {
        this.nota = _nota;
    }

    @Override
    public ContentValues toContent() {
        ContentValues values = new ContentValues();
        if (getId() > 0) {
            values.put("_id", getId());
        }
        values.put(TabelaProva.COLUMN_MATERIA_ID, materia.getId());
        values.put(TabelaProva.COLUMN_DATA, data);
        values.put(TabelaProva.COLUMN_CONTEUDO, conteudo);
        values.put(TabelaProva.COLUMN_NOTA, nota);
        values.put(TabelaProva.COLUMN_VARIAVEL, variavel);
        return values;
    }

    public static Prova fromCursor(Cursor cursor) {
        Prova prova = new Prova();
        prova.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        prova.setData(cursor.getLong(cursor.getColumnIndexOrThrow(TabelaProva.COLUMN_DATA)));
        prova.setMateriaById(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaProva.COLUMN_MATERIA_ID)));
        prova.setConteudo(cursor.getString(cursor.getColumnIndexOrThrow(TabelaProva.COLUMN_CONTEUDO)));
        prova.setNota(cursor.getDouble(cursor.getColumnIndexOrThrow(TabelaProva.COLUMN_NOTA)));
        prova.setVariavel(cursor.getString(cursor.getColumnIndexOrThrow(TabelaProva.COLUMN_VARIAVEL)));
        return prova;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prova prova = (Prova) o;

        if (data != prova.data) return false;
        if (!conteudo.equals(prova.conteudo)) return false;
        if (nota != prova.nota) return false;
        if (!variavel.equals(prova.variavel)) return false;
        try {
            if (materia.getId() != prova.materia.getId()) return false;
        }
        catch (Exception ex) {

        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = conteudo.hashCode() + variavel.hashCode();
        result = 31 * result + (int)data;
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("data", getData());
            obj.put("conteudo", getConteudo());
            obj.put("nota", getNota());
            obj.put("variavel", getVariavel());
            obj.put("materia_id", getMateria().getId());
            obj.put("jsonId", getId());
        }
        catch (JSONException ex) {

        }
        return obj;
    }
}