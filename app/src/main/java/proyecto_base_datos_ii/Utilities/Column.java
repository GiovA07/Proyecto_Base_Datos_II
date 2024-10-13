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
        return "Column: " + name + " " + type + " " + (autoincrement?"autoincrement ":"") +
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
                differences.append("En columna: " + name + " Tiene clave foranea: " + foreingKey.toString());
                //aca ver si le ponemos que la otra columna  no tiene clave foranea
            } else if(foreingKey == null && other.foreingKey != null) {
                differences.append("En columna: " + name + " Tiene clave foranea: " + other.foreingKey.toString());
            }

        differences.append("\n\n");
        return differences.toString();
    }


    public static void main(String[] args) {
        // Crear dos instancias de ForeignKey para usar en las columnas
        ForeignKey fk1 = new ForeignKey();
        fk1.setName("FK_Usuario_Rol");
        fk1.setTableReference("Rol");
        fk1.setColumnReference("id_rol");

        ForeignKey fk2 = new ForeignKey();
        fk2.setName("FK_Usuario_Tienda");
        fk2.setTableReference("Tienda");
        fk2.setColumnReference("id_tienda");

        // Crear dos instancias de Column
        Column column1 = new Column();
        column1.setName("usuario_id");
        column1.setType("INT");
        column1.setAutoincrement(true);
        column1.setPrimaryKey(true);
        column1.setUniqe(true);
        column1.setForeingKey(fk1);

        Column column2 = new Column();
        column2.setName("usuario_id");
        column2.setType("VARCHAR(255)");
        column2.setAutoincrement(false);
        column2.setPrimaryKey(false);
        column2.setUniqe(false);
        column2.setForeingKey(fk2);

        // Comparar las dos columnas
        String differences = column1.compareTo(column2);

        // Mostrar resultados de la comparación
        if (differences.isEmpty()) {
            System.out.println("No hay diferencias entre las columnas.");
        } else {
            System.out.println("Diferencias encontradas:");
            System.out.println(differences);
        }
    }

}

