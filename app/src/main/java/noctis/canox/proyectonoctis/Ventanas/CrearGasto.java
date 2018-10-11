package noctis.canox.proyectonoctis.Ventanas;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.Clases.BaseDatosRemota;
import noctis.canox.proyectonoctis.Clases.Categoria;
import noctis.canox.proyectonoctis.R;

public class CrearGasto extends AppCompatActivity{
private Button crear;
private EditText txtNombre, txtDinero;
private AutoCompleteTextView txtCategoria;
private TextView txtFecha;

public String nombre, categoria,f;
public Date fecha;
public double dinero;

public Intent a;
private BaseDatos bd;
private SimpleDateFormat formatoDeFecha;

private ArrayAdapter<Categoria> adaptador;
private ArrayList<Categoria>arrayCategoria;
private Context context;

private BaseDatosRemota bdRemota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto);
        txtNombre=findViewById(R.id.nombreGasto);
        txtCategoria=findViewById(R.id.categoriaGasto);
        txtDinero=findViewById(R.id.dineroGasto);
        txtFecha=findViewById(R.id.Fecha);
        crear= findViewById(R.id.Boton);

        context=this;
        a=getIntent();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); // Permite que se ejecute el hilo de descarga

        formatoDeFecha= new SimpleDateFormat("dd-MM-yyyy");
        fecha=(Date) a.getExtras().get("fecha");
        f=formatoDeFecha.format(fecha);
        txtFecha.setText(f);

        bd= new BaseDatos(this);
        bd.crearBaseDatos();
        rellenarCategorias();

        //bdRemota=new BaseDatosRemota(this);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               nombre=txtNombre.getText().toString();
               categoria=txtCategoria.getText().toString();
               dinero=Double.parseDouble(txtDinero.getText().toString());
               //Base de datos local
               bd.insertarCategoria(categoria);
               int idC=bd.cargarCategoria().get(bd.cargarCategoria().size()-1).getId();
               bd.insertarGasto(nombre,dinero,idC,fecha);
               // Base de datos remota
                // bdRemota.insertarCategoria(categoria);
               //bdRemota.insertarGasto(nombre,1,dinero,fecha);

               finish();
            }
        });
    }
    public void rellenarCategorias(){
        arrayCategoria=bd.cargarCategoria();
        adaptador=new ArrayAdapter<Categoria>(this,android.R.layout.simple_list_item_1,arrayCategoria);
        txtCategoria.setAdapter(adaptador);
        txtCategoria.setThreshold(1);

    }


}
