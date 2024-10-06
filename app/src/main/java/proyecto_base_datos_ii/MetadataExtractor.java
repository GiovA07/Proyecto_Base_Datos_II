package proyecto_base_datos_ii;

import java.sql.*;
import java.util.Set;

import proyecto_base_datos_ii.Utilities.Proc_manager;
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
}
