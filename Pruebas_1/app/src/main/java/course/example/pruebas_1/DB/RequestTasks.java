package course.example.pruebas_1.DB;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RequestTasks extends AsyncTask<String, Void, String> {
    public String response = "";
    private Context context;
    public RequestTasks(Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {

        HttpURLConnection con = null;

        try {
            URL url = new URL("http://portafolio-annmedina.herokuapp.com/import");
            // Construir los datos a enviar
            String data = "body=" + URLEncoder.encode(urls[0], "UTF-8");

            con = (HttpURLConnection)url.openConnection();

            // Activar método POST
            con.setDoOutput(true);

            // Tamaño previamente conocido
            con.setFixedLengthStreamingMode(data.getBytes().length);

            // Establecer application/x-www-form-urlencoded debido a la simplicidad de los datos
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            con.setRequestProperty("Authorization","body=" + URLEncoder.encode(urls[0], "UTF-8"));

            con.getOutputStream().write(data.getBytes());

            Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s)
    {

        /* Se crea un adaptador con el el resultado del parsing
        que se realizó al arreglo JSON
         */

        /*
        ArrayAdapter adapter = new ArrayAdapter(
                getBaseContext(),
                android.R.layout.simple_list_item_1,
                s);
        */
        // Relacionar adaptador a la lista
        response = s;
        Toast.makeText(this.context, response, Toast.LENGTH_SHORT).show();
    }
}
