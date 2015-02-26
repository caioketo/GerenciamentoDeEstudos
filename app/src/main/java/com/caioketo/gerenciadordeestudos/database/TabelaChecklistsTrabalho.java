package com.caioketo.gerenciadordeestudos.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Caio on 20/10/2014.
 */
public class TabelaChecklistsTrabalho implements TabelaBase {
    private static TabelaChecklistsTrabalho instance;
    public static TabelaChecklistsTrabalho getInstance() {
        if (instance == null) {
            instance = new TabelaChecklistsTrabalho();
        }
        return instance;
    }


    public static final String TABLE = "checklists_trabalho";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TRABALHO_ID = "trabalho_id";
    public static final String COLUMN_TITULO = "titulo";

    // SQL para criar a tabela
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TRABALHO_ID + " integer, "
            + COLUMN_TITULO + " text "
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
        return TabelaChecklistsTrabalho.COLUMN_ID;
    }

    @Override
    public String getTable() {
        return TabelaChecklistsTrabalho.TABLE;
    }

    @Override
    public String[] getColumns() {
        String[] retorno = { COLUMN_TRABALHO_ID, COLUMN_TITULO, COLUMN_ID };
        return retorno;
    }
}
