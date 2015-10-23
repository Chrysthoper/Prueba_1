package course.example.pruebas_1.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Util;

public class TransaccionAdapter extends BaseAdapter {

    private IAdaptersCallerGrid caller;

    private Context context;
    private final ArrayList<Transaccion> Transacciones;
    private int seleccion;
    private DBHelper dbHelper;
    private final ArrayList<Categoria> Categorias;
    private String fecha = "2500-01-01";
    private boolean ConFechas;
    private ArrayList<Integer> TransaccionConBaner;
    private ArrayList<String> FechaContada;

    public TransaccionAdapter(Context context, ArrayList<Transaccion> Transacciones, boolean ConFechas) {
        this.context = context;
        dbHelper = new DBHelper(context);
        this.Categorias = dbHelper.Categorias.Obten();
        this.Transacciones = Transacciones;
        this.ConFechas = ConFechas;
        TransaccionConBaner = new ArrayList<Integer>();
        FechaContada = new ArrayList<String>();
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {

        View row = convertView;
        final MyViewHolder holder;
        final Transaccion trans = Transacciones.get(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.transaccion_adapter, null, false);
            holder = new MyViewHolder();

            holder.lyTransAdapterDia = (LinearLayout) row.findViewById(R.id.lyTransAdapterDia);
            holder.lyTransAdapterDia.setVisibility(View.GONE);

            holder.tvCostoAdapter = (TextView) row.findViewById(R.id.tvCostoAdapter);
            holder.tvNotaAdapter = (TextView) row.findViewById(R.id.tvNotaAdapter);
            holder.ivCategoriaAdapter = (ImageView) row.findViewById(R.id.ivCategoriaAdapter);
            holder.tvDescripcionAdapter = (TextView) row.findViewById(R.id.tvDescripcionAdapter);
            holder.lyTransAdapter1 = (LinearLayout) row.findViewById(R.id.lyTransAdapter1);
            holder.lyTransAdapter1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(context)
                            .setMessage("¿Seguro que desea borrar la transacción?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (dbHelper.Transacciones.Elimina(trans.id)) {
                                        Transacciones.remove(position);
                                        notifyDataSetChanged();
                                        caller.ActualizaGrid(Transacciones);
                                        Toast.makeText(context, "Se elimino la transacción", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                    return true;
                }
            });


            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }

        if (ConFechas &&
                (Util.FormatToFecha(fecha).after(Util.FormatToFecha(trans.fecha_alta)) && !FechaContada.contains(trans.fecha_alta)) ||
                TransaccionConBaner.contains(trans.id)) {
            TransaccionConBaner.add(trans.id);
            fecha = trans.fecha_alta;
            FechaContada.add(fecha);
            holder.lyTransAdapterDia = (LinearLayout) row.findViewById(R.id.lyTransAdapterDia);
            holder.lyTransAdapterDia.setVisibility(View.VISIBLE);
            holder.tvTransAdapterDia = (TextView) row.findViewById(R.id.tvTransAdapterDia);
            holder.tvTransAdapterDia.setText(fecha);
        }
        else
        {
            holder.lyTransAdapterDia = (LinearLayout)row.findViewById(R.id.lyTransAdapterDia);
            holder.lyTransAdapterDia.setVisibility(View.GONE);
        }

        holder.tvCostoAdapter.setText(Util.PriceFormat(trans.costo));
        holder.tvDescripcionAdapter.setText(trans.descripcion);
        holder.tvNotaAdapter.setText(trans.nota);
        holder.ivCategoriaAdapter.setImageResource(trans.categoriaObj.resource);
        holder.ivCategoriaAdapter.setBackgroundResource(trans.categoriaObj.formaCirculo);

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

    public void setCallback(IAdaptersCallerGrid caller){
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
        public TextView tvCostoAdapter,tvDescripcionAdapter,tvNotaAdapter;
        public ImageView ivCategoriaAdapter;
        public LinearLayout lyTransAdapterDia,lyTransAdapter1;
        public TextView tvTransAdapterDia;
    }

}

