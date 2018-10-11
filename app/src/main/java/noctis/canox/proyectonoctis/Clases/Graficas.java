package noctis.canox.proyectonoctis.Clases;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PieChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class Graficas {
    private final int[] arrayColores={Color.RED,Color.BLUE,Color.YELLOW,Color.WHITE,Color.GREEN, Color.CYAN,Color.MAGENTA,Color.DKGRAY,Color.BLACK,Color.GRAY};
    private GraphicalView chartView;
    private Context context;
    private CategorySeries series;
    private XYMultipleSeriesDataset dataset;
    public Graficas(Context context) {
        this.context = context;
    }
    public GraphicalView graficoTarta(Object[][] array,String texto){
        series=new CategorySeries(texto);
        DefaultRenderer mRenderer= new DefaultRenderer();
        SimpleSeriesRenderer renderer=null;
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setChartTitle("Gasto por categorias");
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[] { 100, 100, 100, 100 });

        //mRenderer.setStartAngle(90);
        for(int i=0;i<array.length;i++) {
            Double valu = (Double) array[i][1];
            series.add((String) array[i][0], valu.intValue());
            renderer= new SimpleSeriesRenderer();
            renderer.setColor(arrayColores[i]);
            mRenderer.addSeriesRenderer(renderer);
        }
        if (chartView != null) {
            chartView.repaint();
        }

        PieChart chart=new PieChart(series,mRenderer);
        chartView = new GraphicalView(context,chart);
        return chartView;
    }
   // public void graficaPuntos(){
    //series=new CategorySeries("Gastos por categorias");

    //dataset=new XYMultipleSeriesDataset();
          /* // dataset.addSeries(series);
        // Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        // Include low and max value
        renderer.setDisplayBoundingPoints(true);
        // we add point markers
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        // We want to avoid black border
// transparent margins
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
// Disable Pan on two axis

        mRenderer.setPanEnabled(true, true);
        mRenderer.setYAxisMax(50);
        mRenderer.setYAxisMin(0);
        mRenderer.setXAxisMax(50);
        mRenderer.setShowGrid(true); // we show the grid*/
        //LineChart chart=new LineChart(series,mRenderer);
  //  }

}
