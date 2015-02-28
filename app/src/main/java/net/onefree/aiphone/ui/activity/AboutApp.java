package net.onefree.aiphone.ui.activity;

import android.app.ActionBar;
import android.os.Bundle;

import net.onefree.aiphone.R;

/**
 * Created by maoah on 14/12/1.
 */
public class AboutApp extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        initView();
    }

    public void initView() {

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(" 关于 AiPhone");
    }
}
