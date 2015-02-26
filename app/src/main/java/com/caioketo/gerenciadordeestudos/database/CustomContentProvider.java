package com.caioketo.gerenciadordeestudos.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.caioketo.gerenciadordeestudos.utils.Util;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Caio on 17/09/2014.
 */
public class CustomContentProvider extends ContentProvider {
    private DatabaseHelper database;

    public static final String AUTHORITY = "com.caioketo.gerenciadordeestudos.database.customcontentprovider";

    // ------- Definimos URIs uma para cada tabela
    private static final String PATH_AULA = "aula";
    public static final Uri CONTENT_URI_AULA = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_AULA);
    private static final String PATH_AULA_J = "aula_j";
    public static final Uri CONTENT_URI_AULA_J = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_AULA_J);
    private static final String PATH_MATERIA = "materia";
    public static final Uri CONTENT_URI_MATERIA = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_MATERIA);
    private static final String PATH_PROVA = "prova";
    public static final Uri CONTENT_URI_PROVA = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_PROVA);
    private static final String PATH_PROVA_J = "prova_j";
    public static final Uri CONTENT_URI_PROVA_J = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_PROVA_J);
    private static final String PATH_TRABALHO = "trabalho";
    public static final Uri CONTENT_URI_TRABALHO = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_TRABALHO);
    private static final String PATH_ANEXOS_TRABALHO = "anexos_trabalho";
    public static final Uri CONTENT_URI_ANEXOS_TRABALHO = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_ANEXOS_TRABALHO);
    private static final String PATH_CHECKLISTS_TRABALHO = "anexos_trabalho";
    public static final Uri CONTENT_URI_CHECKLISTS_TRABALHO = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_CHECKLISTS_TRABALHO);
    private static final String PATH_TRABALHO_J = "trabalho_j";
    public static final Uri CONTENT_URI_TRABALHO_J = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_TRABALHO_J);


    // ------- Configurando o UriMatcher
    private static final int AULA = 10;
    private static final int AULA_ID = AULA + 1;
    private static final int MATERIA = AULA_ID + 1;
    private static final int MATERIA_ID = MATERIA + 1;
    private static final int PROVA = MATERIA_ID + 1;
    private static final int PROVA_ID = PROVA + 1;
    private static final int AULA_J = PROVA_ID + 1;
    private static final int AULA_J_ID = AULA_J + 1;
    private static final int PROVA_J = AULA_J_ID + 1;
    private static final int TRABALHO = PROVA_J + 1;
    private static final int TRABALHO_ID = TRABALHO + 1;
    private static final int ANEXOS_TRABALHO = TRABALHO_ID + 1;
    private static final int ANEXOS_TRABALHO_ID = ANEXOS_TRABALHO + 1;
    private static final int TRABALHO_J = ANEXOS_TRABALHO_ID + 1;
    private static final int CHECKLISTS_TRABALHO = TRABALHO_J + 1;
    private static final int CHECKLISTS_TRABALHO_ID = CHECKLISTS_TRABALHO + 1;



    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, PATH_AULA, AULA);
        sURIMatcher.addURI(AUTHORITY, PATH_AULA + "/#", AULA_ID);
        sURIMatcher.addURI(AUTHORITY, PATH_MATERIA, MATERIA);
        sURIMatcher.addURI(AUTHORITY, PATH_MATERIA + "/#", MATERIA_ID);
        sURIMatcher.addURI(AUTHORITY, PATH_PROVA, PROVA);
        sURIMatcher.addURI(AUTHORITY, PATH_PROVA + "/#", PROVA_ID);
        sURIMatcher.addURI(AUTHORITY, PATH_AULA_J, AULA_J);
        sURIMatcher.addURI(AUTHORITY, PATH_AULA_J + "/#", AULA_J_ID);
        sURIMatcher.addURI(AUTHORITY, PATH_PROVA_J, PROVA_J);
        sURIMatcher.addURI(AUTHORITY, PATH_TRABALHO, TRABALHO);
        sURIMatcher.addURI(AUTHORITY, PATH_TRABALHO + "/#", TRABALHO_ID);
        sURIMatcher.addURI(AUTHORITY, PATH_ANEXOS_TRABALHO, ANEXOS_TRABALHO);
        sURIMatcher.addURI(AUTHORITY, PATH_ANEXOS_TRABALHO + "/#", ANEXOS_TRABALHO_ID);
        sURIMatcher.addURI(AUTHORITY, PATH_TRABALHO_J, TRABALHO_J);
        sURIMatcher.addURI(AUTHORITY, PATH_CHECKLISTS_TRABALHO, CHECKLISTS_TRABALHO);
        sURIMatcher.addURI(AUTHORITY, PATH_CHECKLISTS_TRABALHO + "/#", CHECKLISTS_TRABALHO_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return false;
    }

    public Cursor queryStr(String sql, String[] selecionArgs) {
        return database.getWritableDatabase().rawQuery(sql, selecionArgs);
    }

    //@Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Vamos utilizar o queryBuilder em vez do método query()
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        TabelaBase tabela;
        StringBuilder sb = new StringBuilder();
        boolean add_id = false;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db;
        Cursor cursor;
        switch (uriType) {
            case MATERIA_ID:
                add_id = true;
            case MATERIA:
                tabela = TabelaMateria.getInstance();
                break;
            case AULA_ID:
                add_id = true;
            case AULA:
                tabela = TabelaAula.getInstance();
                break;
            case PROVA_ID:
                add_id = true;
            case PROVA:
                tabela = TabelaProva.getInstance();
                break;
            case TRABALHO_ID:
                add_id = true;
            case TRABALHO:
                tabela = TabelaTrabalho.getInstance();
                break;
            case ANEXOS_TRABALHO_ID:
                add_id = true;
            case ANEXOS_TRABALHO:
                tabela = TabelaAnexosTrabalho.getInstance();
                break;
            case CHECKLISTS_TRABALHO_ID:
                add_id = true;
            case CHECKLISTS_TRABALHO:
                tabela = TabelaChecklistsTrabalho.getInstance();
                break;
            case AULA_J:
            case AULA_J_ID:
                sb.append(TabelaAula.TABLE);
                sb.append(" LEFT OUTER JOIN ");
                sb.append(TabelaMateria.TABLE);
                sb.append(" ON (");
                sb.append(TabelaAula.addPrefix(TabelaAula.COLUMN_MATERIA_ID));
                sb.append(" = ");
                sb.append(TabelaMateria.addPrefix(TabelaMateria.COLUMN_ID));
                sb.append(")");
                queryBuilder.setTables(sb.toString());
                db = database.getWritableDatabase();
                cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
                // Notificamos caso exista algum Listener
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            case PROVA_J:
                sb.append(TabelaProva.TABLE);
                sb.append(" LEFT OUTER JOIN ");
                sb.append(TabelaMateria.TABLE);
                sb.append(" ON (");
                sb.append(TabelaProva.addPrefix(TabelaProva.COLUMN_MATERIA_ID));
                sb.append(" = ");
                sb.append(TabelaMateria.addPrefix(TabelaMateria.COLUMN_ID));
                sb.append(")");
                queryBuilder.setTables(sb.toString());
                db = database.getWritableDatabase();
                cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
                // Notificamos caso exista algum Listener
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;
            case TRABALHO_J:
                sb.append(TabelaTrabalho.TABLE);
                sb.append(" LEFT OUTER JOIN ");
                sb.append(TabelaMateria.TABLE);
                sb.append(" ON (");
                sb.append(TabelaTrabalho.addPrefix(TabelaTrabalho.COLUMN_MATERIA_ID));
                sb.append(" = ");
                sb.append(TabelaMateria.addPrefix(TabelaMateria.COLUMN_ID));
                sb.append(")");
                queryBuilder.setTables(sb.toString());
                db = database.getWritableDatabase();
                cursor = queryBuilder.query(db, projection, selection,
                        selectionArgs, null, null, sortOrder);
                // Notificamos caso exista algum Listener
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        assert (tabela != null);
        if (add_id) {
            queryBuilder.appendWhere(tabela.getColumnID() + "="
                    + uri.getLastPathSegment());
        }
        queryBuilder.setTables(tabela.getTable());
        db = database.getWritableDatabase();
        cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // Notificamos caso exista algum Listener
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    public String getPATH(int pathID) {
        switch (pathID) {
            case AULA:
                return PATH_AULA;
            case MATERIA:
                return PATH_MATERIA;
            case PROVA:
                return PATH_PROVA;
            case TRABALHO:
                return PATH_TRABALHO;
            case ANEXOS_TRABALHO:
                return PATH_ANEXOS_TRABALHO;
            case CHECKLISTS_TRABALHO:
                return PATH_CHECKLISTS_TRABALHO;
            default:
                return "";
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        TabelaBase tabela;
        long id = 0;
        switch (uriType) {
            case AULA:
                tabela = TabelaAula.getInstance();
                break;
            case MATERIA:
                tabela = TabelaMateria.getInstance();
                break;
            case PROVA:
                tabela = TabelaProva.getInstance();
                break;
            case TRABALHO:
                tabela = TabelaTrabalho.getInstance();
                break;
            case ANEXOS_TRABALHO:
                tabela = TabelaAnexosTrabalho.getInstance();
                break;
            case CHECKLISTS_TRABALHO:
                tabela = TabelaChecklistsTrabalho.getInstance();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        id = sqlDB.insert(tabela.getTable(), null, values);
        Log.d("INSERRT", Long.toString(id));
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(getPATH(uriType) + "/" + id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        String id;
        TabelaBase tabela;
        boolean is_id = false;
        switch (uriType) {
            case AULA_ID:
                is_id = true;
            case AULA:
                tabela = TabelaAula.getInstance();
                break;
            case MATERIA_ID:
                is_id = true;
            case MATERIA:
                tabela = TabelaMateria.getInstance();
                break;
            case PROVA_ID:
                is_id = true;
            case PROVA:
                tabela = TabelaProva.getInstance();
                break;
            case TRABALHO_ID:
                is_id = true;
            case TRABALHO:
                tabela = TabelaTrabalho.getInstance();
                break;
            case ANEXOS_TRABALHO_ID:
                is_id = true;
            case ANEXOS_TRABALHO:
                tabela = TabelaAnexosTrabalho.getInstance();
                break;
            case CHECKLISTS_TRABALHO_ID:
                is_id = true;
            case CHECKLISTS_TRABALHO:
                tabela = TabelaChecklistsTrabalho.getInstance();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (!is_id) {
            rowsDeleted = sqlDB.delete(tabela.getTable(), selection,
                    selectionArgs);
        }
        else {
            id = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = sqlDB.delete(tabela.getTable(),
                        tabela.getColumnID() + "=" + id,
                        null);
            } else {
                rowsDeleted = sqlDB.delete(tabela.getTable(),
                        tabela.getColumnID() + "=" + id
                                + " and " + selection,
                        selectionArgs);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        String id;
        TabelaBase tabela;
        boolean is_id = false;
        switch (uriType) {
            case AULA_ID:
                is_id = true;
            case AULA:
                tabela = TabelaAula.getInstance();
                break;
            case MATERIA_ID:
                is_id = true;
            case MATERIA:
                tabela = TabelaMateria.getInstance();
                break;
            case PROVA_ID:
                is_id = true;
            case PROVA:
                tabela = TabelaProva.getInstance();
                break;
            case TRABALHO_ID:
                is_id = true;
            case TRABALHO:
                tabela = TabelaTrabalho.getInstance();
                break;
            case ANEXOS_TRABALHO_ID:
                is_id = true;
            case ANEXOS_TRABALHO:
                tabela = TabelaAnexosTrabalho.getInstance();
                break;
            case CHECKLISTS_TRABALHO_ID:
                is_id = true;
            case CHECKLISTS_TRABALHO:
                tabela = TabelaChecklistsTrabalho.getInstance();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (!is_id) {
            rowsUpdated = sqlDB.update(tabela.getTable(),
                    values,
                    selection,
                    selectionArgs);
        }
        else {
            id = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsUpdated = sqlDB.update(tabela.getTable(),
                        values,
                        tabela.getColumnID() + "=" + id,
                        null);
            } else {
                rowsUpdated = sqlDB.update(tabela.getTable(),
                        values,
                        tabela.getColumnID() + "=" + id
                                + " and "
                                + selection,
                        selectionArgs);
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = Util.concat(Util.concat(TabelaMateria.getInstance().getColumns(),
                        TabelaProva.getInstance().getColumns()), TabelaAula.getInstance().getColumns());
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}