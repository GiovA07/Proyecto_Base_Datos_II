package proyecto_base_datos_ii.Utilities;
import java.util.List;

public class Procedure {
    private String name;
    private List<Param> params;
    String returnType;

    public Procedure(String name, String returnType, List<Param> params) {
        this.name = name;
        this.params = params;
        this.returnType = returnType;
    }

    public String toString() {
        return name + params + " -> " + returnType;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Procedure)) return false;
        Procedure other = (Procedure) obj;

        if (!this.name.equals(other.name)) return false;
        if (!this.returnType.equals(other.returnType)) return false;

        if (this.params.size() != other.params.size()) return false;
        return params.equals(other.params);
    }

    public String differencesToString(Procedure other) {
        StringBuilder differences = new StringBuilder();
        if (!this.returnType.equals(other.returnType)){
            differences.append("\n- Retorno: ");
            differences.append(this.returnType);
            differences.append(" vs. ");
            differences.append(other.returnType);
        }

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
                    differences.append("\n Los parametros de mas que tiene el segundo esquema son: \n");
                    differences.append(" ->" + strCompare);
                }
            }


        } else {
            strCompare += compareParams(other.params, params);
            if(!strCompare.isEmpty()) {
                differences.append("\n- Parámetros: ");
                differences.append("Parametros del procedimiento perteneciente al Segundo Esquema vs Primer Esquema \n");
                differences.append(strCompare);
            }
            strCompare = "";
            strCompare += paramsDeMas(params, other.params.size());
            if(!strCompare.isEmpty()) {
                differences.append("Los parametros de mas que tiene el primer esquema son:");
                differences.append(strCompare);
            }
        }
        return differences.toString();
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
