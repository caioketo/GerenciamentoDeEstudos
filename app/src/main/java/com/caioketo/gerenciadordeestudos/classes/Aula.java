package com.caioketo.gerenciadordeestudos.classes;

import android.content.ContentValues;
import android.database.Cursor;

import com.caioketo.gerenciadordeestudos.database.TabelaAula;
import com.caioketo.gerenciadordeestudos.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;


public class Aula extends Base {
    private Materia materia;
    private int materia_id;
    private int duracao;
    private int diaDaSemana;
    private int horaInicio;
    private int minutoInicio;

    public void setMateria_id(int id) {
        materia_id = id;
    }

    public int getMateria_id() {
        return materia_id;
    }

    public int getDiaDaSemana() {
        return this.diaDaSemana;
    }

    public int getHoraInicio() {
        return this.horaInicio;
    }

    public int getMinutoInicio() {
        return this.minutoInicio;
    }

    public int getDuracao() {
        return this.duracao;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public void setDuracao(int _duracao) {
        this.duracao = _duracao;
    }

    public void setMateria(Materia _materia) {
        this.materia = _materia;
    }

    public void setMateriaById(int id) {
        this.materia = Util.getMateriaById(id);
    }

    public void setDiaDaSemana(int _diaDaSemana) {
        this.diaDaSemana = _diaDaSemana;
    }

    public void setHoraInicio(int _horaInicio) {
        this.horaInicio = _horaInicio;
    }

    public void setMinutoInicio(int _minutoInicio) {
        this.minutoInicio = _minutoInicio;
    }

    @Override
    public ContentValues toContent() {
        ContentValues values = new ContentValues();
        if (getId() > 0) {
            values.put("_id", getId());
        }
        if (materia == null) {
            materia = Util.getMateriaById(materia_id);
        }
        values.put(TabelaAula.COLUMN_MATERIA_ID, materia.getId());
        values.put(TabelaAula.COLUMN_DURACAO, duracao);
        values.put(TabelaAula.COLUMN_DIA_DA_SEMANA, diaDaSemana);
        values.put(TabelaAula.COLUMN_HORA_INICIO, horaInicio);
        values.put(TabelaAula.COLUMN_MINUTO_INICIO, minutoInicio);
        return values;
    }

    public static Aula fromCursor(Cursor cursor) {
        Aula aula = new Aula();
        aula.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        aula.setHoraInicio(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaAula.COLUMN_HORA_INICIO)));
        aula.setMateriaById(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaAula.COLUMN_MATERIA_ID)));
        aula.setDiaDaSemana(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaAula.COLUMN_DIA_DA_SEMANA)));
        aula.setMinutoInicio(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaAula.COLUMN_MINUTO_INICIO)));
        aula.setDuracao(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaAula.COLUMN_DURACAO)));
        return aula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aula aula = (Aula) o;

        if (horaInicio != aula.horaInicio) return false;
        try {
            if (materia.getId() != aula.materia.getId()) return false;
        }
        catch (Exception ex) {

        }
        if (diaDaSemana != aula.diaDaSemana) return false;
        if (minutoInicio != aula.minutoInicio) return false;
        if (duracao != aula.duracao) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + horaInicio + diaDaSemana + minutoInicio + duracao;
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("materia_id", getMateria().getId());
            obj.put("duracao", getDuracao());
            obj.put("diaDaSemana", getDiaDaSemana());
            obj.put("horaInicio", getHoraInicio());
            obj.put("minutoInicio", getMinutoInicio());
            obj.put("jsonId", getId());
        }
        catch (JSONException ex) {

        }
        return obj;
    }
}
