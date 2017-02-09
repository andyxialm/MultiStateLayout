package cn.refactor.multistatelayout;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by andy (https://github.com/andyxialm)
 * Creation time: 17/2/9 18:15
 * Description: animator loader
 */
public interface TransitionAnimatorLoader {

    /**
     *
     * @param targetView  target view
     * @return animator
     */
    ObjectAnimator loadAnimator(View targetView);
}
