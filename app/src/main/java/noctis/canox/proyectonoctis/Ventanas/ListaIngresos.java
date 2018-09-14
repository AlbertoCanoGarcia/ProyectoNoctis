package noctis.canox.proyectonoctis.Ventanas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.Clases.Ingresos;
import noctis.canox.proyectonoctis.R;

public class ListaIngresos extends AppCompatActivity {
    private ArrayAdapter<Ingresos> adaptador;
    private BaseDatos bd;
    private ListView lista;
    private TextView textoGastoTotal;
    private Double gastoTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ingresos2);
        lista=findViewById(R.id.lista);
        //textoGastoTotal=findViewById(R.id.gastosTotales);
        bd= new BaseDatos(this);
        bd.crearBaseDatos();
        adaptador = new ArrayAdapter<Ingresos>(this, android.R.layout.simple_list_item_1, bd.cargarIngresos());
        lista.setAdapter(adaptador);
    }
}
