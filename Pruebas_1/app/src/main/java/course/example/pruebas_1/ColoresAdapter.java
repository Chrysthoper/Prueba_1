package course.example.pruebas_1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ColoresAdapter extends BaseAdapter {
    private Context context;
    private final int[] colores;
    private int seleccion;

    public ColoresAdapter(Context context, int[] colores, int seleccion) {
        this.context = context;
        this.colores = colores;
        this.seleccion = seleccion;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null)
        {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.colores_adapter, null);
            ImageView imageView = (ImageView) gridView.findViewById(R.id.ivGridColor);
            imageView.setBackgroundResource(colores[position]);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return colores.length;
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
