package androidlab.app.assets.interfaces;

import android.widget.Adapter;

/**
 * Created by miki4 on 29/05/2017.
 */

/** Interfaccia usata per permettere agli asynctask di comunicare un adapter a fine esecuzione **/
public interface AsyncResponse {
    void processFinish(Adapter output);
}
