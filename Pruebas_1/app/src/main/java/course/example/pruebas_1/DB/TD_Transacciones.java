package course.example.pruebas_1.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import course.example.pruebas_1.Transaccion;

/**
 * Created by Chrys-Emcor on 08/10/2015.
 */
public class TD_Transacciones
{
    private Context context;
    public TD_Transacciones(Context context)
    {
        this.context = context;
    }

    public List<Transaccion> Obten()
    {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseSchema.TD_Transacciones._ID,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_2,
                DatabaseSchema.TD_Transacciones.COLUMN_NAME_3
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

        return null;
    }
}
