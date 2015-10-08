package course.example.pruebas_1.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import course.example.pruebas_1.Categoria;
import course.example.pruebas_1.Transaccion;

/**
 * Created by Chrys-Emcor on 08/10/2015.
 */
public class TD_Categorias
{
    private DBHelper dbHelper;
    public TD_Categorias(DBHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    public ArrayList<Categoria> Obten(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseSchema.TD_Categorias.COLUMN_NAME_ID,
                DatabaseSchema.TD_Categorias.COLUMN_NAME_2,
                DatabaseSchema.TD_Categorias.COLUMN_NAME_3
        };
        String sortOrder = DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " DESC";
        Cursor c = db.query(
                DatabaseSchema.TD_Categorias.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        ArrayList<Categoria> lista = GetObject(c);
        return lista;
    }

    private ArrayList<Categoria> GetObject(Cursor c){
        ArrayList<Categoria> lista = new ArrayList<Categoria>();
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                Categoria categoria = new Categoria();
                categoria.id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_ID));
                categoria.nombre = c.getString(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_2));
                categoria.resource = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_3));
                lista.add(categoria);
            } while(c.moveToNext());
        }
        return lista;
    }
}
