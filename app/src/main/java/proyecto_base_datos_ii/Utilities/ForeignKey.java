package proyecto_base_datos_ii.Utilities;

public class ForeignKey {
    private String name;
    private String tableReference;
    private String columnReference;

    public  ForeignKey() {
        this.name = "";
        this.tableReference = "";
        this.columnReference = "";
    }



    public void setName(String name) {this.name = name;}
    public void setTableReference(String tableReference) {this.tableReference = tableReference;}
    public void setColumnReference(String columnReference) {this.columnReference = columnReference;}


    @Override
    public String toString() {
        return name + " reference to (" + columnReference + ") in " + tableReference;
    }


    public String compareTo(ForeignKey other) {

        StringBuilder differences = new StringBuilder();

        if (!this.name.equals(other.name)) {
            differences.append("Diferencia de Nombres: ").append(this.name)
                       .append(" vs ").append(other.name).append("\n");

        } else if (!this.tableReference.equals(other.tableReference)) {
            differences.append("Nombre de la Clave Foranea: ").append(this.name).append("\n");
            differences.append("Diferencia de Referencia a tabla: ").append(this.tableReference)
                       .append(" vs ").append(other.tableReference).append("\n");
        }

        if (!this.columnReference.equals(other.columnReference)) {
            differences.append("Diferencia  de Referencia a columna: ").append(this.columnReference)
                       .append(" vs ").append(other.columnReference).append("\n");
        }

        return differences.toString();
    }

}
