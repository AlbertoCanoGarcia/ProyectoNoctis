package noctis.canox.proyectonoctis.Clases;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class Descarga extends AsyncTask<String, Void, String> implements Response.ErrorListener, Response.Listener<JSONObject> {
        String resultado="";
        private Context context;
        private RequestQueue requestQueue;
        private JsonObjectRequest jsonObjectRequest;

    public Descarga(Context context) {
        this.context = context;
    }

    @Override protected void onPreExecute(){ }
        @Override protected String doInBackground(String... urls) {

            String datos = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                //InputStream inputStream = urlConnection.getInputStream();
               // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                urlConnection.setRequestMethod("POST");
               // urlConnection.setSSLSocketFactory(CustomSSLSocketFactory.getSSLSocketFactory(context));

                jsonObjectRequest= new JsonObjectRequest(Request.Method.POST,urls[0],null,this,this);
                requestQueue.add(jsonObjectRequest);

                urlConnection.disconnect();
               // inputStream.close();
                //bufferedReader.close();
                return resultado;
            } catch (Exception e) {
                urlConnection.disconnect();
                e.printStackTrace();
                return resultado;
            }
        }
        @Override protected void onPostExecute(String s) {

        }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
