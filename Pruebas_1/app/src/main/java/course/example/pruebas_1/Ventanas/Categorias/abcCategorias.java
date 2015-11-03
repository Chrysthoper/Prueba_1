package course.example.pruebas_1.Ventanas.Categorias;

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
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class abcCategorias extends ActionBarActivity {

    private IAdaptersCallerGridCategoriasGroup caller;
    private int indexColor = -1;
    private int indexImagen = -1;
    public GridView gvColores, gvImagenes;
    public ImageView ivCategoriaDummy;
    public EditText etNombreCategoria;
    public RadioGroup rdbGroupTipoCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc_categorias);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        gvColores = (GridView)findViewById(R.id.gvColores);
        gvColores.setAdapter(new ColoresAdapter(this, Util.colores, -1));
        gvColores.setOnItemClickListener(click_color);

        gvImagenes = (GridView)findViewById(R.id.gvImagenes);
        gvImagenes.setAdapter(new ImagenesAdapter(this, Util.imagenes, -1));
        gvImagenes.setOnItemClickListener(click_imagen);

        ivCategoriaDummy = (ImageView)findViewById(R.id.ivCategoriaDummy);
        etNombreCategoria = (EditText)findViewById(R.id.etNombreCategoria);

        rdbGroupTipoCategorias = (RadioGroup) findViewById(R.id.rdbGroupTipoCategorias);
    }

    public boolean Validate()
    {
        if(etNombreCategoria.getText().length() == 0)
            return false;
        if(indexImagen == -1)
            return false;
        if(indexColor == -1)
            return false;
        return true;
    }

    AdapterView.OnItemClickListener click_color = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            ivCategoriaDummy.setBackgroundResource(Util.colores[position]);
            indexColor = position;
        }
    };

    AdapterView.OnItemClickListener click_imagen = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            ivCategoriaDummy.setImageResource(Util.imagenes[position]);
            if(indexColor == -1)
                ivCategoriaDummy.setBackgroundResource(R.drawable.forma_circulonegro);
            indexImagen = position;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ventana_categorias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.btnCrearCategoriaNueva) {
            if(Validate())
            {
                int TipoCategoria = (rdbGroupTipoCategorias.getCheckedRadioButtonId() == R.id.rdbEntradas) ? 1 : 0;
                Categoria categoria = new Categoria(0,etNombreCategoria.getText().toString().toUpperCase(),Util.imagenes[indexImagen],Util.colores[indexColor],Util.coloresHex[indexColor],TipoCategoria);
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                if(dbHelper.Categorias.Inserta(categoria))
                {
                    Toast.makeText(abcCategorias.this, "Se agrego la categoria de " + categoria.nombre, Toast.LENGTH_SHORT).show();
                    setResult(1, new Intent());
                    finish();
                }
            }
            else
                Toast.makeText(abcCategorias.this, "No se puede crear la categoria ya que falta informacion", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setCallback(IAdaptersCallerGridCategoriasGroup caller){
        this.caller = caller;
    }
}
