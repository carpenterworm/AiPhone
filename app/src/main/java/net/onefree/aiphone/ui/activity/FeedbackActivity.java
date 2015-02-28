package net.onefree.aiphone.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import net.onefree.aiphone.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2014/12/2.
 */
public class FeedbackActivity extends BaseActivity {

    @InjectView(R.id.content)
    public EditText content;

    @InjectView(R.id.submit)
    public Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    public void initView() {
        ButterKnife.inject(this);
        showBack("我想要");
    }


    private void hintSubmitSuccess() {
        new AlertDialog.Builder(this)
//                .setTitle("反馈成功")//设置标题
                .setMessage("提交成功！")//设置提示消息
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置确定的按键
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)//设置按返回键是否响应返回，这是是不响应
                .show();//显示
    }
}
