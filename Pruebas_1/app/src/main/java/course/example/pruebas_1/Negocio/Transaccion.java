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
    public double costo;
    public String fecha_alta,nota,descripcion;
    public Categoria categoriaObj;
    public Cuenta cuentaObj;
    public int cuenta_id,tipo_transaccion;

    public Transaccion(){}

    //Dummy
    public Transaccion(String textoKeyPad, int numeroCategoria,int tipo_transaccion, String fecha_alta, String nota, String descripcion, int cuenta_id)
    {
        this.textoKeyPad = textoKeyPad;
        this.numeroCategoria = numeroCategoria;
        this.fecha_alta = fecha_alta;
        this.cuenta_id = cuenta_id;
        this.tipo_transaccion = tipo_transaccion;
    }
    public Transaccion(int id, double costo, int numeroCategoria, String fecha_alta, String nota, String descripcion, int cuenta_id)
    {
        this.id = id;
        this.costo = costo;
        this.numeroCategoria = numeroCategoria;
        this.fecha_alta = fecha_alta;
        this.nota = nota;
        this.descripcion = descripcion;
        this.cuenta_id = cuenta_id;
    }
}
