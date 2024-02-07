package com.uth.grupo2.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uth.grupo2.R;
import com.uth.grupo2.db.DbPersonas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.uth.grupo2.Models.Personas;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtEdad, txtCorreo, txtDireccion;
    Button btnGuardar;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;
    Personas persona;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Actualizar Registro");
        }

        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtDireccion = findViewById(R.id.txtDireccion);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setText(R.string.btn_editar);

        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbPersonas dbPersonas = new DbPersonas(EditarActivity.this);
        persona = dbPersonas.verPersona(id);

        if (persona != null) {
            txtNombres.setText(persona.getNombres());
            txtApellidos.setText(persona.getApellidos());
            txtEdad.setText(persona.getEdad().toString());
            txtCorreo.setText(persona.getCorreo());
            txtDireccion.setText(persona.getDireccion());
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombres.getText().toString().equals("") && !txtEdad.getText().toString().equals("")) {
                    correcto = dbPersonas.editarPersona(id, txtNombres.getText().toString(), txtApellidos.getText().toString(), txtEdad.getText().toString(), txtCorreo.getText().toString(), txtDireccion.getText().toString());

                    if(correcto){
                        Toast.makeText(EditarActivity.this, "Registro de "+txtNombres.getText().toString()+" Actualizado con exito", Toast.LENGTH_LONG).show();
                        lista();
                    } else {
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}