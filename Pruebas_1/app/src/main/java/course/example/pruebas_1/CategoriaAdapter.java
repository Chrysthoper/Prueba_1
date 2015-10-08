package course.example.pruebas_1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriaAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Categoria> categorias;
    private int seleccion;

    public CategoriaAdapter(Context context, ArrayList<Categoria> categorias, int seleccion) {
        this.context = context;
        this.categorias = categorias;
        this.seleccion = seleccion;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null)
        {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.categoria_adapter, null);
            if(seleccion == position)
                gridView.setBackgroundColor(Color.parseColor("#FFAA2300"));
            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
            textView.setText(categorias.get(position).nombre);
            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
            imageView.setImageResource(categorias.get(position).resource);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
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

}
