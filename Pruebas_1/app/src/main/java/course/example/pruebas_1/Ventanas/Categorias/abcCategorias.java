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
import android.widget.Toast;

import course.example.pruebas_1.Adapters.ColoresAdapter;
import course.example.pruebas_1.Adapters.ImagenesAdapter;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.R;

public class abcCategorias extends ActionBarActivity {

    private int indexColor = -1;
    private int indexImagen = -1;
    public GridView gvColores, gvImagenes;
    public ImageView ivCategoriaDummy;
    public EditText etNombreCategoria;
    public int[] colores = new int[]{
            R.drawable.forma_circulo1,
            R.drawable.forma_circulo2,
            R.drawable.forma_circulo3,
            R.drawable.forma_circulo4,
            R.drawable.forma_circulo5,
            R.drawable.forma_circulo6,
            R.drawable.forma_circulo7,
            R.drawable.forma_circulo8,
            R.drawable.forma_circulo9,
            R.drawable.forma_circulo10
    };
    public int[] coloresHex = new int[]{
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
            R.color.color10
    };
    public int[] imagenes = new int[]{
            R.mipmap.gas,
            R.mipmap.chucherias,
            R.mipmap.camion,
            R.mipmap.agua,
            R.mipmap.casa,
            R.mipmap.comida,
            R.mipmap.compras,
            R.mipmap.luz,
            R.mipmap.gasolina,
            R.mipmap.pareja,
            R.mipmap.ropa,
            R.mipmap.carro,
            R.mipmap.internet,
            R.mipmap.cellphone,
            R.mipmap.credito,
            R.mipmap.trabajo
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc_categorias);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        gvColores = (GridView)findViewById(R.id.gvColores);
        gvColores.setAdapter(new ColoresAdapter(this, colores, -1));
        gvColores.setOnItemClickListener(click_color);

        gvImagenes = (GridView)findViewById(R.id.gvImagenes);
        gvImagenes.setAdapter(new ImagenesAdapter(this, imagenes, -1));
        gvImagenes.setOnItemClickListener(click_imagen);

        ivCategoriaDummy = (ImageView)findViewById(R.id.ivCategoriaDummy);
        etNombreCategoria = (EditText)findViewById(R.id.etNombreCategoria);
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
            ivCategoriaDummy.setBackgroundResource(colores[position]);
            indexColor = position;
        }
    };

    AdapterView.OnItemClickListener click_imagen = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            ivCategoriaDummy.setImageResource(imagenes[position]);
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
                Categoria categoria = new Categoria(0,etNombreCategoria.getText().toString().toUpperCase(),imagenes[indexImagen],colores[indexColor],coloresHex[indexColor]);
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
}
