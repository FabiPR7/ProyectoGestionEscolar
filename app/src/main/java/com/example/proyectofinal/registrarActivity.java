package com.example.proyectofinal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;
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

public class registrarActivity extends AppCompatActivity {
EditText correo, pwd1, pwd2, codigo;
Button b;
SQLmyDataBase admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);
        // DAMOS VALOR A TODOS NUESTROS DATOS
        correo = (EditText) findViewById(R.id.correotxt);
        pwd1 = (EditText) findViewById(R.id.pwd1txt);
        pwd2 = (EditText) findViewById(R.id.pwd2txt);
        codigo = (EditText) findViewById(R.id.codigotxt);
        b = (Button) findViewById(R.id.button);
        //CREAMOS LA BASE DE DATOS QUE SE LLAMA GESTSCHOOLBD
        admin = new SQLmyDataBase(this, "GestSchoolBD",null,1);


    //SI ESTA REGISTRADO PASAMOS DIRECTAMENTE A LAS PAGINAS IMPORTNATES
        if (admin.estaRegistrado()){
            Intent i = new Intent(this, MainActivity.class);
            //LAMAMOS A LA ACTIVIDAD PRINCIPAL SALUDANDO POR EL NOMBRE DE USUARIO
            i.putExtra("nombre",admin.obtenerNombreUsuario());
            startActivity(i);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
/*
AQUI ES SI PRESIONA EL BOTON MANDAREMOS TODOS LOS DATOS AL SIGUIENTE ACTIVITY
PARA SU REGISTRO
 */
  public void presionar(View v){
        if (pwd1.getText().toString().equalsIgnoreCase(pwd2.getText().toString())){
                Intent i = new Intent(this, registro2Activty.class);
                //MANDAREMOS TODOS LOS DATOS RECIBIDOS
                i.putExtra("ID",1);
                i.putExtra("CORREO",correo.getText().toString());
                i.putExtra("PWD",pwd1.getText().toString());
                startActivity(i);
        }
        else{
            Toast.makeText(this, "lAS CONTRASEÑAS NO COINCIDEN", Toast.LENGTH_SHORT).show();
        }
    }

}