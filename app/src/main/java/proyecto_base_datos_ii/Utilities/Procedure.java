package proyecto_base_datos_ii.Utilities;
import java.util.HashSet;
import java.util.Set;

public class Procedure {
    private String name;
    private Set<Param> params;
    String returnType;

    public Procedure(String name, String returnType, Set<Param> params) {
        this.name = name;
        this.params = params;
        this.returnType = returnType;
    }

    public String toString() {
        return name + params + " -> " + returnType;
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
        if(this.equals(other)){
            differences.append("\nLos procedimientos son iguales: ");
            differences.append(this.toString());
            return differences.toString();
        }

        differences.append("\nLos procedimientos difieren: ");
        differences.append("\n 1: " + this);
        differences.append("\n 2: "+ other);

        if (!this.name.equals(other.name)){
            differences.append("\n- Nombre: ");
            differences.append(this.name);
            differences.append(" vs. ");
            differences.append(other.name);
        }

        if (!this.returnType.equals(other.returnType)){
            differences.append("\n- Retorno: ");
            differences.append(this.returnType);
            differences.append(" vs. ");
            differences.append(other.returnType);
        }

        if (!this.params.equals(other.params)){
            differences.append("\n- Parámetros: ");
            Set<Param> commonParams = commonParams(this.params, other.params);
            if(!commonParams.isEmpty()){
                differences.append("\n-- Comunes: ");
                for(Param param: commonParams){
                    differences.append("\n--- ");
                    differences.append(param);
                }
            }
            Set<Param> exclusiveParamsThisFunc = new HashSet<>(this.params);
            exclusiveParamsThisFunc.removeAll(commonParams);
            if(!exclusiveParamsThisFunc.isEmpty()){
                differences.append("\n-- Exclusivos del primero: ");
                for(Param param: exclusiveParamsThisFunc){
                    differences.append("\n--- ");
                    differences.append(param);
                }
            }
            Set<Param> exclusiveParamsOtherFunc = new HashSet<>(other.params);
            exclusiveParamsOtherFunc.removeAll(commonParams);
            if(!exclusiveParamsOtherFunc.isEmpty()){
                differences.append("\n-- Exclusivos del segundo: ");
                for(Param param: exclusiveParamsOtherFunc){
                    differences.append("\n--- ");
                    differences.append(param);
                }
            }
        }
        return differences.toString();
    }

    public static Set<Param> commonParams(Set<Param> s1, Set<Param> s2){
        Set<Param> result = new HashSet<>(s1);
        result.retainAll(s2);
        return result;
    }

}
