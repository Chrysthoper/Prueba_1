package course.example.pruebas_1.Ventanas.Cuentas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import course.example.pruebas_1.Adapters.CategoriaAdapter;
import course.example.pruebas_1.Adapters.CuentasAdapter;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.DB.TD_Categorias;
import course.example.pruebas_1.DB.TD_Cuentas;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Ventanas.Categorias.abcCategorias;

public class VentanaCuentas extends ActionBarActivity {

    GridView lvCuentasGridPager;
    CuentasAdapter adapterCuentas;
    ArrayList<Cuenta> listaCuentas;
    TD_Cuentas td_cuentas;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_cuentas);

        dbHelper = new DBHelper(this);

        listaCuentas = dbHelper.Cuentas.Obten();
        lvCuentasGridPager = (GridView) findViewById(R.id.lvCuentasGridPager);
        this.adapterCuentas = new CuentasAdapter(getApplicationContext(),this.listaCuentas);
        lvCuentasGridPager.setAdapter(adapterCuentas);
        adapterCuentas.notifyDataSetChanged();

        td_cuentas = dbHelper.Cuentas;
        listaCuentas = td_cuentas.Obten();
        this.adapterCuentas = new CuentasAdapter(this,this.listaCuentas);
        lvCuentasGridPager.setAdapter(this.adapterCuentas);
        lvCuentasGridPager.setOnItemClickListener(click_grid);
        lvCuentasGridPager.setOnItemLongClickListener(click_long_item);

    }

    AdapterView.OnItemLongClickListener click_long_item = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if(position > 0)
            {
                final Cuenta cuenta = listaCuentas.get(position);
                new AlertDialog.Builder(VentanaCuentas.this)
                        .setMessage("¿Seguro que desea borrar la categoria?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (dbHelper.Cuentas.Elimina(cuenta.id)) {
                                    ActualizaVentana();
                                    Toast.makeText(VentanaCuentas.this, "Se elimino la cuenta", Toast.LENGTH_SHORT).show();
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
        td_cuentas = dbHelper.Cuentas;
        listaCuentas = td_cuentas.Obten();
        adapterCuentas = new CuentasAdapter(this,listaCuentas);
        lvCuentasGridPager.setAdapter(adapterCuentas);
        adapterCuentas.notifyDataSetChanged();
    }

    AdapterView.OnItemClickListener click_grid = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            if(position == 0)
                startActivityForResult(new Intent(getApplicationContext(), abcCuentas.class),1);
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
