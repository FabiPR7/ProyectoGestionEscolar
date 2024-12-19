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

public class FragmentTemas extends Fragment {
    RecyclerView rv;
    ArrayList<String> nombres = new ArrayList<>();
    String nombreCuros;
    /*
    EN LA ZONA DE CURSO TENGO UN FRAGMENTO DE TEMAS PUES ESTA CLASE SE ENCARGA
    DE SU CORRECTO FUNCIONAMIENTO
     */
    public FragmentTemas() {
        // Required empty public constructor
    }
    //CONTRSUCTOR CON EL NOMBRE DEL CURSO (usare este)
    public FragmentTemas(String curso) {
        // Required empty public constructor
        this.nombreCuros = curso;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_temas, container, false);
    }

    /*
    METODO DE VISUALIZACION
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //OBTENGO PRIMERO TODOS LOS NOMBRES DE LOS TEMAS
        obtenerTemasAdapter();
        //LUEGO ADAPTO MI RECYCLERVIEW AL QUE HE CREADO
        rv =view.findViewById(R.id.recytemas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(new miAdapter());
    }
/*
    AGREGO TODOS LOS NOMBRES DE LA TABLA TEMAS
 */
    @SuppressLint("Range")
    public void obtenerTemasAdapter() {
        //CREO EL CURSOR LLAMANDO AL METODO QUE DEVUELVE TODOS LOS DATOS
        Cursor cursor = new SQLmyDataBase(this.getContext(), "GestSchoolBD", null, 1).obtenerTenas(nombreCuros);
        if (cursor.moveToFirst()) {
            do {
                //LOS AGREGO A MI ARRAY DE NOMBRES
                nombres.add(cursor.getString(cursor.getColumnIndex("NOMBRE")));
            } while (cursor.moveToNext());
        }
    }
/*
ESTE ES MI ADAPTADOR HECHO PARA GESTIONAR LA VISUALIZACION
DE MI RECYLCERVIEW DE TEMAS
 */
    private class miAdapter extends RecyclerView.Adapter<miAdapter.miAdapterHolder> {

        @NonNull
        @Override
        public  miAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new miAdapterHolder(getLayoutInflater().inflate(R.layout.layout_temas_recycler,parent,false));
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
                tv = itemView.findViewById(R.id.textotemaslayout);
                iv = itemView.findViewById(R.id.fototemas);
            }

            public void imprimir(int position) {
                tv.setText(nombres.get(position));
                iv.setImageResource(R.drawable.temasconteinerdos);
            }
        }
    }
}