package com.caioketo.gerenciadordeestudos.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Caio on 17/09/2014.
 */
public class TabelaAula implements TabelaBase{
    private static TabelaAula instance;

    public static TabelaAula getInstance() {
        if (instance == null) {
            instance = new TabelaAula();
        }
        return instance;
    }
    public static final String TABLE = "aula";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MATERIA_ID = "materia_id";
    public static final String COLUMN_DURACAO = "duracao";
    public static final String COLUMN_DIA_DA_SEMANA = "dia_da_semana";
    public static final String COLUMN_HORA_INICIO = "hora_inicio";
    public static final String COLUMN_MINUTO_INICIO = "minuto_inicio";


    // SQL para criar a tabela
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MATERIA_ID + " integer, "
            + COLUMN_DURACAO + " integer, "
            + COLUMN_DIA_DA_SEMANA + " integer, "
            + COLUMN_HORA_INICIO + " integer, "
            + COLUMN_MINUTO_INICIO + " integer "
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
        String[] retorno = { COLUMN_DURACAO, COLUMN_ID, COLUMN_MATERIA_ID };
        return retorno;
    }
}
