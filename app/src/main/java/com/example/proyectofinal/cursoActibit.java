package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Modelo.SQLmyDataBase;

public class cursoActibit extends AppCompatActivity {
   private RecyclerView rv;
    private TextView tv;
    ImageView iv;
    private Cursor cursor;
    private ImageView menuImg;
    private ArrayList<String> nombres = new ArrayList<>();
    private ArrayList<String> descripcion = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_curso_actibit);
        tv = (TextView) findViewById(R.id.agregarC);
        rv = (RecyclerView) findViewById(R.id.rec);
        obtenerCursosAdapter();
        menuImg = findViewById(R.id.menuImageView);
        iv = findViewById(R.id.atrasCurso);
        // ESTA IMAGEN ES SOLO PARA TERMINAR LA ACTIVITY Y VOLVER ATRAS
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //SI SE PULSA EL TEXTO DE AGREGAR TE MANDARA A OTRO ACTIVITY
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                agregarCursoActivity();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    //ESTE INICIA EL ACTIVRTY CON ESPERA DE RESPUESTA AL AGREGAR EL CURSO
    public void agregarCursoActivity(){
        Intent i = new Intent(this, agregarCursoActivity.class);
        startActivityForResult(i,100);
    }
//ESTO ES SOLO PARA INICIAR MI ADAPTER Y RECYCLERVIEW
    public void corrrerCursos(){
        LinearLayoutManager li = new LinearLayoutManager(this);
        rv.setLayoutManager(li);
        rv.setAdapter(new miAdapter());
    }
    /*
    AQUI ENTRAMOS A LA ACTIVIDAD DEL CURSO SELECCIONADO
     MANDADO EL NOMBRE DEL CURSOSELECCIONADO
     */
    public void cursoSeleccionad(String nombreCurso){
        Intent i = new Intent(this, CursoSeleccionadoActivity.class);
        i.putExtra("nombrec",nombreCurso);
        startActivity(i);
    }

/*
EN LA ACTIVIDAD DE AGREGAR CURSO QUE ESPERAMOS RESPUESTA, ESTA ES LA RESPUESTA
AQUI RECIBIMOS LOS DATOS Y LOS ALMACENAMOS EN LA BASE DE DATOS DE CURSO
 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //INSTANCIO MI BASE DE DATOS
        SQLmyDataBase admin = new SQLmyDataBase(this,"GestSchoolBD", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        //VERIFICAMOS QUE ES LA RESPUESTA DEL CODIGO QUE ESPERAMOS
        if (requestCode == 100){
            if (resultCode == RESULT_OK ){
                Bundle datos = data.getExtras();
                //ME ASEGURO QYE NO ESTE VACIO EL BUNDLE
                if (datos != null){
                ContentValues valores = new ContentValues();
                //AGREGO LOS DATOS RECIBIDOS
                valores.put("CODMATRICULA", datos.getString("COD"));
                valores.put("NOMBRE",datos.getString("NOMBRE"));
                valores.put("DESCRIPCION",datos.getString("DESCRIPCION"));
                db.insert("CURSO",null,valores);
                //AGREGAMOS EL CURSO AL RECYCLERVIEW
                obtenerCursosAdapter();
                }
            }
        }
    }
    /*
        AQUI OBTENEMOS TODOS LOS CURSOS DE LA BASE DE DATOS PARA
        SER MOSTRADOS EN EL RECYCLERVIEW
     */
    @SuppressLint("Range")
    public void obtenerCursosAdapter(){
        cursor = new SQLmyDataBase(this,"GestSchoolBD", null,1).obtenerCursos();
        nombres = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                    nombres.add(cursor.getString(cursor.getColumnIndex("NOMBRE") ));
                    descripcion.add(cursor.getString(cursor.getColumnIndex("DESCRIPCION") ));

            } while (cursor.moveToNext());
        }
        corrrerCursos();
    }
/*
SIMPLEMENTE SOLO ES EL ADAPTADOR DE MI RECYCLER
 */
    private class miAdapter extends RecyclerView.Adapter<miAdapter.miAdapterHolder> {

        @NonNull
        @Override
        public miAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new miAdapterHolder(getLayoutInflater().inflate(R.layout.cursoslayaout,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull miAdapterHolder holder, int position) {
                holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return nombres.size();
        }
/*
AQUI ADAPTAMOS TODOS LOS DATOS CON MI LAYOUT CREADO ANTERIORMENTE
 */
        private class miAdapterHolder extends RecyclerView.ViewHolder{
            TextView t1,t2;
            ImageView iv;
        public miAdapterHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.nombreLayoutCurso);
            t2 = itemView.findViewById(R.id.descripcionLayoutCurso);
            iv = itemView.findViewById(R.id.imagenlayoutCurso);
            //CREO UN SETONCLICK PARA GESTIONAR EL CLICK EN EL
            //RECYCLER
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //SACAMOS LA POSICION DEL CLCIK
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        //SACAMOS EL NOMBRE DE LA POSCION
                        String clicadoItem = nombres.get(position);
                        //LLAMAMOS A CURSO SELECCIONADO
                        cursoSeleccionad(clicadoItem);
                    }

                }
            });
        }
            /*
            IMPRIMO LOS VALORES QUE SE ENCUENTREN EN LA POSICION
             */
            public void imprimir(int position) {
                t1.setText(nombres.get(position));
                t2.setText(descripcion.get(position));
                iv.setImageResource(R.drawable.barranegra);
            }
        }
    }
}