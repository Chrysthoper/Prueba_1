package course.example.pruebas_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.codepond.wizardroid.WizardStep;

public class TutorialStep2 extends WizardStep {

    private Categoria[] categorias = new Categoria[]{
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
        View v = inflater.inflate(R.layout.step2_tutorial, container, false);
        gridView = (GridView)v.findViewById(R.id.gridView1);
        gridView.setAdapter(new CategoriaAdapter(v.getContext(), categorias));
        return v;
    }
}