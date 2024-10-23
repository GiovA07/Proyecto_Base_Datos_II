package proyecto_base_datos_ii.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
    private String tableName;
    private Map<String, Column> columns;
    private List<Trigger> triggers;
    private List<String> indexs;


    public Table() {
        this.columns = new HashMap<>();
        this.triggers = new ArrayList<>();
        this.indexs = new ArrayList<>();
    }

    public String getName() {return tableName;}
    public List<String> getIndex(){return indexs;}

    public void setName(String name){this.tableName = name;}
    public void addColumn(Column column){columns.put(column.getName(), column);}

    public void addIndex(String index) {indexs.add(index);}

    public void setTriggers(List<Trigger> trList) {
        this.triggers = trList;
    }

    @Override
    public String toString() {
        return "Table: " + tableName + '\n' +
             (columns.isEmpty()?"":columns) +'\n' +
             (triggers.isEmpty()?"":triggers) +
             (indexs.isEmpty()?"":indexs) + "\n\n";
    }


    public String compareTo(Table other) {
        StringBuilder differences = new StringBuilder();

        // Compara indices de la tabla
        compareIndex(other, differences);
        // Comparar el numero de columnas
        compareColumnCount(other, differences);

        // Comparar columnas en ambas tablas
        compareCommonColumns(other, differences);

        // Comparar columnas que solo estan en la segunda tabla
        compareMissingColumns(other, differences);


        compareTriggers(other, differences);

        return differences.toString();
    }


    private void compareIndex(Table other, StringBuilder differences) {
        // Comparar indices en ambas tablas
        List<String> aux = new ArrayList<>();
        List<String> indexsOther = other.getIndex();
        
        for (String idx : indexs) {
            if (!indexsOther.contains(idx)) {
                aux.add(idx + "(segundo esquema)");
            }
        }
        for (String idx : indexsOther) {
            if (!indexs.contains(idx)) {
                aux.add(idx + "(primer esquema)");
            }
        }
        if (aux.size() > 0) 
            for (String index : aux)
                differences.append("  * " +index +"\n");
    }

    // Metodo para comparar el número de columnas
    private void compareColumnCount(Table other, StringBuilder differences) {
        if (columns.size() != other.columns.size()) {
            differences.append(" - El número de columnas es diferente: ")
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
                    differences.append(" - Ambas tablas tienen la misma columna (" + strColumn + ").\n");
                    differences.append("  -> Con las diferencias: ").append(diffColumns);
                }
            } else {
                differences.append("  * La columna ")
                        .append("(").append(strColumn).append(") ")
                        .append("solo existe en la tabla del segundo esquema, no existe en la otra tabla.\n");
            }
        }
    }

    // Metodo para comparar las columnas que estan en la segunda tabla pero no en la primera
    private void compareMissingColumns(Table other, StringBuilder differences) {
        Map<String, Column> otherColumnsMap = other.columns;
        StringBuilder missingColumns = new StringBuilder();

        for (String strColumn : otherColumnsMap.keySet()) {
            if (!columns.containsKey(strColumn)) {
                missingColumns.append("  * La columna ")
                            .append(strColumn)
                            .append(" no existe en la tabla (" + tableName + ") del segundo esquema.\n");
            }
        }

        if (missingColumns.length() > 0) {
            differences.append("  * Las columnas diferentes de la tabla (" + tableName + ") del primer esquema son:\n")
                    .append(missingColumns);
        }
    }


    private void compareTriggers(Table other, StringBuilder differences) {
        List<Trigger> otherTriggers = other.triggers;

        // Comparar triggers que existen en ambas tablas
        for (Trigger trigger : triggers) {
            if (otherTriggers.contains(trigger)) {
                differences.append(" - El trigger ").append(trigger.getName()).append(" existe en ambas tablas.\n");
            } else {
                differences.append("  * El trigger ").append(trigger.getName()).append(" solo existe en la tabla del primer esquema.\n");
            }
        }

        // Comparar triggers que estan en la segunda tabla pero no en la primera
        for (Trigger otherTrigger : otherTriggers) {
            if (!triggers.contains(otherTrigger)) {
                differences.append("  * El trigger ").append(otherTrigger.getName()).append(" no existe en la tabla del primer esquema. \n");
            }
        }
    }

}
