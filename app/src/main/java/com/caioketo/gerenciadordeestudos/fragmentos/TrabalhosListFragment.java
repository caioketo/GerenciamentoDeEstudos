package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.caioketo.gerenciadordeestudos.MainActivity;
import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;
import com.caioketo.gerenciadordeestudos.database.TabelaTrabalho;
import com.caioketo.gerenciadordeestudos.utils.Util;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Caio on 20/10/2014.
 */
public class TrabalhosListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, ICFragment {

    View rootView;
    SwipeMenuListView lstTrabalhos;
    SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final TrabalhosListFragment fragThis = this;
        rootView = inflater.inflate(R.layout.fragment_trabalhos, container, false);
        lstTrabalhos = (SwipeMenuListView)rootView.findViewById(R.id.lsttrabalhos);
        lstTrabalhos.setMenuCreator(Util.genMenuCreator());
        lstTrabalhos.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        TrabalhoDetailFragment trabalhoFrag = new TrabalhoDetailFragment();
                        //addTrabalho.frag = fragThis;
                        MainActivity.instance.mCurrentFrag = trabalhoFrag;
                        trabalhoFrag.trabalhoId = adapter.getCursor().getInt(
                                adapter.getCursor().getColumnIndexOrThrow(TabelaTrabalho.COLUMN_ID));
                        MainActivity.instance.getFragmentManager().beginTransaction()
                                .replace(R.id.container, trabalhoFrag)
                                .commit();
                        break;
                    case 1:
                        // delete
                        adapter.getCursor().moveToPosition(position);
                        Uri uri = Uri.parse(CustomContentProvider.CONTENT_URI_TRABALHO + "/"
                                + adapter.getCursor().getInt(adapter.getCursor().getColumnIndexOrThrow(TabelaTrabalho.COLUMN_ID)));
                        getActivity().getContentResolver().delete(uri, null, null);
                        fillData();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        String[] from = new String[] { TabelaTrabalho.COLUMN_ASSUNTO };
        int[] to = new int[] { R.id.tv_name };
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.trabalho_row, null, from,
                to, 0);
        lstTrabalhos.setAdapter(adapter);
        FloatingActionButton floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.btnAdd);
        floatingActionButton.attachToListView(lstTrabalhos);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTrabalhoFragment addTrabalho = new AddTrabalhoFragment();
                addTrabalho.frag = fragThis;
                MainActivity.instance.mCurrentFrag = addTrabalho;
                MainActivity.instance.getFragmentManager().beginTransaction()
                        .replace(R.id.container, addTrabalho)
                        .commit();
                fillData();
            }
        });
        getLoaderManager().initLoader(0, null, this);

        return rootView;
    }


   private void fillData() {
        String[] from = new String[] { TabelaTrabalho.COLUMN_ASSUNTO };
        int[] to = new int[] { R.id.tv_name };
        getLoaderManager().initLoader(0, null, this);

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.trabalho_row, null, from,
                to, 0);

        lstTrabalhos.setAdapter(adapter);
   }

    @Override
    public boolean fCreateOptionMenu(Menu menu)  {
        getActivity().getMenuInflater().inflate(R.menu.global, menu);
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.titulo_secao_trabalhos));
        return false;
    }

    @Override
    public boolean fOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean fOnBackPressed() {
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        String[] projection = { TabelaTrabalho.addPrefix(TabelaTrabalho.COLUMN_ID), TabelaTrabalho.addPrefix(TabelaTrabalho.COLUMN_MATERIA_ID),
                TabelaMateria.addPrefix(TabelaMateria.COLUMN_DESCRICAO) + " as descricao", TabelaTrabalho.COLUMN_ASSUNTO,
                TabelaMateria.COLUMN_COR, TabelaTrabalho.COLUMN_DATA_ENTREGA, TabelaTrabalho.COLUMN_CONTEUDO};
        switch (loaderID) {
            case 0:
                return new CursorLoader(getActivity(),
                        CustomContentProvider.CONTENT_URI_TRABALHO_J, projection, null, null, null);
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
}
