package noctis.canox.proyectonoctis.Clases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class BaseDatos {
    SQLiteDatabase baseDatos;
    Context context;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    String selectGasto="Select nombre,dinero,fecha,categoria FROM Gastos Order by fecha asc";
    String selectFechas="Select distinct Fecha from Gastos";
    String selectGastoTotal="Select sum(dinero) from Gastos ";
    String selectIngresos="Select * from Ingresos  Order by fecha asc";
    String selectIngresosTotal="Select sum(dinero) from Ingresos";
    public BaseDatos(Context context){
        this.context=context;
    }
    public void crearBaseDatos(){
        baseDatos = context.openOrCreateDatabase("ProyectoNoctis", MODE_PRIVATE,null) ;
        //baseDatos.execSQL("Drop Table Categoria");
        //baseDatos.execSQL("Drop Table Gastos");
        //baseDatos.execSQL("Drop Table Ingresos");
        String sqlCrearTabla2=
                "CREATE TABLE IF NOT EXISTS Categoria(" +
                        "id integer primary key  AUTOINCREMENT UNIQUE NOT NULL,"+
                        "categoria varchar(30) not null)";
        String sqlCrearTabla1 =
                "CREATE TABLE IF NOT EXISTS Gastos (" +
                        "id INTEGER  PRIMARY KEY AUTOINCREMENT," +
                        "Nombre VARCHAR(300)," +
                        "Dinero Decimal, " +
                        "categoria INTEGER NOT NULL, " +
                        "Fecha DATE,"+
                        "FOREIGN KEY(Categoria) References Categoria(id))";
        String sqlCrearTabla3="CREATE TABLE IF NOT EXISTS Ingresos (" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "Nombre VARCHAR(300)," +
                "Dinero Decimal, " +
                "Fecha DATE)";
        baseDatos.execSQL(sqlCrearTabla2);
        baseDatos.execSQL(sqlCrearTabla1) ;
        baseDatos.execSQL(sqlCrearTabla3);
    }
    // insertamos los datos de la direccion marcada en la tabla de la base de datps
    public void insertarGasto(String nombre, Double gasto, int Categoria,Date fecha){
        String sqlInsertarDatos = "INSERT INTO Gastos (Nombre,Dinero,Categoria,Fecha) VALUES ('"+nombre+"','"+gasto+"','"+Categoria+"','"+fecha+"');";
        baseDatos.execSQL(sqlInsertarDatos) ;
    }
    public void insertarIngreso(String nombre, Double gasto,Date fecha){
        String sqlInsertarDatos = "INSERT INTO Ingresos (Nombre,Dinero,Fecha) VALUES ('"+nombre+"','"+gasto+"','"+fecha+"');";
        baseDatos.execSQL(sqlInsertarDatos) ;
    }
    public void insertarCategoria(String categoria){
        Boolean existe=false;
        ArrayList<Categoria> arrayCatg;
        arrayCatg=cargarCategoria();
        for(int i=0;i<arrayCatg.size();i++){
            if(arrayCatg.get(i).getCategoria()==categoria){
                existe=true;
            }
        }
        if(!existe){
            String sqlInsertarDatos = "INSERT INTO Categoria (Categoria) VALUES ('"+categoria+"');";
            baseDatos.execSQL(sqlInsertarDatos) ;
        }
    }
    // Metodo para cargar todos los datos de las ubicaciones guardadas
    public ArrayList<Gasto> cargarGastos() {
        ArrayList<Gasto> arrayGastos = new ArrayList<Gasto>();
        Cursor c = baseDatos.rawQuery(selectGasto, null); // consulta para coger los datos en un cursor
        int indiceNombre = c.getColumnIndex("Nombre");
        int indiceCategoria = c.getColumnIndex("categoria");
        int indiceDinero = c.getColumnIndex("Dinero");
        int indiceFecha = c.getColumnIndex("Fecha");
// Movemos el cursor al primer resultado
        c.moveToFirst();
// Recorremos el resto de resultados
        if(c.getCount()!=0){
        int i=0;
        // Mientras que todavia quedean direcciones por leer
        while (i<c.getCount()) {
            String fecha=(c.getString(indiceFecha));
            Date date=null;
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
            try {
                date=formatoDeFecha.parse(fecha);
            } catch (Exception e) {
                e.printStackTrace();
            }
            arrayGastos.add(new Gasto(c.getString(indiceNombre),c.getString(indiceCategoria),
                    Double.parseDouble(c.getString(indiceDinero)),date ));
            c.moveToNext() ;
            i++;
        }
    }
    return arrayGastos;
    }
    public ArrayList<Categoria> cargarCategoria() {
        ArrayList<Categoria> arrayCategoria = new ArrayList<Categoria>();
        Cursor c = baseDatos.rawQuery("Select * from Categoria", null); // consulta para coger los datos en un cursor
//// permitirá acceder más tarde a estas columnas
        int indiceId = c.getColumnIndex("id");
        int indiceCategoria = c.getColumnIndex("categoria");
// Movemos el cursor al primer resultado
        c.moveToFirst();
// Recorremos el resto de resultados
        if(c.getCount()!=0){
            int i=0;
            // Mientras que todavia quedean direcciones por leer
            while (i<c.getCount()) {
                arrayCategoria.add(new Categoria(Integer.parseInt(c.getString(indiceId)),c.getString(indiceCategoria)));
                c.moveToNext() ;
                i++;
            }
        }
        return arrayCategoria;
    }
    public ArrayList<String> cargarFechas(){
        DateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
        String f="";
        ArrayList<String> arrayFechas = new ArrayList<String>();
        Cursor c = baseDatos.rawQuery(selectFechas, null); // consulta para coger los datos en un cursor
//// permitirá acceder más tarde a estas columnas
        int indice = c.getColumnIndex("Fecha");
// Movemos el cursor al primer resultado
        c.moveToFirst();
// Recorremos el resto de resultados
        if(c.getCount()!=0){
            int i=0;
            // Mientras que todavia quedean direcciones por leer
            while (i<c.getCount()) {
                f= formatter2.format(c.getString(indice));
                arrayFechas.add(f);
                c.moveToNext() ;
                i++;
            }
        }
        return arrayFechas;
    }
    public Double cargarGastoTotal(){
        Double gastoTotal=0.0;
        Cursor c = baseDatos.rawQuery(selectGastoTotal, null); // consulta para coger los datos en un cursor
//// permitirá acceder más tarde a estas columnas
// Movemos el cursor al primer resultado
        c.moveToFirst();
// Recorremos el resto de resultados
        if(c.getCount()!=0){
            int i=0;
            // Mientras que todavia quedean direcciones por leer
            while (i<c.getCount()) {
                gastoTotal=c.getDouble(0);
                c.moveToNext();
                i++;
            }
        }
        return gastoTotal;
    }
    public ArrayList<Ingresos> cargarIngresos() {
        ArrayList<Ingresos> arrayIngresos = new ArrayList<Ingresos>();
        Cursor c = baseDatos.rawQuery(selectIngresos, null); // consulta para coger los datos en un cursor
        int indiceNombre = c.getColumnIndex("Nombre");
        int indiceDinero = c.getColumnIndex("Dinero");
        int indiceFecha = c.getColumnIndex("Fecha");
// Movemos el cursor al primer resultado
        c.moveToFirst();
// Recorremos el resto de resultados
        if(c.getCount()!=0){
            int i=0;
            // Mientras que todavia quedean direcciones por leer
            while (i<c.getCount()) {
                String fecha=(c.getString(indiceFecha));
                Date date=null;
                SimpleDateFormat formatoDeFecha = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                        Locale.ENGLISH);
                try {
                    date=formatoDeFecha.parse(fecha);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                arrayIngresos.add(new Ingresos(c.getString(indiceNombre),
                        Double.parseDouble(c.getString(indiceDinero)),date ));
                c.moveToNext() ;
                i++;
            }
        }
        return arrayIngresos;
    }
    public Double cargarIngresosTotal(){
        Double gastoTotal=0.0;
        Cursor c = baseDatos.rawQuery(selectIngresosTotal, null); // consulta para coger los datos en un cursor
//// permitirá acceder más tarde a estas columnas
// Movemos el cursor al primer resultado
        c.moveToFirst();
// Recorremos el resto de resultados
        if(c.getCount()!=0){
            int i=0;
            // Mientras que todavia quedean direcciones por leer
            while (i<c.getCount()) {
                gastoTotal=c.getDouble(0);
                c.moveToNext();
                i++;
            }
        }
        return gastoTotal;
    }
}
