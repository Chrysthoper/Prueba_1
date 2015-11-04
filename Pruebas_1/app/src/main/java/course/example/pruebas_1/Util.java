package course.example.pruebas_1;

import android.graphics.Color;

import java.text.DateFormatSymbols;
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

    public static String FormatToShort(String format)
    {
        Date fecha = Util.FormatToFecha(format);
        String stringMonth = (String) android.text.format.DateFormat.format("MMM", fecha);
        String day = (String) android.text.format.DateFormat.format("dd", fecha);
        return stringMonth.toUpperCase() + " " + day;
    }

    public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    public static int[] colores = new int[]{
            R.drawable.forma_circulo1,
            R.drawable.forma_circulo2,
            R.drawable.forma_circulo3,
            R.drawable.forma_circulo4,
            R.drawable.forma_circulo5,
            R.drawable.forma_circulo6,
            R.drawable.forma_circulo7,
            R.drawable.forma_circulo8,
            R.drawable.forma_circulo9,
            R.drawable.forma_circulo10,
            R.drawable.forma_circulonegro
    };
    public static int[] coloresHex = new int[]{
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
            R.color.color10,
            Color.BLACK
    };
    public static int[] imagenes = new int[]{
            R.mipmap.gas,
            R.mipmap.chucherias,
            R.mipmap.camion,
            R.mipmap.agua,
            R.mipmap.casa,
            R.mipmap.comida,
            R.mipmap.compras,
            R.mipmap.luz,
            R.mipmap.gasolina,
            R.mipmap.pareja,
            R.mipmap.ropa,
            R.mipmap.carro,
            R.mipmap.internet,
            R.mipmap.cellphone,
            R.mipmap.credito,
            R.mipmap.trabajo,
            R.mipmap.phone,
            R.mipmap.prestamo,
            R.mipmap.monedas
    };
    public static int[] imagenesFull = new int[]{
            R.mipmap.gas,
            R.mipmap.chucherias,
            R.mipmap.camion,
            R.mipmap.agua,
            R.mipmap.casa,
            R.mipmap.comida,
            R.mipmap.compras,
            R.mipmap.luz,
            R.mipmap.gasolina,
            R.mipmap.pareja,
            R.mipmap.ropa,
            R.mipmap.carro,
            R.mipmap.internet,
            R.mipmap.cellphone,
            R.mipmap.credito,
            R.mipmap.trabajo,
            R.mipmap.phone,
            R.mipmap.prestamo,
            R.mipmap.monedas,
            R.mipmap.sin_categoria,
            R.mipmap.agregar_blanco
    };

}
