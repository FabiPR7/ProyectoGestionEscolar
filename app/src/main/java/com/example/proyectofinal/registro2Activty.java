package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Modelo.SQLmyDataBase;

public class registro2Activty extends AppCompatActivity {
    EditText nombre, apellido, telefono, direccion;
    Button b;
    SQLmyDataBase admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro2_activty);
        nombre = (EditText) findViewById(R.id.nombreTxt);
        apellido = (EditText) findViewById(R.id.apellidotxt);
        telefono = (EditText) findViewById(R.id.telefonotxt);
        direccion = (EditText) findViewById(R.id.direccionTxt);
        b = (Button) findViewById(R.id.button2);
        //SI CLICA A EST BOTON LLAMO A LA FUNCION ACEPTAR
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aceptar();
            }
        });
        admin = new SQLmyDataBase(this,"GestSchoolBD",null,1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
/*
ESTE METODO ES EL QUE AÑADE LOS DATOS A LA TABLA DE USUARIOS PARA ASI TENER
REGISTRADO EL USUARIO
 */
    public void aceptar(){
        //AQUI RECIBO LOS DATOS MANDADOS DE MI ANTERIOR ACTIVTIY
        Bundle recibios = getIntent().getExtras();
        SQLiteDatabase db = admin.getWritableDatabase();
          ContentValues datos = new ContentValues();
          //LOS AGREGO A MI CONTENTVALUES PARA INSERTAR POSTERIORMENTE
          datos.put("ID",recibios.getInt("ID"));
          datos.put("NOMBRE",nombre.getText().toString());
          datos.put("APELLIDO",apellido.getText().toString());
         datos.put("TELEFONO",telefono.getText().toString());
         datos.put("DIRECCION",direccion.getText().toString());
         datos.put("PWD",recibios.getString("PWD"));
         datos.put("CORREO",recibios.getString("CORREO"));
         //INSERTO TODOS LOS DATOS A LA TABLA USUARIO
         db.insert("USUARIO",null,datos);
         Intent inte = new Intent(this, MainActivity.class);
         inte.putExtra("nombre",nombre.getText().toString());
         //VOY AL MENU PRINCPAL (ACTIVITY)
         startActivity(inte);
    }
}