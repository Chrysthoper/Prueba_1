package course.example.pruebas_1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.ViewPager;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
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
import course.example.pruebas_1.Ventanas.Calendario.VentanaCalendario;
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
    Calendar c,cFin,cTransaccion = Calendar.getInstance();
    ViewPager pager = null;

    private SlidingUpPanelLayout mLayout;
    GridView lvCuentasGridPager;
    CuentasGridAdapter adapterCuentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_principal);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = inflater.inflate(R.layout.activity_splash, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        builder.show();

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
            c = Calendar.getInstance();
            cTransaccion = Calendar.getInstance();
            cFin = Calendar.getInstance();
            cFin.add(Calendar.DAY_OF_MONTH, 1);
        }

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
        mLayout.setDragView(R.id.lyControlBar);

        lyTransferenciaPrincipal = (LinearLayout)findViewById(R.id.lyTransferenciaPrincipal);
        lyTransferenciaPrincipal.setOnClickListener(btnTransacciones);

        DialogoFechaPrincipal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvFechaPrincipalDia.setText(Integer.toString(dayOfMonth));
                tvFechaPrincipalMes.setText(Util.getMonthForInt(monthOfYear).substring(0, 3).toUpperCase());
                cTransaccion.set(year, monthOfYear, dayOfMonth);
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
                (mLayout.getPanelState() == PanelState.COLLAPSED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.EXPANDED);
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
        if(id == R.id.categorias_settings)
            startActivity(new Intent(getApplicationContext(), VentanaCategorias.class));
        else if(id == R.id.calendario_settings)
        {
            Intent i = new Intent(getApplicationContext(), VentanaCalendario.class);
            startActivityForResult(i,2);
        }
        else if(id == R.id.backup_settings)
        {
            this.exportDB();
        }
        else if(id == R.id.restore_settings)
        {
            this.importDB();
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
            String fecha = Util.FechaToFormat(cTransaccion.getTime());
            final Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            intent.putExtra("FECHA", fecha);
            intent.putExtra("TIPO", v.getId());
            intent.putExtra("OP", 'A');
            intent.putExtra("CUENTA_ID", 2);//Cuentas.get(which).id);
            startActivityForResult(intent, 1);
        }
    };

    //endregion

    private void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
                String currentDBPath = "//data//" + "course.example.pruebas_1"
                        + "//databases//" + "MiChochinito.db";
                String backupDBPath = "MiChochinitoBKUP.db"; // From SD directory.
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getApplicationContext(), "Import Successful!",
                        Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Import Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    private void exportDB() {
        try {
            File sd = Environment.getRootDirectory();
            File data = Environment.getDataDirectory();

            String currentDBPath = "//data//" + "course.example.pruebas_1"
                    + "//databases//" + "MiChochinito.db";
            String backupDBPath = "MiChochinitoBKUP.db";
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            Toast.makeText(getApplicationContext(), "Backup Successful!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Backup Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }

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
                if(rango)
                {
                    Date fecha2 = new Date(data.getLongExtra("FECHA2",-1));
                    cFin.setTime(fecha2);
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
        outState.putLong("FECHA1", c.getTimeInMillis());
        outState.putLong("FECHA2", cFin.getTimeInMillis());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        c.setTime(new Date(savedState.getLong("FECHA1")));
        cFin.setTime(new Date(savedState.getLong("FECHA2")));
    }

    //endregion

}
