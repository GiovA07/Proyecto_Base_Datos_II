package proyecto_base_datos_ii.Utilities;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Function {
    private String name;
    private String typeReturn;
    private Set<Param> params;


    public Function(String name, String ret, Set<Param> params) {
        this.name = name;
        this.typeReturn = ret;
        this.params = params;
    }

    public String toString() {
        return "Function: "+ name + " ret-> "+ typeReturn + params + "\n";
    }

    public boolean equals(Function other){
        if (this.name != other.name) return false;
        if (this.typeReturn != other.typeReturn) return false;
        if (this.params.size() != other.params.size()) return false;
        return params.equals(other.params);
    }

    public String compareTo(Function other){
        if (this.equals(other)){
            return "Las funciones son iguales";
        }
        StringBuilder differences = new StringBuilder();
        if(this.name == other.name){
            differences.append("Diferencia en nombres: " + this.name + " vs " + other.name + ".");
        }
        //list common parameters
        Set<Param> commonParams = params.retainAll(other.params);


        return differences.toString();
    }


}
