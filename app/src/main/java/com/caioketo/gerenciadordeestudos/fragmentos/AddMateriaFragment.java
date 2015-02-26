package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.caioketo.gerenciadordeestudos.MainActivity;
import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.classes.Materia;
import com.caioketo.gerenciadordeestudos.colorpicker.ColorPicker;
import com.caioketo.gerenciadordeestudos.colorpicker.ColorPickerDialog;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;

public class AddMateriaFragment extends Fragment implements ICFragment, ColorPickerDialog.OnColorSelectedListener {

    View rootView;
    EditText edtDescricao;
    EditText edtFormula;
    ImageView imgCor;
    public Uri uri = null;
    int selectedCor = Color.WHITE;
    ColorPickerDialog colorPickerDialog;

    public AddMateriaFragment() {
    }

    @Override
    public void onColorSelected(int color) {
        selectedCor = color;
        imgCor.setBackgroundColor(selectedCor);
    }


    private void fillData(Uri uri) {
        String[] projection = { TabelaMateria.COLUMN_ID, TabelaMateria.COLUMN_DESCRICAO, TabelaMateria.COLUMN_COR,
            TabelaMateria.COLUMN_CALCULO_MEDIA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            edtDescricao.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO)));
            edtFormula.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(TabelaMateria.COLUMN_CALCULO_MEDIA)));
            int initialColor = cursor.getInt(cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR));
            imgCor.setBackgroundColor(initialColor);
            selectedCor = initialColor;
            colorPickerDialog = new ColorPickerDialog(getActivity(), initialColor, this);
            cursor.close();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_materia, container, false);
        edtDescricao = (EditText)rootView.findViewById(R.id.edtdescricao);
        edtFormula = (EditText)rootView.findViewById(R.id.edtformula);
        imgCor = (ImageView)rootView.findViewById(R.id.btnCor);
        rootView.findViewById(R.id.btnCor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPickerDialog.show();
            }
        });
        if (uri != null) {
            fillData(uri);
        }
        else {
            int initialColor = Color.WHITE;

            colorPickerDialog = new ColorPickerDialog(getActivity(), initialColor, this);
        }
        (rootView.findViewById(R.id.btnconfirmar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descricao = ((EditText)rootView.findViewById(R.id.edtdescricao)).getText().toString();
                String formula = ((EditText)rootView.findViewById(R.id.edtformula)).getText().toString();
                Materia materia = new Materia();
                materia.setDescricao(descricao);
                materia.setCor(selectedCor);
                materia.setCalculoMedia(formula.toUpperCase());
                ContentValues values = materia.toContent();
                if (uri == null) {
                    getActivity().getContentResolver().insert(CustomContentProvider.CONTENT_URI_MATERIA, values);
                }
                else {
                    getActivity().getContentResolver().update(uri, values, null, null);
                }
                MainActivity.instance.getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MateriasListFragment())
                        .commit();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public boolean fCreateOptionMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.titulo_secao_add_materia));
        return true;
    }

    @Override
    public boolean fOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean fOnBackPressed() {
        MainActivity.instance.getFragmentManager().beginTransaction()
                .replace(R.id.container, new MateriasListFragment())
                .commit();
        return true;
    }
}