package com.caioketo.gerenciadordeestudos.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Caio on 20/10/2014.
 */
public class TabelaAnexosTrabalho implements TabelaBase {
    private static TabelaAnexosTrabalho instance;
    public static TabelaAnexosTrabalho getInstance() {
        if (instance == null) {
            instance = new TabelaAnexosTrabalho();
        }
        return instance;
    }


    public static final String TABLE = "anexos_trabalho";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TRABALHO_ID = "trabalho_id";
    public static final String COLUMN_IMAGEM = "imagem";

    // SQL para criar a tabela
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TRABALHO_ID + " integer, "
            + COLUMN_IMAGEM + " blob "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static String addPrefix(String column) {
        return TABLE + "." + column;
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(database);
    }


    @Override
    public String getColumnID() {
        return TabelaAnexosTrabalho.COLUMN_ID;
    }

    @Override
    public String getTable() {
        return TabelaAnexosTrabalho.TABLE;
    }

    @Override
    public String[] getColumns() {
        String[] retorno = { COLUMN_TRABALHO_ID, COLUMN_IMAGEM, COLUMN_ID };
        return retorno;
    }
}
