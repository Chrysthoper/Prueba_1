package course.example.pruebas_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.ViewPager;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import course.example.pruebas_1.Adapters.TransaccionAdapter;
import course.example.pruebas_1.Adapters.TransaccionesFragmentPagerAdapter;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter1;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter2;
import course.example.pruebas_1.Interfaces.IAdaptersCallerVentana;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Ventanas.Categorias.VentanaCategorias;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Ventanas.Historial.VentanaHistorial;
import course.example.pruebas_1.Ventanas.Transacciones.TutorialActivity;


public class MainActivity extends ActionBarActivity implements IAdaptersCallerVentana {

    Button btnTransaccionSalida,btnTransaccionEntrada;
    LinearLayout lyFechaPrincipal;
    TextView tvFechaPrincipalDia,tvFechaPrincipalMes,tvBalancePrincipal;
    DatePickerDialog DialogoFechaPrincipal;
    ListView lvTransaccionesPrincipal;
    TransaccionAdapter adapter;
    DBHelper dbHelper;
    ArrayList<Transaccion> listaTransacciones;
    ArrayList<Categoria> listaCategorias;
    private TransaccionesPagerAdapter1 pagerAdapter1;
    private TransaccionesPagerAdapter2 pagerAdapter2;
    private TransaccionesFragmentPagerAdapter adapterFrag;
    Calendar c;

    /**
     * The pager widget, which handles animation and allows swiping horizontally
     * to access previous and next pages.
     */
    ViewPager pager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbHelper = new DBHelper(getApplicationContext());

        c = Calendar.getInstance();
        Calendar cFin = Calendar.getInstance();
        cFin.add(Calendar.DAY_OF_MONTH, 1);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        String fechaIni = Util.FechaToFormat(c.getTime());
        String fechaFin = Util.FechaToFormat(cFin.getTime());

        double SumEntradas = dbHelper.Transacciones.SumatoriaEntradas(fechaIni,fechaFin);
        double SumSalidas = dbHelper.Transacciones.SumatoriaSalidas(fechaIni,fechaFin);

        btnTransaccionEntrada = (Button)findViewById(R.id.btnTransacionEntrada);
        btnTransaccionEntrada.setOnClickListener(btnTransacciones);
        btnTransaccionEntrada.setText(Util.PriceFormat(SumEntradas));
        btnTransaccionSalida = (Button)findViewById(R.id.btnTransacionSalida);
        btnTransaccionSalida.setOnClickListener(btnTransacciones);
        btnTransaccionSalida.setText(Util.PriceFormat(SumSalidas));
        lyFechaPrincipal = (LinearLayout)findViewById(R.id.lyFechaPrincipal);
        lyFechaPrincipal.setOnClickListener(clickFechaPrincipal);

        tvBalancePrincipal = (TextView)findViewById(R.id.tvBalancePrinncipal);
        tvBalancePrincipal.setText(Util.PriceFormat(SumEntradas - SumSalidas));
        if((SumEntradas - SumSalidas) < 0)
            tvBalancePrincipal.setTextColor(Color.parseColor("#ffff4444"));
        else
            tvBalancePrincipal.setTextColor(Color.parseColor("#ff99cc00"));

        tvFechaPrincipalDia = (TextView)findViewById(R.id.tvFechaPrincipalDia);
        tvFechaPrincipalDia.setText(Integer.toString(dia));
        tvFechaPrincipalMes = (TextView)findViewById(R.id.tvFechaPrincipalMes);
        tvFechaPrincipalMes.setText(getMonthForInt(mes).substring(0, 3).toUpperCase());

        listaTransacciones = dbHelper.Transacciones.Obten(fechaIni,fechaFin);
        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fechaIni,fechaFin);
        this.pager = (ViewPager) this.findViewById(R.id.pager);
        // Create an adapter with the fragments we show on the ViewPager
        adapterFrag = new TransaccionesFragmentPagerAdapter(
                getSupportFragmentManager());
        pagerAdapter1 = TransaccionesPagerAdapter1.newInstance(listaTransacciones, false);
        pagerAdapter1.setCallback(this);
        pagerAdapter2 = TransaccionesPagerAdapter2.newInstance(listaCategorias);
        adapterFrag.addFragment(pagerAdapter1);
        adapterFrag.addFragment(pagerAdapter2);
        this.pager.setAdapter(adapterFrag);
        /*
        lvTransaccionesPrincipal = (ListView)findViewById(R.id.lvTransaccionesPrincipal);
        adapter = new TransaccionAdapter(MainActivity.this,listaTransacciones, false);
        adapter.setCallback(this);
        lvTransaccionesPrincipal.setAdapter(adapter);
        lvTransaccionesPrincipal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            final Transaccion trans = listaTransacciones.get(i);
            new AlertDialog.Builder(MainActivity.this)
                .setMessage("¿Seguro que desea borrar la transacción?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(dbHelper.Transacciones.Elimina(trans.id))
                        {
                            ActualizaVentana();
                            Toast.makeText(MainActivity.this, "Se elimino la transaccion", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
                return true;
            }
        });
        */
        DialogoFechaPrincipal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvFechaPrincipalDia.setText(Integer.toString(dayOfMonth));
                tvFechaPrincipalMes.setText(getMonthForInt(monthOfYear).substring(0, 3).toUpperCase());
                c.set(year,monthOfYear,dayOfMonth);
                ActualizaVentana();
                ActualizaAdapter();
            }

        },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onBackPressed() {
        // Return to previous page when we press back button
        if (this.pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);
    }

    public void ActualizaAdapter(){
        Calendar cFin = Calendar.getInstance();
        cFin.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        cFin.add(Calendar.DAY_OF_MONTH, 1);
        String fechaIni = Util.FechaToFormat(c.getTime());
        String fechaFin = Util.FechaToFormat(cFin.getTime());

        listaTransacciones = dbHelper.Transacciones.Obten(fechaIni,fechaFin);
        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fechaIni, fechaFin);
        pagerAdapter1.ActualizaGrid(listaTransacciones);
        pagerAdapter2.ActualizaGrid(listaCategorias);
        adapterFrag.notifyDataSetChanged();

    }

    public void ActualizaVentana(){
        Calendar cFin = Calendar.getInstance();
        cFin.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        cFin.add(Calendar.DAY_OF_MONTH, 1);
        String fechaIni = Util.FechaToFormat(c.getTime());
        String fechaFin = Util.FechaToFormat(cFin.getTime());

        double SumEntradas = dbHelper.Transacciones.SumatoriaEntradas(fechaIni,fechaFin);
        double SumSalidas = dbHelper.Transacciones.SumatoriaSalidas(fechaIni,fechaFin);

        btnTransaccionEntrada.setText(Util.PriceFormat(SumEntradas));
        btnTransaccionSalida.setText(Util.PriceFormat(SumSalidas));

        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fechaIni, fechaFin);
        pagerAdapter2.ActualizaGrid(listaCategorias);
        adapterFrag.notifyDataSetChanged();
        /*
        listaTransacciones = dbHelper.Transacciones.Obten(fechaIni,fechaFin);
        pagerAdapter1.ActualizaGrid(listaTransacciones);
        adapterFrag.notifyDataSetChanged();
        */
        /*
        adapter = new TransaccionAdapter(MainActivity.this,listaTransacciones, false);
        adapter.setCallback(this);
        lvTransaccionesPrincipal.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        */
        tvBalancePrincipal.setText(Util.PriceFormat(SumEntradas - SumSalidas));
        if((SumEntradas - SumSalidas) < 0)
            tvBalancePrincipal.setTextColor(Color.parseColor("#ffff4444"));
        else
            tvBalancePrincipal.setTextColor(Color.parseColor("#ff99cc00"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.categorias_settings)
            startActivity(new Intent(getApplicationContext(), VentanaCategorias.class));
        else if(id == R.id.historial_settings)
        {
            Intent i = new Intent(getApplicationContext(), VentanaHistorial.class);
            i.putExtra("FECHA_INICIAL", "2015-10-01");
            i.putExtra("FECHA_FINAL", "2015-11-30");
            startActivityForResult(i,2);
        }

        return super.onOptionsItemSelected(item);
    }

    LinearLayout.OnClickListener clickFechaPrincipal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogoFechaPrincipal.show();
        }
    };

    Button.OnClickListener btnTransacciones = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String msj = "";
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            String fecha = Util.FechaToFormat(c.getTime());
            intent.putExtra("FECHA", fecha);
            switch(v.getId()) {
                case R.id.btnTransacionEntrada:
                    intent.putExtra("OP", R.id.btnTransacionEntrada);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.btnTransacionSalida:
                    intent.putExtra("OP", R.id.btnTransacionSalida);
                    startActivityForResult(intent, 1);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1)
        {
            if(resultCode != 1)
                Toast.makeText(MainActivity.this, "No se genero ninguna transaccion", Toast.LENGTH_SHORT).show();
            else
            {
                ActualizaVentana();
                ActualizaAdapter();
            }
        }
        if (requestCode == 2)
        {
            ActualizaVentana();
            ActualizaAdapter();
        }
    }
}
