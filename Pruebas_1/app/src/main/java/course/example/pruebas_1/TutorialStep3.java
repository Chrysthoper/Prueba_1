package course.example.pruebas_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TutorialStep3 extends WizardStep {

    @ContextVariable
    private String textoKeyPad2;
    @ContextVariable
    private int numeroCategoria;


    TextView tvCostoTransaccion;

    //Wire the layout to the step
    public TutorialStep3() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.step1_tutorial, container, false);
        tvCostoTransaccion = (TextView) v.findViewById(R.id.tvCostoTransaccion);
        if(!textoKeyPad2.equals(""))
            IngresaCosto(textoKeyPad2);
        Button btn1 = (Button)v.findViewById(R.id.btn1);
        Button btn2 = (Button)v.findViewById(R.id.btn2);
        Button btn3 = (Button)v.findViewById(R.id.btn3);
        Button btn4 = (Button)v.findViewById(R.id.btn4);
        Button btn5 = (Button)v.findViewById(R.id.btn5);
        Button btn6 = (Button)v.findViewById(R.id.btn6);
        Button btn7 = (Button)v.findViewById(R.id.btn7);
        Button btn8 = (Button)v.findViewById(R.id.btn8);
        Button btn9 = (Button)v.findViewById(R.id.btn9);
        Button btnZero = (Button)v.findViewById(R.id.btnZero);
        Button btnDel = (Button)v.findViewById(R.id.btnDel);
        Button btnPunto = (Button)v.findViewById(R.id.btnPunto);
        btn1.setOnClickListener(clickEvent);
        btn2.setOnClickListener(clickEvent);
        btn3.setOnClickListener(clickEvent);
        btn4.setOnClickListener(clickEvent);
        btn5.setOnClickListener(clickEvent);
        btn6.setOnClickListener(clickEvent);
        btn7.setOnClickListener(clickEvent);
        btn8.setOnClickListener(clickEvent);
        btn9.setOnClickListener(clickEvent);
        btnZero.setOnClickListener(clickEvent);
        btnDel.setOnClickListener(clickEvent);
        btnPunto.setOnClickListener(clickEvent);
        return v;
    }

    View.OnClickListener clickEvent = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            Button b = (Button)v;
            String texto = b.getText().toString();
            if(!texto.equals("DEL"))
            {
                //String textoCosto = tvCostoTransaccion.getText() + texto;
                IngresaCosto(textoKeyPad2 + texto);
            }
            else
            {
                textoKeyPad2 = "";
                tvCostoTransaccion.setText("");
            }
        }
    };

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                bindDataFields();
                break;
            case WizardStep.EXIT_PREVIOUS:
                break;
        }
    }

    private void bindDataFields() {
        //The values of these fields will be automatically stored in the wizard context
        //and will be populated in the next steps only if the same field names are used.
        Toast.makeText(getActivity(), "La categoria seleccionada es:" + numeroCategoria, Toast.LENGTH_SHORT).show();
    }

    public void IngresaCosto(String costo)
    {
        Pattern patron = Pattern.compile("^([1-9][0-9]{0,9})?(\\.[0-9]{0,2})?$");
        Matcher matcher = patron.matcher(costo);
        if(matcher.matches())
        {
            textoKeyPad2 = costo;

            if(textoKeyPad2.startsWith("."))
                tvCostoTransaccion.setText("$ 0" + textoKeyPad2);
            else
            {
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                tvCostoTransaccion.setText("$ " + formatter.format(Double.parseDouble(textoKeyPad2)));
            }
        }
    }
}
