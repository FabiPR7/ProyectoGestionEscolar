package com.example.proyectofinal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import Modelo.SQLmyDataBase;

public class CursoSeleccionadoActivity extends AppCompatActivity {
    TextView tv; Button tv2;
    EditText ed1;
    TabLayout tab;
    ImageView iv;
    SQLmyDataBase admin;
    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_curso_seleccionado);
            //GESTIONO TODOS LOS OBJETOS
            tv = findViewById(R.id.textoSeleccionado);
            tv2 = findViewById(R.id.agregarCursoSeleccionado);
            tab = findViewById(R.id.mitab);
            //INSTANCIO MI BASE DE DATOS
            admin = new SQLmyDataBase(this,"GestSchoolBD",null,1);
            ed1 = findViewById(R.id.nombreCursoSeleccionado);
            iv = findViewById(R.id.atrasCursoSeleccionado);
           //CREO EL BUNDLE PARA RECIBIR EL NOMBRE DEL CURSO QUE ME MANDÓ
        //EL ACTIVTY ANTERIOR
            Bundle datos = getIntent().getExtras();
            tv.setText(datos.getString("nombrec"));
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //SI LE DAMOS A VOLVER TEMINARA EL ACTIVTY
                    finish();
                }
            });
            //LE DOY VALOR A MI FRAME DE FRAGMENT
            getSupportFragmentManager().beginTransaction().add(R.id.frame1,new FragmentTemas(tv.getText().toString())).commit();
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //SI QUIERO CREAR ALGO PERO ESTA VACIO NO ME DEJARA
                    if (ed1.getText().toString().isEmpty() || ed1.getText().toString()==""){
                        Toast.makeText(CursoSeleccionadoActivity.this, "ERROR : NOMBRE VACIO", Toast.LENGTH_SHORT).show();
                    }
                    //SI NO ESTA VACIO GESTIONAMOS EN QUE TABPAGE ESTOY
                    else{
                        //SI ESTOY EN LA PRIMERA(TEMAS)
                        if (n == 0){
                            SQLiteDatabase db = admin.getWritableDatabase();
                            ContentValues valores = new ContentValues();
                            valores.put("NOMBRE",ed1.getText().toString());
                            valores.put("CURSO",tv.getText().toString());
                            //INSERTARE EL NOMBRE Y EL CURSO EN LA TABLA TEMAS
                            db.insert("TEMAS",null,valores);
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new FragmentTemas(tv.getText().toString())).commit();
                        }//SI ESTOY EN LA TABPAGE 2 (ALUMNOS)
                        if (n == 1){
                            //INSTANCIO LA BASE DATOS
                            SQLiteDatabase db = admin.getWritableDatabase();
                            //CREO MIO CONTENEDOR DE VALORES
                            ContentValues valores = new ContentValues();
                            valores.put("NOMBRE",ed1.getText().toString());
                            valores.put("CURSO",tv.getText().toString());
                            //LOS INSERTO A LA TABLA ALUMNOS
                            db.insert("ALUMNO",null,valores);
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new FragmentAlumnos(tv.getText().toString())).commit();
                        }
                    }
                }

            });
            //AL CAMBIAR DE TABPAGE
         tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //GESTIONAREMOS PARA QUE AL CAMBIARSE DE PAGINAS SE MUESTRE
                //EL FRAGMENT CORRECTO
               if (tab.getPosition()==0){
                   //SI ESTOY EN EL PRIMERO MANDO AL TEMAS
                      getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new FragmentTemas(tv.getText().toString())).commit();
                     tv2.setText("Agregar tema");
                     //CAMBIO EL TEXTO AL BOTON
                     n = 0;
                     }
                  if (tab.getPosition()==1){
                      //SI ESTOY EN EL SEGUNDO LLAMO AL SEGUNDO FRAGMENT(ALUMNOS)
                      getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new FragmentAlumnos(tv.getText().toString())).commit();
                      tv2.setText("Agregar alumno");
                        n = 1;
                  }
              }

                      @Override
              public void onTabUnselected(TabLayout.Tab tab) {

                  }

                          @Override
            public void onTabReselected(TabLayout.Tab tab) {

             }
          });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}