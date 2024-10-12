package proyecto_base_datos_ii.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Schema {
    private  String name;
    private List<Procedure> procedures;
    private List<Table> tables;
    private  List<Function> functions;

    public Schema(String name) {
        this.name = name;
        procedures = new ArrayList<>();
        tables = new ArrayList<>();
        functions = new ArrayList<>();
    }

    public String getName() {return name;}

    public void setProcedures(List<Procedure> procedures) {this.procedures = procedures;}
    public void setFunctions(List<Function> functions) {this.functions = functions;}
    public void setTables(List<Table> tables) {this.tables = tables;}
    public void addTable(Table table) { tables.add(table); }



    public void printTables() { System.out.println(tables);}



}
