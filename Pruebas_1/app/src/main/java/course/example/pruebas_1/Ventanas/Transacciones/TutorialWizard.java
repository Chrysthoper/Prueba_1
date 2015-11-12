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
    private Transaccion trans = new Transaccion("",-1,-1,"","","",0,0);
    @ContextVariable
    private char op = 'X';

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
        int tipo = 0;
        int cuenta_id = 0;
        Transaccion transaccion;
        if (savedInstanceState != null) {
            // Restore last state
            fecha = savedInstanceState.getString("FECHA");
            tipo = savedInstanceState.getInt("TIPO");
            cuenta_id = savedInstanceState.getInt("CUENTA_ID");
            op = savedInstanceState.getChar("OP");
            transaccion = (Transaccion)savedInstanceState.getSerializable("TRANS");
            this.trans.fecha_alta = fecha;
            this.trans.tipo_transaccion = tipo;
            this.trans.cuenta_prin_id = cuenta_id;
        } else {
            fecha = getArguments().getString("FECHA");
            tipo = getArguments().getInt("TIPO", 0);
            cuenta_id = getArguments().getInt("CUENTA_ID", 0);
            op = getArguments().getChar("OP");
            transaccion = (Transaccion)getArguments().getSerializable("TRANS");
            this.trans.fecha_alta = fecha;
            this.trans.tipo_transaccion = tipo;
            this.trans.cuenta_prin_id = cuenta_id;
        }

        switch(tipo)
        {
            case R.id.btnEntrada: case 1:
                this.trans.tipo_transaccion = 1;
                frag.setBackgroundColor(Color.parseColor("#ff99cc00"));
                break;
            case R.id.btnSalida: case 0:
                this.trans.tipo_transaccion = 0;
                frag.setBackgroundColor(Color.parseColor("#ffff4444"));
                break;
            case R.id.lyTransferenciaPrincipal:case 2:
                this.trans.tipo_transaccion = 2;
                frag.setBackgroundColor(Color.BLACK);
                break;
            default:
                break;
        }

        if(op == 'C')
        {
            this.trans = transaccion;
            this.trans.textoKeyPad = String.valueOf(trans.costo);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("FECHA", trans.fecha_alta);
        outState.putInt("TIPO", trans.tipo_transaccion);
        outState.putInt("CUENTA_ID", trans.cuenta_prin_id);
        outState.putChar("OP", op);
        outState.putSerializable("TRANS", trans);
    }

    @Override
    public void onWizardComplete() {
        super.onWizardComplete();
        Intent returnIntent = new Intent();
        String error = Validate();
        if(error.equals(""))
        {
            DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext());
            Transaccion transaccion = new Transaccion(trans.id,Double.parseDouble(trans.textoKeyPad),trans.numeroCategoria,trans.fecha_alta,trans.nota,trans.descripcion,trans.cuenta_prin_id,trans.cuenta_secu_id,trans.tipo_transaccion);
            if(op == 'C')
                dbHelper.Transacciones.Modifica(transaccion);
            else
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
        else if(trans.tipo_transaccion < 2)
        {
            if(trans.numeroCategoria == -1)
                return "No se ha seleccionado una categoria";
            else
                return "";
        }
        else
        {
            if(trans.cuenta_secu_id == -1)
                return "No se ha seleccionado una cuenta";
            else
                return "";
        }

    }

}