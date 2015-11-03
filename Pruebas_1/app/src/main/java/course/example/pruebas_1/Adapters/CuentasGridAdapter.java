package course.example.pruebas_1.Adapters;

import android.content.Context;
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
            row = inflater.inflate(R.layout.categoria_adapter, null, false);

            holder = new MyViewHolder();
            holder.tvNombreCategoria = (TextView) row.findViewById(R.id.tvGridCategoriaAdapter);
            holder.tvIDCategoriaAdapter = (TextView) row.findViewById(R.id.tvIDCategoriaAdapter);
            holder.ivImagenCategoria = (ImageView) row.findViewById(R.id.ivGridCategoriaAdapter);
            holder.lyColorCategoria = (LinearLayout) row.findViewById(R.id.lyGridCategoriaAdapter);

            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        holder.tvNombreCategoria.setText(Util.PriceFormat(cuenta.total));
        holder.tvIDCategoriaAdapter.setText(String.valueOf(cuenta.id));
        holder.ivImagenCategoria.setImageResource(cuenta.resource);
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
        public TextView tvNombreCategoria,tvIDCategoriaAdapter;
        public ImageView ivImagenCategoria;
        public LinearLayout lyColorCategoria;
    }

}
