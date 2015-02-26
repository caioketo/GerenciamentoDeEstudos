package com.caioketo.gerenciadordeestudos.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Caio on 20/10/2014.
 */
public class TabelaTrabalho implements TabelaBase {
    private static TabelaTrabalho instance;
    public static TabelaTrabalho getInstance() {
        if (instance == null) {
            instance = new TabelaTrabalho();
        }
        return instance;
    }


    public static final String TABLE = "trabalho";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MATERIA_ID = "materia_id";
    public static final String COLUMN_ASSUNTO = "assunto";
    public static final String COLUMN_CONTEUDO = "conteudo";
    public static final String COLUMN_DATA_ENTREGA = "data_entrega";

    // SQL para criar a tabela
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MATERIA_ID + " integer, "
            + COLUMN_CONTEUDO + " text not null,"
            + COLUMN_ASSUNTO + " text,"
            + COLUMN_DATA_ENTREGA + " long "
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
        return TabelaTrabalho.COLUMN_ID;
    }

    @Override
    public String getTable() {
        return TabelaTrabalho.TABLE;
    }

    @Override
    public String[] getColumns() {
        String[] retorno = { COLUMN_DATA_ENTREGA, COLUMN_MATERIA_ID, COLUMN_ID, COLUMN_CONTEUDO, COLUMN_ASSUNTO };
        return retorno;
    }
}
