package proyecto_base_datos_ii.Utilities;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class SqlTypeMap {
    private Map<Integer, String> sqlTypeMap = new HashMap<>();

    public SqlTypeMap() {

        sqlTypeMap.put(Types.BIT, "BIT");
        sqlTypeMap.put(Types.TINYINT, "TINYINT");
        sqlTypeMap.put(Types.SMALLINT, "SMALLINT");
        sqlTypeMap.put(Types.INTEGER, "INTEGER");
        sqlTypeMap.put(Types.BIGINT, "BIGINT");
        sqlTypeMap.put(Types.FLOAT, "FLOAT");
        sqlTypeMap.put(Types.REAL, "REAL");
        sqlTypeMap.put(Types.DOUBLE, "DOUBLE");
        sqlTypeMap.put(Types.NUMERIC, "NUMERIC");
        sqlTypeMap.put(Types.DECIMAL, "DECIMAL");
        sqlTypeMap.put(Types.CHAR, "CHAR");
        sqlTypeMap.put(Types.VARCHAR, "VARCHAR");
        sqlTypeMap.put(Types.LONGVARCHAR, "LONGVARCHAR");
        sqlTypeMap.put(Types.DATE, "DATE");
        sqlTypeMap.put(Types.TIME, "TIME");
        sqlTypeMap.put(Types.TIMESTAMP, "TIMESTAMP");
        sqlTypeMap.put(Types.BINARY, "BINARY");
        sqlTypeMap.put(Types.VARBINARY, "VARBINARY");
        sqlTypeMap.put(Types.LONGVARBINARY, "LONGVARBINARY");
        sqlTypeMap.put(Types.NULL, "NULL");
        sqlTypeMap.put(Types.OTHER, "OTHER");
        sqlTypeMap.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
        sqlTypeMap.put(Types.DISTINCT, "DISTINCT");
        sqlTypeMap.put(Types.STRUCT, "STRUCT");
        sqlTypeMap.put(Types.ARRAY, "ARRAY");
        sqlTypeMap.put(Types.BLOB, "BLOB");
        sqlTypeMap.put(Types.CLOB, "CLOB");
        sqlTypeMap.put(Types.REF, "REF");
        sqlTypeMap.put(Types.DATALINK, "DATALINK");
        sqlTypeMap.put(Types.BOOLEAN, "BOOLEAN");
        sqlTypeMap.put(Types.ROWID, "ROWID");
        sqlTypeMap.put(Types.NCHAR, "NCHAR");
        sqlTypeMap.put(Types.NVARCHAR, "NVARCHAR");
        sqlTypeMap.put(Types.LONGNVARCHAR, "LONGNVARCHAR");
        sqlTypeMap.put(Types.NCLOB, "NCLOB");
        sqlTypeMap.put(Types.SQLXML, "SQLXML");

        // You can now use the sqlTypeMap to retrieve the SQL type name by its code
        // For example:
    }

    public String getType(int n) {
        return sqlTypeMap.get(n);
    }
}