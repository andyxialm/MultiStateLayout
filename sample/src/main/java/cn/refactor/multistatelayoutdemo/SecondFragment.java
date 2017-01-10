package cn.refactor.multistatelayoutdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.refactor.multistatelayout.MultiStateLayout;


/**
 * Created by andy (https://github.com/andyxialm)
 * Creation time: 17/1/10 14:34
 * Description: Second fragment
 */
public class SecondFragment extends Fragment {

    private MultiStateLayout mMultiStateLayout;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMultiStateLayout = (MultiStateLayout) view.findViewById(R.id.multi_state_layout);
        mMultiStateLayout.setState(MultiStateLayout.State.LOADING);

        mMultiStateLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMultiStateLayout.setState(MultiStateLayout.State.NETWORK_ERROR);
            }
        }, 2000);

        View networkErrorView = mMultiStateLayout.getNetworkErrorView();
        if (null != networkErrorView) {
            ((TextView) networkErrorView.findViewById(R.id.tv_msg)).setText(R.string.mock_text);
            networkErrorView.findViewById(R.id.btn_reload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMultiStateLayout.setState(MultiStateLayout.State.CONTENT);
                }
            });
        }
    }
}
