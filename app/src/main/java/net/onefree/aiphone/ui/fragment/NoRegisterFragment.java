package net.onefree.aiphone.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.onefree.aiphone.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by maoah on 14-10-18.
 */
public class NoRegisterFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.register)
    Button register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_register, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    public void initView() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                register();
                break;
        }
    }

    @OnClick(R.id.register)
    void register() {
    }
}
