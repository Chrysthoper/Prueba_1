package course.example.pruebas_1.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Cuenta;

/**
 * Created by Chrys-Emcor on 08/10/2015.
 */
public class TD_Cuentas
{
    private DBHelper dbHelper;

    public TD_Cuentas(DBHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    public ArrayList<Cuenta> Obten(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID,
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_2,
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_3,
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_4
        };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID + " DESC";

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseSchema.TD_Cuentas.TABLE_NAME, null);

        ArrayList<Cuenta> lista = GetObject(c);
        return lista;
    }

    public Cuenta Obten(int ID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID,
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_2,
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_3,
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_4
        };
        String sortOrder = DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID + " DESC";
        Cursor c = db.query(
                DatabaseSchema.TD_Cuentas.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID + "=?",                                // The columns for the WHERE clause
                new String[]{String.valueOf(ID)},// The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Cuenta lista = GetObjectCuenta(c);
        return lista;
    }

    public Boolean Inserta(Cuenta cuenta){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.TD_Cuentas.COLUMN_NAME_2, cuenta.nombre);
        values.put(DatabaseSchema.TD_Cuentas.COLUMN_NAME_3, cuenta.resource);
        values.put(DatabaseSchema.TD_Cuentas.COLUMN_NAME_4, cuenta.color);

        long newRowId;
        newRowId = db.insert(
                DatabaseSchema.TD_Cuentas.TABLE_NAME,
                null,
                values);
        return (newRowId != 0) ? true : false;
    }

    public Boolean Elimina(int cuenta_id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                DatabaseSchema.TD_Cuentas.TABLE_NAME,
                DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID + "=" + cuenta_id,
                null) > 0;
    }

    private ArrayList<Cuenta> GetObject(Cursor c){
        ArrayList<Cuenta> lista = new ArrayList<Cuenta>();
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                Cuenta cuenta = new Cuenta();
                cuenta.id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID));
                cuenta.nombre = c.getString(c.getColumnIndex(DatabaseSchema.TD_Cuentas.COLUMN_NAME_2));
                cuenta.resource = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Cuentas.COLUMN_NAME_3));
                cuenta.color = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Cuentas.COLUMN_NAME_4));
                lista.add(cuenta);
            } while(c.moveToNext());
        }
        return lista;
    }

    private Cuenta GetObjectCuenta(Cursor c) {
        Cuenta cuenta = new Cuenta();
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                cuenta.id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID));
                cuenta.nombre = c.getString(c.getColumnIndex(DatabaseSchema.TD_Cuentas.COLUMN_NAME_2));
                cuenta.resource = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Cuentas.COLUMN_NAME_3));
                cuenta.color = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Cuentas.COLUMN_NAME_4));
            } while(c.moveToNext());
        }
        return cuenta;
    }

}
