package noctis.canox.proyectonoctis.Ventanas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.lambdainvoker.*;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.Clases.Categoria;
import noctis.canox.proyectonoctis.Lambda.MyInterface;
import noctis.canox.proyectonoctis.R;
import noctis.canox.proyectonoctis.Lambda.RequestClass;
import noctis.canox.proyectonoctis.Lambda.ResponseClass;

public class CrearGasto extends AppCompatActivity {
private Button crear;
private EditText txtNombre, txtDinero;
private MultiAutoCompleteTextView txtCategoria;
private TextView txtFecha;
public String nombre, categoria,f;
public Date fecha;
public double dinero;
public Intent a;
private BaseDatos bd;
private SimpleDateFormat formatoDeFecha;
private ArrayAdapter<String> adaptador;
private ArrayList<Categoria>arrayCategoria;
private Context context;
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

        formatoDeFecha= new SimpleDateFormat("dd-MM-yyyy");
        fecha=(Date) a.getExtras().get("fecha");
        f=formatoDeFecha.format(fecha);
        txtFecha.setText(f);

        bd= new BaseDatos(this);
        bd.crearBaseDatos();
        rellenarCategorias();

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=txtNombre.getText().toString();
                categoria=txtCategoria.getText().toString();
                dinero=Double.parseDouble(txtDinero.getText().toString());
                bd.insertarCategoria(categoria);
                bd.insertarGasto(nombre,dinero,bd.cargarCategoria().get(bd.cargarCategoria().size()-1).getId(),fecha);
                finish();
            }
        });

    }
    public void rellenarCategorias(){
        arrayCategoria=bd.cargarCategoria();
        int tamano=arrayCategoria.size();
        String [] nca=new String[tamano];
        for(int i=0;i<tamano;i++){
            nca[i]=arrayCategoria.get(i).getCategoria();
        }
        adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nca);
        txtCategoria.setAdapter(adaptador);
        txtCategoria.setThreshold(1);
    }

   /* private void Lambda(){
        // Create an instance of CognitoCachingCredentialsProvider
        CognitoCachingCredentialsProvider cognitoProvider = new CognitoCachingCredentialsProvider(
                this.getApplicationContext(), "identity-pool-id", Regions.US_WEST_2);

// Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
        LambdaInvokerFactory factory = new LambdaInvokerFactory(this.getApplicationContext(),
                Regions.US_WEST_2, cognitoProvider);

// Create the Lambda proxy object with a default Json data binder.
// You can provide your own data binder by implementing
// LambdaDataBinder.
        final MyInterface myInterface = factory.build(MyInterface.class);

        RequestClass request = new RequestClass("John", "Doe");
// The Lambda function invocation results in a network call.
// Make sure it is not called from the main thread.
        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    return myInterface.AndroidBackendLambdaFunction(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ResponseClass result) {
                if (result == null) {
                    return;
                }

                // Do a toast
                Toast.makeText(CrearGasto.this, result.getGreetings(), Toast.LENGTH_LONG).show();
            }
        }.execute(request);
    }*/
}
