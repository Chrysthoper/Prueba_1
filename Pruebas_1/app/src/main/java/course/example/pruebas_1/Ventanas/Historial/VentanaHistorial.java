package course.example.pruebas_1.Ventanas.Historial;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import course.example.pruebas_1.Adapters.TransaccionAdapter;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class VentanaHistorial extends ActionBarActivity implements IAdaptersCallerGrid {

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
    public void ActualizaGrid(ArrayList<Transaccion> listaTransacciones) {
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
