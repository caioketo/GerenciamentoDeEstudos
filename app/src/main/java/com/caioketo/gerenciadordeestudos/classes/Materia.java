package com.caioketo.gerenciadordeestudos.classes;

import android.content.ContentValues;
import android.database.Cursor;

import com.caioketo.gerenciadordeestudos.database.TabelaMateria;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Caio on 17/09/2014.
 */
public class Materia extends Base {
    private String descricao;
    private int cor;
    private String calculoMedia;

    public Materia() {}

    public Materia(String _desc, int _cor, String _calculo, int _id) {
        this.descricao = _desc;
        this.cor = _cor;
        this.calculoMedia = _calculo;
        this.setId(_id);
    }

    public String getDescricao() {
        return this.descricao;
    }

    public int getCor() {
        return this.cor;
    }

    public String getCalculoMedia() {
        return this.calculoMedia;
    }

    public void setCalculoMedia(String _calculoMedia) {
        this.calculoMedia = _calculoMedia;
    }

    public void setDescricao(String _descricao) {
        this.descricao = _descricao;
    }

    public void setCor(int _cor) {
        this.cor = _cor;
    }

    @Override
    public ContentValues toContent() {
        ContentValues values = new ContentValues();
        if (getId() > 0) {
            values.put("_id", getId());
        }
        values.put(TabelaMateria.COLUMN_DESCRICAO, descricao);
        values.put(TabelaMateria.COLUMN_COR, cor);
        values.put(TabelaMateria.COLUMN_CALCULO_MEDIA, calculoMedia);
        return values;
    }

    public static Materia fromCursor(Cursor curMateria) {
        String desc = curMateria.getString(curMateria.getColumnIndex(TabelaMateria.COLUMN_DESCRICAO));
        int cor = curMateria.getInt(curMateria.getColumnIndex(TabelaMateria.COLUMN_COR));
        String calculo = curMateria.getString(curMateria.getColumnIndex(TabelaMateria.COLUMN_CALCULO_MEDIA));
        int id = curMateria.getInt(curMateria.getColumnIndex("_id"));

        return new Materia(desc, cor, calculo, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Materia materia = (Materia) o;

        if (cor != materia.cor) return false;
        if (!descricao.equals(materia.descricao)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = descricao.hashCode();
        result = 31 * result + cor;
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("descricao", getDescricao());
            obj.put("calculoMedia", getCalculoMedia());
            obj.put("cor", getCor());
            obj.put("jsonId", getId());
        }
        catch (JSONException ex) {

        }
        return obj;
    }

}