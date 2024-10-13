package proyecto_base_datos_ii.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
    private String tableName;
    private Map<String, Column> columns;
    private List<Trigger> triggers;


    public Table(){
        this.columns = new HashMap<>();
        this.triggers = new ArrayList<>();
    }

    public String getName() {return tableName;}

    public void setName(String name){this.tableName = name;}
    public void addColumn(Column column){columns.put(column.getName(), column);}

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

        // Comparar el numero de columnas
        compareColumnCount(other, differences);

        // Comparar columnas en ambas tablas
        compareCommonColumns(other, differences);

        // Comparar columnas que solo estan en la segunda tabla
        compareMissingColumns(other, differences);

        return differences.toString();
    }

    // Metodo para comparar el número de columnas
    private void compareColumnCount(Table other, StringBuilder differences) {
        if (columns.size() != other.columns.size()) {
            differences.append("El número de columnas es diferente: ")
                       .append(columns.size())
                       .append(" vs ")
                       .append(other.columns.size())
                       .append("\n");
        }
    }

    // Metodo para comparar las columnas que existen en ambas tablas
    private void compareCommonColumns(Table other, StringBuilder differences) {
        Map<String, Column> otherColumnsMap = other.columns;

        for (String strColumn : columns.keySet()) {
            if (otherColumnsMap.containsKey(strColumn)) {
                Column column = columns.get(strColumn);
                Column columnOther = otherColumnsMap.get(strColumn);
                String diffColumns = column.compareTo(columnOther);

                if (!diffColumns.trim().isEmpty()) {
                    differences.append("Ambas tablas (" + tableName + ") tienen la columna: ")
                            .append("(").append(strColumn).append(") ")
                            .append("Diferencias:\n")
                            .append(diffColumns);
                }
            } else {
                differences.append("La columna ")
                        .append("(").append(strColumn).append(") ")
                        .append("solo existe en la tabla (" + tableName + ") del primer esquema, no existe en la otra tabla.\n");
            }
        }
    }

    // Metodo para comparar las columnas que estan en la segunda tabla pero no en la primera
    private void compareMissingColumns(Table other, StringBuilder differences) {
        Map<String, Column> otherColumnsMap = other.columns;
        StringBuilder missingColumns = new StringBuilder();

        for (String strColumn : otherColumnsMap.keySet()) {
            if (!columns.containsKey(strColumn)) {
                missingColumns.append("La columna ")
                            .append(strColumn)
                            .append(" no existe en la tabla (" + tableName + ") del segundo esquema.\n");
            }
        }

        if (missingColumns.length() > 0) {
            differences.append("Las columnas diferentes de la tabla (" + tableName + ") del segundo esquema son:\n")
                    .append(missingColumns);
        }
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
