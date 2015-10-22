package course.example.pruebas_1.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Util;

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

    public ArrayList<Transaccion> Obten(String fechaIni, String fechaFin){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_2,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_3,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_4,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_5,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_6,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_7
        };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " DESC";

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseSchema.TD_Transacciones.TABLE_NAME +
                        " WHERE " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_5 + " > Datetime('" + fechaIni.substring(0,10) + "')" +
                        " AND " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_5 + " < Datetime('" + fechaFin.substring(0,10) + "')" +
                        " ORDER BY " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_5 + " DESC, " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " ASC", null);

        ArrayList<Transaccion> lista = GetObject(c);
        return lista;
    }

    public Boolean Inserta(Transaccion trans){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_2, trans.costo);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_3, trans.tipoTransaccion);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_4, trans.numeroCategoria);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_5, trans.fecha_alta);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_6, trans.nota);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_7, trans.descripcion);

        long newRowId;
        newRowId = db.insert(
                DatabaseSchema.TD_Transacciones.TABLE_NAME,
                null,
                values);
        return (newRowId != 0) ? true : false;
    }

    public Boolean Elimina(int transaccion_id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                DatabaseSchema.TD_Transacciones.TABLE_NAME,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + "=" + transaccion_id,
                null) > 0;
    }

    public double SumatoriaSalidas(String fechaIni, String fechaFin){
        ArrayList<Transaccion> transacciones = this.Obten(fechaIni, fechaFin);
        double sum = 0;
        for(Transaccion t : transacciones)
        {
            if(t.tipoTransaccion == 0)
                sum += t.costo;
        }
        return sum;
    }

    public double SumatoriaEntradas(String fechaIni, String fechaFin){
        ArrayList<Transaccion> transacciones = this.Obten(fechaIni, fechaFin);
        double sum = 0;
        for(Transaccion t : transacciones)
        {
            if(t.tipoTransaccion == 1)
                sum += t.costo;
        }
        return sum;
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
                transaccion.fecha_alta = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_5)).substring(0,10);
                transaccion.nota = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_6));
                transaccion.descripcion = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_7));
                lista.add(transaccion);
            } while(c.moveToNext());
        }
        return lista;
    }

}
