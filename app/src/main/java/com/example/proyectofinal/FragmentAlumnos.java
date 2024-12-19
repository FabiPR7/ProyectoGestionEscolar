package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Modelo.SQLmyDataBase;

public class FragmentAlumnos extends Fragment {
    /*
    ESTA CLASE ME AYUDARA A GESTIONAR EL FRAGMENTO QUE SE ENCUNETRA EN CURSO
    GESTIONANDO EL FUNCIONAMINETO DE AGREGAR ALUMNOS
     */
    RecyclerView rv;
    String nombreCurso;
    ArrayList<String> nombres = new ArrayList<>();
    public FragmentAlumnos() {
        // Required empty public constructor
    }
    //CONSTRUCTOR CON STRING PARA PASARLE EL NOMBRE DEL CURSO
    public FragmentAlumnos(String curso){
        this.nombreCurso = curso;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_alumnos, container, false);
    }
/*
AQUI GESTIONO LA VISUALIZACION GENERAL DEL FRAGMENT
 */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ADAPTO MI RECYCLER AL QUE HE CREADO
        rv =view.findViewById(R.id.recalumno);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(new miAdapter());
        //LLAMO AL METODO PARA OBTENER TODOS LOS ALUMNOS DEL CURSO
        obtenerAlumnosAdapter();

    }
/*
ESTE METODO AGREGA TODOS LOS ALUMNOS DE UN CURSO EN ESPECIFICO
 */
    @SuppressLint("Range")
    public void obtenerAlumnosAdapter() {
        //CREO EL CURSOS QUE DEVUELVE A TODOS LOS ALUMNOS
        Cursor cursor = new SQLmyDataBase(this.getContext(), "GestSchoolBD", null, 1).obtenerAlumnos(nombreCurso);
        if (cursor.moveToFirst()) {
            do {//AGREGO SOLO EL NOMBRE DE LOS ALUMNOS
                nombres.add(cursor.getString(cursor.getColumnIndex("NOMBRE")));
            } while (cursor.moveToNext());
        }
    }
    /*
    ESTE ES MI ADAPTADOR QUE ME AYUDA A VISUALIZAR DE MANERA
    CORRECTA MI RECYLER
     */
        private class miAdapter extends RecyclerView.Adapter<miAdapter.miAdapterHolder> {

        @NonNull
        @Override
        public miAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //DEVUELVO EL ADAPTER DEL LAYOUT QUE CREE PARA ESTO
            return new miAdapterHolder(getLayoutInflater().inflate(R.layout.alumno_layout_recycler,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull miAdapterHolder holder, int position) {
                holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return nombres.size();
        }

        private class miAdapterHolder extends RecyclerView.ViewHolder{
            TextView tv;
            ImageView iv;
            public miAdapterHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.alumnoTexto);
                iv = itemView.findViewById(R.id.alumnoImagen);
            }

            public void imprimir(int position) {
                tv.setText(nombres.get(position));
                iv.setImageResource(R.drawable.alumno);
            }
        }
    }
}