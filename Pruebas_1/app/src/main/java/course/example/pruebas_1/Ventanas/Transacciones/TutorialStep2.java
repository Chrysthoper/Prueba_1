package course.example.pruebas_1.Ventanas.Transacciones;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.ArrayList;

import course.example.pruebas_1.Adapters.CategoriaAdapter;
import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.DB.TD_Categorias;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;

public class TutorialStep2 extends WizardStep {

    @ContextVariable
    private Transaccion trans;
    ArrayList<Categoria> ListaCategorias;
    TD_Categorias td_categorias;
    GridView lvCategoriasPaso2;
    private DBHelper dbHelper;

    //You must have an empty constructor for every step
    public TutorialStep2() {

    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.step2_tutorial, container, false);
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        td_categorias = dbHelper.Categorias;
        ListaCategorias = td_categorias.Obten();
        lvCategoriasPaso2 = (GridView)v.findViewById(R.id.lvCategoriasPaso2);
        lvCategoriasPaso2.setAdapter(new CategoriaAdapter(v.getContext(), ListaCategorias, trans.numeroCategoria));
        lvCategoriasPaso2.setOnItemClickListener(click_grid);
        return v;
    }

    AdapterView.OnItemClickListener click_grid = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
        long id) {
            TextView tvIDCategoria = (TextView)v.findViewById(R.id.tvIDCategoriaAdapter);
            int idCategoria = ListaCategorias.get(position).id;
            if (trans.numeroCategoria != idCategoria)
            {
                if (trans.numeroCategoria != -1)
                    lvCategoriasPaso2.getChildAt(ListaCategorias.indexOf(trans.categoriaObj)).setBackground(null);
                trans.numeroCategoria = ListaCategorias.get(position).id;
                trans.categoriaObj = ListaCategorias.get(position);
                v.setBackgroundColor(Color.parseColor("#FFAA2300"));
            }
            else
            {
                lvCategoriasPaso2.getChildAt(ListaCategorias.indexOf(trans.categoriaObj)).setBackground(null);
                trans.numeroCategoria = -1;
            }
        }
    };

}