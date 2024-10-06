package proyecto_base_datos_ii.Utilities;

import java.io.*;

public class InformPrinter {

    public void printFile() {
        try {
            FileWriter fw = new FileWriter("app/src/main/resources/Diferecnces_Inform.txt");
            fw.write("...");

            fw.close();
        } catch (IOException e) {
            System.out.println("Lectura del archivo fallida");
        }

    }

}
