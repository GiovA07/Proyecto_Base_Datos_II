package proyecto_base_datos_ii.Utilities;

public class Param {
    private String name;
    private String type_param;
    private String type_data;

    public Param(String name, String typeP, String typeDP) {
        this.name = name;
        this.type_param = typeP; // IN, OUT IN-OUT
        this.type_data = typeDP; 
    }

    /*
     * Constructor method for functions, param type is IN
     */
    public Param(String name, String datatype){
        this.name = name;
        this.type_data = datatype;
        this.type_param = "IN";
    }

    public String toString() {
        return name + '|' + type_param + '|' + type_data;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Param other = (Param) obj;

        if (!this.name.equals(other.name)) return false;
        if (!this.type_param.equals(other.type_param)) return false;
        if (!this.type_data.equals(other.type_data)) return false;

        return true;
    }

    public String differencesToString(Param other) {
        if (this.equals(other)){
            return "Son el mismo parámetro: " + this;
        }
        StringBuilder differences = new StringBuilder();
        differences.append("\nLos parámetros difieren: ");
        differences.append("\n 1: " + this);
        differences.append("\n 2: "+ other);

        if(!this.name.equals(other.name)){
            differences.append("\n- En nombre: " + this.name + " vs. " + other.name);
        }
        if(!this.type_param.equals(other.type_param)){
            differences.append("\n- En tipo de parámetro: " + this.type_param + " vs. " + other.type_param);
        }
        if(!this.type_data.equals(other.type_data)){
            differences.append("\n- En tipo de dato: " + this.type_data + " vs. " + other.type_data);
        }
        return differences.toString();
    }


    // used to compare in other classes
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type_param.hashCode();
        result = 31 * result + type_data.hashCode();
        return result;
    }

    public static void main(String[] args) {
        Param param1 = new Param("a", "IN", "Integer");
        Param param2 = new Param("b", "OUT", "String");
        Param param4 = new Param("x", "IN", "Float");
        Param param6 = new Param("a", "OUT", "Integer"); // Mismo nombre, tipo_param diferente
        Param param8 = new Param("b", "IN", "String");
        System.out.println(param1.differencesToString(param4));
        System.out.println(param1.differencesToString(param6));
        System.out.println(param2.differencesToString(param4));
        System.out.println(param1.differencesToString(param1));

    }
}
