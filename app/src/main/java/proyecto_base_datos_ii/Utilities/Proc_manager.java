package proyecto_base_datos_ii.Utilities;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Proc_manager {

    private SqlTypeMap  types;

    public Proc_manager() {
        this.types  = new SqlTypeMap();
    }

    public void captureFuncData(ResultSet func, DatabaseMetaData metaData) throws SQLException  {
        while (func.next()) {
            String f = func.getString("FUNCTION_NAME");
            String ff = func.getString("FUNCTION_TYPE");
            System.out.println("\nfuncion = " + f);
            System.out.println("funcion type = " + ff);
            String[] type = {"NO C","IN","INOUT","OUT","RETORNO","RESULSET"};
            ResultSet r = metaData.getFunctionColumns(null, "Esquema1", f, "%");
            while (r.next()) {
                System.out.println("\nparametro = "+ r.getString(4));
                System.out.println("typo = "+ type[r.getInt(5)]);
                int n = r.getInt(6);
                System.out.println("Data_type = "+ types.getType(n));
            }
            r.close();     
        }
    }

    public void captureProcData(ResultSet proc, DatabaseMetaData metaData) throws SQLException  {
        while (proc.next()) {
            String f = proc.getString("PROCEDURE_NAME");
            System.out.println("\nprocedimiento = " + f);
            String[] type = {"NO C","IN","INOUT","OUT","RETORNO","RESULSET"};
            ResultSet r = metaData.getProcedureColumns(null, "Esquema1", f, "%");
            while (r.next()) {
                System.out.println("\nparametro = "+ r.getString(4));
                System.out.println("typo = "+ type[r.getInt(5)]);
                int n = r.getInt(6);
                System.out.println("Data_type = "+ types.getType(n));
            }
            r.close();
        }

    }

}
