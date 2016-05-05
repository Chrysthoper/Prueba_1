package course.example.pruebas_1.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import course.example.pruebas_1.Negocio.TransaccionProgramada;
import course.example.pruebas_1.Util;

public class TD_Transacciones_Programadas
{
    private DBHelper dbHelper;

    public TD_Transacciones_Programadas(DBHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    public ArrayList<TransaccionProgramada> Obten(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
            "SELECT * FROM " + DatabaseSchema.TD_Transacciones_Programadas.TABLE_NAME +
            " ORDER BY " + DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_4 + " DESC, " + DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " ASC", null);

        ArrayList<TransaccionProgramada> lista = GetObject(c);
        return lista;
    }

    public TransaccionProgramada Obten(int transaccion_id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM " + DatabaseSchema.TD_Transacciones_Programadas.TABLE_NAME +
                        " WHERE " + DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_ID + " = " + transaccion_id +
                        " ORDER BY " + DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_4 + " DESC", null);

        TransaccionProgramada transaccion = GetObjectTransaccion(c);
        return transaccion;
    }

    public TransaccionProgramada Inserta(TransaccionProgramada trans){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Calendar c = Calendar.getInstance();
        c.setTime(Util.FormatToFecha(trans.fecha_siguiente));
        if(trans.intervalo == "DIARIO")
            c.add(Calendar.DAY_OF_YEAR,1);
        else if(trans.intervalo == "SEMANAL")
            c.add(Calendar.WEEK_OF_YEAR,1);
        else if(trans.intervalo == "MENSUAL")
            c.add(Calendar.MONTH,1);
        else if(trans.intervalo == "ANUAL")
            c.add(Calendar.YEAR,1);
        trans.fecha_siguiente = Util.FechaToFormat(c.getTime());

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_2, trans.costo);
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_3, trans.numeroCategoria);
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_4, trans.fecha_alta);
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_5, trans.nota);
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_6, trans.descripcion);
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_7, trans.cuenta_prin_id);
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_8, trans.tipo_transaccion);
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_9, trans.fecha_siguiente);
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_10, trans.intervalo);
        values.put(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_11, trans.activa);

        long newRowId;
        newRowId = db.insert(
                DatabaseSchema.TD_Transacciones_Programadas.TABLE_NAME,
                null,
                values);
        if (newRowId > 0)
        {
            return this.Obten((int) newRowId);
        }
        else
            return null;
    }

    private ArrayList<TransaccionProgramada> GetObject(Cursor c){
        ArrayList<TransaccionProgramada> lista = new ArrayList<TransaccionProgramada>();
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                TransaccionProgramada transaccion = new TransaccionProgramada();
                transaccion.id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_ID));
                transaccion.costo = c.getDouble(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_2));
                transaccion.numeroCategoria = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_3));
                transaccion.fecha_alta = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_4));
                transaccion.nota = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_5));
                transaccion.descripcion = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_6));
                transaccion.cuenta_prin_id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_7));
                transaccion.tipo_transaccion = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_8));
                transaccion.fecha_siguiente = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_9));
                transaccion.intervalo = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_10));
                transaccion.activa = (c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_11)) == 1) ? true : false;
                transaccion.categoriaObj = (transaccion.numeroCategoria != 0) ? dbHelper.Categorias.Obten(transaccion.numeroCategoria) : null;
                transaccion.cuentaPrincipalObj = dbHelper.Cuentas.Obten(transaccion.cuenta_prin_id);
                lista.add(transaccion);
            } while(c.moveToNext());
        }
        return lista;
    }

    private TransaccionProgramada GetObjectTransaccion(Cursor c){
        TransaccionProgramada Transaccion = new TransaccionProgramada();
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                Transaccion.id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_ID));
                Transaccion.costo = c.getDouble(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_2));
                Transaccion.numeroCategoria = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_3));
                Transaccion.fecha_alta = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_4));
                Transaccion.nota = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_5));
                Transaccion.descripcion = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_6));
                Transaccion.cuenta_prin_id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_7));
                Transaccion.tipo_transaccion = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_8));
                Transaccion.fecha_siguiente = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_9));
                Transaccion.intervalo = c.getString(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_10));
                Transaccion.activa = (c.getInt(c.getColumnIndex(DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_11)) == 1) ? true : false;
                Transaccion.categoriaObj = (Transaccion.numeroCategoria != 0) ? dbHelper.Categorias.Obten(Transaccion.numeroCategoria) : null;
                Transaccion.cuentaPrincipalObj = dbHelper.Cuentas.Obten(Transaccion.cuenta_prin_id);
            } while(c.moveToNext());
        }
        return Transaccion;
    }
}
