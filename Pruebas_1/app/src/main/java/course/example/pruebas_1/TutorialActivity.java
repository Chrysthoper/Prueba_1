package course.example.pruebas_1;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class TutorialActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tutorial);
        Intent intent = getIntent();
        int op = intent.getIntExtra("OP", 0);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        TutorialWizard fragment = new TutorialWizard();
        Bundle args = new Bundle();
        args.putInt("OP", op);
        fragment.setArguments(args);
        ft.add(R.id.tutorial_wizard_fragment, fragment);
        ft.commit();

    }

}
