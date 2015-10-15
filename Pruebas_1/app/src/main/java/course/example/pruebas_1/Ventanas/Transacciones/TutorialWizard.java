package course.example.pruebas_1.Ventanas.Transacciones;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.Date;

import course.example.pruebas_1.DB.DBHelper;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;

public class TutorialWizard extends BasicWizardLayout {

    @ContextVariable
    private Transaccion trans = new Transaccion("",-1,-1,"");

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
                //.addStep(TutorialStep3.class)           //to appear and eventually call create()
                .create();                              //to create the wizard flow.
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int op = getArguments().getInt("OP", 0);
        String fecha = getArguments().getString("FECHA");
        trans.fecha_alta = fecha;
        View frag = (View)view.findViewById(R.id.step_container);
        switch(op)
        {
            case R.id.btnTransacionEntrada:
                trans.tipoTransaccion = 1;
                frag.setBackgroundColor(Color.parseColor("#ff99cc00"));
                break;
            case R.id.btnTransacionSalida:
                trans.tipoTransaccion = 0;
                frag.setBackgroundColor(Color.parseColor("#ffff4444"));
                break;
            default:
                break;
        }
    }

    @Override
    public void onWizardComplete() {
        super.onWizardComplete();   //Make sure to first call the super method before anything else
        Intent returnIntent = new Intent();
        if(Validate())
        {
            DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());
            Transaccion transaccion = new Transaccion(0,Double.parseDouble(trans.textoKeyPad),trans.tipoTransaccion,trans.numeroCategoria,trans.fecha_alta);
            dbHelper.Transacciones.Inserta(transaccion);
            returnIntent.putExtra("trans", trans);
            getActivity().setResult(1, returnIntent);
            getActivity().finish();     //Terminate the wizard
        }
    }

    public boolean Validate(){
        if(trans.textoKeyPad.equals(".") && trans.textoKeyPad.equals(""))
            return false;
        else if(trans.numeroCategoria == -1)
            return false;
        else
            return true;
    }

}