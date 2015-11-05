package course.example.pruebas_1.Ventanas.Calendario;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import course.example.pruebas_1.Adapters.TransaccionesFragmentPagerAdapter;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter1;
import course.example.pruebas_1.Adapters.TransaccionesPagerAdapter2;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;

public class VentanaCalendario extends FragmentActivity {

    DBHelper dbHelper;
    private CaldroidFragment caldroidFragment;

    private Date fecha1 = new Date(0);
    private Date fecha2 = new Date(0);
    boolean RangoCompleto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ventana_calendario);

        dbHelper = new DBHelper(getApplicationContext());

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
            }

            @Override
            public void onChangeMonth(int month, int year) {
            }

            @Override
            public void onLongClickDate(Date date, View view) {
            }

            @Override
            public void onCaldroidViewCreated() {
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
                caldroidFragment.moveToDate(c.getTime());
                caldroidFragment.refreshView();
                RangoCompleto = true;
                fecha1.setTime(c.getTimeInMillis());
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
                caldroidFragment.moveToDate(c.getTime());
                caldroidFragment.refreshView();
                RangoCompleto = true;
                fecha1.setTime(c.getTimeInMillis());
                fecha2.setTime(cFin.getTimeInMillis());
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
                caldroidFragment.moveToDate(c.getTime());
                caldroidFragment.refreshView();
                RangoCompleto = true;
                fecha1.setTime(c.getTimeInMillis());
                fecha2.setTime(cFin.getTimeInMillis());
            }
        });

        final Button btnAnoFechaPrincipal = (Button) findViewById(R.id.btnAnoFechaPrincipal);
        btnAnoFechaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_YEAR,1);
                Calendar cFin = Calendar.getInstance();
                cFin.set(Calendar.DAY_OF_YEAR, 1);
                cFin.add(Calendar.YEAR, 1);
                cFin.add(Calendar.DAY_OF_YEAR, -1);
                caldroidFragment.clearSelectedDates();
                caldroidFragment.setSelectedDates(c.getTime(), cFin.getTime());
                caldroidFragment.moveToDate(c.getTime());
                caldroidFragment.refreshView();
                RangoCompleto = true;
                fecha1.setTime(c.getTimeInMillis());
                fecha2.setTime(cFin.getTimeInMillis());
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
