package proyecto_base_datos_ii.Utilities;

import java.io.*;

public class InformPrinter {

    private String init = "\n - - - Diferencias entre las Bases de datos - - - \n\n";
    private String tableSection = "\n\n - - - Tablas que difieren - - -\n";
    private String funcSection = "\n\n - - - Funciones que difieren - - -\n";
    private String procSection = "\n\n Procedimientos almacenados que difieren\n";

    public void printFile() {
        try {
            FileWriter fw = new FileWriter("app/src/main/resources/Diferecnces_Inform.txt");
            int a = tableSection.length();
            int b = funcSection.length();
            int c = procSection.length();

            if (a == 35 && b == 38 && c == 43)
                fw.write(init + " No se encontraron diferencias, bases de datos compatibles");
            else if (a == 35 && b == 38 && c >= 43)
                fw.write(init + procSection);
            else if (a == 35 && b >= 38 && c == 43)
                fw.write(init + funcSection);
            else if (a == 35 && b >= 38 && c >= 43)
                fw.write(init + funcSection + procSection);
            
            else if (a >= 35 && b == 38 && c == 43)
                fw.write(init + tableSection);
            else if (a >= 35 && b == 38 && c >= 43)
                fw.write(init + tableSection + procSection);
            else if (a >= 35 && b >= 38 && c == 43)
                fw.write(init + tableSection + funcSection);
            else if (a >= 35 && b >= 38 && c >= 43)
                fw.write(init + tableSection + funcSection + procSection);

            fw.close();
        } catch (IOException e) {
            System.out.println("Lectura del archivo fallida");
        }

    }

}
