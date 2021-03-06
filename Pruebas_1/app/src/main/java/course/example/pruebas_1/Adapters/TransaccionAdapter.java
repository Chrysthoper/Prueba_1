package course.example.pruebas_1.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Util;
import course.example.pruebas_1.Ventanas.Transacciones.TutorialActivity;

public class TransaccionAdapter extends BaseAdapter implements Serializable {

    private IAdaptersCallerGrid caller;

    private Context context;
    public ArrayList<Transaccion> Transacciones;
    private DBHelper dbHelper;

    public TransaccionAdapter(Context context, ArrayList<Transaccion> Transacciones) {
        this.context = context;
        dbHelper = new DBHelper(context);
        this.Transacciones = Transacciones;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {

        View row = convertView;
        final MyViewHolder holder;
        final Transaccion trans = Transacciones.get(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adapter_transaccion_list, null, false);
            holder = new MyViewHolder();

            holder.tvCostoAdapter = (TextView) row.findViewById(R.id.tvCostoAdapter);
            holder.tvNotaAdapter = (TextView) row.findViewById(R.id.tvNotaAdapter);
            holder.ivCategoriaAdapter = (ImageView) row.findViewById(R.id.ivCategoriaAdapter);
            holder.tvDescripcionAdapter = (TextView) row.findViewById(R.id.tvDescripcionAdapter);

            holder.tvFechaAdapter = (TextView) row.findViewById(R.id.tvFechaAdapter);
            holder.ivCuentaAdapter = (ImageView) row.findViewById(R.id.ivCuentaAdapter);

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
            holder.lyTransAdapter1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent((Activity) context, TutorialActivity.class);
                    intent.putExtra("FECHA", trans.fecha_alta);
                    intent.putExtra("TIPO", trans.tipo_transaccion);
                    intent.putExtra("OP", 'C');
                    intent.putExtra("TRANS", trans);
                    intent.putExtra("CUENTA_ID", trans.cuenta_prin_id);//Cuentas.get(which).id);
                    ((Activity) context).startActivityForResult(intent, 1);
                }
            });

            holder.lyTransAdapter2 = (LinearLayout) row.findViewById(R.id.lyTransAdapter2);
            holder.lyTransAdapter2.setOnLongClickListener(new View.OnLongClickListener() {
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
            holder.lyTransAdapter2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent((Activity) context, TutorialActivity.class);
                    intent.putExtra("FECHA", trans.fecha_alta);
                    intent.putExtra("TIPO", trans.tipo_transaccion);
                    intent.putExtra("OP", 'C');
                    intent.putExtra("TRANS", trans);
                    intent.putExtra("CUENTA_ID", trans.cuenta_prin_id);//Cuentas.get(which).id);
                    ((Activity) context).startActivityForResult(intent, 1);
                }
            });


            holder.tvMontoTransAdapter2 = (TextView) row.findViewById(R.id.tvMontoTransAdapter2);
            holder.tvFechaTransAdapter2 = (TextView) row.findViewById(R.id.tvFechaTransAdapter2);

            holder.tvDescTransAdapter2 = (TextView) row.findViewById(R.id.tvDescTransAdapter2);
            holder.tvNotaTransAdapter2 = (TextView) row.findViewById(R.id.tvNotaTransAdapter2);

            holder.ivCuenta1TransAdapter2 = (ImageView) row.findViewById(R.id.ivCuenta1TransAdapter2);
            holder.ivCuenta2TransAdapter2 = (ImageView) row.findViewById(R.id.ivCuenta2TransAdapter2);

            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        holder.lyTransAdapter1.setVisibility(View.GONE);
        holder.lyTransAdapter2.setVisibility(View.GONE);

        if(trans.tipo_transaccion < 2)//0 && 1
        {
            holder.lyTransAdapter1.setVisibility(View.VISIBLE);
            holder.tvFechaAdapter.setText(Util.FormatToShort(trans.fecha_alta.substring(0,10)));
            holder.ivCuentaAdapter.setImageResource(Util.imagenesFull[trans.cuentaPrincipalObj.resource]);

            holder.tvCostoAdapter.setText(Util.PriceFormat(trans.costo));
            holder.tvDescripcionAdapter.setText(trans.descripcion);
            holder.tvNotaAdapter.setText(trans.nota);
            holder.ivCategoriaAdapter.setImageResource(Util.imagenesFull[trans.categoriaObj.resource]);
            holder.ivCategoriaAdapter.setBackgroundResource(Util.colores[trans.categoriaObj.formaCirculo]);

            switch(trans.categoriaObj.tipo)
            {
                case 0:
                    holder.tvCostoAdapter.setTextColor(Color.parseColor("#ffff4444"));
                    break;
                case 1:
                    holder.tvCostoAdapter.setTextColor(Color.parseColor("#ff99cc00"));
                    break;
                default:
                    break;
            }
        }
        else
        {
            holder.lyTransAdapter2.setVisibility(View.VISIBLE);
            holder.tvFechaTransAdapter2.setText(Util.FormatToShort(trans.fecha_alta.substring(0, 10)));

            holder.tvMontoTransAdapter2.setText(Util.PriceFormat(trans.costo));
            holder.tvDescTransAdapter2.setText(trans.descripcion);
            holder.tvNotaTransAdapter2.setText(trans.nota);

            holder.ivCuenta1TransAdapter2.setImageResource(Util.imagenesFull[trans.cuentaPrincipalObj.resource]);
            holder.ivCuenta2TransAdapter2.setImageResource(Util.imagenesFull[trans.cuentaSecundariaObj.resource]);
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
        public TextView tvCostoAdapter,tvMontoTransAdapter2,tvDescripcionAdapter,tvDescTransAdapter2,tvNotaAdapter,tvNotaTransAdapter2,tvFechaAdapter,tvFechaTransAdapter2;
        public ImageView ivCategoriaAdapter,ivCuenta2TransAdapter2,ivCuenta1TransAdapter2,ivCuentaAdapter;
        public LinearLayout lyTransAdapter2,lyTransAdapter1;
    }

}

