package course.example.pruebas_1.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class CuentasGridAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<Cuenta> cuentas;
    private int seleccion;

    public CuentasGridAdapter(Context context, ArrayList<Cuenta> cuentas, int seleccion) {
        this.context = context;
        this.cuentas = cuentas;
        this.seleccion = seleccion;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyViewHolder holder;
        final Cuenta cuenta = this.cuentas.get(position);
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adapter_cuentas_grid, null, false);

            holder = new MyViewHolder();
            holder.tvGridNombreCuentaAdapter = (TextView) row.findViewById(R.id.tvGridNombreCuentaAdapter);
            holder.tvGridPrecioCuentaAdapter = (TextView) row.findViewById(R.id.tvGridPrecioCuentaAdapter);
            holder.ivImagenCuenta = (ImageView) row.findViewById(R.id.ivGridCategoriaAdapter);
            holder.lyColorCuenta = (LinearLayout) row.findViewById(R.id.lyGridCategoriaAdapter);

            if(seleccion == cuenta.id)
                row.setBackgroundColor(Color.parseColor("#FFAA2300"));

            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        holder.tvGridNombreCuentaAdapter.setText(cuenta.nombre);
        holder.tvGridPrecioCuentaAdapter.setText(Util.PriceFormat(cuenta.total));
        holder.ivImagenCuenta.setImageResource(Util.imagenesFull[cuenta.resource]);
        return row;
    }

    @Override
    public int getCount() {
        return cuentas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class MyViewHolder {
        public TextView tvGridPrecioCuentaAdapter,tvGridNombreCuentaAdapter;
        public ImageView ivImagenCuenta;
        public LinearLayout lyColorCuenta;
    }

}
