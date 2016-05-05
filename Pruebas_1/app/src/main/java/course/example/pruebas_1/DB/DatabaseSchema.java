package course.example.pruebas_1.DB;

import android.provider.BaseColumns;

public final class DatabaseSchema {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DatabaseSchema() {}

    public static abstract class TD_Transacciones implements BaseColumns {
        public static final String TABLE_NAME = "TD_Transacciones";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_2 = "COSTO";
        public static final String COLUMN_NAME_3 = "CATEGORIA_ID";
        public static final String COLUMN_NAME_4 = "FECHA_ALTA";
        public static final String COLUMN_NAME_5 = "NOTA";
        public static final String COLUMN_NAME_6 = "DESCRIPCION";
        public static final String COLUMN_NAME_7 = "CUENTA_PRIN_ID";
        public static final String COLUMN_NAME_8 = "CUENTA_SECU_ID";
        public static final String COLUMN_NAME_9 = "TIPO_TRANSACCION";//0 = SALIDA, 1 = ENTRADA, 2 = TRANSFERENCIA
        public static final String COLUMN_NAME_10 = "PROGRAMACION_ID";//0 = SALIDA, 1 = ENTRADA, 2 = TRANSFERENCIA
    }

    public static abstract class TD_Transacciones_Programadas implements BaseColumns {
        public static final String TABLE_NAME = "TD_Transacciones_Programadas";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_2 = "COSTO";
        public static final String COLUMN_NAME_3 = "CATEGORIA_ID";
        public static final String COLUMN_NAME_4 = "FECHA_ALTA";
        public static final String COLUMN_NAME_5 = "NOTA";
        public static final String COLUMN_NAME_6 = "DESCRIPCION";
        public static final String COLUMN_NAME_7 = "CUENTA_PRIN_ID";
        public static final String COLUMN_NAME_8 = "TIPO_TRANSACCION";//0 = SALIDA, 1 = ENTRADA, 2 = TRANSFERENCIA
        public static final String COLUMN_NAME_9 = "FECHA_SIGUIENTE";
        public static final String COLUMN_NAME_10 = "INTERVALO";
        public static final String COLUMN_NAME_11 = "ACTIVA";
    }

    public static abstract class TD_Categorias implements BaseColumns {
        public static final String TABLE_NAME = "TD_Categorias";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_2 = "NOMBRE";
        public static final String COLUMN_NAME_3 = "RECURSO_ID";
        public static final String COLUMN_NAME_4 = "FORMACIRCULO_ID";
        public static final String COLUMN_NAME_5 = "COLOR_ID";
        public static final String COLUMN_NAME_6 = "TIPO";
    }
    public static abstract class TD_Cuentas implements BaseColumns {
        public static final String TABLE_NAME = "TD_Cuentas";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_2 = "NOMBRE";
        public static final String COLUMN_NAME_3 = "RECURSO_ID";
        public static final String COLUMN_NAME_4 = "COLOR_ID";
        public static final String COLUMN_NAME_5 = "TOTAL";
    }
}
