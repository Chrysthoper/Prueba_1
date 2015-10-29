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

import course.example.pruebas_1.DB.DatabaseSchema;
import course.example.pruebas_1.R;

public class DBHelper extends SQLiteOpenHelper {

    public TD_Categorias Categorias;
    public TD_Transacciones Transacciones;
    public TD_Cuentas Cuentas;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "MiChochinito.db";

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
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_4 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_5 + DATETIME_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_6 + TEXT_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_7 + TEXT_TYPE +
                    " )";
    private static final String SQL_CREATE_CUENTAS =
            "CREATE TABLE " + DatabaseSchema.TD_Cuentas.TABLE_NAME +
                    " (" +
                    DatabaseSchema.TD_Cuentas.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseSchema.TD_Cuentas.COLUMN_NAME_2 + TEXT_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Cuentas.COLUMN_NAME_3 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Cuentas.COLUMN_NAME_4 + INTEGER_TYPE +
                    " )";

    private static final String SQL_DELETE_CATEGORIAS = "DROP TABLE IF EXISTS " + DatabaseSchema.TD_Categorias.TABLE_NAME;
    private static final String SQL_DELETE_TRANSACCIONES = "DROP TABLE IF EXISTS " + DatabaseSchema.TD_Transacciones.TABLE_NAME;
    private static final String SQL_DELETE_CUENTAS = "DROP TABLE IF EXISTS " + DatabaseSchema.TD_Cuentas.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Categorias = new TD_Categorias(this);
        Transacciones = new TD_Transacciones(this);
        Cuentas = new TD_Cuentas(this);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CATEGORIAS);
        db.execSQL(SQL_CREATE_TRANSACCIONES);
        db.execSQL(SQL_CREATE_CUENTAS);
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('SIN CATEGORIA'," + R.mipmap.sin_categoria + "," + R.drawable.forma_circulonegro + "," + Color.BLACK + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('SUELDO'," + R.mipmap.trabajo + "," + R.drawable.forma_circulo7 + "," + R.color.color7 + ",1)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('FINANCIAMIENTO'," + R.mipmap.prestamo + "," + R.drawable.forma_circulo9 + "," + R.color.color9 + ",1)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('AGUA'," + R.mipmap.agua + "," + R.drawable.forma_circulo1 + "," + R.color.color1 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('LUZ'," + R.mipmap.luz + "," + R.drawable.forma_circulo2 + "," + R.color.color2 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('CABLE'," + R.mipmap.casa + "," + R.drawable.forma_circulo3 + "," + R.color.color3 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('CAMION'," + R.mipmap.camion + "," + R.drawable.forma_circulo4 + "," + R.color.color4 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('CHUCHERIAS'," + R.mipmap.chucherias + "," + R.drawable.forma_circulo5 + "," + R.color.color5 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('COMIDA'," + R.mipmap.comida + "," + R.drawable.forma_circulo6 + "," + R.color.color6 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('COMPRAS'," + R.mipmap.compras + "," + R.drawable.forma_circulo7 + "," + R.color.color7 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('GAS'," + R.mipmap.gas + "," + R.drawable.forma_circulo8 + "," + R.color.color8 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('GASOLINA'," + R.mipmap.gasolina + "," + R.drawable.forma_circulo9 + "," + R.color.color9 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('PAREJA'," + R.mipmap.pareja + "," + R.drawable.forma_circulo10 + "," + R.color.color10 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID,TIPO) VALUES ('ROPA'," + R.mipmap.ropa + "," + R.drawable.forma_circulo10 + "," + R.color.color10 + ",0)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Cuentas.TABLE_NAME + " (NOMBRE,RECURSO_ID,COLOR_ID) VALUES ('BANAMEX'," + R.mipmap.credito + "," + R.color.color4 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Cuentas.TABLE_NAME + " (NOMBRE,RECURSO_ID,COLOR_ID) VALUES ('EFECTIVO'," + R.mipmap.monedas + "," + R.color.color10 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Cuentas.TABLE_NAME + " (NOMBRE,RECURSO_ID,COLOR_ID) VALUES ('FINANCIAMIENTO'," + R.mipmap.prestamo + "," + R.color.color2 + ")");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_CATEGORIAS);
        db.execSQL(SQL_DELETE_TRANSACCIONES);
        db.execSQL(SQL_DELETE_CUENTAS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}


