package proyecto_base_datos_ii.Utilities;

import java.util.List;

public class Procedure {
    private String name;
    private String typeReturn;
    private List<Param> params;

    public Procedure(String name, String ret, List<Param> params) {
        this.name = name;
        this.typeReturn = ret;
        this.params = params;
    }
}
