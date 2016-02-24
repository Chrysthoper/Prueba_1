package course.example.pruebas_1.Negocio;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Chrys-Emcor on 07/10/2015.
 */
public class TransaccionProgramada implements Serializable
{
    public int id;
    public int numeroCategoria;
    public double costo;
    public String fecha_alta, nota,descripcion, intervalo, fecha_siguiente;
    public int cuenta_prin_id, tipo_transaccion;
    public Boolean activa;
    public Categoria categoriaObj;
    public Cuenta cuentaPrincipalObj;

    public TransaccionProgramada(){}

    public TransaccionProgramada(int id, double costo, int numeroCategoria, String fecha_alta, String nota, String descripcion, int cuenta_prin_id, int tipo_transaccion, String fecha_siguiente, String intervalo, Boolean activa)
    {
        this.id = id;
        this.costo = costo;
        this.numeroCategoria = numeroCategoria;
        this.fecha_alta = fecha_alta;
        this.nota = nota;
        this.descripcion = descripcion;
        this.cuenta_prin_id = cuenta_prin_id;
        this.tipo_transaccion = tipo_transaccion;
        this.fecha_siguiente = fecha_siguiente;
        this.intervalo = intervalo;
        this.activa = activa;
    }
}
