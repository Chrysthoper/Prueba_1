package course.example.pruebas_1.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import course.example.pruebas_1.Negocio.Categoria;

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
                DatabaseSchema.TD_Categorias.COLUMN_NAME_3,
                DatabaseSchema.TD_Categorias.COLUMN_NAME_4,
                DatabaseSchema.TD_Categorias.COLUMN_NAME_5
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

    public Categoria Obten(int ID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseSchema.TD_Categorias.COLUMN_NAME_ID,
                DatabaseSchema.TD_Categorias.COLUMN_NAME_2,
                DatabaseSchema.TD_Categorias.COLUMN_NAME_3,
                DatabaseSchema.TD_Categorias.COLUMN_NAME_4,
                DatabaseSchema.TD_Categorias.COLUMN_NAME_5
        };
        String sortOrder = DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " DESC";
        Cursor c = db.query(
                DatabaseSchema.TD_Categorias.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                DatabaseSchema.TD_Categorias.COLUMN_NAME_ID + "=?",                                // The columns for the WHERE clause
                new String[] { String.valueOf(ID) },// The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Categoria lista = GetObjectCategoria(c);
        return lista;
    }

    public Boolean Inserta(Categoria cat) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.TD_Categorias.COLUMN_NAME_2, cat.nombre);
        values.put(DatabaseSchema.TD_Categorias.COLUMN_NAME_3, cat.resource);
        values.put(DatabaseSchema.TD_Categorias.COLUMN_NAME_4, cat.formaCirculo);
        values.put(DatabaseSchema.TD_Categorias.COLUMN_NAME_5, cat.color);

        long newRowId;
        newRowId = db.insert(
                DatabaseSchema.TD_Categorias.TABLE_NAME,
                null,
                values);
        return (newRowId != 0) ? true : false;
    }

    public Boolean Elimina(int categoria_id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                DatabaseSchema.TD_Categorias.TABLE_NAME,
                DatabaseSchema.TD_Categorias.COLUMN_NAME_ID + "=" + categoria_id,
                null) > 0;
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
                categoria.formaCirculo = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_4));
                categoria.color = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_5));
                lista.add(categoria);
            } while(c.moveToNext());
        }
        return lista;
    }

    private Categoria GetObjectCategoria(Cursor c) {
        Categoria categoria = new Categoria();
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                categoria.id = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_ID));
                categoria.nombre = c.getString(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_2));
                categoria.resource = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_3));
                categoria.formaCirculo = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_4));
                categoria.color = c.getInt(c.getColumnIndex(DatabaseSchema.TD_Categorias.COLUMN_NAME_5));
            } while(c.moveToNext());
        }
        return categoria;
    }
}
