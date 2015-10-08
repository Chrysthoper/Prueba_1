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

public class CategoriaAdapter extends BaseAdapter {
    private Context context;
    private final Categoria[] categorias;
    private int seleccion;

    public CategoriaAdapter(Context context, Categoria[] categorias, int seleccion) {
        this.context = context;
        this.categorias = categorias;
        this.seleccion = seleccion;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null)
        {
            gridView = new View(context);
            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.categoria_adapter, null);
            if(seleccion == position)
                gridView.setBackgroundColor(Color.parseColor("#FFAA2300"));
            // set value into textview
            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
            textView.setText(categorias[position].nombre);
            // set image based on selected text
            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
            switch(categorias[position].id)
            {
                case 1:
                    imageView.setImageResource(R.mipmap.agua);
                    break;
                case 2:
                    imageView.setImageResource(R.mipmap.electricidad);
                    break;
                case 3:
                    imageView.setImageResource(R.mipmap.gas);
                    break;
                case 4:
                    imageView.setImageResource(R.mipmap.gasolina);
                    break;
                case 5:
                    imageView.setImageResource(R.mipmap.comida);
                    break;
                case 6:
                    imageView.setImageResource(R.mipmap.ropa);
                    break;
                case 7:
                    imageView.setImageResource(R.mipmap.chucherias);
                    break;
                case 8:
                    imageView.setImageResource(R.mipmap.pareja);
                    break;
                case 9:
                    imageView.setImageResource(R.mipmap.camion);
                    break;
                default:
                    imageView.setImageResource(R.mipmap.casa);
                    break;
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return categorias.length;
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
