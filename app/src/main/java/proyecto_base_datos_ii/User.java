package proyecto_base_datos_ii;

public class User {
    private String driver;
    private String url;
    private String username;
    private String password;

    public User(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public String get_driver() { return driver; }
    public String get_url() { return url; }
    public String get_username() { return username; }
    public String get_password() { return password; }
}
