package course.example.pruebas_1.DB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ServiceRequestHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceRequestHandler() {

    }
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String path, String metodo, JSONObject params) {
        HttpURLConnection con = null;
        try {

            //URL url = new URL("http://portafolio-annmedina.herokuapp.com/import");
            URL url = new URL(path);
            con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setFixedLengthStreamingMode(params.toString().length());
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStream out = con.getOutputStream();
            out.write(params.toString().getBytes("UTF-8"));
            out.close();

            Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            response = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        return response;
    }
}
