package net.onefree.aiphone;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import net.onefree.aiphone.ui.activity.AboutApp;
import net.onefree.aiphone.ui.activity.BaseActivity;
import net.onefree.aiphone.ui.fragment.ToolsFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 主界面
 */
public class MainAcitivity extends BaseActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    @InjectView(R.id.share)
    public RadioButton shareRadio;
    @InjectView(R.id.tool)
    public RadioButton toolRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_acitivity);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        ButterKnife.inject(this);
    }

    public void initView() {
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        shareRadio.setChecked(true);
                        break;
                    case 1:
                        toolRadio.setChecked(true);
                        break;
                }
                invalidateOptionsMenu();
            }
        });

        shareRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                invalidateOptionsMenu();
            }
        });
        toolRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
                invalidateOptionsMenu();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        switch (mViewPager.getCurrentItem()) {
//            case 0:
//                getMenuInflater().inflate(R.menu.menu_qrcode_result, menu);
//                break;
//            case 1:
//                getMenuInflater().inflate(R.menu.main_acitivity, menu);
//                break;
//        }
        getMenuInflater().inflate(R.menu.main_acitivity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.clear();
//        MenuInflater inflater = this.getMenuInflater();
//        switch (mViewPager.getCurrentItem()) {
//            case 0:
//                inflater.inflate(R.menu.menu_share, menu);
//                break;
//
//            case 1:
//                inflater.inflate(R.menu.main_acitivity, menu);
//                break;
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.user_center:
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, AboutApp.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments = new ArrayList<Fragment>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments.size() <= position) {
                switch (position) {
                    case 0:
                        fragments.add(position, new ToolsFragment());
                        break;
                    default:
                        break;
                }
            }
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section3);
                case 1:
                    return getString(R.string.title_section2);
                case 2:
                    return getString(R.string.title_section1);
            }
            return null;
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return false;
    }

}
