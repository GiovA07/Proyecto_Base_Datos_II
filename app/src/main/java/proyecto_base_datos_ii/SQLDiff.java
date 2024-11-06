package proyecto_base_datos_ii;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import proyecto_base_datos_ii.Utilities.*;


public class SQLDiff {

    private static Connection connectionUser1;
    private static Connection connectionUser2;
    private static Connector conectorUser1 = new Connector();
    private static Connector conectorUser2 = new Connector();
    private static InformPrinter printer = new InformPrinter();
    private static MetadataExtractor metadataExtractorUser1;
    private static MetadataExtractor metadataExtractorUser2;

    private static void setUp(){
        conectorUser1.addConnection();
        conectorUser2.addConnection();
        conectorUser1.changeAutoCommit(false);
        conectorUser2.changeAutoCommit(false);
    }

    private static MetadataExtractor extractMetadata(Schema schema, Connection conecction) throws  SQLException {
        MetadataExtractor meta = new MetadataExtractor(conecction);
        meta.captureInfoTables(schema);
        meta.captureMethodsInfo(schema);
        return meta;
    }

    public static void main(String[] args) throws SQLException {
        conectorUser1.configInit("app/src/main/resources/user1.properties");
        conectorUser2.configInit("app/src/main/resources/user2.properties");
        setUp();

        System.out.println("Configuracion Inicial Lista");

        connectionUser1 = conectorUser1.get_connection();
        connectionUser2 = conectorUser2.get_connection();

        User user = conectorUser1.get_user();
        User user2 = conectorUser2.get_user();


        Schema  schema = new Schema(user.get_schema());
        Schema  schema2 = new Schema(user2.get_schema());

        metadataExtractorUser1 = extractMetadata(schema, connectionUser1);

        metadataExtractorUser2 = extractMetadata(schema2, connectionUser2);

        printer.printFile(schema.compareTo(schema2));

        connectionUser1.close();
        connectionUser2.close();

        System.out.println("Se creo el archivo de diferencias...\n");
    }
}
