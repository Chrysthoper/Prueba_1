package course.example.pruebas_1.DB;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Credenciales;
import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.Negocio.ExportObj;
import course.example.pruebas_1.Negocio.Transaccion;

public class RequestTasks extends AsyncTask<Credenciales, Void, String> {
    ProgressDialog progressDialog;
    DBHelper db;
    Credenciales credential;

    public String response = "";
    private Activity act;
    public RequestTasks(Activity act)
    {
        this.act = act;
        db = new DBHelper(this.act);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(this.act, "Wait","Wait");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Credenciales... credentials) {
        try {
            //URL url = new URL("http://portafolio-annmedina.herokuapp.com/import");
            //URL url = new URL("http://192.168.0.13:3000/export");
            ExportObj export = new ExportObj();

            this.credential = credentials[0];

            if(credentials[0].metodo == "export")
            {
                export.ListaCuentasJSON = new JSONArray();
                List<Cuenta> ListaCuentas = db.Cuentas.Obten();
                for (Cuenta cuenta : ListaCuentas) {
                    export.ListaCuentasJSON.put(cuenta.getJSONObject());
                }

                export.ListaCategoriasJSON = new JSONArray();
                List<Categoria> ListaCategorias = db.Categorias.Obten();
                for (Categoria cat : ListaCategorias) {
                    export.ListaCategoriasJSON.put(cat.getJSONObject());
                }

                export.ListaTransaccionesJSON = new JSONArray();
                List<Transaccion> ListaTransaccion = db.Transacciones.Obten();
                for (Transaccion trans : ListaTransaccion) {
                    export.ListaTransaccionesJSON.put(trans.getJSONObject());
                }
            }
            export.credentialsJSON = credentials[0].getJSONObject();

            ServiceRequestHandler sh = new ServiceRequestHandler();
            //String path = "http://192.168.0.13:3000/";
            String path = "http://portafolio-annmedina.herokuapp.com/";
            String jsonStr = sh.makeServiceCall(path + credentials[0].metodo, credentials[0].metodo, export.getJSONObject());

            response = jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();

        if(this.credential.metodo == "import")
        {
            ExportObj export = new ExportObj();

            JSONArray categorias = null;
            JSONArray cuentas = null;
            JSONArray transacciones = null;

            try {
                JSONObject jsonObj = new JSONObject(s);

                categorias = jsonObj.getJSONArray("ListaCategorias");
                export.ListaCategorias = new ArrayList<Categoria>();
                for (int i = 0; i < categorias.length(); i++) {
                    JSONObject cat = categorias.getJSONObject(i);

                    Categoria categoria = new Categoria(cat);
                    export.ListaCategorias.add(categoria);
                }
                cuentas = jsonObj.getJSONArray("ListaCuentas");
                export.ListaCuentas = new ArrayList<Cuenta>();
                for (int i = 0; i < cuentas.length(); i++) {
                    JSONObject c = cuentas.getJSONObject(i);

                    Cuenta cuenta = new Cuenta(c);
                    export.ListaCuentas.add(cuenta);
                }
                transacciones = jsonObj.getJSONArray("ListaTransacciones");
                export.ListaTransacciones = new ArrayList<Transaccion>();
                for (int i = 0; i < transacciones.length(); i++) {
                    JSONObject trans = transacciones.getJSONObject(i);

                    Transaccion transaccion = new Transaccion(trans);
                    export.ListaTransacciones.add(transaccion);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                response = db.onImport(export);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        final ScrollView scrollView = new ScrollView(act);
        final LinearLayout layout = new LinearLayout(act);
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextView input = new TextView(act);
        input.setText(response);
        input.setMaxLines(1000000);
        input.setLines(1000000);

        builder.setTitle("Respuesta del Post");
        layout.addView(input);
        scrollView.addView(layout);
        builder.setView(scrollView);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
