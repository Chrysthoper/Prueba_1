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

import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Negocio.TransaccionProgramada;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;
import course.example.pruebas_1.Ventanas.Transacciones.TutorialActivity;

public class TransaccionProgramaListAdapter extends BaseAdapter implements Serializable {

    private IAdaptersCallerGrid caller;

    private Context context;
    public ArrayList<TransaccionProgramada> Transacciones;
    private DBHelper dbHelper;

    public TransaccionProgramaListAdapter(Context context, ArrayList<TransaccionProgramada> Transacciones) {
        this.context = context;
        dbHelper = new DBHelper(context);
        this.Transacciones = Transacciones;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {

        View row = convertView;
        final MyViewHolder holder;
        final TransaccionProgramada trans = Transacciones.get(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adapter_transaccion_programada_list, null, false);
            holder = new MyViewHolder();

            holder.tvCostoTransProgramadaAdapter = (TextView) row.findViewById(R.id.tvCostoTransProgramadaAdapter);

            holder.lyTransProgramadaAdapter = (LinearLayout) row.findViewById(R.id.lyTransProgramadaAdapter);

            holder.tvFechaSiguienteTransProgramadaAdapter = (TextView) row.findViewById(R.id.tvFechaSiguienteTransProgramadaAdapter);

            holder.ivCategoriaTransProgramadaAdapter = (ImageView) row.findViewById(R.id.ivCategoriaTransProgramadaAdapter);

            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }

        holder.tvFechaSiguienteTransProgramadaAdapter.setText(Util.FormatToShort(trans.fecha_siguiente.substring(0, 10)));
        holder.tvCostoTransProgramadaAdapter.setText(Util.PriceFormat(trans.costo));
        holder.ivCategoriaTransProgramadaAdapter.setImageResource(Util.imagenesFull[trans.categoriaObj.resource]);
        int color = context.getResources().getColor(Util.coloresHex[trans.categoriaObj.color]);
        holder.lyTransProgramadaAdapter.setBackgroundColor(color);

        switch(trans.categoriaObj.tipo)
        {
            case 0:
                holder.tvCostoTransProgramadaAdapter.setTextColor(Color.parseColor("#ffff4444"));
                break;
            case 1:
                holder.tvCostoTransProgramadaAdapter.setTextColor(Color.parseColor("#ff99cc00"));
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
        public TextView tvCostoTransProgramadaAdapter,tvFechaSiguienteTransProgramadaAdapter;
        public ImageView ivCategoriaTransProgramadaAdapter;
        public LinearLayout lyTransProgramadaAdapter;
    }

}

