package com.caioketo.gerenciadordeestudos.database.loaders;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caioketo.gerenciadordeestudos.classes.AnexosTrabalho;
import com.caioketo.gerenciadordeestudos.database.DatabaseHelper;
import com.caioketo.gerenciadordeestudos.database.TabelaAnexosTrabalho;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caio on 22/10/2014.
 */
public class ImagensTrabalhoLoader extends Loader<List<AnexosTrabalho>> {

    private List<AnexosTrabalho> mAnexosList;
    private Cursor mCursor;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    private int mTrabalhoId;

    public ImagensTrabalhoLoader(Context context, int trabalhoId) {
        super(context);
        mContext = context;
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        mTrabalhoId = trabalhoId;
    }

    @Override
    public void deliverResult(List<AnexosTrabalho> imagens) {
        super.deliverResult(imagens);
    }


    @Override
    public boolean isStarted() {
        return super.isStarted();
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        mAnexosList = new ArrayList<AnexosTrabalho>();

        getFromDB();

        deliverResult(mAnexosList);
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();

        mAnexosList = new ArrayList<AnexosTrabalho>();
        getFromDB();

        deliverResult(mAnexosList);
    }


    @Override
    protected void onReset() {

        mAnexosList = new ArrayList<AnexosTrabalho>();

        super.onReset();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    private void getFromDB() {
        mCursor = mDatabase.query(TabelaAnexosTrabalho.TABLE, TabelaAnexosTrabalho.getInstance().getColumns(),
                " trabalho_id = ? ", new String[] { Integer.toString(mTrabalhoId) }, null, null, null);
        mCursor.moveToFirst();

        while (mCursor.isAfterLast() == false) {
            AnexosTrabalho anexo = new AnexosTrabalho();
            anexo.setImagem(mCursor.getBlob(mCursor.getColumnIndexOrThrow(TabelaAnexosTrabalho.COLUMN_IMAGEM)));
            anexo.setTrabalhoById(mCursor.getInt(mCursor.getColumnIndexOrThrow(TabelaAnexosTrabalho.COLUMN_TRABALHO_ID)));
            mAnexosList.add(anexo);
            mCursor.moveToNext();
        }
    }
}
