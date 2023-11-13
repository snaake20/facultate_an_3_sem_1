package com.example.seminar7;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<CustomItem> {
    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<CustomItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // prima afisare
            // deserializare
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        ImageView image;
        TextView description;
        CustomItem item = getItem(position);
        if (item.description.length() < 5) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_reversed, parent, false);

            image = convertView.findViewById(R.id.lv_iv_reversed);
            description = convertView.findViewById(R.id.lv_tv_reversed);

        } else {

        image = convertView.findViewById(R.id.lv_iv);
        description = convertView.findViewById(R.id.lv_tv);

        }

        image.setImageBitmap(item.getImg());

        description.setText(item.getDescription());


        return  convertView;
    }
}
