package course.example.pruebas_1;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Chrys-Emcor on 08/10/2015.
 */
public class Util
{
    public static String PriceFormat(Double costo)
    {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return "$ " + formatter.format(costo);
    }

    public static String FechaToFormat(Date fecha)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(fecha.getTime());
    }

    public static Date FormatToFecha(String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(format);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
