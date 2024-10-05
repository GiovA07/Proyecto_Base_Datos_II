package proyecto_base_datos_ii;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

import proyecto_base_datos_ii.Utilities.SqlTypeMap;

public class SQLDiff {

    private static SqlTypeMap  types = new SqlTypeMap();


    public static void main(String[] args) throws SQLException {
        Connector post = new Connector();
        post.configInit("app/src/main/resources/config.properties");
        System.out.println("Configuracion Inicial Lista");
        post.addConnection();
        System.out.println("Conexion Lista");
        post.changeAutoCommit(false);
        System.out.println("AutoCommit Listo");
        Connection connection = post.get_connection();

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet fun = metaData.getFunctions(null, "Esquema1", "%");
        while (fun.next()) {
            String f = fun.getString("FUNCTION_NAME");
            System.out.println("funcion = " + f);
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

        ResultSet pro = metaData.getProcedures(null, "Esquema1", "%");
        while (pro.next()) {
            String f = pro.getString("PROCEDURE_NAME");
            System.out.println("procedimiento = " + f);
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

        fun.close();
        connection.close();
    }
}
