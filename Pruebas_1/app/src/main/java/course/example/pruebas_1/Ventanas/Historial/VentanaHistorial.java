package course.example.pruebas_1.Ventanas.Historial;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import course.example.pruebas_1.Adapters.TransaccionAdapter;
import course.example.pruebas_1.Adapters.TransaccionesFragmentPagerAdapter;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter1;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter2;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.Interfaces.IAdaptersCallerVentana;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class VentanaHistorial extends ActionBarActivity implements IAdaptersCallerVentana {

    TextView tvBalanceHistorial;
    DBHelper dbHelper;
    String fecha_inicial;
    String fecha_final;
    ArrayList<Transaccion> listaTransacciones;
    ArrayList<Categoria> listaCategorias;
    private TransaccionesPagerAdapter1 pagerAdapter1;
    private TransaccionesPagerAdapter2 pagerAdapter2;
    private TransaccionesFragmentPagerAdapter adapterFrag;
    Calendar c;

    ViewPager pager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial2);

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

        listaTransacciones = dbHelper.Transacciones.Obten(fecha_inicial,fecha_final);
        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fecha_inicial,fecha_final);
        this.pager = (ViewPager) this.findViewById(R.id.pager);
        // Create an adapter with the fragments we show on the ViewPager
        adapterFrag = new TransaccionesFragmentPagerAdapter(
                getSupportFragmentManager());
        pagerAdapter1 = TransaccionesPagerAdapter1.newInstance(listaTransacciones,true);
        pagerAdapter1.setCallback(this);
        pagerAdapter2 = TransaccionesPagerAdapter2.newInstance(listaCategorias);
        adapterFrag.addFragment(pagerAdapter1);
        adapterFrag.addFragment(pagerAdapter2);
        this.pager.setAdapter(adapterFrag);
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

        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fecha_inicial, fecha_final);
        pagerAdapter2.ActualizaGrid(listaCategorias);
        adapterFrag.notifyDataSetChanged();
    }

    public void ActualizaAdapter(){
        listaTransacciones = dbHelper.Transacciones.Obten(fecha_inicial,fecha_final);
        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fecha_inicial, fecha_final);
        pagerAdapter1.ActualizaGrid(listaTransacciones);
        pagerAdapter2.ActualizaGrid(listaCategorias);
        adapterFrag.notifyDataSetChanged();
    }


}
