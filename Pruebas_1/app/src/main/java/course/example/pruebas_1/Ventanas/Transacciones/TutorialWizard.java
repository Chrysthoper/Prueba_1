package course.example.pruebas_1.Ventanas.Transacciones;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.Date;

import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;

public class TutorialWizard extends BasicWizardLayout {

    @ContextVariable
    private Transaccion trans = new Transaccion("",-1,-1,"","","",0);

    /**
     * Note that initially BasicWizardLayout inherits from {@link android.support.v4.app.Fragment} and therefore you must have an empty constructor
     */
    public TutorialWizard() {
        super();
    }

    //You must override this method and create a wizard flow by
    //using WizardFlow.Builder as shown in this example
    @Override
    public WizardFlow onSetup() {
        /* Optionally, you can set different labels for the control buttons
        setNextButtonLabel("Advance");
        setBackButtonLabel("Return");
        setFinishButtonLabel("Finalize"); */
        return new WizardFlow.Builder()
                .addStep(TutorialStep1.class)           //Add your steps in the order you want them
                .addStep(TutorialStep2.class)           //to appear and eventually call create()
                .addStep(TutorialStep3.class)           //to appear and eventually call create()
                .create();                              //to create the wizard flow.
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View frag = (View)view.findViewById(R.id.step_container);
        String fecha;
        int op = 0;
        if (savedInstanceState != null) {
            // Restore last state
            fecha = savedInstanceState.getString("FECHA");
            op = savedInstanceState.getInt("OP");
            trans.fecha_alta = fecha;
            trans.tipo_transaccion = op;
        } else {
            fecha = getArguments().getString("FECHA");
            op = getArguments().getInt("OP", 0);
            trans.fecha_alta = fecha;
            trans.tipo_transaccion = op;
        }

        switch(op)
        {
            case R.id.btnTransacionEntrada:case 1:
                trans.tipo_transaccion = 1;
                frag.setBackgroundColor(Color.parseColor("#ff99cc00"));
                break;
            case R.id.btnTransacionSalida:case 0:
                trans.tipo_transaccion = 0;
                frag.setBackgroundColor(Color.parseColor("#ffff4444"));
                break;
            default:
                break;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("FECHA", trans.fecha_alta);
        outState.putInt("OP", trans.tipo_transaccion);
    }

    @Override
    public void onWizardComplete() {
        super.onWizardComplete();
        Intent returnIntent = new Intent();
        String error = Validate();
        if(error.equals(""))
        {
            DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());
            Transaccion transaccion = new Transaccion(0,Double.parseDouble(trans.textoKeyPad),trans.numeroCategoria,trans.fecha_alta,trans.nota,trans.descripcion,2);
            dbHelper.Transacciones.Inserta(transaccion);
            returnIntent.putExtra("trans", trans);
            getActivity().setResult(1, returnIntent);
            getActivity().finish();     //Terminate the wizard
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    public String Validate(){
        if(trans.textoKeyPad.equals(".") || trans.textoKeyPad.equals(""))
            return "No se ha ingresado un total";
        else if(trans.numeroCategoria == -1)
            return "No se ha seleccionado una categoria";
        else
            return "";
    }

}