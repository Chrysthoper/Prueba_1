package course.example.pruebas_1.Ventanas.Historial;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import course.example.pruebas_1.Adapters.TransaccionAdapter;
import course.example.pruebas_1.Adapters.TransaccionesFragmentPagerAdapter;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter1;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter2;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.Interfaces.IAdaptersCallerVentana;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class VentanaHistorial extends FragmentActivity {

    TextView tvBalanceHistorial;
    DBHelper dbHelper;
    String fecha_inicial;
    String fecha_final;
    ArrayList<Transaccion> listaTransacciones;
    ArrayList<Categoria> listaCategorias;
    ArrayList<Cuenta> listaCuentas;
    private TransaccionesPagerAdapter1 pagerAdapter1;
    private TransaccionesPagerAdapter2 pagerAdapter2;
    private TransaccionesFragmentPagerAdapter adapterFrag;
    Calendar c;

    ViewPager pager = null;

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;

    private Date fecha1 = new Date(0);
    private Date fecha2 = new Date(0);
    boolean RangoCompleto = false;

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            caldroidFragment.setBackgroundResourceForDate(R.color.caldroid_sky_blue,
                    blueDate);
            caldroidFragment.setBackgroundResourceForDate(R.color.caldroid_gray,
                    greenDate);
            caldroidFragment.setTextColorForDate(R.color.caldroid_white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.caldroid_white, greenDate);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_historial2);

        dbHelper = new DBHelper(getApplicationContext());

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        caldroidFragment = new CaldroidFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                if(fecha1.equals(new Date(0))) {
                    fecha1 = date;
                    caldroidFragment.clearSelectedDates();
                    caldroidFragment.setSelectedDate(fecha1);
                    RangoCompleto = false;
                }
                else
                {
                    if(fecha1.after(date) || RangoCompleto)
                    {
                        fecha1 = date;
                        caldroidFragment.clearSelectedDates();
                        caldroidFragment.setSelectedDate(fecha1);
                        RangoCompleto = false;
                    }
                    else
                    {
                        fecha2 = date;
                        caldroidFragment.setSelectedDates(fecha1, fecha2);
                        RangoCompleto = true;
                    }
                }
                caldroidFragment.refreshView();
                Toast.makeText(getApplicationContext(), "FECHA 1:" + formatter.format(fecha1) + "FECHA 2:" + formatter.format(fecha2),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                Toast.makeText(getApplicationContext(),
                        "Caldroid view is created", Toast.LENGTH_SHORT)
                        .show();
            }

        };
        caldroidFragment.setCaldroidListener(listener);

        final Button btnConfirmarFechaPrincipal = (Button) findViewById(R.id.btnConfirmarFechaPrincipal);
        btnConfirmarFechaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendario = new Intent();
                calendario.putExtra("FECHA1", fecha1.getTime());
                calendario.putExtra("FECHA2", fecha2.getTime());
                calendario.putExtra("RANGO", RangoCompleto);
                setResult(1, calendario);
                finish();
            }
        });

        final Button btnCancelarFechaPrincipal = (Button) findViewById(R.id.btnCancelarFechaPrincipal);
        btnCancelarFechaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Button btnDiaFechaPrincipal = (Button) findViewById(R.id.btnDiaFechaPrincipal);

        btnDiaFechaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                caldroidFragment.clearSelectedDates();
                caldroidFragment.setSelectedDate(c.getTime());
                caldroidFragment.refreshView();
                RangoCompleto = false;
            }
        });

        final Button btnSemanaFechaPrincipal = (Button) findViewById(R.id.btnSemanaFechaPrincipal);
        btnSemanaFechaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,1);
                Calendar cFin = Calendar.getInstance();
                cFin.set(Calendar.DAY_OF_WEEK, 1);
                cFin.add(Calendar.DAY_OF_WEEK, 7);
                cFin.add(Calendar.DAY_OF_WEEK, -1);
                caldroidFragment.clearSelectedDates();
                caldroidFragment.setSelectedDates(c.getTime(), cFin.getTime());
                caldroidFragment.refreshView();
                RangoCompleto = false;
            }
        });

        final Button btnMesFechaPrincipal = (Button) findViewById(R.id.btnMesFechaPrincipal);
        btnMesFechaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH,1);
                Calendar cFin = Calendar.getInstance();
                cFin.set(Calendar.DAY_OF_MONTH, 1);
                cFin.add(Calendar.MONTH, 1);
                cFin.add(Calendar.DAY_OF_MONTH, -1);
                caldroidFragment.clearSelectedDates();
                caldroidFragment.setSelectedDates(c.getTime(), cFin.getTime());
                caldroidFragment.refreshView();
                RangoCompleto = false;
            }
        });

        final Button btnAnoFechaPrincipal = (Button) findViewById(R.id.btnAnoFechaPrincipal);
        btnAnoFechaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_YEAR,1);
                Calendar cFin = Calendar.getInstance();
                cFin.set(Calendar.DAY_OF_YEAR,1);
                cFin.add(Calendar.YEAR, 1);
                cFin.add(Calendar.DAY_OF_YEAR, -1);
                caldroidFragment.clearSelectedDates();
                caldroidFragment.setSelectedDates(c.getTime(), cFin.getTime());
                caldroidFragment.refreshView();
                RangoCompleto = false;
            }
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
    }
}
