package course.example.pruebas_1.Negocio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Chrysthoper on 14/05/2016.
 */
public class ExportObj {
    public JSONObject credentialsJSON;
    public JSONArray ListaCategoriasJSON;
    public JSONArray ListaCuentasJSON;
    public JSONArray ListaTransaccionesJSON;

    public Credenciales credentials;
    public List<Categoria> ListaCategorias;
    public List<Cuenta> ListaCuentas;
    public List<Transaccion> ListaTransacciones;

    public ExportObj(){ }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("credentials", this.credentialsJSON);
            obj.put("ListaCategorias", this.ListaCategoriasJSON);
            obj.put("ListaCuentas", this.ListaCuentasJSON);
            obj.put("ListaTransacciones", this.ListaTransaccionesJSON);
        } catch (JSONException e) {
            return null;
        }
        return obj;
    }

}
