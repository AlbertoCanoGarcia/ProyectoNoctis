package noctis.canox.proyectonoctis.Ventanas;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.Clases.Categoria;
import noctis.canox.proyectonoctis.Clases.Gasto;
import noctis.canox.proyectonoctis.R;

public class ListaCategorias extends AppCompatActivity {
    private ArrayAdapter<Categoria> adaptador;
    private BaseDatos bd;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categorias);
        lista=(ListView) findViewById(R.id.lista);
        bd= new BaseDatos(this);
        bd.crearBaseDatos();

        adaptador = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1, bd.cargarCategoria());
        lista.setAdapter(adaptador);
    }
}
