package proyecto_base_datos_ii.Utilities;

public class Trigger {
    private String name;
    private String action;
    private String moment;

    public Trigger(String name, String action, String moment) {
        this.name = name;
        this.action = action; //INSERT, UPDATE, DELETE
        this.moment = moment; //BEFORE, AFTER, INSTEADOF
    }

    public String getName() {
        return name;
    }

    public String getMoment() {
        return moment;
    }

    public String getAction() {
        return action;
    }


    public String toString() {
        return name + " " + moment + " " + action;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Trigger)) return false;
        Trigger other = (Trigger) obj;

        if (!this.name.equals(other.name)) return false;
        if (!this.action.equals(other.action)) return false;
        if (!this.moment.equals(other.moment)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + action.hashCode();
        result = 31 * result + moment.hashCode();
        return result;
    }

    public String differencesToString(Trigger other) {
        StringBuilder differences = new StringBuilder();
        if(this.equals(other)){
            differences.append("\nLos triggers son iguales: ");
            differences.append(this.toString());
            return differences.toString();
        }

        differences.append("\nLos triggers difieren: ");
        differences.append("\n 1: " + this);
        differences.append("\n 2: "+ other);

        if (!this.name.equals(other.name)){
            differences.append("\n- Nombre: ");
            differences.append(this.name);
            differences.append(" vs. ");
            differences.append(other.name);
        }

        if (!this.moment.equals(other.moment)){
            differences.append("\n- Momento de Ejecucion: ");
            differences.append(this.moment);
            differences.append(" vs. ");
            differences.append(other.moment);
        }

        if (!this.action.equals(other.action)){
            differences.append("\n- Por acción: ");
            differences.append(this.action);
            differences.append(" vs. ");
            differences.append(other.action);
        }
        return differences.toString();
    }
}
