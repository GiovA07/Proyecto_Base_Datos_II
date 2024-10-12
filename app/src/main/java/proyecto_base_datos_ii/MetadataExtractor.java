package proyecto_base_datos_ii;

import java.sql.*;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import proyecto_base_datos_ii.Utilities.Column;
import proyecto_base_datos_ii.Utilities.ForeingKey;
import proyecto_base_datos_ii.Utilities.Proc_manager;
import proyecto_base_datos_ii.Utilities.Schema;
import proyecto_base_datos_ii.Utilities.Table;

public class MetadataExtractor {

    private Connection connection;
    private static Proc_manager procMan;

    public MetadataExtractor(Connection con) {
        connection = con;
    }

    public void recupere() throws SQLException {
        Table tab = new Table(connection);
        procMan = new Proc_manager(connection);
        Set<String> setSchemas = procMan.getSchemas();
        for (String string : setSchemas) {
            System.out.println(string);
            procMan.captureFuncData(string);
            procMan.captureProcData(string);
            procMan.captureTriData();
            tab.captureTableData(string);
        }
    }


    public void captureInfoTables(Schema schema) throws SQLException {
        String nameSchema = schema.getName();
        String[] list = {"TABLE", "VIEW", "COLUMN", "SYSTEM TABLE", "ALIAS"};

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet r = metaData.getTables(null, nameSchema, null, list);

        Table table;
        while (r.next()) {
            String tableName = r.getString("TABLE_NAME");

            table = new Table(connection);
            table.setName(tableName);

            ResultSet columns = metaData.getColumns(null, nameSchema, tableName, "%");

            Set<String> primaryKeys = get_primarysKeys(metaData,nameSchema,tableName);
            Map<String, ForeingKey> foreignKeys = get_foreingsKeys(metaData,nameSchema,tableName);

            while (columns.next()) {
                Column column = new Column();
                String columnName = columns.getString("COLUMN_NAME");

                column.setName(columnName);
                column.setType(columns.getString("TYPE_NAME"));

                String isAutoincrement = columns.getString("IS_AUTOINCREMENT");
                column.setAutoincrement("YES".equalsIgnoreCase(isAutoincrement));

                column.setPrimaryKey(primaryKeys.contains(columnName));

                if (foreignKeys.containsKey(columnName)) {
                    column.setForeingKey(foreignKeys.get(columnName));
                }

                ResultSet uniqueIndex = metaData.getIndexInfo(null, nameSchema, tableName, true, true);
                boolean isUnique = false;
                while (uniqueIndex.next()) {
                    String indexColumnName = uniqueIndex.getString("COLUMN_NAME");
                    if (columnName.equals(indexColumnName)) {
                        isUnique = true;
                        break;
                    }
                }
                uniqueIndex.close();
                column.setUniqe(isUnique);

                table.addColumn(column);
            }
            columns.close();
            schema.addTable(table);
        }

        r.close();
    }

    private Set<String> get_primarysKeys(DatabaseMetaData metaData, String nameSchema, String tableName) throws SQLException {
        Set<String> primaryKeys = new HashSet<>();
        ResultSet prim_key_data = metaData.getPrimaryKeys(null, nameSchema, tableName);
        while (prim_key_data.next()) {
            primaryKeys.add(prim_key_data.getString("COLUMN_NAME"));
        }
        prim_key_data.close();
        return primaryKeys;
    }

    private Map<String, ForeingKey> get_foreingsKeys(DatabaseMetaData  metaData, String nameSchema, String tableName) throws SQLException {
        Map<String, ForeingKey> foreignKeys =  new HashMap<>();

        ResultSet fk = metaData.getImportedKeys(null, nameSchema, tableName);
        while (fk.next()) {
            String foreinKey = fk.getString("FKCOLUMN_NAME");
            ForeingKey foreignKey = new ForeingKey();
            foreignKey.setName(fk.getString("FK_NAME"));
            foreignKey.setTableReference(fk.getString("PKTABLE_NAME"));
            foreignKey.setColumnReference(fk.getString("PKCOLUMN_NAME"));
            foreignKeys.put(foreinKey, foreignKey);
        }
        fk.close();
        return foreignKeys;

    }

}
