package proyecto_base_datos_ii;


import java.sql.*;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

import proyecto_base_datos_ii.Utilities.*;


public class MetadataExtractor {

    private Connection connection;
    private static Proc_manager procMan;
    private SqlTypeMap sqlTypeMap;

    public MetadataExtractor(Connection con) throws SQLException {
        connection = con;
        procMan = new Proc_manager(con);
        sqlTypeMap = new SqlTypeMap();
    }

    public void captureMethodsInfo(Schema shcema) throws SQLException {
        String sName = shcema.getName();

        shcema.setFunctions(procMan.captureFuncData(sName));
        shcema.setProcedures(procMan.captureProcData(sName));
    }

    public void captureInfoTables(Schema schema) throws SQLException {
        String nameSchema = schema.getName();
        String[] list = {"TABLE", "VIEW", "COLUMN", "INDEX"};

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet r = metaData.getTables(null, nameSchema, null, list);

        Table table;
        while (r.next()) {
            String tableName = r.getString("TABLE_NAME");

            table = new Table();
            table.setName(tableName);

            ResultSet columns = metaData.getColumns(null, nameSchema, tableName, "%");

            Set<String> primaryKeys = get_primarysKeys(metaData,nameSchema,tableName);
            Map<String, ForeignKey> foreignKeys = get_foreingsKeys(metaData,nameSchema,tableName);
            List<Trigger> trList = get_TriData(tableName, nameSchema);

            while (columns.next()) {
                Column column = new Column();
                String columnName = columns.getString("COLUMN_NAME");

                column.setName(columnName);
                column.setType(sqlTypeMap.getType(columns.getInt("DATA_TYPE")));

                String isAutoincrement = columns.getString("IS_AUTOINCREMENT");
                column.setAutoincrement("YES".equalsIgnoreCase(isAutoincrement));


                String isNullable = columns.getString("IS_NULLABLE");
                column.setNullable("YES".equalsIgnoreCase(isNullable));

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
                table.setTriggers(trList);
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

    private Map<String, ForeignKey> get_foreingsKeys(DatabaseMetaData  metaData, String nameSchema, String tableName) throws SQLException {
        Map<String, ForeignKey> foreignKeys =  new HashMap<>();

        ResultSet fk = metaData.getImportedKeys(null, nameSchema, tableName);
        while (fk.next()) {
            String foreinKey = fk.getString("FKCOLUMN_NAME");
            ForeignKey foreignKey = new ForeignKey();
            foreignKey.setName(fk.getString("FK_NAME"));
            foreignKey.setTableReference(fk.getString("PKTABLE_NAME"));
            foreignKey.setColumnReference(fk.getString("PKCOLUMN_NAME"));
            foreignKeys.put(foreinKey, foreignKey);
        }
        fk.close();
        return foreignKeys;

    }

    private List<Trigger> get_TriData(String table, String schema) throws SQLException {

        Statement statement = connection.createStatement();
        List<Trigger> trigs = new ArrayList<>();

        String query = "SELECT * FROM information_schema.triggers;";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {

            String name = resultSet.getString(3);
            String tableN = resultSet.getString(7);
            String schem = resultSet.getString(2);
            String action = resultSet.getString(4);
            String moment = resultSet.getString(12);
            if (tableN.equals(table) && schem.equals(schema))
                trigs.add(new Trigger(name, action, moment));
        }

        resultSet.close();
        return trigs;
    }

}
