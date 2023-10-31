package com.example.seminar2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

public class DespreAplicatieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despre_aplicatie);
        Toolbar toolbar = findViewById(R.id.despreToolbar);
        toolbar.setTitle("About");
        setSupportActionBar(toolbar);
        //TEMA2 ADAUGATI ACTIVTATEA DespreActivity la proiect
        //invocati activitatea DespreActivity
        //personalizati interfata activitatii DespreActivity
        //LinearLayout (vertical), TextView, ImageView, TextView
        //Titlul aplicatiei
        //Logo (Drawable)
        //Autoru si anul
    }

    public void onClick(View v) {
        finish();
    }
}