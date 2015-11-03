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
    public Cuenta cuentaPrincipalObj,cuentaSecundariaObj;
    public int cuenta_prin_id, cuenta_secu_id,tipo_transaccion;

    public Transaccion(){}

    //Dummy
    public Transaccion(String textoKeyPad, int numeroCategoria,int tipo_transaccion, String fecha_alta, String nota, String descripcion, int cuenta_prin_id, int cuenta_secu_id)
    {
        this.textoKeyPad = textoKeyPad;
        this.numeroCategoria = numeroCategoria;
        this.fecha_alta = fecha_alta;
        this.cuenta_prin_id = cuenta_prin_id;
        this.cuenta_secu_id = cuenta_secu_id;
        this.tipo_transaccion = tipo_transaccion;
        this.nota = nota;
        this.descripcion = descripcion;
    }
    public Transaccion(int id, double costo, int numeroCategoria, String fecha_alta, String nota, String descripcion, int cuenta_prin_id, int cuenta_secu_id, int tipo_transaccion)
    {
        this.id = id;
        this.costo = costo;
        this.numeroCategoria = numeroCategoria;
        this.fecha_alta = fecha_alta;
        this.nota = nota;
        this.descripcion = descripcion;
        this.cuenta_prin_id = cuenta_prin_id;
        this.cuenta_secu_id = cuenta_secu_id;
        this.tipo_transaccion = tipo_transaccion;
    }
}
