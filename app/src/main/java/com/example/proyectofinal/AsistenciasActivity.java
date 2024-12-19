package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Modelo.SQLmyDataBase;

public class AsistenciasActivity extends AppCompatActivity {
    Spinner sp;
    ArrayList<String> nombresCusos;
    ArrayList<String> alumnos;
    ArrayAdapter<String> adpatador;
    RecyclerView rv;
    ImageView iv1;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_asistencias);
        nombresCusos = new ArrayList<String>();
        alumnos = new ArrayList<String>();
        //OBTENGO TODOS LOS CURSOS PARA ALMACENARLOS EN
        //NOMBRES CURSOS
        obtenerCursos();
        //CREO EL SPINNER CON LOS NOMBRES DE LOS CURSOS
        adpatador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,nombresCusos);
        sp  = findViewById(R.id.spinnerCursos);
        sp.setAdapter(adpatador);
        b = findViewById(R.id.aceptAsisButt);
        rv = findViewById(R.id.recAsistencias);
        iv1 = findViewById(R.id.atrasAsistenciaButton);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //AL CLICAR ACEPTAR
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            //LLAMAMOS AL METODO ONCLICK
            public void onClick(View view) {
                //OBTENEMOS TODOS LOS ALUMNOS
                obtenerAlumnos();
                //SI NO HAY ALUMNOS MOSTRAMOS MENSAJE Y YA
                if (alumnos.isEmpty()){
                    Toast.makeText(AsistenciasActivity.this, "Este curso no tiene alumnos", Toast.LENGTH_SHORT).show();
                }
                //GESTIONO MI ADAPTER
                    rv.setLayoutManager(linearLayoutManager);
                    rv.setAdapter(new miAdapter());
            }
        });// SI LE DA A LA IMAGEN DE VOLVER SE TERMINARA LA ACTIVITY
        iv1.setOnClickListener(new View.OnClickListener() {
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
    }
/*
OBTENGO TODOS LOS CURSOS
 */
    @SuppressLint("Range")
    public void obtenerCursos(){
        //INSTANCIO LA BASE DE DATOS
        SQLmyDataBase admin = new SQLmyDataBase(this,"GestSchoolBD", null, 1);
       Cursor c = admin.obtenerCursos();
       if (c.moveToFirst()){
           do {//AGREGO TODOS LOS NOMBRES A MI ARRAYLIST DE NOMBRESCURSOS
               nombresCusos.add(c.getString(c.getColumnIndex("NOMBRE")));
           }while(c.moveToNext());
       }
    }
    /*
    ES LO MISMO DE OBTENER CURSOS PERO CON ALUMOS
     */
    @SuppressLint("Range")
    public void obtenerAlumnos(){
        SQLmyDataBase admin = new SQLmyDataBase(this,"GestSchoolBD", null, 1);
            //OBTENGO LOS ALUMNOS DEL CURSO MANDADO
        Cursor c = admin.obtenerAlumnos(sp.getSelectedItem().toString());
        alumnos = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                //LOS AGREGO A MI LISTA DE ALUMNOS A MOSTRAR
                alumnos.add(c.getString(c.getColumnIndex("NOMBRE")));
            }while(c.moveToNext());
        }
    }

        //Mi adapter de alumnos
    private class miAdapter extends RecyclerView.Adapter<miAdapter.miAdapterHolder> {

        @NonNull
        @Override
        public miAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new miAdapterHolder(getLayoutInflater().inflate(R.layout.asistencialayout,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull miAdapterHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return alumnos.size();
        }

        private class miAdapterHolder extends RecyclerView.ViewHolder{
            TextView t1;
            ImageView iv;
            RadioButton r1,r2,r3;

            //relaciono todos los objetos
            public miAdapterHolder(@NonNull View itemView) {
                super(itemView);
                t1 = itemView.findViewById(R.id.textoAsistenciaLayout);
                iv = itemView.findViewById(R.id.imagenAsistenciaLayout);
                r1 = itemView.findViewById(R.id.radb1);
                r2 = itemView.findViewById(R.id.radb2);
                r3 = itemView.findViewById(R.id.radb3);
                r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    //gestionamos cuando cliquean
                    //si marca uno se desmarcan los otros
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        r2.setChecked(false);
                        r3.setChecked(false);
                    }
                });
                r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        r1.setChecked(false);
                        r3.setChecked(false);
                    }
                });
                r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        r1.setChecked(false);
                        r2.setChecked(false);
                    }
                });
                }

            /**
             *Imprimmos los datos recibidos con la imagen puesta
             * @param position
             */
            public void imprimir(int position) {
               t1.setText(alumnos.get(position));
               iv.setImageResource(R.drawable.alumnoasistencia);

            }
        }
    }
}