package noctis.canox.proyectonoctis.Ventanas;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.R;

public class MainActivity extends AppCompatActivity {
    private CalendarView calendario;
    private Context con=this;
    private int dia, mes ,ano;
    private Date fecha;
    private ArrayList<String> fechasG;
    DateFormat formatter2;
    private BaseDatos bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd=new BaseDatos(this);
        bd.crearBaseDatos();
        calendario= findViewById(R.id.calendario);
        calendario.setFirstDayOfWeek(2);
        calendario.setShowWeekNumber(false);
        formatter2= new SimpleDateFormat("dd-MM-yyyy");
        fecha=new Date();

 /*       dia=fecha.getDay();
        mes=fecha.getMonth();
        ano=fecha.getYear();*/
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               dia=dayOfMonth;
               mes=month+1;
               ano=year;
                try {
                    fecha=null;
                    fecha=formatter2.parse(dia+"-"+mes+"-"+ano+"");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.barra, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent i;
        switch (item.getItemId()) {
            case R.id.anadir:
                i = new Intent(con,CrearGasto.class);
                i.putExtra("fecha",fecha);
                startActivity(i);
                return true;
            case R.id.gastosTotales:
                i = new Intent(con,ListaGastos.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
