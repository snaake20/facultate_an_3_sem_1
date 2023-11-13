package com.example.aplicatieinventariere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<Item> data = new ArrayList<Item>();
    List<Item> items = new ArrayList<Item>();
    ItemAdapter adapter;
    Button btn_sort;

    private void initItems(List<Item> list) {
        list.add(new Item(69, "foarfeca", 420, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.emidale.ro%2F_galerie%2Fproduse%2F1724%2Ffoarfeca-croitorie-f31830900-23-cm-1.jpg&f=1&nofb=1&ipt=7e0d2f40340f607e48886f84fb260608dc89bd88492192343d044f4560a2dd74&ipo=images"));
        list.add(new Item(123, "matura", 3, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.homenish.com%2Fwp-content%2Fuploads%2F2021%2F06%2FOld-Fashioned-Broom.jpg&f=1&nofb=1&ipt=7d59e4916615020c37f45dce403f9e3b6915310da6423f4adac7431a496c0c99&ipo=images"));
        list.add(new Item(234, "ciocan", 6, "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.evoracenter.ro%2Fuserfiles%2F7d8a9b76-9ea6-48ae-9883-5065bd2e2104%2Fproducts%2F956960_big.jpg&f=1&nofb=1&ipt=6cfc2480305c4906af97727c81cca19907ba353213de34d96aa5911528296959&ipo=images"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lista de inventar");
        setSupportActionBar(toolbar);

        initItems(data);
        initItems(items);

        btn_sort = findViewById(R.id.btn_sortare);

        listView = findViewById(R.id.listview);
        adapter = new ItemAdapter(this, R.layout.lv_item, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, pos, id) -> {
            Item item = (Item) adapterView.getItemAtPosition(pos);
            startActivityForResult(new Intent(this, ItemForm.class).putExtra("item", item), 1);
        });
        listView.setOnItemLongClickListener(((adapterView, view, pos, id) -> {
            items.remove(pos);
            ((ItemAdapter)adapterView.getAdapter()).notifyDataSetChanged();
            return true;
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) { // Check if the result is for our request code
            if (resultCode == RESULT_OK) {
                // Handle a successful result here
                if (data != null) {
                    Item item = (Item) data.getSerializableExtra("item");
                    boolean[] isInList = {false};
                    items.forEach((it -> {
                        if (it.getCod() == item.getCod()) {
                            isInList[0] = true;
                            it.setDenumire(item.getDenumire());
                            it.setInventarFaptic(item.getInventarFaptic());
                        }
                    } ));
                    if (!isInList[0]) {
                        items.add(item);
                    }
                    adapter.notifyDataSetChanged();
                    // Use the result data as needed
                }
            } else if (resultCode == RESULT_CANCELED) {
                // Handle the case when the user cancels the operation

            }
        }
    }

    public void addItem(View v) {
        // deschidem activitatea pentru preluare date
        startActivityForResult(new Intent(this, ItemForm.class), 1);
    }

    public void reset(View v) {
        items.clear();
        data.forEach(profil -> {
            items.add(profil);
        });
        adapter.notifyDataSetChanged();
    }

    public void sortHandler(View v) {
        String sorting = String.valueOf(btn_sort.getText());
        items.sort(Comparator.comparing(Item::getInventarFaptic));
        if (sorting.equals("Crescator")) {
            btn_sort.setText("Descrescator");
        } else {
            Collections.reverse(items);
            btn_sort.setText("Crescator");
        }
        adapter.notifyDataSetChanged();
    }
}