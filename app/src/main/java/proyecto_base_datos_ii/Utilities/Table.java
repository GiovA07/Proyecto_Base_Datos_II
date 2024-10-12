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

    public void setName(String name){this.tableName = name;}
    public void addColumn(Column column){columns.add(column);}

    public void setTriggers(List<Trigger> trList) {
        this.triggers = trList;
    }

    @Override
    public String toString() {
        return "Table: \n\t " + tableName + '\n' +
            "Columns=" + columns +'\n' +
             triggers +'\n';
    }

}
