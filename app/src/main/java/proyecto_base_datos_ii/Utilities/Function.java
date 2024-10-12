package proyecto_base_datos_ii.Utilities;
import java.util.List;

public class Function {
    private String name;
    private String typeReturn;
    private List<Param> params;


    public Function(String name, String ret, List<Param> params) {
        this.name = name;
        this.typeReturn = ret;
        this.params = params;
    }

    public String toString() {
        return "Function: "+ name + " ret-> "+ typeReturn + params + "\n";
    }
}
