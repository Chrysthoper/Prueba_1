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

import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.DB.TD_Categorias;

public class TutorialStep2 extends WizardStep {

    @ContextVariable
    private Transaccion trans;

    TD_Categorias td_categorias;
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
        td_categorias = new DBHelper(getActivity().getApplicationContext()).Categorias;
        gridView = (GridView)v.findViewById(R.id.gridView1);
        gridView.setAdapter(new CategoriaAdapter(v.getContext(), td_categorias.Obten(), trans.numeroCategoria));
        gridView.setOnItemClickListener(click_grid);
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