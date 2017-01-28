/**
 * Copyright 2016 andy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.refactor.multistatelayout;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by andy (https://github.com/andyxialm)
 * Creation time: 16/11/30 13:46
 * Description : MultiStateLayout
 */
public class MultiStateLayout extends FrameLayout {

    private static final int DEFAULT_ANIM_DURATION = 300;
    private static MultiStateConfiguration.Builder mCommonConfiguration;
    private View mContentView;
    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mNetworkErrorView;

    private int mEmptyResId;
    private int mErrorResId;
    private int mLoadingResId;
    private int mNetworkErrorResId;

    private int mAnimDuration;
    private boolean mAnimEnable;
    private LayoutInflater mInflater;
    private ObjectAnimator mAlphaAnimator;

    @IntDef({State.CONTENT, State.EMPTY, State.LOADING, State.ERROR, State.NETWORK_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        int CONTENT = 0;
        int EMPTY   = 1;
        int LOADING = 2;
        int ERROR   = 3;
        int NETWORK_ERROR = 4;
    }

    private @State int mCurState = State.CONTENT;

    public MultiStateLayout(Context context) {
        this(context, null);
    }

    public MultiStateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(attrs);
    }

    public MultiStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MultiStateLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateLayout);
        mEmptyResId = ta.getResourceId(R.styleable.MultiStateLayout_layout_empty, getCommonLayoutResIdByState(State.EMPTY));
        mErrorResId = ta.getResourceId(R.styleable.MultiStateLayout_layout_error, getCommonLayoutResIdByState(State.ERROR));
        mLoadingResId = ta.getResourceId(R.styleable.MultiStateLayout_layout_loading, getCommonLayoutResIdByState(State.LOADING));
        mNetworkErrorResId = ta.getResourceId(R.styleable.MultiStateLayout_layout_network_error, getCommonLayoutResIdByState(State.NETWORK_ERROR));

        mAnimEnable = ta.getBoolean(R.styleable.MultiStateLayout_animEnable, isCommonAnimEnable());
        mAnimDuration = ta.getInt(R.styleable.MultiStateLayout_animDuration, getCommonAnimDuration());
        ta.recycle();

        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() > 1) {
            throw new IllegalStateException("Expect to have one child.");
        } else if (getChildCount() == 1) {
            mContentView = getChildAt(State.CONTENT);
        } else {
            mContentView = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearTargetViewAnimation();
    }

    /**
     * set common mCommonConfiguration settings
     *
     * @param builder MultiStateConfiguration.Builder
     */
    @SuppressWarnings("unused")
    public static void setConfiguration(MultiStateConfiguration.Builder builder) {
        mCommonConfiguration = builder;
    }

    /**
     * set state
     *
     * @param state State
     */
    @SuppressLint("Assert")
    public void setState(@State int state) {
        setState(state, false);
    }

    /**
     * set state
     * @param state State
     * @param displayContentLayout display or conceal content layout
     */
    @SuppressLint("Assert")
    public void setState(@State int state, boolean displayContentLayout) {
        assert !(state < State.CONTENT || state > State.NETWORK_ERROR);
        clearTargetViewAnimation();
        hideViewByState(mCurState, displayContentLayout);
        showViewByState(state);
        mCurState = state;
    }

    /**
     * set empty view by resource id
     *
     * @param resId layout
     */
    @SuppressWarnings("unused")
    public void setEmptyView(@LayoutRes int resId) {
        if (null != mEmptyView) {
            removeView(mEmptyView);
            mEmptyView = null;
        }
        mEmptyResId = resId;
    }

    /**
     * set empty view by view that had created.
     *
     * @param emptyView view
     */
    @SuppressWarnings("unused")
    public void setEmptyView(View emptyView) {
        removeView(mEmptyView);
        mEmptyView = emptyView;
        mEmptyView.setVisibility(GONE);
        addView(mEmptyView);
    }

    /**
     * return empty view
     *
     * @return view
     */
    @SuppressWarnings("unused")
    public View getEmptyView() {
        if (null == mEmptyView) {
            if (mEmptyResId > -1) {
                mEmptyView = mInflater.inflate(mEmptyResId, this, false);
                addView(mEmptyView, mEmptyView.getLayoutParams());
                mEmptyView.setVisibility(GONE);
            }
        }
        return mEmptyView;
    }

    /**
     * set loading view by resource id
     *
     * @param resId layout
     */
    @SuppressWarnings("unused")
    public void setLoadingView(@LayoutRes int resId) {
        if (null != mLoadingView) {
            removeView(mLoadingView);
            mLoadingView = null;
        }
        mLoadingResId = resId;
    }

    /**
     * set loading view by view that had created.
     *
     * @param loadingView view
     */
    @SuppressWarnings("unused")
    public void setLoadingView(View loadingView) {
        removeView(mLoadingView);
        mLoadingView = loadingView;
        mLoadingView.setVisibility(GONE);
        addView(mLoadingView);
    }

    /**
     * return loading view
     *
     * @return view
     */
    @SuppressWarnings("unused")
    public View getLoadingView() {
        if (null == mLoadingView) {
            if (mLoadingResId > -1) {
                mLoadingView = mInflater.inflate(mLoadingResId, this, false);
                addView(mLoadingView, mLoadingView.getLayoutParams());
                mLoadingView.setVisibility(GONE);
            }
        }
        return mLoadingView;
    }

    /**
     * set error view by resource id
     *
     * @param resId layout
     */
    @SuppressWarnings("unused")
    public void setErrorView(@LayoutRes int resId) {
        if (null != mErrorView) {
            removeView(mErrorView);
            mErrorView = null;
        }
        mErrorResId = resId;
    }

    /**
     * set error view by view that had created.
     *
     * @param errorView view
     */
    @SuppressWarnings("unused")
    public void setErrorView(View errorView) {
        removeView(mErrorView);
        mErrorView = errorView;
        mErrorView.setVisibility(GONE);
        addView(mErrorView);
    }

    /**
     * return error view
     *
     * @return view
     */
    @SuppressWarnings("unused")
    public View getErrorView() {
        if (null == mErrorView) {
            if (mErrorResId > -1) {
                mErrorView = mInflater.inflate(mErrorResId, this, false);
                addView(mErrorView, mErrorView.getLayoutParams());
                mErrorView.setVisibility(GONE);
            }
        }
        return mErrorView;
    }

    /**
     * set network error view by resource id
     *
     * @param resId layout
     */
    @SuppressWarnings("unused")
    public void setNetworkErrorView(@LayoutRes int resId) {
        if (null != mNetworkErrorView) {
            removeView(mNetworkErrorView);
            mNetworkErrorView = null;
        }
        mNetworkErrorResId = resId;
    }

    /**
     * set network error view by view that had created.
     *
     * @param networkErrorView view
     */
    @SuppressWarnings("unused")
    public void setNetworkErrorView(View networkErrorView) {
        removeView(mNetworkErrorView);
        mNetworkErrorView = networkErrorView;
        mNetworkErrorView.setVisibility(GONE);
        addView(mNetworkErrorView);
    }

    /**
     * return network error view
     *
     * @return view
     */
    @SuppressWarnings("unused")
    public View getNetworkErrorView() {
        if (null == mNetworkErrorView) {
            if (mNetworkErrorResId > -1) {
                mNetworkErrorView = mInflater.inflate(mNetworkErrorResId, this, false);
                addView(mNetworkErrorView, mNetworkErrorView.getLayoutParams());
                mNetworkErrorView.setVisibility(GONE);
            }
        }
        return mNetworkErrorView;
    }

    /**
     * open/close optional animation
     * @param animEnable open/close
     */
    @SuppressWarnings("unused")
    public void setAnimEnable(boolean animEnable) {
        mAnimEnable = animEnable;
    }

    /**
     * get animation status
     * @return enable
     */
    @SuppressWarnings("unused")
    public boolean isAnimEnable() {
        return mAnimEnable;
    }

    /**
     * set animation duration
     * @param duration duration
     */
    @SuppressWarnings("unused")
    public void setAnimDuration(int duration) {
        mAnimDuration = duration;
    }

    /**
     * get animation duration
     */
    @SuppressWarnings("unused")
    public int getAnimDuration() {
        return mAnimDuration;
    }

    private void clearTargetViewAnimation() {
        if (null != mAlphaAnimator && mAlphaAnimator.isRunning()) {
            mAlphaAnimator.cancel();
        }
    }

    private void hideViewByState(@State int state, boolean displayContentLayout) {
        switch (state) {
            case State.CONTENT:
                if (null != mContentView) {
                    mContentView.setVisibility(displayContentLayout ? VISIBLE : GONE);
                }
                break;
            case State.EMPTY:
                if (null != mEmptyView) {
                    mEmptyView.setVisibility(GONE);
                }
                break;
            case State.LOADING:
                if (null != mLoadingView) {
                    mLoadingView.setVisibility(GONE);
                }
                break;
            case State.ERROR:
                if (null != mErrorView) {
                    mErrorView.setVisibility(GONE);
                }
                break;
            case State.NETWORK_ERROR:
                if (null != mNetworkErrorView) {
                    mNetworkErrorView.setVisibility(GONE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * show view by current state
     *
     * @param state state
     */
    private void showViewByState(@State int state) {
        switch (state) {
            case State.CONTENT:
                showContentView();
                break;
            case State.EMPTY:
                showEmptyView();
                break;
            case State.LOADING:
                showLoadingView();
                break;
            case State.ERROR:
                showErrorView();
                break;
            case State.NETWORK_ERROR:
                showNetworkErrorView();
                break;
            default:
                break;
        }
    }

    /**
     * start alpha animation
     * @param targetView target view
     */
    private void execAlphaAnimation(View targetView) {
        if (null == targetView) {
            return;
        }
        mAlphaAnimator = ObjectAnimator.ofFloat(targetView, "alpha", 0.0f, 1.0f);
        mAlphaAnimator.setInterpolator(new AccelerateInterpolator());
        mAlphaAnimator.setDuration(mAnimDuration);
        mAlphaAnimator.start();
    }

    /**
     * show content view without animation
     */
    private void showContentView() {
        if (null != mContentView) {
            mContentView.setVisibility(VISIBLE);
        }
    }

    /**
     * show customize empty view
     */
    private void showEmptyView() {
        if (null == mEmptyView && mEmptyResId > -1) {
            mEmptyView = mInflater.inflate(mEmptyResId, this, false);
            addView(mEmptyView, mEmptyView.getLayoutParams());
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(VISIBLE);
            if (mAnimEnable) {
                execAlphaAnimation(mEmptyView);
            }
        } else {
            throw new NullPointerException("Expect to have an empty view.");
        }
    }

    /**
     * show customize loading view
     */
    private void showLoadingView() {
        if (null == mLoadingView && mLoadingResId > -1) {
            mLoadingView = mInflater.inflate(mLoadingResId, this, false);
            addView(mLoadingView, mLoadingView.getLayoutParams());
        }
        if (null != mLoadingView) {
            mLoadingView.setVisibility(VISIBLE);
            if (mAnimEnable) {
                execAlphaAnimation(mLoadingView);
            }
        } else {
            throw new NullPointerException("Expect to have an loading view.");
        }
    }

    /**
     * show customize error view
     */
    private void showErrorView() {
        if (null == mErrorView && mErrorResId > -1) {
            mErrorView = mInflater.inflate(mErrorResId, this, false);
            addView(mErrorView, mErrorView.getLayoutParams());
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(VISIBLE);
            if (mAnimEnable) {
                execAlphaAnimation(mErrorView);
            }
        } else {
            throw new NullPointerException("Expect to have one error view.");
        }
    }

    /**
     * show customize network error view
     */
    private void showNetworkErrorView() {
        if (null == mNetworkErrorView && mNetworkErrorResId > -1) {
            mNetworkErrorView = mInflater.inflate(mNetworkErrorResId, this, false);
            addView(mNetworkErrorView, mNetworkErrorView.getLayoutParams());
        }
        if (null != mNetworkErrorView) {
            mNetworkErrorView.setVisibility(VISIBLE);
            if (mAnimEnable) {
                execAlphaAnimation(mNetworkErrorView);
            }
        } else {
            throw new NullPointerException("Expect to have one network error view.");
        }
    }

    /**
     * get common layout resource id by state except CONTENT(Content)
     * @param state state
     * @return resource id
     */
    private int getCommonLayoutResIdByState(@State int state) {
        switch (state) {
            case State.EMPTY:
                return mCommonConfiguration == null ? -1 : mCommonConfiguration.getCommonEmptyLayout();
            case State.LOADING:
                return mCommonConfiguration == null ? -1 : mCommonConfiguration.getCommonLoadingLayout();
            case State.ERROR:
                return mCommonConfiguration == null ? -1 : mCommonConfiguration.getCommonErrorLayout();
            case State.NETWORK_ERROR:
                return mCommonConfiguration == null ? -1 : mCommonConfiguration.getCommonNetworkErrorLayout();
            case State.CONTENT:
                return -1;
        }
        return 0;
    }

    /**
     * get anim status from common settings
     * @return animEnable
     */
    private boolean isCommonAnimEnable() {
        return mCommonConfiguration != null && mCommonConfiguration.isAnimEnable();
    }

    /**
     * get anim duration from common settings
     * @return animDuration
     */
    private int getCommonAnimDuration() {
        return mCommonConfiguration == null ? DEFAULT_ANIM_DURATION : mCommonConfiguration.getAnimDuration();
    }
}
