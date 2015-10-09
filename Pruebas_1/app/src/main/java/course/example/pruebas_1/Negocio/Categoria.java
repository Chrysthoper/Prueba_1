package course.example.pruebas_1.Negocio;

/**
 * Created by Chrys-Emcor on 06/10/2015.
 */
public class Categoria
{
    public int id;
    public String nombre;
    public int resource;
    public int color;
    public int formaCirculo;

    public Categoria(){}

    public Categoria(int id, String nombre, int resource, int formaCirculo, int color)
    {
        this.id = id;
        this.nombre = nombre;
        this.resource = resource;
        this.color = color;
        this.formaCirculo = formaCirculo;
    }
}
