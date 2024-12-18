package proyecto_base_datos_ii.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                    if (!tableDifferences.trim().isEmpty()) {
                        tempDifferences.append("Ambos esquemas tienen la misma tabla ("+ otb.getName() + ")\n");
                        tempDifferences.append(tableDifferences).append("\n\n");
                    }
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

        return diferences.toString();
    }

    public void tablesCompare(List<Table> first, List<Table> second, String sch) {
        for(Table tab: first) {
            boolean isEQ = false;
            for(Table tab2: second) {
                if (tab.getName().equals(tab2.getName()))
                    isEQ = true;
            }
            if (!isEQ && !tab.getName().contains("pk")) {
                diferences.append("\nEl schema " + sch + " contine particularmente la tabla\n");
                diferences.append(tab);
            }
        }
    }

    public void procCompare(List<Procedure> first, List<Procedure> second, String sch) {
        for(Procedure pr: first) {
            boolean isEQ = false;
            for(Procedure pr2: second) {
                if (pr.getName().equals(pr2.getName())) {
                    String dif = pr.differencesToString(pr2);
                    if (!dif.isEmpty()) {
                        diferences.append("\nLas diferencias del procedimiento " + pr.getName() + " son: \n");
                        diferences.append(dif);
                    }
                    isEQ =  true;
                    break;
                }
            }
            if (!isEQ) {
                diferences.append(" \n El segundo esquema tiene el procedimiento: \n");
                diferences.append(pr.toString()+ "\n");
            }
        }


        for(Procedure pr: second) {
            boolean isEQ = false;
            for(Procedure pr2: first) {
                if (pr.getName().equals(pr2.getName())) {
                    isEQ =  true;
                    break;
                }
            }
            if (!isEQ) {
                diferences.append("\nEl segundo esquema tiene el procedimiento: \n");
                diferences.append(pr.toString() + "\n");
            }
        }
    }

    public void funcCompare(List<Function> first, List<Function> second, String sch) {
        List<Function> exclusiveOfFirstSchema = new ArrayList<>(); 
        List<Function> exclusiveOfSecondSchema = new ArrayList<>();
        HashMap<Function, Function> differentWithSameName = new HashMap<>(); 
        boolean inBoth;

        for(Function f1: first) {
            inBoth = false; 
            for(Function f2: second) {
                if (f1.getName().equals(f2.getName())){
                    if(!f1.equals(f2)) differentWithSameName.put(f1, f2); 
                    inBoth = true; 
                    break;
                }
            }
            if (!inBoth) exclusiveOfFirstSchema.add(f1);
        }

        for(Function f1: second) {
            inBoth = false; 
            for(Function f2: first) {
                if (f1.getName().equals(f2.getName())){
                    if(!f1.equals(f2)) differentWithSameName.put(f2, f1); 
                    inBoth = true; 
                    break;
                }
            }
            if (!inBoth)
            exclusiveOfSecondSchema.add(f1);
        }

        if(!exclusiveOfFirstSchema.isEmpty()){
            diferences.append("\nFunciones exclusivas del esquema 1:");
            for (Function function : exclusiveOfFirstSchema) {
                diferences.append("\n - " + function.getName());
            }
        }
        
        if(!exclusiveOfSecondSchema.isEmpty()){
            diferences.append("\nFunciones exclusivas del esquema 2:");
            for (Function function : exclusiveOfSecondSchema) {
                diferences.append("\n - " + function.getName());
            }
        }

        for (Map.Entry<Function, Function> entry : differentWithSameName.entrySet()) {
            String dif = entry.getKey().differencesToString(entry.getValue());
            diferences.append("\n\nDiferencias en la función " + entry.getKey().getName() + ":");
            diferences.append(dif);
        }
    }
}

