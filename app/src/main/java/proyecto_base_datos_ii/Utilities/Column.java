package proyecto_base_datos_ii.Utilities;

public class Column {
    private String name;
    private String type;
    private boolean autoincrement;
    private ForeingKey foreingKey;
    private boolean primaryKey;
    private boolean unique;


    public void setName(String name){this.name = name;}
    public void setType(String type){this.type = type;}
    public void setAutoincrement(boolean autoincrement){this.autoincrement = autoincrement;}
    public void setPrimaryKey(boolean primaryKey){this.primaryKey = primaryKey;}
    public void setUniqe(boolean unique){this.unique = unique;}
    public void setForeingKey(ForeingKey fk){this.foreingKey = fk;}

    @Override
    public String toString() {
        return "Column: " + name + " type = " + type + " autoincrement = " + autoincrement +
        " primary key = " + primaryKey + " unique = " + unique + " foreign key = " + foreingKey + '\n';
    }

}

