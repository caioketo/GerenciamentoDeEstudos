package com.caioketo.gerenciadordeestudos.classes;

import android.content.ContentValues;

import com.caioketo.gerenciadordeestudos.database.TabelaAnexosTrabalho;
import com.caioketo.gerenciadordeestudos.database.TabelaChecklistsTrabalho;
import com.caioketo.gerenciadordeestudos.utils.Util;

/**
 * Created by Caio on 24/10/2014.
 */
public class CheckListsTrabalho extends Base {
    private Trabalho trabalho;
    private String titulo;

    public void setTrabalho(Trabalho _trabalho) {
        trabalho = _trabalho;
    }

    public void setTrabalhoById(int id) {
        trabalho = Util.getTrabalhoById(id);
    }

    public void setTitulo(String _titulo) {
        titulo = _titulo;
    }

    public Trabalho getTrabalho() {
        return trabalho;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public ContentValues toContent() {
        ContentValues values = new ContentValues();
        values.put(TabelaChecklistsTrabalho.COLUMN_TRABALHO_ID, trabalho.getId());
        values.put(TabelaChecklistsTrabalho.COLUMN_TITULO, titulo);
        return values;
    }

}
