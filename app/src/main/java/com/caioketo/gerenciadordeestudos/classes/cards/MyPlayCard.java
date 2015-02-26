package com.caioketo.gerenciadordeestudos.classes.cards;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caioketo.gerenciadordeestudos.R;
import com.fima.cardsui.objects.Card;
import com.fima.cardsui.objects.RecyclableCard;

/**
 * Created by Caio on 02/10/2014.
 */
public class MyPlayCard extends RecyclableCard {

    public interface OnDismissCallback {
        public void onDismiss(Card card);
    }


    private OnDismissCallback dismissCallback;

    public void setDismissCallback(OnDismissCallback _dismissCallback) {
        dismissCallback = _dismissCallback;
    }

    public MyPlayCard(String titlePlay, String description, String color,
                      String titleColor, Boolean hasOverflow, Boolean isClickable) {
        super(titlePlay, description, color, titleColor, hasOverflow,
                isClickable);
    }

    @Override
    public void OnSwipeCard() {
        super.OnSwipeCard();
        dismissCallback.onDismiss(this);
    }

    @Override
    protected int getCardLayoutId() {
        return R.layout.card_play;
    }

    @Override
    protected void applyTo(View convertView) {
        ((TextView) convertView.findViewById(R.id.title)).setText(titlePlay);
        ((TextView) convertView.findViewById(R.id.title)).setTextColor(Color
                .parseColor(titleColor));
        ((TextView) convertView.findViewById(R.id.description))
                .setText(description);
        ((ImageView) convertView.findViewById(R.id.stripe))
                .setBackgroundColor(Color.parseColor(color));

        if (isClickable == true)
            ((LinearLayout) convertView.findViewById(R.id.contentLayout))
                    .setBackgroundResource(R.drawable.selectable_background_cardbank);

        if (hasOverflow == true)
            ((ImageView) convertView.findViewById(R.id.overflow))
                    .setVisibility(View.VISIBLE);
        else
            ((ImageView) convertView.findViewById(R.id.overflow))
                    .setVisibility(View.GONE);
    }
}
