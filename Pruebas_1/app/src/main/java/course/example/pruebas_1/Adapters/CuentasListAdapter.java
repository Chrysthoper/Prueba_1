package course.example.pruebas_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class CuentasListAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<Cuenta> Cuentas;


    public CuentasListAdapter(Context context, ArrayList<Cuenta> Cuentas) {
        this.context = context;
        this.Cuentas = Cuentas;
        this.Cuentas.add(0,new Cuenta(0,"Nueva Cuenta",20,10));
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        final MyViewHolder holder;
        final Cuenta cuenta = Cuentas.get(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adapter_cuentas_list, null, false);
            holder = new MyViewHolder();

            holder.tvListCuentaAdapter = (TextView) row.findViewById(R.id.tvCuentaDescripcionAdapterList);
            holder.tvListTotalCuentaAdapter = (TextView) row.findViewById(R.id.tvCuentaTotalAdapterList);
            holder.ivListCuentaAdapter = (ImageView) row.findViewById(R.id.ivCuentaAdapterList);

            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }

        holder.tvListCuentaAdapter.setText(cuenta.nombre);
        holder.tvListTotalCuentaAdapter.setText(Util.PriceFormat(cuenta.total));
        holder.ivListCuentaAdapter.setImageResource(Util.imagenesFull[cuenta.resource]);
        return row;
    }

    @Override
    public int getCount() {
        return Cuentas.size();
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
        public TextView tvListCuentaAdapter, tvListTotalCuentaAdapter;
        public ImageView ivListCuentaAdapter;
    }

}

