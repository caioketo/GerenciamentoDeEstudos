package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.classes.Aula;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaAula;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;
import com.caioketo.weekview.WeekView;
import com.caioketo.weekview.WeekViewEvent;
import java.util.ArrayList;
import java.util.List;

public class AddAulaFragment extends Fragment implements
        ICFragment {
    View rootView;
    WeekView mWeekView;
    List<WeekViewEvent> mEvents = new ArrayList<WeekViewEvent>();

    public AddAulaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_aula, container, false);
        mWeekView = (WeekView) rootView.findViewById(R.id.weekView);
        mWeekView.setNumberOfVisibleDays(7);
        mWeekView.setHourHeight(150);
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {
                //
                Toast.makeText(getActivity(), "Teste", Toast.LENGTH_SHORT).show();
            }
        });
        mWeekView.setOnTimeClickListener(new WeekView.TimeClickListener() {
            @Override
            public void onTimeClick(int hour, int day) {
                //Toast.makeText(getActivity(), "Day: " + Integer.toString(day) + " Hour: " + Integer.toString(hour), Toast.LENGTH_SHORT).show();
                //mEvents.add(new WeekViewEvent(1, "Teste", day, hour, 0, hour, 30));
                //mWeekView.setEvents(mEvents);
                String[] projection = { TabelaMateria.COLUMN_ID, TabelaMateria.COLUMN_DESCRICAO };
                Cursor addcursor = getActivity().getContentResolver().query(CustomContentProvider.CONTENT_URI_MATERIA, projection,
                        null, null, null);

                final int diaDaSemana = day;
                if (addcursor != null) {
                    String[] adapterCols = new String[]{ TabelaMateria.COLUMN_DESCRICAO };
                    int[] adapterRowViews=new int[]{android.R.id.text1};
                    SimpleCursorAdapter sca = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                            addcursor, adapterCols, adapterRowViews);

                    sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    final Dialog d = new Dialog(getActivity());
                    d.setTitle(R.string.titulo_secao_add_aula);
                    d.setContentView(R.layout.add_aula_dialog);
                    final Spinner spn = (Spinner)d.findViewById(R.id.spnmateria);
                    spn.setAdapter(sca);
                    final TimePicker tpc = (TimePicker)d.findViewById(R.id.tpcinicio);
                    tpc.setCurrentHour(hour);
                    tpc.setCurrentMinute(0);
                    final EditText edt = (EditText)d.findViewById(R.id.edtnota);

                    d.findViewById(R.id.btnconfirmar).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Aula aula = new Aula();
                            aula.setDiaDaSemana(diaDaSemana);
                            aula.setDuracao(Integer.parseInt(edt.getText().toString()));
                            aula.setHoraInicio(tpc.getCurrentHour());
                            aula.setMinutoInicio(tpc.getCurrentMinute());
                            int materia = ((Cursor)spn.getSelectedItem()).getInt(((Cursor)spn.getSelectedItem()).getColumnIndexOrThrow(TabelaMateria.COLUMN_ID));
                            aula.setMateriaById(materia);
                            ContentValues values = aula.toContent();
                            getActivity().getContentResolver().insert(CustomContentProvider.CONTENT_URI_AULA, values);
                            fillData();
                            d.dismiss();
                        }
                    });
                    d.show();
                }
            }
        });
        mWeekView.setMonthChangeListener(new WeekView.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int i, int i2) {
                return new ArrayList<WeekViewEvent>();
            }
        });
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent weekViewEvent, RectF rectF) {
                //
            }
        });

        fillData();

        return rootView;
    }

    public void fillData() {
        mEvents.clear();
        String[] projection = { TabelaAula.addPrefix(TabelaAula.COLUMN_ID), TabelaAula.COLUMN_MATERIA_ID,
                TabelaAula.addPrefix(TabelaAula.COLUMN_DURACAO) + " as duracao",
                TabelaMateria.addPrefix(TabelaMateria.COLUMN_DESCRICAO) + " as descricao",
                TabelaMateria.addPrefix(TabelaMateria.COLUMN_COR) + " as " + TabelaMateria.COLUMN_COR,
                TabelaAula.COLUMN_DIA_DA_SEMANA, TabelaAula.COLUMN_HORA_INICIO, TabelaAula.COLUMN_MINUTO_INICIO};
        Cursor _cursor = getActivity().getContentResolver().query(CustomContentProvider.CONTENT_URI_AULA_J, projection,
                null, null, null);

        for (int i = 0; i < _cursor.getCount(); i++) {
            _cursor.moveToPosition(i);
            int horaInicio = _cursor.getInt(_cursor.getColumnIndexOrThrow(TabelaAula.COLUMN_HORA_INICIO));
            int minutoInicio = _cursor.getInt(_cursor.getColumnIndexOrThrow(TabelaAula.COLUMN_MINUTO_INICIO));
            int minutoFim = minutoInicio + _cursor.getInt(_cursor.getColumnIndexOrThrow(TabelaAula.COLUMN_DURACAO));
            int horaFim = horaInicio;

            if (minutoFim > 60) {
                horaFim += 1;
                minutoFim -= 60;
                if (horaFim > 24) {
                    horaFim = 24;
                }
            }
            String materia = _cursor.getString(_cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO));
            int diaDaSemana = _cursor.getInt(_cursor.getColumnIndexOrThrow(TabelaAula.COLUMN_DIA_DA_SEMANA));

            WeekViewEvent event = new WeekViewEvent((long)_cursor.getInt(_cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_ID)),
                    materia, diaDaSemana, horaInicio, minutoInicio, horaFim, minutoFim);

            event.setColor(_cursor.getInt(_cursor.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR)));
            mEvents.add(event);
        }

        mWeekView.setEvents(mEvents);
        _cursor.close();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public boolean fCreateOptionMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        ActionBar actionBar = getActivity().getActionBar();
        assert (actionBar != null);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(getString(R.string.title_section3));
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