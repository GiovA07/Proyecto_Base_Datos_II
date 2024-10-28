package proyecto_base_datos_ii;

import java.sql.Connection;
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


    public static void main(String[] args) throws SQLException {
        conectorUser1.configInit("app/src/main/resources/user1.properties");
        conectorUser2.configInit("app/src/main/resources/user2.properties");
        conectorUser1.addConnection();
        conectorUser2.addConnection();
        conectorUser1.changeAutoCommit(false);
        conectorUser2.changeAutoCommit(false);
        System.out.println("Configuracion Inicial Lista");
        connectionUser1 = conectorUser1.get_connection();
        connectionUser2 = conectorUser2.get_connection();
        User user = conectorUser1.get_user();
        User user2 = conectorUser2.get_user();


        Schema  schema = new Schema(user.get_schema());
        Schema  schema2 = new Schema(user2.get_schema());

        metadataExtractorUser1 = new MetadataExtractor(connectionUser1);
        metadataExtractorUser1.captureInfoTables(schema);
        metadataExtractorUser1.captureMethodsInfo(schema);

        metadataExtractorUser2 = new MetadataExtractor(connectionUser2);
        metadataExtractorUser2.captureInfoTables(schema2);
        metadataExtractorUser2.captureMethodsInfo(schema2);

        printer.printFile(schema.compareTo(schema2));

        connectionUser1.close();
    }
}
