package picrate.app.assets.interfaces;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by miki4 on 03/12/2017.
 */

public interface Updatable {
    boolean updateItems(List<?> items);
    List<?> getItems();
}
