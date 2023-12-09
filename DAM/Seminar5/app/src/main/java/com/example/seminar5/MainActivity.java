package com.example.seminar5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.seminar5.data.AppDatabase;
import com.example.seminar5.data.Gen;
import com.example.seminar5.data.Profil;
import com.example.seminar5.data.ProfilDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    int sortDirection = 1;
    ListView listView;
//    List<Profil> usersUnsorted = new ArrayList<>();
    List<Profil> users = new ArrayList<Profil>();
    ProfilAdapter adapter;

//    Button btn_sort;

    Executor executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.myLooper());

    SharedPreferences pref;

    private void initUsers(List<Profil> list) {
        list.clear();
        executor.execute(() -> {
            List<Profil> profile = AppDatabase.getInstance(getApplicationContext()).getProfilDao().getAll();
            handler.post(() -> {
                list.addAll(profile);
            });
        });


//        list.add(new Profil("email1@example.com", "alex", 1, true));
//        list.add(new Profil("email2@example.com", "larisa", 2, false));
//        list.add(new Profil("email3@example.com", "andrei", 3, true));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getPreferences(MODE_PRIVATE);
        String lastAccessTime = pref.getString("LastAccessTime", "No data");

        // Display or use the last access time
        System.out.println("Last Access Time: " + lastAccessTime);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Users");
        setSupportActionBar(toolbar);

//        initUsers(usersUnsorted);
        initUsers(users);

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
            executor.execute(() -> {
                AppDatabase.getInstance(getApplicationContext()).getProfilDao().delete(users.get(pos));
                handler.post(() -> {
                    users.remove(pos);
                    ((ArrayAdapter<Profil>)adapterView.getAdapter()).notifyDataSetChanged();
                });
            });
            // adapter.notifyDataSetChanged();
            return true;
        }));

        boolean prima_rulare =  pref.getBoolean("prima_rulare", false);
        if (!prima_rulare) {
            SharedPreferences.Editor prefEditor = pref.edit();
            prefEditor.putBoolean("prima_rulare", true);
            prefEditor.apply();
        }

//        btn_sort = findViewById(R.id.btn_sortare);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Profil profil = (Profil) data.getSerializableExtra("profile");
                    users.add(profil);
                    adapter.notifyDataSetChanged();
//                    usersUnsorted.add(profil);
                    executor.execute(() -> {
                        AppDatabase.getInstance(getApplicationContext()).getProfilDao().insert(profil);
                    });
                }
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    public void addUser(View v) {
        // deschidem activitatea pentru preluare date
        startActivityForResult(new Intent(this, AddUserActivity.class), 1);
    }

//    public void reset(View v) {
//        users.clear();
//        users.addAll(usersUnsorted);
//        adapter.notifyDataSetChanged();
//    }

//    public void sort(View v) {
//        String sorting = String.valueOf(btn_sort.getText());
//        users.sort(Comparator.comparing(Profil::getNume));
//        if (sorting.equals("Crescator")) {
//            btn_sort.setText("Descrescator");
//        } else {
//            Collections.reverse(users);
//            btn_sort.setText("Crescator");
//        }
//        adapter.notifyDataSetChanged();
//    }

    @Override
    protected void onPause() {
        super.onPause();

        // Get current time
        String currentTime = new Date().toString();

        // Save the current time as last access time
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("LastAccessTime", currentTime);
        editor.apply();
    }
}