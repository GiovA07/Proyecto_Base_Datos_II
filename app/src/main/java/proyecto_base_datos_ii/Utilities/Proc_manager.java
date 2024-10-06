package proyecto_base_datos_ii.Utilities;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Proc_manager {

    private SqlTypeMap  types;
    private Connection connection;
    private DatabaseMetaData metaData;


    public Proc_manager(Connection connection) throws SQLException {
        this.connection = connection;
        this.types  = new SqlTypeMap();
        this.metaData = this.connection.getMetaData();
    }


    public Set<String> getSchemas() throws SQLException {
        Set<String> schemas = new HashSet<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getSchemas();

        while (rs.next()) {
            String schemaName = rs.getString("TABLE_SCHEM");
            if(!schemaName.equals("pg_catalog")  && !schemaName.equals("public") &&  !schemaName.equals("information_schema")){

                schemas.add(schemaName);
            }
        }
        rs.close();
        return schemas;
    }

    public void captureFuncData(String schema) throws SQLException  {
        ResultSet funcs = metaData.getFunctions(null, schema, "%");

        while (funcs.next()) {
            String f = funcs.getString("FUNCTION_NAME");
            // String ff = funcs.getString("FUNCTION_TYPE");

            String[] type = {"NO C","IN","INOUT","OUT","RETORNO","RESULSET"};
            ResultSet r = metaData.getFunctionColumns(null,schema, f, "%");
            while (r.next()) {
                System.out.println("\nparametro = "+ r.getString(4));
                System.out.println("typo = "+ type[r.getInt(5)]);
                int n = r.getInt(6);
                System.out.println("Data_type = "+ types.getType(n));
            }
            r.close();
        }
    }

    public void captureProcData(String schema) throws SQLException  {
        ResultSet procs = metaData.getProcedures(null, schema, "%");
        while (procs.next()) {
            String f = procs.getString("PROCEDURE_NAME");
            System.out.println("\nprocedimiento = " + f);
            String[] type = {"NO C","IN","INOUT","OUT","RETORNO","RESULSET"};
            ResultSet r = metaData.getProcedureColumns(null, schema, f, "%");
            while (r.next()) {
                System.out.println("\nparametro = "+ r.getString(4));
                System.out.println("typo = "+ type[r.getInt(5)]);
                int n = r.getInt(6);
                System.out.println("Data_type = "+ types.getType(n));
            }
            r.close();
        }

    }

    public void captureTriData() throws SQLException {
        
        Statement statement = connection.createStatement();

        String query = "SELECT * FROM information_schema.triggers;";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            System.out.println("\nTrigger Name: " + resultSet.getString(3));
            System.out.println("Trigger Table: " + resultSet.getString(7));
            System.out.println("Trigger Schem: " + resultSet.getString(2));
            System.out.println("Trigger Action: " + resultSet.getString(4));
            System.out.println("Action Momment: " + resultSet.getString(12));
            // System.out.println("Trigger Function: " + resultSet.getString(10));
        }
        resultSet.close();

    }

}
