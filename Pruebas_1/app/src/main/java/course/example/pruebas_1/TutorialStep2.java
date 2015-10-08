package course.example.pruebas_1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

public class TutorialStep2 extends WizardStep {

    @ContextVariable
    private Transaccion trans;

    private Categoria[] categorias = new Categoria[]{
            new Categoria(1,"Agua"),
            new Categoria(2,"Electricidad"),
            new Categoria(3,"Gas"),
            new Categoria(4,"Gasolina"),
            new Categoria(5,"Comida"),
            new Categoria(6,"Ropa"),
            new Categoria(7,"Cochinero"),
            new Categoria(8,"Novia"),
            new Categoria(9,"Camiones"),
            new Categoria(1,"Agua"),
            new Categoria(2,"Electricidad"),
            new Categoria(3,"Gas"),
            new Categoria(4,"Gasolina"),
            new Categoria(5,"Comida"),
            new Categoria(6,"Ropa"),
            new Categoria(7,"Cochinero"),
            new Categoria(8,"Novia"),
            new Categoria(9,"Camiones")
    };

    GridView gridView;

    //You must have an empty constructor for every step
    public TutorialStep2() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.step2_tutorial, container, false);
        gridView = (GridView)v.findViewById(R.id.gridView1);
        gridView.setAdapter(new CategoriaAdapter(v.getContext(), categorias, trans.numeroCategoria));
        gridView.setOnItemClickListener(click_grid);

        /*
        int childcount = gridView.getChildCount();
        int firstPos = gridView.getFirstVisiblePosition();
        for ( int i = 0; i < childcount; i++ )
        {
            int posInArray = firstPos + i;
            if (posInArray == trans.numeroCategoria)
                gridView.getChildAt(i).setBackgroundColor(Color.BLUE);
        }
        */

        //if(trans.numeroCategoria != -1)
        //    gridView.getChildAt(trans.numeroCategoria).setBackgroundColor(Color.parseColor("#FFAA2300"));
        return v;
    }

    AdapterView.OnItemClickListener click_grid = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
        long id) {
            if (trans.numeroCategoria != position) {
                if (trans.numeroCategoria != -1)
                    gridView.getChildAt(trans.numeroCategoria).setBackground(null);
                trans.numeroCategoria = position;
                v.setBackgroundColor(Color.parseColor("#FFAA2300"));
            }
            else
            {
                gridView.getChildAt(trans.numeroCategoria).setBackground(null);
                trans.numeroCategoria = -1;
            }
        }
    };

}