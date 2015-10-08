package course.example.pruebas_1;

import java.io.Serializable;

/**
 * Created by Chrys-Emcor on 07/10/2015.
 */
public class Transaccion implements Serializable
{
    public String textoKeyPad;
    public int numeroCategoria;
    public int tipoTransaccion;
    public Transaccion(String textoKeyPad, int numeroCategoria, int tipoTransaccion)
    {
        this.textoKeyPad = textoKeyPad;
        this.numeroCategoria = numeroCategoria;
        this.tipoTransaccion = tipoTransaccion;
    }
    @Override
    public String toString()
    {
        return tipoTransaccion + " - " + numeroCategoria + " - " + textoKeyPad;
    }
}
