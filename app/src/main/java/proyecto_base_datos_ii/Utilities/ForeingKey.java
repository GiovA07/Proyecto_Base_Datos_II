package proyecto_base_datos_ii.Utilities;

public class ForeingKey {
    private String name;
    private String tableReference;
    private String columnReference;

    public void setName(String name) {this.name = name;}
    public void setTableReference(String tableReference) {this.tableReference = tableReference;}
    public void setColumnReference(String columnReference) {this.columnReference = columnReference;}


    @Override
    public String toString() {
        return "ForeingKey {" +
            "name='" + name + '\'' +
            ", tableReference='" + tableReference + '\'' +
            ", columnReference='" + columnReference + '\'' +
            '}';
    }

}
