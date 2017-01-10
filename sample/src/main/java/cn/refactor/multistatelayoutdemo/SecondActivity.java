package cn.refactor.multistatelayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by andy (https://github.com/andyxialm)
 * Creation time: 17/1/9 18:36
 * Description: SecondActivity
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, SecondFragment.newInstance())
                .commit();
    }
}
