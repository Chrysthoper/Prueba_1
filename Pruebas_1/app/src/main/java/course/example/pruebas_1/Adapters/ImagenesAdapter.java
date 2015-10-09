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

import course.example.pruebas_1.R;

public class ImagenesAdapter extends BaseAdapter {
    private Context context;
    private final int[] imagenes;
    private int seleccion;

    public ImagenesAdapter(Context context, int[] imagenes, int seleccion) {
        this.context = context;
        this.imagenes = imagenes;
        this.seleccion = seleccion;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.colores_adapter, null, false);

            holder = new MyViewHolder();
            holder.ivHolderImagen = (ImageView) row.findViewById(R.id.ivGridColor);
            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        holder.ivHolderImagen.setImageResource(imagenes[position]);
        return row;
    }

    @Override
    public int getCount() {
        return imagenes.length;
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
        public ImageView ivHolderImagen;
    }

}