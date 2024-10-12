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
            int a = tableSection.length();
            int b = funcSection.length();
            int c = procSection.length();
            int d = triggerSection.length();

            if (a == 35 && b == 38 && c == 56)
                init += " No se encontraron diferencias, bases de datos compatibles";

            if (a > 35)
                init += tableSection;
            if (d > 37)
                init += triggerSection;
            if (b > 38)
                init += funcSection;
            if (c > 56)
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
