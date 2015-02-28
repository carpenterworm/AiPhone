package net.onefree.aiphone.ui.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.onefree.aiphone.R;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by admin on 2014/11/18.
 */
public class PhoneNumberWhereActivity extends BaseActivity implements View.OnClickListener {

    public static String[] operation = {"", "固话", "非正常号", "特服号", "铁通", "铁通固话", "铁通一号通", "联通", "电信", "移动", "联通数据卡", "移动数据卡", "移动G3卡", "虚拟运营商(电信)", "爱施德(电信)", "阿里通信(电信)", "京东通信(电信)", "话机通信(电信)",
            "虚拟运营商(联通)", "中麦通信(联通)", "阿里通信(联通)", "京东通信(联通)", "爱施德(联通)", "话机通信(联通)", "蜗牛移动(联通)", "联通沃卡", "电信天翼4G卡", "电信天翼卡"};

    public static String[] phoneType = {"137", "136", "139", "138", "131", "130", "133", "132", "135", "134", "152", "153", "155", "156", "157", "158", "159", "145", "147", "150", "151", "170",
            "186", "187", "184", "185", "188", "189", "178", "176", "177", "182", "183", "180", "181",};

    private static String FILE_NAME = "location";
    public EditText phoneNumber;
    public Button search;

    public TextView phoneInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_where);
    }

    public void initView() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("电话号码归属地查询");
//        actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_action_back));

        phoneNumber = (EditText) findViewById(R.id.phone_number);
        search = (Button) findViewById(R.id.search);
        phoneInfo = (TextView) findViewById(R.id.phone_info);

        search.setOnClickListener(this);
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPhoneNumber();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                checkPhoneNumber();
                break;
            default:
                break;
        }
    }

    private void checkPhoneNumber() {
        final String phoneUmber = phoneNumber.getText().toString();
        if (phoneUmber.length() < 3) {
            outPrint("");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                search(phoneUmber);
            }
        }).start();
    }

    /**
     * 查询电话归属地
     *
     * @param s
     */
    private void search(String s) {
        s = s.replace("+", "");
        try {
            Long.valueOf(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        String numberHead = s.substring(0, 3);
        boolean isFindMobile = false;
        for (String numberTemp : phoneType) {
            if (numberTemp.equals(numberHead)) {
                isFindMobile = true;
                if (s.length() >= 7) {
                    String numberBody = s.substring(3, 7);
                    searchLocation(numberHead, numberBody);
                } else {
                    outPrint("");
                }
                return;
            }
        }

        if (!isFindMobile) {
            findLocationOther(s);
        }

    }

    private void findLocationOther(String s) {
        boolean isFind = false;
        try {
            DataInputStream dataInputStream = new DataInputStream(getAssets().open("location_other.bin"));

            while (true) {
                int number = dataInputStream.readInt();
                int data = dataInputStream.readShort();
                if (Long.valueOf(s) == number) {
                    String area = getPhoneArea(data % 1000);
                    String operationTemp = operation[data / 1000];
                    outPrint(area + " " + operationTemp);
                    isFind = true;
                    return;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        if(!isFind){
//            outPrint("");
//        }
    }

    private void searchLocation(final String numberHead, final String numberBody) {
        try {
            DataInputStream dataInputStream = new DataInputStream(getAssets().open("location" + numberHead + ".bin"));
            dataInputStream.skip(Integer.valueOf(numberBody) * 2);
            int data = dataInputStream.readShort();
            String area = getPhoneArea(data % 1000);
            String operationTemp = operation[data / 1000];
            outPrint(area + " " + operationTemp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void outPrint(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                phoneInfo.setText(s);
            }
        });
    }

    private String getPhoneArea(int index) {
        String area = "";
        try {
            DataInputStream dataInputStream = new DataInputStream(getAssets().open("location_area.bin"));
            for (int i = 0; i < index; i++) {
                dataInputStream.readUTF();
            }
            area = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return area;
    }

    public void alert(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PhoneNumberWhereActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
