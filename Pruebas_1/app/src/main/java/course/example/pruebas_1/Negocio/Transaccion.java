package course.example.pruebas_1.Negocio;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Chrys-Emcor on 07/10/2015.
 */
public class Transaccion implements Serializable
{
    public int id;
    public String textoKeyPad;
    public int numeroCategoria;
    public int tipoTransaccion;
    public double costo;
    public String fecha_alta,nota,descripcion;

    public Transaccion(){}

    //Dummy
    public Transaccion(String textoKeyPad, int tipoTransaccion, int numeroCategoria, String fecha_alta, String nota, String descripcion)
    {
        this.textoKeyPad = textoKeyPad;
        this.numeroCategoria = numeroCategoria;
        this.tipoTransaccion = tipoTransaccion;
        this.fecha_alta = fecha_alta;
    }
    public Transaccion(int id, double costo, int tipoTransaccion, int numeroCategoria, String fecha_alta, String nota, String descripcion)
    {
        this.id = id;
        this.costo = costo;
        this.numeroCategoria = numeroCategoria;
        this.tipoTransaccion = tipoTransaccion;
        this.fecha_alta = fecha_alta;
        this.nota = nota;
        this.descripcion = descripcion;
    }
}
