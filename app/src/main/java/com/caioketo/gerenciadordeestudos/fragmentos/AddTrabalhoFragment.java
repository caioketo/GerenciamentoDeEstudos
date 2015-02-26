package com.caioketo.gerenciadordeestudos.fragmentos;


import android.app.ActionBar;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.caioketo.gerenciadordeestudos.MainActivity;
import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.classes.Trabalho;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;
import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Caio on 20/10/2014.
 */
public class AddTrabalhoFragment extends Fragment implements DatePickerDialog.OnDateSetListener, ICFragment {

    public static final String DATEPICKER_TAG = "datepicker";

    View rootView;
    Trabalho trabalho;
    TrabalhosListFragment frag = null;
    Button btnData;
    Spinner spnMateria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);

        rootView = inflater.inflate(R.layout.fragment_add_trabalho, container, false);

        btnData = (Button)rootView.findViewById(R.id.btndata);
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.setYearRange(1985, 2036);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
            }
        });
        btnData.setText("Selecione a Data");
        trabalho = new Trabalho();
        spnMateria = (Spinner)rootView.findViewById(R.id.spnmateria);
        rootView.findViewById(R.id.btnconfirmar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trabalho.setAssunto(((EditText)rootView.findViewById(R.id.edtassunto)).getText().toString());
                trabalho.setConteudo(((EditText)rootView.findViewById(R.id.edtconteudo)).getText().toString());
                int materia = ((Cursor)spnMateria.getSelectedItem()).getInt(((Cursor)spnMateria.getSelectedItem()).getColumnIndexOrThrow(TabelaMateria.COLUMN_ID));
                trabalho.setMateriaById(materia);
                ContentValues values = trabalho.toContent();
                getActivity().getContentResolver().insert(CustomContentProvider.CONTENT_URI_TRABALHO, values);
                MainActivity.instance.getFragmentManager().beginTransaction()
                        .replace(R.id.container, frag)
                        .commit();
            }
        });

        String[] projection = { TabelaMateria.COLUMN_ID, TabelaMateria.COLUMN_DESCRICAO };
        Cursor addcursor = getActivity().getContentResolver().query(CustomContentProvider.CONTENT_URI_MATERIA, projection,
                null, null, null);

        if (addcursor != null) {
            String[] adapterCols = new String[]{TabelaMateria.COLUMN_DESCRICAO};
            int[] adapterRowViews = new int[]{android.R.id.text1};
            SimpleCursorAdapter sca = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                    addcursor, adapterCols, adapterRowViews);

            sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnMateria.setAdapter(sca);
        }

        return rootView;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(year, month, day);
        trabalho.setDataEntrega(calendar.getTimeInMillis());

        btnData.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR));
    }

    @Override
    public boolean fCreateOptionMenu(Menu menu) {
        try {
            getActivity().getMenuInflater().inflate(R.menu.global, menu);
            ActionBar actionBar = getActivity().getActionBar();
            assert actionBar != null;
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(getString(R.string.titulo_secao_add_trabalho));
            return false;
        }
        catch (Exception e) {
            Log.e("GDE", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean fOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean fOnBackPressed() {
        if (frag != null ) {
            MainActivity.instance.getFragmentManager().beginTransaction()
                    .replace(R.id.container, frag)
                    .commit();
            return true;
        }

        return false;
    }
}


