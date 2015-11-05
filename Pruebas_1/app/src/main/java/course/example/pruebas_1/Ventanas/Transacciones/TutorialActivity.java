package course.example.pruebas_1.Ventanas.Transacciones;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import course.example.pruebas_1.R;


public class TutorialActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ventana_transaccion);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int op = intent.getIntExtra("OP", 0);
            String fecha = intent.getStringExtra("FECHA");
            int cuenta_id = intent.getIntExtra("CUENTA_ID",0);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            TutorialWizard fragment = new TutorialWizard();
            Bundle args = new Bundle();
            args.putInt("OP", op);
            args.putString("FECHA", fecha);
            args.putInt("CUENTA_ID", cuenta_id);
            fragment.setArguments(args);
            //ft.add(R.id.tutorial_wizard_fragment, fragment);
            ft.replace(R.id.tutorial_wizard_fragment, fragment, "your_fragment_tag");
            ft.commit();
        } else {
            TutorialWizard test = (TutorialWizard) getSupportFragmentManager().findFragmentByTag("your_fragment_tag");
        }


    }

}
