package proyecto_base_datos_ii.Utilities;

public class Param {
    private String name;
    private String type_param;
    private String type_data;

    public Param(String name, String typeP, String typeDP) {
        this.name = name;
        this.type_param = typeP;
        this.type_data = typeDP;
    }

    public String toString() {
        return name + " " + type_param + " " + type_data;
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



    public String compareTo(Param other) {
        if (this.equals(other)){
            return "Los parametros son iguales.";
        }
        StringBuilder differences = new StringBuilder();
        if(this.name == other.name){
            differences.append("Diferencia en nombres: " + this.name + " vs " + other.name + ".");
        }
        if(this.type_param != other.type_param){
            differences.append("Diferencia en los tipos de parámetros: " + this.type_param + " vs " + other.type_param + ".");
        }
        if(this.type_data != other.type_data){
            differences.append("Diferencia en los tipos de datos: " + this.type_data + " vs " + other.type_data + ".");
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
