package ro.ase.semdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    public static final String ADD_MASINA = "addMasina";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();

        String[] culori = {"RED", "BLACK", "GREEN", "YELLOW", "WHITE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                culori);
        Spinner spinnerCulori = findViewById(R.id.spinnerCuloare);
        spinnerCulori.setAdapter(adapter);

        EditText etMarca = findViewById(R.id.editTextMarca);
        EditText etDataFabricatiei = findViewById(R.id.editTextDate);
        EditText etPret = findViewById(R.id.editTextPret);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        if(intent.hasExtra(MainActivity.EDIT_MASINA))
        {
            Masina masina = (Masina) intent.getSerializableExtra(MainActivity.EDIT_MASINA);
            etMarca.setText(masina.getMarca());
            etDataFabricatiei.setText(new SimpleDateFormat("MM/dd/yyyy", Locale.US).
                    format(masina.getDataFabricatiei()));
            //etPret.setText(String.valueOf(masina.getPret()));
            etPret.setText(masina.getPret()+"");
            ArrayAdapter<String> adapter1 = (ArrayAdapter<String>) spinnerCulori.getAdapter();
            for(int i=0;i<adapter1.getCount();i++)
                if(adapter1.getItem(i).equals(masina.getCuloare()))
                {
                    spinnerCulori.setSelection(i);
                    break;
                }
            if(masina.getMotorizare().equals("GASOLINE"))
                radioGroup.check(R.id.radioButton1);
            else
            if(masina.getMotorizare().equals("HYBRID"))
                radioGroup.check(R.id.radioButton2);
            else
            if(masina.getMotorizare().equals("ELECTRIC"))
                radioGroup.check(R.id.radioButton3);
        }

        Button btnAdauga = findViewById(R.id.btnAdauga);
        if(intent.hasExtra(MainActivity.EDIT_MASINA))
            btnAdauga.setText("Editare masina");

        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(getApplicationContext(),
                        "Masina creata!", Toast.LENGTH_LONG).show();*/
                if(etMarca.getText().toString().isEmpty())
                    etMarca.setError("Introduceti marca!");
                else
                    if(etDataFabricatiei.getText().toString().isEmpty())
                        etDataFabricatiei.setError("Introduceti data!");
                    else
                        if(etPret.getText().toString().isEmpty())
                            etPret.setError("Introduceti pretul!");
                        else {

                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                            try {
                                sdf.parse(etDataFabricatiei.getText().toString());
                                Date dataFabracatiei = new Date(etDataFabricatiei.getText().toString());
                                String marca = etMarca.getText().toString();
                                float pret  = Float.parseFloat(etPret.getText().toString());
                                String culoare = spinnerCulori.getSelectedItem().toString();
                                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                                String motorizare = radioButton.getText().toString();

                                Masina masina = new Masina(marca, dataFabracatiei, pret,
                                        culoare, motorizare);
                                /*Toast.makeText(getApplicationContext(), masina.toString(), Toast.LENGTH_LONG).show();*/
                                intent.putExtra(ADD_MASINA, masina);
                                setResult(RESULT_OK, intent);
                                finish();

                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            catch (Exception ex)
                            {
                                Log.e("AddActivity", "Erori introducere date!");
                                Toast.makeText(getApplicationContext(), "Erori introducere date!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
            }
        });
    }
}