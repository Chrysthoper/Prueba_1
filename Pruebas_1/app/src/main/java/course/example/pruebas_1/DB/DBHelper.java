package course.example.pruebas_1.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import course.example.pruebas_1.DB.DatabaseSchema;
import course.example.pruebas_1.R;

public class DBHelper extends SQLiteOpenHelper {

    public TD_Categorias Categorias;
    public TD_Transacciones Transacciones;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 18;
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
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_5 + INTEGER_TYPE +
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
    private static final String SQL_DELETE_CATEGORIAS = "DROP TABLE IF EXISTS " + DatabaseSchema.TD_Categorias.TABLE_NAME;
    private static final String SQL_DELETE_TRANSACCIONES = "DROP TABLE IF EXISTS " + DatabaseSchema.TD_Transacciones.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Categorias = new TD_Categorias(this);
        Transacciones = new TD_Transacciones(this);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CATEGORIAS);
        db.execSQL(SQL_CREATE_TRANSACCIONES);
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('AGUA'," + R.mipmap.agua +",2130837591," + R.color.color1 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('LUZ'," + R.mipmap.luz +",2130837593," + R.color.color2 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('CABLE'," + R.mipmap.casa +",2130837594," + R.color.color3 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('CAMION'," + R.mipmap.camion +",2130837595," + R.color.color4 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('CHUCHERIAS'," + R.mipmap.chucherias +",2130837596," + R.color.color5 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('COMIDA'," + R.mipmap.comida +",2130837597," + R.color.color6 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('COMPRAS'," + R.mipmap.compras +",2130837598," + R.color.color7 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('GAS'," + R.mipmap.gas +",2130837599," + R.color.color8 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('GASOLINA'," + R.mipmap.gasolina +",2130837600," + R.color.color9 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('PAREJA'," + R.mipmap.pareja +",2130837592," + R.color.color10 + ")");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID,FORMACIRCULO_ID,COLOR_ID) VALUES ('ROPA'," + R.mipmap.ropa +",2130837592," + R.color.color10 + ")");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_CATEGORIAS);
        db.execSQL(SQL_DELETE_TRANSACCIONES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}


