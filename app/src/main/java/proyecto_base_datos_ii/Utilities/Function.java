package proyecto_base_datos_ii.Utilities;
import java.util.HashSet;
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
        if(this.equals(other)){
            differences.append("\nAmbas funciones son iguales: ");
            differences.append(this.toString());
            return differences.toString();
        }
    
        differences.append("\nLas funciones difieren: ");
        differences.append("\n 1: " + this);
        differences.append("\n 2: "+ other);

        if (!this.name.equals(other.name)){
            differences.append("\n- Nombre: ");
            differences.append(this.name);
            differences.append(" vs. ");
            differences.append(other.name);
        }

        if (!this.typeReturn.equals(other.typeReturn)){
            differences.append("\n- Tipo de Retorno : ");
            differences.append(this.typeReturn);
            differences.append(" vs. ");
            differences.append(other.typeReturn);
        }

        if (!this.params.equals(other.params)){
            differences.append("\n- Parámetros: ");
            Set<Param> commonParams = commonParams(this, other);
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
    
    public static Set<Param> commonParams(Function function1, Function function2){
        Set<Param> result = new HashSet<>(function1.params);
        result.retainAll(function2.params);
        return result;
    }

    public static void main(String[] args){
        Set<Param> params1 = new HashSet<>();
        params1.add(new Param("a", "int"));
        params1.add(new Param("b", "int"));

        Set<Param> params2 = new HashSet<>();
        params2.add(new Param("x", "String"));

        Set<Param> params3 = new HashSet<>();
        params3.add(new Param("y", "double"));
        params3.add(new Param("z", "double"));

        Set<Param> params4 = new HashSet<>();
        params4.add(new Param("a", "int"));

        // Funciones similares
        Function func1 = new Function("add", "int", params1);
        Function func1Similar = new Function("add", "int", params1); // Igual
        Function func1DifferentReturn = new Function("add", "float", params1); // Diferente tipo de retorno
        Function func1DifferentParamCount = new Function("add", "int", new HashSet<>()); // Diferente número de parámetros
        Function func1DifferentCommonParams = new Function("add", "int", params4); // Diferente número de parámetros, uno en común

        Function func2 = new Function("print", "void", params2);

        System.out.println(func1.differencesToString(func1));
        System.out.println(func1.differencesToString(func1Similar));
        System.out.println(func1.differencesToString(func1DifferentReturn));
        System.out.println(func1.differencesToString(func1DifferentParamCount));
        System.out.println(func1.differencesToString(func1DifferentCommonParams));
        System.out.println(func1.differencesToString(func2));

    }
}
