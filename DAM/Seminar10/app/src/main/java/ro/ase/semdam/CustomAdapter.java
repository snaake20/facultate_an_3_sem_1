package ro.ase.semdam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapter extends ArrayAdapter<Masina> {

    private Context context;
    private int resource;
    private List<Masina> masinaList;
    private LayoutInflater layoutInflater;

    public CustomAdapter(@NonNull Context context, int resource, List<Masina> lista, LayoutInflater layoutInflater) {
        super(context, resource, lista);
        this.context = context;
        this.resource = resource;
        this.masinaList = lista;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);

        Masina masina = masinaList.get(position);

        if(masina!=null)
        {
            TextView tv1 = view.findViewById(R.id.tvMarca);
            tv1.setText(masina.getMarca());

            TextView tv2 = view.findViewById(R.id.tvDataFabricatiei);
            tv2.setText(masina.getDataFabricatiei().toString());

            TextView tv3 = view.findViewById(R.id.tvPret);
            tv3.setText(String.valueOf(masina.getPret()));

            TextView tv4 = view.findViewById(R.id.tvCuloare);
            tv4.setText(masina.getCuloare());

            TextView tv5 = view.findViewById(R.id.tvMotorizare);
            tv5.setText(masina.getMotorizare());
        }

        return view;
    }
}
