package course.example.pruebas_1.Ventanas.Cuentas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import course.example.pruebas_1.Adapters.ColoresAdapter;
import course.example.pruebas_1.Adapters.ImagenesAdapter;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGridCategoriasGroup;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class abcCuentas extends ActionBarActivity {

    private int indexImagen = -1;
    public GridView gvImagenes;
    public ImageView ivCuentaDummy;
    public EditText etNombreCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_cuentas_abc);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        gvImagenes = (GridView)findViewById(R.id.gvImagenes);
        gvImagenes.setAdapter(new ImagenesAdapter(this, Util.imagenes, -1));
        gvImagenes.setOnItemClickListener(click_imagen);

        ivCuentaDummy = (ImageView)findViewById(R.id.ivCuentaDummy);
        etNombreCuenta = (EditText)findViewById(R.id.etNombreCuenta);
    }

    public boolean Validate()
    {
        if(etNombreCuenta.getText().length() == 0)
            return false;
        if(indexImagen == -1)
            return false;
        return true;
    }

    AdapterView.OnItemClickListener click_imagen = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            ivCuentaDummy.setImageResource(Util.imagenes[position]);
            ivCuentaDummy.setBackgroundResource(R.drawable.forma_circulonegro);
            indexImagen = position;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_abc_cuentas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.btnCrearCuentas) {
            if(Validate())
            {
                Cuenta cuenta = new Cuenta(0,etNombreCuenta.getText().toString().toUpperCase(),indexImagen,0);
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                if(dbHelper.Cuentas.Inserta(cuenta))
                {
                    Toast.makeText(abcCuentas.this, "Se agrego la cuenta de " + cuenta.nombre, Toast.LENGTH_SHORT).show();
                    setResult(1, new Intent());
                    finish();
                }
            }
            else
                Toast.makeText(abcCuentas.this, "No se puede crear la cuenta ya que falta informacion", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
