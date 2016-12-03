package cn.refactor.multistatelayoutdemo;

import android.app.Application;

import cn.refactor.multistatelayout.MultiStateConfiguration;
import cn.refactor.multistatelayout.MultiStateLayout;


/**
 * Created by andy (https://github.com/andyxialm)
 * Creation time: 16/12/3 22:40
 * Description: Application
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MultiStateConfiguration.Builder builder = new MultiStateConfiguration.Builder();
        builder.setCommonEmptyLayout(R.layout.layout_empty)
               .setCommonErrorLayout(R.layout.layout_error)
               .setCommonLoadingLayout(R.layout.layout_loading);
        MultiStateLayout.setConfiguration(builder);
    }
}
