package com.example.seminar5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class AddUserActivity extends AppCompatActivity {
    EditText nume, varsta, email;
    CheckBox checkBox;

    Spinner spinner;

    ArrayAdapter<Gen> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = findViewById(R.id.toolbarEdit);
        toolbar.setTitle("Adauga un user");
        setSupportActionBar(toolbar);
        nume = findViewById(R.id.editNume);
        varsta = findViewById(R.id.editVarsta);
        email = findViewById(R.id.editEmail);
        checkBox = findViewById(R.id.checkBox);
        spinner = findViewById(R.id.spinner);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Gen.values());
        spinner.setAdapter(adapter);
    }

    public void addProfile(View v) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("profile", new Profil(String.valueOf(email.getText()), String.valueOf(nume.getText()), Integer.parseInt(String.valueOf(varsta.getText())), checkBox.isChecked(), (Gen) spinner.getSelectedItem()));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}