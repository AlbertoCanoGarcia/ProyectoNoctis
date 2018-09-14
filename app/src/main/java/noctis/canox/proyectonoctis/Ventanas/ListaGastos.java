package noctis.canox.proyectonoctis.Ventanas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.Clases.Gasto;
import noctis.canox.proyectonoctis.R;

public class ListaGastos extends AppCompatActivity {
private ArrayAdapter<Gasto> adaptador;
private BaseDatos bd;
private ListView lista;
private TextView textoGastoTotal;
private Double gastoTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gastos);
        lista=findViewById(R.id.lista);
        textoGastoTotal=findViewById(R.id.gastosTotales);
        bd= new BaseDatos(this);
        bd.crearBaseDatos();
        adaptador = new ArrayAdapter<Gasto>(this, android.R.layout.simple_list_item_1, bd.cargarGastos());
        lista.setAdapter(adaptador);
        DecimalFormat df = new DecimalFormat("0.00");
        gastoTotal= bd.cargarGastoTotal();
        textoGastoTotal.setText("Gasto Total: "+df.format(gastoTotal));

    }
}
