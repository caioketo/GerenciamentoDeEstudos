package com.caioketo.gerenciadordeestudos.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Caio on 17/09/2014.
 */
public class TabelaMateria implements TabelaBase {

    private static TabelaMateria instance;

    public static TabelaMateria getInstance() {
        if (instance == null) {
            instance = new TabelaMateria();
        }
        return instance;
    }
    public static final String TABLE = "materia";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_COR = "cor";
    public static final String COLUMN_CALCULO_MEDIA = "calculoMedia";

    // SQL para criar a tabela
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_DESCRICAO + " text not null, "
            + COLUMN_CALCULO_MEDIA + " text, "
            + COLUMN_COR + " integer "
            + ");";

    public static String addPrefix(String column) {
        return TABLE + "." + column;
    }

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
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
        String[] retorno = { COLUMN_DESCRICAO, COLUMN_ID, COLUMN_COR };
        return retorno;
    }
}
