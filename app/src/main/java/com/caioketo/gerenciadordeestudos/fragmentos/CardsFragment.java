package com.caioketo.gerenciadordeestudos.fragmentos;

import android.app.Fragment;
import android.content.ContentProvider;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.caioketo.gerenciadordeestudos.MainActivity;
import com.caioketo.gerenciadordeestudos.R;
import com.caioketo.gerenciadordeestudos.classes.cards.MyPlayCard;
import com.caioketo.gerenciadordeestudos.database.CustomContentProvider;
import com.caioketo.gerenciadordeestudos.database.TabelaMateria;
import com.caioketo.gerenciadordeestudos.database.TabelaProva;
import com.caioketo.gerenciadordeestudos.utils.Expression;
import com.fima.cardsui.objects.Card;
import com.fima.cardsui.objects.CardFactory;
import com.fima.cardsui.objects.CardModel;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.tyczj.extendedcalendarview.Event;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by Caio on 02/10/2014.
 */
public class CardsFragment extends Fragment implements
        ICFragment {

    View rootView;
    CardUI mCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cards, container, false);
        mCardView = (CardUI)rootView.findViewById(R.id.cardsview);
        mCardView.setSwipeable(true);
        fillProvas();
        fillMedias();
        return rootView;
    }

    public void fillMedias() {
        String[] projection = { TabelaMateria.COLUMN_ID, TabelaMateria.COLUMN_DESCRICAO,
                TabelaMateria.COLUMN_COR, TabelaMateria.COLUMN_CALCULO_MEDIA };
        Cursor c = getActivity().getContentResolver().query(CustomContentProvider.CONTENT_URI_MATERIA, projection,
                null, null, null);
        if (c != null && c.moveToFirst()) {
            CardStack stack2 = new CardStack();
            stack2.setTitle("MÉDIAS");
            mCardView.addStack(stack2);
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


                MyPlayCard mediaCard = new MyPlayCard(c.getString(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO)),
                        "Média: " + expression.eval().toString(),
                        String.format("#%06X", 0xFFFFFF & c.getInt(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR))),
                        "black", true, false);

                mediaCard.setDismissCallback(new MyPlayCard.OnDismissCallback() {
                    @Override
                    public void onDismiss(Card card) {
                        //TODO controlar os cartões que foram dismisseds para não mostrar mais
                    }
                });
                mCardView.addCardToLastStack(mediaCard);
            } while (c.moveToNext());
            c.close();
        }
        mCardView.refresh();
    }

    public void fillProvas() {
        Calendar cal = Calendar.getInstance();
        Calendar hoje = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(hoje.get(Calendar.YEAR), hoje.get(Calendar.MONTH), hoje.get(Calendar.DAY_OF_MONTH));
        String[] projection = { TabelaProva.addPrefix(TabelaProva.COLUMN_ID), TabelaMateria.COLUMN_DESCRICAO,
                TabelaMateria.COLUMN_COR, TabelaProva.COLUMN_DATA, TabelaProva.COLUMN_CONTEUDO };
        Cursor c = MainActivity.instance.getContentResolver().query(CustomContentProvider.CONTENT_URI_PROVA_J, projection,
                " data >= ? ", new String[] { Long.toString(cal.getTimeInMillis()) }, null);
        if (c != null && c.moveToFirst()) {
            CardStack stack2 = new CardStack();
            stack2.setTitle("PRÓXIMAS PROVAS");
            mCardView.addStack(stack2);
            do {
                MyPlayCard provaCard = new MyPlayCard(c.getString(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_DESCRICAO)),
                        c.getString(c.getColumnIndexOrThrow(TabelaProva.COLUMN_CONTEUDO)),
                        String.format("#%06X", 0xFFFFFF & c.getInt(c.getColumnIndexOrThrow(TabelaMateria.COLUMN_COR))),
                        "black", true, false);

                provaCard.setDismissCallback(new MyPlayCard.OnDismissCallback() {
                    @Override
                    public void onDismiss(Card card) {
                        //TODO controlar os cartões que foram dismisseds para não mostrar mais
                    }
                });
                mCardView.addCardToLastStack(provaCard);
            } while (c.moveToNext());
            c.close();
        }
        mCardView.refresh();
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
