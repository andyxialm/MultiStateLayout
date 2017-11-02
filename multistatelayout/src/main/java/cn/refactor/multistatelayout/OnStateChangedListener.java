package cn.refactor.multistatelayout;

/**
 * Created on 2017/11/2 17:44
 *
 * @author andy
 */

public interface OnStateChangedListener {

    /**
     * call this method when state was changed
     * @param state current state
     */
    void onChanged(int state);
}
