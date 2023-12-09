package com.example.seminar5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.seminar5.data.Profil;

import java.util.List;

public class ProfilAdapter extends ArrayAdapter<Profil> {
    public ProfilAdapter(@NonNull Context context, int resource, @NonNull List<Profil> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // prima afisare
            // deserializare
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.elem_lista_profil, parent, false);
        }

        // TODO: de folosit ViewHolder

        ImageView lvIvProfil = convertView.findViewById(R.id.lv_iv_profil);
        TextView lvTvNume = convertView.findViewById(R.id.lv_tv_nume);
        TextView lvTvEmail = convertView.findViewById(R.id.lv_tv_email);
        TextView lvTvVarsta = convertView.findViewById(R.id.lv_tv_varsta);

        Profil profil = getItem(position);

//        switch (profil.getGen()){
//            case FEMININ:
//                lvIvProfil.setImageResource(R.drawable.female);
//                break;
//            case MASCULIN:
//                lvIvProfil.setImageResource(R.drawable.male);
//                break;
//            case INDECIS:
//                lvIvProfil.setImageResource(R.drawable.idk);
//                break;
//        }

//        lvIvProfil.setImageDrawable();

        lvTvNume.setText(profil.getNume());
        lvTvEmail.setText(profil.getEmail());
        lvTvVarsta.setText(String.valueOf(profil.getVarsta()));

        return  convertView;
    }
}
