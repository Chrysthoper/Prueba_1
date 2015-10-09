package course.example.pruebas_1.DB;

import android.content.ContentValues;
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
public class TD_Transacciones
{
    private DBHelper dbHelper;

    public TD_Transacciones(DBHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    public ArrayList<Transaccion> Obten(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_2,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_3,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_4
        };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " DESC";

        Cursor c = db.query(
                DatabaseSchema.TD_Transacciones.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        ArrayList<Transaccion> lista = GetObject(c);
        return lista;
    }

    public Boolean Inserta(Transaccion trans)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_2, trans.costo);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_3, trans.tipoTransaccion);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_4, trans.numeroCategoria);

        long newRowId;
        newRowId = db.insert(
                DatabaseSchema.TD_Transacciones.TABLE_NAME,
                null,
                values);
        return (newRowId != 0) ? true : false;
    }

    private ArrayList<Transaccion> GetObject(Cursor c){
        ArrayList<Transaccion> lista = new ArrayList<Transaccion>();
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                Transaccion transaccion = new Transaccion();
                transaccion.id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID));
                transaccion.costo = c.getDouble(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_2));
                transaccion.tipoTransaccion = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_3));
                transaccion.numeroCategoria = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_4));
                lista.add(transaccion);
            } while(c.moveToNext());
        }
        return lista;
    }

}
