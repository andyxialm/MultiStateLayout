package cn.refactor.multistatelayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.refactor.multistatelayout.MultiStateLayout;


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
        setupViews();
    }

    private void setupViews() {
        MultiStateLayout multiStateLayout = (MultiStateLayout) findViewById(R.id.multi_state_layout);
        multiStateLayout.setState(MultiStateLayout.State.EMPTY);
    }
}
