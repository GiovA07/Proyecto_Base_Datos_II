package proyecto_base_datos_ii.Utilities;
import java.util.HashSet;
import java.util.Set;

public class Procedure {
    private String name;
    private Set<Param> params;

    public Procedure(String name, Set<Param> params) {
        this.name = name;
        this.params = params;
    }

    public String toString() {
        return name + params;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Function)) return false;
        Procedure other = (Procedure) obj;
        
        if (!this.name.equals(other.name)) return false;
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

    public static void main(String[] args){
        Set<Param> params1 = new HashSet<>();
        params1.add(new Param("a", "IN", "Integer"));
        params1.add(new Param("b", "OUT", "String"));

        Set<Param> params2 = new HashSet<>();
        params2.add(new Param("a", "IN", "Integer")); // Common parameter
        params2.add(new Param("c", "OUT", "Double"));

        Set<Param> params3 = new HashSet<>();
        params3.add(new Param("x", "IN", "Float"));
        params3.add(new Param("y", "IN-OUT", "Boolean"));

        Set<Param> params4 = new HashSet<>();
        params4.add(new Param("a", "IN", "Integer")); // Common parameter
        params4.add(new Param("b", "IN", "String")); // Similar name, same type

        // Create Procedure objects
        Procedure proc1 = new Procedure("procedureA", params1);
        Procedure proc2 = new Procedure("procedureB", params2);
        Procedure proc3 = new Procedure("procedureC", params3);
        Procedure proc4 = new Procedure("procedureA", params4);

        // Comparing procedures
        System.out.println(proc1.differencesToString(proc1));
        System.out.println(proc1.differencesToString(proc2));
        System.out.println(proc1.differencesToString(proc3)); 
        System.out.println(proc1.differencesToString(proc4));
    }
}
