package cn.refactor.multistatelayoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cn.refactor.multistatelayout.MultiStateLayout;
import cn.refactor.multistatelayout.OnStateChangedListener;
import cn.refactor.multistatelayout.OnStateViewCreatedListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int KEY_CUSTOM_NOTICE = 1024;

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
        findViewById(R.id.tv_content).setOnClickListener(this);

        // setup toolbar
        setupToolbar();

        // setup custom notice message view
        View customNoticeMsgView = LayoutInflater.from(this).inflate(R.layout.layout_custom_notice, mStateLayout, false);
        mStateLayout.putCustomStateView(KEY_CUSTOM_NOTICE, customNoticeMsgView);

        // set view created listener
        mStateLayout.setOnStateViewCreatedListener(new OnStateViewCreatedListener() {
            @Override
            public void onViewCreated(View view, int state) {
                switch (state) {
                    case MultiStateLayout.State.NETWORK_ERROR:
                        view.findViewById(R.id.btn_reload).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, getString(R.string.reload), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_content:
                        mStateLayout.setState(MultiStateLayout.State.CONTENT);
                        break;
                    case R.id.menu_empty:
                        mStateLayout.setState(MultiStateLayout.State.EMPTY);
                        break;
                    case R.id.menu_loading:
                        mStateLayout.setState(MultiStateLayout.State.LOADING);
                        break;
                    case R.id.menu_error:
                        mStateLayout.setState(MultiStateLayout.State.ERROR);
                        break;
                    case R.id.menu_network_error:
                        mStateLayout.setState(MultiStateLayout.State.NETWORK_ERROR);
                        break;
                    case R.id.menu_custom:
                        mStateLayout.setCustomState(KEY_CUSTOM_NOTICE, true);
                        execHideViewDelay(mStateLayout.findCustomStateViewByKey(KEY_CUSTOM_NOTICE));
                        break;
                    default:
                        break;
                }
                Log.d(TAG, String.valueOf(mStateLayout.getState()));
                return true;
            }
        });
    }

    private void execHideViewDelay(final View customView) {
        customView.postDelayed(new Runnable() {
            @Override
            public void run() {
                customView.setVisibility(View.GONE);
            }
        }, 3000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_content:
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                break;
            default:
                break;
        }
    }
}
