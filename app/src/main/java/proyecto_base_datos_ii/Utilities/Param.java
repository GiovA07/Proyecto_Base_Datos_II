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
        return 
        "name=" + name + '|' +
        ", type_param=" + type_param + '|' +
        ", type_data=" + type_data + "|";
    }
}
