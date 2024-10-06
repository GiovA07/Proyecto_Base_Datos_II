package proyecto_base_datos_ii.Utilities;

import java.util.List;

import java.sql.*;

public class Table {
    private String tableName;
    private List<String> columns;
    private Connection connection;
    private DatabaseMetaData metaData;

    public Table(Connection con) throws SQLException{
        this.connection = con;
        this.metaData = con.getMetaData();
    }

    public void captureTableData(String schema) throws SQLException{
        String[] list = {"TABLE","VIEW","COLUMN","SYSTEM TABLE","ALIAS"};
        ResultSet r =  metaData.getTables(null, schema, null, list);
        
        while (r.next()) {
            System.out.println("\nTabla: "+r.getString(3));
            ResultSet col =  metaData.getColumns(null, schema, r.getString(3),"%");
            ResultSet fk = metaData.getCrossReference(null, null, null, null, schema, r.getString(3));
            ResultSet pk = metaData.getPrimaryKeys(null, schema, r.getString(3));
            
            while (col.next()) {
                System.out.println("Col name: "+col.getString(4));
                System.out.println("Col type: "+col.getString(5));
                System.out.println("Col type name: "+col.getString(6));
                System.out.println("Nullable: "+col.getString(18));
                System.out.println("Increment: "+col.getString(23));
                System.out.println();
            }

            while (pk.next()) {
                System.out.println("pk: "+pk.getString(4));
            }
            
            while (fk.next()) {
                    System.out.println("fk_tab = "+ fk.getString(3));
                    System.out.println("fk_col = "+ fk.getString(8));
            }
            fk.close();
            col.close();
        }

        r.close();
    }
}
