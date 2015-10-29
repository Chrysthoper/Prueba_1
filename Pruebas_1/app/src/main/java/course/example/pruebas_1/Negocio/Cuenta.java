package course.example.pruebas_1.Negocio;

import java.io.Serializable;

/**
 * Created by Chrys-Emcor on 06/10/2015.
 */
public class Cuenta implements Serializable
{
    public int id;
    public String nombre;
    public int resource;
    public int color;
    public double total;

    public Cuenta(){}

    public Cuenta(int id, String nombre, int resource, int color)
    {
        this.id = id;
        this.nombre = nombre;
        this.resource = resource;
        this.color = color;
    }
}
