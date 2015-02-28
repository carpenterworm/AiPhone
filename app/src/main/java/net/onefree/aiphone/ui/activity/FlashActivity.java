package net.onefree.aiphone.ui.activity;

import android.app.ActionBar;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.onefree.aiphone.R;
import net.onefree.aiphone.controler.CameraManager;

/**
 * Created by admin on 2014/11/17.
 */
public class FlashActivity extends BaseActivity {

    private boolean isopent = false;
    private Camera camera;
    private Button onOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
    }

    public void initView() {

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("手电筒");

        onOff = (Button) findViewById(R.id.on_off);
        onOff.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         if (!isopent) {
                                             onOff.setText("开");
                                             CameraManager.openFlash(FlashActivity.this);
                                         } else {
                                             onOff.setText("关");
                                             CameraManager.closeFlash();
                                             CameraManager.release();
                                         }
                                         isopent = !isopent;
                                     }

                                 }

        );
    }

    @Override
    protected void onDestroy() {

        if (isopent) {
            onOff.performClick();
        }
        super.onDestroy();
    }
}
