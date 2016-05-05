package course.example.pruebas_1.Adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import course.example.pruebas_1.Negocio.Transaccion;

public class ReceptorBroadcast extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service1 = new Intent(context, NotificationService.class);
        int tipo = intent.getIntExtra("TIPO", 0);
        char op = intent.getCharExtra("OP", 'X');
        String fecha = intent.getStringExtra("FECHA");
        int cuenta_id = intent.getIntExtra("CUENTA_ID", 0);
        Transaccion trans = (Transaccion)intent.getSerializableExtra("TRANS");
        service1.putExtra("FECHA", fecha);
        service1.putExtra("TIPO", tipo);
        service1.putExtra("OP", op);
        service1.putExtra("TRANS", trans);
        service1.putExtra("CUENTA_ID", cuenta_id);
        context.startService(service1);
    }
}
