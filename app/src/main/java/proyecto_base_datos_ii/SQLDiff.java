package proyecto_base_datos_ii;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

import proyecto_base_datos_ii.Utilities.Proc_manager;
import proyecto_base_datos_ii.Utilities.Trigger;

public class SQLDiff {

    private static Proc_manager procMan = new Proc_manager();


    public static void main(String[] args) throws SQLException {
        Connector post = new Connector();
        post.configInit("app/src/main/resources/config.properties");
        System.out.println("Configuracion Inicial Lista");
        post.addConnection();
        System.out.println("Conexion Lista");
        post.changeAutoCommit(false);
        System.out.println("AutoCommit Listo\n");
        Connection connection = post.get_connection();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet fun = metaData.getFunctions(null, "Esquema1", "%");
        ResultSet pro = metaData.getProcedures(null, "Esquema1", "%");

        procMan.captureFuncData(fun, metaData);
        procMan.captureProcData(pro, metaData);

        pro.close();
        fun.close();
        connection.close();
    }
}
