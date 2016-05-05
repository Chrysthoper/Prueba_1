package course.example.pruebas_1.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.Util;

public class TD_Transacciones
{
    private DBHelper dbHelper;

    public TD_Transacciones(DBHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    public ArrayList<Transaccion> Obten(String fechaIni, String fechaFin){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM " + DatabaseSchema.TD_Transacciones.TABLE_NAME +
                " WHERE " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_4 + " >= Datetime('" + fechaIni.substring(0,10) + "')" +
                " AND " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_4 + " < Datetime('" + fechaFin.substring(0,10) + "')" +
                " ORDER BY " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_4 + " DESC, " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " ASC", null);

        ArrayList<Transaccion> lista = GetObject(c);
        return lista;
    }

    public ArrayList<Transaccion> Obten(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
            "SELECT * FROM " + DatabaseSchema.TD_Transacciones.TABLE_NAME +
            " ORDER BY " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_4 + " DESC, " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " ASC", null);

        ArrayList<Transaccion> lista = GetObject(c);
        return lista;
    }

    public Transaccion Obten(int transaccion_id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM " + DatabaseSchema.TD_Transacciones.TABLE_NAME +
                        " WHERE " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " = " + transaccion_id +
                        " ORDER BY " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_4 + " DESC", null);

        Transaccion transaccion = GetObjectTransaccion(c);
        return transaccion;
    }

    public Transaccion Inserta(Transaccion trans){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_2, trans.costo);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_3, trans.numeroCategoria);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_4, trans.fecha_alta);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_5, trans.nota);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_6, trans.descripcion);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_7, trans.cuenta_prin_id);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_8, trans.cuenta_secu_id);
        values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_9, trans.tipo_transaccion);

        long newRowId;
        newRowId = db.insert(
                DatabaseSchema.TD_Transacciones.TABLE_NAME,
                null,
                values);
        if (newRowId > 0)
        {
            ActualizaCuentas((int) newRowId);
            return this.Obten((int) newRowId);
        }
        else
            return null;
    }

    public Transaccion Modifica(Transaccion trans){

        if(this.RestablecerCuentas(trans.id))
        {
            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_2, trans.costo);
            values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_3, trans.numeroCategoria);
            values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_4, trans.fecha_alta);
            values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_5, trans.nota);
            values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_6, trans.descripcion);
            values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_7, trans.cuenta_prin_id);
            values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_8, trans.cuenta_secu_id);
            values.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_9, trans.tipo_transaccion);

            long newRowId;
            newRowId = db.update(DatabaseSchema.TD_Transacciones.TABLE_NAME,
                    values,
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + "=" + trans.id, null);

            if (newRowId > 0)
            {
                this.ActualizaCuentas(trans.id);
                return this.Obten(trans.id);
            }
            else
                return null;
        }
        else
            return null;
    }

    public Boolean ActualizaCuentas(int transaccion_id) {
        Transaccion transaccion = this.Obten(transaccion_id);
        switch(transaccion.tipo_transaccion)
        {
            case 0:
                transaccion.cuentaPrincipalObj.total -= transaccion.costo;
                return this.dbHelper.Cuentas.Actualiza(transaccion.cuentaPrincipalObj);
            case 1:
                transaccion.cuentaPrincipalObj.total += transaccion.costo;
                return this.dbHelper.Cuentas.Actualiza(transaccion.cuentaPrincipalObj);
            case 2:
                if(transaccion.cuenta_prin_id != transaccion.cuenta_secu_id)
                {
                    transaccion.cuentaPrincipalObj.total -= transaccion.costo;
                    transaccion.cuentaSecundariaObj.total += transaccion.costo;
                    return this.dbHelper.Cuentas.Actualiza(transaccion.cuentaPrincipalObj) && this.dbHelper.Cuentas.Actualiza(transaccion.cuentaSecundariaObj);
                }
                else
                    return true;
            default:
                return false;
        }
    }

    public Boolean RestablecerCuentas(int transaccion_id) {
        Transaccion transaccion = this.Obten(transaccion_id);
        switch(transaccion.tipo_transaccion)
        {
            case 0:
                transaccion.cuentaPrincipalObj.total += transaccion.costo;
                return this.dbHelper.Cuentas.Actualiza(transaccion.cuentaPrincipalObj);
            case 1:
                transaccion.cuentaPrincipalObj.total -= transaccion.costo;
                return this.dbHelper.Cuentas.Actualiza(transaccion.cuentaPrincipalObj);
            case 2:
                if(transaccion.cuenta_prin_id != transaccion.cuenta_secu_id)
                {
                    transaccion.cuentaPrincipalObj.total += transaccion.costo;
                    transaccion.cuentaSecundariaObj.total -= transaccion.costo;
                    return this.dbHelper.Cuentas.Actualiza(transaccion.cuentaPrincipalObj) && this.dbHelper.Cuentas.Actualiza(transaccion.cuentaSecundariaObj);
                }
                else
                    return true;
            default:
                return false;
        }
    }

    public Boolean Elimina(int transaccion_id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Transaccion trans = this.Obten(transaccion_id);
        boolean actualiza = false;
        switch(trans.tipo_transaccion)
        {
            case 0:
                trans.cuentaPrincipalObj.total += trans.costo;
                actualiza = this.dbHelper.Cuentas.Actualiza(trans.cuentaPrincipalObj);
                break;
            case 1:
                trans.cuentaPrincipalObj.total -= trans.costo;
                actualiza = this.dbHelper.Cuentas.Actualiza(trans.cuentaPrincipalObj);
                break;
            case 2:
                if(trans.cuenta_prin_id != trans.cuenta_secu_id)
                {
                    trans.cuentaPrincipalObj.total += trans.costo;
                    trans.cuentaSecundariaObj.total -= trans.costo;
                    actualiza = this.dbHelper.Cuentas.Actualiza(trans.cuentaPrincipalObj) && this.dbHelper.Cuentas.Actualiza(trans.cuentaSecundariaObj);
                }
                else
                    actualiza = true;
                break;
            default:
                actualiza = false;
                break;
        }
        if(actualiza)
            return db.delete(
                    DatabaseSchema.TD_Transacciones.TABLE_NAME,
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + "=" + transaccion_id,
                    null) > 0;
        else
            return false;

    }

    public double SumatoriaSalidas(String fechaIni, String fechaFin){
        ArrayList<Transaccion> transacciones = this.Obten(fechaIni, fechaFin);
        double sum = 0;
        for(Transaccion t : transacciones)
        {
            if(t.tipo_transaccion == 0)
                sum += t.costo;
        }
        return sum;
    }

    public double SumatoriaEntradas(String fechaIni, String fechaFin){
        ArrayList<Transaccion> transacciones = this.Obten(fechaIni, fechaFin);
        double sum = 0;
        for(Transaccion t : transacciones)
        {
            if(t.tipo_transaccion == 1)
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
                transaccion.numeroCategoria = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_3));
                transaccion.fecha_alta = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_4));
                transaccion.nota = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_5));
                transaccion.descripcion = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_6));
                transaccion.cuenta_prin_id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_7));
                transaccion.cuenta_secu_id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_8));
                transaccion.tipo_transaccion = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_9));
                transaccion.categoriaObj = (transaccion.numeroCategoria != 0) ? dbHelper.Categorias.Obten(transaccion.numeroCategoria) : null;
                transaccion.cuentaPrincipalObj = dbHelper.Cuentas.Obten(transaccion.cuenta_prin_id);
                transaccion.cuentaSecundariaObj = (transaccion.cuenta_secu_id != 0) ? dbHelper.Cuentas.Obten(transaccion.cuenta_secu_id) : null;
                transaccion.textoKeyPad = "";
                lista.add(transaccion);
            } while(c.moveToNext());
        }
        return lista;
    }

    private Transaccion GetObjectTransaccion(Cursor c){
        Transaccion Transaccion = new Transaccion();
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                Transaccion.id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID));
                Transaccion.costo = c.getDouble(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_2));
                Transaccion.numeroCategoria = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_3));
                Transaccion.fecha_alta = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_4));
                Transaccion.nota = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_5));
                Transaccion.descripcion = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_6));
                Transaccion.cuenta_prin_id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_7));
                Transaccion.cuenta_secu_id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_8));
                Transaccion.tipo_transaccion = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones.COLUMN_NAME_9));
                Transaccion.categoriaObj = (Transaccion.numeroCategoria != 0) ? dbHelper.Categorias.Obten(Transaccion.numeroCategoria) : null;
                Transaccion.cuentaPrincipalObj = dbHelper.Cuentas.Obten(Transaccion.cuenta_prin_id);
                Transaccion.cuentaSecundariaObj = (Transaccion.cuenta_secu_id != 0) ? dbHelper.Cuentas.Obten(Transaccion.cuenta_secu_id) : null;
                Transaccion.textoKeyPad = "";
            } while(c.moveToNext());
        }
        return Transaccion;
    }

    public Boolean ModificaCategorias(int categoria){
            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues valores = new ContentValues();
            valores.put(DatabaseSchema.TD_Transacciones.COLUMN_NAME_3, 1);

            return db.update(DatabaseSchema.TD_Transacciones.TABLE_NAME, valores, DatabaseSchema.TD_Transacciones.COLUMN_NAME_3 + "=" + categoria, null) > 0;
    }
}
