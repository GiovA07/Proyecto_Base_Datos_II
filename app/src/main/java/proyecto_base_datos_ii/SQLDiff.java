package proyecto_base_datos_ii;

import java.sql.Connection;
import java.sql.SQLException;

import proyecto_base_datos_ii.Utilities.*;


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

        Schema  schema = new Schema("Esquema1");
        Schema  schema2 = new Schema("Esquema2");

        metadataExtractor = new MetadataExtractor(connection);
        metadataExtractor.captureInfoTables(schema);
        metadataExtractor.captureMethodsInfo(schema);

        metadataExtractor.captureInfoTables(schema2);
        metadataExtractor.captureMethodsInfo(schema2);

        printer.printFile(schema.compareTo(schema2));

        connection.close();
    }
}
