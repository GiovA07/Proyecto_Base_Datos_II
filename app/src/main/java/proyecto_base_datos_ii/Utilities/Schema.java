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

        //ver si este cambio lo dejamos o no.
        StringBuilder tempDifferences = new StringBuilder();

        for (Table otb : othTables) {
            for (Table tb : tables) {
                if (otb.getName().equals(tb.getName())) {
                    String tableDifferences = otb.compareTo(tb);
                    tempDifferences.append("Ambos esquemas tienen la misma tabla ("+ otb.getName() + ")\n");
                    if (!tableDifferences.trim().isEmpty())
                        tempDifferences.append(tableDifferences);
                }
            }
        }

        if (tempDifferences.length() > 0)
            diferences.append("Diferencias entre las tablas iguales de ")
                       .append(this.getName())
                       .append(" vs ")
                       .append(other.getName())
                       .append(":\n")
                       .append(tempDifferences);
        else
            diferences.append("En las tablas que tienen en comun no existen diferencias.\n");

        funcCompare(functions, othFunctions, this.name);
        funcCompare(othFunctions, othFunctions, other.name);

        procCompare(procedures, othProcedures, this.name);
        procCompare(othProcedures, procedures, other.name);

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
                // diferences.append('\n');
                diferences.append("\nEl schema " + sch + " contine particularmente la tabla\n");
                diferences.append(tab);
            }
        }
    }

    public void procCompare(List<Procedure> first, List<Procedure> second, String sch) {
        for(Procedure pr: first) {
            boolean isEQ = false;
            for(Procedure pr2: second) {
                if (pr.toString().equals(pr2.toString()))
                isEQ = true;
            }
            if (!isEQ) {
                diferences.append('\n');
                diferences.append("\nEl schema " + sch + " contine particularmente el procedmiento \n");
                diferences.append(pr);
            }
        }
    }

    public void funcCompare(List<Function> first, List<Function> second, String sch) {
        for(Function fn: first) {
            boolean isEQ = false;
            for(Function fn2: second) {
                if (fn.toString().equals(fn2.toString()))
                    isEQ = true;
            }
            if (!isEQ) {
                diferences.append('\n');
                diferences.append("\nEl schema " + sch + " contine particularmente la funcion \n");
                diferences.append(fn);
            }
        }
    }

}

