package com.caioketo.gerenciadordeestudos.classes;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.caioketo.gerenciadordeestudos.database.TabelaAnexosTrabalho;
import com.caioketo.gerenciadordeestudos.utils.Util;

import java.io.ByteArrayInputStream;

/**
 * Created by Caio on 20/10/2014.
 */
public class AnexosTrabalho extends Base {
    private Trabalho trabalho;
    private byte[] imagem;
    private Bitmap imagemBmp;

    public void setTrabalho(Trabalho _trabalho) {
        trabalho = _trabalho;
    }

    public void setTrabalhoById(int id) {
        trabalho = Util.getTrabalhoById(id);
    }

    public void setImagem(byte[] _imagem) {
        imagem = _imagem;
        imagemBmp = Util.getBitmapImagem(_imagem);
    }

    public Trabalho getTrabalho() {
        return trabalho;
    }

    public byte[] getImage() {
        return imagem;
    }

    public Bitmap getImagemBmp() {
        return imagemBmp;
    }

    @Override
    public ContentValues toContent() {
        ContentValues values = new ContentValues();
        values.put(TabelaAnexosTrabalho.COLUMN_TRABALHO_ID, trabalho.getId());
        values.put(TabelaAnexosTrabalho.COLUMN_IMAGEM, imagem);
        return values;
    }
}
