/**
 * Copyright 2017 andy (https://github.com/andyxialm)
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

import android.support.annotation.LayoutRes;

/**
 * Created by andy (https://github.com/andyxialm)
 * Creation time: 16/12/3 22:21
 * Description: MultiStateLayout common settings
 */
public class MultiStateConfiguration {

    public static class Builder {

        private int mEmptyResId   = -1;
        private int mErrorResId   = -1;
        private int mLoadingResId = -1;
        private int mNetworkErrorResId = -1;
        private int mAnimDuration = 300;
        private boolean mAnimEnable;

        public Builder() {
        }

        @SuppressWarnings("unused")
        public Builder setCommonEmptyLayout(@LayoutRes int resId) {
            mEmptyResId = resId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setCommonLoadingLayout(@LayoutRes int resId) {
            mLoadingResId = resId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setCommonErrorLayout(@LayoutRes int resId) {
            mErrorResId = resId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setCommonNetworkErrorLayout(@LayoutRes int resId) {
            mNetworkErrorResId = resId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setAnimDuration(int duration) {
            mAnimDuration = duration;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setAnimEnable(boolean animEnable) {
            mAnimEnable = animEnable;
            return this;
        }

        @SuppressWarnings("unused")
        public int getCommonEmptyLayout() {
            return mEmptyResId;
        }

        @SuppressWarnings("unused")
        public int getCommonLoadingLayout() {
            return mLoadingResId;
        }

        @SuppressWarnings("unused")
        public int getCommonErrorLayout() {
            return mErrorResId;
        }

        @SuppressWarnings("unused")
        public int getCommonNetworkErrorLayout() {
            return mNetworkErrorResId;
        }

        @SuppressWarnings("unused")
        public int getAnimDuration() {
            return mAnimDuration;
        }

        @SuppressWarnings("unused")
        public boolean isAnimEnable() {
            return mAnimEnable;
        }

    }
}
