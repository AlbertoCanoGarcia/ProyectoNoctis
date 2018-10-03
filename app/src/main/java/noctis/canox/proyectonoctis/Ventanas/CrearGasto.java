package noctis.canox.proyectonoctis.Ventanas;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import noctis.canox.proyectonoctis.Clases.BaseDatos;
import noctis.canox.proyectonoctis.Clases.Categoria;
import noctis.canox.proyectonoctis.Clases.CustomSSLSocketFactory;
import noctis.canox.proyectonoctis.R;

public class CrearGasto extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
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

private RequestQueue requestQueue;
private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_gasto);
        txtNombre=findViewById(R.id.nombreGasto);
        txtCategoria=findViewById(R.id.categoriaGasto);
        txtDinero=findViewById(R.id.dineroGasto);
        txtFecha=findViewById(R.id.Fecha);
        crear= findViewById(R.id.Boton);
        requestQueue = Volley.newRequestQueue(this);

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

        requestQueue= Volley.newRequestQueue(this);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               nombre=txtNombre.getText().toString();
               categoria=txtCategoria.getText().toString();
               dinero=Double.parseDouble(txtDinero.getText().toString());
               bd.insertarCategoria(categoria);
               int idC=bd.cargarCategoria().get(bd.cargarCategoria().size()-1).getId()
               bd.insertarGasto(nombre,dinero,idC,fecha);
               BDremotaInsertaGasto();
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
    public void BDremotaInsertaGasto(String nombre , int categoria, Double dinero) {
        String url="http://proyectoalbertocano.000webhostapp.com/ConexionBD2.php?" +
                "id=1&Nombre="+nombre+"&Dinero="+dinero+"&categoria="+categoria+"&Fecha="+fecha+"";
      /*  URL url2 = null;
        try {
            url2 = new URL("https://proyectoalbertocano.000webhostapp.com");
            HttpsURLConnection connection = (HttpsURLConnection) url2.openConnection();
            connection.setRequestMethod("POST");
            connection.setSSLSocketFactory(CustomSSLSocketFactory.getSSLSocketFactory(context));*/

            jsonObjectRequest= new JsonObjectRequest(Request.Method.POST,url,null,this,this);
            requestQueue.add(jsonObjectRequest);
      /*  } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }*/

}

    @Override
    public void onErrorResponse(VolleyError error) {
      //  Toast.makeText(context,"Fallo al crear gasto",Toast.LENGTH_SHORT).show();
       String a=error.toString();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(context,"Gasto creado correctamente",Toast.LENGTH_SHORT).show();
        txtNombre.setText("");
        txtCategoria.setText("");
        txtDinero.setText("");
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
