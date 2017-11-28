package androidlab.app.assets.objects;

/**
 * Created by miki4 on 18/09/2017.
 */

public class Filter {
    private String name;
    private int cost;

    public Filter(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }


    @Override
    public String toString() {
        return name;
    }
}
