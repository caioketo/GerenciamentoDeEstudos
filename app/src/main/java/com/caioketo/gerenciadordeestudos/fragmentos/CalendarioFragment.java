package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.caioketo.gerenciadordeestudos.MainActivity;
import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.classes.Prova;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;
import com.caioketo.gerenciadordeestudos.database.TabelaProva;
import com.caioketo.gerenciadordeestudos.utils.Util;
import com.tyczj.extendedcalendarview.Day;
import com.tyczj.extendedcalendarview.Event;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarioFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, ICFragment {


    private static final int SET_NOTA_ID = Menu.FIRST + 1;
    private static final int SHOW_NOTA_ID = SET_NOTA_ID + 1;

    View rootView;
    ExtendedCalendarView calendar;
    ListView lstProvas;
    SimpleCursorAdapter adapter;
    long curDay;
    static CalendarioFragment fragThis;

    public CalendarioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragThis = this;
        rootView = inflater.inflate(R.layout.fragment_calendario, container, false);
        lstProvas = (ListView)rootView.findViewById(R.id.lstprovas);
        registerForContextMenu(lstProvas);
        calendar = (ExtendedCalendarView)rootView.findViewById(R.id.calendar);
        calendar.setEventLoaderListener(new Day.EventLoaderListener() {
            @Override
            public ArrayList<Event> loadEvents(long day) {
                ArrayList<Event> events = new ArrayList<Event>();
                String[] projection = { TabelaProva.addPrefix(TabelaProva.COLUMN_ID), TabelaMateria.COLUMN_DESCRICAO,
                        TabelaMateria.COLUMN_COR, TabelaProva.COLUMN_DATA, TabelaProva.COLUMN_CONTEUDO };
                Cursor c = MainActivity.instance.getContentResolver().query(CustomContentProvider.CONTENT_URI_PROVA_J, projection,
                        " data = ? ", new String[] { Long.toString(day) }, null);
                if (c != null && c.moveToFirst()) {
                    do {
                        Event event = new Event(c.getLong(c.getColumnIndexOrThrow(TabelaProva.COLUMN_ID)),
                                c.getLong(c.getColumnIndexOrThrow(TabelaProva.COLUMN_DATA)),
                                c.getLong(c.getColumnIndexOrThrow(TabelaProva.COLUMN_DATA)));
                        event.setName(c.getString(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO)));
                        event.setDescription(c.getString(c.getColumnIndexOrThrow(TabelaProva.COLUMN_CONTEUDO)));
                        event.setLocation(c.getString(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO)));
                        event.setColor(c.getInt(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR)));
                        events.add(event);
                    } while (c.moveToNext());
                    c.close();
                }
                return events;
            }
        });
        calendar.setOnDayClickListener(new ExtendedCalendarView.OnDayClickListener() {
            @Override
            public void onDayClicked(AdapterView<?> adapter, View view, int position, long id, Day day) {
                curDay = Util.D2MS(day.getMonth(), day.getDay(), day.getYear(), 0, 0, 0);
                getLoaderManager().restartLoader(1, null, fragThis);
            }
        });

        fillData();

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, SET_NOTA_ID, 0, R.string.menu_set_nota);
        menu.add(0, SHOW_NOTA_ID, 0, R.string.menu_update);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case SET_NOTA_ID:
                final int notaId = adapter.getCursor().getInt(adapter.getCursor().getColumnIndexOrThrow(TabelaProva.COLUMN_ID));
                final Dialog d = new Dialog(getActivity());
                d.setTitle(R.string.titulo_secao_add_prova);
                d.setContentView(R.layout.set_nota_dialog);
                final EditText edt = (EditText)d.findViewById(R.id.edtnota);

                d.findViewById(R.id.btnconfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(CustomContentProvider.CONTENT_URI_PROVA + "/"
                                + notaId);
                        ContentValues values = new ContentValues();
                        values.put(TabelaProva.COLUMN_NOTA, Float.parseFloat(edt.getText().toString()));
                        getActivity().getContentResolver().update(uri, values, null, null);
                        d.dismiss();
                        getLoaderManager().restartLoader(1, null, fragThis);
                    }
                });
                d.show();
                return true;
            case SHOW_NOTA_ID:
                Toast.makeText(getActivity(),
                        Float.toString(adapter.getCursor().getFloat(adapter.getCursor().getColumnIndexOrThrow(TabelaProva.COLUMN_NOTA))),
                        Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public boolean fCreateOptionMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.materia, menu);
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.titulo_secao_calendario));
        return true;
    }

    @Override
    public boolean fOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            String[] projection = { TabelaMateria.COLUMN_ID, TabelaMateria.COLUMN_DESCRICAO };
            Cursor addcursor = getActivity().getContentResolver().query(CustomContentProvider.CONTENT_URI_MATERIA, projection,
                    null, null, null);
            if (addcursor != null) {
                String[] adapterCols = new String[]{ TabelaMateria.COLUMN_DESCRICAO };
                int[] adapterRowViews=new int[]{android.R.id.text1};
                SimpleCursorAdapter sca = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                        addcursor, adapterCols, adapterRowViews);

                sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                final Dialog d = new Dialog(getActivity());
                d.setTitle(R.string.titulo_secao_add_prova);
                d.setContentView(R.layout.add_prova_dialog);
                final Spinner spn = (Spinner)d.findViewById(R.id.spnmateria);
                spn.setAdapter(sca);
                final EditText edt = (EditText)d.findViewById(R.id.edtnota);
                final EditText edtVariavel = (EditText)d.findViewById(R.id.edtvariavel);

                d.findViewById(R.id.btnconfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Prova prova = new Prova();
                        prova.setConteudo(edt.getText().toString());
                        prova.setData(curDay);
                        prova.setVariavel(edtVariavel.getText().toString().toUpperCase());
                        prova.setNota(-1);
                        int materia = ((Cursor)spn.getSelectedItem()).getInt(((Cursor)spn.getSelectedItem()).getColumnIndexOrThrow(TabelaMateria.COLUMN_ID));
                        prova.setMateriaById(materia);
                        ContentValues values = prova.toContent();
                        getActivity().getContentResolver().insert(CustomContentProvider.CONTENT_URI_PROVA, values);


                        getLoaderManager().restartLoader(1, null, fragThis);
                        calendar.refreshCalendar();
                        d.dismiss();
                    }
                });
                d.show();
            }
            return true;
        }
        return false;
    }

    private void fillData() {
        String[] from = new String[] { TabelaProva.COLUMN_DATA };
        int[] to = new int[] { R.id.textView };
        Calendar cal = Calendar.getInstance();
        Calendar hoje = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(hoje.get(Calendar.YEAR), hoje.get(Calendar.MONTH), hoje.get(Calendar.DAY_OF_MONTH));
        curDay = cal.getTimeInMillis();

        getLoaderManager().initLoader(1, null, this);
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.prova_row, null, from,
                to, 0) {
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
                        " - Prova de " + cursor.getString(cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO));

                textView.setText(prova);
                ImageView img;
                img = (ImageView)view.findViewById(R.id.img);
                Bitmap image = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
                image.eraseColor(cursor.getInt(cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR)));
                img.setImageBitmap(image);
            }
        };

        lstProvas.setAdapter(adapter);
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
            case 1:
                return new CursorLoader(getActivity(),
                        CustomContentProvider.CONTENT_URI_PROVA_J, projection, " data = ? ", new String[] { Long.toString(curDay) }, null);
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
