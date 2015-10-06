package course.example.pruebas_1;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
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
        setContentView(R.layout.activity_tutorial);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//Quita el Titulo a la ventana
        Intent intent = getIntent();
        int op = intent.getIntExtra("OP", 0);
        View frag = (View)findViewById(R.id.step_container);
        switch(op)
        {
            case R.id.btnTransacionEntrada:
                frag.setBackgroundColor(Color.parseColor("#ff99cc00"));
                break;
            case R.id.btnTransacionSalida:
                frag.setBackgroundColor(Color.parseColor("#ffff4444"));
                break;
            default:
                break;
        }


    }

}
