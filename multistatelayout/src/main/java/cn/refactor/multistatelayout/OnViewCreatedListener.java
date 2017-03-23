package cn.refactor.multistatelayout;

import android.view.View;

/**
 * Created by andy (https://github.com/andyxialm)
 * Creation time: 17/3/23 22:23
 * Description: OnViewCreatedListener
 */
public interface OnViewCreatedListener {

    /**
     * Called on state view created.
     * @param view  state view
     * @param state state
     */
    void onViewCreated(View view, int state);
}
