package com.caioketo.gerenciadordeestudos.classes;

import android.content.ContentValues;

import com.caioketo.gerenciadordeestudos.database.TabelaTrabalho;
import com.caioketo.gerenciadordeestudos.utils.Util;

/**
 * Created by Caio on 18/10/2014.
 */
public class Trabalho extends Base {
    private String assunto;
    private String conteudo;
    private Materia materia;
    private long dataEntrega;

    public void setAssunto(String _assunto) {
        assunto = _assunto;
    }

    public void setConteudo(String _conteudo) {
        conteudo = _conteudo;
    }

    public void setMateria(Materia _materia) {
        materia = _materia;
    }

    public void setMateriaById(int id) {
        this.materia = Util.getMateriaById(id);
    }

    public void setDataEntrega(long _dataEntrega) {
        dataEntrega = _dataEntrega;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Materia getMateria() {
        return materia;
    }

    public long getDataEntrega() {
        return dataEntrega;
    }

    @Override
    public ContentValues toContent() {
        ContentValues values = new ContentValues();
        values.put(TabelaTrabalho.COLUMN_MATERIA_ID, materia.getId());
        values.put(TabelaTrabalho.COLUMN_ASSUNTO, assunto);
        values.put(TabelaTrabalho.COLUMN_CONTEUDO, conteudo);
        values.put(TabelaTrabalho.COLUMN_DATA_ENTREGA, dataEntrega);
        return values;
    }
}
