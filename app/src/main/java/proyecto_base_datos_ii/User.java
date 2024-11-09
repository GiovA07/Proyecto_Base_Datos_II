package proyecto_base_datos_ii;

public class User {
    private String driver;
    private String url;
    private String username;
    private String password;
    private String schema;

    public User(String driver, String url, String username, String password) {
        if(driver == null || driver.isEmpty())
            throw new IllegalArgumentException("Driver is null or empty. Please provide a valid schema.");
        this.driver = driver;
        if(url == null || url.isEmpty())
            throw new IllegalArgumentException("Url is null or empty. Please provide a valid schema.l");
        this.url = url;
        if(username == null || username.isEmpty())
            throw new IllegalArgumentException("Username is null or empty. Please provide a valid schema.");
        this.username = username;
        if(password == null || password.isEmpty())
            throw new IllegalArgumentException("Driver is null or empty. Please provide a valid schema.");
        this.password = password;
    }
    public void set_schema(String schema) { this.schema = schema; }
    public String get_driver() { return driver; }
    public String get_url() { return url; }
    public String get_username() { return username; }
    public String get_password() { return password; }
    public String get_schema() { return schema; }

}
