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

    public static void main(String[] args) {

        Properties propiedades = new Properties();

        // Load database driver if not already loaded.
        String url;
        String driver;
        String user;
        String password;
        Connection connection = null;
        CallableStatement call_st = null;

        try {
            propiedades.load(new FileInputStream("../../resources/config.properties"));

            url = propiedades.getProperty("db1_url");
            driver = propiedades.getProperty("db1_driver");
            user = propiedades.getProperty("db1_user");
            password = propiedades.getProperty("db1_password");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

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
        } catch (SQLException e) {
            System.out.println("sqlex" + e.getMessage());
        } catch (Exception s) {
            System.out.println("Algo salio mal");
        }

    }
}
