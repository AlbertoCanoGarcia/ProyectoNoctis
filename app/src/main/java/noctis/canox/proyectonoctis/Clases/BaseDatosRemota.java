package noctis.canox.proyectonoctis.Clases;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseDatosRemota implements Response.ErrorListener, Response.Listener<JSONObject> {
    Context context;
    SimpleDateFormat formatoDeFecha=new SimpleDateFormat("yyyy-MM-dd");
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    public BaseDatosRemota(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }
    public void postJson(String url){
        jsonObjectRequest= new JsonObjectRequest(Request.Method.POST,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }
    public void getJson(String url){
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray jsArray = response.getJSONArray("categoria");
//                    for(int i = 0;i < jsArray.length();i++){
//                        JSONObject object = jsArray.getJSONObject(i);
//                        String a =response.toString();
                        Toast.makeText(context,"aaaaaaaaaaaaa",Toast.LENGTH_SHORT);

//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }
        } ,this);
        requestQueue.add(jsonObjectRequest);

    }
    public void insertarGasto(String nombre , int categoria, Double dinero,Date fecha) {
        SimpleDateFormat formatoDeFecha=new SimpleDateFormat("yyyy-MM-dd");
        String f=formatoDeFecha.format(fecha);
        String url="http://proyectoalbertocano.000webhostapp.com/PostGastos.php?" +
                "Nombre="+nombre+"&Dinero="+dinero+"&categoria="+categoria+"&Fecha="+f+"";
        postJson(url);
    }
    public void insertarCategoria(String categoria){
        String url="http://proyectoalbertocano.000webhostapp.com/PostCategorias.php?" +
                "&categoria="+categoria+"";
        postJson(url);
    }
    public void maxIdCategoria(){

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        String a = error.toString();
    }

    @Override
    public void onResponse(JSONObject response) {
        String a =response.toString();
        Toast.makeText(context,a,Toast.LENGTH_SHORT);
    }

}
