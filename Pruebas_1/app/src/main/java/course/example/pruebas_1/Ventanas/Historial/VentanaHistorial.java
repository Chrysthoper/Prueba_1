package course.example.pruebas_1.Ventanas.Historial;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import course.example.pruebas_1.Adapters.TransaccionAdapter;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Interfaces.IAdaptersCaller;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class VentanaHistorial extends ActionBarActivity implements IAdaptersCaller {

    TextView tvBalanceHistorial;
    ListView lvTransaccionesHistorial;
    TransaccionAdapter adapter;
    DBHelper dbHelper;
    ArrayList<Transaccion> listaTransacciones;
    String fecha_inicial;
    String fecha_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        dbHelper = new DBHelper(getApplicationContext());

        Intent intent = getIntent();
        fecha_inicial = intent.getStringExtra("FECHA_INICIAL");
        fecha_final = intent.getStringExtra("FECHA_FINAL");

        double SumEntradas = dbHelper.Transacciones.SumatoriaEntradas(fecha_inicial,fecha_final);
        double SumSalidas = dbHelper.Transacciones.SumatoriaSalidas(fecha_inicial,fecha_final);

        tvBalanceHistorial = (TextView)findViewById(R.id.tvBalanceHistorial);
        tvBalanceHistorial.setText(Util.PriceFormat(SumEntradas - SumSalidas));
        if((SumEntradas - SumSalidas) < 0)
            tvBalanceHistorial.setTextColor(Color.parseColor("#ffff4444"));
        else
            tvBalanceHistorial.setTextColor(Color.parseColor("#ff99cc00"));

        lvTransaccionesHistorial = (ListView)findViewById(R.id.lvTransaccionesHistorial);
        listaTransacciones = dbHelper.Transacciones.Obten(fecha_inicial,fecha_final);
        adapter = new TransaccionAdapter(VentanaHistorial.this,listaTransacciones, true);
        adapter.setCallback(this);
        lvTransaccionesHistorial.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ActualizaVentana() {
        double SumEntradas = dbHelper.Transacciones.SumatoriaEntradas(fecha_inicial,fecha_final);
        double SumSalidas = dbHelper.Transacciones.SumatoriaSalidas(fecha_inicial,fecha_final);

        tvBalanceHistorial = (TextView)findViewById(R.id.tvBalanceHistorial);
        tvBalanceHistorial.setText(Util.PriceFormat(SumEntradas - SumSalidas));
        if((SumEntradas - SumSalidas) < 0)
            tvBalanceHistorial.setTextColor(Color.parseColor("#ffff4444"));
        else
            tvBalanceHistorial.setTextColor(Color.parseColor("#ff99cc00"));

        lvTransaccionesHistorial = (ListView)findViewById(R.id.lvTransaccionesHistorial);
        listaTransacciones = dbHelper.Transacciones.Obten(fecha_inicial,fecha_final);
        adapter = new TransaccionAdapter(VentanaHistorial.this,listaTransacciones, true);
        adapter.setCallback(this);
        lvTransaccionesHistorial.setAdapter(adapter);
    }
}
