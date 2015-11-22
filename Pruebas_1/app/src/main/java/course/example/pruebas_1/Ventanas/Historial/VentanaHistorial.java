package course.example.pruebas_1.Ventanas.Historial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import course.example.pruebas_1.Adapters.TransaccionesFragmentPagerAdapter;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter1;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter2;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Interfaces.IAdaptersCallerVentana;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class VentanaHistorial extends ActionBarActivity implements IAdaptersCallerVentana {

    ViewPager pager = null;
    private TransaccionesPagerAdapter1 pagerAdapter1;
    private TransaccionesPagerAdapter2 pagerAdapter2;
    private TransaccionesFragmentPagerAdapter adapterFrag;
    Calendar c,cFin;

    ArrayList<Transaccion> listaTransacciones;
    ArrayList<Categoria> listaCategorias;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_historial);

        dbHelper = new DBHelper(getApplicationContext());

        if(savedInstanceState != null)
        {
            c = Calendar.getInstance();
            c.setTime(new Date(savedInstanceState.getLong("FECHA1")));
            cFin = Calendar.getInstance();
            cFin.setTime(new Date(savedInstanceState.getLong("FECHA2")));
        }
        else
        {
            Intent intent = getIntent();
            c = Calendar.getInstance();
            c.setTime(new Date(intent.getLongExtra("FECHA1", 0)));
            cFin = Calendar.getInstance();
            cFin.setTime(new Date(intent.getLongExtra("FECHA2", 0)));
        }

        String fechaIni = Util.FechaToFormat(c.getTime());
        String fechaFin = Util.FechaToFormat(cFin.getTime());

        listaTransacciones = dbHelper.Transacciones.Obten(fechaIni, fechaFin);
        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fechaIni, fechaFin);

        this.pager = (ViewPager) this.findViewById(R.id.pager);
        adapterFrag = new TransaccionesFragmentPagerAdapter(
                getSupportFragmentManager());
        pagerAdapter1 = TransaccionesPagerAdapter1.newInstance(listaTransacciones);
        pagerAdapter1.setCallback(this);
        pagerAdapter2 = TransaccionesPagerAdapter2.newInstance(listaCategorias);
        adapterFrag.addFragment(pagerAdapter1);
        adapterFrag.addFragment(pagerAdapter2);
        this.pager.setAdapter(adapterFrag);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("FECHA1", c.getTimeInMillis());
        outState.putLong("FECHA2", c.getTimeInMillis());
    }

    @Override
    public void ActualizaVentana() {

    }

    public void ActualizaAdapter() {
        String fechaIni = Util.FechaToFormat(c.getTime());
        String fechaFin = Util.FechaToFormat(cFin.getTime());

        listaTransacciones = dbHelper.Transacciones.Obten(fechaIni,fechaFin);
        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fechaIni, fechaFin);
        pagerAdapter1.ActualizaGrid(listaTransacciones);
        pagerAdapter2.ActualizaGrid(listaCategorias);
        adapterFrag.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1)
        {
            if(resultCode != 1)
                Toast.makeText(VentanaHistorial.this, "No se genero ninguna transaccion", Toast.LENGTH_SHORT).show();
            else
            {
                ActualizaAdapter();
            }
        }
    }
}
