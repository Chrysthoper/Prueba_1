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

import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Util;

public class TransaccionAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Transaccion> Transacciones;
    private int seleccion;
    private DBHelper dbHelper;
    private final ArrayList<Categoria> Categorias;
    private String fecha = "";
    private boolean ConFechas;

    public TransaccionAdapter(Context context, ArrayList<Transaccion> Transacciones, boolean ConFechas) {
        this.context = context;
        dbHelper = new DBHelper(context);
        this.Categorias = dbHelper.Categorias.Obten();
        this.Transacciones = Transacciones;
        this.ConFechas = ConFechas;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        MyViewHolder holder;

        Transaccion trans = Transacciones.get(position);
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.transaccion_adapter, null, false);
            holder = new MyViewHolder();
            if(ConFechas && !fecha.equals(trans.fecha_alta))
            {
                fecha = trans.fecha_alta;
                holder.lyTransAdapterDia = (LinearLayout)row.findViewById(R.id.lyTransAdapterDia);
                holder.lyTransAdapterDia.setVisibility(View.VISIBLE);
                holder.tvTransAdapterDia = (TextView)row.findViewById(R.id.tvTransAdapterDia);
                holder.tvTransAdapterDia.setText(fecha);
            }
            holder.tvCostoAdapter = (TextView) row.findViewById(R.id.tvCostoAdapter);
            holder.ivCategoriaAdapter = (ImageView) row.findViewById(R.id.ivCategoriaAdapter);
            holder.vTipoTransaccionAdapter = (View) row.findViewById(R.id.vTipoTransaccionAdapter);

            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        holder.tvCostoAdapter.setText(Util.PriceFormat(trans.costo));
        holder.ivCategoriaAdapter.setImageResource(Categorias.get(trans.numeroCategoria).resource);
        holder.vTipoTransaccionAdapter.setBackgroundResource(Categorias.get(trans.numeroCategoria).formaCirculo);
        switch(trans.tipoTransaccion)
        {
            case 1:
                holder.vTipoTransaccionAdapter.setBackgroundColor(Color.parseColor("#ff99cc00"));
                break;
            case 0:
                holder.vTipoTransaccionAdapter.setBackgroundColor(Color.parseColor("#ffff4444"));
                break;
            default:
                break;
        }
        return row;
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

    private static class MyViewHolder {
        public TextView tvCostoAdapter;
        public ImageView ivCategoriaAdapter;
        public View vTipoTransaccionAdapter;
        public LinearLayout lyTransAdapterDia;
        public TextView tvTransAdapterDia;
    }

}

