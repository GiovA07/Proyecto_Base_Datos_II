package proyecto_base_datos_ii;

import java.util.Set;
import java.sql.Connection;
import java.sql.SQLException;

import proyecto_base_datos_ii.Utilities.Proc_manager;

public class SQLDiff {

    private static Proc_manager procMan;


    public static void main(String[] args) throws SQLException {
        Connector post = new Connector();
        post.configInit("app/src/main/resources/config.properties");
        System.out.println("Configuracion Inicial Lista");
        post.addConnection();
        System.out.println("Conexion Lista");
        post.changeAutoCommit(false);
        System.out.println("AutoCommit Listo\n");
        Connection connection = post.get_connection();

        procMan = new Proc_manager(connection);

        Set<String> setSchemas = procMan.getSchemas();
        for (String string : setSchemas) {
            System.out.println(string);
            procMan.captureFuncData(string);
            procMan.captureProcData(string);
        }

        connection.close();
    }
}
