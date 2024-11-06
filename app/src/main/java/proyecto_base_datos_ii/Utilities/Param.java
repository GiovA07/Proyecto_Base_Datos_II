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

        if (!this.type_param.equals(other.type_param)) return false;
        if (!this.type_data.equals(other.type_data)) return false;

        return true;
    }

    public String differencesToString(Param other) {
        StringBuilder differences = new StringBuilder();
        String dif = "";

        if(!this.type_param.equals(other.type_param)){
            dif += "\n- En tipo de parámetro: " + this.type_param + " vs. " + other.type_param;
        }
        if(!this.type_data.equals(other.type_data)){
            dif += "\n- En tipo de dato: " + this.type_data + " vs. " + other.type_data;
        }

        if (!dif.isEmpty()) {
            differences.append("\n" + this.name + " y " + other.name + " difieren: ");
            differences.append(dif);
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
}
