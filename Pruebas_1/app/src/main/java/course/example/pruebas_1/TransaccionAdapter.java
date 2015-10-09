package course.example.pruebas_1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import course.example.pruebas_1.DB.DBHelper;

public class TransaccionAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Transaccion> Transacciones;
    private int seleccion;
    private DBHelper dbHelper;
    private final ArrayList<Categoria> Categorias;

    public TransaccionAdapter(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        this.Categorias = dbHelper.Categorias.Obten();
        this.Transacciones = dbHelper.Transacciones.Obten();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null)
        {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.transaccion_adapter, null);
            Transaccion trans = Transacciones.get(position);
            TextView textView = (TextView) gridView.findViewById(R.id.tvCostoAdapter);
            textView.setText(Util.PriceFormat(trans.costo));
            ImageView imageView = (ImageView) gridView.findViewById(R.id.ivCategoriaAdapter);
            imageView.setImageResource(Categorias.get(trans.numeroCategoria).resource);
            View vTipoTransaccion = (View) gridView.findViewById(R.id.vTipoTransaccionAdapter);
            switch(trans.tipoTransaccion)
            {
                case 1:
                    vTipoTransaccion.setBackgroundColor(Color.parseColor("#ff99cc00"));
                    break;
                case 0:
                    vTipoTransaccion.setBackgroundColor(Color.parseColor("#ffff4444"));
                    break;
                default:
                    break;
            }
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return Transacciones.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
