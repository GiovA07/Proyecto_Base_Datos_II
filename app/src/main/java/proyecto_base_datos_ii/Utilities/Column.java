package proyecto_base_datos_ii.Utilities;

public class Column {
    private String name;
    private String type;
    private boolean autoincrement;
    private ForeignKey foreingKey;
    private boolean primaryKey;
    private boolean unique;
    private boolean isNullable;


    public void setName(String name){this.name = name;}
    public String getName(){return name;}
    public void setType(String type){this.type = type;}
    public void setAutoincrement(boolean autoincrement){this.autoincrement = autoincrement;}
    public void setPrimaryKey(boolean primaryKey){this.primaryKey = primaryKey;}
    public void setUniqe(boolean unique){this.unique = unique;}
    public void setNullable(boolean isNullable){this.isNullable = isNullable;}
    public void setForeingKey(ForeignKey fk){this.foreingKey = fk;}

    @Override
    public String toString() {
        return " " + type + " " + (autoincrement?"autoincrement ":"") +
         (primaryKey?"primary key ":"") + (unique?"unique ":"")  + (foreingKey!=null?"foreign key: "+foreingKey:"")+ '\n';
    }



    public String compareTo(Column other) {
        StringBuilder differences = new StringBuilder();

            if (!this.type.equals(other.type)) {
                differences.append("Diferencia de tipo: ").append(this.type)
                           .append(" vs ").append(other.type).append("\n");
            }

            if (this.autoincrement !=  other.autoincrement) {
                differences.append("Esta columna es autoincrementada?: ").append(this.autoincrement)
                           .append(" vs ").append(other.autoincrement).append("\n");
            }

            if (this.isNullable !=  other.isNullable) {
                differences.append("Esta columna puede ser null?: ").append(this.isNullable)
                           .append(" vs ").append(other.isNullable).append("\n");
            }

            if (this.primaryKey !=  other.primaryKey) {
                differences.append("Esta columna es clave primaria?: ").append(this.primaryKey)
                           .append(" vs ").append(other.primaryKey).append("\n");
            }

            if (this.unique !=  other.unique) {
                differences.append("Esta columna es unica?: ").append(this.unique)
                           .append(" vs ").append(other.unique).append("\n");
            }

            if(foreingKey != null &&  other.foreingKey != null) {
                differences.append(this.foreingKey.compareTo(other.foreingKey));
            } else if(foreingKey != null && other.foreingKey == null) {
                differences.append("\n  - El segundo esquema comparado tiene clave foranea: " + foreingKey.toString() + '\n');
                //aca ver si le ponemos que la otra columna  no tiene clave foranea
            } else if(foreingKey == null && other.foreingKey != null) {
                differences.append("\n  - El primer esquema tiene clave foranea: " + other.foreingKey.toString() + '\n');
            }

        return differences.toString();
    }

}

