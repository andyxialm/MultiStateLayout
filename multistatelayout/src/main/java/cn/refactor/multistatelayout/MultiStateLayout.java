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
        int emptyResId = ta.getResourceId(R.styleable.MultiStateLayout_empty_layout, -1);
        int errorResId = ta.getResourceId(R.styleable.MultiStateLayout_error_layout, -1);
        int loadingResId = ta.getResourceId(R.styleable.MultiStateLayout_loading_layout, -1);
        int networkErrorResId = ta.getResourceId(R.styleable.MultiStateLayout_network_error_layout, -1);
        ta.recycle();

        mEmptyView = LayoutInflater.from(getContext()).inflate(emptyResId, null);
        mErrorView = LayoutInflater.from(getContext()).inflate(errorResId, null);
        mLoadingView = LayoutInflater.from(getContext()).inflate(loadingResId, null);
        mNetworkErrorView = LayoutInflater.from(getContext()).inflate(networkErrorResId, null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() > 1) {
            throw new IllegalStateException("Expect to have one child.");
        }
        mContentView = getChildAt(NORMAL);

        addView(mEmptyView);
        addView(mErrorView);
        addView(mLoadingView);
        addView(mNetworkErrorView);

        mEmptyView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mLoadingView.setVisibility(GONE);
        mNetworkErrorView.setVisibility(GONE);
    }

    @SuppressLint("Assert")
    public void setState(@State int state) {
        assert !(state < NORMAL || state > NETWORK_ERROR);
        hideViewByState(mCurState);
        showViewByState(state);
        mCurState = state;
    }

    public void setEmptyView(View emptyView) {
        removeView(mEmptyView);
        mEmptyView = emptyView;
        mEmptyView.setVisibility(GONE);
        addView(mEmptyView);
    }

    public void setLoadingView(View loadingView) {
        removeView(mLoadingView);
        mLoadingView = loadingView;
        mLoadingView.setVisibility(GONE);
        addView(mLoadingView);
    }

    public void setErrorView(View errorView) {
        removeView(mErrorView);
        mErrorView = errorView;
        mErrorView.setVisibility(GONE);
        addView(mErrorView);
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
                mContentView.setVisibility(GONE);
                break;
            case EMPTY:
                mEmptyView.setVisibility(GONE);
                break;
            case LOADING:
                mLoadingView.setVisibility(GONE);
                break;
            case ERROR:
                mErrorView.setVisibility(GONE);
                break;
            case NETWORK_ERROR:
                mNetworkErrorView.setVisibility(GONE);
                break;
        }
    }

    private void showViewByState(@State int state) {
        switch (state) {
            case NORMAL:
                mContentView.setVisibility(VISIBLE);
                break;
            case EMPTY:
                mEmptyView.setVisibility(VISIBLE);
                break;
            case LOADING:
                mLoadingView.setVisibility(VISIBLE);
                break;
            case ERROR:
                mErrorView.setVisibility(VISIBLE);
                break;
            case NETWORK_ERROR:
                mNetworkErrorView.setVisibility(VISIBLE);
                break;
        }
    }
}
