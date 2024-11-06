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

    public String getName() {
        return name;
    }

    public String toString() {
        return name + params +  " -> "+ typeReturn;
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

        compareReturnType(other, differences);

        String paramDifferences = "";
        StringBuilder strExtraParams = new StringBuilder();

        boolean sameSize = this.params.size() == other.params.size();

        if (!paramDifferences.isEmpty()) {
            differences.append("\n- Parametros: Diferencias encontradas:\n")
                       .append(paramDifferences);
        }
        //Si no tienen el mismo size, significa que alguno tiene un parametro de mas
        if (!sameSize) {
            if (this.params.size() < other.params.size()) {
                paramDifferences = compareParams(this.params, other.params);
                addExtraParams(other.params, this.params.size(), "segundo esquema", strExtraParams);
            } else {
                paramDifferences = compareParams(other.params, this.params);
                addExtraParams(this.params, other.params.size(), "primer esquema", strExtraParams);
            }
        }
        // si hay diferencias:
        if (!paramDifferences.isEmpty()) {
            differences.append("\n- Parametros: Diferencias encontradas:\n")
                       .append(paramDifferences);
        }
        //si tiene parametros extras:
        if (!strExtraParams.isEmpty())
            differences.append(strExtraParams);

        return differences.toString();
    }


    private void addExtraParams(List<Param> params, int startIndex, String schemaName, StringBuilder differences) {
        differences.append("\n- Parametros adicionales en el ").append(schemaName).append(":\n");
        for (int i = startIndex; i < params.size(); i++) {
            differences.append(" -> ").append(params.get(i).toString()).append("\n");
        }
    }

    private String compareParams(List<Param>  params, List<Param> otherParams) {
        StringBuilder differences = new StringBuilder();
        for (int i = 0; i <  params.size(); i++) {
            differences.append(params.get(i).differencesToString(otherParams.get(i)));
        }
        return differences.toString();

    }

    private void compareReturnType(Function other, StringBuilder differences) {
        if (!this.typeReturn.equals(other.typeReturn)){
            differences.append("\n- Retorno: ")
            .append(this.typeReturn)
            .append(" vs. ")
            .append(other.typeReturn);
        }

    }
}
