package com.caioketo.gerenciadordeestudos.database.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.Collection;
import java.util.List;

/**
 * Created by Caio on 22/10/2014.
 */
public class CustomArrayAdapter<E> extends ArrayAdapter<E> {
    private final Context context;
    protected final List<E> values;
    protected final LayoutInflater mInflater;

    public CustomArrayAdapter(Context context, int resource, List<E> objects) {
        super(context, resource, objects);
        this.values = objects;
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void add(E newData) {
        values.add(newData);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(Collection<? extends E> newDatas) {
        values.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public void insert(E newData, int index) {
        values.add(index, newData);
        notifyDataSetChanged();
    }

    @Override
    public void remove(E newData) {
        values.remove(newData);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        values.clear();
        notifyDataSetChanged();
    }
}
