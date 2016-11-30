package cn.refactor.multistatelayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.refactor.multistatelayout.MultiStateLayout;

public class MainActivity extends AppCompatActivity {
    private MultiStateLayout mStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void setupViews() {
        mStateLayout = (MultiStateLayout) findViewById(R.id.multi_state_layout);
        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_normal:
                        mStateLayout.setState(MultiStateLayout.NORMAL);
                        break;
                    case R.id.menu_empty:
                        mStateLayout.setState(MultiStateLayout.EMPTY);
                        break;
                    case R.id.menu_loading:
                        mStateLayout.setState(MultiStateLayout.LOADING);
                        break;
                    case R.id.menu_error:
                        mStateLayout.setState(MultiStateLayout.ERROR);
                        break;
                    case R.id.menu_network_error:
                        mStateLayout.setState(MultiStateLayout.NETWORK_ERROR);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        View customNetworkErrorView = View.inflate(this, R.layout.layout_custom_network_error, null);
        mStateLayout.setNetworkErrorView(customNetworkErrorView);
        customNetworkErrorView.findViewById(R.id.btn_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStateLayout.setState(MultiStateLayout.NORMAL);
            }
        });
    }

}
