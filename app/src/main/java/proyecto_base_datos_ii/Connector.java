package proyecto_base_datos_ii;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Connector {
    private User user = null;
    private Connection connection = null;

    public Connection get_connection() {
        if (connection == null)
            throw new IllegalAccessError();
        return connection;

    }

    public User get_user() {
        if (user == null)
            throw new IllegalAccessError();
        return user;
    }


    public void configInit(String pathProp) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(pathProp)));
            String driver = (String) properties.get("DRIVER");
            String url = (String) properties.get("URL");
            String username = (String) properties.get("USUARIO");
            String password = (String) properties.get("CLAVE");
            user = new User(driver, url, username, password);
            String schema = (String) properties.get("SCHEMA");
            user.set_schema(schema);
            Class.forName(driver);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addConnection() {
        String url = user.get_url();
        String username = user.get_username();
        String password = user.get_password();
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeAutoCommit(boolean  commit) {
        try {
            connection.setAutoCommit(commit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
