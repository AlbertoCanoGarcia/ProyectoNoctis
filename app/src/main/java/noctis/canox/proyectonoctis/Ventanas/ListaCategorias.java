package noctis.canox.proyectonoctis.Ventanas;

import android.app.ActionBar;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.StrictMode;
import android.service.autofill.CharSequenceTransformation;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.achartengine.GraphicalView;
import org.achartengine.chart.BubbleChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PieChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.XYChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.Clases.Categoria;
import noctis.canox.proyectonoctis.Clases.Gasto;
import noctis.canox.proyectonoctis.Clases.Graficas;
import noctis.canox.proyectonoctis.R;


public class ListaCategorias extends AppCompatActivity {
    private ArrayAdapter<Categoria> adaptador;
    private BaseDatos bd;
    private Object[][] array;
    private GraphicalView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categorias);
        LinearLayout layout=findViewById(R.id.layout);
        bd= new BaseDatos(this);
        bd.crearBaseDatos();

        array=bd.gastoTotalXCategoria(); // arrray con nombre de categoria y gasto total por cada categoria
        Graficas graficas=new Graficas(this);
        chartView=graficas.graficaTarta(array,"Gasto por categorias");
        layout.addView(chartView);
    }


}
