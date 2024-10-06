package proyecto_base_datos_ii;

import java.sql.Connection;
import java.sql.SQLException;

import proyecto_base_datos_ii.Utilities.InformPrinter;


public class SQLDiff {

    private static Connection connection;
    private static Connector post = new Connector();
    private static InformPrinter printer = new InformPrinter();
    private static MetadataExtractor metadataExtractor;


    public static void main(String[] args) throws SQLException {
        post.configInit("app/src/main/resources/config.properties");
        post.addConnection();
        post.changeAutoCommit(false);
        System.out.println("Configuracion Inicial Lista");
        connection = post.get_connection();

        metadataExtractor = new MetadataExtractor(connection);
    
        metadataExtractor.recupere();

        printer.printFile();

        connection.close();
    }
}
