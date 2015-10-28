package course.example.pruebas_1.Ventanas.Categorias;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import course.example.pruebas_1.Adapters.CategoriaAdapter;
import course.example.pruebas_1.Adapters.ColoresAdapter;
import course.example.pruebas_1.Adapters.ImagenesAdapter;
import course.example.pruebas_1.Adapters.TransaccionAdapter;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.DB.TD_Categorias;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;


public class VentanaCategorias extends ActionBarActivity {

    ArrayList<Categoria> ListaCategorias;
    TD_Categorias td_categorias;
    GridView lvVentanaCategorias;
    private DBHelper dbHelper;
    private CategoriaAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_categorias);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        lvVentanaCategorias = (GridView)findViewById(R.id.lvVentanaCategorias);

        dbHelper = new DBHelper(this);
        td_categorias = dbHelper.Categorias;
        ListaCategorias = td_categorias.Obten();
        adapter = new CategoriaAdapter(this,ListaCategorias, -1);
        lvVentanaCategorias.setAdapter(adapter);
        lvVentanaCategorias.setOnItemClickListener(click_grid);
        lvVentanaCategorias.setOnItemLongClickListener(click_long_item);
    }

    AdapterView.OnItemLongClickListener click_long_item = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if(position > 0)
        {
            final Categoria categoria = ListaCategorias.get(position);
            new AlertDialog.Builder(VentanaCategorias.this)
                    .setMessage("¿Seguro que desea borrar la categoria?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (dbHelper.Categorias.Elimina(categoria.id)) {
                                ActualizaVentana();
                                Toast.makeText(VentanaCategorias.this, "Se elimino la categoria", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        return true;
        }
    };

    public void ActualizaVentana(){
        td_categorias = dbHelper.Categorias;
        ListaCategorias = td_categorias.Obten();
        adapter = new CategoriaAdapter(this,ListaCategorias, -1);
        lvVentanaCategorias.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    AdapterView.OnItemClickListener click_grid = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            if(position > 0)
            {
            }
            else
            {
                startActivityForResult(new Intent(getApplicationContext(), abcCategorias.class),1);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActualizaVentana();
        if (requestCode == 1)
        {
            if(resultCode != 1)
                Toast.makeText(this, "No se genero ninguna categoria", Toast.LENGTH_SHORT).show();
        }
    }
}
