package com.example.seminar5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<Profil> users = new ArrayList<Profil>();
    ProfilAdapter adapter;

    private void initUsers() {
        users.add(new Profil("email1@example.com", "example 1", 1, true, Gen.MASCULIN));
        users.add(new Profil("email2@example.com", "example 2", 2, false, Gen.FEMININ));
        users.add(new Profil("email3@example.com", "example 3", 3, true, Gen.INDECIS));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Users");
        setSupportActionBar(toolbar);

        initUsers();

        listView = findViewById(R.id.listview);
        adapter = new ProfilAdapter(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, pos, id) -> {
            // Profil profil = (Profil) adapterView.getAdapter().getItem(pos);
            Profil profil = (Profil) adapterView.getItemAtPosition(pos);
            System.out.println(profil);
            // invocam activitatea de vizualizare (editare - mai tarziu) profil
            startActivity(new Intent(this, ProfileActivity.class).putExtra("profile", profil));
        });

        listView.setOnItemLongClickListener(((adapterView, view, pos, id) -> {
            // confirmare de la utilizator!
            users.remove(pos);
            ((ArrayAdapter<Profil>)adapterView.getAdapter()).notifyDataSetChanged();
            // adapter.notifyDataSetChanged();
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
                    Profil profil = (Profil) data.getSerializableExtra("profile");
                    users.add(profil);
                    adapter.notifyDataSetChanged();
                    // Use the result data as needed
                }
            } else if (resultCode == RESULT_CANCELED) {
                // Handle the case when the user cancels the operation

            }
        }
    }

    public void addUser(View v) {
        // deschidem activitatea pentru preluare date
        startActivityForResult(new Intent(this, AddUserActivity.class), 1);
    }
}