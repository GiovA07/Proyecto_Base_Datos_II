package proyecto_base_datos_ii.Utilities;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return name + params +  " -> "+ typeReturn;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Function)) return false;
        Function other = (Function) obj;

        if (!this.name.equals(other.name)) return false;
        if (!this.typeReturn.equals(other.typeReturn)) return false;
        if (this.params.size() != other.params.size()) return false;
        return params.equals(other.params);
    }

    public String differencesToString(Function other) {
        StringBuilder differences = new StringBuilder();
        String strCompare = "";
        if (params.size() <= other.params.size()) {
            strCompare += compareParams(params, other.params);
            if(!strCompare.isEmpty()) {
                differences.append("\n- Parámetros: ");
                differences.append("Diferencias en el procedimiento perteneciente al Primer Esquema vs Segundo Esquema \n");
                differences.append(strCompare);
            }

            if (!(params.size() == other.params.size())) {
                strCompare = "";
                strCompare += paramsDeMas(other.params, params.size());
                if (!strCompare.isEmpty()) {
                    differences.append("\n Los parametros de mas que tiene la 2da función son: \n");
                    differences.append(" ->" + strCompare);
                }
            }


        } else {
            strCompare += compareParams(other.params, params);
            if(!strCompare.isEmpty()) {
                differences.append("\n- Parámetros: ");
                differences.append(strCompare);
            }
            strCompare = "";
            strCompare += paramsDeMas(params, other.params.size());
            if(!strCompare.isEmpty()) {
                differences.append("\nLos parametros de mas que tiene la 1ra funcion son: \n");
                differences.append(strCompare);
            }
        }
        return differences.toString();
    }

    public static Set<Param> commonParams(Function function1, Function function2){
        Set<Param> result = new HashSet<>(function1.params);
        result.retainAll(function2.params);
        return result;
    }

    private String compareParams(List<Param>  params, List<Param> otherParams) {
        StringBuilder differences = new StringBuilder();
        for (int i = 0; i <  params.size(); i++) {
            differences.append(params.get(i).differencesToString(otherParams.get(i)));
        }
        return differences.toString();

    }

    private String paramsDeMas(List<Param> params, int index) {
        StringBuilder differences = new StringBuilder();
        for (int i = index; i <  params.size(); i++) {
            Param param = params.get(i);
            differences.append(param.toString() + "\n");
        }
        return differences.toString();

    }
}
