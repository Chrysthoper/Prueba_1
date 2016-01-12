package course.example.pruebas_1.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Interfaces.IAdaptersCallerGridCategoriasGroup;
import course.example.pruebas_1.Negocio.Categoria;
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
            row = inflater.inflate(R.layout.adapter_categoria_list, null, false);
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
        int color = context.getResources().getColor(Util.coloresHex[categoria.color]);
        shape_item_color.setColor(color);


        holder.tvCostoAdapterGroup.setText(Util.PriceFormat(categoria.total));
        holder.tvDescripcionAdapterGroup.setText(categoria.nombre);
        holder.ivCategoriaAdapterGroup.setImageResource(Util.imagenesFull[categoria.resource]);
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
        public TextView tvCostoAdapterGroup,tvDescripcionAdapterGroup;
        public ImageView ivCategoriaAdapterGroup;
        public View lyTransAdapterGroup1;
    }

}

