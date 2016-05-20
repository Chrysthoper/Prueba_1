package course.example.pruebas_1.Negocio;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Chrys-Emcor on 06/10/2015.
 */
public class Cuenta implements Serializable
{
    public int id;
    public String nombre;
    public int resource;
    public int color;
    public double total;

    public Cuenta(){}

    public Cuenta(int id, String nombre, int resource, int color)
    {
        this.id = id;
        this.nombre = nombre;
        this.resource = resource;
        this.color = color;
    }

    public Cuenta(JSONObject json)
    {
        try
        {
            this.id = json.getInt("id");
            this.nombre = json.getString("nombre");
            this.resource = json.getInt("resource");
            this.color = json.getInt("color");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", this.id);
            obj.put("nombre", this.nombre);
            obj.put("resource", this.resource);
            obj.put("color", this.color);
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
