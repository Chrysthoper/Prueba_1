package course.example.pruebas_1.Ventanas.Transacciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;

public class TutorialStep3 extends WizardStep {

    @ContextVariable
    private Transaccion trans;


    EditText etDescripcion,etNotas;
    Spinner spIntervalosProgramacion;

    String[] arraySpinner = new String[] {
            "Diario", "Semanal", "Mensual", "Anual"
    };
    CheckBox chProgramarTrans;
    LinearLayout lyProgramarTrans;

    //Wire the layout to the step
    public TutorialStep3() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.ventana_transaccion_paso3, container, false);
        etDescripcion = (EditText) v.findViewById(R.id.etDescripcion);
        etDescripcion.setText(trans.descripcion);
        etNotas = (EditText) v.findViewById(R.id.etNotas);
        etNotas.setText(trans.nota);

        chProgramarTrans = (CheckBox) v.findViewById(R.id.chProgramarTrans);

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
        trans.programar = chProgramarTrans.isChecked();
    }

}
