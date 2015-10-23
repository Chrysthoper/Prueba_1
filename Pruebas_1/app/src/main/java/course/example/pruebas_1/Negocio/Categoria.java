package course.example.pruebas_1.Negocio;

import java.io.Serializable;

/**
 * Created by Chrys-Emcor on 06/10/2015.
 */
public class Categoria implements Serializable
{
    public int id;
    public String nombre;
    public int resource;
    public int color;
    public int formaCirculo;
    public double total;

    public Categoria(){}

    public Categoria(int id, String nombre, int resource, int formaCirculo, int color)
    {
        this.id = id;
        this.nombre = nombre;
        this.resource = resource;
        this.color = color;
        this.formaCirculo = formaCirculo;
    }

    public Categoria(int id, String nombre, int resource, int formaCirculo, int color, double total)
    {
        this.id = id;
        this.nombre = nombre;
        this.resource = resource;
        this.color = color;
        this.formaCirculo = formaCirculo;
        this.total = total;
    }
}
