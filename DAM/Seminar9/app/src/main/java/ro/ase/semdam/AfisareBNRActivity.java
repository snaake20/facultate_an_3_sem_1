package ro.ase.semdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AfisareBNRActivity extends AppCompatActivity {

    List<CursValutar> listaCursuri = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afisare_bnr);

        Intent intent = getIntent();

        CursValutar cursValutar = (CursValutar) intent.getSerializableExtra("curs");
        listaCursuri.add(cursValutar);

        ListView listView = findViewById(R.id.listViewCursuri);

        ArrayAdapter<CursValutar> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listaCursuri);
        listView.setAdapter(adapter);
    }
}