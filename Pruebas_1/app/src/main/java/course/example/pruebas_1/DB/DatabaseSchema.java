package course.example.pruebas_1.DB;

import android.provider.BaseColumns;

public final class DatabaseSchema {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DatabaseSchema() {}

    /* Inner class that defines the table contents */
    public static abstract class TD_Transacciones implements BaseColumns {
        public static final String TABLE_NAME = "TD_Transacciones";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_2 = "COSTO";
        public static final String COLUMN_NAME_3 = "TIPO_TRANSACCION";
        public static final String COLUMN_NAME_4 = "CATEGORIA_ID";
    }

    /* Inner class that defines the table contents */
    public static abstract class TD_Categorias implements BaseColumns {
        public static final String TABLE_NAME = "TD_Categorias";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_2 = "NOMBRE";
        public static final String COLUMN_NAME_3 = "RECURSO_ID";
    }
}
