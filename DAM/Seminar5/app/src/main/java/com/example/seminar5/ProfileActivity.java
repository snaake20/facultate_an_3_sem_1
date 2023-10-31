package com.example.seminar5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    EditText nume, varsta, email;
    CheckBox checkBox;

    Spinner spinner;

    Profil profil;

    ArrayAdapter<Gen> adapter;

    public void back(View v) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profil = (Profil) getIntent().getSerializableExtra("profile");
        System.out.println(profil);

        Toolbar toolbar = findViewById(R.id.toolbarProfile);
        toolbar.setTitle("User: " + profil.getNume());
        setSupportActionBar(toolbar);

        nume = findViewById(R.id.editNume);
        varsta = findViewById(R.id.editVarsta);
        email = findViewById(R.id.editEmail);
        checkBox = findViewById(R.id.checkBox);
        spinner = findViewById(R.id.spinner);

        initControls();

    }

    void initControls() {
        nume.setText(profil.getNume());
        nume.setEnabled(false);
        varsta.setText(String.valueOf(profil.getVarsta()));
        varsta.setEnabled(false);
        email.setText(profil.getEmail());
        email.setEnabled(false);
        checkBox.setChecked(profil.iseMajor());
        checkBox.setEnabled(false);
        ArrayList<Gen> list = new ArrayList<Gen>();
        list.add(profil.getGen());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        spinner.setAdapter(adapter);
        spinner.setEnabled(false);
    }
}