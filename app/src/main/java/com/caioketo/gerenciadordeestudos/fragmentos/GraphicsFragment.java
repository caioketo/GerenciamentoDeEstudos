package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;
import com.caioketo.gerenciadordeestudos.database.TabelaProva;
import com.caioketo.gerenciadordeestudos.utils.Expression;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.XLabels;

import java.util.ArrayList;

/**
 * Created by Caio on 07/10/2014.
 */
public class GraphicsFragment extends Fragment implements
        ICFragment {

    View rootView;
    BarChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_graphics, container, false);
        mChart = (BarChart)rootView.findViewById(R.id.chart);
        mChart.setDescription("");
        mChart.setHighlightIndicatorEnabled(false);
        mChart.setDrawBorder(false);
        mChart.setDrawGridBackground(false);
        mChart.setDrawVerticalGrid(false);
        mChart.setDrawXLabels(true);
        mChart.setDrawYValues(true);
        mChart.setUnit("");
        mChart.setDrawBarShadow(false);
        mChart.setData(generateBarDataMedias());
        XLabels xLabels = mChart.getXLabels();
        xLabels.setCenterXLabelText(true);
        return rootView;
    }

    public BarData generateBarDataMedias() {
        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        String[] projection = { TabelaMateria.COLUMN_ID, TabelaMateria.COLUMN_DESCRICAO,
                TabelaMateria.COLUMN_COR, TabelaMateria.COLUMN_CALCULO_MEDIA };
        Cursor c = getActivity().getContentResolver().query(CustomContentProvider.CONTENT_URI_MATERIA, projection,
                null, null, null);
        int[] colors = null;
        ArrayList<String> labels = new ArrayList<String>();
        int i = 0;
        if (c != null && c.moveToFirst()) {
            colors = new int[c.getCount()];
            do {
                String[] projection2 = { TabelaProva.COLUMN_ID, TabelaProva.COLUMN_NOTA,
                        TabelaProva.COLUMN_VARIAVEL };

                Cursor cursorProvas = getActivity().getContentResolver().query(CustomContentProvider.CONTENT_URI_PROVA,
                        projection2, TabelaProva.COLUMN_MATERIA_ID + " = ?", new String[] { Integer.toString(c.getInt(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_ID))) },
                        null);


                String calculo = c.getString(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_CALCULO_MEDIA));
                Expression expression = new Expression(calculo);

                if (cursorProvas != null && cursorProvas.moveToFirst()) {
                    do {
                        Float nota = cursorProvas.getFloat(cursorProvas.getColumnIndexOrThrow(TabelaProva.COLUMN_NOTA));
                        if (nota >= 0) {
                            String variavel = cursorProvas.getString(cursorProvas.getColumnIndexOrThrow(TabelaProva.COLUMN_VARIAVEL));
                            expression.with(variavel.toUpperCase(), Float.toString(nota));
                        }

                    } while (cursorProvas.moveToNext());
                    cursorProvas.close();
                }

                BarEntry entry = new BarEntry(expression.eval().floatValue(), i,
                        c.getString(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO)));

                labels.add(c.getString(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO)));
                colors[i] = c.getInt(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR));
                i++;

                entries.add(entry);
            } while (c.moveToNext());
            c.close();
        }

        BarDataSet ds = new BarDataSet(entries, "Médias");
        assert (colors != null);
        ds.setColors(colors);
        sets.add(ds);
        BarData d = new BarData(labels, sets);
        return d;
    }

    protected BarData generateBarData(int dataSets, float range, int count) {

        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();

        for(int i = 0; i < dataSets; i++) {

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

            for(int j = 0; j < count; j++) {
                entries.add(new BarEntry((float) (Math.random() * range) + range / 4, j));
            }

            BarDataSet ds = new BarDataSet(entries, "Teste " + i);
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }

        BarData d = new BarData(ChartData.generateXVals(0, count), sets);
        return d;
    }

    public void fillGraph() {
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<Entry> valsComp2 = new ArrayList<Entry>();
        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        // and so on ...

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q"); xVals.add("2.Q"); xVals.add("3.Q"); xVals.add("4.Q");

        LineData data = new LineData(xVals, dataSets);
        //lineChart.setData(data);
    }


    @Override
    public boolean fCreateOptionMenu(Menu menu) {
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
}
