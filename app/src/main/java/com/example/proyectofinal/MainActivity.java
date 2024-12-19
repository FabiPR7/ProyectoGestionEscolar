package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Modelo.SQLmyDataBase;

public class MainActivity extends AppCompatActivity {
    ImageView curso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //RECIBIMOS EL NOMBRE DEL ANTERIOR ACTIVITY
        Bundle recibidos = getIntent().getExtras();
        //DAMOS BIENVENIDA
        Toast.makeText(this, "BIENVENIDO "+recibidos.getString("nombre").toUpperCase(), Toast.LENGTH_SHORT).show();
        curso = (ImageView) findViewById(R.id.imageView7);
        curso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               cursoAct();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



/*
TODOS ESTOS METODOS NOS LLEVAN A LAS ACTIVIDADES SEGUN QUE SE PRESIONE
 */
    public void cursoAct(){
        Intent i = new Intent(this, cursoActibit.class);
        startActivity(i);
    }
    public void asistenciaAct(View v){
        Intent i = new Intent(this, AsistenciasActivity.class);
        startActivity(i);
    }
    public void perfilAct(View v){
        Intent i = new Intent(this, PerfilActivity.class);
        startActivity(i);
    }
    public void notiAct(View v){
        Intent i = new Intent(this, NotiActivity.class);
        startActivity(i);
    }
    public void ajusAct(View v){
        Intent i = new Intent(this, AjustesActivity.class);
        startActivity(i);
    }
    public void calenAct(View v){
        Intent i = new Intent(this, CalendarActivity.class);
        startActivity(i);
    }
}