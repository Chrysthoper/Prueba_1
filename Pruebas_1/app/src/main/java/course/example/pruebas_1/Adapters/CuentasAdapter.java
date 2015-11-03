package course.example.pruebas_1.Adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
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

public class CuentasAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<Cuenta> Cuentas;


    public CuentasAdapter(Context context, ArrayList<Cuenta> Cuentas) {
        this.context = context;
        this.Cuentas = Cuentas;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {

        View row = convertView;
        final MyViewHolder holder;
        final Cuenta cuenta = Cuentas.get(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.categorias_group_adapter, null, false);
            holder = new MyViewHolder();

            holder.tvCostoAdapterGroup = (TextView) row.findViewById(R.id.tvCostoAdapterGroup);
            holder.tvDescripcionAdapterGroup = (TextView) row.findViewById(R.id.tvDescripcionAdapterGroup);
            holder.ivCategoriaAdapterGroup = (ImageView) row.findViewById(R.id.ivCategoriaAdapterGroup);
            holder.lyTransAdapterGroup1 = row.findViewById(R.id.lyTransAdapterGroup1);

            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }

        LayerDrawable bgDrawable = (LayerDrawable)holder.lyTransAdapterGroup1.getBackground();
        GradientDrawable shape_item_color = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.shape_itemgrid_color);
        int color = context.getResources().getColor(cuenta.color);
        shape_item_color.setColor(color);

        holder.tvCostoAdapterGroup.setText(Util.PriceFormat(cuenta.total));
        holder.tvDescripcionAdapterGroup.setText(cuenta.nombre);
        holder.ivCategoriaAdapterGroup.setImageResource(cuenta.resource);
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
        public TextView tvCostoAdapterGroup,tvDescripcionAdapterGroup;
        public ImageView ivCategoriaAdapterGroup;
        public View lyTransAdapterGroup1;
    }

}

