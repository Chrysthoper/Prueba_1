package course.example.pruebas_1.Ventanas.Transacciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;

public class TutorialStep3 extends WizardStep {

    @ContextVariable
    private Transaccion trans;


    EditText etDescripcion,etNotas;

    //Wire the layout to the step
    public TutorialStep3() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.step3_tutorial, container, false);
        etDescripcion = (EditText) v.findViewById(R.id.etDescripcion);
        etDescripcion.setText(trans.descripcion);
        etNotas = (EditText) v.findViewById(R.id.etNotas);
        etNotas.setText(trans.nota);
        return v;
    }

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                bindDataFields();
                break;
            case WizardStep.EXIT_PREVIOUS:
                //Do nothing...
                break;
        }
    }

    private void bindDataFields() {
        trans.descripcion = etDescripcion.getText().toString().trim();
        trans.nota = etNotas.getText().toString().trim();
    }

}
