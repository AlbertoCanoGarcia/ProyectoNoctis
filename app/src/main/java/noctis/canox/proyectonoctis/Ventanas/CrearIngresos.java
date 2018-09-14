package noctis.canox.proyectonoctis.Ventanas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.R;

public class CrearIngresos extends AppCompatActivity {
    private Button crear;
    private EditText txtNombre, txtDinero;
    private TextView txtFecha;
    public String nombre,f;
    public Date fecha;
    public double dinero;
    public Intent a;
    private BaseDatos bd;
    private SimpleDateFormat formatoDeFecha;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ingresos);
        txtNombre=findViewById(R.id.nombreIngreso);
        txtDinero=findViewById(R.id.dineroIngreso);
        txtFecha=findViewById(R.id.Fecha2);
        crear= findViewById(R.id.BotonIngreso);
        context=this;
        a=getIntent();

        formatoDeFecha= new SimpleDateFormat("dd-MM-yyyy");
        fecha=(Date) a.getExtras().get("fecha");
        f=formatoDeFecha.format(fecha);
        txtFecha.setText(f);

        bd= new BaseDatos(this);
        bd.crearBaseDatos();

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=txtNombre.getText().toString();
                dinero=Double.parseDouble(txtDinero.getText().toString());
                bd.insertarIngreso(nombre,dinero,fecha);
                finish();
            }
        });

    }
}
