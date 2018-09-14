package noctis.canox.proyectonoctis.Ventanas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.R;

public class Saldo extends AppCompatActivity {
    private TextView txtSaldo;
    private Double saldo;
    private BaseDatos bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);
        DecimalFormat df = new DecimalFormat("0.00");
        bd= new BaseDatos(this);
        bd.crearBaseDatos();
        txtSaldo=findViewById(R.id.saldo);
        saldo=bd.cargarIngresosTotal()-bd.cargarGastoTotal();
        txtSaldo.setText(df.format(saldo));
    }
}
