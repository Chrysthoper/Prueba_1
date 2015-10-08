package course.example.pruebas_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    Button btnTransaccionSalida,btnTransaccionEntrada;
    LinearLayout lyFechaPrincipal;
    TextView tvFechaPrincipalDia,tvFechaPrincipalMes;
    DatePickerDialog DialogoFechaPrincipal;
    ListView lvTransaccionesPrincipal;
    TransaccionAdapter adapter;
    ArrayList<Transaccion> ListaTransacciones = new ArrayList<Transaccion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTransaccionEntrada = (Button)findViewById(R.id.btnTransacionEntrada);
        btnTransaccionEntrada.setOnClickListener(btnTransacciones);
        btnTransaccionSalida = (Button)findViewById(R.id.btnTransacionSalida);
        btnTransaccionSalida.setOnClickListener(btnTransacciones);
        lyFechaPrincipal = (LinearLayout)findViewById(R.id.lyFechaPrincipal);
        lyFechaPrincipal.setOnClickListener(clickFechaPrincipal);
        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        tvFechaPrincipalDia = (TextView)findViewById(R.id.tvFechaPrincipalDia);
        tvFechaPrincipalDia.setText(Integer.toString(dia));
        tvFechaPrincipalMes = (TextView)findViewById(R.id.tvFechaPrincipalMes);
        tvFechaPrincipalMes.setText(getMonthForInt(mes).substring(0, 3).toUpperCase());

        lvTransaccionesPrincipal = (ListView)findViewById(R.id.lvTransaccionesPrincipal);
        adapter = new TransaccionAdapter(getApplicationContext(), ListaTransacciones);
        lvTransaccionesPrincipal.setAdapter(adapter);

        Calendar newCalendar = Calendar.getInstance();
        DialogoFechaPrincipal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvFechaPrincipalDia.setText(Integer.toString(dayOfMonth));
                tvFechaPrincipalMes.setText(getMonthForInt(monthOfYear).substring(0, 3).toUpperCase());
            }

        },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
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

    LinearLayout.OnClickListener clickFechaPrincipal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this,"La fecha principal es: " + tvFechaPrincipalDia.getText() + " de " + tvFechaPrincipalMes.getText() ,Toast.LENGTH_SHORT).show();
            DialogoFechaPrincipal.show();
        }
    };

    Button.OnClickListener btnTransacciones = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msj = "";
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
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
            if(resultCode == 1)
            {
                Transaccion trans = (Transaccion)data.getSerializableExtra("trans");
                ListaTransacciones.add(0,trans);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Se agrego la transaccion de $ " + Util.PriceFormat(Double.parseDouble(trans.textoKeyPad)), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(MainActivity.this, "No se genero ninguna transaccion", Toast.LENGTH_SHORT).show();
        }
    }

}
