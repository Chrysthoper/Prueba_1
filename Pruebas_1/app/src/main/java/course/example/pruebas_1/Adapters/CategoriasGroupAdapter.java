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

import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGrid;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGridCategoriasGroup;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;

public class CategoriasGroupAdapter extends BaseAdapter {

    private IAdaptersCallerGridCategoriasGroup caller;

    private Context context;
    private final ArrayList<Categoria> Categorias;
    private int seleccion;
    private DBHelper dbHelper;
    private String fecha = "2500-01-01";
    private boolean ConFechas;
    private ArrayList<Integer> TransaccionConBaner;
    private ArrayList<String> FechaContada;

    public CategoriasGroupAdapter(Context context, ArrayList<Categoria> Categorias) {
        this.context = context;
        dbHelper = new DBHelper(context);
        this.Categorias = Categorias;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {

        View row = convertView;
        final MyViewHolder holder;
        final Categoria categoria = Categorias.get(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.categorias_group_adapter, null, false);
            holder = new MyViewHolder();

            holder.tvCostoAdapter = (TextView) row.findViewById(R.id.tvCostoAdapter);
            holder.tvDescripcionAdapter = (TextView) row.findViewById(R.id.tvDescripcionAdapter);
            holder.ivCategoriaAdapter = (ImageView) row.findViewById(R.id.ivCategoriaAdapter);

            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }

        holder.tvCostoAdapter.setText(Util.PriceFormat(categoria.total));
        holder.tvDescripcionAdapter.setText(categoria.nombre);
        holder.ivCategoriaAdapter.setImageResource(categoria.resource);
        holder.ivCategoriaAdapter.setBackgroundResource(categoria.formaCirculo);

        return row;
    }

    public void setCallback(IAdaptersCallerGridCategoriasGroup caller){
        this.caller = caller;
    }

    @Override
    public int getCount() {
        return Categorias.size();
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
        public TextView tvCostoAdapter,tvDescripcionAdapter;
        public ImageView ivCategoriaAdapter;
    }

}

