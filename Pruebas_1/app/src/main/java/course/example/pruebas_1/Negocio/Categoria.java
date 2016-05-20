package course.example.pruebas_1.Negocio;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Chrys-Emcor on 06/10/2015.
 */
public class Categoria implements Serializable
{
    public int id;
    public String nombre;
    public int resource;
    public int color;
    public int formaCirculo;
    public int tipo;
    public double total;

    public Categoria(){}

    public Categoria(int id, String nombre, int resource, int formaCirculo, int color, int tipo)
    {
        this.id = id;
        this.nombre = nombre;
        this.resource = resource;
        this.color = color;
        this.formaCirculo = formaCirculo;
        this.tipo = tipo;
    }

    public Categoria(int id, String nombre, int resource, int formaCirculo, int color, double total,int tipo)
    {
        this.id = id;
        this.nombre = nombre;
        this.resource = resource;
        this.color = color;
        this.formaCirculo = formaCirculo;
        this.total = total;
        this.tipo = tipo;
    }

    public Categoria(JSONObject json)
    {
        try
        {
            this.id = json.getInt("id");
            this.nombre = json.getString("nombre");
            this.tipo = json.getInt("tipo");
            this.total = json.getDouble("total");
            this.color = json.getInt("color");
            this.formaCirculo = json.getInt("formaCirculo");
            this.resource = json.getInt("resource");
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
            obj.put("formaCirculo", this.formaCirculo);
            obj.put("color", this.color);
            obj.put("total", this.total);
            obj.put("tipo", this.tipo);
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }
}
