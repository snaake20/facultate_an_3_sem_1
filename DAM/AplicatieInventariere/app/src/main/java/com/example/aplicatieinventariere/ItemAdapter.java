package com.example.aplicatieinventariere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_item, parent, false);
        }
        TextView lv_nrCrt = convertView.findViewById(R.id.lv_nrCrt), lv_cod = convertView.findViewById(R.id.lv_cod), lv_denumire = convertView.findViewById(R.id.lv_denumire), lv_inventarFaptic = convertView.findViewById(R.id.lv_inventarFaptic);
        Item item = getItem(position);

        lv_nrCrt.setText(String.valueOf(position+1));
        lv_cod.setText(String.valueOf(item.getCod()));
        lv_denumire.setText(item.getDenumire());
        lv_inventarFaptic.setText(String.valueOf(item.getInventarFaptic()));

        return  convertView;
    }
}
