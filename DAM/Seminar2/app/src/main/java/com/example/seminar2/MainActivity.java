package com.example.seminar2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Profil profil;
    TextView nume, varsta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Main Screen");
        setSupportActionBar(toolbar);
        nume = findViewById(R.id.nume);
        varsta = findViewById(R.id.varsta);
        profil = new Profil("john.doe@gmail.com", "john", 40);
        initControls(profil);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) { // Check if the result is for our request code
            if (resultCode == RESULT_OK) {
                // Handle a successful result here
                if (data != null) {
                    profil = (Profil) data.getSerializableExtra("profile");
                    initControls(profil);
                    // Use the result data as needed
                }
            } else if (resultCode == RESULT_CANCELED) {
                // Handle the case when the user cancels the operation

            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //TEMA
        // salvam profilul curent in Bundle( adica in outState ) adica cand rotim ecranu sa nu se modifice nimic
        outState.putSerializable("profil", profil);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //restauram profilul curent din savedInstanceState
        //initializam controale cu valorile noi
        profil = (Profil) savedInstanceState.getSerializable("profil");
        initControls(profil);
    }

    public void onClick(View v) {
        startActivityForResult(new Intent(this, EditProfileActivity.class).putExtra("profile", profil), 1);
    }

    void initControls(Profil profil) {
        nume.setText(profil.getNume());
        varsta.setText(String.valueOf(profil.getVarsta()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_despre)
        {
            startActivity(new Intent(this, DespreAplicatieActivity.class));
        }
        return true;
    }
}