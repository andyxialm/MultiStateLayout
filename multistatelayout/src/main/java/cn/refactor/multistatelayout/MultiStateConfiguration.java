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

        public Builder() {
        }

        public Builder setCommonEmptyLayout(@LayoutRes int resId) {
            mEmptyResId = resId;
            return this;
        }

        public Builder setCommonLoadingLayout(@LayoutRes int resId) {
            mLoadingResId = resId;
            return this;
        }

        public Builder setCommonErrorLayout(@LayoutRes int resId) {
            mErrorResId = resId;
            return this;
        }

        public Builder setCommonNetworkErrorLayout(@LayoutRes int resId) {
            mNetworkErrorResId = resId;
            return this;
        }

        public int getCommonEmptyLayout() {
            return mEmptyResId;
        }

        public int getCommonLoadingLayout() {
            return mLoadingResId;
        }

        public int getCommonErrorLayout() {
            return mErrorResId;
        }

        public int getCommonNetworkErrorLayout() {
            return mNetworkErrorResId;
        }
    }
}
