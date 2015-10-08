package course.example.pruebas_1;

import java.text.DecimalFormat;

/**
 * Created by Chrys-Emcor on 08/10/2015.
 */
public class Util
{
    public static Categoria[] categorias = new Categoria[]{
            new Categoria(1,"Agua",R.mipmap.agua),
            new Categoria(2,"Electricidad",R.mipmap.electricidad),
            new Categoria(3,"Gas",R.mipmap.gas),
            new Categoria(4,"Gasolina",R.mipmap.gasolina),
            new Categoria(5,"Comida",R.mipmap.comida),
            new Categoria(6,"Ropa",R.mipmap.ropa),
            new Categoria(7,"Cochinero",R.mipmap.chucherias),
            new Categoria(8,"Novia",R.mipmap.pareja),
            new Categoria(9,"Camiones",R.mipmap.camion)
    };

    public static String PriceFormat(Double costo)
    {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return "$ " + formatter.format(costo);
    }

}
