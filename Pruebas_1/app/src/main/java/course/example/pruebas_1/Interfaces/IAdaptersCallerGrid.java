package course.example.pruebas_1.Interfaces;

import java.util.ArrayList;

import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Transaccion;

/**
 * Created by Chrysthoper on 18/10/2015.
 */
public interface IAdaptersCallerGrid {
    void ActualizaGrid(ArrayList<Transaccion> ListaTransacciones);
}
