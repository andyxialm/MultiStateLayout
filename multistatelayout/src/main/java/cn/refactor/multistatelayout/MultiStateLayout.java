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
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by andy (https://github.com/andyxialm)
 * Creation time: 16/11/30 13:46
 * Description : MultiStateLayout
 */
public class MultiStateLayout extends FrameLayout {

    public static final int NORMAL  = 0;
    public static final int EMPTY   = 1;
    public static final int LOADING = 2;
    public static final int ERROR   = 3;
    public static final int NETWORK_ERROR = 4;

    private View mContentView;
    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mNetworkErrorView;

    private int mEmptyResId;
    private int mErrorResId;
    private int mLoadingResId;
    private int mNetworkErrorResId;

    @IntDef({NORMAL, EMPTY, LOADING, ERROR, NETWORK_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    private  @interface State {}

    private @State int mCurState = NORMAL;

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
        mEmptyResId = ta.getResourceId(R.styleable.MultiStateLayout_empty_layout, -1);
        mErrorResId = ta.getResourceId(R.styleable.MultiStateLayout_error_layout, -1);
        mLoadingResId = ta.getResourceId(R.styleable.MultiStateLayout_loading_layout, -1);
        mNetworkErrorResId = ta.getResourceId(R.styleable.MultiStateLayout_network_error_layout, -1);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() > 1) {
            throw new IllegalStateException("Expect to have one child.");
        }
        mContentView = getChildAt(NORMAL);
    }

    @SuppressLint("Assert")
    public void setState(@State int state) {
        assert !(state < NORMAL || state > NETWORK_ERROR);
        hideViewByState(mCurState);
        showViewByState(state);
        mCurState = state;
    }

    public void setEmptyView(@LayoutRes int resId) {
        if (null != mEmptyView) {
            removeView(mEmptyView);
            mEmptyView = null;
        }
        mEmptyResId = resId;
    }

    public void setEmptyView(View emptyView) {
        removeView(mEmptyView);
        mEmptyView = emptyView;
        mEmptyView.setVisibility(GONE);
        addView(mEmptyView);
    }

    public void setLoadingView(@LayoutRes int resId) {
        if (null != mLoadingView) {
            removeView(mLoadingView);
            mLoadingView = null;
        }
        mLoadingResId = resId;
    }

    public void setLoadingView(View loadingView) {
        removeView(mLoadingView);
        mLoadingView = loadingView;
        mLoadingView.setVisibility(GONE);
        addView(mLoadingView);
    }

    public void setErrorView(@LayoutRes int resId) {
        if (null != mErrorView) {
            removeView(mErrorView);
            mErrorView = null;
        }
        mErrorResId = resId;
    }

    public void setErrorView(View errorView) {
        removeView(mErrorView);
        mErrorView = errorView;
        mErrorView.setVisibility(GONE);
        addView(mErrorView);
    }

    public void setNetworkErrorView(@LayoutRes int resId) {
        if (null != mNetworkErrorView) {
            removeView(mNetworkErrorView);
            mNetworkErrorView = null;
        }
        mNetworkErrorResId = resId;
    }

    public void setNetworkErrorView(View networkErrorView) {
        removeView(mNetworkErrorView);
        mNetworkErrorView = networkErrorView;
        mNetworkErrorView.setVisibility(GONE);
        addView(mNetworkErrorView);
    }

    private void hideViewByState(@State int state) {
        switch (state) {
            case NORMAL:
                if (null != mContentView) {
                    mContentView.setVisibility(GONE);
                }
                break;
            case EMPTY:
                if (null != mEmptyView) {
                    mEmptyView.setVisibility(GONE);
                }
                break;
            case LOADING:
                if (null != mLoadingView) {
                    mLoadingView.setVisibility(GONE);
                }
                break;
            case ERROR:
                if (null != mErrorView) {
                    mErrorView.setVisibility(GONE);
                }
                break;
            case NETWORK_ERROR:
                if (null != mNetworkErrorView) {
                    mNetworkErrorView.setVisibility(GONE);
                }
                break;
            default:
                break;
        }
    }

    private void showViewByState(@State int state) {
        switch (state) {
            case NORMAL:
                showContentView();
                break;
            case EMPTY:
                showEmptyView();
                break;
            case LOADING:
                showLoadingView();
                break;
            case ERROR:
                showErrorView();
                break;
            case NETWORK_ERROR:
                showNetworkErrorView();
                break;
            default:
                break;
        }
    }

    private void showContentView() {
        mContentView.setVisibility(VISIBLE);
    }

    private void showEmptyView() {
        if (null == mEmptyView && mEmptyResId > -1) {
            mEmptyView = LayoutInflater.from(getContext()).inflate(mEmptyResId, this, false);
            addView(mEmptyView, mEmptyView.getLayoutParams());
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(VISIBLE);
        }
    }

    private void showLoadingView() {
        if (null == mLoadingView && mLoadingResId > -1) {
            mLoadingView = LayoutInflater.from(getContext()).inflate(mLoadingResId, this, false);
            addView(mLoadingView, mLoadingView.getLayoutParams());
        }
        if (null != mLoadingView) {
            mLoadingView.setVisibility(VISIBLE);
        }
    }

    private void showErrorView() {
        if (null == mErrorView && mErrorResId > -1) {
            mErrorView = LayoutInflater.from(getContext()).inflate(mErrorResId, this, false);
            addView(mErrorView, mErrorView.getLayoutParams());
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(VISIBLE);
        }
    }

    private void showNetworkErrorView() {
        if (null == mNetworkErrorView && mNetworkErrorResId > -1) {
            mNetworkErrorView = LayoutInflater.from(getContext()).inflate(mNetworkErrorResId, this, false);
            addView(mNetworkErrorView, mNetworkErrorView.getLayoutParams());
        }
        if (null != mNetworkErrorView) {
            mNetworkErrorView.setVisibility(VISIBLE);
        }
    }
}
