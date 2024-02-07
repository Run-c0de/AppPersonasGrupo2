package com.uth.grupo2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.uth.grupo2.Models.Personas;

public class DbPersonas extends DbHelper {

    Context context;

    public DbPersonas(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPersona(String nombres, String apellidos, String edad, String correo, String direccion) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombres", nombres);
            values.put("apellidos", apellidos);
            values.put("edad", edad);
            values.put("correo", correo);
            values.put("direccion", direccion);

            id = db.insert(TABLE_PERSONAS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Personas> mostrarPersonas() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Personas> listaPersonas = new ArrayList<>();
        Personas persona;
        Cursor cursorPersonas;

        cursorPersonas = db.rawQuery("SELECT * FROM " + TABLE_PERSONAS + " ORDER BY nombres ASC", null);

        if (cursorPersonas.moveToFirst()) {
            do {
                persona = new Personas();
                persona.setId(cursorPersonas.getInt(0));
                persona.setNombres(cursorPersonas.getString(1));
                persona.setApellidos(cursorPersonas.getString(2));
                persona.setEdad(cursorPersonas.getInt(3));
                persona.setCorreo(cursorPersonas.getString(4));
                persona.setDireccion(cursorPersonas.getString(5));
                listaPersonas.add(persona);
            } while (cursorPersonas.moveToNext());
        }

        cursorPersonas.close();

        return listaPersonas;
    }

    public Personas verPersona(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Personas persona = null;
        Cursor cursorPersonas;

        cursorPersonas = db.rawQuery("SELECT * FROM " + TABLE_PERSONAS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorPersonas.moveToFirst()) {
            persona = new Personas();
            persona.setId(cursorPersonas.getInt(0));
            persona.setNombres(cursorPersonas.getString(1));
            persona.setApellidos(cursorPersonas.getString(2));
            persona.setEdad(cursorPersonas.getInt(3));
            persona.setCorreo(cursorPersonas.getString(4));
            persona.setDireccion(cursorPersonas.getString(5));
        }

        cursorPersonas.close();

        return persona;
    }

    public boolean editarPersona(int id, String nombres, String apellidos, String edad, String correo, String direccion) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PERSONAS + " SET nombres = '" + nombres + "', apellidos = '" + apellidos + "',  edad = '" + edad + "', correo = '" + correo + "', direccion = '" + direccion + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarPersona(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_PERSONAS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
