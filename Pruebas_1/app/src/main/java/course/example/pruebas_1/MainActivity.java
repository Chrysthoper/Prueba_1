package course.example.pruebas_1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.ViewPager;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import course.example.pruebas_1.Adapters.CuentasAdapter;
import course.example.pruebas_1.Adapters.CuentasGridAdapter;
import course.example.pruebas_1.Adapters.TransaccionesFragmentPagerAdapter;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter1;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter2;
import course.example.pruebas_1.Interfaces.IAdaptersCallerVentana;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.Ventanas.Categorias.VentanaCategorias;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Ventanas.Historial.VentanaHistorial;
import course.example.pruebas_1.Ventanas.Transacciones.TutorialActivity;


public class MainActivity extends ActionBarActivity implements IAdaptersCallerVentana {

    Button btnTransaccionSalida,btnTransaccionEntrada;
    LinearLayout lyFechaPrincipal,lyTransferenciaPrincipal;
    TextView tvFechaPrincipalDia,tvFechaPrincipalMes,tvBalancePrincipal;
    DatePickerDialog DialogoFechaPrincipal;
    DBHelper dbHelper;
    ArrayList<Transaccion> listaTransacciones;
    ArrayList<Categoria> listaCategorias;
    ArrayList<Cuenta> listaCuentas;
    private TransaccionesPagerAdapter1 pagerAdapter1;
    private TransaccionesPagerAdapter2 pagerAdapter2;
    private TransaccionesFragmentPagerAdapter adapterFrag;
    Calendar c,cFin,cTransaccion;
    ViewPager pager = null;

    private SlidingUpPanelLayout mLayout;
    private static final String TAG = "DemoActivity";
    GridView lvCuentasGridPager;
    CuentasGridAdapter adapterCuentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbHelper = new DBHelper(getApplicationContext());

        c = Calendar.getInstance();
        cTransaccion = Calendar.getInstance();
        cFin = Calendar.getInstance();
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
        tvFechaPrincipalMes.setText(Util.getMonthForInt(mes).substring(0, 3).toUpperCase());

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

        listaCuentas = dbHelper.Cuentas.Obten();
        lvCuentasGridPager = (GridView) findViewById(R.id.lvCuentasGridPager);
        this.adapterCuentas = new CuentasGridAdapter(getApplicationContext(),this.listaCuentas,0);
        lvCuentasGridPager.setAdapter(adapterCuentas);
        adapterCuentas.notifyDataSetChanged();

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setPanelState(PanelState.EXPANDED);
        mLayout.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");

            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");

            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "onPanelHidden");
            }
        });
        mLayout.setDragView(R.id.lyControlBar);

        lyTransferenciaPrincipal = (LinearLayout)findViewById(R.id.lyTransferenciaPrincipal);
        lyTransferenciaPrincipal.setOnClickListener(btnTransacciones);

        DialogoFechaPrincipal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvFechaPrincipalDia.setText(Integer.toString(dayOfMonth));
                tvFechaPrincipalMes.setText(Util.getMonthForInt(monthOfYear).substring(0, 3).toUpperCase());
                cTransaccion.set(year, monthOfYear,dayOfMonth);
                //ActualizaVentana();
                //ActualizaAdapter();
            }

        },cTransaccion.get(Calendar.YEAR), cTransaccion.get(Calendar.MONTH), cTransaccion.get(Calendar.DAY_OF_MONTH));
    }

    //region ActualizaVentana

    public void ActualizaAdapter(){

        String fechaIni = Util.FechaToFormat(c.getTime());
        String fechaFin = Util.FechaToFormat(cFin.getTime());

        listaTransacciones = dbHelper.Transacciones.Obten(fechaIni,fechaFin);
        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fechaIni, fechaFin);
        pagerAdapter1.ActualizaGrid(listaTransacciones);
        pagerAdapter2.ActualizaGrid(listaCategorias);
        adapterFrag.notifyDataSetChanged();

        listaCuentas = dbHelper.Cuentas.Obten();
        this.adapterCuentas = new CuentasGridAdapter(getApplicationContext(),this.listaCuentas,0);
        lvCuentasGridPager.setAdapter(adapterCuentas);
        adapterCuentas.notifyDataSetChanged();

    }

    public void ActualizaVentana(){

        String fechaIni = Util.FechaToFormat(c.getTime());
        String fechaFin = Util.FechaToFormat(cFin.getTime());

        double SumEntradas = dbHelper.Transacciones.SumatoriaEntradas(fechaIni,fechaFin);
        double SumSalidas = dbHelper.Transacciones.SumatoriaSalidas(fechaIni,fechaFin);

        btnTransaccionEntrada.setText(Util.PriceFormat(SumEntradas));
        btnTransaccionSalida.setText(Util.PriceFormat(SumSalidas));

        /*
        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fechaIni, fechaFin);
        pagerAdapter2.ActualizaGrid(listaCategorias);
        adapterFrag.notifyDataSetChanged();
        */

        listaCuentas = dbHelper.Cuentas.Obten();
        this.adapterCuentas = new CuentasGridAdapter(getApplicationContext(),this.listaCuentas,0);
        lvCuentasGridPager.setAdapter(adapterCuentas);
        adapterCuentas.notifyDataSetChanged();

        tvBalancePrincipal.setText(Util.PriceFormat(SumEntradas - SumSalidas));
        if((SumEntradas - SumSalidas) < 0)
            tvBalancePrincipal.setTextColor(Color.parseColor("#ffff4444"));
        else
            tvBalancePrincipal.setTextColor(Color.parseColor("#ff99cc00"));

    }

    //endregion

    @Override
    public void onBackPressed() {
        // Return to previous page when we press back button
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else if (this.pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    //region Listeners

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
            final Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            final ArrayList<Cuenta> Cuentas = dbHelper.Cuentas.Obten();
            String fecha = Util.FechaToFormat(cTransaccion.getTime());

            intent.putExtra("FECHA", fecha);
            intent.putExtra("OP", v.getId());

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
            builderSingle.setTitle("Selecciona una Cuenta");
            final CuentasAdapter adapter = new CuentasAdapter(MainActivity.this,Cuentas);
            builderSingle.setNegativeButton(
                    "Cancelar",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builderSingle.setAdapter(
                    adapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent.putExtra("CUENTA_ID", Cuentas.get(which).id);
                            startActivityForResult(intent, 1);
                        }
                    });
            builderSingle.show();
        }
    };

    //endregion

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
            if(resultCode == 1)
            {
                boolean rango = data.getBooleanExtra("RANGO", false);
                Date fecha1 = new Date(data.getLongExtra("FECHA1",-1));
                c.setTime(fecha1);
                //c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                if(rango)
                {
                    Date fecha2 = new Date(data.getLongExtra("FECHA2",-1));
                    cFin.setTime(fecha2);
                    //cFin.set(cFin.get(Calendar.YEAR), cFin.get(Calendar.MONTH), cFin.get(Calendar.DAY_OF_MONTH));
                    cFin.add(Calendar.DAY_OF_MONTH, 1);
                }
                else
                {
                    cFin.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    cFin.add(Calendar.DAY_OF_MONTH, 1);
                }
                ActualizaVentana();
                ActualizaAdapter();
            }
        }
    }

    //region Window State

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
    }

    //endregion

}
