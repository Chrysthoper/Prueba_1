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
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;
import course.example.pruebas_1.Ventanas.Categorias.VentanaCategorias;

public class CategoriaAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<Categoria> categorias;
    private int seleccion;

    public CategoriaAdapter(Context context, ArrayList<Categoria> categorias, int seleccion) {
        this.context = context;
        this.categorias = categorias;
        this.categorias.add(0,new Categoria(0,"Nueva Categoria",20,10,10,0));
        this.seleccion = seleccion;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyViewHolder holder;
        final Categoria cat = this.categorias.get(position);
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adapter_categoria_grid, null, false);

            holder = new MyViewHolder();
            holder.tvNombreCategoria = (TextView) row.findViewById(R.id.tvGridCategoriaAdapter);
            holder.ivImagenCategoria = (ImageView) row.findViewById(R.id.ivGridCategoriaAdapter);
            holder.lyColorCategoria = (LinearLayout) row.findViewById(R.id.lyGridCategoriaAdapter);

            if (context instanceof VentanaCategorias)
                holder.tvNombreCategoria.setTextColor(Color.BLACK);

            if(seleccion == cat.id)
                row.setBackgroundColor(Color.parseColor("#FFAA2300"));

            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        holder.tvNombreCategoria.setText(cat.nombre);
        holder.ivImagenCategoria.setImageResource(Util.imagenesFull[cat.resource]);
        holder.lyColorCategoria.setBackgroundResource(Util.colores[cat.formaCirculo]);
        return row;
    }



    @Override
    public int getCount() {
        return categorias.size();
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
