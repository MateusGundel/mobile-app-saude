package com.elefante.app_saude.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elefante.app_saude.R;

import java.util.ArrayList;

public class ListItemAdapter extends ArrayAdapter<ListItem> {
    public ListItemAdapter(Context context, ArrayList<ListItem> listItems){
        super(context, 0, listItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListItem listItem = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_list_view, parent, false);
        }
        TextView tvDescricao = (TextView) convertView.findViewById(R.id.list_item_description);
        TextView tvData = (TextView) convertView.findViewById(R.id.list_item_date);

        tvDescricao.setText(listItem.descricao);
        tvData.setText("Data Registro: " + listItem.data);

        return convertView;
    }
}
