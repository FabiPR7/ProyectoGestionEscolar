package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Modelo.SQLmyDataBase;

public class PerfilActivity extends AppCompatActivity {
    ImageView iv;
    TextView nom,ape,tel,dir,ide,correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        nom = findViewById(R.id.nombrePerfil);
        ape= findViewById(R.id.apellidoPerfil);
        dir = findViewById(R.id.direccionPerfil);
        correo = findViewById(R.id.correoPerfil);
        ide = findViewById(R.id.idperfil);
        tel = findViewById(R.id.telefonoPerfil);
        iv = findViewById(R.id.atrasPerfil);
        cambiarTexto();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }/*
    LA CLASE QUE IMPORTA ES ESTA QUE CAMBIA EL VALOR DE LOS TEXTOS A
    LOS DATOS DEL USUARIO
    */
    @SuppressLint("Range")
    public void cambiarTexto(){
        //CREO UN CURSOR CON LA BASE DE DATOS
       SQLmyDataBase admin = new SQLmyDataBase(this,"GestSchoolBD", null,1);
        //OBTENGO LOS DATOS DEL USUARIO
       Cursor c = admin.obtenerdatosUsario();
       if (c.moveToFirst()){
           //LOS INTRODUZCO
           nom.setText(c.getString(c.getColumnIndex("NOMBRE")));
           correo.setText(c.getString(c.getColumnIndex("CORREO")));
           ape.setText(c.getString(c.getColumnIndex("APELLIDO")));
           dir.setText(c.getString(c.getColumnIndex("DIRECCION")));
           ide.setText(c.getString(c.getColumnIndex("ID")));
           tel.setText(c.getString(c.getColumnIndex("TELEFONO")));
       }
    }
}