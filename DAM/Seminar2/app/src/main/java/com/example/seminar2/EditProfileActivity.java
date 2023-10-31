package com.example.seminar2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {
    Profil profil;
    EditText nume, varsta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = findViewById(R.id.toolbarEdit);
        toolbar.setTitle("Editeaza Profilul");
        setSupportActionBar(toolbar);
        nume = findViewById(R.id.editNume);
        varsta = findViewById(R.id.editVarsta);
        profil = (Profil) getIntent().getSerializableExtra("profile");
        initControls(profil);
    }

    void initControls(Profil profil) {
        nume.setText(profil.getNume());
        varsta.setText(String.valueOf(profil.getVarsta()));
    }

    public void editProfile(View v) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("profile", new Profil(profil.getEmail(), String.valueOf(nume.getText()), Integer.parseInt(String.valueOf(varsta.getText()))));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}