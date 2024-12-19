package com.example.proyectofinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class agregarCursoActivity extends AppCompatActivity {
    Button b1;
    EditText m,n,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_curso);
        b1 = (Button) findViewById(R.id.crearCursoButton);
        m = (EditText) findViewById(R.id.codMatriculaTxt);
        n = (EditText) findViewById(R.id.nombreCursoTxt);
        d = (EditText) findViewById(R.id.descrpcionCursoTxt);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AL ACEPTAR LLAMO A DEVOLVER DATOS
                devolverDatos();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    /*
    MANDA DE VUELTA AL ACTIVTY QUE LO LLAMO LOS DATOS
     */
    public void devolverDatos(){
        Intent i = new Intent();
        //MANDO LOS VALORES CON SU CLAVE Y VALOR
        i.putExtra("COD",m.getText().toString());
        i.putExtra("NOMBRE",n.getText().toString());
        i.putExtra("DESCRIPCION",d.getText().toString());
        setResult(Activity.RESULT_OK,i);
        finish();
    }
}