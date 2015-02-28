package net.onefree.aiphone.ui.activity;

import android.app.ActionBar;
import android.os.Bundle;

import net.onefree.aiphone.R;

/**
 * Created by admin on 2014/11/17.
 */
public class PhoneNumberMergeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_merge);
    }

    public void initView() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("电话号码去重");
    }
}
