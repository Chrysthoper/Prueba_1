package course.example.pruebas_1.Adapters;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;
import course.example.pruebas_1.Util;
import course.example.pruebas_1.Ventanas.Transacciones.TutorialActivity;


public class NotificationService extends Service
{

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        int tipo = intent.getIntExtra("TIPO", 0);
        char op = intent.getCharExtra("OP", 'X');
        String fecha = intent.getStringExtra("FECHA");
        int cuenta_id = intent.getIntExtra("CUENTA_ID", 0);
        Transaccion trans = (Transaccion)intent.getSerializableExtra("TRANS");

        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), TutorialActivity.class);

        intent1.putExtra("FECHA", fecha);
        intent1.putExtra("TIPO", tipo);
        intent1.putExtra("OP", op);
        intent1.putExtra("TRANS", trans);
        intent1.putExtra("CUENTA_ID", cuenta_id);

        Notification notification = new Notification(Util.imagenesFull[trans.categoriaObj.resource],"Transacción Calendarizada!", System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(this.getApplicationContext(), trans.descripcion, "¿Ya realizaste esta transacción?", pendingNotificationIntent);

        mManager.notify(0, notification);
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
