package proyecto_base_datos_ii;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class SQLDiff {
    public static void main(String[] args) throws SQLException {
        Connector post = new Connector();
        post.configInit("app/src/main/resources/user1.properties");
        System.out.println("Configuracion Inicial Lista");
        post.addConnection();
        System.out.println("Conexion Lista");
        post.changeAutoCommit(false);
        System.out.println("AutoCommit Listo");
        Connection connection = post.get_connection();


        String[] tipo = { "TABLE", "COLUMN" };
        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSetTables = metaData.getTables(null, "repaso2", null, tipo);

        System.out.println(" Tablas de la base de datos ");
        while (resultSetTables.next()) {
            String actual = resultSetTables.getString(3);
            System.out.println("\n Nombre de tabla: " + actual + "\n");

        }

        resultSetTables.close();
        connection.close();
    }
}
