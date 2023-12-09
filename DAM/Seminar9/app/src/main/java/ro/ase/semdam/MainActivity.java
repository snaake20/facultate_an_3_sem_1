package ro.ase.semdam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    public static final int REQUEST_CODE_ADD = 100;

    public static final int REQUEST_CODE_EDIT = 200;

    public static final String EDIT_MASINA = "editMasina";

    public int poz;

    List<Masina> listaMasini = new ArrayList<>();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        listView = findViewById(R.id.listViewMasini);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                Masina masina = listaMasini.get(position);
                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confimare stergere")
                        .setMessage("Sigur doriti stergerea?")
                        .setNegativeButton("NU", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Nu am sters nimic!",
                                        Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listaMasini.remove(masina);
                                adapter.notifyDataSetChanged();

                                MasiniDB database = MasiniDB.getInstanta(getApplicationContext());
                                database.getMasiniDao().delete(masina);

                                Toast.makeText(getApplicationContext(), "Am sters "+masina.toString(),
                                        Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).create();

                dialog.show();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                poz = position;
                intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra(EDIT_MASINA, listaMasini.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       /* switch (item.getItemId())
        {
            case R.id.optiune1:
                Intent intent1 = new Intent(this, BNRActivity.class);
                startActivity(intent1);

                return true;
        }*/

        if(item.getItemId() == R.id.optiune1)
        {
            Intent intent1 = new Intent(this, BNRActivity.class);
            startActivity(intent1);
            return true;
        }
        else
        if(item.getItemId() == R.id.optiune2)
        {
            ExtractXML extractXML = new ExtractXML()
            {
                @Override
                protected void onPostExecute(InputStream inputStream) {
                    listaMasini.addAll(this.masinaList);

                    MasiniDB database = MasiniDB.getInstanta(getApplicationContext());
                    database.getMasiniDao().insert(this.masinaList);

                    CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                            R.layout.elem_listview, listaMasini, getLayoutInflater())
                    {

                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            Masina masina1 = listaMasini.get(position);

                            TextView tvPret = view.findViewById(R.id.tvPret);
                            if(masina1.getPret()>=10000)
                                tvPret.setTextColor(Color.RED);
                            else
                                tvPret.setTextColor(Color.GREEN);

                            TextView tvMotorizare = view.findViewById(R.id.tvMotorizare);
                            if(masina1.getMotorizare().equals("ELECTRIC"))
                                tvMotorizare.setTextColor(Color.MAGENTA);

                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                }
            };
            try {
                extractXML.execute(new URL("https://pastebin.com/raw/K2gc0QSx"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            return true;
        }
        else
        if(item.getItemId() == R.id.optiune3)
        {
            ExtractJSON extractJSON = new ExtractJSON()
            {

                ProgressDialog progressDialog;

                @Override
                protected void onPreExecute() {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                }

                @Override
                protected void onPostExecute(String s) {
                    progressDialog.cancel();

                    listaMasini.addAll(this.masinaListJSON);

                    MasiniDB database = MasiniDB.getInstanta(getApplicationContext());
                    database.getMasiniDao().insert(this.masinaListJSON);

                    CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                            R.layout.elem_listview, listaMasini, getLayoutInflater())
                    {

                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            Masina masina1 = listaMasini.get(position);

                            TextView tvPret = view.findViewById(R.id.tvPret);
                            if(masina1.getPret()>=10000)
                                tvPret.setTextColor(Color.RED);
                            else
                                tvPret.setTextColor(Color.GREEN);

                            TextView tvMotorizare = view.findViewById(R.id.tvMotorizare);
                            if(masina1.getMotorizare().equals("ELECTRIC"))
                                tvMotorizare.setTextColor(Color.MAGENTA);

                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                }
            };
            try {
                extractJSON.execute(new URL("https://pastebin.com/raw/r9Yka6fJ"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        MasiniDB database = MasiniDB.getInstanta(getApplicationContext());
        listaMasini = database.getMasiniDao().getAll();

        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                R.layout.elem_listview, listaMasini, getLayoutInflater())
        {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Masina masina1 = listaMasini.get(position);

                TextView tvPret = view.findViewById(R.id.tvPret);
                if(masina1.getPret()>=10000)
                    tvPret.setTextColor(Color.RED);
                else
                    tvPret.setTextColor(Color.GREEN);

                TextView tvMotorizare = view.findViewById(R.id.tvMotorizare);
                if(masina1.getMotorizare().equals("ELECTRIC"))
                    tvMotorizare.setTextColor(Color.MAGENTA);

                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_ADD && resultCode==RESULT_OK && data!=null)
        {
            Masina masina = (Masina) data.getSerializableExtra(AddActivity.ADD_MASINA);
            if(masina!=null)
            {
                /*Toast.makeText(getApplicationContext(), masina.toString(), Toast.LENGTH_LONG).show();*/
                listaMasini.add(masina);
                /*ArrayAdapter<Masina> adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, listaMasini);
                listView.setAdapter(adapter);*/

                MasiniDB database = MasiniDB.getInstanta(getApplicationContext());
                database.getMasiniDao().insert(masina);

                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                        R.layout.elem_listview, listaMasini, getLayoutInflater())
                {

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        Masina masina1 = listaMasini.get(position);

                        TextView tvPret = view.findViewById(R.id.tvPret);
                        if(masina1.getPret()>=10000)
                            tvPret.setTextColor(Color.RED);
                        else
                            tvPret.setTextColor(Color.GREEN);

                        TextView tvMotorizare = view.findViewById(R.id.tvMotorizare);
                        if(masina1.getMotorizare().equals("ELECTRIC"))
                            tvMotorizare.setTextColor(Color.MAGENTA);

                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }
        }
        else
        if(requestCode==REQUEST_CODE_EDIT && resultCode==RESULT_OK && data!=null)
        {
            Masina masina = (Masina) data.getSerializableExtra(AddActivity.ADD_MASINA);
            if(masina!=null)
            {
                listaMasini.get(poz).setMarca(masina.getMarca());
                listaMasini.get(poz).setDataFabricatiei(masina.getDataFabricatiei());
                listaMasini.get(poz).setPret(masina.getPret());
                listaMasini.get(poz).setCuloare(masina.getCuloare());
                listaMasini.get(poz).setMotorizare(masina.getMotorizare());

                MasiniDB database = MasiniDB.getInstanta(getApplicationContext());
                database.getMasiniDao().update(listaMasini.get(poz));
               /* ArrayAdapter<Masina> adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, listaMasini);
                listView.setAdapter(adapter);*/

                CustomAdapter adapter = (CustomAdapter) listView.getAdapter();
                adapter.notifyDataSetChanged();
            }
        }
    }
}