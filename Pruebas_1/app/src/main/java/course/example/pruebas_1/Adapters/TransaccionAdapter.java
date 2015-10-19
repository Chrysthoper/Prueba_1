package course.example.pruebas_1.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import course.example.pruebas_1.Interfaces.IAdaptersCaller;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Util;

public class TransaccionAdapter extends BaseAdapter {

    private IAdaptersCaller caller;

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

    public View getView(final int position, final View convertView, ViewGroup parent) {

        final AlertDialog.Builder dialog;
        View row = convertView;
        final MyViewHolder holder;
        final Transaccion trans = Transacciones.get(position);

        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.transaccion_adapter, null, false);
            dialog = new AlertDialog.Builder(row.getContext());
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
            holder.tvDescripcionAdapter = (TextView)row.findViewById(R.id.tvDescripcionAdapter);
            holder.lyTransAdapter1 = (LinearLayout)row.findViewById(R.id.lyTransAdapter1);
            holder.lyTransAdapter1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.lyTransAdapter1.setVisibility(View.GONE);
                    holder.lyTransAdapter2.setVisibility(View.VISIBLE);
                }
            });
            holder.lyTransAdapter2 = (LinearLayout)row.findViewById(R.id.lyTransAdapter2);
            holder.btnEliminarTrans = (Button)row.findViewById(R.id.btnEliminarTrans);
            holder.btnEliminarTrans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    if (dbHelper.Transacciones.Elimina(trans.id)) {
                        Transacciones.remove(position);
                        notifyDataSetChanged();
                        caller.ActualizaVentana();
                        holder.lyTransAdapter2.setVisibility(View.GONE);
                        holder.lyTransAdapter1.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Se elimino la transaccion", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.btnTransDetalleCerrar = (Button)row.findViewById(R.id.btnTransDetalleCerrar);
            holder.btnTransDetalleCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.lyTransAdapter2.setVisibility(View.GONE);
                    holder.lyTransAdapter1.setVisibility(View.VISIBLE);
                }
            });
            holder.tvTransDetalleDesc = (TextView) row.findViewById(R.id.tvTransDetalleDesc);
            holder.ivTransDetalleCategoria = (ImageView) row.findViewById(R.id.ivTransDetalleCategoria);
            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        holder.tvCostoAdapter.setText(Util.PriceFormat(trans.costo));
        holder.tvDescripcionAdapter.setText(trans.descripcion);
        holder.ivCategoriaAdapter.setImageResource(Categorias.get(trans.numeroCategoria).resource);
        holder.ivCategoriaAdapter.setBackgroundResource(Categorias.get(trans.numeroCategoria).formaCirculo);

        holder.tvTransDetalleDesc.setText(trans.descripcion);
        holder.ivTransDetalleCategoria.setImageResource(Categorias.get(trans.numeroCategoria).resource);
        holder.ivTransDetalleCategoria.setBackgroundResource(Categorias.get(trans.numeroCategoria).formaCirculo);

        switch(trans.tipoTransaccion)
        {
            case 1:
                holder.tvCostoAdapter.setTextColor(Color.parseColor("#ff99cc00"));
                break;
            case 0:
                holder.tvCostoAdapter.setTextColor(Color.parseColor("#ffff4444"));
                break;
            default:
                break;
        }
        return row;
    }

    public void setCallback(IAdaptersCaller caller){
        this.caller = caller;
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
        public TextView tvCostoAdapter,tvDescripcionAdapter,tvTransDetalleDesc;
        public ImageView ivCategoriaAdapter,ivTransDetalleCategoria;
        public LinearLayout lyTransAdapterDia,lyTransAdapter1,lyTransAdapter2;
        public TextView tvTransAdapterDia;
        public Button btnEliminarTrans, btnTransDetalleCerrar;
    }

}

