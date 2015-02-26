package com.caioketo.gerenciadordeestudos.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Caio on 17/09/2014.
 */
public class TabelaProva implements TabelaBase {
    private static TabelaProva instance;

    public static TabelaProva getInstance() {
        if (instance == null) {
            instance = new TabelaProva();
        }
        return instance;
    }


    public static final String TABLE = "prova";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MATERIA_ID = "materia_id";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_CONTEUDO = "conteudo";
    public static final String COLUMN_NOTA = "nota";
    public static final String COLUMN_VARIAVEL = "variavel";

    // SQL para criar a tabela
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MATERIA_ID + " integer, "
            + COLUMN_CONTEUDO + " text not null,"
            + COLUMN_VARIAVEL + " text,"
            + COLUMN_DATA + " long, "
            + COLUMN_NOTA + " real "
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
        return COLUMN_ID;
    }

    @Override
    public String getTable() {
        return TABLE;
    }

    @Override
    public String[] getColumns() {
        String[] retorno = { COLUMN_DATA, COLUMN_MATERIA_ID, COLUMN_ID, COLUMN_CONTEUDO };
        return retorno;
    }
}
