package com.caioketo.gerenciadordeestudos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Caio on 17/09/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gde.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método chamado ao criar o BD
    @Override
    public void onCreate(SQLiteDatabase database) {
        TabelaAula.onCreate(database);
        TabelaMateria.onCreate(database);
        TabelaProva.onCreate(database);
        TabelaTrabalho.onCreate(database);
        TabelaAnexosTrabalho.onCreate(database);
    }

    // Método chamado na atualização do BD, se você mudar o DATABASE_VERSION ele irá chamar esse método
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        TabelaAula.onUpgrade(database, oldVersion, newVersion);
        TabelaMateria.onUpgrade(database, oldVersion, newVersion);
        TabelaProva.onUpgrade(database, oldVersion, newVersion);
        TabelaTrabalho.onUpgrade(database, oldVersion, newVersion);
        TabelaAnexosTrabalho.onUpgrade(database, oldVersion, newVersion);
    }
}