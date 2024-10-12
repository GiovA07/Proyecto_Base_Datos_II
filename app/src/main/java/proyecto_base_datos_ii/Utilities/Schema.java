package proyecto_base_datos_ii.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Schema {
    private String name;
    private List<Table> tables;
    private List<Function> functions;
    private List<Procedure> procedures;

    public Schema(String name) {
        this.name = name;
        procedures = new ArrayList<>();
        tables = new ArrayList<>();
        functions = new ArrayList<>();
    }

    public String getName() {return name;}
    public List<Table> getTables() {return tables;}
    public List<Function> getFunctions() {return functions;}
    public List<Procedure> getProcedures() {return procedures;}

    public void setProcedures(List<Procedure> procedures) {this.procedures = procedures;}
    public void setFunctions(List<Function> functions) {this.functions = functions;}
    public void setTables(List<Table> tables) {this.tables = tables;}
    public void addTable(Table table) { tables.add(table); }


    public void printMethods(){System.out.println(functions + "\n\n" + procedures);}
    public void printTables() { System.out.println(tables);}


    private StringBuilder diferences = new StringBuilder();
    public String compareTo(Schema other) {
        if (other == null)
            return this.tables.toString() + this.functions.toString() + this.procedures.toString();

        List<Table> othTables = other.getTables();
        List<Function> othFunctions = other.getFunctions();
        List<Procedure> othProcedures = other.getProcedures();

        tablesCompare(tables, othTables, this.name);
        tablesCompare(othTables, tables, other.getName());

        return diferences.toString();
    }

    public void tablesCompare(List<Table> first, List<Table> second, String sch) {
        for(Table tab: first) {
            boolean isEQ = false;
            for(Table tab2: second) {
                if (tab.getName().equals(tab2.getName()))
                    isEQ = true;
            }
            if (!isEQ) {
                diferences.append("El schema: " + sch + " contine particularmente la tabla\n");
                diferences.append(tab);
            }
        }
    }
}

