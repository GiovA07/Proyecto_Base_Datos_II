package proyecto_base_datos_ii.Utilities;

import java.io.*;

public class InformPrinter {

    private String init = "\n - - - Diferencias entre las Bases de datos - - - \n\n";
    private String tableSection = "\n\n - - - Tablas que difieren - - -\n";
    private String triggerSection = "\n\n - - - Triggers que difieren - - -\n";
    private String funcSection = "\n\n - - - Funciones que difieren - - -\n";
    private String procSection = "\n\n - - - Procedimientos almacenados que difieren - - - \n";

    public void printFile() {
        try {
            FileWriter fw = new FileWriter("app/src/main/resources/Diferecnces_Inform.txt");
            int numTabDiff = tableSection.length(),
            numFuncDiff = funcSection.length(),
            numProcDiff = procSection.length(),
            numTriDiff = triggerSection.length();

            if (numTabDiff == 35 && numFuncDiff == 38 && numProcDiff == 56)
                init += " No se encontraron diferencias, bases de datos compatibles";

            if (numTabDiff > 35)
                init += tableSection;
            if (numTriDiff > 37)
                init += triggerSection;
            if (numFuncDiff > 38)
                init += funcSection;
            if (numProcDiff > 56)
                init += procSection;

            fw.write(init);
            fw.close();
        } catch (IOException e) {
            System.out.println("Lectura del archivo fallida");
        }

    }

    public void addTable(String distTable) {
        tableSection += distTable;
    }

    public void addTrigger(String distTrigger) {
        triggerSection += distTrigger;
    }

    public void addFunction(String distFunc) {
        funcSection += distFunc;
    }

    public void addProcedure(String distProc) {
        procSection += distProc;
    }
}
