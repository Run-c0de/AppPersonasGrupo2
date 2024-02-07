package com.uth.grupo2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.uth.grupo2.R;
import com.uth.grupo2.adaptadores.ListaPersonasAdapter;
import com.uth.grupo2.db.DbPersonas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com.uth.grupo2.Models.Personas;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaPersonas;
    ArrayList<Personas> listaArrayPersonas;
    FloatingActionButton fabNuevo;
    FloatingActionButton fabAbout;
    ListaPersonasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        txtBuscar = findViewById(R.id.txtBuscar);
        listaPersonas = findViewById(R.id.listaPersonas);
        fabNuevo = findViewById(R.id.favNuevo);
        fabAbout = findViewById(R.id.favAbout);
        listaPersonas.setLayoutManager(new LinearLayoutManager(this));

        DbPersonas dbPersonas = new DbPersonas(MainActivity.this);

        listaArrayPersonas = new ArrayList<>();

        adapter = new ListaPersonasAdapter(dbPersonas.mostrarPersonas());
        listaPersonas.setAdapter(adapter);

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        fabAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAboutDialog(MainActivity.this);
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }

    public  static  void showAboutDialog(Activity activity) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.dialog_about, null);
        TextView txtAppVersion = view.findViewById(R.id.txt_app_version);
        txtAppVersion.setText(activity.getString(R.string.msg_about_version) + " " + 1 + " (" + 1.0 + ")");
        final MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(activity);
        alert.setView(view);
        alert.setPositiveButton(R.string.dialog_option_ok, (dialog, which) -> dialog.dismiss());
        alert.show();
    }


    private void nuevoRegistro(){
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}