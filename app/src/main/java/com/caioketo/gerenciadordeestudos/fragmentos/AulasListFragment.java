package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.caioketo.gerenciadordeestudos.MainActivity;
import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaAula;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;


public class AulasListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, ICFragment {


    private static final int LOADER = 0;
    private static final int DELETE_ID = Menu.FIRST + 1;

    View rootView;
    ListView listView;
    SimpleCursorAdapter adapter;

    public AulasListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView)rootView.findViewById(R.id.lista1);
        listView.setDividerHeight(2);
        fillData();
        registerForContextMenu(listView);
        return rootView;
    }

    private void fillData() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { TabelaMateria.COLUMN_DESCRICAO, TabelaAula.COLUMN_DURACAO};
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.label, R.id.tvwduracao };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.aula_row, null, from,
                to, 0);

        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        //menu.add(0, UPDATE_ID, 0, R.string.menu_update);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case DELETE_ID:
                Uri uri = Uri.parse(CustomContentProvider.CONTENT_URI_AULA + "/"
                        + info.id);
                getActivity().getContentResolver().delete(uri, null, null);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        switch (loaderID) {
            case LOADER:
                String[] projection = { TabelaAula.addPrefix(TabelaAula.COLUMN_ID), TabelaAula.addPrefix(TabelaAula.COLUMN_MATERIA_ID),
                        TabelaAula.addPrefix(TabelaAula.COLUMN_DURACAO) + " as duracao",
                        TabelaMateria.addPrefix(TabelaMateria.COLUMN_DESCRICAO) + " as descricao"};
                return new CursorLoader(getActivity(),
                        CustomContentProvider.CONTENT_URI_AULA_J, projection, null, null, null);
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
    public boolean fCreateOptionMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.materia, menu);
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.title_section2));
        return true;
    }

    @Override
    public boolean fOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            //add
            AddAulaFragment addAula = new AddAulaFragment();
            MainActivity.instance.getFragmentManager().beginTransaction()
                    .replace(R.id.container, addAula)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean fOnBackPressed() {
        return false;
    }
}