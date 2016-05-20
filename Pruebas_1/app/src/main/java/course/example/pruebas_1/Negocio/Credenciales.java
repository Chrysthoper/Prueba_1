package course.example.pruebas_1.Negocio;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chrysthoper on 12/05/2016.
 */
public class Credenciales {

    public String username;
    public String password;
    public String metodo;

    public Credenciales(String username, String password, String metodo)
    {
        this.username =  username;
        this.password = password;
        this.metodo = metodo;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", this.username);
            obj.put("password", this.password);
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
