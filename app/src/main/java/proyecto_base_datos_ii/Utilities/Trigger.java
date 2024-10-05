package proyecto_base_datos_ii.Utilities;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Trigger {
    private String nombre;
    private String momentDisparo;
    private SqlTypeMap types = new SqlTypeMap();


    public void captureTriData(ResultSet tri, DatabaseMetaData metaData) throws SQLException{
        while (tri.next()) {
            String f = tri.getString("TABLE_NAME");
            System.out.println("trigger = " + f);
            // String[] type = {"NO C","IN","INOUT","OUT","RETORNO","RESULSET"};
            // ResultSet r = metaData.getFunctionColumns(null, "Esquema1", f, "%");
            // while (r.next()) {
            //     System.out.println("\nparametro = "+ r.getString(4));
            //     System.out.println("typo = "+ type[r.getInt(5)]);
            //     int n = r.getInt(6);
            //     System.out.println("Data_type = "+ types.getType(n));
            // }
            // r.close();    
            // System.out.println(); 
        }
    }

}
