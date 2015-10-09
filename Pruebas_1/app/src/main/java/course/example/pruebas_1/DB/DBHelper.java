package course.example.pruebas_1.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import course.example.pruebas_1.DB.DatabaseSchema;

public class DBHelper extends SQLiteOpenHelper {

    public TD_Categorias Categorias;
    public TD_Transacciones Transacciones;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "MiChochinito.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_CATEGORIAS =
            "CREATE TABLE " + DatabaseSchema.TD_Categorias.TABLE_NAME +
                    " (" +
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_2 + TEXT_TYPE + COMMA_SEP +
                        DatabaseSchema.TD_Categorias.COLUMN_NAME_3 + INTEGER_TYPE +
                    " )";
    private static final String SQL_CREATE_TRANSACCIONES =
            "CREATE TABLE " + DatabaseSchema.TD_Transacciones.TABLE_NAME +
                    " (" +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_2 + DOUBLE_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_3 + INTEGER_TYPE + COMMA_SEP +
                    DatabaseSchema.TD_Transacciones.COLUMN_NAME_4 + INTEGER_TYPE +
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
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('AGUA',2130903040)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('LUZ',2130903048)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('CABLE',2130903042)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('CAMION',2130903041)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('CHUCHERIAS',2130903043)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('COMIDA',2130903046)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('COMPRAS',2130903047)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('GAS',2130903049)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('GASOLINA',2130903050)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('PAREJA',2130903052)");
        db.execSQL("INSERT INTO " + DatabaseSchema.TD_Categorias.TABLE_NAME + " (NOMBRE,RECURSO_ID) VALUES ('ROPA',2130903053)");
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


