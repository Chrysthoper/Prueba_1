package course.example.pruebas_1.Negocio;

import java.io.Serializable;

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

    public Transaccion(){}

    public Transaccion(String textoKeyPad, int tipoTransaccion, int numeroCategoria)
    {
        this.textoKeyPad = textoKeyPad;
        this.numeroCategoria = numeroCategoria;
        this.tipoTransaccion = tipoTransaccion;
    }
    public Transaccion(int id, double costo, int tipoTransaccion, int numeroCategoria)
    {
        this.id = id;
        this.costo = costo;
        this.numeroCategoria = numeroCategoria;
        this.tipoTransaccion = tipoTransaccion;
    }
    @Override
    public String toString()
    {
        return tipoTransaccion + " - " + numeroCategoria + " - " + textoKeyPad;
    }
}
