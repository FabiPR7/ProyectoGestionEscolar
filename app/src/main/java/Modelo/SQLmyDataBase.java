package Modelo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLmyDataBase extends SQLiteOpenHelper {
/*
ESTE ES MI GESTOR DE BASE DE DATOS CON SQLITE
 */
    public SQLmyDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /*
    AL CREARLE LA BASE DE DATOS HACEMOS QUE SE CREEN YA TODAS LAS TABLAS
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTableTemas();
        crearTableAlumno();
        crearTablaCurso();
        crearTablaUsuario();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    /*
    CREAMOS LAS TABLAS, ESTA ES DE USUARIO CON LOS DATOS
     */
    public void crearTablaUsuario()
    {   String CREATE_TABLE_USUARIO =
            "CREATE TABLE USUARIO (" +
                    "ID INTEGER PRIMARY KEY, " +
                    "NOMBRE TEXT, " +
                    "APELLIDO TEXT, " +
                    "CORREO TEXT, " +
                    "PWD TEXT,"+
                    "DIRECCION TEXT,"+
                    "TELEFONO TEXT);";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_USUARIO);
    }
    /*
    CREAMOS LA TABLA CURSOS
     */
    public void crearTablaCurso()
    {   String CREATE_TABLE_CURSO =
            "CREATE TABLE CURSO (" +
                    "CODMATRICULA TEXT PRIMARY KEY, " +
                    "NOMBRE TEXT, " +
                    "DESCRIPCION TEXT);";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_CURSO);
    }
    /*
    CREAMOS LA TABLA ALUMNO
     */
    public void crearTableAlumno()
    {   String CREATE_TABLE_ALUMNO =
            "CREATE TABLE ALUMNO (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NOMBRE TEXT, " +
                    "CURSO TEXT);";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_ALUMNO);
    }
    /*
    CREAMOS LA TABLA DE TEMAS
     */
    public void crearTableTemas()
    {   String CREATE_TABLE_Temas =
            "CREATE TABLE TEMAS (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NOMBRE TEXT, " +
                    "CURSO TEXT);";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_Temas);
    }

/*
EN ESTA SECCION ES PARA LOS METODOS DE ELIMINACION
 */
    /*
    AQUI BORRO TODOS LOS DATOS DEL USUARIO
     */
    public void borrarTodosLosDatosUsuario() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  " + "USUARIO");
    }
    /*
  AQUI BORRO TODOS LOS DATOS DEL CURSO
   */
    public void borrarTodosLosDatosCurso() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  " + "CURSO");
    }
    /*
  AQUI BORRO TODOS LOS DATOS DE ALUMNOS
   */
    public void borrarTodosLosDatosAlumnos() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  " + "ALUMNO");
    }
    /*
  AQUI BORRO TODOS LOS DATOS DE TEMAS
   */
    public void borrarTodosLosDatosTemas() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  " + "TEMAS");
    }
    /*
    ESTE METODO ES PARA ACTUALIZAR LOS DATOS DEL USUARIO
     */
    public int actualizarUsuario(int id, String nombre, String apellido, String direccion, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE", nombre);
        values.put("APELLIDO", apellido);
        values.put("DIRECCION", direccion);
        values.put("TELEFONO", telefono);
        return db.update("USUARIO", values, "ID = ?", new String[]{String.valueOf(id)});
    }
    /*
    AQUI OBTENDREMOS UN CURSOS CON TODOS LOS DATOS DE LA TABLA CURSO
     */
    public Cursor obtenerCursos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + "CURSO", null);
    }
    /*
       AQUI OBTENDREMOS UN CURSOS CON TODOS LOS DATOS DE LA TABLA ALUMNO
        */
    public Cursor obtenerAlumnos(String curso) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + "ALUMNO "+"WHERE CURSO='"+curso+"'", null);
    }
    /*
   AQUI OBTENDREMOS UN CURSOS CON TODOS LOS DATOS DE LA TABLA TEMAS
    */
    public Cursor obtenerTenas(String curso) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + "TEMAS "+"WHERE CURSO='"+curso+"'", null);
    }
    /*
       ESTE ES UN METODO QUE USO PARA SABER SI EL USUARIO YA ESTA REGISTRADO Y ASI NO PEDIRLE
       QUE SE REGISTRE DE NUEVO
        */
    public boolean estaRegistrado() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + "USUARIO", null);
        return c.moveToFirst();
    }
/*
CLASE UTIL DONDE OBTENGO EL NOMNBRE DEL USUARIO
 */
    @SuppressLint("Range")
    public String obtenerNombreUsuario(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + "USUARIO", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("NOMBRE"));
    }
    /*
   AQUI OBTENGO TODOS LOS DATOS DEL USUARIO PRINCIPAL
    */
    @SuppressLint("Range")
    public Cursor obtenerdatosUsario(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + "USUARIO", null);
        return  c;
    }
}
