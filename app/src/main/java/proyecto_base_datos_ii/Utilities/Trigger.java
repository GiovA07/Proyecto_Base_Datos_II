package proyecto_base_datos_ii.Utilities;

public class Trigger {
    private String name;
    private String action;
    private String moment;

    public Trigger(String name, String action, String moment) {
        this.name = name;
        this.action = action;
        this.moment = moment;
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
        return "Trigger:\n\t\t|" +
        "name=" + name + '|' +
        ", triggerMoment=" + moment + '|' +
        ", triggerAction=" + action + "|\n";
    }
}
