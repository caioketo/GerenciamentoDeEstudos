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
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.caioketo.gerenciadordeestudos.MainActivity;
import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;
import com.caioketo.gerenciadordeestudos.database.TabelaTrabalho;
import com.caioketo.gerenciadordeestudos.utils.Util;
import com.melnykov.fab.FloatingActionButton;

public class MateriasListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, ICFragment {

    private static final int LOADER = 0;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int UPDATE_ID = DELETE_ID + 1;

    View rootView;
    SwipeMenuListView listView;
    SimpleCursorAdapter adapter;

    public MateriasListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final MateriasListFragment fragThis = this;
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (SwipeMenuListView)rootView.findViewById(R.id.lista1);
        listView.setMenuCreator(Util.genMenuCreator());
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        adapter.getCursor().moveToPosition(position);
                        int infoId = adapter.getCursor().getInt(adapter.getCursor().getColumnIndexOrThrow(TabelaTrabalho.COLUMN_ID));
                        AddMateriaFragment addMateria = new AddMateriaFragment();
                        Uri editUri = Uri.parse(CustomContentProvider.CONTENT_URI_MATERIA + "/" + infoId);
                        Toast.makeText(getActivity(), Long.toString(infoId), Toast.LENGTH_SHORT).show();
                        addMateria.uri = editUri;
                        MainActivity.instance.getFragmentManager().beginTransaction()
                                .replace(R.id.container, addMateria)
                                .commit();
                        break;
                    case 1:
                        // delete
                        adapter.getCursor().moveToPosition(position);
                        Uri uri = Uri.parse(CustomContentProvider.CONTENT_URI_MATERIA + "/"
                                + adapter.getCursor().getInt(adapter.getCursor().getColumnIndexOrThrow(TabelaTrabalho.COLUMN_ID)));
                        getActivity().getContentResolver().delete(uri, null, null);
                        fillData();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        FloatingActionButton floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.btnAdd);
        floatingActionButton.attachToListView(listView);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMateriaFragment addMateria = new AddMateriaFragment();
                MainActivity.instance.mCurrentFrag = addMateria;
                MainActivity.instance.getFragmentManager().beginTransaction()
                        .replace(R.id.container, addMateria)
                        .commit();
            }
        });
        fillData();
        //registerForContextMenu(listView);
        return rootView;
    }

    private void fillData() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { TabelaMateria.COLUMN_DESCRICAO };
        //String[] from = new String[] { TabelaProva.COLUMN_ID };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.tv_name };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.materia_row, null, from,
                to, 0);

        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        menu.add(0, UPDATE_ID, 0, R.string.menu_update);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case DELETE_ID:
                Uri uri = Uri.parse(CustomContentProvider.CONTENT_URI_MATERIA + "/"
                        + info.id);
                getActivity().getContentResolver().delete(uri, null, null);
                fillData();
                return true;
            case UPDATE_ID:
                AddMateriaFragment addMateria = new AddMateriaFragment();
                Uri editUri = Uri.parse(CustomContentProvider.CONTENT_URI_MATERIA + "/" + info.id);
                Toast.makeText(getActivity(), Long.toString(info.id), Toast.LENGTH_SHORT).show();
                addMateria.uri = editUri;
                MainActivity.instance.getFragmentManager().beginTransaction()
                        .replace(R.id.container, addMateria)
                        .commit();
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
                String[] projection = { TabelaMateria.COLUMN_ID, TabelaMateria.COLUMN_DESCRICAO };
                return new CursorLoader(getActivity(),
                        CustomContentProvider.CONTENT_URI_MATERIA, projection, null, null, null);
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
        //getActivity().getMenuInflater().inflate(R.menu.materia, menu);
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.titulo_secao_materia));
        return true;
    }

    @Override
    public boolean fOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean fOnBackPressed() {
        return false;
    }
}