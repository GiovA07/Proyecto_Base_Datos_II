package proyecto_base_datos_ii.Utilities;

import java.io.*;

public class InformPrinter {


    public void printFile(String res) {
        try {
            FileWriter fw = new FileWriter("app/src/main/resources/differences_report.txt");
            fw.write(res);
            fw.close();
        } catch (IOException e) {
            System.out.println("Lectura del archivo fallida");
            System.exit(1);
        }

    }

}
