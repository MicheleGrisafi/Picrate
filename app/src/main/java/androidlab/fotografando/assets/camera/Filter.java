package androidlab.fotografando.assets.camera;

/**
 * Created by miki4 on 18/09/2017.
 */

public class Filter {
    private String name;

    public int getCost() {
        return cost;
    }

    private int cost;

    @Override
    public String toString() {
        return name;
    }
}
