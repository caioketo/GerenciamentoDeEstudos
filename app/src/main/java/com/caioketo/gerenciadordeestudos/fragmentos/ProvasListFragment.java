package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;
import com.caioketo.gerenciadordeestudos.database.TabelaProva;

import java.util.Calendar;

/**
 * Created by Caio on 17/10/2014.
 */
public class ProvasListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, ICFragment {

    View rootView;
    ListView lstProvas;
    SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_provas, container, false);
        lstProvas = (ListView)rootView.findViewById(R.id.lstprovas);
        registerForContextMenu(lstProvas);
        String[] from = new String[] { TabelaProva.COLUMN_DATA };
        int[] to = new int[] { R.id.textView };
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.prova_row_2, null, from,
                to, 0){
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                final LayoutInflater inflater = LayoutInflater.from(context);

                View row = inflater.inflate(R.layout.prova_row, parent, false);
                TextView textView;
                textView = (TextView)row.findViewById(R.id.textView);
                ImageView img;
                img = (ImageView)row.findViewById(R.id.img);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(cursor.getLong(cursor.getColumnIndexOrThrow(TabelaProva.COLUMN_DATA)));
                String prova = Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
                        Integer.toString(cal.get(Calendar.MONTH) + 1) + "/" + Integer.toString(cal.get(Calendar.YEAR)) +
                        " - Prova de " + cursor.getString(cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO));
                textView.setText(prova);
                Bitmap image = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
                image.eraseColor(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR)));
                img.setImageBitmap(image);
                return row;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView textView;
                textView = (TextView) view.findViewById(R.id.textView);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(cursor.getLong(cursor.getColumnIndexOrThrow(TabelaProva.COLUMN_DATA)));
                String prova = Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
                        Integer.toString(cal.get(Calendar.MONTH) + 1) + "/" + Integer.toString(cal.get(Calendar.YEAR)) +
                        " - Prova de " + cursor.getString(cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO)) +
                        " - Nota: " + Float.toString(cursor.getFloat(cursor.getColumnIndexOrThrow(TabelaProva.COLUMN_NOTA)));

                textView.setText(prova);
                ImageView img;
                img = (ImageView)view.findViewById(R.id.img);
                Bitmap image = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
                image.eraseColor(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR)));
                img.setImageBitmap(image);
            }
        };
        lstProvas.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);

        return rootView;
    }

    @Override
    public boolean fCreateOptionMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.global, menu);
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.titulo_secao_provas));
        return false;
    }

    @Override
    public boolean fOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        String[] projection = { TabelaProva.addPrefix(TabelaProva.COLUMN_ID), TabelaProva.addPrefix(TabelaProva.COLUMN_MATERIA_ID),
                TabelaMateria.addPrefix(TabelaMateria.COLUMN_DESCRICAO) + " as descricao", TabelaProva.COLUMN_DATA,
                TabelaMateria.COLUMN_COR, TabelaProva.COLUMN_NOTA };
        switch (loaderID) {
            case 0:
                return new CursorLoader(getActivity(),
                        CustomContentProvider.CONTENT_URI_PROVA_J, projection, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.changeCursor(null);
    }

    @Override
    public boolean fOnBackPressed() {
        return false;
    }
}
