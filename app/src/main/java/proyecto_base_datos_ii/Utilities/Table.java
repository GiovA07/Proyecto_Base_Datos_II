package proyecto_base_datos_ii.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String tableName;
    private List<Column> columns;
    private List<Trigger> triggers;


    public Table(){
        this.columns = new ArrayList<>();
        this.triggers = new ArrayList<>();
    }

    public String getName() {return tableName;}

    public void setName(String name){this.tableName = name;}
    public void addColumn(Column column){columns.add(column);}

    public void setTriggers(List<Trigger> trList) {
        this.triggers = trList;
    }

    @Override
    public String toString() {
        return "Table: " + tableName + '\n' +
             columns +'\n' +
             triggers +'\n';
    }



    public String compareTo(Table other) {
        StringBuilder differences = new StringBuilder();
        if(columns.size() != other.columns.size()) {
            differences.append("El numero de columnas es diferente: ").append(columns.size()).append(" vs ").append(other.columns.size() + "\n");
        }
        for (Column column : columns) {
            boolean found = false;
            Column columnEquals = null;
            for (Column columnOther : other.columns) {
                if(column.getName().equals(columnOther.getName())) {
                    found = true;
                    columnEquals = columnOther;
                    break;
                }
            }

            if(!found) {
                differences.append("La columna ").append("(" + column.getName() + ")").append(" solo existe en la tabla del primer esquema, no existe en la otra tabla..\n");
            } else {
                differences.append("Ambas tablas tienen la columna: " + "(" + column.getName() + ")" + " Diferencias: \n");
                differences.append(column.compareTo(columnEquals));
            }
        }

        differences.append("Las columnas diferentes de la tabla del segundo esquema son: ");
        for (Column column : other.columns) {
            for (Column columnOther : columns) {
                if(!column.getName().equals(columnOther.getName())) {
                    differences.append("La columna ").append(column.getName()).append(" no existe en la otra tabla\n");
                    break;
                }
            }
        }

        return differences.toString();
    }



    public static void main(String[] args) {
        // Crear claves foráneas para usar en las columnas
        ForeignKey fk1 = new ForeignKey();
        fk1.setName("FK_Usuario_Rol");
        fk1.setTableReference("Rol");
        fk1.setColumnReference("id_rol");

        ForeignKey fk2 = new ForeignKey();
        fk2.setName("FK_Usuario_Tienda");
        fk2.setTableReference("Tienda");
        fk2.setColumnReference("id_tienda");

        // Crear columnas para la primera tabla
        Column column1_1 = new Column();
        column1_1.setName("usuario_id");
        column1_1.setType("INT");
        column1_1.setAutoincrement(true);
        column1_1.setPrimaryKey(true);
        column1_1.setUniqe(true);
        column1_1.setForeingKey(fk1);

        Column column1_2 = new Column();
        column1_2.setName("nombre");
        column1_2.setType("VARCHAR(100)");
        column1_2.setAutoincrement(false);
        column1_2.setPrimaryKey(false);
        column1_2.setUniqe(false);

        // Crear la primera tabla
        Table table1 = new Table();
        table1.setName("Usuarios");
        table1.addColumn(column1_1);
        table1.addColumn(column1_2);

        // Crear columnas para la segunda tabla
        Column column2_1 = new Column();
        column2_1.setName("usuario_id");
        column2_1.setType("INT");
        column2_1.setAutoincrement(false);
        column2_1.setPrimaryKey(false);
        column2_1.setUniqe(false);
        column2_1.setForeingKey(fk2);

        Column column2_2 = new Column();
        column2_2.setName("email");
        column2_2.setType("VARCHAR(100)");
        column2_2.setAutoincrement(false);
        column2_2.setPrimaryKey(false);
        column2_2.setUniqe(true);

        // Crear la segunda tabla
        Table table2 = new Table();
        table2.setName("Usuarios");
        table2.addColumn(column2_1);
        table2.addColumn(column2_2);

        // Comparar las dos tablas
        String differences = table1.compareTo(table2);

        // Mostrar resultados de la comparación
        if (differences.isEmpty()) {
            System.out.println("No hay diferencias entre las tablas.");
        } else {
            System.out.println("Diferencias encontradas:");
            System.out.println(differences);
        }
    }

}
