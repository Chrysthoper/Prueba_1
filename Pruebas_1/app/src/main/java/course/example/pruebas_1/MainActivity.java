package course.example.pruebas_1;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.ViewPager;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import course.example.pruebas_1.Adapters.CuentasGridAdapter;
import course.example.pruebas_1.Adapters.CuentasListAdapter;
import course.example.pruebas_1.Adapters.ReceptorBroadcast;
import course.example.pruebas_1.Adapters.TransaccionAdapter;
import course.example.pruebas_1.Adapters.TransaccionProgramaListAdapter;
import course.example.pruebas_1.Adapters.TransaccionesFragmentPagerAdapter;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter1;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter2;
import course.example.pruebas_1.DB.RequestTasks;
import course.example.pruebas_1.Interfaces.IAdaptersCallerVentana;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Credenciales;
import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.Negocio.TransaccionProgramada;
import course.example.pruebas_1.Ventanas.Categorias.VentanaCategorias;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Ventanas.Calendario.VentanaCalendario;
import course.example.pruebas_1.Ventanas.Cuentas.VentanaCuentas;
import course.example.pruebas_1.Ventanas.Historial.VentanaHistorial;
import course.example.pruebas_1.Ventanas.Transacciones.TutorialActivity;

public class MainActivity extends ActionBarActivity implements IAdaptersCallerVentana {

    Button btnSalida,btnEntrada;

    LinearLayout lyFechaPrincipal,lyHistorial,lyTraspaso;
    TextView tvFechaPrincipalDia,tvFechaPrincipalMes,tvBalancePrinncipal, tvEntradasPrincipal,tvSalidasPrincipal,tvBalancePrincipal;
    ListView lvTransaccionesProgramadasPrincipal, list_nav_view;
    DatePickerDialog DialogoFechaPrincipal;
    DBHelper dbHelper;
    ArrayList<Transaccion> listaTransacciones;
    ArrayList<TransaccionProgramada> listaTransaccionesProgramadas;
    ArrayList<Categoria> listaCategorias;
    ArrayList<Cuenta> listaCuentas;
    private TransaccionesPagerAdapter1 pagerAdapter1;
    private TransaccionesPagerAdapter2 pagerAdapter2;
    private TransaccionesFragmentPagerAdapter adapterFrag;
    Calendar c,cFin,cTransaccion = Calendar.getInstance();
    ViewPager pager = null;

    private SlidingUpPanelLayout mLayout;
    TwoWayView lvCuentasListaPrincipal;
    CuentasGridAdapter adapterCuentas;
    CuentasListAdapter adapterCuentasLista;
    TransaccionProgramaListAdapter adapterTransProgramadas;

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.ventana_principal);
        setContentView(R.layout.activity_main_navview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_nav_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        navView = (NavigationView)findViewById(R.id.navview);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = inflater.inflate(R.layout.activity_splash, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);

        final AlertDialog dlg = builder.create();
        dlg.show();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dlg.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000); // after 2 second (or 2000 miliseconds), the task will be active.

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

        btnEntrada = (Button)findViewById(R.id.btnEntrada);
        btnEntrada.setOnClickListener(btnTransacciones);
        btnEntrada.setText(Util.PriceFormat(SumEntradas));
        btnSalida = (Button)findViewById(R.id.btnSalida);
        btnSalida.setOnClickListener(btnTransacciones);
        btnSalida.setText(Util.PriceFormat(SumSalidas));
        lyFechaPrincipal = (LinearLayout)findViewById(R.id.lyFechaPrincipal);
        lyFechaPrincipal.setOnClickListener(clickFechaPrincipal);

        tvBalancePrinncipal = (TextView)findViewById(R.id.tvBalancePrinncipal);
        tvBalancePrincipal = (TextView)findViewById(R.id.tvBalancePrincipal);
        tvBalancePrincipal.setText(Util.PriceFormat(SumEntradas - SumSalidas));
        tvEntradasPrincipal = (TextView)findViewById(R.id.tvEntradasPrincipal);
        tvEntradasPrincipal.setText(Util.PriceFormat(SumEntradas));
        tvSalidasPrincipal = (TextView)findViewById(R.id.tvSalidasPrincipal);
        tvSalidasPrincipal.setText(Util.PriceFormat(SumSalidas));
        tvBalancePrinncipal.setText(Util.PriceFormat(SumEntradas - SumSalidas));
        if((SumEntradas - SumSalidas) < 0)
            tvBalancePrinncipal.setTextColor(Color.parseColor("#ffff4444"));
        else
            tvBalancePrinncipal.setTextColor(Color.parseColor("#ff99cc00"));

        tvFechaPrincipalDia = (TextView)findViewById(R.id.tvFechaPrincipalDia);
        tvFechaPrincipalDia.setText(Integer.toString(dia));
        tvFechaPrincipalMes = (TextView)findViewById(R.id.tvFechaPrincipalMes);
        tvFechaPrincipalMes.setText(Util.getMonthForInt(mes).toUpperCase());

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
        lvCuentasListaPrincipal = (TwoWayView) findViewById(R.id.lvCuentasListaPrincipal);
        this.adapterCuentas = new CuentasGridAdapter(getApplicationContext(),this.listaCuentas,0);
        lvCuentasListaPrincipal.setAdapter(adapterCuentas);
        adapterCuentas.notifyDataSetChanged();

        listaCuentas = dbHelper.Cuentas.Obten();
        list_nav_view = (ListView)findViewById(R.id.lst_menu_items);
        this.adapterCuentasLista = new CuentasListAdapter(getApplicationContext(),this.listaCuentas);
        list_nav_view.setAdapter(adapterCuentasLista);
        adapterCuentasLista.notifyDataSetChanged();

        listaTransaccionesProgramadas = dbHelper.Transacciones_Programadas.Obten();
        lvTransaccionesProgramadasPrincipal = (ListView) findViewById(R.id.lvTransaccionesProgramadasPrincipal);
        this.adapterTransProgramadas = new TransaccionProgramaListAdapter(getApplicationContext(),this.listaTransaccionesProgramadas);
        lvTransaccionesProgramadasPrincipal.setAdapter(adapterTransProgramadas);
        adapterTransProgramadas.notifyDataSetChanged();

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setPanelState(PanelState.COLLAPSED);
        mLayout.setDragView(R.id.lyControlBar);

        lyHistorial = (LinearLayout)findViewById(R.id.lyHistorial);
        lyHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VentanaCalendario.class);
                startActivityForResult(i,2);
            }
        });

        lyTraspaso = (LinearLayout)findViewById(R.id.lyTraspaso);
        lyTraspaso.setOnClickListener(btnTransacciones);

        DialogoFechaPrincipal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvFechaPrincipalDia.setText(Integer.toString(dayOfMonth));
                tvFechaPrincipalMes.setText(Util.getMonthForInt(monthOfYear).toUpperCase());
                cTransaccion.set(year, monthOfYear, dayOfMonth);
                c.set(year, monthOfYear, dayOfMonth);
                cFin.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                cFin.add(Calendar.DAY_OF_MONTH, 1);
                ActualizaVentana();
                ActualizaAdapter();
            }

        },cTransaccion.get(Calendar.YEAR), cTransaccion.get(Calendar.MONTH), cTransaccion.get(Calendar.DAY_OF_MONTH));
    }

    //region ActualizaVentana

    public void ActualizaAdapter(){

        String fechaIni = Util.FechaToFormat(c.getTime());
        String fechaFin = Util.FechaToFormat(cFin.getTime());

        listaTransacciones = dbHelper.Transacciones.Obten(fechaIni, fechaFin);
        pagerAdapter1.ActualizaGrid(listaTransacciones);
    }

    public void ActualizaVentana(){

        String fechaIni = Util.FechaToFormat(c.getTime());
        String fechaFin = Util.FechaToFormat(cFin.getTime());

        double SumEntradas = dbHelper.Transacciones.SumatoriaEntradas(fechaIni,fechaFin);
        double SumSalidas = dbHelper.Transacciones.SumatoriaSalidas(fechaIni,fechaFin);

        btnEntrada.setText(Util.PriceFormat(SumEntradas));
        btnSalida.setText(Util.PriceFormat(SumSalidas));

        listaCuentas = dbHelper.Cuentas.Obten();
        lvCuentasListaPrincipal = (TwoWayView) findViewById(R.id.lvCuentasListaPrincipal);
        this.adapterCuentas = new CuentasGridAdapter(getApplicationContext(),this.listaCuentas,0);
        lvCuentasListaPrincipal.setAdapter(adapterCuentas);
        adapterCuentas.notifyDataSetChanged();

        listaCuentas = dbHelper.Cuentas.Obten();
        list_nav_view = (ListView)findViewById(R.id.lst_menu_items);
        this.adapterCuentasLista = new CuentasListAdapter(getApplicationContext(),this.listaCuentas);
        list_nav_view.setAdapter(adapterCuentasLista);
        adapterCuentasLista.notifyDataSetChanged();

        tvBalancePrincipal.setText(Util.PriceFormat(SumEntradas - SumSalidas));
        tvEntradasPrincipal.setText(Util.PriceFormat(SumEntradas));
        tvSalidasPrincipal.setText(Util.PriceFormat(SumSalidas));
        tvBalancePrinncipal.setText(Util.PriceFormat(SumEntradas - SumSalidas));
        if((SumEntradas - SumSalidas) < 0)
            tvBalancePrinncipal.setTextColor(Color.parseColor("#ffff4444"));
        else
            tvBalancePrinncipal.setTextColor(Color.parseColor("#ff99cc00"));

        listaTransaccionesProgramadas = dbHelper.Transacciones_Programadas.Obten();
        lvTransaccionesProgramadasPrincipal = (ListView) findViewById(R.id.lvTransaccionesProgramadasPrincipal);
        this.adapterTransProgramadas = new TransaccionProgramaListAdapter(getApplicationContext(),this.listaTransaccionesProgramadas);
        lvTransaccionesProgramadasPrincipal.setAdapter(adapterTransProgramadas);
        adapterTransProgramadas.notifyDataSetChanged();

        listaCategorias = dbHelper.Categorias.ObtenTotalCategorias(fechaIni, fechaFin);
        pagerAdapter2.ActualizaGrid(listaCategorias);
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
        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        if(id == R.id.categorias_settings)
            startActivity(new Intent(getApplicationContext(), VentanaCategorias.class));
        else if(id == R.id.cuentas_settings)
            startActivityForResult(new Intent(getApplicationContext(), VentanaCuentas.class), 3);
        else if(id == R.id.backup_settings)
        {
            this.exportDB();
        }
        else if(id == R.id.restore_settings)
        {
            this.importDB();
            dbHelper = new DBHelper(getApplicationContext());
            ActualizaVentana();
            ActualizaAdapter();
        }
        else if (id == R.id.notification_settings)
        {
            Intent intent = new Intent(this, VentanaCategorias.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

            Notification noti = new Notification.Builder(this)
                    .setContentTitle("Mi Cochinito")
                    .setContentText("¿Ya realizaste esta transacción?").setSmallIcon(R.drawable.icon_app)
                    .setContentIntent(pIntent)
                    .addAction(R.mipmap.agregar_blanco, "Crear", pIntent)
                    .addAction(R.mipmap.compras, "Omitir", pIntent).build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, noti);
        }
        else if(id == R.id.acerca_settings)
        {
            Toast.makeText(getApplicationContext(), "Mi Cochinito v" + BuildConfig.VERSION_NAME, Toast.LENGTH_LONG).show();
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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ingresa un usuario");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RequestTasks request = new RequestTasks(MainActivity.this);
                    request.execute(new Credenciales(input.getText().toString(), "12345", "import"));
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "La restauración falló", Toast.LENGTH_SHORT).show();
        }
    }

    private void exportDB() {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ingresa un usuario");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RequestTasks request = new RequestTasks(MainActivity.this);
                    request.execute(new Credenciales(input.getText().toString(), "12345", "export"));
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "La restauración falló", Toast.LENGTH_SHORT).show();
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
                Transaccion trans = (Transaccion)data.getExtras().get("trans");
                Intent intent = new Intent(MainActivity.this, ReceptorBroadcast.class);
                intent.putExtra("FECHA", trans.fecha_alta);
                intent.putExtra("TIPO", trans.tipo_transaccion);
                intent.putExtra("OP", 'N');
                intent.putExtra("TRANS", trans);
                intent.putExtra("CUENTA_ID", trans.cuenta_prin_id);
                /*
                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

                // Build notification
                // Actions are just fake
                Notification noti = new Notification.Builder(this)
                        .setAutoCancel(true)
                        .setContentTitle(trans.descripcion)
                        .setContentText("¿Ya realizaste esta transacción?")
                        .setSmallIcon(Util.imagenesFull[trans.categoriaObj.resource])
                        .setContentIntent(pIntent).build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, noti);

                */


                /*
                NOTIFICACIONES!!!
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, 2);
                //Intent myIntent = new Intent(MainActivity.this, ReceptorBroadcast.class);
                pendingIntent = PendingIntent.getBroadcast(this, 1, intent,0);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);
                */
                ActualizaVentana();
                ActualizaAdapter();
            }
        }
        else if (requestCode == 2)
        {
            if(resultCode == 1)
            {
                final Intent intent = new Intent(MainActivity.this, VentanaHistorial.class);
                intent.putExtra("FECHA1", data.getLongExtra("FECHA1",-1));
                boolean rango = data.getBooleanExtra("RANGO", false);
                if(rango)
                {
                    Calendar cAux = Calendar.getInstance();
                    cAux.setTimeInMillis(data.getLongExtra("FECHA2",-1));
                    cAux.add(Calendar.DAY_OF_MONTH, 1);
                    intent.putExtra("FECHA2",cAux.getTimeInMillis());
                }
                else
                {
                    Calendar cAux = Calendar.getInstance();
                    cAux.setTimeInMillis(data.getLongExtra("FECHA1", -1));
                    cAux.add(Calendar.DAY_OF_MONTH, 1);
                    intent.putExtra("FECHA2", cAux.getTimeInMillis());
                }
                startActivity(intent);
            }
        }
        else if (requestCode == 3)
        {
            ActualizaVentana();
            ActualizaAdapter();
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
