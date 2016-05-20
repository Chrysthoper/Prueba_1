package course.example.pruebas_1.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import course.example.pruebas_1.DB.DatabaseSchema;
import course.example.pruebas_1.Negocio.Categoria;
import course.example.pruebas_1.Negocio.Cuenta;
import course.example.pruebas_1.Negocio.ExportObj;
import course.example.pruebas_1.Negocio.Transaccion;
import course.example.pruebas_1.R;

public class DBHelper extends SQLiteOpenHelper {

    public TD_Categorias Categorias;
    public TD_Transacciones Transacciones;
    public TD_Transacciones_Programadas Transacciones_Programadas;
    public TD_Cuentas Cuentas;

    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "MiCochinito.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String DATETIME_TYPE = " DATETIME";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_CATEGORIAS =
            "CREATE TABLE " + DatabaseSchema.TD_Categorias.TABLE_NAME +
                    " (" +
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_2 + TEXT_TYPE + COMMA_SEP +
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_3 + INTEGER_TYPE + COMMA_SEP +
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_4 + INTEGER_TYPE + COMMA_SEP +
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_5 + INTEGER_TYPE + COMMA_SEP +
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_6 + INTEGER_TYPE +
                    " )";
    private static final String SQL_CREATE_TRANSACCIONES =
            "CREATE TABLE " + DatabaseSchema.TD_Transacciones.TABLE_NAME +
                    " (" +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_2 + DOUBLE_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_3 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_4 + TEXT_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_5 + DATETIME_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_6 + TEXT_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_7 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_8 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_9 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_10 + INTEGER_TYPE +
                    " )";

    private static final String SQL_CREATE_TRANSACCIONES_PROGRAMADAS =
            "CREATE TABLE " + DatabaseSchema.TD_Transacciones_Programadas.TABLE_NAME +
                    " (" +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_2 + DOUBLE_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_3 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_4 + TEXT_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_5 + DATETIME_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_6 + TEXT_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_7 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_8 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_9 + TEXT_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_10 + TEXT_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones_Programadas.COLUMN_NAME_11 + INTEGER_TYPE +
                    " )";

    private static final String SQL_CREATE_CUENTAS =
            "CREATE TABLE " + DatabaseSchema.TD_Cuentas.TABLE_NAME +
                    " (" +
                    DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseSchema.TD_Cuentas.COLUMN_NAME_2 + TEXT_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Cuentas.COLUMN_NAME_3 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Cuentas.COLUMN_NAME_4 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Cuentas.COLUMN_NAME_5 + DOUBLE_TYPE +
                    " )";

    private static final String SQL_DELETE_CATEGORIAS = "DROP TABLE IF EXISTS " + DatabaseSchema.TD_Categorias.TABLE_NAME;
    private static final String SQL_DELETE_TRANSACCIONES = "DROP TABLE IF EXISTS " + DatabaseSchema.TD_Transacciones.TABLE_NAME;
    private static final String SQL_DELETE_CUENTAS = "DROP TABLE IF EXISTS " + DatabaseSchema.TD_Cuentas.TABLE_NAME;
    private static final String SQL_DELETE_TRANSACCIONES_PROGRAMADAS = "DROP TABLE IF EXISTS " + DatabaseSchema.TD_Transacciones_Programadas.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Categorias = new TD_Categorias(this);
        Transacciones = new TD_Transacciones(this);
        Cuentas = new TD_Cuentas(this);
        Transacciones_Programadas = new TD_Transacciones_Programadas(this);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CATEGORIAS);
        db.execSQL(SQL_CREATE_TRANSACCIONES);
        db.execSQL(SQL_CREATE_TRANSACCIONES_PROGRAMADAS);
        db.execSQL(SQL_CREATE_CUENTAS);
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('SIN CATEGORIA',19,10,10,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('SUELDO',15,6,6,1)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('CREDITO',14,0,0,1)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('PRESTAMO',17,8,8,1)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('AGUA',3,0,0,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('LUZ',7,1,1,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('CABLE',4,2,2,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('CAMION',2,3,3,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('CHUCHERIAS',1,4,4,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('COMIDA',5,5,5,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('COMPRAS',6,6,6,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('GAS',0,7,7,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('GASOLINA',8,8,8,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('PAREJA',9,9,9,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('ROPA',10,9,9,0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Cuentas.TABLE_NAME + " (NOMBRE,RECURSO_ID,COLOR_ID) VALUES ('BANAMEX',14,3)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Cuentas.TABLE_NAME + " (NOMBRE,RECURSO_ID,COLOR_ID) VALUES ('BANCOMER',14,6)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Cuentas.TABLE_NAME + " (NOMBRE,RECURSO_ID,COLOR_ID) VALUES ('EFECTIVO',18,9)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Cuentas.TABLE_NAME + " (NOMBRE,RECURSO_ID,COLOR_ID) VALUES ('FINANCIAMIENTO',17,1)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_CATEGORIAS);
        db.execSQL(SQL_DELETE_TRANSACCIONES);
        db.execSQL(SQL_DELETE_TRANSACCIONES_PROGRAMADAS);
        db.execSQL(SQL_DELETE_CUENTAS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public String onImport(ExportObj export)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(SQL_DELETE_TRANSACCIONES);
            db.execSQL(SQL_DELETE_TRANSACCIONES_PROGRAMADAS);

            List<Cuenta> ListaCuentas = this.Cuentas.Obten();
            List<Categoria> ListaCategorias = this.Categorias.Obten();

            db.execSQL(SQL_DELETE_CATEGORIAS);
            db.execSQL(SQL_DELETE_CUENTAS);

            db.execSQL(SQL_CREATE_CATEGORIAS);
            db.execSQL(SQL_CREATE_TRANSACCIONES);
            db.execSQL(SQL_CREATE_TRANSACCIONES_PROGRAMADAS);
            db.execSQL(SQL_CREATE_CUENTAS);

            for(Categoria cat: export.ListaCategorias)
            {
                this.Categorias.Inserta(cat);
            }
            for(Cuenta c: export.ListaCuentas)
            {
                this.Cuentas.Inserta(c);
            }
            for(Transaccion trans: export.ListaTransacciones)
            {
                for(Cuenta cuenta:ListaCuentas)
                {
                    if(cuenta.id == trans.cuenta_prin_id)
                    {

                    }
                }
                this.Transacciones.Inserta(trans);
            }
            return "La Restauración de la base de datos fue exitosa!!!";
        }
        catch(Exception e)
        {
            return "Ocurrió un error en la restauración";
        }
    }

}


